Arquillian Maven Plugin
=======================

A Maven plugin with support for deploy/undeploy and run operations using the Arquillian Containers.


Operations
----------

* run
Similar to the famous jetty:run maven plugin. This goal will Setup and Start the Container, Deploy the given Archive, then wait for a JVM shutdown (ctrl+c), 
for so to UnDeploy and Stop the Container. 

This should work with all types of containers: Embedded, Managed and Remote.

(note: Remote containers are not actually started, but Archive will be undeployed on shutdown) 

* deploy

This goal will Setup and Start the Container and Deploy the Archive, for so to Stop the Container.

(note: This goal makes most sense used with Remote containers)

* undeploy

This goal will Setup and Start the Container and Deploy the Archive, for so to Stop the Container.

(note: This goal makes most sense used with Remote containers)


Configuration
-------------

* targetDir

Parent directory to where the Archive can be found. Default value is: ${project.build.directory}/

* filename

The Archive name as found in 'targetDir'. Default value is: ${project.build.finalName}.${project.packaging}


Usage
-----

```xml
<properties>
    <version.arquillian_maven>1.0.0-SNAPSHOT</version.arquillian_maven>
    <version.arquillian_jetty>1.0.0.CR1</version.arquillian_jetty>
    <version.jetty>8.0.0.M3</version.jetty>
</properties>

<build>
    <plugins>
        <plugin>
            <groupId>org.jboss.arquillian.maven</groupId>
            <artifactId>arquillian-maven-plugin</artifactId>
            <version>${version.arquillian_maven}</version>
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
        </plugin>
    </plugins>
</build>
```

The dependencies needed are the same as described in the [Reference Guide -> Complete Container Reference](https://docs.jboss.org/author/display/ARQ/Complete+Container+Reference).

See the test/ sub module for a complete setup. 