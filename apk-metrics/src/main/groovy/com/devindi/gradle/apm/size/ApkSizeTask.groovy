package com.devindi.gradle.apm.size

import com.android.build.gradle.api.BaseVariant
import com.android.build.gradle.api.BaseVariantOutput
import com.devindi.gradle.internal.ApkField
import com.devindi.gradle.internal.AppInfoResolver
import com.devindi.gradle.internal.MetricsReporter
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class ApkSizeTask extends DefaultTask {

    BaseVariant variant
    BaseVariantOutput output

    MetricsReporter reporter
    AppInfoResolver appInfoResolver

    @SuppressWarnings("unused")
    @TaskAction
    def collectApkSize() {
        def file = output.outputFile

        def map = appInfoResolver.readFile(file)
        String version = "${map[ApkField.VERSION_NAME]}(${map[ApkField.VERSION_CODE]})"
        reporter.sendApkSize(map[ApkField.PACKAGE_ID], version, file.length())
    }
}
