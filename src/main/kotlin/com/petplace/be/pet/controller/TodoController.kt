package com.petplace.be.pet.controller

import com.petplace.be.common.response.BaseResponse
import com.petplace.be.pet.dto.param.TodoSaveParam
import com.petplace.be.pet.dto.param.TodoUpdateParam
import com.petplace.be.pet.dto.result.TodoCountResult
import com.petplace.be.pet.dto.result.TodoResult
import com.petplace.be.pet.service.TodoService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "Todo", description = "todo API")
@RestController
@RequestMapping("/todo")
class TodoController(
    private val todoService: TodoService
) {

    @Operation(summary = "카테고리 목록 조회", description = "Todo 등록시 사용할 카테고리 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "한글 문자열로 반환됩니다.")
    @GetMapping("/categories")
    fun getCategoryList(): BaseResponse<List<String>> {
        val response = todoService.categoryList()
        return BaseResponse(response)
    }

    @Operation(summary = "Todo 등록", description = "반려동물의 Todo 를 등록합니다.")
    @PostMapping
    fun saveTodo(@RequestBody param: TodoSaveParam): BaseResponse<TodoResult>{
        val response = todoService.saveTodo(param)
        return BaseResponse(response)
    }

    @Operation(summary = "Todo 수정", description = "반려동물의 Todo 를 수정합니다.")
    @PutMapping
    fun updateTodo(@RequestBody param: TodoUpdateParam): BaseResponse<TodoResult>{
        val response = todoService.updateTodo(param)
        return BaseResponse(response)
    }

    @Operation(summary = "Todo 삭제", description = "반려동물의 Todo 를 삭제합니다.")
    @DeleteMapping("/{id}")
    fun deleteTodo(@PathVariable id: Long): BaseResponse<Void>{
        todoService.deleteTodo(id)
        return BaseResponse()
    }

    @Operation(summary = "Todo 목록 조회", description = "반려동물의 Todo 목록을 조회합니다.")
    @GetMapping("/{petId}")
    fun findTodoList(@PathVariable petId: Long): BaseResponse<MutableList<TodoResult>>{
        val response = todoService.findTodoList(petId)
        return BaseResponse(response)
    }

    @Operation(summary = "todo 체크", description = "todo를 체크 또는 체크 해제 합니다.", parameters = [
        Parameter(name = "checked", description = "todo를 체크하면 true, 체크를 해제하면 false 입니다."),
    ])
    @PutMapping("/click/{id}")
    fun checkTodo(@PathVariable id: Long,
                  @RequestParam checked: Boolean): BaseResponse<TodoCountResult>{
        val todoCountResult = todoService.checkTodo(checked, id)
        return BaseResponse(todoCountResult)
    }

}