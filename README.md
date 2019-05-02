# ![Obsolete](https://dummyimage.com/700x100/fff/f00&text=This%20Repository%20Is%20Obsolete!)

We don't maintain this code base anymore.

If you are interested in picking it up from where we left please reach out to us through [Arquillian forum](http://discuss.arquillian.org/).

Arquillian Maven Plugin
=======================

A Maven plugin with support for start/deploy/undeploy/stop and run operations using the Arquillian Containers.


Operations
----------

* run

    Similar to the famous jetty:run maven plugin. This goal will Setup and Start the Container, Deploy the given Archive, then wait for a JVM shutdown (ctrl+c), 
    for so to UnDeploy and Stop the Container. 

    This should work with all types of containers: Embedded, Managed and Remote.

    (note: Remote containers are not actually started, but Archive will be undeployed on shutdown) 

* start

    This goal will Setup and Start the Container. The Container is added to the Maven execution context for reuse by other command. 

* deploy

    This goal will deploy the given Archive defined by "filename". An Exception is thrown if start has not been called.

* undeploy

    This goal will undeploy the given Archive defined by "filename". An Exception is thrown if start has not been called.

* stop

    This goal stops the Container and cleans up the Maven context.

* deployRemote

    This goal will Setup and Start the Container and Deploy the Archive, for so to Stop the Container.

    (note: This goal only makes sense used with Remote containers)

* undeployRemote

    This goal will Setup and Start the Container and Deploy the Archive, for so to Stop the Container.

    (note: This goal only makes sense used with Remote containers)


Configuration
-------------

* target

    Parent directory to where the Archive can be found. Default value is: ${project.build.directory}/

* filename

    The Archive name as found in 'targetDir'. Default value is: ${project.build.finalName}.${project.packaging}

* classloading

    Where the plugin should find the Container libraries. 

* * TEST (default)

        Loads the projects Test Scoped ClassPath.

* * COMPILE

        Loads the projects Compile Scoped ClassPath.

* * PLUGIN

        Only search the ClassPath defined in the plugins dependencies section.


Usage
-----

```xml
<properties>
    <version.arquillian>1.0.0.CR1</version.arquillian>
    <version.arquillian_maven>1.0.0-SNAPSHOT</version.arquillian_maven>
    <version.arquillian_jetty>1.0.0.CR1</version.arquillian_jetty>
    <version.jetty>8.0.0.M3</version.jetty>
</properties>


<!-- Normal dependencies used for testing -->
<dependencies>
	<dependency>
	    <groupId>org.jboss.arquillian.junit</groupId>
	    <artifactId>arquillian-junit-container</artifactId>
	    <version>${version.arquillian}</version>
	</dependency>
</dependencies>

<build>
    <plugins>
        <plugin>
            <groupId>org.jboss.arquillian.maven</groupId>
            <artifactId>arquillian-maven-plugin</artifactId>
            <version>${version.arquillian_maven}</version>
        </plugin>
    </plugins>
</build>
<profiles>
	<profile>
		<id>jetty</id>
        <dependencies>
            <dependency>
                <groupId>org.jboss.arquillian.container</groupId>
                <artifactId>arquillian-jetty-embedded-7</artifactId>
                <version>${version.arquillian_jetty}</version>
            </dependency>
            <dependency>
                <groupId>org.eclipse.jetty</groupId>
                <artifactId>jetty-webapp</artifactId>
                <version>${version.jetty}</version>
            </dependency>
            <dependency>
                <groupId>org.eclipse.jetty</groupId>
                <artifactId>jetty-plus</artifactId>
                <version>${version.jetty}</version>
            </dependency>      
        </dependencies>
	</profile>
</profiles>
```
Call the plugin using the following command line:

```
mvn arquillian:run -Pjetty
```

The dependencies needed are the same as described in the [Reference Guide -> Complete Container Reference](https://docs.jboss.org/author/display/ARQ/Complete+Container+Reference).

See the test/ sub module for a complete setup using both Arquillian for testing the module and the Maven module for manual verification. 
