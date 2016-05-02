package cl.st.tramite;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;

import org.kie.api.task.model.TaskSummary;

import camilonko.metelasmanos.Persona;
import cl.corfo.ee.bpmsapi.bean.BPMSBean;
import cl.corfo.ee.bpmsapi.domain.JBPMClient;
import cl.corfo.ee.bpmsapi.domain.JBPMClientImpl;
import cl.corfo.ee.bpmsapi.vo.BpmsVO;
import cl.st.util.ServletUtil;

@ManagedBean(name = "gestion")
@SessionScoped
public class Gestion implements Serializable {

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

	public Gestion() {
	}
	
	private JBPMClient jBPMClient;

	public String enviaMail() {
		
		System.out.println("REcibiendo todo desde gestion ");
		try{
			

			Map<String,Object> map= new HashMap<String,Object>();
			map.put("outNombre", this.getNombre());
			
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
			//long idProceso = 1;			
			this.jBPMClient = new JBPMClientImpl();
			//se obtiene la lists de tareas de BPM
			List<TaskSummary> lisTTask = this.jBPMClient.listTasksByPotencialOwner(bpmsVO.getDeploymentId(), 
					bpmsVO.getUsername(), bpmsVO.getPassword());
			//se itera para obtener la tarea seleccionada.
			for(TaskSummary t : lisTTask){
				if(this.getIdProceso().equals(t.getProcessInstanceId())){
					//String recuperaNombre 	= this.jBPMClient.getVariableFromProcess(bpmsVO.getDeploymentId(), bpmsVO.getUsername(), bpmsVO.getPassword(), this.getIdProceso(), "nombre");
					String recuperaPojo 	= this.jBPMClient.getVariableFromProcess(bpmsVO.getDeploymentId(), bpmsVO.getUsername(), bpmsVO.getPassword(), this.getIdProceso(), "Persona");	
					System.out.println("La variable recuperada es :  "+recuperaPojo);
					//Object o = this.jBPMClient.getObjectFromProcessId(bpmsVO.getDeploymentId(), bpmsVO.getUsername(), bpmsVO.getPassword(), this.getIdProceso(), "Persona");
					//Persona p = (Persona) o;
					
					//System.out.println("objeto persona "+p.getNombre());
					this.jBPMClient.completeTask(bpmsVO.getDeploymentId(), t.getId(), bpmsVO.getUsername(), bpmsVO.getPassword(), map);
				
					
				}
			}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			//string utilizado para strut2
			return "tarea";
		
		//if (!this.getNombre().isEmpty()) {
	/*	HttpSession session = ServletUtil.getSession();
			try {
				System.out.println("metodo Grabar " + this.getNombre() + " "
						+ this.getMail());
				RestClientSimple rc = new RestClientSimple(HOSTNAME, PORT,
						USER, PASSWD);
				Map<String, Object> params = new HashMap<String, Object>();
				session.getAttribute("proceso");
				Long idProceso = (Long)session.getAttribute("proceso");
				System.err.println("id de Proceso" + idProceso);
				p("Buscando tareas asociadas a :" + USER, true);
				List<TaskSummary> taskListForPotentialOwner = rc
						.getTaskListForPotentialOwner(rc, DEPLOYMENT_ID, USER);
				for (TaskSummary ts : taskListForPotentialOwner) {
					if(idProceso == ts.getProcessInstanceId()){
						String task = "tarea {[%s][%s][%s][%s]}";
						task = String.format(task, ts.getId(), ts.getName(),
								ts.getProcessInstanceId(), ts.getStatus());
						p("Reclamando " + task, false);
						// rc.claimTask(rc, DEPLOYMENT_ID, ts.getId(), USER);
						rc.startTask(rc, DEPLOYMENT_ID, ts.getId(), USER);
						p("Completando " + task, false);
						params.put("outNombre", this.getNombre());
						params.put("outMail", this.getMail());
						params.put("outPuntaje", this.getPuntaje());
						rc.completeTask(rc, DEPLOYMENT_ID, ts.getId(), USER, params);
					}
				}
				/*BDService bdService = BDServiceImp.class.newInstance();
				boolean resultado = bdService.guardarPersona(nombre, mail);
				System.out.println(resultado);*/
		/*	} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		//}
			/*this.setNombre("");
			this.setMail("");
			this.setPuntaje("");*/
		 
	}

	@PostConstruct
	private void init() {
		this.setMail("");
		this.setNombre("");
		this.setPuntaje("");
		
		
		
	}
	

	public void carga() {
		/*HttpSession session = ServletUtil.getSession();
		session.getAttribute("proceso");
		Long l = (Long)session.getAttribute("proceso");
		//long l = (long)session.getAttribute("proceso");
		JBPMClient client = new JBPMClient(HOSTNAME, PORT, USER, PASSWD);
		try {
			this.setNombre((String)(client.getVariableFromProcess(DEPLOYMENT_ID, l, "nombre")));
			this.setMail((String)(client.getVariableFromProcess(DEPLOYMENT_ID, l, "mail")));
			this.setPuntaje((String)(client.getVariableFromProcess(DEPLOYMENT_ID, l, "puntaje")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

	private static void p(String t, boolean titulo) {
		StringBuilder sb = new StringBuilder();
		sb.append(titulo ? "\n\n" : "");
		sb.append(titulo ? t.toUpperCase() : "\t>> " + t);
		sb.append(titulo ? "......................................." : "");
		System.out.println(sb.toString());
	}

	public Long getIdProceso() {
		return idProceso;
	}

	public void setIdProceso(Long idProceso) {
		this.idProceso = idProceso;
	}
}
