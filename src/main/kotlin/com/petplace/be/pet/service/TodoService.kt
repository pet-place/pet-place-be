package com.petplace.be.pet.service

import com.petplace.be.common.enums.ErrorCode
import com.petplace.be.common.exception.CommonException
import com.petplace.be.pet.TodoCategory
import com.petplace.be.pet.domain.Todo
import com.petplace.be.pet.dto.param.TodoSaveParam
import com.petplace.be.pet.dto.param.TodoUpdateParam
import com.petplace.be.pet.dto.result.TodoCountResult
import com.petplace.be.pet.dto.result.TodoResult
import com.petplace.be.pet.repository.TodoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TodoService(
    private val todoRepository: TodoRepository,
    private val petService: PetService
) {

    @Transactional
    fun saveTodo(petId: Long, param: TodoSaveParam): TodoResult{
        // pet 조회
        val pet = petService.findPet(petId)
        val todo = todoRepository.save(
            Todo(
                category = param.category,
                frequency = param.frequency,
                startDate = param.startDate,
                endDate = param.endDate,
                memo = param.memo,
                petId = petId
        ))
        return TodoResult.generateFrom(todo)
    }

    @Transactional
    fun updateTodo(petId: Long, todoId: Long, param: TodoUpdateParam): TodoResult{
        val todo = findTodo(petId, todoId)
        todo.update(
            param.category,
            param.frequency,
            param.startDate,
            param.endDate,
            param.memo
        )
        return TodoResult.generateFrom(todo)
    }

    @Transactional
    fun deleteTodo(petId: Long, todoId: Long){
        findTodo(petId, todoId)
        todoRepository.deleteById(todoId)
    }

    fun findTodoList(petId: Long): MutableList<TodoResult> {
        val pet = petService.findPet(petId)
        return todoRepository.findAllByPetId(petId).stream()
               .map { todo -> TodoResult.generateFrom(todo) }.toList()
    }

    @Transactional
    fun checkTodo(petId: Long, todoId: Long, checked: Boolean): TodoCountResult{
        val todo = findTodo(petId, todoId)
        val count = todo.updateFrequency(checked)
        return TodoCountResult(id = todo.id!!, count = count)
    }

    fun findTodo(petId: Long, id: Long): Todo{
        return todoRepository.findByIdAndPetId(petId, id).orElseThrow { CommonException(ErrorCode.TODO_NOT_FOUND) }
    }
}