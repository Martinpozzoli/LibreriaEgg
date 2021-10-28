package egg.web.libreria.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import egg.web.libreria.entidades.Autor;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor,Integer>{
	

	@Query("SELECT a FROM Autor a")
	public List<Autor> listarAutores();
	
	@Query("SELECT a FROM Autor a WHERE a.nombre =:nombre")
	public List<Autor> listarAutoresNombre(@Param("nombre") String nombre);

	@Query("SELECT a FROM Autor a WHERE a.id =:id")
	public Autor buscarAutorId(@Param("id") Integer id);

	@Query("UPDATE Autor a SET a.alta =:alta WHERE a.id =:id")
	public void altabaja(@Param("alta") boolean alta);
	
	@Query("DELETE Autor a WHERE a.id =:id")
	public void eliminarAutor(@Param("id") Integer id);

}
