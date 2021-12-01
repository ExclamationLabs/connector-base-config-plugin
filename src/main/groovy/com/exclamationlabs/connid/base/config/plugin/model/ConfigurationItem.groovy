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

class ConfigurationItem {

    private Boolean required
    private Boolean internal
    private ConfigurationItemType type
    private String name
    private String baseName
    private def defaultValue
    private Boolean confidential
    private String displayText
    private String helpText
    private List<String> yamlPath
    private List<String> validations

    ConfigurationItem() {}

    ConfigurationItem(Builder builderInput) {
        yamlPath = builderInput.getYamlPath()
        name = builderInput.getName()
        baseName = builderInput.getBaseName()
        required = builderInput.getRequired()
        internal = builderInput.getInternal()
        type = builderInput.getType()
        confidential = builderInput.getConfidential()
        defaultValue = builderInput.getDefaultValue()
        displayText = builderInput.getDisplayText()
        helpText = builderInput.getHelpText()
        validations = builderInput.getValidations()
    }

    List<String> getValidations() {
        this.validations
    }

    Boolean getConfidential() {
        this.confidential == null ? false : this.confidential
    }

    String getConfidentialString() {
        this.confidential ? 'true' : 'false'
    }

    Boolean getRequired() {
        this.required
    }

    Boolean getInternal() {
        this.internal == null ? false : this.internal
    }

    String getRequiredString() {
        this.required ? 'true' : 'false'
    }

    void setRequired(Boolean required) {
        this.required = required
    }

    ConfigurationItemType getType() {
        this.type
    }

    String getJavaType() {
        def output
        switch (this.type) {
            case ConfigurationItemType.GUARDED_STRING: output = 'GuardedString'; break
            case ConfigurationItemType.LONG: output = 'Long'; break
            case ConfigurationItemType.INT: output ='Integer'; break
            case ConfigurationItemType.BOOLEAN: output = 'Boolean'; break
            case ConfigurationItemType.FLOAT: output = 'Float'; break
            case ConfigurationItemType.STRING: output = 'String'; break
            case ConfigurationItemType.STRING_ARRAY: output = 'String[]'; break
            case ConfigurationItemType.STRING_MAP: output = 'Map<String,String>'; break
            default: output = 'Object'; break
        }
        output
    }

    void setType(ConfigurationItemType type) {
        this.type = type
    }

    String getName() {
        this.baseName
    }

    String getNameUpperCaseFirst() {
        this.baseName.capitalize()
    }

    void setName(String name) {
        this.baseName = name
    }

    def getDefaultValue() {
        this.defaultValue
    }

    void setDefaultValue(defaultValue) {
        this.defaultValue = defaultValue
    }

    String getHelpText() {
        if (helpText != null) {
            helpText
        } else {
            buildDisplayProperty() + '.help'
        }
    }

    String getDisplayText() {
        if (displayText != null) {
            displayText
        } else {
            buildDisplayProperty() + '.display'
        }
    }

    private String buildDisplayProperty() {
        boolean first = true
        String output = ''
        for (String part : yamlPath) {
            output += ((!first) ? '.' : '' ) + part
            first = false
        }
        output += (yamlPath == null ? 'custom.' : '') + name
        output
    }

    public String getYamlPathAsString() {
        boolean first = true
        String output = ''
        for (String part : yamlPath) {
            output += ((!first) ? '.' : '' ) + part
            first = false
        }
        output += (yamlPath == null ? 'custom.' : '.') + baseName
        output
    }

    @Override
    boolean equals(Object input) {
        input != null && input instanceof ConfigurationItem &&
                this.getName() == ((ConfigurationItem) input).getName()
    }

    @Override
    int hashCode() {
        getName().hashCode()
    }

    static class Builder {
        private Boolean required
        private Boolean internal
        private String name
        private String baseName
        private ConfigurationItemType type
        private def defaultValue
        private Boolean confidential
        private String displayText
        private String helpText
        private List<String> yamlPath
        private List<String> validations

        ConfigurationItem build() {
            new ConfigurationItem(this)
        }

        String getDisplayText() {
            this.displayText
        }

        String getHelpText() {
            this.helpText
        }

        List<String> getYamlPath() {
            this.yamlPath
        }

        def getDefaultValue() {
            this.defaultValue
        }

        Boolean getRequired() {
            this.required
        }

        Boolean getInternal() {
            this.internal
        }

        Boolean getConfidential() {
            this.confidential
        }

        ConfigurationItemType getType() {
            this.type
        }

        String getName() {
            this.name
        }

        String getBaseName() {
            this.baseName
        }

        List<String> getValidations() {
            this.validations
        }

        Builder required(boolean input) {
            required = input
            this
        }

        Builder internal(boolean input) {
            internal = input
            this
        }

        Builder name(String input) {
            name = input
            baseName = input
            this
        }

        Builder helpText(String input) {
            helpText = input
            this
        }

        Builder displayText(String input) {
            displayText = input
            this
        }

        Builder confidential(boolean input) {
            confidential = input
            this
        }

        Builder type(ConfigurationItemType input) {
            type = input
            this
        }

        Builder defaultValue(def input) {
            defaultValue = input
            this
        }

        Builder validations(List<String> input) {
            validations = input
            this
        }


        Builder name(String input, List<String> yamlPathIn) {
            def path = ''
            boolean firstItem = true
            for (String currentPath : yamlPathIn) {
                if (firstItem) {
                    firstItem = false
                    path += currentPath
                } else {
                    path += currentPath.capitalize()
                }
            }
            path += input.capitalize()
            name = path
            baseName = input
            yamlPath = yamlPathIn
            this
        }

    }
}
