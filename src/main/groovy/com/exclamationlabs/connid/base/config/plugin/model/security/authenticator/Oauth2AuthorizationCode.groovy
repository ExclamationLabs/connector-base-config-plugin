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

class Oauth2AuthorizationCode extends ConfigurationGroup {
    @Override
    List<String> getYamlPath() {
        ['security', 'authenticator', 'oauth2AuthorizationCode']
    }

    @Override
    Set<ConfigurationItem> getConfigurationItems() {
        [new ConfigurationItem.Builder()
                 .name('tokenUrl', getYamlPath())
                 .order(2801)
                 .displayText('OAuth2 Token URL')
                 .helpText('URL used to obtain OAuth2 token')
                 .required(true)
                 .type(ConfigurationItemType.STRING).build(),
         new ConfigurationItem.Builder()
                 .name('authorizationCode', getYamlPath())
                 .order(2802)
                 .displayText('OAuth2 Authorization Code')
                 .helpText('Authorization code used for OAuth2 access')
                 .required(true)
                 .confidential(true)
                 .type(ConfigurationItemType.GUARDED_STRING).build(),
         new ConfigurationItem.Builder()
                 .name('clientId', getYamlPath())
                 .order(2803)
                 .displayText('OAuth2 Client Id')
                 .helpText('OAuth2 Client Id')
                 .required(false)
                 .type(ConfigurationItemType.STRING).build(),
         new ConfigurationItem.Builder()
                 .name('clientSecret', getYamlPath())
                 .order(2804)
                 .displayText('OAuth2 Client Secret')
                 .helpText('OAuth2 Client Secret')
                 .required(false)
                 .confidential(true)
                 .type(ConfigurationItemType.GUARDED_STRING).build(),
         new ConfigurationItem.Builder()
                 .name('redirectUri', getYamlPath())
                 .order(2805)
                 .displayText('OAuth2 Redirect URI')
                 .helpText('Redirect URI for OAuth2 configuration (not always used)')
                 .required(false)
                 .type(ConfigurationItemType.STRING).build(),
         new ConfigurationItem.Builder()
                 .name('oauth2Information', getYamlPath())
                 .order(2806)
                 .required(false)
                 .internal(true)
                 .confidential(true)
                 .type(ConfigurationItemType.STRING_MAP).build()
        ] as Set<ConfigurationItem>
    }

    @Override
    String getConfigurationInterface() {
        getBaseTypesPackage() + '.security.authenticator.Oauth2AuthorizationCodeConfiguration'
    }
}
