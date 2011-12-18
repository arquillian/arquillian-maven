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

import org.apache.maven.plugin.testing.AbstractMojoTestCase;

/**
 * @author Davide D'Alto
 */
abstract class BaseCommandTestBase extends AbstractMojoTestCase
{

   private static final String ARQUILLIAN_XML_SYS_PROP = "arquillian.xml";

   public void testArquillianXmlIsNullWhenNotSet() throws Exception
   {
      BaseCommand baseCommand = (BaseCommand) lookupMojo(goal(), pomFile("empty-configuration-pom.xml"));
      assertNull(baseCommand.arquillianXml());
   }

   public void testArquillianXmlWhenIsSet() throws Exception
   {
      String savedValue = System.getProperty(ARQUILLIAN_XML_SYS_PROP);

      BaseCommand baseCommand = (BaseCommand) lookupMojo(goal(), pomFile("arquillianXml4test-pom.xml"));
      assertNotNull(baseCommand.arquillianXml());
      assertEquals("Unexpected value for arquillian.xml system property",
            "arquillianXml4test.xml",
            System.getProperty(ARQUILLIAN_XML_SYS_PROP));

      System.setProperty(ARQUILLIAN_XML_SYS_PROP, savedValue);
   }

   public void testArquillianXmlInitialization() throws Exception
   {
      BaseCommand baseCommand = (BaseCommand) lookupMojo(goal(), pomFile("arquillianXml4test-pom.xml"));
      baseCommand.initArquillianXml();

      assertEquals("Unexpected value for arquillianXml property", "arquillianXml4test.xml", baseCommand.arquillianXml());
   }

   private File pomFile(String pomFileName)
   {
      File pom = getTestFile("src/test/resources/" + pomFileName);
      assertNotNull(pom);
      assertTrue(pom.exists());
      return pom;
   }

   abstract String goal();

}
