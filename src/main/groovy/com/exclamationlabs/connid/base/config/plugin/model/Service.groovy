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
package com.exclamationlabs.connid.base.config.plugin.model

class Service extends ConfigurationGroup {
    @Override
    List<String> getYamlPath() {
        ['service']
    }

    @Override
    Set<ConfigurationItem> getConfigurationItems() {
        [
            new ConfigurationItem.Builder()
                    .name('serviceUrl', getYamlPath())
                    .required(true)
                    .displayText('Service URL')
                    .helpText('The base service URL for the API this connector' +
                    ' needs to access to get or store data.')
                    .type(ConfigurationItemType.STRING).build()
        ] as Set<ConfigurationItem>
    }

    @Override
    String getConfigurationInterface() {
        getBaseTypesPackage() + '.ServiceConfiguration'
    }
}
