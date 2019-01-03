package com.devindi.gradle.apm

import com.android.build.gradle.AppExtension
import com.android.build.gradle.api.BaseVariant
import com.devindi.gradle.apm.dex.DexCountTask
import com.devindi.gradle.apm.size.ApkSizeTask
import com.devindi.gradle.internal.AppInfoResolver
import com.devindi.gradle.internal.MetricsReporter
import com.devindi.gradle.internal.OkHttpFactory
import groovy.json.JsonSlurper
import okhttp3.OkHttpClient
import org.gradle.api.DomainObjectSet
import org.gradle.api.Plugin
import org.gradle.api.Project

@SuppressWarnings("GrMethodMayBeStatic")
class ApkMetricsPlugin implements Plugin<Project> {

	private final OkHttpFactory httpClientFactory = new OkHttpFactory()
	private OkHttpClient httpClient
	private MetricsReporter metricsReporter
	private AppInfoResolver appInfoResolver = new AppInfoResolver()

	void apply(Project project) {
		def extension = project.extensions.create("metrics", ApkMetricsExtension)
		project.logger.lifecycle("$extension")
		httpClient = httpClientFactory.createHttpClient(project.logger)
		metricsReporter = new MetricsReporter(httpClient, new JsonSlurper(), extension, project.logger)
		if (project.plugins.hasPlugin("com.android.application")) {
			def variants = project.extensions.findByType(AppExtension).applicationVariants

			initArtifactSize(project, variants)
			initDexCount(project, variants)
		} else {
			project.logger.lifecycle("Project is not app")
		}
	}

	private def initArtifactSize(Project project, DomainObjectSet<BaseVariant> variants) {
		variants.all { BaseVariant variant ->
			variant.outputs.each { output ->
				def key = variant.name.capitalize()

				if (variant.outputs.size() > 1) {
					key += output.name
				}

				ApkSizeTask task = project.tasks.create("size$key", ApkSizeTask) as ApkSizeTask

				task.description = "Collect apk size"
				task.group = "devindi"
				task.variant = variant
				task.output = output
				task.appInfoResolver = appInfoResolver
				task.reporter = metricsReporter
			}
		}
	}

	private void initDexCount(Project project, DomainObjectSet<BaseVariant> variants) {
		variants.all { BaseVariant variant ->
			variant.outputs.each { output ->
				def key = variant.name.capitalize()

				if (variant.outputs.size() > 1) {
					key += output.name
				}

				DexCountTask task = project.tasks.create("dexCount$key", DexCountTask) as DexCountTask
				task.description = "Collect dex count"
				task.group = "devindi"
				task.output = output
				task.appInfoResolver = appInfoResolver
				task.reporter = metricsReporter
			}
		}
	}
}