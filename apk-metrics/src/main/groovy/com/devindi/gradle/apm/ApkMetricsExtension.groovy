package com.devindi.gradle.apm

class ApkMetricsExtension {
    String apiToken
    String serverUrl = "http://localhost:9000"

    @Override
    String toString() {
        return "ApkMetricsExtension{" +
                "apiToken='" + apiToken + '\'' +
                ", serverUrl='" + serverUrl + '\'' +
                '}';
    }
}
