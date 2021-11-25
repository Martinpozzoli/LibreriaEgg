package egg.web.libreria.entidades;

import javax.persistence.*;

@Entity
@Table(name="autor")
public class Autor {
	@Id
	@Column(name="autor_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
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
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
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
