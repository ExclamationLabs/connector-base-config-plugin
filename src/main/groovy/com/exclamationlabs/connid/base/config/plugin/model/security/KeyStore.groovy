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

class KeyStore extends ConfigurationGroup {
    @Override
    List<String> getYamlPath() {
        return ['security', 'keystore']
    }

    @Override
    Set<ConfigurationItem> getConfigurationItems() {
        [new ConfigurationItem.Builder()
                 .name('keystoreFile', getYamlPath())
                 .order(2301)
                 .displayText('Keystore File')
                 .helpText('Full file path to a keystore file')
                 .required(true)
                 .type(ConfigurationItemType.STRING).build(),
         new ConfigurationItem.Builder()
                 .name('keystorePassword', getYamlPath())
                 .order(2302)
                 .displayText('Keystore Password')
                 .helpText('Password value protecting the keystore')
                 .required(true)
                 .confidential(true)
                 .type(ConfigurationItemType.GUARDED_STRING).build(),
         new ConfigurationItem.Builder()
                 .name('keyPassword', getYamlPath())
                 .order(2303)
                 .displayText('Key Password')
                 .helpText('Password for the private key in the keystore.  If not given the `keystorePassword` value will be reused.')
                 .required(false)
                 .confidential(true)
                 .type(ConfigurationItemType.GUARDED_STRING).build(),
         new ConfigurationItem.Builder()
                 .name('keyAlias', getYamlPath())
                 .order(2304)
                 .displayText('Alias for the key in the keystore')
                 .helpText('Alias for the key in the keystore.  If not given, the entire keystore will be considered. Note, if there are multiple keys in the keystore, the first one found will be used.')
                 .required(false)
                 .confidential(true)
                 .type(ConfigurationItemType.STRING).build(),
         new ConfigurationItem.Builder()
                 .name('tlsVersion', getYamlPath())
                 .order(2305)
                 .displayText('TLS Version')
                 .helpText('TLS version to use for the connection.  If not given, the default version of TLSv1.3 will be used.')
                 .defaultValue('TLSv1.3')
                 .required(false)
                 .confidential(false)
                 .type(ConfigurationItemType.STRING).build()
        ] as Set<ConfigurationItem>
    }

    @Override
    String getConfigurationInterface() {
        getBaseTypesPackage() + '.security.KeyStoreConfiguration'
    }
}
