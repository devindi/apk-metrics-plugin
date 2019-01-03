package com.devindi.gradle.internal

class DexInfo {

    final int classCount
    final int methodCount
    final int fieldCount

    DexInfo(int classCount, int methodCount, int fieldCount) {
        this.classCount = classCount
        this.methodCount = methodCount
        this.fieldCount = fieldCount
    }


    @Override
    String toString() {
        return "DexInfo{" +
                "classCount=" + classCount +
                ", methodCount=" + methodCount +
                ", fieldCount=" + fieldCount +
                '}'
    }
}
