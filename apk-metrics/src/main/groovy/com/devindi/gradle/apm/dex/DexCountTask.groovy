package com.devindi.gradle.apm.dex

import com.android.build.gradle.api.BaseVariantOutput

import com.devindi.gradle.internal.AppInfoResolver
import com.devindi.gradle.internal.DexCalculator
import com.devindi.gradle.internal.MetricsReporter
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class DexCountTask extends DefaultTask {

    BaseVariantOutput output
    MetricsReporter reporter
    AppInfoResolver appInfoResolver

    @SuppressWarnings("unused")
    @TaskAction
    void reportDexCount() {
        def file = output.outputFile

        def appInfo = appInfoResolver.readFile(file)
        String version = "${appInfo.versionName}(${appInfo.versionCode})"

        DexCalculator calculator = new DexCalculator()
        def dexInfo = calculator.doCalculation(file)

        reporter.sendArtifactDexCount(appInfo.packageId, version, dexInfo.classCount, dexInfo.methodCount, dexInfo.fieldCount)
    }
}
