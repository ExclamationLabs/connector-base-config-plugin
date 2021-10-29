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
package com.exclamationlabs.connid.base.config.plugin.model

abstract class ConfigurationGroup {

    static final String BASETYPES_PACKAGE = "com.exclamationlabs.connid.base.connector.configuration.basetypes"

    protected Boolean enabled

    abstract List<String> getYamlPath()

    abstract Set<ConfigurationItem> getConfigurationItems()

    /**
     * Subclasses need to make sure this response coincides with the proper interface
     * name for the ConfigurationGroup.  These are found in the connector-base project,
     * under package com.exclamationlabs.connid.base.connector.configuration.basetypes
     * @return
     */
    abstract String getConfigurationInterface()

    @Override
    boolean equals(Object input) {
        input instanceof ConfigurationGroup &&
                ((ConfigurationGroup) input).getYamlPath().toString() ==
                this.getYamlPath().toString()
    }

    @Override
    int hashCode() {
        return getYamlPath().toString().hashCode()
    }

    void setEnabled(Boolean input) {
        enabled = input
    }

    boolean isEnabled() {
        enabled != null && enabled
    }

    protected String getBaseTypesPackage() {
        BASETYPES_PACKAGE
    }
}
