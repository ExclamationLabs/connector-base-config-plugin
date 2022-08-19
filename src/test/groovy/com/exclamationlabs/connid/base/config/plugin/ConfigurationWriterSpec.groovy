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

import com.exclamationlabs.connid.base.config.plugin.model.ConfigurationItem
import com.exclamationlabs.connid.base.config.plugin.model.ConfigurationItemType
import spock.lang.Specification

class ConfigurationWriterSpec extends Specification {

    def 'writeSimple'() {
        given:
        def testFolder = 'testGeneratedSrc'
            ConfigurationProcessor simpleProcessor =
                    new ConfigurationProcessor()
            simpleProcessor.outputPackage = 'com.dummy.test'
            simpleProcessor.outputClassName = 'SimpleConfiguration'
            simpleProcessor.name = 'dummyTest'
            simpleProcessor.getConfigurationGroups().first().setEnabled(true)
            simpleProcessor.getConfigurationGroups().last().setEnabled(true)
            simpleProcessor.configurationItems =
                    [new ConfigurationItem.Builder()
                             .name('diablo')
                             .displayText('Diablo Custom 1')
                             .required(true)
                             .type(ConfigurationItemType.STRING).build(),
                     new ConfigurationItem.Builder()
                             .name('item1', ['test'])
                             .displayText('Display text item 1')
                             .defaultValue(5)
                             .validations(['@Min(1)', '@Max(10)'])
                             .required(true)
                             .type(ConfigurationItemType.LONG).build(),
                     new ConfigurationItem.Builder()
                             .name('item2', ['test'])
                             .required(false)
                             .confidential(true)
                             .helpText('HelpText item 2')
                             .type(ConfigurationItemType.STRING).build(),
                     new ConfigurationItem.Builder()
                             .name('item3', ['test'])
                             .required(true)
                             .defaultValue('Hello World')
                             .helpText('HelpText item 3')
                             .type(ConfigurationItemType.STRING).build(),
                     new ConfigurationItem.Builder()
                             .name('myInternal', ['myInternal'])
                             .required(false)
                             .internal(true)
                             .defaultValue('myInternal def')
                             .helpText('myInternal help')
                             .type(ConfigurationItemType.GUARDED_STRING).build(),
                     new ConfigurationItem.Builder()
                             .name('myInternal2', ['myInternal2'])
                             .required(false)
                             .internal(true)
                             .defaultValue('myInternal2 def')
                             .helpText('myInternal2 help')
                             .type(ConfigurationItemType.STRING_ARRAY).build()
                    ] as Set<ConfigurationItem>

            ConfigurationWriter writer = new ConfigurationWriter(
                    new File (testFolder), simpleProcessor)

            File expectedOutFile = new File(testFolder + '/com/dummy/test/SimpleConfiguration.java' )

        when:
            writer.execute()

        then:
            expectedOutFile.exists()

        cleanup:
            new File(testFolder).deleteDir()

    }

}
