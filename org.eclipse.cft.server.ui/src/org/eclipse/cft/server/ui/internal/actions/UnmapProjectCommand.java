/*******************************************************************************
 * Copyright (c) 2015, 2016 Pivotal Software, Inc. and IBM Corporation 
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
 ********************************************************************************/
package org.eclipse.cft.server.ui.internal.actions;

import org.eclipse.cft.server.core.internal.CloudFoundryServer;
import org.eclipse.cft.server.core.internal.client.CloudFoundryApplicationModule;
import org.eclipse.cft.server.core.internal.client.ICloudFoundryOperation;
import org.eclipse.cft.server.ui.internal.Messages;

public class UnmapProjectCommand extends UpdateMappingCommand {

	protected ICloudFoundryOperation getCloudOperation(CloudFoundryApplicationModule appModule,
			CloudFoundryServer cloudServer) {
		return new UnmapProjectOperation(appModule, cloudServer);
	}

	@Override
	protected String getJobNameString() {
		return Messages.REMOVE_PROJECT_MAPPING;
	}
}
