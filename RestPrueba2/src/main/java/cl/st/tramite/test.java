package cl.st.tramite;


public class test {

	private static final String HOSTNAME = "172.16.2.160";
	private static final String PORT = "8080";
	private static final String USER = "bpmsAdmin";
	private static final String PASSWD = "redhat2014.";

	private static final String DEPLOYMENT_ID = "cl.st.demo:DemoBPM:1.0";
	private static final String PROCESS_DEF_ID = "demoBPM.Tramite";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/*RestClientSimple rc = new RestClientSimple(HOSTNAME, PORT, USER, PASSWD);
		List<TaskSummary> taskListForPotentialOwner = null;
		try {
			taskListForPotentialOwner = rc.getTaskListForPotentialOwner(rc, DEPLOYMENT_ID, USER);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		try {
			//this.setListTask(clienteRest.invocaServicioTS(url, params));
			 for (TaskSummary tas : taskListForPotentialOwner) {
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
	private static void p(String t, boolean titulo){
		StringBuilder sb =  new StringBuilder();
		sb.append(titulo? "\n\n" : "");
		sb.append(titulo? t.toUpperCase() : "\t>> "+t);
		sb.append(titulo? ".......................................": "");
		System.out.println(sb.toString());
	}

}
