package com.dgryzhkov.secururity.hashing

data class SaltedHash(
    val hash: String,
    val salt: String
)