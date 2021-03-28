package com.enzoroiz.coroutines

import kotlinx.coroutines.delay

class UserRepository {
    suspend fun getUsers(): List<User> {
        delay(4000)
        return listOf(
            User(1L, "test@test.com"),
            User(2L, "enzor@test.com"),
            User(3L, "eroiz@test.com"),
            User(4L, "roiz@test.com"),
            User(5L, "enzo@test.com")
        )
    }
}