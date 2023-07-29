package com.goofy.todo.handler

import com.goofy.todo.application.TodoV1Service
import com.goofy.todo.dto.TodoRequest
import com.goofy.todo.extension.param
import com.goofy.todo.extension.response
import com.goofy.todo.extension.wrapPage
import com.goofy.todo.extension.wrap
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono

@Component
class TodoHandler(
    private val todoService: TodoV1Service
) {
    fun findById(req: ServerRequest): Mono<ServerResponse> {
        val id = req.pathVariable("id").toLong()
        val response = Mono.justOrEmpty(todoService.findById(id).wrap())

        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(response)
            .switchIfEmpty(ServerResponse.notFound().build())
    }

    fun findAll(req: ServerRequest): Mono<ServerResponse> {
        val page = req.param("page").toInt()
        val size = req.param("size").toInt()

        val pageable = PageRequest.of(page, size)
        val todos = todoService.findAll(pageable).wrapPage()

        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(todos.response())
            .switchIfEmpty(ServerResponse.notFound().build())
    }

    fun add(req: ServerRequest): Mono<ServerResponse> {
        return req.bodyToMono(TodoRequest::class.java).flatMap {
            ServerResponse.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(todoService.add(it).wrap().response())
                .switchIfEmpty(ServerResponse.notFound().build())
        }
    }

    fun update(req: ServerRequest): Mono<ServerResponse> {
        val id = req.pathVariable("id").toLong()

        return req.bodyToMono(TodoRequest::class.java).flatMap {
            ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(todoService.update(id, it).wrap().response())
                .switchIfEmpty(ServerResponse.notFound().build())
        }
    }

    fun changedActive(req: ServerRequest): Mono<ServerResponse> {
        val id = req.pathVariable("id").toLong()
        val isActive = req.queryParam("isActive").get().toBoolean()

        val todo = todoService.changedActive(id, isActive)

        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(todo.response())
            .switchIfEmpty(ServerResponse.notFound().build())
    }

    fun delete(req: ServerRequest): Mono<ServerResponse> {
        val id = req.pathVariable("id").toLong()

        todoService.delete(id)

        return ServerResponse.noContent().build()
    }
}
