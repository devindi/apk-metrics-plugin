package com.devindi.gradle.apm.dex

import com.android.build.gradle.api.BaseVariantOutput
import com.devindi.gradle.internal.DexCalculator
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class DexCountTask extends DefaultTask {

    BaseVariantOutput output

    @SuppressWarnings("unused")
    @TaskAction
    void reportDexCount() {
        def file = output.outputFile

        DexCalculator calculator = new DexCalculator()
        calculator.doCalculation(file)

    }
}
