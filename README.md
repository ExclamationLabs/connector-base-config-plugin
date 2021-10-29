# connector-base-config-plugin

## Configuration class generation plugin for Base Connector framework.

This project, written in Groovy, is a gradle plugin used by the Base Connector
framework in order to automatically generate a Configuration Java code class 
based upon the `configuration.structure.yaml` in the project.

This is done so that common configuration types, particularly those for
authentication, do not have to be copied/pasted or rewritten for every connector
implementation that has similar configuration needs.  It also eliminates some
risk for making a mistake in manual creation of the Configuration class.  The
Connid framework uses reflection and expects the look and be structured
in a very particular way, which this plugin is aware of and adheres to.

Every connector updating to the 2.0+ base connector framework will now need to:
- have a `configuration.structure.yml` written and present in the top-level directory.
- have `build.gradle` setup to have the plugin ran and code generated, prior to
the start of compiling Java.

