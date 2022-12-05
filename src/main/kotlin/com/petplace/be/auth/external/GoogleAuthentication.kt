package com.petplace.be.auth.external

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.HttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.gson.GsonFactory

class GoogleAuthentication {
    companion object{
        fun verifierIdToken(idToken: String): GoogleIdToken? {
            val transport: HttpTransport = NetHttpTransport()
            val jsonFactory: JsonFactory = GsonFactory.getDefaultInstance()
            val verifier =
                GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                    //TODO property 로 빼기
                    .setAudience(listOf("663371736991-3gjbva9d64mplielt3m14ji527q4ihad.apps.googleusercontent.com"))
                    .build()
            return try {
                verifier.verify(idToken)
            } catch (e: Exception) {
                null
            }
        }
    }
}
