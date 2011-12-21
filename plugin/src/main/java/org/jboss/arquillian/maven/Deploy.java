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

import org.jboss.arquillian.container.spi.Container;
import org.jboss.arquillian.container.spi.client.container.DeploymentException;
import org.jboss.arquillian.core.spi.Manager;
import org.jboss.shrinkwrap.api.Archive;

/**
 * Deploy to a Container
 *
 * @goal deploy
 * 
 * @author <a href="mailto:aslak@redhat.com">Aslak Knutsen</a>
 * @version $Revision: $
 * 
 */
public final class Deploy extends BaseCommand
{
   /* (non-Javadoc)
    * @see org.jboss.arquillian.maven.BaseCommand#goal()
    */
   @Override
   public String goal()
   {
      return "deploy";
   }

   /* (non-Javadoc)
    * @see org.jboss.arquillian.maven.BaseCommand#perform(org.jboss.arquillian.core.spi.Manager, org.jboss.arquillian.container.spi.Container, org.jboss.shrinkwrap.api.Archive)
    */
   @Override
   public void perform(Manager manager, Container container, Archive<?> deployment) throws DeploymentException
   {
      getLog().info("Perform deploy on " + container.getName() + " of deployment " + deployment.getName());
      Utils.deploy(manager, container, deployment);
   }

   /* (non-Javadoc)
    * @see org.jboss.arquillian.maven.BaseCommand#stopContainer(org.jboss.arquillian.core.spi.Manager, org.jboss.arquillian.container.spi.Container)
    */
   @Override
   void stopContainer(Manager manager, Container container)
   {
      // Don't stop the container
   }
   
   /* (non-Javadoc)
    * @see org.jboss.arquillian.maven.BaseCommand#shutDownManager(org.jboss.arquillian.core.spi.Manager)
    */
   @Override
   void shutDownManager(Manager manager)
   {
      // Don't shutdown the manager
   }
}
