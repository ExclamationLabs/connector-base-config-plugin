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

class HttpBasicAuth extends ConfigurationGroup {
    @Override
    List<String> getYamlPath() {
        ['security', 'httpBasicAuth']
    }

    @Override
    Set<ConfigurationItem> getConfigurationItems() {
        [ new ConfigurationItem.Builder()
            .name('basicUsername', getYamlPath())
            .order(2001)
            .displayText('HTTP Basic Auth Username')
            .helpText('HTTP Basic Authentication Username value')
            .required(true)
            .validations(['@NotBlank'])
            .type(ConfigurationItemType.STRING).build(),
          new ConfigurationItem.Builder()
              .name('basicPassword', getYamlPath())
              .order(2002)
              .required(true)
              .displayText('HTTP Basic Auth Password')
              .helpText('HTTP Basic Authentication Password value')
              .validations(['@NotBlank'])
              .confidential(true)
              .type(ConfigurationItemType.GUARDED_STRING).build()
        ] as Set<ConfigurationItem>
    }

    @Override
    String getConfigurationInterface() {
        getBaseTypesPackage() + '.security.HttpBasicAuthConfiguration'
    }
}
