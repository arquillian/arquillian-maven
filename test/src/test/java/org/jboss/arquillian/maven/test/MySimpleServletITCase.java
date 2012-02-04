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

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import junit.framework.Assert;

import org.junit.Test;

/**
 * MySimpleServletITCase
 *
 * @author Davide D'Alto
 * @version $Revision: $
 */
public class MySimpleServletITCase
{
   @Test
   public void shouldBeAbleToCallServlet() throws Exception 
   {
      String url = "http://127.0.0.1:" + servletPort() + "/arquillian-maven/hello";
      String result = read(new URL(url));
      Assert.assertEquals(MySimpleServlet.WELCOME_MSG, result);
   }

   private String servletPort() throws IOException
   {
      Properties properties = new Properties();
      properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("test.properties"));
      return properties.getProperty("servlet.port");
   }
}
