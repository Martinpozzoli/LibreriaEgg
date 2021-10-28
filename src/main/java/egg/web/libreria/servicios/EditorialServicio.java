package egg.web.libreria.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egg.web.libreria.entidades.Editorial;
import egg.web.libreria.errores.ErrorServicio;
import egg.web.libreria.repositorios.EditorialRepositorio;

@Service
public class EditorialServicio {

	@Autowired
	private EditorialRepositorio editorialRepo;
	
	public void creareditorial(String nombre, boolean alta) throws ErrorServicio{
		
		validar(nombre);
		
		Editorial editorial = new Editorial();
		editorial.setNombre(nombre);
		editorial.setAlta(alta);
		
		editorialRepo.save(editorial);
	}
	
	public void modificareditorial(String id, String nombre, boolean alta) throws ErrorServicio{
		
		validar(nombre);
		
		if(!editorialRepo.findById(id).isPresent()) {
			throw new ErrorServicio("No se encontró el editorial con id = " + id);
		}
		
		Editorial editorial = editorialRepo.findById(id).get();
		editorial.setNombre(nombre);
		editorial.setAlta(alta);
		
		editorialRepo.save(editorial);
	}
	
	public void darAltaBajaeditorial(String id)  throws ErrorServicio{
		
		if(!editorialRepo.findById(id).isPresent()) {
			throw new ErrorServicio("No se encontró el editorial con id = " + id);
		}
		
		if (editorialRepo.findById(id).get().isAlta()) {
			editorialRepo.altabaja(false);
		}else {
			editorialRepo.altabaja(true);
		}
	}
	
	public void quitareditorial(String id) throws ErrorServicio{
		
		if(!editorialRepo.findById(id).isPresent()) {
			throw new ErrorServicio("No se encontró el editorial con id = " + id);
		}
		editorialRepo.eliminarEditorial(id);
	}
	
	
	private void validar(String nombre) throws ErrorServicio{
		
		if (nombre == null || nombre.isEmpty()) {
			throw new ErrorServicio("nombre sin especificar");
		}
	}
}
