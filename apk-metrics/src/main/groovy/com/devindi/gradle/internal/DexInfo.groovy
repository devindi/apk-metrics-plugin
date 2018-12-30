package com.devindi.gradle.internal

class DexInfo {

    private final int classCount;
    private final int methodCount;
    private final int fieldCount;

    DexInfo(int classCount, int methodCount, int fieldCount) {
        this.classCount = classCount
        this.methodCount = methodCount
        this.fieldCount = fieldCount
    }

    int getClassCount() {
        return classCount
    }

    int getMethodCount() {
        return methodCount
    }

    int getFieldCount() {
        return fieldCount
    }
}
