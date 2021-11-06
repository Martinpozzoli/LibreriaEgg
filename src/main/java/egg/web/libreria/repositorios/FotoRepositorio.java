package egg.web.libreria.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import egg.web.libreria.entidades.Foto;


@Repository
public interface FotoRepositorio extends JpaRepository<Foto, Integer>{

}
