/*******************************************************************************
 ******************************************************************************/
package org.apache.taverna.activities.externaltool.manager;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import org.apache.taverna.activities.externaltool.manager.InvocationGroupManager;
import org.apache.taverna.activities.externaltool.manager.InvocationMechanism;

import org.apache.taverna.activities.externaltool.manager.impl.InvocationGroupManagerImpl;

/**
 * UI for creating/editing dataflow input ports.
 *
 * @author David Withers
 */
public class GroupPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextField groupNameField;

	private JComboBox mechanismComboBox;

	private static InvocationGroupManager manager = InvocationGroupManagerImpl.getInstance();

	public GroupPanel(Object[] mechanisms) {
		super(new GridBagLayout());

		groupNameField = new JTextField();


		setBorder(new EmptyBorder(10, 10, 10, 10));

		GridBagConstraints constraints = new GridBagConstraints();

		constraints.anchor = GridBagConstraints.WEST;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.ipadx = 10;
		add(new JLabel("Name:"), constraints);

		constraints.gridx = 1;
		constraints.gridwidth = 2;
		constraints.ipadx = 0;
		constraints.weightx = 1d;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		add(groupNameField, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.weightx = 0d;
		constraints.fill = GridBagConstraints.NONE;
		constraints.ipadx = 10;
		constraints.insets = new Insets(10, 0, 0, 0);
		add(new JLabel("Explicit location:"), constraints);

		mechanismComboBox = new JComboBox(mechanisms);
		mechanismComboBox.setSelectedItem(manager.getDefaultMechanism());

		constraints.gridx = 1;
		constraints.gridwidth = 2;
		constraints.ipadx = 0;
		add(mechanismComboBox, constraints);
	}

	/**
	 * Returns the portNameField.
	 *
	 * @return the portNameField
	 */
	public JTextField getGroupNameField() {
		return groupNameField;
	}

	/**
	 * Returns the group name.
	 *
	 * @return the group name
	 */
	public String getGroupName() {
		return groupNameField.getText();
	}

	public InvocationMechanism getSelectedMechanism() {
		return (InvocationMechanism) mechanismComboBox.getSelectedItem();
	}

}
