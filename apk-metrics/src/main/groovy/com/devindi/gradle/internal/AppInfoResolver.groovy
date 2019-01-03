package com.devindi.gradle.internal

import net.dongliu.apk.parser.ApkFile

class AppInfoResolver {

    @SuppressWarnings("GrMethodMayBeStatic")
    AppInfo readFile(File file) {
        def apkFile = null
        try {
            apkFile = new ApkFile(file)
            def meta = apkFile.apkMeta
            return new AppInfo(meta.packageName, meta.versionName, meta.versionCode)
        } finally {
            apkFile.close()
        }
    }
}
