package com.petplace.be.story.controller

import com.petplace.be.common.response.BaseResponse
import com.petplace.be.story.dto.SaveStoryParam
import com.petplace.be.story.service.StoryService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/story")
class StoryController(
    val storyService: StoryService,
) {
    @Operation(summary = "스토리 등록", description = "")
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun saveStory(saveStoryParam: SaveStoryParam): BaseResponse<Long> {
        return BaseResponse(
            data = storyService.saveStory(
                saveStoryParam.title,
                saveStoryParam.contents,
                saveStoryParam.files
            )
        )
    }
}
