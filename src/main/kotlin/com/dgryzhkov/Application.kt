package com.dgryzhkov

import com.dgryzhkov.data.MongoUserDataSource
import com.dgryzhkov.plugins.configureMonitoring
import com.dgryzhkov.plugins.configureRouting
import com.dgryzhkov.plugins.configureSecurity
import com.dgryzhkov.plugins.configureSerialization
import com.dgryzhkov.secururity.hashing.SHA256HashingService
import com.dgryzhkov.secururity.token.JwtTokenService
import com.dgryzhkov.secururity.token.TokenConfig
import io.ktor.server.application.*
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {

    val mongoPassword = System.getenv("MONGO_PASSWORD")
    val dbName ="ktor-notes"
    val db = KMongo.createClient(
        connectionString = "mongodb+srv://Dgryzhkov:<password>@cluster0.4skcucv.mongodb.net/?retryWrites=true&w=majority"
    )
        .coroutine
        .getDatabase(dbName)

    val userDataSource = MongoUserDataSource(db)

    val tokenService = JwtTokenService()
    val tokenConfig = TokenConfig(
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        exiresIn = 365L*1000*60L*24L,
        secret = System.getenv("JWT_SECRET")
    )

    val hashingService = SHA256HashingService()
    configureMonitoring()
    configureSerialization()
    configureSecurity()
    configureRouting()
}
