package com.dgryzhkov

import com.dgryzhkov.data.MongoUserDataSource
import com.dgryzhkov.plugins.configureMonitoring
import com.dgryzhkov.plugins.configureRouting
import com.dgryzhkov.plugins.configureSecurity
import com.dgryzhkov.plugins.configureSerialization
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
    configureMonitoring()
    configureSerialization()
    configureSecurity()
    configureRouting()
}
