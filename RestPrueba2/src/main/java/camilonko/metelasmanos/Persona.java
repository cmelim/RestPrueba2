package camilonko.metelasmanos;


import java.io.Serializable;

public class Persona implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6664815984427229799L;
	private String nombre;
	private Integer edad;
	private String estadoCivil;
	private Boolean flag;
	private String sexo;
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Integer getEdad() {
		return edad;
	}
	public void setEdad(Integer edad) {
		this.edad = edad;
	}
	public String getEstadoCivil() {
		return estadoCivil;
	}
	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}
	public Boolean getFlag() {
		return flag;
	}
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	@Override
	public String toString(){
		return "{\"nombre\":\"" 	 + this.nombre + "\"" 
			 + ",\"edad\":\""		 + this.edad + "\"" 
			 + ",\"estadoCivil\":\"" + this.estadoCivil + "\""
			 + ",\"flag\":\"" 		 + this.flag + "\""
			 + ",\"sexo\":\"" 		 + this.sexo + "\"" 
			 + "}";
	}
	
}
