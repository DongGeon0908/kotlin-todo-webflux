package com.goofy.todo.dto

import com.goofy.todo.domain.vo.TodoCategory

data class TodoRequest(
    val title: String,
    val content: String,
    val category: TodoCategory
)

data class TodoSearchRequest(
    val page: Int?,
    val size: Int?
)
