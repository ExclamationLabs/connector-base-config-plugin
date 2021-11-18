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
package com.exclamationlabs.connid.base.config.plugin

import com.exclamationlabs.connid.base.config.plugin.model.ConfigurationGroup
import com.exclamationlabs.connid.base.config.plugin.model.ConfigurationItem
import com.exclamationlabs.connid.base.config.plugin.model.ConfigurationItemType
import com.exclamationlabs.connid.base.config.plugin.model.Rest
import com.exclamationlabs.connid.base.config.plugin.model.Results
import com.exclamationlabs.connid.base.config.plugin.model.Service
import com.exclamationlabs.connid.base.config.plugin.model.security.HttpBasicAuth
import com.exclamationlabs.connid.base.config.plugin.model.security.Jks
import com.exclamationlabs.connid.base.config.plugin.model.security.Pem
import com.exclamationlabs.connid.base.config.plugin.model.security.Pfx
import com.exclamationlabs.connid.base.config.plugin.model.security.Proxy
import com.exclamationlabs.connid.base.config.plugin.model.security.authenticator.DirectAccessToken
import com.exclamationlabs.connid.base.config.plugin.model.security.authenticator.JwtHs256
import com.exclamationlabs.connid.base.config.plugin.model.security.authenticator.JwtRs256
import com.exclamationlabs.connid.base.config.plugin.model.security.authenticator.Oauth2AuthorizationCode
import com.exclamationlabs.connid.base.config.plugin.model.security.authenticator.Oauth2ClientCredentials
import com.exclamationlabs.connid.base.config.plugin.model.security.authenticator.Oauth2Jwt
import com.exclamationlabs.connid.base.config.plugin.model.security.authenticator.Oauth2Password
import com.exclamationlabs.connid.base.config.plugin.model.security.authenticator.Oauth2RefreshToken
import org.yaml.snakeyaml.Yaml

import javax.lang.model.SourceVersion

class ConfigurationProcessor {

    Set<ConfigurationGroup> groups

    private InputStream yamlSource

    private String name
    private String outputPackage
    private String outputClassName
    private Set<ConfigurationItem> configurationItems = new HashSet<>()

    ConfigurationProcessor(InputStream input) {
        yamlSource = input
        groups =
                [
                        new Rest(),
                        new Results(),
                        new Service(),
                        new HttpBasicAuth(),
                        new Proxy(),
                        new Jks(),
                        new Pem(),
                        new Pfx(),
                        new DirectAccessToken(),
                        new JwtHs256(),
                        new JwtRs256(),
                        new Oauth2AuthorizationCode(),
                        new Oauth2ClientCredentials(),
                        new Oauth2Jwt(),
                        new Oauth2Password(),
                        new Oauth2RefreshToken()
                ] as Set<ConfigurationGroup>
    }

    def execute() throws IllegalArgumentException {
        Yaml yaml = new Yaml()
        Map<String, Object> dataMap = yaml.load(yamlSource)
        println 'YAML content loaded from input'

        parseName(dataMap)
        parseOutputClassInformation(dataMap)
        parseConfigurationGroups(dataMap, null)
        parseCustomItems(dataMap, false)
        parseCustomItems(dataMap, true)
        println 'Done Processing Configuration input, setup ' +
                configurationItems.size() + ' configuration items'
    }

    private def parseName(Map<String, Object> dataMap) throws IllegalArgumentException {
        def nameValue = dataMap.get('name')
        if (nameValue == null) {
            throw new IllegalArgumentException('`name` missing in structure yml')
        }

        if (!(nameValue instanceof String)) {
            throw new IllegalArgumentException('`name` not a valid String in structure yml')
        }

        if (nameValue.trim().matches(".*\\s.*")) {
            throw new IllegalArgumentException('`name` input cannot contain any whitespace characters')
        }
        name = nameValue.trim()
    }

    private def parseOutputClassInformation(Map<String, Object> dataMap) throws IllegalArgumentException {
        def classDataMap = dataMap.get('configurationClass')
        if (classDataMap == null) {
            throw new IllegalArgumentException('`configurationClass` missing in structure yml')
        }

        if (!(classDataMap instanceof Map)) {
            throw new IllegalArgumentException('`configurationClass` not a valid map in structure yml')
        }

        def className = classDataMap.get('name')
        if (className == null) {
            throw new IllegalArgumentException('`configurationClass.name` missing in structure yml')
        }

        if (!(className instanceof String)) {
            throw new IllegalArgumentException('`configurationClass.name` not a valid String in structure yml')
        }

        if (!SourceVersion.isName(className.trim())) {
            throw new IllegalArgumentException('`configurationClass.name` value `' + className +
                    '` is not a valid output Java class name')
        }
        outputClassName = className.trim()

        def packageName = classDataMap.get('package')
        if (packageName == null) {
            throw new IllegalArgumentException('`configurationClass.package` missing in structure yml')
        }

        if (!(packageName instanceof String)) {
            throw new IllegalArgumentException('`configurationClass.package` not a valid String in structure yml')
        }

        if (!(packageName.trim().matches('^[a-z][a-z0-9_]*(\\.[a-z0-9_]+)+[0-9a-z_]$'))) {
            throw new IllegalArgumentException('`configurationClass.package` value `' + packageName +
                    '` is not a valid output Java package name')
        }
        outputPackage = packageName.trim()
    }

