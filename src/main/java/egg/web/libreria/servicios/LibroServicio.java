package egg.web.libreria.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egg.web.libreria.entidades.Libro;
import egg.web.libreria.errores.ErrorServicio;
import egg.web.libreria.repositorios.*;

@Service
public class LibroServicio {
	
	@Autowired
	private LibroRepositorio libroRepo;
	
	@Autowired
	private AutorRepositorio autorRepo;
	
	@Autowired
	private EditorialRepositorio editorialRepo;

	public void crearLibro(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados,
			Integer ejemplaresRestantes, Integer autorId, Integer editorialId) throws ErrorServicio{
		
		validar(isbn, titulo, anio, ejemplares, ejemplaresPrestados,
				ejemplaresRestantes, autorId, editorialId);
		
		Libro libro = new Libro();
		libro.setIsbn(isbn);
		libro.setTitulo(titulo);
		libro.setAnio(anio);
		libro.setEjemplares(ejemplares);
		libro.setEjemplaresPrestados(ejemplaresPrestados);
		libro.setEjemplaresRestantes(ejemplaresRestantes);
		libro.setAlta(true);
		libro.setAutor(autorRepo.buscarAutorId(autorId));
		libro.setEditorial(editorialRepo.buscarEditorialId(editorialId));
		
		libroRepo.save(libro);
	}
	
	
	public void modificarLibro(Integer id, Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados,
			Integer ejemplaresRestantes, Integer autorId, Integer editorialId) throws ErrorServicio {
		
		validar(isbn, titulo, anio, ejemplares, ejemplaresPrestados,
				ejemplaresRestantes, autorId, editorialId);
		
		if(!libroRepo.findById(id).isPresent()) {
			throw new ErrorServicio("No se encontró el libro con id = " + id);
		}
		
		Libro libro = libroRepo.findById(id).get();
		
		libro.setIsbn(isbn);
		libro.setTitulo(titulo);
		libro.setAnio(anio);
		libro.setEjemplares(ejemplares);
		libro.setEjemplaresPrestados(ejemplaresPrestados);
		libro.setEjemplaresRestantes(ejemplaresRestantes);
		libro.setAlta(true);
		libro.setAutor(autorRepo.buscarAutorId(autorId));
		libro.setEditorial(editorialRepo.buscarEditorialId(editorialId));
		
		libroRepo.save(libro);
		
	}
	
	public void darAltaBajaLibro(Integer id) throws ErrorServicio{
			if(!libroRepo.findById(id).isPresent()) {
				throw new ErrorServicio("No se encontró el libro con id = " + id);
			}
		if (libroRepo.findById(id).get().isAlta()) {
			libroRepo.altabaja(false);
		}else {
			libroRepo.altabaja(true);
		}
	}
	
	public void quitarLibro(Integer id) throws ErrorServicio{
		if(!libroRepo.findById(id).isPresent()) {
			throw new ErrorServicio("No se encontró el libro con id = " + id);
		}
		libroRepo.eliminarLibro(id);
	}
	
	private void validar(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados,
			Integer ejemplaresRestantes, Integer autorId, Integer editorialId) throws ErrorServicio{
		
		if (isbn == null || isbn.toString().isEmpty()) {
			throw new ErrorServicio("isbn sin especificar");
		}
		
		if (titulo == null || titulo.isEmpty()) {
			throw new ErrorServicio("titulo sin especificar");
		}
		
		if (anio == null || anio.toString().isEmpty()) {
			throw new ErrorServicio("año sin especificar");
		}
		
		if (ejemplares == null || ejemplares.toString().isEmpty()) {
			throw new ErrorServicio("N° de ejemplares sin especificar");
		}
		
		if (ejemplaresPrestados == null || ejemplaresPrestados.toString().isEmpty()) {
			throw new ErrorServicio("N° de ejemplares prestados sin especificar");
		}
		
		if (ejemplaresRestantes == null || ejemplaresRestantes.toString().isEmpty()) {
			throw new ErrorServicio("N° de ejemplares restantes sin especificar");
		}
		
		if (autorId == null || autorId.toString().isEmpty()) {
			throw new ErrorServicio("autorID sin especificar");
		}
		
		if (editorialId == null || editorialId.toString().isEmpty()) {
			throw new ErrorServicio("editorialId sin especificar");
		}
	}
	
	
}
