package com.petplace.be.story.controller

import com.petplace.be.common.response.BaseResponse
import com.petplace.be.story.dto.SaveAndUpdateStoryCommentParam
import com.petplace.be.story.dto.SaveAndUpdateStoryParam
import com.petplace.be.story.dto.StoryCommentResult
import com.petplace.be.story.dto.StoryResult
import com.petplace.be.story.service.StoryService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/story")
class StoryController(
    val storyService: StoryService,
) {
    @Operation(summary = "스토리 등록", description = "새 스토리를 등록합니다.")
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun saveStory(saveStoryParam: SaveAndUpdateStoryParam): BaseResponse<Long> {
        return BaseResponse(
            data = storyService.saveStory(
                saveStoryParam.title,
                saveStoryParam.contents,
                saveStoryParam.files
            )
        )
    }

    @Operation(summary = "스토리 수정", description = "등록된 스토리를 수정합니다.")
    @PutMapping("/{id}", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun updateStory(@PathVariable id: Long, updateStoryParam: SaveAndUpdateStoryParam): BaseResponse<Void> {
        storyService.updateStory(
            id,
            updateStoryParam.title,
            updateStoryParam.contents,
            updateStoryParam.files
        )
        return BaseResponse()
    }

    @Operation(summary = "스토리 삭제", description = "등록된 스토리를 삭제합니다.")
    @DeleteMapping("/{id}")
    fun deleteStory(@PathVariable id: Long): BaseResponse<Void> {
        storyService.deleteStory(id)
        return BaseResponse()
    }

    @Operation(summary = "스토리 1개 조회", description = "등록된 스토리 1개를 조회합니다.")
    @GetMapping("/{id}")
    fun getStory(@PathVariable id: Long): BaseResponse<StoryResult> {
        return BaseResponse(data = storyService.getStory(id))
    }

    @Operation(summary = "스토리 목록 조회", description = "일정 개수의 스토리들을 조회합니다.")
    @GetMapping
    fun getStories(@RequestParam page: Int, @RequestParam size: Int): BaseResponse<List<StoryResult>> {
        return BaseResponse(data = storyService.getStories(page - 1, size))
    }

    @Operation(summary = "댓글 등록", description = "특정 스토리에 새 댓글을 등록합니다.")
    @PostMapping("/{storyId}/comment")
    fun saveStoryComment(
        @PathVariable storyId: Long,
        @RequestBody saveAndUpdateStoryCommentParam: SaveAndUpdateStoryCommentParam
    ): BaseResponse<Long> {
        return BaseResponse(data = storyService.saveStoryComment(storyId, saveAndUpdateStoryCommentParam.contents))
    }

    @Operation(summary = "댓글 수정", description = "특정 스토리에 등록된 댓글을 수정합니다.")
    @PutMapping("/{storyId}/comment/{storyCommentId}")
    fun updateStoryComment(
        @PathVariable storyId: Long,
        @PathVariable storyCommentId: Long,
        @RequestBody saveAndUpdateStoryCommentParam: SaveAndUpdateStoryCommentParam
    ): BaseResponse<Void> {
        storyService.updateStoryComment(storyId, storyCommentId, saveAndUpdateStoryCommentParam.contents)
        return BaseResponse()
    }

    @Operation(summary = "댓글 삭제", description = "특정 스토리에 등록된 댓글을 삭제합니다.")
    @DeleteMapping("/{storyId}/comment/{storyCommentId}")
    fun deleteStoryComment(@PathVariable storyId: Long, @PathVariable storyCommentId: Long): BaseResponse<Void> {
        storyService.deleteStoryComment(storyId, storyCommentId)
        return BaseResponse()
    }

    @Operation(summary = "댓글 목록 조회", description = "특정 스토리에 등록된 일정 개수의 댓글들을 조회합니다.")
    @GetMapping("/{storyId}/comment")
    fun getStoryComments(
        @PathVariable storyId: Long,
        @RequestParam page: Int,
        @RequestParam size: Int
    ): BaseResponse<List<StoryCommentResult>> {
        return BaseResponse(storyService.getStoryComments(storyId, page - 1, size))
    }

    @Operation(summary = "좋아요 추가", description = "특정 스토리에 좋아요를 추가합니다.")
    @PostMapping("/{storyId}/like")
    fun saveStoryUserLike(@PathVariable storyId: Long): BaseResponse<Void> {
        storyService.saveStoryUserLike(storyId)
        return BaseResponse()
    }

    @Operation(summary = "좋아요 제거", description = "특정 스토리에 추가했던 특정 사용자의 좋아요를 제거합니다.")
    @DeleteMapping("/{storyId}/like")
    fun deleteStoryUserLike(@PathVariable storyId: Long): BaseResponse<Void> {
        storyService.deleteStoryUserLike(storyId)
        return BaseResponse()
    }
}
