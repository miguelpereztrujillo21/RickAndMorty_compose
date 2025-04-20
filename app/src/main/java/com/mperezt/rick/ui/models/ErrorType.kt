package com.mperezt.rick.ui.models

enum class NetworkErrorPattern(val pattern: String) {
    NETWORK("network"),
    INTERNET("internet"),
    CONEXION("conexión"),
    CONECTAR("conectar"),
    TIMEOUT("timeout"),
    TIMED_OUT("timed out")
}

enum class ServerErrorPattern(val pattern: String) {
    SERVER("server"),
    SERVIDOR("servidor"),
    ERROR_500("500"),
    INTERNAL_ERROR("internal error")
}

enum class NotFoundErrorPattern(val pattern: String) {
    NOT_FOUND("not found"),
    NO_ENCONTRADO("no encontrado"),
    ERROR_404("404")
}

sealed class ErrorType {
    object Network : ErrorType()
    object Server : ErrorType()
    object NotFound : ErrorType()
    data class Generic(val message: String) : ErrorType()

    companion object {
        fun fromErrorMessage(errorMessage: String?): ErrorType {
            if (errorMessage == null) return Generic("")

            val lowerMessage = errorMessage.lowercase()

            for (pattern in NetworkErrorPattern.values()) {
                if (lowerMessage.contains(pattern.pattern)) {
                    return Network
                }
            }

            for (pattern in ServerErrorPattern.values()) {
                if (lowerMessage.contains(pattern.pattern)) {
                    return Server
                }
            }

            for (pattern in NotFoundErrorPattern.values()) {
                if (lowerMessage.contains(pattern.pattern)) {
                    return NotFound
                }
            }

            return Generic(errorMessage)
        }
    }
}
