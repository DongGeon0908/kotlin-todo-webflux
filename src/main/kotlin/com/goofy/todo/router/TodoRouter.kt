package com.goofy.todo.router

import com.goofy.todo.handler.TodoHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RequestPredicates.path
import org.springframework.web.reactive.function.server.RouterFunctions.nest
import org.springframework.web.reactive.function.server.router

@Configuration
class TodoRouter(
    private val todoHandler: TodoHandler
) {
    @Bean
    fun routeTodo() = nest(
        path("/api/v1/todos"),
        router {
            GET("/{id}", todoHandler::findById)
            GET("", todoHandler::findAll)
            POST("", todoHandler::add)
            PUT("/{id}", todoHandler::update)
            PATCH("/{id}", todoHandler::changedActive)
            DELETE("/{id}", todoHandler::delete)
        }
    )
}
