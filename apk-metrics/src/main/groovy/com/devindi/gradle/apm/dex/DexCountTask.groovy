package com.devindi.gradle.apm.dex

import com.android.build.gradle.api.BaseVariantOutput
import com.devindi.gradle.internal.ApkField
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

        def map = appInfoResolver.readFile(file)
        String version = "${map[ApkField.VERSION_NAME]}(${map[ApkField.VERSION_CODE]})"

        DexCalculator calculator = new DexCalculator()
        def dexInfo = calculator.doCalculation(file)

        reporter.sendArtifactDexCount(map[ApkField.PACKAGE_ID].toString(), version, dexInfo.classCount, dexInfo.methodCount, dexInfo.fieldCount)

    }
}
