package com.goofy.todo.exception

import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono

@RestControllerAdvice
class GlobalExceptionHandler {
    private val logger = KotlinLogging.logger { }

    @ExceptionHandler(BusinessException::class)
    protected fun handleBusinessException(e: BusinessException): Mono<ServerResponse> {
        logger.error { "BusinessException ${e.message}" }

        val response = ErrorResponse(e.errorCode, e)

        return ServerResponse.status(e.errorCode.status)
            .body(Mono.justOrEmpty(response))
            .switchIfEmpty(ServerResponse.notFound().build())
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    protected fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): Mono<ServerResponse> {
        logger.error("HttpMessageNotReadableException", e)

        val response = ErrorResponse(ErrorCode.INVALID_INPUT_VALUE_ERROR, e)

        return ServerResponse.status(HttpStatus.BAD_REQUEST)
            .body(Mono.justOrEmpty(response))
            .switchIfEmpty(ServerResponse.notFound().build())
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    protected fun handleMethodArgumentTypeMismatchException(e: MethodArgumentTypeMismatchException): Mono<ServerResponse> {
        logger.error("MethodArgumentTypeMismatchException", e)

        val response = ErrorResponse(ErrorCode.INVALID_TYPE_VALUE_ERROR, e)

        return ServerResponse.status(HttpStatus.BAD_REQUEST)
            .body(Mono.justOrEmpty(response))
            .switchIfEmpty(ServerResponse.notFound().build())
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    protected fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): Mono<ServerResponse> {
        logger.error("MethodArgumentNotValidException", e)

        val response = ErrorResponse(ErrorCode.INVALID_INPUT_VALUE_ERROR, e.bindingResult)

        return ServerResponse.status(HttpStatus.BAD_REQUEST)
            .body(Mono.justOrEmpty(response))
            .switchIfEmpty(ServerResponse.notFound().build())
    }
}
