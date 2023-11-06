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

class PemPrivateKey extends ConfigurationGroup {
    @Override
    List<String> getYamlPath() {
        return ['security', 'pemPrivateKey']
    }

    @Override
    Set<ConfigurationItem> getConfigurationItems() {
        [new ConfigurationItem.Builder()
                 .name('pemFile', getYamlPath())
                 .order(2501)
                 .displayText('PEM File')
                 .helpText('Base 64 Encoded Representation of .pem file')
                 .required(true)
                 .type(ConfigurationItemType.GUARDED_STRING).build(),
         new ConfigurationItem.Builder()
                 .name('privateKeyFile', getYamlPath())
                 .order(2502)
                 .displayText('Private Key File')
                 .helpText('Base 64 Encoded Representation of .key private key file')
                 .required(true)
                 .type(ConfigurationItemType.GUARDED_STRING).build(),
         new ConfigurationItem.Builder()
                 .name('keyPassword', getYamlPath())
                 .order(2503)
                 .displayText('Private Key Password')
                 .helpText('Password for the private key')
                 .required(false)
                 .type(ConfigurationItemType.GUARDED_STRING).build()
         ,
         new ConfigurationItem.Builder()
                 .name('keyStorePassword', getYamlPath())
                 .order(2504)
                 .displayText('KeyStore Password')
                 .helpText('KeyStorePassword for the generated KeyStore')
                 .required(false)
                 .type(ConfigurationItemType.GUARDED_STRING).build()
        ] as Set<ConfigurationItem>
    }

    @Override
    String getConfigurationInterface() {
        getBaseTypesPackage() + '.security.PemPrivateKeyConfiguration'
    }
}
