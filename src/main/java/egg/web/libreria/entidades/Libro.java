package egg.web.libreria.entidades;

import javax.persistence.*;

@Entity
@Table(name="libro")
public class Libro {
	@Id
	@Column(name="libro_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private Long isbn;
	private String titulo;
	private Integer anio;
	private String sinopsis;
	private Integer ejemplares;
	private Integer ejemplaresPrestados;
	private Integer ejemplaresRestantes;
	private boolean alta;
	@ManyToOne
	private Autor autor;
	@ManyToOne
	private Editorial editorial;
	@OneToOne
	private Foto foto;
	

	public Libro(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados,
			Integer ejemplaresRestantes, boolean alta, Autor autor, Editorial editorial, String sinopsis) {
		super();
		this.isbn = isbn;
		this.titulo = titulo;
		this.anio = anio;
		this.ejemplares = ejemplares;
		this.ejemplaresPrestados = ejemplaresPrestados;
		this.ejemplaresRestantes = ejemplaresRestantes;
		this.alta = alta;
		this.autor = autor;
		this.editorial = editorial;
		this.sinopsis = sinopsis;
	}

	public Libro() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getIsbn() {
		return isbn;
	}

	public void setIsbn(Long isbn) {
		this.isbn = isbn;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public Integer getEjemplares() {
		return ejemplares;
	}

	public void setEjemplares(Integer ejemplares) {
		this.ejemplares = ejemplares;
	}

	public Integer getEjemplaresPrestados() {
		return ejemplaresPrestados;
	}

	public void setEjemplaresPrestados(Integer ejemplaresPrestados) {
		this.ejemplaresPrestados = ejemplaresPrestados;
	}

	public Integer getEjemplaresRestantes() {
		return ejemplaresRestantes;
	}

	public void setEjemplaresRestantes() {
		this.ejemplaresRestantes = ejemplares - ejemplaresPrestados;
	}

	public boolean isAlta() {
		return alta;
	}

	public void setAlta(boolean alta) {
		this.alta = alta;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public Editorial getEditorial() {
		return editorial;
	}

	public void setEditorial(Editorial editorial) {
		this.editorial = editorial;
	}
	

	public Foto getFoto() {
		return foto;
	}

	public void setFoto(Foto foto) {
		this.foto = foto;
	}

	public String getSinopsis() {
		return sinopsis;
	}

	public void setSinopsis(String sinopsis) {
		this.sinopsis = sinopsis;
	}
	
	
}
