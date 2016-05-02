package cl.st.tramite;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;

import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.model.TaskSummary;

import cl.corfo.ee.bpmsapi.bean.BPMSBean;
import cl.corfo.ee.bpmsapi.domain.JBPMClient;
import cl.corfo.ee.bpmsapi.domain.JBPMClientImpl;
import cl.corfo.ee.bpmsapi.vo.BpmsVO;
import cl.st.util.ServletUtil;

@ManagedBean(name = "revision")
@SessionScoped
public class Revision implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1386532891858896240L;
	private String nombre;
	private String mail;
	private String enviaMail;
	private String puntaje;
	private Long idProceso;

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
	
	public Long getIdProceso() {
		return idProceso;
	}

	public void setIdProceso(Long idProceso) {
		this.idProceso = idProceso;
	}
	
	public Revision() {
	}
	private JBPMClient jBPMClient;

	public String enviaMail() {
		System.out.println("aqui");
		try{
		HttpSession session = ServletUtil.getSession();
		//id de proceso enviado desde aplicacion
		this.setIdProceso((Long)session.getAttribute("proceso"));
		//se prepara la conexion al BPM
			BPMSBean bpmsBean = new BPMSBean();
			BpmsVO bpmsVO = new BpmsVO();
			bpmsVO.setDeploymentId("camilonko:METELASMANOS:1.0");
			bpmsVO.setProcessDef("METELASMANOS.pruebita");
			//bpmsVO.setUsername("bpmsAdmin");
			bpmsVO.setUsername("Ejecutor1");
			bpmsVO.setPassword("redhat2014.");
			long idProceso = 1;			
			this.jBPMClient = new JBPMClientImpl();
			//se obtiene la lists de tareas de BPM
			List<TaskSummary> lisTTask = this.jBPMClient.listTasksByPotencialOwner(bpmsVO.getDeploymentId(), 
					bpmsVO.getUsername(), bpmsVO.getPassword());
			//se itera para obtener la tarea seleccionada.
			for(TaskSummary t : lisTTask){
				if(this.getIdProceso().equals(t.getProcessInstanceId())){
					this.jBPMClient.completeTask(bpmsVO.getDeploymentId(), t.getId(), bpmsVO.getUsername(), bpmsVO.getPassword(), null);
				}
			}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			//string utilizado para strut2
			return "tarea";
	}

	@PostConstruct
	private void init() {
			this.setMail("");
			this.setNombre("");
			this.setPuntaje("");
	}
	
	public void carga() {
		HttpSession session = ServletUtil.getSession();
		session.getAttribute("proceso");
		Long l = (Long)session.getAttribute("proceso");
		this.setIdProceso(l);
	}

	private static void p(String t, boolean titulo) {
		StringBuilder sb = new StringBuilder();
		sb.append(titulo ? "\n\n" : "");
		sb.append(titulo ? t.toUpperCase() : "\t>> " + t);
		sb.append(titulo ? "......................................." : "");
		System.out.println(sb.toString());
	}
	
}
