/**
 * 
 */
package org.apache.taverna.activities.externaltool.views;
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

import java.awt.event.ActionEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.apache.taverna.activities.externaltool.desc.ScriptInput;
import org.apache.taverna.activities.externaltool.desc.ScriptInputUser;

/**
 * @author alanrw
 *
 */
public class ExternalToolStringReplacementViewer {
	
	private static Pattern p = Pattern.compile("\\w+");
	private static final String PERCENTS = "%%";
	ScriptInput input;
	private JTextField nameField;
	private String name;
	private JTextField valueField;
	private JCheckBox valueFromField;

	public ExternalToolStringReplacementViewer(String name, ScriptInputUser input) {
		this(name);
		this.input = input;
		nameField.setText(name);
		if (!input.getTag().equals(name)) {
			valueFromField.setSelected(false);
			valueField.setText(PERCENTS + input.getTag() + PERCENTS);
			valueField.setEnabled(true);
		}
	}

	public ExternalToolStringReplacementViewer(String name) {
		this.name = name;
		nameField = new JTextField(20);
		nameField.setText(name);
		valueField = new JTextField(20);
		valueFromField = new JCheckBox(new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (valueFromField.isSelected()) {
					valueField.setText("");
					valueField.setEnabled(false);
				} else {
					valueField.setText(PERCENTS + getName() + PERCENTS);
					valueField.setEnabled(true);
				}
			}});
		valueFromField.setSelected(true);
		valueField.setEnabled(false);
	}

	public JTextField getNameField() {
		return nameField;
	}
	
	public JTextField getValueField() {
		return valueField;
	}

	public String getName() {
		return nameField.getText();
	}

	public String getValue() {
		if (valueFromField.isSelected()) {
			return getName();
		}
		String enteredValue = valueField.getText();

		Matcher m = p.matcher(enteredValue);
		String result = "";
		if (m.find()) {
			result = m.group();
		}
		return result;
	}

	/**
	 * @return the valueFromField
	 */
	public JCheckBox getValueFromField() {
		return valueFromField;
	}

}
