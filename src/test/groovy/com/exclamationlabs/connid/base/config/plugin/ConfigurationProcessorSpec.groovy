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

import com.exclamationlabs.connid.base.config.plugin.model.ConfigurationItemType
import com.exclamationlabs.connid.base.config.plugin.model.Rest
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
import spock.lang.Specification

class ConfigurationProcessorSpec extends Specification {

    def 'groups setup'() {
        given:
            ConfigurationProcessor processor = new ConfigurationProcessor()
            def groups = processor.getConfigurationGroups()

        when:
            1 == 1

        then:
            groups
            16 == groups.size()
            groups.contains(new Rest())
            groups.contains(new Service())
            groups.contains(new HttpBasicAuth())
            groups.contains(new Jks())
            groups.contains(new Pem())
            groups.contains(new Pfx())
            groups.contains(new Proxy())
            groups.contains(new DirectAccessToken())
            groups.contains(new JwtHs256())
            groups.contains(new JwtRs256())
            groups.contains(new Oauth2AuthorizationCode())
            groups.contains(new Oauth2ClientCredentials())
            groups.contains(new Oauth2Jwt())
            groups.contains(new Oauth2Password())
            groups.contains(new Oauth2RefreshToken())
    }

    def 'happyPath'() {
        given:
            def happyData = """
---
name: 'myconn'
configurationClass:
  name: 'TestMeConfiguration'
  package: 'com.exclamationlabs.connid.base.testme.configuration'
rest: true # io-error-retries
service: true # service-url
security:
  httpBasicAuth: true # username, password
  proxy: true # host, port, type (http/socks5)
  jks: true
  pem: true # pem private key loading - file
  pfx: true # pfx key store loading
  authenticator:
    directAccessToken: true # token
    jwtHs256: true # issuer, secret, expirationPeriod
    jwtRs256: true 
    oauth2AuthorizationCode: true # tokenUrl, authorizationCode, clientId, clientSecret, redirectUri
    oauth2ClientCredentials: true # tokenUrl, clientId, clientSecret, scope
    oauth2Jwt: true
    oauth2Password: true
    oauth2RefreshToken: true
custom:
  items:
    optional:
      mine1:
        type: int # Message.properties connector.myconn.my-type-1.help & connector.custom.my-type-1.display
        default: 1
        validations:
          - '@Min(1)'
          - '@Max(10)'
      mine2:
        type: boolean
        default: true
      mine21:
        type: long
        default: 123456789
      mine3:
        type: float
        default: 1.23
      mine4:
        type: string
        default: 'hi'
    required:
      mine5:
        type: string # subset of string
        default: hi@hi.com
        confidential: true
      mine6:
        type: string # subset of string
        default: 'http://www.google.com'
      mine7:
        type: guarded_string
      mine8:
        type: string_array
  helpText:
    serviceUrl:
      display: 'My Service URL'
      help: 'My Service Help'
    security:
      httpBasicAuthUsername:
        display: 'something.displayme'
        help: 'something.helpme'
"""

            ConfigurationProcessor processor = setupProcessor(happyData)

        when:
            processor.execute()

        then:
            1 == 1
            'myconn' == processor.name
            'com.exclamationlabs.connid.base.testme.configuration' == processor.outputPackage
            'TestMeConfiguration' == processor.outputClassName
            processor.configurationItems
            40 == processor.configurationItems.size()
    }

    def 'happyPath single custom required item'() {
        given:
        def happyData = """
---
name: 'myconn'
configurationClass:
  name: 'TestMeConfiguration'
  package: 'com.exclamationlabs.connid.base.testme.configuration'
custom:
  items:
    required:
      mine1:
        type: int
        default: 5
        confidential: true
        validations:
          - '@Min(1)'
          - '@Max(10)'
        displayText: 'test display'
        helpText: 'test help'
"""

        ConfigurationProcessor processor = setupProcessor(happyData)

        when:
        processor.execute()

        then:
        1 == 1
        'myconn' == processor.name
        'com.exclamationlabs.connid.base.testme.configuration' == processor.outputPackage
        'TestMeConfiguration' == processor.outputClassName
        processor.configurationItems
        1 == processor.configurationItems.size()

        def singleItem = processor.configurationItems.getAt(0)
        ConfigurationItemType.INT == singleItem.getType()
        5 == singleItem.getDefaultValue()
        'test display' == singleItem.getDisplayText()
        'test help' == singleItem.getHelpText()
        singleItem.getConfidential()
    }

