package egg.web.libreria.servicios;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egg.web.libreria.entidades.Prestamo;
import egg.web.libreria.errores.ErrorServicio;
import egg.web.libreria.repositorios.LibroRepositorio;
import egg.web.libreria.repositorios.PrestamoRepositorio;
import egg.web.libreria.repositorios.UsuarioRepositorio;

@Service
public class PrestamoServicio {

	@Autowired
	private PrestamoRepositorio prestamoRepo;
	
	@Autowired
	private UsuarioRepositorio usuarioRepo;
	
	@Autowired
	private LibroRepositorio libroRepo;
	
	@Autowired
	private LibroServicio libroServicio;
	
	public void nuevoPrestamo(String idUsuario, Integer idLibro) throws ErrorServicio {
		validar(idUsuario, idLibro);
		
		Prestamo prestamo = new Prestamo();
		prestamo.setLibro(libroRepo.getById(idLibro));
		prestamo.setUsuario(usuarioRepo.getById(idUsuario));
		prestamo.setAlta(true);
		prestamo.setFechaPrestamo(LocalDate.now()); //salida: 2021-11-16
		prestamo.setFechaDevolucion(LocalDate.now().plusDays(30));
		
		libroServicio.prestarLibro(idLibro);
		
		prestamoRepo.save(prestamo);
	}
	
	@Transactional
	public void removerPrestamo(String id) throws ErrorServicio{
		if(!prestamoRepo.findById(id).isPresent()) {
			throw new ErrorServicio("No se encontró el prestamo con id = " + id);
		}
		libroServicio.returnLibro(prestamoRepo.findById(id).get().getLibro().getId());
		prestamoRepo.eliminarPrestamo(id);
	}
	
	@Transactional
	public void darAltaBajaPrestamo(String id) throws ErrorServicio{
			if(!prestamoRepo.findById(id).isPresent()) {
				throw new ErrorServicio("No se encontró el prestamo con id = " + id);
			}
		if (prestamoRepo.findById(id).get().isAlta()) {
			prestamoRepo.altabaja(false, id);
		}else {
			prestamoRepo.altabaja(true, id);
		}
	}
	
	public void validar(String idUsuario, Integer idLibro) throws ErrorServicio {
		if(usuarioRepo.findById(idUsuario).get()==null || idUsuario.isEmpty() || idUsuario == null) {
			throw new ErrorServicio("Usuario no encontrado");
		}
		if(libroRepo.findById(idLibro).get()==null || idLibro.toString().isEmpty() || idLibro == null) {
			throw new ErrorServicio("Libro no encontrado");
		}
		if(libroRepo.findById(idLibro).get().getEjemplaresRestantes() == 0) {
			throw new ErrorServicio("No quedan ejemplares disponibles");
		}
		if(libroRepo.findById(idLibro).get()==null || idLibro.toString().isEmpty() || idLibro == null) {
			throw new ErrorServicio("Libro no encontrado");
		}
		if(prestamoRepo.listarPrestamosPorUsuario(idUsuario).size() == 3) {
			throw new ErrorServicio("No puede tener más de 3 prestamos a la vez");
		}
	}
	
	public List<Prestamo> mostrarPrestamos() throws ErrorServicio{
		return prestamoRepo.listarPrestamos();
	}
	
	public List<Prestamo> buscarPorId(String id) {
		return prestamoRepo.listarPrestamosPorUsuario(id);
	}
}




