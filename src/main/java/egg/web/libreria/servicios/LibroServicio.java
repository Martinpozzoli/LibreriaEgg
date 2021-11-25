package egg.web.libreria.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import egg.web.libreria.entidades.Foto;
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
	
	@Autowired
	private FotoServicio fotoServicio;

	public void crearLibro(MultipartFile archivo, Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados,
			Integer autorId, Integer editorialId, String sinopsis) throws ErrorServicio{
		
		validar(isbn, titulo, anio, ejemplares, ejemplaresPrestados, autorId, editorialId);
		
		Libro libro = new Libro();
		libro.setIsbn(isbn);
		libro.setTitulo(titulo);
		libro.setAnio(anio);
		libro.setEjemplares(ejemplares);
		libro.setEjemplaresPrestados(ejemplaresPrestados);
		libro.setEjemplaresRestantes();
		libro.setAlta(true);
		libro.setAutor(autorRepo.buscarAutorId(autorId));
		libro.setEditorial(editorialRepo.buscarEditorialId(editorialId));
		libro.setSinopsis(sinopsis);

		Foto foto = fotoServicio.guardar(archivo);
		libro.setFoto(foto);
		
		
		libroRepo.save(libro);
	}
	
	
	public void modificarLibro(MultipartFile archivo, Integer id, Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados,
			Integer autorId, Integer editorialId, String sinopsis) throws ErrorServicio {
		
		validar(isbn, titulo, anio, ejemplares, ejemplaresPrestados,
				autorId, editorialId);
		
		if(!libroRepo.findById(id).isPresent()) {
			throw new ErrorServicio("No se encontró el libro con id = " + id);
		}
		
		Libro libro = libroRepo.findById(id).get();
		
		libro.setIsbn(isbn);
		libro.setTitulo(titulo);
		libro.setAnio(anio);
		libro.setEjemplares(ejemplares);
		libro.setEjemplaresPrestados(ejemplaresPrestados);
		libro.setEjemplaresRestantes();
		libro.setAlta(true);
		libro.setAutor(autorRepo.buscarAutorId(autorId));
		libro.setEditorial(editorialRepo.buscarEditorialId(editorialId));
		libro.setSinopsis(sinopsis);
		
		Integer idFoto = null;
		if(libro.getFoto() != null) {
			idFoto = libro.getFoto().getId();
		}
		if(archivo.getSize() > 0) {
			Foto foto = fotoServicio.actualizar(idFoto, archivo);
			libro.setFoto(foto);
		}
		
		libroRepo.save(libro);
		
	}
	
	@Transactional
	public void darAltaBajaLibro(Integer id) throws ErrorServicio{
			if(!libroRepo.findById(id).isPresent()) {
				throw new ErrorServicio("No se encontró el libro con id = " + id);
			}
		if (libroRepo.findById(id).get().isAlta()) {
			libroRepo.altabaja(false, id);
		}else {
			libroRepo.altabaja(true, id);
		}
	}
	
	@Transactional
	public void prestarLibro(Integer id) throws ErrorServicio{
		try{
			Libro l = buscarPorId(id).get();
			l.setEjemplaresPrestados(l.getEjemplaresPrestados() + 1);
			l.setEjemplaresRestantes();
			libroRepo.save(l);
		}catch(Exception e) {
			throw new ErrorServicio("No se encontró el libro con id = " + id);
		}
	}
	
	@Transactional
	public void returnLibro(Integer id) throws ErrorServicio{
		try{
			Libro l = buscarPorId(id).get();
			l.setEjemplaresPrestados(l.getEjemplaresPrestados() - 1);
			l.setEjemplaresRestantes();
			libroRepo.save(l);
		}catch(Exception e) {
			throw new ErrorServicio("No se encontró el libro con id = " + id);
		}
	}
	
	@Transactional
	public void quitarLibro(Integer id) throws ErrorServicio{
		if(!libroRepo.findById(id).isPresent()) {
			throw new ErrorServicio("No se encontró el libro con id = " + id);
		}
		libroRepo.eliminarLibro(id);
	}
	
	public List<Libro> mostrarLibros() throws ErrorServicio{
		return libroRepo.listarLibros();
	}
	
	public Optional<Libro> buscarPorId(Integer id) {
		return libroRepo.findById(id);
	}
	
	public List<Libro> buscarPorTitulo(String busqueda) throws ErrorServicio{
		return libroRepo.listarLibrosTitulo(busqueda);
	}
	
	public List<Libro> buscarPorAutor(String busqueda) throws ErrorServicio{
		return libroRepo.listarLibrosAutor(busqueda);
	}
	
	public List<Libro> buscarPorEditorial(String busqueda) throws ErrorServicio{
		return libroRepo.listarLibrosEditorial(busqueda);
	}
	
	private void validar(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados,
			Integer autorId, Integer editorialId) throws ErrorServicio{
		
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
		
		if (autorId == null || autorId.toString().isEmpty()) {
			throw new ErrorServicio("autorID sin especificar");
		}
		
		if (editorialId == null || editorialId.toString().isEmpty()) {
			throw new ErrorServicio("editorialId sin especificar");
		}
	}
	
	
}
