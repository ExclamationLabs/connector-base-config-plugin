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
package com.exclamationlabs.connid.base.config.plugin.model.security.authenticator

import com.exclamationlabs.connid.base.config.plugin.model.ConfigurationGroup
import com.exclamationlabs.connid.base.config.plugin.model.ConfigurationItem
import com.exclamationlabs.connid.base.config.plugin.model.ConfigurationItemType

class JwtHs256 extends ConfigurationGroup {
    @Override
    List<String> getYamlPath() {
        ['security', 'authenticator', 'jwtHs256']
    }

    @Override
    Set<ConfigurationItem> getConfigurationItems() {
        [new ConfigurationItem.Builder()
                 .name('issuer', getYamlPath())
                 .order(2601)
                 .displayText('JWT Issuer')
                 .helpText('Issuer for JWT HS256 Authentication')
                 .required(true)
                 .type(ConfigurationItemType.STRING).build(),
         new ConfigurationItem.Builder()
                 .name('secret', getYamlPath())
                 .order(2602)
                 .displayText('JWT Secret')
                 .helpText('Secret for JWT HS256 Authentication')
                 .required(true)
                 .confidential(true)
                 .type(ConfigurationItemType.STRING).build(),
         new ConfigurationItem.Builder()
                 .name('expirationPeriod', getYamlPath())
                 .order(2603)
                 .displayText('JWT Expiration')
                 .helpText('Expiration period, in milliseconds' +
                         ', for JWT HS256 Authentication')
                 .required(true)
                 .type(ConfigurationItemType.LONG).build()
        ] as Set<ConfigurationItem>
    }

    @Override
    String getConfigurationInterface() {
        getBaseTypesPackage() + '.security.authenticator.JwtHs256Configuration'
    }
}
