package com.goofy.todo.handler

import com.goofy.todo.application.TodoService
import com.goofy.todo.extension.wrapResponse
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
    fun findById(req: ServerRequest): Mono<ServerResponse> = ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(Mono.justOrEmpty(todoService.findById(req.pathVariable("id").toLong()).wrapResponse()))
        .switchIfEmpty(ServerResponse.notFound().build())

    fun findAll(req: ServerRequest): Mono<ServerResponse> = ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(Mono.justOrEmpty(todoService.findAll().wrapResponse()))
        .switchIfEmpty(ServerResponse.notFound().build())
}
