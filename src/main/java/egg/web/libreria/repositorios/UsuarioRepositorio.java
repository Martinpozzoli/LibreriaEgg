package egg.web.libreria.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import egg.web.libreria.entidades.Usuario;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String>{

	@Query("SELECT u FROM Usuario u WHERE u.email =:email")
	public Usuario findByEmail(@Param("email")String email);
}
