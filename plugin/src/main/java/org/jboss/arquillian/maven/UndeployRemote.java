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
import org.jboss.arquillian.container.spi.client.container.DeploymentException;
import org.jboss.arquillian.container.spi.client.container.LifecycleException;
import org.jboss.arquillian.core.spi.Manager;
import org.jboss.shrinkwrap.api.Archive;

/**
 * UnDeploy to a remote container. Start and stop the container before and after the undeploy.
 *
 * @goal undeployRemote
 *
 * @author Davide D'Alto
 * @version $Revision: $
 *
 */
public final class UndeployRemote extends BaseCommand {

    private boolean managerStartedByMe = false;

    /*
     * (non-Javadoc)
     *
     * @see org.jboss.arquillian.maven.BaseCommand#goal()
     */
    @Override
    public String goal() {
        return "undeployRemote";
    }

    Manager startNewManager(Class<?>... extensions) {
        managerStartedByMe = true;
        return super.startNewManager(extensions);
    };

    /*
     * (non-Javadoc)
     *
     * @see org.jboss.arquillian.maven.BaseCommand#perform(org.jboss.arquillian.core.spi.Manager,
     * org.jboss.arquillian.container.spi.Container, org.jboss.shrinkwrap.api.Archive)
     */
    @Override
    public void perform(final Manager manager, final Container container) throws DeploymentException, LifecycleException {
        if (managerStartedByMe)
            Start.execute(manager, container);

        undeploy(manager, container);

        Stop.execute(manager, container);
    }

    private void undeploy(Manager manager, Container container) throws DeploymentException {
        final Archive<?> deployment = createDeployment();
        getLog().info("Perform undeploy on " + container.getName() + " of deployment " + deployment.getName());
        Undeploy.execute(manager, container, deployment);
    }
}
