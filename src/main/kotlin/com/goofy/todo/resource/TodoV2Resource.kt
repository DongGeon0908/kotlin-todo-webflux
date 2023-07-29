package com.goofy.todo.resource

import com.goofy.todo.application.TodoV2Service
import com.goofy.todo.dto.TodoSearchRequest
import com.goofy.todo.extension.wrap
import com.goofy.todo.extension.wrapPage
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/todo", produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
class TodoV2Resource(
    private val todoV2Service: TodoV2Service
) {
    @GetMapping("/{id}")
    suspend fun get(@PathVariable id: Long) = todoV2Service.get(id).wrap()

    @GetMapping
    suspend fun getAll(
        request: TodoSearchRequest
    ) = todoV2Service.getAll(request).wrapPage()
}
