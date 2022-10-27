package com.dgryzhkov.plugins

import com.dgryzhkov.data.UserDataSource
import com.dgryzhkov.secururity.hashing.HashingService
import com.dgryzhkov.secururity.token.JwtTokenService
import com.dgryzhkov.secururity.token.TokenConfig
import io.ktor.server.application.*
import io.ktor.server.routing.*


fun Application.configureRouting(
    userDataSource: UserDataSource,
    hashingService: HashingService,
    tokenService: JwtTokenService,
    tokenConfig: TokenConfig
) {
    routing {

    }

}
