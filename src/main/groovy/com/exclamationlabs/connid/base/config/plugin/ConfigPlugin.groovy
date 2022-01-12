/*
    Copyright 2021 Exclamation Labs
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
        http://www.apache.org/licenses/LICENSE-2.0
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/
package com.exclamationlabs.connid.base.config.plugin

import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project

class ConfigPlugin implements Plugin<Project> {

    private final static OUTPUT_FOLDER = 'generatedConfig'
    private final static FILE_NAME = 'configuration.structure.yml'

    @Override
    void apply(Project project) {
        project.task('generateConfiguration') {
            doLast {
                println 'Project root directory in use is: ' + project.getRootDir()
                println 'Starting generateConfiguration task'
                File filePath = new File(project.getRootDir(), FILE_NAME)
                if (!filePath.exists()) {
                    println 'Skipping configuration processing - file ' +
                            filePath.getAbsolutePath() + ' not found'
                } else {
                    println 'Processing file ' + filePath.getAbsolutePath()
                    ConfigurationProcessor processor =
                            new ConfigurationProcessor(new FileInputStream(filePath))
                    try {
                        processor.execute()
                    } catch(IllegalArgumentException illE) {
                        throw new GradleException(illE.getMessage(), illE)
                    }
                    println 'Processing of YAML complete.  Starting writing new class'
                    ConfigurationWriter writer =
                            new ConfigurationWriter(new File(project.getRootDir(), OUTPUT_FOLDER), processor)
                    writer.execute()
                }
            }
        }
    }
}
