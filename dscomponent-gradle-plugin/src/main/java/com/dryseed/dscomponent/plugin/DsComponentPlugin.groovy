package com.dryseed.dscomponent.plugin

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class DsComponentPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println("============> DsComponentPlugin")

        AppExtension android = project.extensions.getByType(AppExtension)
        DsComponentTransform transform = new DsComponentTransform()
        android.registerTransform(transform)
    }
}