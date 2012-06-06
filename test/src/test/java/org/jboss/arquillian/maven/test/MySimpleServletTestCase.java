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
package org.jboss.arquillian.maven.test;

import static org.jboss.arquillian.maven.test.Utils.read;

import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.jboss.shrinkwrap.descriptor.api.webapp30.WebAppDescriptor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * MySimpleServletTestCase
 *
 * @author <a href="mailto:aslak@redhat.com">Aslak Knutsen</a>
 * @version $Revision: $
 */
@RunWith(Arquillian.class)
public class MySimpleServletTestCase
{
   @Deployment(testable = false)
   public static WebArchive createDeployment()
   {
      String servletName = MySimpleServlet.class.getSimpleName();
      return ShrinkWrap.create(WebArchive.class)
               .addClass(MySimpleServlet.class)
               .setWebXML(new StringAsset(
                     Descriptors.create(WebAppDescriptor.class)
                        .createServlet()
                           .servletName(servletName)
                           .servletClass(MySimpleServlet.class.getName())
                           .up()
                        .createServletMapping()
                           .servletName(servletName)
                           .urlPattern("/*")
                           .up()
                        .exportAsString()
               ));
   }
   
   @Test
   public void shouldBeAbleToCallServlet(@ArquillianResource(MySimpleServlet.class) URL baseURL) throws Exception 
   {
      String result = read(new URL(baseURL + "Test"));
      Assert.assertEquals(MySimpleServlet.WELCOME_MSG, result);
   }
}
