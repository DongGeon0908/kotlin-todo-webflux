package com.goofy.todo.handler

import com.goofy.todo.application.TodoService
import com.goofy.todo.extension.wrapPage
import com.goofy.todo.extension.wrapResponse
import org.springframework.data.domain.PageRequest
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

        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.justOrEmpty(todoService.findById(id).wrapResponse()))
            .switchIfEmpty(ServerResponse.notFound().build())
    }

    fun findAll(req: ServerRequest): Mono<ServerResponse> {
        val page = req.queryParam("page").get().toInt()
        val size = req.queryParam("size").get().toInt()

        val pageable = PageRequest.of(page, size)

        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.justOrEmpty(todoService.findAll(pageable).wrapPage()))
            .switchIfEmpty(ServerResponse.notFound().build())
    }
}
