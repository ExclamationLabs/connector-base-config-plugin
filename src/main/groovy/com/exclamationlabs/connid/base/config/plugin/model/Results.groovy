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

class Results extends ConfigurationGroup {
    @Override
    List<String> getYamlPath() {
        ['results']
    }

    @Override
    Set<ConfigurationItem> getConfigurationItems() {
        [
            new ConfigurationItem.Builder()
                    .name('deepGet', getYamlPath())
                    .displayText('Deep Get Enabled')
                    .helpText('If true, an individual getOne request for each item in' +
                            ' any getAll request will be performed.')
                    .required(false)
                    .type(ConfigurationItemType.BOOLEAN).build(),
            new ConfigurationItem.Builder()
                    .name('deepImport', getYamlPath())
                    .displayText('Deep Import Enabled')
                    .helpText('If true, an individual getOne request for each item in' +
                            ' Import getAll requests will be performed.')
                    .required(false)
                    .type(ConfigurationItemType.BOOLEAN).build(),
            new ConfigurationItem.Builder()
                    .name('importBatchSize', getYamlPath())
                    .displayText('Import Batch Size')
                    .helpText('If supplied, import operations will be invoked using ' +
                            'this given batch size, so that API`s that support paging ' +
                            'can import all records using a particular batch size (instead ' +
                            ' of all at once.')
                    .required(false)
                    .type(ConfigurationItemType.INT).build(),
            new ConfigurationItem.Builder()
                    .name('pagination', getYamlPath())
                    .displayText('Pagination Enabled')
                    .helpText('Set to true if this connector (and its underlying API' +
                            ') supports pagination.')
                    .required(false)
                    .type(ConfigurationItemType.BOOLEAN).build()
        ] as Set<ConfigurationItem>
    }

    @Override
    String getConfigurationInterface() {
        getBaseTypesPackage() + '.ResultsConfiguration'
    }
}
