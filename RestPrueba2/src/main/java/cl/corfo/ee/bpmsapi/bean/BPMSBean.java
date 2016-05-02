package cl.corfo.ee.bpmsapi.bean;

import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.apache.deltaspike.core.api.config.ConfigProperty;
import org.jboss.logging.Logger;
import org.kie.api.runtime.manager.audit.ProcessInstanceLog;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.model.TaskSummary;

import com.google.gson.Gson;

import cl.corfo.ee.bpmsapi.domain.JBPMClient;
import cl.corfo.ee.bpmsapi.domain.JBPMClientImpl;
import cl.corfo.ee.bpmsapi.rest.BPMSRest;
import cl.corfo.ee.bpmsapi.vo.BpmsVO;
import cl.corfo.ee.bpmsapi.vo.Contribuyente;

/**
 * @author psep
 *
 */
@RequestScoped
public class BPMSBean implements BPMSRest {

	private static final Logger logger = Logger.getLogger(BPMSBean.class);

	@Inject
	private JBPMClient jBPMClient;

	@Inject
	@ConfigProperty(name = "bpms.user.default")
	private String userId;

	@Inject
	@ConfigProperty(name = "bpms.passwd.default")
	private String passwd;

	/**
	 * @param bpmsVO
	 */
	private void loadCredentials(BpmsVO bpmsVO) {
		if (bpmsVO.getUsername() != null
				&& !bpmsVO.getUsername().trim().equals("")
				&& bpmsVO.getPassword() != null
				&& !bpmsVO.getPassword().trim().equals("")) {
			this.userId = bpmsVO.getUsername();
			this.passwd = bpmsVO.getPassword();
		}
	}

	public Response startProcess(BpmsVO bpmsVO) {
		try {
			this.loadCredentials(bpmsVO);
			this.jBPMClient = new JBPMClientImpl();
			//bpmsVO.getParams().put("contribuyente", bpmsVO.getContribuyente());
			ProcessInstance instance = this.jBPMClient.startProcess(
					bpmsVO.getDeploymentId(), bpmsVO.getProcessDef(),
					this.userId, this.passwd);

			return Response.ok().entity(instance).build();

		} catch (MalformedURLException e) {
			logger.error(e, e);
		}catch (Exception e) {
			e.printStackTrace();
		}

		return Response.serverError().build();
	}

	public Response completeTask(BpmsVO bpmsVO) {
		try {
			this.loadCredentials(bpmsVO);
			this.jBPMClient = new JBPMClientImpl();
			this.jBPMClient.completeTask(bpmsVO.getDeploymentId(),
					bpmsVO.getTaskId(), userId, passwd, bpmsVO.getParams());

			return Response.ok().build();

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e, e);
		}

		return Response.serverError().build();
	}

	public Response claimTask(BpmsVO bpmsVO) {
		try {
			this.loadCredentials(bpmsVO);

			this.jBPMClient.startTask(bpmsVO.getDeploymentId(),
					bpmsVO.getTaskId(), userId, passwd);

			return Response.ok().build();

		} catch (Exception e) {
			logger.error(e, e);
		}

		return Response.serverError().build();
	}

	public Response endTask(BpmsVO bpmsVO) {
		try {
			this.loadCredentials(bpmsVO);

			this.jBPMClient.endTask(bpmsVO.getDeploymentId(),
					bpmsVO.getTaskId(), userId, passwd, bpmsVO.getParams());

			return Response.ok().build();

		} catch (Exception e) {
			logger.error(e, e);
		}

		return Response.serverError().build();
	}

	public Response listTasksByPotentialOwner(BpmsVO bpmsVO) {
		try {
			return Response.ok().entity(this.listTasks(bpmsVO)).build();

		} catch (Exception e) {
			logger.error(e, e);
		}

		return Response.serverError().build();
	}

	/**
	 * @param bpmsVO
	 * @return
	 * @throws MalformedURLException
	 */
	private List<TaskSummary> listTasks(BpmsVO bpmsVO)
			throws MalformedURLException {
		this.loadCredentials(bpmsVO);

		return this.jBPMClient.listTasksByPotencialOwner(
				bpmsVO.getDeploymentId(), userId, passwd);
	}

	public Response listTasksByContribuyente(BpmsVO bpmsVO) {
		try {
			this.loadCredentials(bpmsVO);
			List<TaskSummary> tasks = this.listTasks(bpmsVO);
			Iterator<TaskSummary> it = tasks.iterator();

			List<TaskSummary> tasksByContribuyente = new LinkedList<TaskSummary>();

			while (it.hasNext()) {
				TaskSummary task = it.next();

				String json = this.jBPMClient.getVariableFromProcess(
						task.getDeploymentId(), userId, passwd,
						task.getProcessInstanceId(), "contribuyente");
				Contribuyente contribuyente = new Gson().fromJson(json,
						Contribuyente.class);

				if (contribuyente.getRut().equals(
						bpmsVO.getContribuyente().getRut())) {
					tasksByContribuyente.add(task);
				}
			}

			return Response.ok().entity(tasksByContribuyente).build();

		} catch (Exception e) {
			logger.error(e, e);
		}

		return Response.serverError().build();
	}

	public Response getVariableFromProcess(BpmsVO bpmsVO) {
		try {
			String object = this.jBPMClient.getVariableFromProcess(
					bpmsVO.getDeploymentId(), userId, passwd,
					bpmsVO.getProcessId(), bpmsVO.getVariableName());

			return Response.ok().entity(object).build();

		} catch (Exception e) {
			logger.error(e, e);
		}

		return Response.serverError().build();
	}

	/**
	 * @param bpmsVO
	 * @return
	 * @throws Exception
	 */
	private List<? extends ProcessInstanceLog> listProcess(BpmsVO bpmsVO)
			throws Exception {
		this.loadCredentials(bpmsVO);
		return this.jBPMClient.getAllProcess(bpmsVO.getDeploymentId(), userId,
				passwd);
	}

	@Override
	public Response getAllProcess(BpmsVO bpmsVO) {
		try {
			return Response.ok().entity(this.listProcess(bpmsVO)).build();
		} catch (Exception e) {
			logger.error(e, e);
		}

		return Response.serverError().build();
	}

	@Override
	public Response getProcessByContribuyente(BpmsVO bpmsVO) {
		try {
			List<? extends ProcessInstanceLog> list = this.listProcess(bpmsVO);
			Iterator<? extends ProcessInstanceLog> it = list.iterator();

			List<ProcessInstanceLog> listByContribuyente = new LinkedList<ProcessInstanceLog>();

			while (it.hasNext()) {
				ProcessInstanceLog process = it.next();

				String json = this.jBPMClient.getVariableFromProcess(
						bpmsVO.getDeploymentId(), userId, passwd,
						process.getProcessInstanceId(), "contribuyente");
				Contribuyente contribuyente = new Gson().fromJson(json,
						Contribuyente.class);

				if (contribuyente.getRut().equals(
						bpmsVO.getContribuyente().getRut())) {
					listByContribuyente.add(process);
				}
			}
			
			return Response.ok().entity(listByContribuyente).build();
		} catch (Exception e) {
			logger.error(e, e);
		}

		return Response.serverError().build();
	}

}
