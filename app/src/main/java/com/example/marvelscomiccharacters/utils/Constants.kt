package com.example.marvelscomiccharacters.utils

import com.example.marvelscomiccharacters.BuildConfig
import java.math.BigInteger
import java.security.MessageDigest
import java.sql.Timestamp

/**
 * Constants used across the files
 */
class Constants {
    companion object {
        const val BASE_URL = "https://gateway.marvel.com/"
        val timeStamp = Timestamp(System.currentTimeMillis()).time.toString()

        const val API_KEY = BuildConfig.API_KEY

        private const val PRIVATE_KEY = BuildConfig.PRIVATE_KEY

        fun hash(): String {
            val input = "$timeStamp$PRIVATE_KEY$API_KEY"
            val md = MessageDigest.getInstance("MD5")
            return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
        }
    }
}
