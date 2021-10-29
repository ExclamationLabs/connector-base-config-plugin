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

class Proxy extends ConfigurationGroup {
    @Override
    List<String> getYamlPath() {
        return ['security', 'proxy']
    }

    @Override
    Set<ConfigurationItem> getConfigurationItems() {
        [new ConfigurationItem.Builder()
                 .name('host', getYamlPath())
                 .displayText('Proxy Host')
                 .helpText('Domain or IP Address of Proxy Server')
                 .required(true)
                 .type(ConfigurationItemType.STRING).build(),
         new ConfigurationItem.Builder()
                 .name('port', getYamlPath())
                 .displayText('Proxy Port')
                 .helpText('Port Number of Proxy Server')
                 .required(true)
                 .type(ConfigurationItemType.INT).build(),
         new ConfigurationItem.Builder()
                 .name('type', getYamlPath())
                 .displayText('Proxy Type')
                 .helpText('Type of Proxy Server - either `socks5` or `http`')
                 .required(true)
                 .validations(['@Pattern(regexp = "socks5|http", flags = Pattern.Flag.CASE_INSENSITIVE)'])
                 .type(ConfigurationItemType.STRING).build()

        ] as Set<ConfigurationItem>
    }

    @Override
    String getConfigurationInterface() {
        getBaseTypesPackage() + '.security.ProxyConfiguration'
    }
}
