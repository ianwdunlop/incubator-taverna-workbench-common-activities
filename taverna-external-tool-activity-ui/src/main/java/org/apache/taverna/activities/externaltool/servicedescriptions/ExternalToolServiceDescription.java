/*******************************************************************************
 ******************************************************************************/

package org.apache.taverna.activities.externaltool.servicedescriptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.apache.log4j.Logger;

import org.apache.taverna.activities.externaltool.ExternalToolActivity;
import org.apache.taverna.activities.externaltool.ExternalToolActivityConfigurationBean;
import org.apache.taverna.activities.externaltool.manager.InvocationGroupManager;
import org.apache.taverna.activities.externaltool.manager.impl.InvocationGroupManagerImpl;
import org.apache.taverna.servicedescriptions.ServiceDescription;
import org.apache.taverna.workflowmodel.processor.activity.Activity;
import org.apache.taverna.activities.externaltool.desc.ToolDescription;

/**
 * ExternalToolServiceDescription stores the repository URL and the use case id so
 * that it can create an ExternalToolActivityConfigurationBean
 * 
 * @author Hajo Nils Krabbenhoeft
 */
public class ExternalToolServiceDescription extends ServiceDescription<ExternalToolActivityConfigurationBean> {
	
	private static Logger logger = Logger
	.getLogger(ExternalToolServiceDescription.class);

	
	private static InvocationGroupManager manager = InvocationGroupManagerImpl.getInstance();

	private String repositoryUrl;
	private String externaltoolid;
	private ToolDescription useCaseDescription;

	public String getRepositoryUrl() {
		return repositoryUrl;
	}

	public void setRepositoryUrl(String repositoryUrl) {
		this.repositoryUrl = repositoryUrl;
	}

	public String getExternaltoolid() {
		return externaltoolid;
	}

	public void setExternaltoolid(String externaltoolid) {
		this.externaltoolid = externaltoolid;
	}

	public Icon getIcon() {
		if (useCaseDescription != null) {
			String icon_url = useCaseDescription.getIcon_url();
			if ((icon_url != null) && !icon_url.isEmpty() && !icon_url.endsWith(".ico"))
				try {
					ImageIcon result = new ImageIcon(new URL(icon_url));
					if ((result != null) && (result.getIconHeight() != 0) && (result.getIconWidth() != 0)){
						return result;
					}
				} catch (MalformedURLException e) {
					logger.error("Problematic URL" + icon_url, e);
				}
		}
		return ExternalToolActivityIcon.getExternalToolIcon();
	}

	public Class<? extends Activity<ExternalToolActivityConfigurationBean>> getActivityClass() {
		return ExternalToolActivity.class;
	}

	public ExternalToolActivityConfigurationBean getActivityConfiguration() {
		ExternalToolActivityConfigurationBean bean = new ExternalToolActivityConfigurationBean();
		bean.setRepositoryUrl(repositoryUrl);
		bean.setExternaltoolid(externaltoolid);
		bean.setToolDescription(useCaseDescription);
		bean.setMechanism(manager.getDefaultMechanism());

		return bean;
	}

	public String getName() {
		return externaltoolid;
	}

	@SuppressWarnings("unchecked")
	public List<? extends Comparable> getPath() {
		List<String> result = new ArrayList<String>();
		result.add("Tools decribed @ " + repositoryUrl);
		String group = useCaseDescription.getGroup();
		if ((group != null) && !group.isEmpty()) {
			String[] groups = group.split(":");
			for (String g : groups) {
				result.add(g);
			}
		}
		return result;
	}

	protected List<Object> getIdentifyingData() {
		// we require use cases inside one XML file to have unique IDs, which
		// means every externaltool is uniquely identified by its repository URL and
		// its use case ID.
		return Arrays.<Object> asList(repositoryUrl, externaltoolid);
	}
	
	public String getDescription() {
		if (useCaseDescription != null) {
			String description = useCaseDescription.getDescription();
			if (description == null) {
				return "";
			}
			return description;
		}
		return "";
	}

	public void setToolDescription(UseCaseDescription usecase) {
		this.useCaseDescription = usecase;
	}

}
