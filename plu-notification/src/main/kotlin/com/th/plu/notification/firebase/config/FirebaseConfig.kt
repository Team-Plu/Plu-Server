package com.th.plu.notification.firebase.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import java.io.InputStream

@Configuration
class FirebaseConfig {

    @Value("\${spring.cloud.firebase.config.path}")
    var firebaseConfigPath: String? = null

    @Bean
    fun firebaseMessaging(): FirebaseMessaging {
        val resource = ClassPathResource(firebaseConfigPath.toString())
        val refreshToken = resource.inputStream

        val firebaseApp = makeApp(FirebaseApp.getApps(), refreshToken)
        return FirebaseMessaging.getInstance(firebaseApp)
    }

    private fun makeApp(firebaseApps: List<FirebaseApp>, refreshToken: InputStream): FirebaseApp {
        if (firebaseApps.isNotEmpty()) {
            for (app in firebaseApps) {
                if (app.name.equals(FirebaseApp.DEFAULT_APP_NAME))
                    return app
            }
        }
        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(refreshToken))
            .build()
        return FirebaseApp.initializeApp(options)
    }
}
