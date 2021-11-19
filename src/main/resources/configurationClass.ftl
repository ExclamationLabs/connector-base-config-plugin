package ${packageName};

import com.exclamationlabs.connid.base.connector.configuration.ConnectorConfiguration;
import com.exclamationlabs.connid.base.connector.configuration.ConfigurationInfo;
import com.exclamationlabs.connid.base.connector.configuration.ConfigurationReader;
import org.identityconnectors.framework.common.objects.ConnectorMessages;
import org.identityconnectors.framework.spi.ConfigurationClass;
import org.identityconnectors.framework.spi.ConfigurationProperty;
<#if hasGuardedString>
import org.identityconnectors.common.security.GuardedString;
</#if>
import javax.validation.constraints.*;
import java.util.*;

<#list groups as group>
    <#if group.isEnabled()>
import ${group.getConfigurationInterface()};
    </#if>
</#list>

/**
* This class was automatically generated by connector-base-config-plugin.
* It is not ideal to modify this file, as subsequent builds of this project will overlay
* your changes in this file.  Instead, modify the configuration.structure.yml in this project.
*/
<#if internalItems??>
@ConfigurationClass(skipUnsupported = true, ignore={${internalItems}})
<#else>
@ConfigurationClass(skipUnsupported = true)
</#if>
<#if allInterfaces??>
public class ${className} implements ConnectorConfiguration, ${allInterfaces} {
<#else>
public class ${className} implements ConnectorConfiguration {
</#if>

    protected ConnectorMessages connectorMessages;

    @ConfigurationInfo(path="source", internal=true)
    protected String source;

    @ConfigurationInfo(path="name", internal=true)
    protected String name;

    @ConfigurationInfo(path="active", internal=true)
    protected Boolean active;

    @ConfigurationInfo(path="currentToken", internal=true)
    protected String currentToken;

<#list items as item>
    <#if item.validations??>
        <#list item.validations as validation>
    ${validation}
        </#list>
    </#if>
    <#if item.required>
        <#if item.getJavaType() == 'String'>
    @NotBlank(message="${item.name} cannot be blank")
        <#else>
    @NotNull(message="${item.name} cannot be null")
        </#if>
    </#if>
    <#if item.getInternal()>
    @ConfigurationInfo(path="${item.getYamlPathAsString()}", internal=true)
    <#else>
    @ConfigurationInfo(path="${item.getYamlPathAsString()}")
    </#if>
    <#if item.defaultValue??>
        <#switch item.getJavaType()>
            <#case 'Long'>
    private ${item.getJavaType()} ${item.name} = ${item.defaultValue}L;
                <#break>
            <#case 'Integer'>
            <#case 'Boolean'>
    private ${item.getJavaType()} ${item.name} = ${item.defaultValue};
                <#break>
            <#case 'Float'>
    private ${item.getJavaType()} ${item.name} = ${item.defaultValue}f;
                <#break>
            <#default>
    private ${item.getJavaType()} ${item.name} = "${item.defaultValue}";
        </#switch>
    <#else>
    private ${item.getJavaType()} ${item.name};
    </#if>

</#list>

    public ${className}() {
        source = "default";
        name = "default";
        active = true;
    }

    public ${className}(String configurationName) {
        name = configurationName;
        active = true;
        ConfigurationReader.prepareTestConfiguration(this);
    }

<#list items as item>
    @ConfigurationProperty(
        displayMessageKey = "${item.getDisplayText()}",
        helpMessageKey = "${item.getHelpText()}",
        required = ${item.getRequiredString()})
    public ${item.getJavaType()} get${item.getNameUpperCaseFirst()}() {
        return this.${item.name};
    }

    public void set${item.getNameUpperCaseFirst()}(${item.getJavaType()} input) {
        this.${item.name} = input;
    }

</#list>

    @Override
    public ConnectorMessages getConnectorMessages() {
        return connectorMessages;
    }

    @Override
    public void setConnectorMessages(ConnectorMessages messages) {
        connectorMessages = messages;
    }

    @Override
    public String getCurrentToken() {
        return currentToken;
    }

    @Override
    public void setCurrentToken(String input) {
        currentToken = input;
    }

    @Override
    public String getSource() {
        return source;
    }

    @Override
    public void setSource(String input) {
        source = input;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String input) {
        name = input;
    }

    @Override
    public Boolean getActive() {
        return active;
    }

    @Override
    public void setActive(Boolean input) {
        active = input;
    }


}