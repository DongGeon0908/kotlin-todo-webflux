package com.goofy.todo.application

import com.goofy.todo.domain.Todo
import com.goofy.todo.infrastructure.TodoRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class TodoService(
    private val todoRepository: TodoRepository
) {
    fun findById(id: Long): Todo? {
        return todoRepository.findByIdOrNull(id)
    }

    fun findAll(): List<Todo> {
        return todoRepository.findAll()
    }
}
