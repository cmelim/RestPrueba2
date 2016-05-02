package cl.st.tramite;

import java.net.MalformedURLException;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.kie.api.task.model.TaskSummary;
import org.primefaces.event.SelectEvent;

import cl.corfo.ee.bpmsapi.domain.JBPMClient;
import cl.corfo.ee.bpmsapi.domain.JBPMClientImpl;
import cl.corfo.ee.bpmsapi.vo.BpmsVO;
import cl.st.util.ServletUtil;

@ManagedBean(name="listaBean")
@ViewScoped
public class ListaBean {
	private String nombreUsuario;
	private String usuarioReasigna;
	private List<TaskSummary> listTask;
	private TaskSummary taskSummary;
	private String rowNumber;
	private String id;
	private String nombrePrceso;
	private String estado;
	private String duenoTarea;
	private String pagina;
	private static final String HOSTNAME = "172.16.2.160";
	private static final String PORT = "8080";
	private static final String USER = "bpmsAdmin";
	private static final String PASSWD = "redhat2014.";

	private static final String DEPLOYMENT_ID = "cl.st.demo:DemoBPM:1.0";
	private static final String PROCESS_DEF_ID = "DemoBPM.Tramite";
	
	public ListaBean() {
		this.init();
	}
	public void init(){
		System.out.println("Pase por aca 111111");
		this.setNombreUsuario("");
		llenaLista();
	}
	private JBPMClient jBPMClient;
	private void llenaLista(){
		this.jBPMClient = new JBPMClientImpl();
		try {
			BpmsVO bpmsVO = new BpmsVO();
			bpmsVO.setDeploymentId("Cencosud:PocCencoRegla:1.0");
			bpmsVO.setProcessDef("PocCencoRegla.testPremio");
			//bpmsVO.setUsername("bpmsAdmin");
			bpmsVO.setUsername("bpmsAdmin");
			bpmsVO.setPassword("redhat2014.");
		
			List<TaskSummary> lisTTask = this.jBPMClient.listTasksByPotencialOwner(bpmsVO.getDeploymentId(), 
					bpmsVO.getUsername(), bpmsVO.getPassword());
			this.setListTask(lisTTask);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*this.setListTask(new LinkedList<TaskSummary>());
		RestClientSimple rc = new RestClientSimple(HOSTNAME, PORT, USER, PASSWD);
		List<TaskSummary> taskListForPotentialOwner = null;
		try {
			taskListForPotentialOwner = rc.getTaskListForPotentialOwner(rc, DEPLOYMENT_ID, USER);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		this.setListTask(taskListForPotentialOwner);
		try {
			//this.setListTask(clienteRest.invocaServicioTS(url, params));
			 for (TaskSummary tas : this.getListTask()) {
					String task = "tarea {[%s][%s][%s][%s][%s]}";
					task = String.format(task, tas.getId(), tas.getName(),tas.getProcessInstanceId(), tas.getStatus(),tas.getActualOwner()); // devuelve tarea especifica segun id proceso guardado
					p("Reclamando "+task, false);
				}
			//System.out.println(response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	
	
	
	
	public String getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(String rowNumber) {
		this.rowNumber = rowNumber;
	}
	
	


	public String getUsuarioReasigna() {
		return usuarioReasigna;
	}

	public void setUsuarioReasigna(String usuarioReasigna) {
		this.usuarioReasigna = usuarioReasigna;
	}
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombrePrceso() {
		return nombrePrceso;
	}

	public void setNombrePrceso(String nombrePrceso) {
		this.nombrePrceso = nombrePrceso;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getDuenoTarea() {
		return duenoTarea;
	}

	public void setDuenoTarea(String duenoTarea) {
		this.duenoTarea = duenoTarea;
	}
	
	
	public List<TaskSummary> getListTask() {
		return listTask;
	}
	public void setListTask(List<TaskSummary> listTask) {
		this.listTask = listTask;
	}
	public TaskSummary getTaskSummary() {
		return taskSummary;
	}
	public void setTaskSummary(TaskSummary taskSummary) {
		this.taskSummary = taskSummary;
	}
	public String getPagina() {
		return pagina;
	}
	public void setPagina(String pagina) {
		this.pagina = pagina;
	}
	public void actualiza(TaskSummary task){
		/*System.out.println("Pase por aca " );
		this.setDuenoTarea(task.getActualOwner());
		Long l = task.getProcessInstanceId();
		this.setId(l.toString());
		this.setNombrePrceso(task.getName());
		this.setEstado(task.getStatus());*/
	}
	public void resuelve(TaskSummary task){
		/*HttpSession session = ServletUtil.getSession();
		Long l = new Long(task.getProcessInstanceId());
		session.setAttribute("nombreUsuario", task.getName());
		session.setAttribute("proceso", l);
		System.out.println("resuelve");
		if(task.getName().equals("Ingreso Solicitud")){
			//this.setPagina("/pages/ingreso.xhtml");
			this.setPagina("http://172.16.2.161:8080/Tramite/ingreso.jsf");
		}else if (task.getName().equals("revision")){
			this.setPagina("http://172.16.2.161:8080/Tramite/revision.jsf");
		}else if (task.getName().equals("gestion")){
			this.setPagina("http://172.16.2.161:8080/Tramite/gestion.jsf");
		}else if (task.getName().equals("rechazo")){
			this.setPagina("http://172.16.2.161:8080/Tramite/rechazo.jsf");
		}else if (task.getName().equals("notificacion")){
			this.setPagina("http://172.16.2.161:8080/Tramite/notificacion.jsf");
		}*/
	}
	public String resue(TaskSummary task){
		HttpSession session = ServletUtil.getSession();
		Long l = new Long(task.getProcessInstanceId());
		session.setAttribute("nombreUsuario", task.getName());
		session.setAttribute("proceso", l);
		System.out.println("resuelve");
		String target = "";
		if(task.getName().equals("Ingreso Solicitud")){
			//this.setPagina("/pages/ingreso.xhtml");
			target = "ingreso";
		}else if (task.getName().equals("formulario 2")){
			target = "revision";
		}else if (task.getName().equals("Formulario 1")){
			String nombre = 
			target = "gestion";
		}else if (task.getName().equals("rechazo")){
			target = "rechazo";
		}else if (task.getName().equals("notificacion")){
			target = "notificacion";
		}
		return target;
	}
	public void timeline(TaskSummary task){
		/*System.out.println("dato previo " + ServletUtil.getIdProceso());
		Long l = new Long(task.getProcessInstanceId());
		HttpSession session = ServletUtil.getSession();
		session.setAttribute("proc", String.valueOf(l));
		System.out.println(ServletUtil.getIdProceso());
		System.out.println("resuelve");
		this.setPagina("http://localhost:8080/Tramite/timeline.jsf");*/
	}
	private static void p(String t, boolean titulo){
		StringBuilder sb =  new StringBuilder();
		sb.append(titulo? "\n\n" : "");
		sb.append(titulo? t.toUpperCase() : "\t>> "+t);
		sb.append(titulo? ".......................................": "");
		System.out.println(sb.toString());
	}
	 public void onRowSelect(SelectEvent event) {  
	        FacesMessage msg = new FacesMessage("Car Selected");  
	        System.out.println("test");
	        FacesContext.getCurrentInstance().addMessage(null, msg);  
	    }  

}
