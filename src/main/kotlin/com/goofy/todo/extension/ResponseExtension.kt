package com.goofy.todo.extension

import com.goofy.todo.common.ResponseDto

/**
 * Wrap Response Ok
 **/
fun <T> T.wrapResponse() = ResponseDto(this)
