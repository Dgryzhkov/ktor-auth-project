package com.dgryzhkov.data

import com.dgryzhkov.data.models.User

interface UserDataSource {

    suspend fun getUserByUserName(userName: String): User?

    suspend fun insertUser(user: User): Boolean
}