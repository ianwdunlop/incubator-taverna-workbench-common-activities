package org.apache.taverna.activities.localworker.actions;
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.awt.Frame;
import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JOptionPane;

import org.apache.taverna.activities.localworker.views.LocalworkerActivityConfigView;
import org.apache.taverna.servicedescriptions.ServiceDescriptionRegistry;
import org.apache.taverna.workbench.activityicons.ActivityIconManager;
import org.apache.taverna.workbench.edits.EditManager;
import org.apache.taverna.workbench.file.FileManager;
import org.apache.taverna.workbench.ui.actions.activity.ActivityConfigurationAction;
import org.apache.taverna.workbench.ui.views.contextualviews.activity.ActivityConfigurationDialog;
import org.apache.taverna.configuration.app.ApplicationConfiguration;
import org.apache.taverna.scufl2.api.activity.Activity;
import org.apache.taverna.scufl2.api.common.Scufl2Tools;
import org.apache.taverna.scufl2.api.configurations.Configuration;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * The {@link LocalworkerActivity}s have pre-defined scripts, ports etc in a serialised form on
 * disk. So if the user wants to change them they have to do so at own risk.
 *
 * @author Ian Dunlop
 */
@SuppressWarnings("serial")
public class LocalworkerActivityConfigurationAction extends ActivityConfigurationAction {

	public static final String EDIT_LOCALWORKER_SCRIPT = "Edit beanshell script";

	private final EditManager editManager;

	private final FileManager fileManager;

	private final ApplicationConfiguration applicationConfiguration;

	private Scufl2Tools scufl2Tools = new Scufl2Tools();

	public LocalworkerActivityConfigurationAction(Activity activity, Frame owner,
			EditManager editManager, FileManager fileManager,
			ActivityIconManager activityIconManager,
			ServiceDescriptionRegistry serviceDescriptionRegistry,
			ApplicationConfiguration applicationConfiguration) {
		super(activity, activityIconManager, serviceDescriptionRegistry);
		this.editManager = editManager;
		this.fileManager = fileManager;
		this.applicationConfiguration = applicationConfiguration;
		putValue(Action.NAME, EDIT_LOCALWORKER_SCRIPT);
	}

	/**
	 * If the localworker has not been changed it pops up a {@link JOptionPane} warning the user
	 * that they change things at their own risk. Otherwise just show the config view
	 */
	public void actionPerformed(ActionEvent e) {
		Object[] options = { "Continue", "Cancel" };
		Configuration configuration = scufl2Tools.configurationFor(activity, activity.getParent());
		JsonNode json = configuration.getJson();
		if (!json.get("isAltered").booleanValue()) {
			int n = JOptionPane
					.showOptionDialog(
							null,
							"Changing the properties of a Local Worker may affect its behaviour. Do you want to continue?",
							"WARNING", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
							null, // do not use a
							// custom Icon
							options, options[0]);

			if (n == 0) {
				// continue was clicked so prepare for config
				openDialog();
			} else {
				// do nothing
			}
		} else {
			openDialog();
		}
	}

	private void openDialog() {
		ActivityConfigurationDialog currentDialog = ActivityConfigurationAction
				.getDialog(getActivity());
		if (currentDialog != null) {
			currentDialog.toFront();
			return;
		}
		final LocalworkerActivityConfigView localworkerConfigView = new LocalworkerActivityConfigView(
				getActivity(), applicationConfiguration);
		final ActivityConfigurationDialog dialog = new ActivityConfigurationDialog(getActivity(),
				localworkerConfigView, editManager);
		ActivityConfigurationAction.setDialog(getActivity(), dialog, fileManager);

	}
}