    private def parseConfigurationGroups(Map<String, Object> dataMap, List<String> parentPath) {
        dataMap.each {
            current ->
                List<String> pathing
                if (parentPath == null) {
                    pathing = [current.key]
                } else {
                    pathing = []
                    pathing.addAll(parentPath)
                    pathing.add(current.key)
                }
                if (current.value instanceof Map) {
                    parseConfigurationGroups((Map) current.value, pathing)
                } else if (current.value === Boolean.TRUE) {
                    checkConfigurationGroupToApply(pathing)
                }
        }

    }

    private def checkConfigurationGroupToApply(List<String> itemPath) {
        for  (ConfigurationGroup item : groups) {
                if (item.getYamlPath().toString() == itemPath.toString()) {
                    item.setEnabled(true)
                    configurationItems.addAll(
                            item.getConfigurationItems()
                    )
                }
        }

    }

    private def parseCustomItems(Map<String, Object> dataMap, boolean requiredItems) throws IllegalArgumentException {
        if (dataMap.containsKey('custom') && dataMap['custom'] instanceof Map) {
            Map<String, Object> customDataMap = (Map) dataMap['custom']
            if (customDataMap.containsKey('items') && customDataMap['items'] instanceof Map) {
                Map<String, Object> itemsDataMap = (Map) customDataMap['items']
                if (requiredItems && itemsDataMap.containsKey('required') &&
                        itemsDataMap['required'] instanceof Map) {
                    parseCustomItemsDetail((Map) itemsDataMap['required'], true)
                } else if ((!requiredItems) && itemsDataMap.containsKey('optional')
                        && itemsDataMap['optional'] instanceof Map) {
                    parseCustomItemsDetail((Map) itemsDataMap['optional'], false)
                }
            }
        }
    }

    private def parseCustomItemsDetail(Map<String, Object> dataMap, boolean requiredItems) throws IllegalArgumentException {
        def requiredText = requiredItems ? 'required' : 'optional'

        for (Map.Entry<String,Object> item : dataMap.entrySet()) {
                if (!item.value instanceof Map) {
                    throw new IllegalArgumentException(requiredText + ' item `' +
                            item.key + '` is not a map type')
                }
                Map<String, Object> itemData = (Map) item.value
                if (!itemData.containsKey('type') || itemData['type'] == null) {
                    throw new IllegalArgumentException(requiredText + ' item `' +
                            item.key + '` does not have a type definition')
                }
                ConfigurationItemType itemType = ConfigurationItemType.parse(itemData['type'].toString())

                def validations = null
                if (itemData.containsKey('validations')) {
                    validations = itemData.get('validations')
                    if (!validations instanceof List) {
                        throw new IllegalArgumentException(requiredText + ' item `' +
                                item.key + '` validations is not a list')
                    }
                }

                def itemDefaultValue = null
                if (itemData.containsKey('default') && itemData['default'] != null) {
                    itemDefaultValue = itemData['default']
                }

                def itemDisplayText = null
                if (itemData.containsKey('displayText') && itemData['displayText'] != null) {
                    itemDisplayText = itemData['displayText']
                }

                def itemHelpText = null
                if (itemData.containsKey('helpText') && itemData['helpText'] != null) {
                    itemHelpText = itemData['helpText']
                }

                ConfigurationItem itemToAdd = new ConfigurationItem.Builder().name(item.key)
                    .defaultValue(itemDefaultValue).required(requiredItems).
                        displayText(itemDisplayText).helpText(itemHelpText).
                        validations(validations).type(itemType).build()
                configurationItems.add(itemToAdd)
        }

    }

    String getName() {
        name
    }

    void setName(String name) {
        this.name = name
    }

    String getOutputPackage() {
        outputPackage
    }

    void setOutputPackage(String outputPackage) {
        this.outputPackage = outputPackage
    }

    String getOutputClassName() {
        outputClassName
    }

    void setOutputClassName(String outputClassName) {
        this.outputClassName = outputClassName
    }

    Set<ConfigurationItem> getConfigurationItems() {
        configurationItems
    }

    void setConfigurationItems(Set<ConfigurationItem> configurationItems) {
        this.configurationItems = configurationItems
    }

    Set<ConfigurationGroup> getConfigurationGroups() {
        groups
    }

    String getAllConfigurationInterfaces() {
        def output = ''
        def first = true
        for (ConfigurationGroup item : groups) {
                if (item.isEnabled()) {
                    int lastIndex = item.getConfigurationInterface().lastIndexOf('.')
                    output += ((!first) ? ', ' : '') +
                            item.getConfigurationInterface().substring(lastIndex + 1)
                    first = false
                }
        }
        return (output == '' ? null : output)

    }

    String getAllInternalItems() {
        def output = '"active", "name", "source", "currentToken"'
        for (ConfigurationItem item : configurationItems) {
                if (item.getInternal()) {
                    output += ', "' + item.getName() + '"'
                }
        }
        return (output == '' ? null : output)
    }
}
