package com.petplace.be.story.service

import com.petplace.be.common.enums.ErrorCode
import com.petplace.be.common.exception.CommonException
import com.petplace.be.story.domain.Story
import com.petplace.be.story.domain.StoryPhoto
import com.petplace.be.story.repository.StoryPhotoRepository
import com.petplace.be.story.repository.StoryRepository
import com.petplace.be.utils.FileUploader
import org.springframework.http.MediaType
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile


@Service
@Transactional
class StoryService(
    val storyRepository: StoryRepository,
    val storyPhotoRepository: StoryPhotoRepository,
    val fileUploader: FileUploader,
) {
    companion object {
        val ALLOWED_CONTENT_TYPES = listOf<String>(MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE)
    }

    fun saveStory(title: String, contents: String, imageFiles: List<MultipartFile>): Long {
        // TODO :: BaseEntity 수정 후 사용
        val userId = SecurityContextHolder.getContext().authentication.principal

        validateImageFiles(imageFiles)

        val savedStory = storyRepository.save(Story(title = title, contents = contents))
        val storyId = savedStory.id!!

        val storyPhotos = uploadImageFilesAndSaveStoryPhotos(imageFiles, storyId)
        savedStory.photos = storyPhotos

        return storyId
    }

    private fun validateImageFiles(files: List<MultipartFile>) {
        if (files.isEmpty()) {
            return
        }
        if (!files.all { file -> ALLOWED_CONTENT_TYPES.contains(file.contentType) }) {
            throw CommonException(ErrorCode.UNKNOWN)
        }
    }

    private fun uploadImageFilesAndSaveStoryPhotos(
        imageFiles: List<MultipartFile>,
        storyId: Long
    ): MutableList<StoryPhoto> {
        var fileNumber = 0
        val storyPhotos = mutableListOf<StoryPhoto>()
        imageFiles.forEach { file ->
            run {
                val uri = fileUploader.upload(file, "${fileNumber++}")
                storyPhotos.add(storyPhotoRepository.save(StoryPhoto(storyId = storyId, uri = uri)))
            }
        }
        return storyPhotos
    }
}
