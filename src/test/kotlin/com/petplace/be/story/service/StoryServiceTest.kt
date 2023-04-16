package com.petplace.be.story.service

import com.petplace.be.common.exception.CommonException
import com.petplace.be.story.repository.StoryCommentRepository
import com.petplace.be.story.repository.StoryPhotoRepository
import com.petplace.be.story.repository.StoryRepository
import com.petplace.be.story.repository.StoryUserLikeRepository
import com.petplace.be.utils.FileUploader
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.context.annotation.Description
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.multipart.MultipartFile

@ExtendWith(MockitoExtension::class)
internal class StoryServiceTest {
    private val storyRepository = mock(StoryRepository::class.java)
    private val storyPhotoRepository = mock(StoryPhotoRepository::class.java)
    private val fileUploader = mock(FileUploader::class.java)
    private val storyCommentRepository = mock(StoryCommentRepository::class.java)
    private val storyUserLikeRepository = mock(StoryUserLikeRepository::class.java)

    private val storyService: StoryService = StoryService(
        storyRepository,
        storyPhotoRepository,
        fileUploader,
        storyCommentRepository,
        storyUserLikeRepository
    )

    @BeforeEach
    fun setUp() {
        val authentication = mock(UsernamePasswordAuthenticationToken::class.java)
        `when`(authentication.principal).thenReturn(1L)

        val securityContext = mock(SecurityContext::class.java)
        `when`(securityContext.authentication).thenReturn(authentication)

        SecurityContextHolder.setContext(securityContext)
    }

    @Test
    @Description("saveStory 메서드 호출 시 이미지 파일 개수 제한이 초과되었다면 예외가 발생해야 한다.")
    fun saveStory_should_throw_exception_when_image_files_are_over_quantity_limit() {
        // given
        val maxImageFileQuantity = 5
        val imageFiles: ArrayList<MultipartFile> = ArrayList()
        for (i in 0..maxImageFileQuantity) {
            imageFiles.add(MockMultipartFile(i.toString(), null))
        }

        // when and then
        assertThrows<CommonException> { storyService.saveStory("test", "test", imageFiles) }
    }

    @Test
    @Description("saveStory 메서드 호출 시 이미지 파일의 Content-Type이 허용되지 않는다면 예외가 발생해야 한다.")
    fun saveStory_should_throw_exception_when_image_files_has_not_allowed_content_type() {
        // given
        val normalImageFile = MockMultipartFile("test", null, MediaType.IMAGE_PNG_VALUE, null)
        val imageFileWithNotAllowedContentType = MockMultipartFile("test", null, MediaType.APPLICATION_PDF_VALUE, null)

        // when and then
        assertThrows<CommonException> {
            storyService.saveStory(
                "test",
                "test",
                listOf(normalImageFile, imageFileWithNotAllowedContentType)
            )
        }
    }
}
