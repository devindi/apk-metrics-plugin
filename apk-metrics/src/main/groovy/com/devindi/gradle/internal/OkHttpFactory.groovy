package com.devindi.gradle.internal

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.gradle.api.logging.Logger

class OkHttpFactory {

    @SuppressWarnings("GrMethodMayBeStatic")
    OkHttpClient createHttpClient(Logger logger) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            void log(String message) {
                logger.lifecycle(message)
            }
        })
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
    }
}
