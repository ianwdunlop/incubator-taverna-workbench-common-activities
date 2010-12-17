/**
 * 
 */
package net.sf.taverna.t2.activities.externaltool.servicedescriptions;

import java.net.URI;

import javax.swing.Icon;

import net.sf.taverna.t2.activities.externaltool.AdHocExternalToolActivityConfigurationBean;
import net.sf.taverna.t2.activities.externaltool.ExternalToolActivityConfigurationBean;
import net.sf.taverna.t2.servicedescriptions.AbstractTemplateService;
import net.sf.taverna.t2.servicedescriptions.ServiceDescription;
import net.sf.taverna.t2.workflowmodel.processor.activity.Activity;
import net.sf.taverna.t2.activities.externaltool.ExternalToolActivity;

/**
 * @author alanrw
 *
 */
public class ExternalToolTemplateServiceDescription extends
		AbstractTemplateService<ExternalToolActivityConfigurationBean> {
	
	private static final URI providerId = URI
	.create("http://taverna.sf.net/2010/service-provider/external-tool");
	
	private static final String EXTERNAL_TOOL = "ExternalTool";


	@Override
	public Class<? extends Activity<ExternalToolActivityConfigurationBean>> getActivityClass() {
		return ExternalToolActivity.class;
	}

	@Override
	public AdHocExternalToolActivityConfigurationBean getActivityConfiguration() {
		return new AdHocExternalToolActivityConfigurationBean();
	}

	@Override
	public Icon getIcon() {
		return ExternalToolActivityIcon.getExternalToolIcon();
	}
	
	@Override
	public String getDescription() {
		return "A service that allows tools to be used as services";	
	}
	
	@SuppressWarnings("unchecked")
	public static ServiceDescription getServiceDescription() {
		ExternalToolTemplateServiceDescription bts = new ExternalToolTemplateServiceDescription();
		return bts.templateService;
	}



	@Override
	public String getId() {
		return providerId.toString();
	}

	@Override
	public String getName() {
		return EXTERNAL_TOOL;
	}

}