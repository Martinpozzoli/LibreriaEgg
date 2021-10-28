package egg.web.libreria.entidades;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="autor")
public class Autor {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	private String nombre;
	private boolean alta;
	
	
	public Autor() {
		super();
	}
	
	public Autor(String nombre, boolean alta) {
		super();
		this.nombre = nombre;
		this.alta = alta;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public boolean isAlta() {
		return alta;
	}
	
	public void setAlta(boolean alta) {
		this.alta = alta;
	}
	
	
}
