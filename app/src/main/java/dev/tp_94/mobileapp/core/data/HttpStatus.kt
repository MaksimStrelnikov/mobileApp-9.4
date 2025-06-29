package dev.tp_94.mobileapp.core.data

enum class HttpStatus(val status: Int) {
    OK(200),
    CREATED(201),
    UPDATED(204),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    CONFLICT(409),
    SERVER_ERROR(500);
}