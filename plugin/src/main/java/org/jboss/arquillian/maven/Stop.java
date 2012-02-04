/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012 Red Hat Inc. and/or its affiliates and other contributors
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
import org.jboss.arquillian.core.spi.Manager;

/**
 * Stop a Container
 *
 * @goal stop
 *
 * @author Davide D'Alto
 * @version $Revision: $
 *
 */
public final class Stop extends BaseCommand
{
   /* (non-Javadoc)
    * @see org.jboss.arquillian.maven.BaseCommand#goal()
    */
   @Override
   public String goal()
   {
      return "stop";
   }

   /* (non-Javadoc)
    * @see org.jboss.arquillian.maven.BaseCommand#validateInput()
    */
   @Override
   void validateInput()
   {
      // No need to validate when stopping
   }

   /* (non-Javadoc)
    * @see org.jboss.arquillian.maven.BaseCommand#perform(org.jboss.arquillian.core.spi.Manager, org.jboss.arquillian.container.spi.Container)
    */
   @Override
   public void perform(Manager manager, Container container)
   {
      execute(manager, container);
   }

   static void execute(Manager manager, Container container)
   {
      try
      {
         Utils.stop(manager, container);
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      finally
      {
         manager.shutdown();
      }
   }
}
