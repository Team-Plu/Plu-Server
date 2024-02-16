package com.th.plu.common.aop.advice

import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.th.plu.common.dto.response.ApiResponse
import com.th.plu.common.exception.code.ErrorCode
import com.th.plu.common.exception.model.*
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException
import org.springframework.web.HttpMediaTypeException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.ServletRequestBindingException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.resource.NoResourceFoundException

@RestControllerAdvice
class ExceptionControllerAdvice {
    private val log = LoggerFactory.getLogger(this.javaClass)

    /**
     * 400 Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException::class)
    fun handleValidationException(exception: ValidationException): ApiResponse<Any> {
        log.error(exception.message)
        return ApiResponse.error(exception.errorCode)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(exception: MethodArgumentNotValidException): ApiResponse<Any> {
        log.error(exception.message, exception);
        return ApiResponse.error(
            ErrorCode.METHOD_ARGUMENT_NOT_VALID_EXCEPTION,
            exception.bindingResult.fieldError?.defaultMessage.toString()
        )
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException::class)
    fun handleBindException(exception: BindException): ApiResponse<Any> {
        log.error(exception.message, exception);
        return ApiResponse.error(
            ErrorCode.BIND_EXCEPTION,
            exception.bindingResult.fieldError?.defaultMessage.toString()
        )
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(
        value = [
            HttpMessageNotReadableException::class,
            InvalidFormatException::class,
            ServletRequestBindingException::class
        ]
    )
    fun handleInvalidFormatException(exception: Exception): ApiResponse<Any> {
        log.error(exception.message, exception);
        return ApiResponse.error(ErrorCode.INVALID_FORMAT_EXCEPTION);
    }

    /**
     * 401 Unauthorized
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException::class)
    fun handleUnauthorizedException(exception: UnauthorizedException): ApiResponse<Any> {
        log.error(exception.message, exception)
        return ApiResponse.error(exception.errorCode)
    }

    /**
     * 403 Forbidden
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException::class)
    fun handleForbiddenException(exception: ForbiddenException): ApiResponse<Any> {
        log.error(exception.message, exception)
        return ApiResponse.error(exception.errorCode)
    }

    /**
     * 404 Not Found
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(exception: NotFoundException): ApiResponse<Any> {
        log.error(exception.message, exception)
        return ApiResponse.error(exception.errorCode)
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(
        value = [
            NoHandlerFoundException::class,
            NoResourceFoundException::class]
    )
    fun handleNotFoundEndpointException(exception: Exception): ApiResponse<Any> {
        log.error(exception.message, exception)
        return ApiResponse.error(ErrorCode.NOT_FOUND_ENDPOINT_EXCEPTION)
    }

    /**
     * 405 Method Not Supported
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleHttpRequestMethodNotSupportedException(exception: HttpRequestMethodNotSupportedException): ApiResponse<Any> {
        return ApiResponse.error(ErrorCode.METHOD_NOT_ALLOWED_EXCEPTION)
    }

    /**
     * 409 Conflict
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ConflictException::class)
    fun handleConflictException(exception: ConflictException): ApiResponse<Any> {
        log.error(exception.message, exception)
        return ApiResponse.error(exception.errorCode)
    }

    /**
     * 415 Unsupported Media Type
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeException::class)
    fun handleHttpMediaTypeException(exception: HttpMediaTypeException): ApiResponse<Any> {
        return ApiResponse.error(ErrorCode.UNSUPPORTED_MEDIA_TYPE)
    }

    /**
     * 500 Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun handleInternalServerException(exception: Exception): ApiResponse<Any> {
        log.error(exception.message, exception)
        return ApiResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION)
    }

    /**
     * 502 Bad Gateway
     */
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ExceptionHandler(BadGatewayException::class)
    fun handleBadGatewayException(exception: BadGatewayException): ApiResponse<Any> {
        log.error(exception.message, exception)
        return ApiResponse.error(exception.errorCode)
    }
}
