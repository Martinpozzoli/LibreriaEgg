package egg.web.libreria.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import egg.web.libreria.entidades.Prestamo;

@Repository
public interface PrestamoRepositorio extends JpaRepository<Prestamo, String>{

	@Query("SELECT p FROM Prestamo p")
	public List<Prestamo> listarPrestamos();
	
	@Query("SELECT p FROM Prestamo p WHERE p.usuario.id =:id")
	public List<Prestamo> listarPrestamosPorUsuario(@Param("id") String id);
	
	@Modifying
	@Query("UPDATE Prestamo p SET p.alta =:alta WHERE p.id =:id")
	public void altabaja(@Param("alta") boolean alta, @Param("id") String id);
	
	@Modifying
	@Query("DELETE Prestamo p WHERE p.id =:id")
	public void eliminarPrestamo(@Param("id") String id);
}
