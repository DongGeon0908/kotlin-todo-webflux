package com.goofy.todo.extension

import com.goofy.todo.common.PageResponseDto
import com.goofy.todo.common.ResponseDto
import org.springframework.data.domain.Page

/**
 * Wrap Response Ok
 **/
fun <T> T.wrapResponse() = ResponseDto(this)

/**
 * Wrap Response Page
 **/
fun <T> Page<T>.wrapPage() = PageResponseDto(this)
