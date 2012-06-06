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

import java.util.HashMap;
import java.util.Map;

import org.jboss.arquillian.container.spi.Container;
import org.jboss.arquillian.container.spi.client.container.DeploymentException;
import org.jboss.arquillian.container.spi.client.container.LifecycleException;
import org.jboss.arquillian.container.spi.client.deployment.Deployment;
import org.jboss.arquillian.container.spi.client.deployment.DeploymentDescription;
import org.jboss.arquillian.container.spi.client.protocol.metadata.ProtocolMetaData;
import org.jboss.arquillian.container.spi.event.DeployDeployment;
import org.jboss.arquillian.container.spi.event.SetupContainer;
import org.jboss.arquillian.container.spi.event.StartContainer;
import org.jboss.arquillian.container.spi.event.StopContainer;
import org.jboss.arquillian.container.spi.event.UnDeployDeployment;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.spi.Manager;
import org.jboss.arquillian.core.spi.NonManagedObserver;
import org.jboss.shrinkwrap.api.Archive;

/**
 * Utils
 *
 * @author <a href="mailto:aslak@redhat.com">Aslak Knutsen</a>
 * @version $Revision: $
 */
final class Utils {

    private static Map<Archive<?>, Deployment> contextMap = new HashMap<Archive<?>, Deployment>();

    private Utils() {
    }

    public static void setup(Manager manager, Container container) throws LifecycleException {
        manager.fire(new SetupContainer(container));
    }

    public static void start(Manager manager, Container container) throws LifecycleException {
        manager.fire(new StartContainer(container));
    }

    public static void stop(Manager manager, Container container) throws LifecycleException {
        manager.fire(new StopContainer(container));
    }

    public static void deploy(Manager manager, Container container, Archive<?> deployment) throws DeploymentException {
        manager.fire(new DeployDeployment(container, getOrCreateDeployment(deployment)),
                new NonManagedObserver<DeployDeployment>() {
                    @Inject
                    private Instance<ProtocolMetaData> metadataInst;

                    @Override
                    public void fired(DeployDeployment event) {
                        ProtocolMetaData metadata = metadataInst.get();
                        if (metadata != null) {
                            System.out.println(metadata);
                        }
                    }
                });
    }

    public static void undeploy(Manager manager, Container container, Archive<?> deployment) throws DeploymentException {
        manager.fire(new UnDeployDeployment(container, getOrCreateDeployment(deployment)));
    }

    private static Deployment getOrCreateDeployment(Archive<?> archive) {
        if (contextMap.containsKey(archive)) {
            return contextMap.remove(archive);
        } else {
            Deployment deployment = new Deployment(new DeploymentDescription("NO-NAME", archive));
            contextMap.put(archive, deployment);

            return deployment;
        }
    }
}
