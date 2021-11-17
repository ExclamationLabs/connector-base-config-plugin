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

class JwtRs256 extends ConfigurationGroup {
    @Override
    List<String> getYamlPath() {
        ['security', 'authenticator', 'jwtRs256']
    }

    @Override
    Set<ConfigurationItem> getConfigurationItems() {
        [new ConfigurationItem.Builder()
                 .name('issuer', getYamlPath())
                 .displayText('JWT Issuer')
                 .helpText('Issuer for JWT RS256 Authentication')
                 .required(true)
                 .type(ConfigurationItemType.STRING).build(),
         new ConfigurationItem.Builder()
                 .name('subject', getYamlPath())
                 .displayText('JWT Subject')
                 .helpText('Subject for JWT RS256 Authentication')
                 .required(false)
                 .type(ConfigurationItemType.STRING).build(),
         new ConfigurationItem.Builder()
                 .name('expirationPeriod', getYamlPath())
                 .displayText('JWT Expiration')
                 .helpText('Expiration period, in milliseconds, ' +
                 'of JWT RS256 Authentication')
                 .required(true)
                 .type(ConfigurationItemType.LONG).build(),
         new ConfigurationItem.Builder()
                 .name('audience', getYamlPath())
                 .displayText('JWT Audience')
                 .helpText('Audience for JWT RS256 Authentication')
                 .required(false)
                 .type(ConfigurationItemType.STRING).build(),
         new ConfigurationItem.Builder()
                 .name('useIssuedAt', getYamlPath())
                 .displayText('JWT Use Issued At')
                 .helpText('Whether or not to include `Issued At` information ' +
                 'during authentication attempt.')
                 .required(false)
                 .type(ConfigurationItemType.BOOLEAN).build()
        ] as Set<ConfigurationItem>
    }

    @Override
    String getConfigurationInterface() {
        getBaseTypesPackage() + '.security.authenticator.JwtRs256Configuration'
    }
}
