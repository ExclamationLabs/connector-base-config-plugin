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

import freemarker.template.Configuration
import freemarker.template.Template
import freemarker.template.TemplateExceptionHandler

class ConfigurationWriter {

    private File outputLocation
    private ConfigurationProcessor processor
    private Configuration freemarkerConfiguration

    ConfigurationWriter(File output, ConfigurationProcessor inputProcessor) {
        outputLocation = output
        processor = inputProcessor
        setupFreemarker()
    }

    void execute() {
        def fullOutputLocation = new File(outputLocation.getAbsolutePath() +
                "/" + processor.getOutputPackage().replace('.', '/'))
        fullOutputLocation.mkdirs()

        Map<String, Object> freeMarkerDataMap =
            [
                'packageName': processor.getOutputPackage(),
                'className': processor.getOutputClassName(),
                'hasGuardedString': processor.hasGuardedStringItems(),
                'items': processor.getConfigurationItems(),
                'groups': processor.getConfigurationGroups(),
                'allInterfaces': processor.getAllConfigurationInterfaces(),
                'internalItems': processor.getAllInternalItems()
            ]

        // Get the template to generate Java source files
        Template template = freemarkerConfiguration.getTemplate("configurationClass.ftl")

        File outputFile = new File(fullOutputLocation, processor.getOutputClassName() + ".java")
        Writer fileWriter = new FileWriter(outputFile)

        println 'Output file location: ' + outputFile.getAbsolutePath()

        // Generate the Java source file
        template.process(freeMarkerDataMap, fileWriter)

        println 'Successfully wrote configuration Java to file ' + outputFile.getAbsolutePath()
    }

    private setupFreemarker() {
        freemarkerConfiguration = new Configuration(Configuration.VERSION_2_3_28)

        // Set the root of the class path ("") as the location to find templates
        freemarkerConfiguration.setClassLoaderForTemplateLoading(getClass().getClassLoader(), "")

        freemarkerConfiguration.setDefaultEncoding("UTF-8")
        freemarkerConfiguration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER)
        freemarkerConfiguration.setLogTemplateExceptions(false)
        freemarkerConfiguration.setWrapUncheckedExceptions(true)
    }
}
