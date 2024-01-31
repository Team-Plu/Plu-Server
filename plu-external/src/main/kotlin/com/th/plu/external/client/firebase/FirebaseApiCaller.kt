package com.th.plu.external.client.firebase

interface FirebaseApiCaller {

    fun requestFcmMessaging(accessToken: String, message: String)
}
