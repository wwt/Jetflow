package com.wwt.jetflow.home.login

data class LoginStatus(val status: Status, val message: String?) {
    companion object {
        fun success(): LoginStatus {
            return LoginStatus(Status.SUCCESS, null)
        }

        fun emailError(msg: String): LoginStatus {
            return LoginStatus(Status.EMAIL_ERROR, msg)
        }

        fun passwordError(msg: String): LoginStatus {
            return LoginStatus(Status.PASSWORD_ERROR, msg)
        }
    }
}

enum class Status {
    SUCCESS,
    EMAIL_ERROR,
    PASSWORD_ERROR;
}