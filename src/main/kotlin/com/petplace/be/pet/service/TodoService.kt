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
    fun saveTodo(param: TodoSaveParam): TodoResult{
        val pet = petService.findPet(param.petId)
        val todo = todoRepository.save(
            Todo(
                category = param.category,
                frequency = param.frequency,
                startDate = param.startDate,
                endDate = param.endDate,
                memo = param.memo,
                pet = pet
        ))
        return TodoResult.generateFrom(todo)
    }

    @Transactional
    fun updateTodo(param: TodoUpdateParam): TodoResult{
        val todo = findTodo(param.id)
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
    fun deleteTodo(id: Long){
        todoRepository.deleteById(id)
    }

    fun findTodoList(petId: Long): MutableList<TodoResult> {
        val pet = petService.findPet(petId)
        return todoRepository.findAllByPet(pet).stream()
               .map { todo -> TodoResult.generateFrom(todo) }.toList()
    }

    fun categoryList(): List<String>{
        val array = TodoCategory.values()
        val categoryList = mutableListOf<String>()
        for (a in array){
            categoryList.add(a.korValue)
        }
        return categoryList
    }

    @Transactional
    fun checkTodo(checked: Boolean, id: Long): TodoCountResult{
        val todo = findTodo(id)
        val count = todo.updateFrequency(checked)
        return TodoCountResult(id = todo.id!!, count = count)
    }

    fun findTodo(id: Long): Todo{
        return todoRepository.findById(id).orElseThrow { CommonException(ErrorCode.TODO_NOT_FOUND) }
    }
}