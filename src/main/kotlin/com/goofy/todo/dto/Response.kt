package com.goofy.todo.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.goofy.todo.domain.Todo
import com.goofy.todo.domain.vo.TodoCategory
import java.time.LocalDateTime

data class TodoResponse(
    val id: Long,
    val title: String,
    val content: String,
    val category: TodoCategory,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Asia/Seoul")
    val createdAt: LocalDateTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Asia/Seoul")
    val modifiedAt: LocalDateTime
) {
    companion object {
        fun from(todo: Todo): TodoResponse {
            return TodoResponse(
                id = todo.id,
                title = todo.title,
                content = todo.content,
                category = todo.category,
                createdAt = todo.createdAt,
                modifiedAt = todo.modifiedAt
            )
        }
    }
}
