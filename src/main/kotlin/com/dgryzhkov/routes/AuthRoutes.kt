package com.dgryzhkov.routes

import com.dgryzhkov.data.UserDataSource
import com.dgryzhkov.data.models.User
import com.dgryzhkov.data.models.requests.AuthRequest
import com.dgryzhkov.secururity.hashing.HashingService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.signUp(
    hashingService: HashingService,
    userDataSource: UserDataSource
){
    post("signup") {
        val request = call.receiveOrNull<AuthRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        val areFieldsBlank = request.username.isBlank() || request.password.isBlank()
        val isPasswordShorts = request.password.length<8
        if (userDataSource.getUserByUserName(request.username)!=null){
            call.respond(HttpStatusCode.Conflict, "Username is already exist")
            return@post
        }

        if (areFieldsBlank || isPasswordShorts){
            call.respond(HttpStatusCode.Conflict)
            return@post
        }

        val saltedHash = hashingService.generateSaltedHash(request.password)
        val user = User(
            userName = request.username,
            password = saltedHash.hash,
            salt = saltedHash.salt
        )
        val wasAcknowledged = userDataSource.insertUser(user)
        if (!wasAcknowledged){
            call.respond(HttpStatusCode.Conflict)
            return@post
        } else{
            call.respond(HttpStatusCode.OK)
        }
    }
}