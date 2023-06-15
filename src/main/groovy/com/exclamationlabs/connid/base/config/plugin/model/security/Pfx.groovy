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
package com.exclamationlabs.connid.base.config.plugin.model.security

import com.exclamationlabs.connid.base.config.plugin.model.ConfigurationGroup
import com.exclamationlabs.connid.base.config.plugin.model.ConfigurationItem
import com.exclamationlabs.connid.base.config.plugin.model.ConfigurationItemType

class Pfx extends ConfigurationGroup {
    @Override
    List<String> getYamlPath() {
        return ['security', 'pfx']
    }

    @Override
    Set<ConfigurationItem> getConfigurationItems() {
        [new ConfigurationItem.Builder()
                 .name('pfxFile', getYamlPath())
                 .order(2301)
                 .displayText('PFX File')
                 .helpText('Full file path to .pfx file')
                 .required(true)
                 .type(ConfigurationItemType.STRING).build(),
         new ConfigurationItem.Builder()
                 .name('pfxPassword', getYamlPath())
                 .order(2302)
                 .displayText('PFX Key Store Password')
                 .helpText('Password value protecting the key store relating to the .pfx file')
                 .required(true)
                 .confidential(true)
                 .type(ConfigurationItemType.GUARDED_STRING).build(),
         new ConfigurationItem.Builder()
                 .name('keyPassword', getYamlPath())
                 .order(2303)
                 .displayText('PFX Key Password')
                 .helpText('Password for the client key relating to the .pfx file.  If not given the `pfxPassword` value will be reused.')
                 .required(false)
                 .confidential(true)
                 .type(ConfigurationItemType.GUARDED_STRING).build()
        ] as Set<ConfigurationItem>
    }

    @Override
    String getConfigurationInterface() {
        getBaseTypesPackage() + '.security.PfxConfiguration'
    }
}
