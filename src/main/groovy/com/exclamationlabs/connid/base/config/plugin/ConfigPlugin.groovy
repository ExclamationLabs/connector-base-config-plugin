package com.exclamationlabs.connid.base.config.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class ConfigPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.task('generateConfiguration') {
            doLast {
                println 'Config Plugin logic coming soon'
            }
        }
    }
}
