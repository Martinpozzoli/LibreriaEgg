package egg.web.libreria.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egg.web.libreria.entidades.Autor;
import egg.web.libreria.errores.ErrorServicio;
import egg.web.libreria.repositorios.AutorRepositorio;

@Service
public class AutorServicio{

	@Autowired
	private AutorRepositorio autorRepo;
	
	public void crearAutor(String nombre, boolean alta) throws ErrorServicio{
		
		validar(nombre);
		
		Autor autor = new Autor();
		autor.setNombre(nombre);
		autor.setAlta(alta);
		
		autorRepo.save(autor);
	}
	
	public void modificarAutor(Integer id, String nombre, boolean alta) throws ErrorServicio{
		
		validar(nombre);
		
		if(!autorRepo.findById(id).isPresent()) {
			throw new ErrorServicio("No se encontró el autor con id = " + id);
		}
		
		Autor autor = autorRepo.findById(id).get();
		autor.setNombre(nombre);
		autor.setAlta(alta);
		
		autorRepo.save(autor);
	}
	
	public void darAltaBajaAutor(Integer id)  throws ErrorServicio{
		
		if(!autorRepo.findById(id).isPresent()) {
			throw new ErrorServicio("No se encontró el autor con id = " + id);
		}
		
		if (autorRepo.findById(id).get().isAlta()) {
			autorRepo.altabaja(false);
		}else {
			autorRepo.altabaja(true);
		}
	}
	
	public void quitarAutor(Integer id) throws ErrorServicio{
		
		if(!autorRepo.findById(id).isPresent()) {
			throw new ErrorServicio("No se encontró el autor con id = " + id);
		}
		autorRepo.eliminarAutor(id);
	}
	
	public List<Autor> mostrarAutores() throws ErrorServicio{
		
		return autorRepo.listarAutores();
	}
	
	private void validar(String nombre) throws ErrorServicio{
		
		if (nombre == null || nombre.isEmpty()) {
			throw new ErrorServicio("nombre sin especificar");
		}
	}
}
