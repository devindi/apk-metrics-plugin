package com.devindi.gradle.internal

import groovy.json.JsonSlurper
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import org.gradle.api.logging.Logger

class MetricsReporter {

    private final OkHttpClient okHttpClient
    private final JsonSlurper jsonSlurper
    private final HttpUrl baseUrl
    private final Logger logger

    MetricsReporter(OkHttpClient okHttpClient, JsonSlurper jsonSlurper, String baseUrl, Logger logger) {
        this.logger = logger
        this.okHttpClient = okHttpClient
        this.jsonSlurper = jsonSlurper
        this.baseUrl = HttpUrl.parse(baseUrl)
    }

    void sendApkSize(String packageId, String version, long size) {
        logger.lifecycle("Sending size: $packageId $version $size")
        def url = baseUrl.newBuilder()
                .addQueryParameter("package", packageId)
                .addQueryParameter("key", "apk_size")
                .addQueryParameter("value", size.toString())
                .addQueryParameter("version", version)
                .build()

        def request = new Request.Builder()
                .url(url)
                .build()

        def response = okHttpClient.newCall(request).execute()
        String responseBody = response.body().string()


    }
}
