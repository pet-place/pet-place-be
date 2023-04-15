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
@RequestMapping("/pets/{petId}/todos")
class TodoController(
    private val todoService: TodoService
) {
    @Operation(summary = "반려동물 todo 등록", description = "반려동물의 Todo 를 등록합니다.")
    @PostMapping
    fun saveTodo(@PathVariable petId: Long
                 ,@RequestBody param: TodoSaveParam): BaseResponse<TodoResult>{
        val response = todoService.saveTodo(petId, param)
        return BaseResponse(response)
    }

    @Operation(summary = "반려동물 todo 수정", description = "반려동물의 Todo 를 수정합니다.")
    @PutMapping("/{todoId}")
    fun updateTodo(@PathVariable petId: Long
                   ,@PathVariable todoId: Long
                   ,@RequestBody param: TodoUpdateParam): BaseResponse<TodoResult>{
        val response = todoService.updateTodo(petId, todoId, param)
        return BaseResponse(response)
    }

    @Operation(summary = "반려동물 todo 삭제", description = "반려동물의 Todo 를 삭제합니다.")
    @DeleteMapping("/{todoId}")
    fun deleteTodo(@PathVariable petId: Long
                   ,@PathVariable todoId: Long): BaseResponse<Void>{
        todoService.deleteTodo(petId, todoId)
        return BaseResponse()
    }

    @Operation(summary = "반려동물 todo 목록 조회", description = "반려동물의 Todo 목록을 조회합니다.")
    @GetMapping
    fun findTodoList(@PathVariable petId: Long): BaseResponse<MutableList<TodoResult>>{
        val response = todoService.findTodoList(petId)
        return BaseResponse(response)
    }

    @Operation(summary = "반려동물 todo 완료/미완료 처리", description = "반려동물 todo를 완료/미완료 합니다.", parameters = [
        Parameter(name = "checked", description = "todo를 체크하면 true, 체크 해제하면 false 입니다."),
    ])
    @PutMapping("/{todoId}/completion-status")
    fun checkTodo(@PathVariable petId: Long
                  ,@PathVariable todoId: Long
                  ,@RequestParam checked: Boolean): BaseResponse<TodoCountResult>{
        val todoCountResult = todoService.checkTodo(petId, todoId, checked)
        return BaseResponse(todoCountResult)
    }
}