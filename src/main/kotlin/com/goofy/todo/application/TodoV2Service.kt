package com.goofy.todo.application

import com.goofy.todo.dto.TodoResponse
import com.goofy.todo.dto.TodoSearchRequest
import com.goofy.todo.exception.TodoNotFoundException
import com.goofy.todo.infrastructure.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class TodoV2Service(
    private val todoRepository: TodoRepository
) {
    suspend fun get(id: Long): TodoResponse {
        val todo = withContext(Dispatchers.IO) {
            todoRepository.findByIdOrNull(id)
        } ?: throw TodoNotFoundException()

        return TodoResponse.from(todo)
    }

    suspend fun getAll(
        request: TodoSearchRequest
    ): Page<TodoResponse> {
        return withContext(Dispatchers.IO) {
            todoRepository.findAll(
                PageRequest.of(request.page ?: 0, request.size ?: 20)
            )
        }.map { todo -> TodoResponse.from(todo) }
    }
}
