/**
 * 
 */
package org.apache.taverna.activities.externaltool.manager.local;
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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.taverna.activities.externaltool.local.ExternalToolLocalInvocationMechanism;
import org.apache.taverna.activities.externaltool.manager.InvocationMechanism;
import org.apache.taverna.activities.externaltool.manager.InvocationMechanismEditor;

/**
 * @author alanrw
 *
 */
public final class LocalInvocationMechanismEditor extends
		InvocationMechanismEditor<ExternalToolLocalInvocationMechanism> {

	private ExternalToolLocalInvocationMechanism invocationMechanism;
	
	private JTextField directoryField = new JTextField(30);
	
	private JTextField shellPrefixField = new JTextField(30);
	
	private JTextField linkCommandField = new JTextField(30);
	
	private JCheckBox retrieveDataField = new JCheckBox();
	

	@Override
	public boolean canShow(Class<?> c) {
		return ExternalToolLocalInvocationMechanism.class.isAssignableFrom(c);
	}

	@Override
	public String getName() {
		return ("Local");
	}

	@Override
	public void show(ExternalToolLocalInvocationMechanism invocationMechanism) {
		this.invocationMechanism = invocationMechanism;
		this.removeAll();
		final JPanel innerPanel = new JPanel(new GridBagLayout());
		final GridBagConstraints inputConstraint = new GridBagConstraints();
//		inputConstraint.insets = new Insets(5,5,5,5);
		inputConstraint.anchor = GridBagConstraints.FIRST_LINE_START;
		inputConstraint.gridx = 0;
		inputConstraint.gridy = 0;
		inputConstraint.weightx = 0.1;
		inputConstraint.fill = GridBagConstraints.BOTH;
		innerPanel.add(new JLabel("Working directory: "), inputConstraint);
		inputConstraint.gridx++;
		directoryField.setText(invocationMechanism.getDirectory());
		innerPanel.add(directoryField, inputConstraint);
		inputConstraint.gridx = 0;
		inputConstraint.gridy++;
		innerPanel.add(new JLabel("Shell: "), inputConstraint);
		inputConstraint.gridx++;
		shellPrefixField.setText(invocationMechanism.getShellPrefix());
		innerPanel.add(shellPrefixField, inputConstraint);
		
		inputConstraint.gridx = 0;
		inputConstraint.gridy++;
		innerPanel.add(new JLabel("Link command: "), inputConstraint);
		inputConstraint.gridx++;
		linkCommandField.setText(invocationMechanism.getLinkCommand());
		innerPanel.add(linkCommandField, inputConstraint);
		
		inputConstraint.gridx = 0;
		inputConstraint.gridy++;
		innerPanel.add(new JLabel("Fetch data: "), inputConstraint);
		inputConstraint.gridx++;
		retrieveDataField.setSelected(invocationMechanism.isRetrieveData());
		innerPanel.add(retrieveDataField, inputConstraint);
		
		this.add(innerPanel);
	}

	@Override
	public ExternalToolLocalInvocationMechanism updateInvocationMechanism() {
		if ((directoryField.getText() == null) || (directoryField.getText().length() == 0)) {
			invocationMechanism.setDirectory(null);
		} else {
			invocationMechanism.setDirectory(directoryField.getText());
		}
		if ((shellPrefixField.getText() == null) || (shellPrefixField.getText().length() == 0)) {
			invocationMechanism.setShellPrefix(null);
		} else {
			invocationMechanism.setShellPrefix(shellPrefixField.getText());
		}
		if ((shellPrefixField.getText() == null) || (shellPrefixField.getText().length() == 0)) {
			invocationMechanism.setShellPrefix(null);
		} else {
			invocationMechanism.setShellPrefix(shellPrefixField.getText());
		}
		if ((linkCommandField.getText() == null) || (linkCommandField.getText().length() == 0)) {
			invocationMechanism.setLinkCommand(null);
		} else {
			invocationMechanism.setLinkCommand(linkCommandField.getText());
		}
		invocationMechanism.setRetrieveData(retrieveDataField.isSelected());
		return invocationMechanism;
	}

	@Override
	public InvocationMechanism createMechanism(String mechanismName) {
		ExternalToolLocalInvocationMechanism result = new ExternalToolLocalInvocationMechanism();
		result.setName(mechanismName);
		return(result);
	}

	public boolean isSingleton() {
		return true;
	}
}
