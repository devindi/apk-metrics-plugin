package com.devindi.gradle.internal

import com.android.dexdeps.FieldRef
import com.android.dexdeps.MethodRef
import com.getkeepsafe.dexcount.Deobfuscator
import com.getkeepsafe.dexcount.DexFile
import com.getkeepsafe.dexcount.PackageTree

class DexCalculator {

    @SuppressWarnings("GrMethodMayBeStatic")
    DexInfo doCalculation(File file) {

        PackageTree tree = null

        def dataList = DexFile.extractDexData(file, 0)

        try {
            tree = new PackageTree(new Deobfuscator(null) {
                @Override
                String deobfuscate(String name) {
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

        return new DexInfo(tree.classCount, tree.methodCount, tree.fieldCount)
    }
}
