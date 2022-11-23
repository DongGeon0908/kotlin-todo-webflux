package com.goofy.todo.handler

import com.goofy.todo.application.TodoService
import com.goofy.todo.dto.TodoRequest
import com.goofy.todo.extension.wrapPage
import com.goofy.todo.extension.wrapResponse
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
    private val todoService: TodoService
) {
    fun findById(req: ServerRequest): Mono<ServerResponse> {
        val id = req.pathVariable("id").toLong()
        val response = Mono.justOrEmpty(todoService.findById(id).wrapResponse())

        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(response)
            .switchIfEmpty(ServerResponse.notFound().build())
    }

    fun findAll(req: ServerRequest): Mono<ServerResponse> {
        val page = req.queryParam("page").get().toInt()
        val size = req.queryParam("size").get().toInt()

        val pageable = PageRequest.of(page, size)
        val response = Mono.justOrEmpty(todoService.findAll(pageable).wrapPage())

        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(response)
            .switchIfEmpty(ServerResponse.notFound().build())
    }

    fun add(req: ServerRequest): Mono<ServerResponse> {
        return req.bodyToMono(TodoRequest::class.java).flatMap {
            ServerResponse.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.justOrEmpty(todoService.add(it).wrapResponse()))
                .switchIfEmpty(ServerResponse.notFound().build())
        }
    }

    fun update(req: ServerRequest): Mono<ServerResponse> {
        val id = req.pathVariable("id").toLong()

        return req.bodyToMono(TodoRequest::class.java).flatMap {
            ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.justOrEmpty(todoService.update(id, it).wrapResponse()))
                .switchIfEmpty(ServerResponse.notFound().build())
        }
    }

    fun changedActive(req: ServerRequest): Mono<ServerResponse> {
        val id = req.pathVariable("id").toLong()
        val isActive = req.queryParam("isActive").get().toBoolean()

        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.justOrEmpty(todoService.changedActive(id, isActive)))
            .switchIfEmpty(ServerResponse.notFound().build())
    }

    fun delete(req: ServerRequest): Mono<ServerResponse> {
        val id = req.pathVariable("id").toLong()

        todoService.delete(id)

        return ServerResponse.noContent().build()
    }
}
