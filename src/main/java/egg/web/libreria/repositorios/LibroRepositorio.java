package egg.web.libreria.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import egg.web.libreria.entidades.*;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, Integer>{

	@Query("SELECT l FROM Libro l")
	public List<Libro> listarLibros();
	
	@Query("SELECT l FROM Libro l WHERE l.titulo =:titulo")
	public List<Libro> listarLibrosTitulo(@Param("titulo") String titulo);
	
	@Query("SELECT l FROM Libro l WHERE l.autor.nombre =:nombre")
	public List<Libro> listarLibrosAutor(@Param("nombre") String nombre);
	
	@Query("SELECT l FROM Libro l WHERE l.editorial.nombre =:nombre")
	public List<Libro> listarLibrosEditorial(@Param("nombre") String nombre);
	
	@Modifying
	@Query("UPDATE Libro l SET l.alta =:alta WHERE l.id =:id")
	public void altabaja(@Param("alta") boolean alta, @Param("id") Integer id);
	
	@Modifying
	@Query("DELETE Libro l WHERE l.id =:id")
	public void eliminarLibro(@Param("id") Integer id);
	
	
}









