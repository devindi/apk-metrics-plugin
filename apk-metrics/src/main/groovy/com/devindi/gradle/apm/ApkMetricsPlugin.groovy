package com.devindi.gradle.apm

import org.gradle.api.Plugin
import org.gradle.api.Project

class ApkMetricsPlugin implements Plugin<Project> {

	void apply(Project project) {
		project.logger.lifecycle("Hello world, apk metrics.")
	}
}