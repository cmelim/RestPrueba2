package cl.corfo.ee.bpmsapi.domain;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.deltaspike.core.api.config.ConfigProperty;

import org.kie.api.runtime.manager.audit.ProcessInstanceLog;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.audit.AuditService;
import org.kie.api.runtime.manager.audit.VariableInstanceLog;
import org.kie.api.runtime.process.NodeInstance;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.TaskService;
import org.kie.api.task.model.Task;
import org.kie.api.task.model.TaskSummary;
import org.kie.remote.client.api.RemoteRestRuntimeEngineFactory;
import org.kie.remote.jaxb.gen.FindVariableInstancesCommand;
import org.kie.services.client.api.RemoteRuntimeEngineFactory;
import org.kie.services.client.api.command.RemoteRuntimeEngine;
import org.kie.services.client.serialization.jaxb.impl.task.JaxbTaskSummary;

/**
 * @author psep
 *
 */
@Named("jBPMClient")
public class JBPMClientImpl implements JBPMClient {
	
	private static final String LOCALE = "en-UK";

	@Inject
	@ConfigProperty(name = "bpms.hostname")
	private String bpmUrl;

	public void completeTask(String deploymentId, long taskId, String userId,
			String passwd, Map<String, Object> params)
			throws MalformedURLException {
		this.startTask(deploymentId, taskId, userId, passwd);
		this.endTask(deploymentId, taskId, userId, passwd, params);
	}

	public void startTask(String deploymentId, long taskId, String userId,
			String passwd) throws MalformedURLException {
		TaskService taskService = getTaskService(deploymentId, userId, passwd);
		taskService.start(taskId, userId);
	}

	public void endTask(String deploymentId, long taskId, String userId,
			String passwd, Map<String, Object> params)
			throws MalformedURLException {
		TaskService taskService = getTaskService(deploymentId, userId, passwd);
		taskService.complete(taskId, userId, params);
	}

	/**
	 * @param deploymentId
	 * @return
	 * @throws MalformedURLException
	 */
	private TaskService getTaskService(String deploymentId, String userId,
			String passwd) throws MalformedURLException {
		RuntimeEngine engine = getRuntimeEngine(deploymentId, userId, passwd);
		return engine.getTaskService();
	}

	/**
	 * @param deploymentId
	 * @return
	 * @throws MalformedURLException
	 */
	private AuditService getAuditService(String deploymentId, String userId,
			String passwd) throws MalformedURLException {
		RuntimeEngine engine = this.getRuntimeEngine(deploymentId, userId,
				passwd);
		return engine.getAuditService();
	}

	public ProcessInstance startProcess(String deploymentId, String processDef,
			String userId, String passwd, Map<String, Object> params)
			throws MalformedURLException {
		return this.initProcess(deploymentId, processDef, userId, passwd,
				params);
	}

	public ProcessInstance startProcess(String deploymentId, String processDef,
			String userId, String passwd) throws MalformedURLException {
		return this.initProcess(deploymentId, processDef, userId, passwd, null);
	}

	/**
	 * @param deploymentId
	 * @param processDef
	 * @param params
	 * @return
	 * @throws MalformedURLException
	 */
	private ProcessInstance initProcess(String deploymentId, String processDef,
			String userId, String passwd, Map<String, Object> params)
			throws MalformedURLException {
		KieSession ksession = getRuntimeEngine(deploymentId, userId, passwd)
				.getKieSession();
		ProcessInstance processInstance = null;

		if (params == null) {
			processInstance = ksession.startProcess(processDef);
		} else {
			processInstance = ksession.startProcess(processDef, params);
		}

		return processInstance;
	}

	/**
	 * @param deploymentId
	 * @return
	 * @throws MalformedURLException
	 */
	private RuntimeEngine getRuntimeEngine(String deploymentId, String userId,
			String passwd) throws MalformedURLException {
		//URL baseUrl = new URL(this.bpmUrl);
		URL baseUrl = new URL("http://localhost:8080/business-central/");
		RuntimeEngine engine = RemoteRuntimeEngineFactory.newRestBuilder()
				.addUrl(baseUrl).addUserName(userId).addPassword(passwd)
				.addDeploymentId(deploymentId).build();

		return engine;
	}

	public List<TaskSummary> listTasksByPotencialOwner(String deploymentId,
			String userId, String passwd) throws MalformedURLException {
		List<TaskSummary> tasks = this.getTaskService(deploymentId, userId,
				passwd).getTasksAssignedAsPotentialOwner(userId, LOCALE);
		return tasks;
	}

	public String getVariableFromProcess(String deploymentId, String userId,
			String passwd, long processInstanceId, String variableName)
			throws Exception {
		AuditService auditService = this.getAuditService(deploymentId, userId, passwd);
		List<? extends VariableInstanceLog> logs = auditService
				.findVariableInstances(processInstanceId, variableName);

		if (logs == null || logs.size() == 0) {
			throw new Exception("Process instance id " + processInstanceId
					+ " no fue encontrado");
		}
		
		VariableInstanceLog log = logs.get(logs.size() - 1);

		return log.getValue();
	}
	
	@Override
	public List<? extends ProcessInstanceLog> getAllProcess(String deploymentId, String userId,
			String passwd) throws Exception {
		AuditService auditService = this.getAuditService(deploymentId, userId, passwd);
		return auditService.findProcessInstances();
	}
	
}
