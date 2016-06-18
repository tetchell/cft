/*******************************************************************************
 * Copyright (c) 2016 Pivotal Software, Inc. and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution. 
 * 
 * The Eclipse Public License is available at 
 * 
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * and the Apache License v2.0 is available at 
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * You may elect to redistribute this code under either of these licenses.
 *  
 *  Contributors:
 *     Pivotal Software, Inc. - initial API and implementation
 ********************************************************************************/
package org.eclipse.cft.server.core.internal.jrebel;

import org.eclipse.cft.server.core.internal.CloudFoundryPlugin;
import org.eclipse.cft.server.core.internal.CloudServerEvent;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.wst.server.core.IModule;
import org.osgi.framework.Bundle;

/**
 * 
 * Utility that contains references to hardcoded JRebel IDE constants used for
 * the actual integration into CFT, as to contain all such occurrences in one
 * location
 *
 */
public class JRebelIntegrationUtility {

	public static boolean isJRebelEnabled(IModule module) {
		IProject project = module != null ? module.getProject() : null;

		return project != null && project.isAccessible()
				&& (hasNature(project, "org.zeroturnaround.eclipse.remoting.remotingNature") //$NON-NLS-1$
						|| hasNature(project, "org.zeroturnaround.eclipse.remotingNature")) //$NON-NLS-1$
				&& hasNature(project, "org.zeroturnaround.eclipse.jrebelNature"); //$NON-NLS-1$
	}

	public static boolean hasNature(IProject project, String nature) {
		try {
			return project.hasNature(nature);
		}
		catch (CoreException e) {
			CloudFoundryPlugin.logError(e);
		}
		return false;
	}

	public static void setAutoGeneratedXMLDisabledProperty(IProject project) throws CoreException {
		project.setPersistentProperty(new QualifiedName("org.zeroturnaround.eclipse.jrebel", //$NON-NLS-1$
				"autoGenerateRebelXml"), "false"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * 
	 * @return true if JRebel bundle is found. False otherwise
	 */
	public static Bundle getJRebelBundle() {
		Bundle bundle = null;
		try {
			bundle = Platform.getBundle("org.zeroturnaround.eclipse"); //$NON-NLS-1$
		}
		catch (Throwable e) {
			CloudFoundryPlugin.logError(e);
		}

		return bundle;
	}

	/**
	 * 
	 * @return true if JRebel is installed in Eclipse. False otherwise.
	 */
	public static boolean isJRebelIDEInstalled() {
		return getJRebelBundle() != null;
	}

	public static boolean isRemotingProject(Object remoteProjectObj) {
		return remoteProjectObj != null && remoteProjectObj.getClass().getName()
				.equals("org.zeroturnaround.eclipse.jrebel.remoting.RemotingProject"); //$NON-NLS-1$
	}

	public static ReflectionHandler createReflectionHandler() {
		return new ReflectionHandler();
	}

	public static boolean shouldReplaceRemotingUrl(int eventType) {
		return eventType == CloudServerEvent.EVENT_APP_URL_CHANGED;
	}
}
