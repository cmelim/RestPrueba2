package cl.st.tramite;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "rechazo")
@SessionScoped
public class Rechazo implements Serializable {

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

	public Rechazo() {
	}

	public String enviaMail() {
		//if (!this.getNombre().isEmpty()) {
		/*HttpSession session = ServletUtil.getSession();
			try {
				System.out.println("metodo Grabar " + this.getNombre() + " "
						+ this.getMail());
				RestClientSimple rc = new RestClientSimple(HOSTNAME, PORT,
						USER, PASSWD);
				Map<String, Object> params = new HashMap<String, Object>();
				p("Buscando tareas asociadas a :" + USER, true);
				List<TaskSummary> taskListForPotentialOwner = rc
						.getTaskListForPotentialOwner(rc, DEPLOYMENT_ID, USER);
				Long idProceso = (Long)session.getAttribute("proceso");
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
			}
		//}
			this.setNombre("");
			this.setMail("");
			this.setPuntaje("");*/
			return "tarea";

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
}
