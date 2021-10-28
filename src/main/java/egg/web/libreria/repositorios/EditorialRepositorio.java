package egg.web.libreria.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import egg.web.libreria.entidades.Editorial;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial,String>{

	@Query("SELECT e FROM Editorial e")
	public List<Editorial> listarEditoriales();
	
	@Query("SELECT e FROM Editorial e WHERE e.nombre =:nombre")
	public List<Editorial> listarEditorialesNombre(@Param("nombre") String nombre);

	@Query("SELECT e FROM Editorial e WHERE e.id =:id")
	public Editorial buscarEditorialId(@Param("id") String id);
	
	@Query("UPDATE Editorial e SET e.alta =:alta WHERE e.id =:id")
	public void altabaja(@Param("alta") boolean alta);
	
	@Query("DELETE Editorial e WHERE e.id =:id")
	public void eliminarEditorial(@Param("id") String id);

}
