package egg.web.libreria.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egg.web.libreria.entidades.Editorial;
import egg.web.libreria.errores.ErrorServicio;
import egg.web.libreria.repositorios.EditorialRepositorio;

@Service
public class EditorialServicio {

	@Autowired
	private EditorialRepositorio editorialRepo;
	
	public void crearEditorial(String nombre, boolean alta) throws ErrorServicio{
		
		validar(nombre);
		
		Editorial editorial = new Editorial();
		editorial.setNombre(nombre);
		editorial.setAlta(alta);
		
		editorialRepo.save(editorial);
	}
	
	public void modificarEditorial(Integer id, String nombre, boolean alta) throws ErrorServicio{
		
		validar(nombre);
		
		if(!editorialRepo.findById(id).isPresent()) {
			throw new ErrorServicio("No se encontró el editorial con id = " + id);
		}
		
		Editorial editorial = editorialRepo.findById(id).get();
		editorial.setNombre(nombre);
		editorial.setAlta(alta);
		
		editorialRepo.save(editorial);
	}
	
	@Transactional
	public void darAltaBajaEditorial(Integer id)  throws ErrorServicio{
		
		if(!editorialRepo.findById(id).isPresent()) {
			throw new ErrorServicio("No se encontró el editorial con id = " + id);
		}
		
		if (editorialRepo.findById(id).get().isAlta()) {
			editorialRepo.altabaja(false, id);
		}else {
			editorialRepo.altabaja(true, id);
		}
	}
	
	@Transactional
	public void quitareditorial(Integer id) throws ErrorServicio{
		
		if(!editorialRepo.findById(id).isPresent()) {
			throw new ErrorServicio("No se encontró el editorial con id = " + id);
		}
		editorialRepo.eliminarEditorial(id);
	}
	
public List<Editorial> mostrarEditoriales() throws ErrorServicio{
		
		return editorialRepo.listarEditoriales();
	}
	
	private void validar(String nombre) throws ErrorServicio{
		
		if (nombre == null || nombre.isEmpty()) {
			throw new ErrorServicio("nombre sin especificar");
		}
	}
}
