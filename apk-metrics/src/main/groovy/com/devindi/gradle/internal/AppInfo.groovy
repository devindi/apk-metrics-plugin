package com.devindi.gradle.internal

class AppInfo {

    final String packageId
    final String versionName
    final Long versionCode

    AppInfo(String packageId, String versionName, Long versionCode) {
        this.packageId = packageId
        this.versionName = versionName
        this.versionCode = versionCode
    }

    @Override
    String toString() {
        return "AppInfo{" +
                "packageId='" + packageId + '\'' +
                ", versionName='" + versionName + '\'' +
                ", versionCode='" + versionCode + '\'' +
                '}'
    }
}
