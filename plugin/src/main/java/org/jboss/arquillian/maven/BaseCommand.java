/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.arquillian.maven;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.jboss.arquillian.container.spi.Container;
import org.jboss.arquillian.container.spi.ContainerRegistry;
import org.jboss.arquillian.container.spi.client.container.DeploymentException;
import org.jboss.arquillian.container.spi.client.deployment.TargetDescription;
import org.jboss.arquillian.core.impl.loadable.LoadableExtensionLoader;
import org.jboss.arquillian.core.spi.Manager;
import org.jboss.arquillian.core.spi.ManagerBuilder;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;

/**
 * BaseCommand
 * 
 * @author <a href="mailto:aslak@redhat.com">Aslak Knutsen</a>
 * @version $Revision: $
 * 
 * @requiresDependencyResolution runtime
 */
abstract class BaseCommand extends AbstractMojo
{
   /**
    * The target directory the application to be deployed is located.
    *
    * @parameter expression="${echo.target}" default-value="${project.build.directory}/"
    */
   private File targetDir;

   /**
    * The file name of the application to be deployed.
    *
    * @parameter expression="${echo.filename}" default-value="${project.build.finalName}.${project.packaging}"
    */
   private String filename;

   /**
    * The target directory the archive is located. The default is {@code project.build.directory}.
    *
    * @return the target directory the archive is located.
    */
   public final File targetDirectory() {
       return targetDir;
   }

   /**
    * The file name of the archive not including the directory. The default is
    * {@code project.build.finalName + . + project.packaging}
    *
    * @return the file name of the archive.
    */
   public final String filename() {
       return filename;
   }

   /**
    * The archive file.
    *
    * @return the archive file.
    */
   public final File file() {
       return new File(targetDir, filename);
   }

   /**
    * The goal of the deployment.
    *
    * @return the goal of the deployment.
    */
   public abstract String goal();

   /**
    * Perform the defined goal, e.g. deploy / run / undeploy
    * 
    * @param container The chosen container to operate on
    * @param deployment The deployment to operate on
    */
   public abstract void perform(Manager manager, Container container, Archive<?> deployment) throws DeploymentException;
   
   /* (non-Javadoc)
    * @see org.apache.maven.plugin.Mojo#execute()
    */
   @Override
   public void execute() throws MojoExecutionException, MojoFailureException
   {
      File deploymentFile = file();
      
      if(!deploymentFile.exists())
      {
         throw new IllegalArgumentException("Specified file does not exist:" + deploymentFile + ". Verify 'target' and 'filename' configuration.");
      }
      
      getLog().info(goal() + " file: " + deploymentFile.getAbsoluteFile());
      
      Manager manager = ManagerBuilder.from()
               .extension(LoadableExtensionLoader.class)
               .create();

      manager.start();
      
      GenericArchive deployment = ShrinkWrap.create(ZipImporter.class, deploymentFile.getName())
                                       .importFrom(deploymentFile)
                                       .as(GenericArchive.class);
      
      ContainerRegistry registry = manager.resolve(ContainerRegistry.class);
      
      if(registry == null)
      {
         throw new IllegalStateException("No ContainerRegistry found in Context. Something is wrong with the classpath.....");
      }
      
      if(registry.getContainers().size() == 0)
      {
         throw new IllegalStateException("No Containers in registry. You need to add the Container Adaptor dependencies to the plugin dependency section");
      }
      
      // TODO: Add support for multi configuration and arquillian.xml selection
      Container container = registry.getContainer(TargetDescription.DEFAULT);
      
      getLog().info("to container: " + container.getName());
      
      try
      {
         Utils.setup(manager, container);
         Utils.start(manager, container);
         
         perform(manager, container, deployment);
      }
      catch (Exception e) 
      {
         throw new MojoExecutionException("Could not perform:" + goal(), e);
      }
      finally 
      {
         try
         {
            Utils.stop(manager, container);
         }
         catch (Exception e) 
         {
            e.printStackTrace();
         }
         manager.shutdown();
      }
   }
}
