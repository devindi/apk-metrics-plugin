package com.devindi.gradle.internal

import com.android.dexdeps.FieldRef
import com.android.dexdeps.MethodRef
import com.getkeepsafe.dexcount.Deobfuscator
import com.getkeepsafe.dexcount.DexFile
import com.getkeepsafe.dexcount.PackageTree
import org.jetbrains.annotations.NotNull

class DexCalculator {

    void doCalculation(File file) {

        PackageTree tree

        def dataList = DexFile.extractDexData(file, 0)

        try {
            tree = new PackageTree(new Deobfuscator(null) {
                @Override
                String deobfuscate(@NotNull String name) {
                    return name
                }
            })

            dataList.each { DexFile dexFile ->
                dexFile.methodRefs.each { MethodRef methodRef ->
                    tree.addMethodRef(methodRef)
                }
                dexFile.fieldRefs.each { FieldRef fieldRef ->
                    tree.addFieldRef(fieldRef)
                }
            }
        } finally {
            dataList.each { DexFile dexFile ->
                dexFile.close()
            }
        }

        println("Method count: $tree.methodCount")
        println("Field count: $tree.fieldCount")
        println("Class count: $tree.classCount")

    }
}
