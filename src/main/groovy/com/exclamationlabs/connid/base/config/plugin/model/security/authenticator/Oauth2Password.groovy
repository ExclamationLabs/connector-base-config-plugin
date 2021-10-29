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

class Oauth2Password extends ConfigurationGroup {
    @Override
    List<String> getYamlPath() {
        ['security', 'authenticator', 'oauth2Password']
    }

    @Override
    Set<ConfigurationItem> getConfigurationItems() {
        [new ConfigurationItem.Builder()
                 .name('tokenUrl', getYamlPath())
                 .displayText('OAuth2 Token URL')
                 .helpText('URL used to obtain OAuth2 token')
                 .required(true)
                 .type(ConfigurationItemType.STRING).build(),
         new ConfigurationItem.Builder()
                 .name('encodedSecret', getYamlPath())
                 .displayText('OAuth2 Encoded Secret')
                 .helpText('Encoded secret used for access (usually a base 64 ' +
                         'encoded version of some user credential)')
                 .required(true)
                 .type(ConfigurationItemType.STRING).build(),
         new ConfigurationItem.Builder()
                 .name('username', getYamlPath())
                 .displayText('OAuth2 Username')
                 .helpText('Account username pertained to OAuth2 access')
                 .required(true)
                 .type(ConfigurationItemType.STRING).build(),
         new ConfigurationItem.Builder()
                 .name('password', getYamlPath())
                 .displayText('OAuth2 Password')
                 .helpText('Account password pertained to OAuth2 access')
                 .required(true)
                 .type(ConfigurationItemType.STRING).build(),
         new ConfigurationItem.Builder()
                 .name('oauth2Information', getYamlPath())
                 .required(false)
                 .internal(true)
                 .type(ConfigurationItemType.STRING_MAP).build()
        ] as Set<ConfigurationItem>
    }

    @Override
    String getConfigurationInterface() {
        getBaseTypesPackage() + '.security.authenticator.Oauth2PasswordConfiguration'
    }
}
