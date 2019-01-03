package com.devindi.gradle.internal

import com.devindi.gradle.apm.ApkMetricsExtension
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import okhttp3.*
import org.gradle.api.logging.Logger

class MetricsReporter {

    private final OkHttpClient okHttpClient
    private final JsonSlurper jsonSlurper
    private final Logger logger
    private final ApkMetricsExtension extension

    MetricsReporter(OkHttpClient okHttpClient, JsonSlurper jsonSlurper, ApkMetricsExtension extension, Logger logger) {
        this.extension = extension
        this.logger = logger
        this.okHttpClient = okHttpClient
        this.jsonSlurper = jsonSlurper
    }

    void sendApkSize(String packageId, String version, long size) {
        Date now = new Date()
        sendMeasurements(packageId, version, [new Measurement(now, "apk_size", size.toString())])
    }

    void sendArtifactDexCount(String packageId, String version, int classCount, int methodCount, int fieldCount) {
        Date now = new Date()
        sendMeasurements(packageId, version, [new Measurement(now, "apk_class_count", classCount.toString()),
                                              new Measurement(now, "apk_method_cnt", methodCount.toString()),
                                              new Measurement(now, "apk_field_count", fieldCount.toString())])
    }

    private void sendMeasurements(String packageId, String version, List<Measurement> measurements) {
        logger.lifecycle("Sending measurements for $packageId ver $version. $measurements")

        def requestObj = new SendMeasurementsRequest(packageId, version, measurements)

        logger.lifecycle("request obj $requestObj", requestObj)

        def requestBodyJson = new JsonBuilder(requestObj).toPrettyString()

        logger.lifecycle("request obj $requestBodyJson", requestBodyJson)

        MediaType json = MediaType.parse("application/json")
        RequestBody requestBody = RequestBody.create(json, requestBodyJson)

        def request = new Request.Builder().url("$extension.serverUrl/api/v0/measurement/add").post(requestBody).header("X-Api-Token", extension.apiToken).build()
        def response = okHttpClient.newCall(request).execute()

        def responseCode = response.code()
        def responseBody = response.body().string()
        if (responseCode != 200) {
            throw new Exception("Failed to send measurement. Code = $responseCode. Body = $responseBody")
        }
    }

    static class SendMeasurementsRequest {

        final String packageId
        final String version
        final List<Measurement> measurements

        SendMeasurementsRequest(String packageId, String version, List<Measurement> measurements) {
            this.packageId = packageId
            this.version = version
            this.measurements = measurements
        }


        @Override
        String toString() {
            return "SendMeasurementsRequest{" +
                    "packageId='" + packageId + '\'' +
                    ", version='" + version + '\'' +
                    ", measurements=" + measurements +
                    '}'
        }
    }
}
