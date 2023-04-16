package com.petplace.be.story.controller

import com.petplace.be.common.response.BaseResponse
import com.petplace.be.story.dto.*
import com.petplace.be.story.service.StoryService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/stories")
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

    @Operation(summary = "스토리 상세 조회", description = "등록된 스토리 1개를 조회합니다.")
    @GetMapping("/{id}")
    fun getStory(@PathVariable id: Long): BaseResponse<StoryResult> {
        return BaseResponse(data = storyService.getStory(id))
    }

    @Operation(summary = "스토리 목록 조회", description = "페이지 단위로 스토리 목록을 조회합니다.")
    @GetMapping
    fun getStories(@RequestParam page: Int, @RequestParam size: Int): BaseResponse<List<StoryResult>> {
        return BaseResponse(data = storyService.getStories(page - 1, size))
    }

    @Operation(summary = "스토리 좋아요 상태 변경", description = "특정 스토리의 좋아요 개수를 늘리거나 줄입니다.")
    @PutMapping("/{id}/like-status")
    fun updateStoryLikeStatus(
        @PathVariable id: Long,
        @RequestBody updateStoryLikeStatusParam: UpdateStoryLikeStatusParam
    ): BaseResponse<Void> {
        storyService.updateStoryLikeStatus(id, updateStoryLikeStatusParam.like)
        return BaseResponse()
    }

    @Operation(summary = "스토리 댓글 등록", description = "특정 스토리에 새 댓글을 등록합니다.")
    @PostMapping("/{storyId}/comments")
    fun saveStoryComment(
        @PathVariable storyId: Long,
        @RequestBody saveAndUpdateStoryCommentParam: SaveAndUpdateStoryCommentParam
    ): BaseResponse<Long> {
        return BaseResponse(data = storyService.saveStoryComment(storyId, saveAndUpdateStoryCommentParam.contents))
    }

    @Operation(summary = "스토리 댓글 수정", description = "특정 스토리에 등록된 댓글을 수정합니다.")
    @PutMapping("/{storyId}/comments/{commentId}")
    fun updateStoryComment(
        @PathVariable storyId: Long,
        @PathVariable commentId: Long,
        @RequestBody saveAndUpdateStoryCommentParam: SaveAndUpdateStoryCommentParam
    ): BaseResponse<Void> {
        storyService.updateStoryComment(storyId, commentId, saveAndUpdateStoryCommentParam.contents)
        return BaseResponse()
    }

    @Operation(summary = "스토리 댓글 삭제", description = "특정 스토리에 등록된 댓글을 삭제합니다.")
    @DeleteMapping("/{storyId}/comments/{commentId}")
    fun deleteStoryComment(@PathVariable storyId: Long, @PathVariable commentId: Long): BaseResponse<Void> {
        storyService.deleteStoryComment(storyId, commentId)
        return BaseResponse()
    }

    @Operation(summary = "댓글 목록 조회", description = "페이지 단위로 특정 스토리에 등록된 댓글 목록을 조회합니다.")
    @GetMapping("/{storyId}/comments")
    fun getStoryComments(
        @PathVariable storyId: Long,
        @RequestParam page: Int,
        @RequestParam size: Int
    ): BaseResponse<List<StoryCommentResult>> {
        return BaseResponse(storyService.getStoryComments(storyId, page - 1, size))
    }
}
