package com.petplace.be.story.service

import com.petplace.be.common.enums.ErrorCode
import com.petplace.be.common.exception.CommonException
import com.petplace.be.story.domain.Story
import com.petplace.be.story.domain.StoryComment
import com.petplace.be.story.domain.StoryPhoto
import com.petplace.be.story.domain.StoryUserLike
import com.petplace.be.story.dto.StoryCommentResult
import com.petplace.be.story.dto.StoryPhotoResult
import com.petplace.be.story.dto.StoryResult
import com.petplace.be.story.repository.StoryCommentRepository
import com.petplace.be.story.repository.StoryPhotoRepository
import com.petplace.be.story.repository.StoryRepository
import com.petplace.be.story.repository.StoryUserLikeRepository
import com.petplace.be.utils.FileUploader
import org.springframework.data.domain.PageRequest
import org.springframework.http.MediaType
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.io.File


@Service
@Transactional
class StoryService(
    val storyRepository: StoryRepository,
    val storyPhotoRepository: StoryPhotoRepository,
    val fileUploader: FileUploader,
    val storyCommentRepository: StoryCommentRepository,
    val storyUserLikeRepository: StoryUserLikeRepository,
) {
    companion object {
        private const val MAX_IMAGE_FILE_QUANTITY = 5
        private val ALLOWED_CONTENT_TYPES = listOf<String>(MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE)
    }

    fun saveStory(title: String, contents: String, imageFiles: List<MultipartFile>): Long {
        val userId = getCurrentUserId()

        validateImageFiles(imageFiles)

        val savedStory =
            storyRepository.save(Story(title = title, contents = contents, createdBy = userId, modifiedBy = userId))
        val storyId = savedStory.id!!

        val storyPhotos = uploadImageFilesAndSaveStoryPhotos(imageFiles, storyId, userId)
        savedStory.photos = storyPhotos

        return storyId
    }

    private fun getCurrentUserId(): Long {
        return SecurityContextHolder.getContext().authentication.principal as Long
    }

    private fun validateImageFiles(files: List<MultipartFile>) {
        if (files.isEmpty()) {
            return
        }
        // TODO :: merge 이후 에러 코드 생성
        if (MAX_IMAGE_FILE_QUANTITY < files.size) {
            throw CommonException(ErrorCode.UNKNOWN)
        }
        if (!files.all { file -> ALLOWED_CONTENT_TYPES.contains(file.contentType) }) {
            throw CommonException(ErrorCode.UNKNOWN)
        }
    }

    private fun uploadImageFilesAndSaveStoryPhotos(
        imageFiles: List<MultipartFile>,
        storyId: Long,
        userId: Long
    ): MutableList<StoryPhoto> {
        var fileNumber = 0
        val storyPhotos = mutableListOf<StoryPhoto>()
        imageFiles.forEach { file ->
            run {
                val uri = fileUploader.upload(file, "${storyId}${File.separator}${fileNumber++}")
                storyPhotos.add(
                    storyPhotoRepository.save(
                        StoryPhoto(
                            storyId = storyId,
                            uri = uri,
                            createdBy = userId,
                            modifiedBy = userId
                        )
                    )
                )
            }
        }
        return storyPhotos
    }

    fun updateStory(storyId: Long, title: String, contents: String, imageFiles: List<MultipartFile>) {
        val userId = getCurrentUserId()

        val story = findStory(storyId)
        story.title = title
        story.contents = contents
        story.modifiedBy = userId

        val oldStoryPhotos = story.photos.toMutableList()

        validateImageFiles(imageFiles)
        val newStoryPhotos = uploadImageFilesAndSaveStoryPhotos(imageFiles, storyId, userId)

        deleteStoryPhotos(oldStoryPhotos)

        story.photos = newStoryPhotos
    }

    private fun findStory(storyId: Long): Story {
        return storyRepository.findById(storyId).orElseThrow { throw CommonException(ErrorCode.UNKNOWN) }
    }

    private fun deleteStoryPhotos(storyPhotos: List<StoryPhoto>) {
        storyPhotos.forEach { storyPhoto ->
            run {
                val uri = storyPhoto.uri!!
                storyPhotoRepository.delete(storyPhoto)
                if (!storyPhotoRepository.existsByUri(uri)) {
                    fileUploader.delete(uri)
                }
            }
        }
    }

    fun deleteStory(storyId: Long) {
        val story = findStory(storyId)
        deleteStoryPhotos(story.photos)
        storyUserLikeRepository.deleteByStoryId(storyId)
        storyRepository.delete(story)
    }

    @Transactional(readOnly = true)
    fun getStory(storyId: Long): StoryResult {
        val story = findStory(storyId)
        val userId = getCurrentUserId()
        return convertToStoryResult(story, userId)
    }

    private fun convertToStoryResult(story: Story, userId: Long): StoryResult {
        val storyId = story.id!!
        val storyPhotoResults = story.photos.map { storyPhoto -> convertToStoryPhotoResult(storyPhoto) }
        return StoryResult(
            id = storyId,
            title = story.title!!,
            contents = story.contents!!,
            photos = storyPhotoResults,
            isLikedStory = storyUserLikeRepository.existsByStoryIdAndUserId(storyId, userId)
        )
    }

    private fun convertToStoryPhotoResult(storyPhoto: StoryPhoto): StoryPhotoResult {
        val uri = storyPhoto.uri!!
        return StoryPhotoResult(order = extractStoryPhotoOrder(uri), uri = uri)
    }

    private fun extractStoryPhotoOrder(uri: String): Int {
        val uriComponents: List<String> = uri.split("/")
        return uriComponents.last().split(".")[0].toInt()
    }

    @Transactional(readOnly = true)
    fun getStories(page: Int, size: Int): List<StoryResult> {
        val pageable = PageRequest.of(page, size)
        val userId = getCurrentUserId()
        return storyRepository.findAllByOrderByIdDesc(pageable).map { story -> convertToStoryResult(story, userId) }
    }

    fun saveStoryComment(storyId: Long, contents: String): Long {
        findStory(storyId)

        val userId = getCurrentUserId()

        val storyComment = StoryComment(storyId = storyId, contents = contents, createdBy = userId, modifiedBy = userId)
        val savedStoryComment = storyCommentRepository.save(storyComment)
        return savedStoryComment.id!!
    }

    fun updateStoryComment(storyId: Long, commentId: Long, contents: String) {
        findStory(storyId)

        val userId = getCurrentUserId()

        val storyComment = findStoryComment(commentId)
        storyComment.contents = contents
        storyComment.modifiedBy = userId
    }

    private fun findStoryComment(commentId: Long): StoryComment {
        return storyCommentRepository.findById(commentId).orElseThrow { throw CommonException(ErrorCode.UNKNOWN) }
    }

    fun deleteStoryComment(storyId: Long, commentId: Long) {
        findStory(storyId)
        val storyComment = findStoryComment(commentId)
        storyCommentRepository.delete(storyComment)
    }

    fun getStoryComments(storyId: Long, page: Int, size: Int): List<StoryCommentResult> {
        val pageable = PageRequest.of(page, size)
        return storyCommentRepository.findAllByOrderByIdDesc(pageable)
            .map { storyComment -> convertToStoryCommentResult(storyComment) }
    }

    private fun convertToStoryCommentResult(storyComment: StoryComment): StoryCommentResult {
        return StoryCommentResult(
            id = storyComment.id!!,
            contents = storyComment.contents!!,
        )
    }

    fun updateStoryLikeStatus(storyId: Long, like: Boolean) {
        val story = findStory(storyId)
        val userId = getCurrentUserId()
        if (like) {
            increaseStoryLikeCount(storyId, userId)
        } else {
            decreaseStoryLikeCount(storyId, userId)
        }
        updateStoryLikeCount(story)
    }

    private fun increaseStoryLikeCount(storyId: Long, userId: Long) {
        if (storyUserLikeRepository.existsByStoryIdAndUserId(storyId, userId)) {
            throw CommonException(ErrorCode.UNKNOWN)
        }
        storyUserLikeRepository.save(StoryUserLike(storyId = storyId, userId = userId))
    }

    private fun decreaseStoryLikeCount(storyId: Long, userId: Long) {
        val storyUserLike = storyUserLikeRepository.findByStoryIdAndUserId(storyId, userId)
            .orElseThrow { throw CommonException(ErrorCode.UNKNOWN) }
        storyUserLikeRepository.delete(storyUserLike)
    }

    private fun updateStoryLikeCount(story: Story) {
        story.likeCount = storyUserLikeRepository.countAllByStoryId(story.id!!)
    }
}