    def 'happyPath single custom optional item'() {
        given:
        def happyData = """
---
name: 'myconn'
configurationClass:
  name: 'TestMeConfiguration'
  package: 'com.exclamationlabs.connid.base.testme.configuration'
custom:
  items:
    optional:
      mine1:
        type: int
        default: 5
        validations:
          - '@Min(1)'
          - '@Max(10)'
        displayText: 'test display'
        helpText: 'test help'
"""

        ConfigurationProcessor processor = setupProcessor(happyData)

        when:
        processor.execute()

        then:
        1 == 1
        'myconn' == processor.name
        'com.exclamationlabs.connid.base.testme.configuration' == processor.outputPackage
        'TestMeConfiguration' == processor.outputClassName
        processor.configurationItems
        1 == processor.configurationItems.size()

        def singleItem = processor.configurationItems.getAt(0)
        ConfigurationItemType.INT == singleItem.getType()
        5 == singleItem.getDefaultValue()
        'test display' == singleItem.getDisplayText()
        'test help' == singleItem.getHelpText()

    }

    def 'missing name'() {
        given:
            def testData = """
bogus: bogus
"""
            ConfigurationProcessor processor = setupProcessor(testData)

        when:
        processor.execute()

        then:
        thrown(IllegalArgumentException)
    }

    def 'name has whitespace'() {
        given:
        def testData = """
name: 'the cat jumped over      the lazy dog '
"""
        ConfigurationProcessor processor = setupProcessor(testData)

        when:
        processor.execute()

        then:
        thrown(IllegalArgumentException)
    }

    def 'name has invalid data'() {
        given:
        def testData = """
name: 
  uno: 'dos'
  tres: 'cuatro'
"""
        ConfigurationProcessor processor = setupProcessor(testData)

        when:
        processor.execute()

        then:
        thrown(IllegalArgumentException)
    }

    def 'configuration-class is missing'() {
        given:
        def testData = """
name: test
"""
        ConfigurationProcessor processor = setupProcessor(testData)

        when:
        processor.execute()

        then:
        thrown(IllegalArgumentException)
    }

    def 'configuration-class is invalid type'() {
        given:
        def testData = """
name: test
configurationClass: 'dummy'
"""
        ConfigurationProcessor processor = setupProcessor(testData)

        when:
        processor.execute()

        then:
        thrown(IllegalArgumentException)
    }

    def 'configuration-class.name is missing'() {
        given:
        def testData = """
name: test
configurationClass:
    dummy: 'test'
"""
        ConfigurationProcessor processor = setupProcessor(testData)

        when:
        processor.execute()

        then:
        thrown(IllegalArgumentException)
    }

    def 'configuration-class.name is invalid type'() {
        given:
        def testData = """
name: test
configurationClass:
  name:
    data1: 'date2'  
"""
        ConfigurationProcessor processor = setupProcessor(testData)

        when:
        processor.execute()

        then:
        thrown(IllegalArgumentException)
    }

    def 'configuration-class.name is invalid class name'() {
        given:
        def testData = """
name: test
configurationClass:
  name: 'int'
"""
        ConfigurationProcessor processor = setupProcessor(testData)

        when:
        processor.execute()

        then:
        thrown(IllegalArgumentException)
    }

    def 'configuration-class.pacakge is missing'() {
        given:
        def testData = """
name: test
configurationClass:
    name: 'test'
"""
        ConfigurationProcessor processor = setupProcessor(testData)

        when:
        processor.execute()

        then:
        thrown(IllegalArgumentException)
    }

    def 'configuration-class.package is invalid type'() {
        given:
        def testData = """
name: test
configurationClass:
  name: 'test'
  package:
    test: 'test2'
"""
        ConfigurationProcessor processor = setupProcessor(testData)

        when:
        processor.execute()

        then:
        thrown(IllegalArgumentException)
    }

    def 'configurationClass.package is invalid package name'() {
        given:
        def testData = """
name: test
configurationClass:
  name: 'test'
  package: '1XYZ'
"""
        ConfigurationProcessor processor = setupProcessor(testData)

        when:
        processor.execute()

        then:
        thrown(IllegalArgumentException)
    }

    private def setupProcessor(String input) {
        new ConfigurationProcessor(new ByteArrayInputStream(input.getBytes()))
    }
}
