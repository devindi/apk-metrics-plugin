package com.devindi.gradle.internal

import net.dongliu.apk.parser.ApkFile

class AppInfoResolver {

    @SuppressWarnings("GrMethodMayBeStatic")
    def readFile(File file, Set<ApkField> keys = ApkField.values()) {
        def apkFile = null
        Map<ApkField, String> values = new HashMap<>()
        try {
            apkFile = new ApkFile(file)
            def meta = apkFile.apkMeta
            keys.each { key ->
                switch (key) {
                    case ApkField.PACKAGE_ID:
                        values.put(key, meta.packageName)
                        break
                    case ApkField.TITLE:
                        values.put(key, meta.label)
                        break
                    case ApkField.VERSION_NAME:
                        values.put(key, meta.versionName)
                        break
                    case ApkField.VERSION_CODE:
                        values.put(key, meta.versionCode.toString())
                        break
                }
            }
        } finally {
            apkFile.close()
        }
        return values
    }
}
