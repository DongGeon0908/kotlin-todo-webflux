package com.goofy.todo.exception

open class BusinessException(
    val errorCode: ErrorCode,
    override val message: String? = errorCode.description
) : RuntimeException(message ?: errorCode.description)

/**
 *
 * Todo Exception
 **/
class TodoNotFoundException(message: String? = null) : BusinessException(
    errorCode = ErrorCode.TODO_NOT_FOUND_ERROR,
    message = message
)
