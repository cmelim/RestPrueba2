package cl.corfo.ee.bpmsapi.domain;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.kie.api.runtime.manager.audit.ProcessInstanceLog;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.model.TaskSummary;

/**
 * @author psep
 *
 */
public interface JBPMClient {

	/**
	 * @param deploymentId
	 * @param taskId
	 * @param userId
	 * @param passwd
	 * @param params
	 * @throws MalformedURLException
	 */
	public void completeTask(String deploymentId, long taskId, String userId,
			String passwd, Map<String, Object> params)
			throws MalformedURLException;

	/**
	 * @param deploymentId
	 * @param taskId
	 * @param userId
	 * @param passwd
	 * @throws MalformedURLException
	 */
	public void startTask(String deploymentId, long taskId, String userId,
			String passwd) throws MalformedURLException;

	/**
	 * @param deploymentId
	 * @param taskId
	 * @param userId
	 * @param passwd
	 * @param params
	 * @throws MalformedURLException
	 */
	public void endTask(String deploymentId, long taskId, String userId,
			String passwd, Map<String, Object> params)
			throws MalformedURLException;

	/**
	 * @param deploymentId
	 * @param processDef
	 * @param userId
	 * @param passwd
	 * @param params
	 * @return
	 * @throws MalformedURLException
	 */
	public ProcessInstance startProcess(String deploymentId, String processDef,
			String userId, String passwd, Map<String, Object> params)
			throws MalformedURLException;

	/**
	 * @param deploymentId
	 * @param processDef
	 * @param userId
	 * @param passwd
	 * @return
	 * @throws MalformedURLException
	 */
	public ProcessInstance startProcess(String deploymentId, String processDef,
			String userId, String passwd) throws MalformedURLException;

	/**
	 * @param deploymentId
	 * @param userId
	 * @param passwd
	 * @return
	 * @throws MalformedURLException
	 */
	public List<TaskSummary> listTasksByPotencialOwner(String deploymentId,
			String userId, String passwd) throws MalformedURLException;

	/**
	 * @param deploymentId
	 * @param userId
	 * @param passwd
	 * @param processInstanceId
	 * @param variableName
	 * @return
	 * @throws Exception
	 */
	public String getVariableFromProcess(String deploymentId, String userId,
			String passwd, long processInstanceId, String variableName)
			throws Exception;

	/**
	 * @param deploymentId
	 * @param userId
	 * @param passwd
	 * @return
	 * @throws Exception
	 */
	public List<? extends ProcessInstanceLog> getAllProcess(String deploymentId, String userId,
			String passwd) throws Exception;

	//public Object getObjectFromProcessId(String deploymentId, String userId, String passwd, Long processInstanceId, String variableName  );
	
}
