package com.tori.mementocaps.presentation.exception

import org.springframework.http.HttpStatus

abstract class MementoCapsException(
    httpStatus: HttpStatus,
    message: String
): RuntimeException(message) {
    val httpStatus: HttpStatus = httpStatus
}

class MementoCapsNotFoundException(message: String): MementoCapsException(
    httpStatus = HttpStatus.NOT_FOUND,
    message = message
)

class MementoCapsBadRequestException(message: String) : MementoCapsException(
    httpStatus = HttpStatus.BAD_REQUEST,
    message = message
)

class MementoCapsForbiddenException(message: String): MementoCapsException(
    httpStatus = HttpStatus.FORBIDDEN,
    message = message
)

class MementoCapsInternalServerErrorException(message: String): MementoCapsException(
    httpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
    message = message
)