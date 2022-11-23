package com.goofy.todo.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(val status: HttpStatus, val description: String) {
    /**
     * Common Error Code
     **/
    INVALID_INPUT_VALUE_ERROR(HttpStatus.BAD_REQUEST, "잘못된 입력 입니다."),
    INVALID_TYPE_VALUE_ERROR(HttpStatus.BAD_REQUEST, "잘못된 입력 타입 입니다."),

    /**
     * Todo Error Code
     **/
    TODO_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, "todo를 찾을 수 없습니다."),
    ;
}
