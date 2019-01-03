package com.devindi.gradle.internal

class Measurement {

    final Date measuredAt
    final String key
    final String value

    Measurement(Date measuredAt, String key, String value) {
        this.measuredAt = measuredAt
        this.key = key
        this.value = value
    }

    @Override
    String toString() {
        return "Measurement{" +
                "measuredAt=" + measuredAt +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}'
    }
}
