package com.petplace.be.story.controller

import com.petplace.be.common.response.BaseResponse
import com.petplace.be.story.dto.SaveAndUpdateStoryParam
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
    @Operation(summary = "스토리 등록", description = "새 스토리가 등록됩니다.")
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

    @Operation(summary = "스토리 수정", description = "등록된 스토리가 수정됩니다.")
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

    @Operation(summary = "스토리 삭제", description = "등록된 스토리가 삭제됩니다.")
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
}
