package com.tori.mementocaps.presentation.advice

import com.tori.mementocaps.presentation.exception.MementoCapsException
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class PresentationApiControllerAdvice {
    val log = LoggerFactory.getLogger(PresentationApiControllerAdvice::class.java)

    // Custom Exception 처리
    @ExceptionHandler
    fun handleException(e: MementoCapsException): ResponseEntity<String> {
        log.info(e.message, e)

        return ResponseEntity.status(e.httpStatus).body(e.message)
    }

    // Validation에서 던지는 MethodArgumentNotValidException 처리
    @ExceptionHandler
    fun handleException(e: MethodArgumentNotValidException) : ResponseEntity<String> {
        log.info(e.message, e)

        val fieldError = e.bindingResult.fieldErrors[0]
        val message = "[${fieldError.field} ${fieldError.defaultMessage}]"

        return ResponseEntity.badRequest().body(message)
    }

    // 그 외 예상치 못한 에러 처리
    fun handleException(e: Exception) : ResponseEntity<String> {
        log.info(e.message, e)

        return ResponseEntity.internalServerError().body("서버 오류 발생")
    }
}