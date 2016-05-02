package cl.st.tramite;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name="dvBean")
@ViewScoped
public class DVBean {
	private String rowNumber;
	private String id;
	private String nombrePrceso;
	private String estado;
	private String duenoTarea;
	private String pagina;
	private List<PocST> listPersonaMail;
	
	public DVBean() {
		this.init();
	}
	public void init(){
		System.out.println("Pase por aca 111111");
		llenaLista();
	}
	
	private void llenaLista(){
		/*BDService bdService = new BDServiceImp();
		listPersonaMail= bdService.getPersonaMail();*/
	}
	
	public String getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(String rowNumber) {
		this.rowNumber = rowNumber;
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
	
	
	public String getPagina() {
		return pagina;
	}
	public void setPagina(String pagina) {
		this.pagina = pagina;
	}
	
	
	public List<PocST> getListPersonaMail() {
		return listPersonaMail;
	}
	public void setListPersonaMail(List<PocST> listPersonaMail) {
		this.listPersonaMail = listPersonaMail;
	}
}
