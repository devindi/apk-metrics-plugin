package com.devindi.gradle.apm

import com.android.build.gradle.AppExtension
import com.android.build.gradle.api.BaseVariant

import com.devindi.gradle.apm.size.ApkSizeTask
import com.devindi.gradle.internal.AppInfoResolver
import com.devindi.gradle.internal.MetricsReporter
import groovy.json.JsonSlurper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.gradle.api.DomainObjectSet
import org.gradle.api.Plugin
import org.gradle.api.Project

class ApkMetricsPlugin implements Plugin<Project> {

	void apply(Project project) {
		project.logger.lifecycle("Hello world, apk metrics.!")
		if (project.plugins.hasPlugin("com.android.application")) {
			applyToProject(project, project.extensions.findByType(AppExtension).applicationVariants)
		} else {
			project.logger.lifecycle("Project is not app")
		}
	}

	@SuppressWarnings("GrMethodMayBeStatic")
	private def applyToProject(Project project, DomainObjectSet<BaseVariant> variants) {
		HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
			@Override
			void log(String message) {
				project.logger.debug(message)
			}
		})
		logging.setLevel(HttpLoggingInterceptor.Level.BODY)
		OkHttpClient client = new OkHttpClient.Builder()
				.addInterceptor(logging)
				.build()

		variants.all { BaseVariant variant ->
			variant.outputs.each { output ->
				def key = variant.name.capitalize()

				if (variant.outputs.size() > 1) {
					key += output.name
				}

				ApkSizeTask task = project.tasks.create("size${key}Apk", ApkSizeTask) as ApkSizeTask

				task.description = "Collect apk size"
				task.group = "devindi"
				task.variant = variant
				task.output = output
				task.appInfoResolver = new AppInfoResolver()
				task.reporter = new MetricsReporter(client, new JsonSlurper(), "http://localhost:9000/api/v0/measurement/add", project.logger)

				project.logger.lifecycle("Create task $task.name")

				project.logger.lifecycle("flavors $variant.name - ${variant.productFlavors[0].name}")
			}
		}
	}
}