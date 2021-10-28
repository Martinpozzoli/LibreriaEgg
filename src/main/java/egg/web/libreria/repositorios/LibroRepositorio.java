package egg.web.libreria.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import egg.web.libreria.entidades.*;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, Integer>{

	@Query("SELECT l FROM Libro l")
	public List<Libro> listarLibros();
	
	@Query("SELECT l FROM Libro l WHERE l.autor.nombre =:nombre")
	public List<Libro> listarLibrosAutor(@Param("nombre") String nombre);
	
	@Query("SELECT l FROM Libro l WHERE l.editorial.nombre =:nombre")
	public List<Libro> listarLibrosEditorial(@Param("nombre") String nombre);
	
	
//	@Query("UPDATE libro l SET l.isbn =:isbn, l.titulo =:titulo, l.anio =:anio, l.ejemplares =:ejemplares, "
//			+ "ejemplaresPrestados =:ejemplaresPrestados, ejemplaresRestantes =:ejemplaresRestantes, "
//			+ "l.autor =:autor, l.editorial =:editorial WHERE l.id =:id")
//	public void editarLibro(@Param("id") String id, 
//							@Param("isbn") Long isbn,
//							@Param("titulo") String titulo,
//							@Param("anio") Integer anio,
//							@Param("ejemplares") Integer ejemplares,
//							@Param("ejemplaresPrestados") Integer ejemplaresPrestados,
//							@Param("ejemplaresRestantes") Integer ejemplaresRestantes,
//							//No se si lo que sigue va asi:
//							@Param("autor") Autor autor,
//							@Param("editorial") Editorial editorial);
	
	@Query("UPDATE Libro l SET l.alta =:alta WHERE l.id =:id")
	public void altabaja(@Param("alta") boolean alta);
	
	@Query("DELETE Libro l WHERE l.id =:id")
	public void eliminarLibro(@Param("id") Integer id);
	
	
}









