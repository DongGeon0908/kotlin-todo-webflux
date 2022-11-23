package com.goofy.todo.application

import com.goofy.todo.domain.Todo
import com.goofy.todo.dto.TodoRequest
import com.goofy.todo.exception.TodoNotFoundException
import com.goofy.todo.infrastructure.TodoRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class TodoService(
    private val todoRepository: TodoRepository
) {
    fun findById(id: Long): Todo {
        return todoRepository.findByIdOrNull(id) ?: throw TodoNotFoundException()
    }

    fun findAll(pageable: Pageable): Page<Todo> {
        return todoRepository.findAll(pageable)
    }

    fun add(request: TodoRequest): Todo {
        return todoRepository.save(
            Todo(
                title = request.title,
                content = request.content,
                category = request.category
            )
        )
    }

    fun update(id: Long, request: TodoRequest): Todo {
        val todo = todoRepository.findByIdOrNull(id) ?: throw TodoNotFoundException()

        return todoRepository.save(
            todo.apply {
                this.update(
                    title = request.title,
                    content = request.content,
                    category = request.category
                )
            }
        )
    }

    fun changedActive(id: Long, isActive: Boolean): Todo {
        val todo = todoRepository.findByIdOrNull(id) ?: throw TodoNotFoundException()

        return todoRepository.save(
            todo.apply { this.changedActive(isActive) }
        )
    }

    fun delete(id: Long) {
        if (todoRepository.existsById(id)) {
            todoRepository.deleteById(id)
        }
    }
}
