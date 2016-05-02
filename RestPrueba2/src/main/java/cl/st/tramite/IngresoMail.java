package cl.st.tramite;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.swing.plaf.basic.BasicPopupMenuSeparatorUI;
import javax.ws.rs.core.Response;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.model.TaskSummary;

import camilonko.metelasmanos.Persona;
import cl.corfo.ee.bpmsapi.bean.BPMSBean;
import cl.corfo.ee.bpmsapi.domain.JBPMClient;
import cl.corfo.ee.bpmsapi.domain.JBPMClientImpl;
import cl.corfo.ee.bpmsapi.vo.BpmsVO;
import cl.prueba.util.ClienteRest;

@ManagedBean(name = "ingresoMail")
@SessionScoped
public class IngresoMail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1386532891858896240L;
	private String nombre;
	private String mail;
	private String enviaMail;
	private String puntaje;
	

	private static final String HOSTNAME = "172.16.2.160";
	private static final String PORT = "8080";
	private static final String USER = "bpmsAdmin";
	private static final String PASSWD = "redhat2014.";
	private static final String DEPLOYMENT_ID = "cl.st.demo:DemoBPM:1.0";
	private static final String PROCESS_DEF_ID = "DemoBPM.Tramite";

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	

	public String getEnviaMail() {
		return enviaMail;
	}

	public void setEnviaMail(String enviaMail) {
		this.enviaMail = enviaMail;
	}
	
	

	public String getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(String puntaje) {
		this.puntaje = puntaje;
	}

	public IngresoMail() {
	}
	
	private JBPMClient jBPMClient;

	public String enviaMail() {
		StringEntity params = null;
		try {
			//params = new StringEntity("{\"username\":\"bpmsAdmin\",\"password\":\"redhat2014.\",\"deploymentId\":\"camilonko:METELASMANOS:1.0\",\"processDef\":\"METELASMANOS.pruebita\"}");
			//String url = "http://localhost:8280/bpms-apis/rest/1.0/startProcess";
			Persona persona = new Persona();
			/*persona.setNombre("pelao");
			persona.setEdad(34);
			persona.setSexo("gay");*/
			
			persona.setNombre(this.getNombre());
			persona.setEdad(Integer.valueOf(this.getPuntaje()));
			persona.setSexo(this.getMail());//sexo
			
			
			Map<String,Object> map= new HashMap<String,Object>();
			map.put("outPersona", persona);
			map.put("outNombre", persona.getNombre());
			BPMSBean bpmsBean = new BPMSBean();
			BpmsVO bpmsVO = new BpmsVO();
			bpmsVO.setDeploymentId("camilonko:METELASMANOS:1.0");
			bpmsVO.setProcessDef("METELASMANOS.pruebita");
			//bpmsVO.setUsername("bpmsAdmin");
			bpmsVO.setUsername("bpmsAdmin");
			bpmsVO.setPassword("redhat2014.");
			this.jBPMClient = new JBPMClientImpl();
			ProcessInstance instance = this.jBPMClient.startProcess(
					bpmsVO.getDeploymentId(), bpmsVO.getProcessDef(),
					bpmsVO.getUsername(), bpmsVO.getPassword());
			//Response rs = bpmsBean.startProcess(bpmsVO);
			long idProceso = 1;			
			List<TaskSummary> lisTTask = this.jBPMClient.listTasksByPotencialOwner(bpmsVO.getDeploymentId(), 
					bpmsVO.getUsername(), bpmsVO.getPassword());
			for(TaskSummary t : lisTTask){
				if(instance.getId() == t.getProcessInstanceId()){
					this.jBPMClient.completeTask(bpmsVO.getDeploymentId(), t.getId(), bpmsVO.getUsername(), bpmsVO.getPassword(), map);
				}
			}
			//Response rs = bpmsBean.listTasksByPotentialOwner(bpmsVO);
			//bpmsVO.setTaskId(new Long(5));
			//bpmsVO.setParams(map);
			//bpmsBean.completeTask(bpmsVO);
			/*JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("nombre", "Pelao");
				jsonObject.put("edad", 34);
				jsonObject.put("sexo", "gay");
				System.out.println(persona.toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			/*String url = "http://localhost:8280/bpms-apis/rest/1.0/completeTask";
			String json = "{\"username\":\"bpmsAdmin\",\"password\":\"redhat2014.\",\"deploymentId\":\"camilonko:METELASMANOS:1.0\",\"processDef\":\"METELASMANOS.pruebita\",\"taskId\":\"1\",\"params\":{\"outPersona\":" + jsonObject.toString()+ "}}";
			params = new StringEntity(json);
			ClienteRest clienteRest = new ClienteRest();
			clienteRest.invocaServicio(url, params);*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "tarea";
	}

	@PostConstruct
	private void init() {
		this.setMail("");
		this.setNombre("");
		this.setPuntaje("");
	}

	private static void p(String t, boolean titulo) {
		StringBuilder sb = new StringBuilder();
		sb.append(titulo ? "\n\n" : "");
		sb.append(titulo ? t.toUpperCase() : "\t>> " + t);
		sb.append(titulo ? "......................................." : "");
		System.out.println(sb.toString());
	}
}
