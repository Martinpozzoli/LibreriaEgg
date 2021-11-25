package egg.web.libreria.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import egg.web.libreria.entidades.Libro;
import egg.web.libreria.errores.ErrorServicio;
import egg.web.libreria.servicios.LibroServicio;

@Controller
@RequestMapping("/libro")
public class LibroController {

	@Autowired
	private LibroServicio libroServicio;
	
	@GetMapping("/detalles/{id}")
	public String detalles(@PathVariable Integer id, ModelMap model) throws ErrorServicio{
		Libro libro = libroServicio.buscarPorId(id).get();
		model.addAttribute("libro", libro);
		if(libro.getEjemplaresRestantes() == 0) {
			String error = "No quedan ejemplares disponibles";
			model.addAttribute("error", error);
		}
		return "detalles.html";
	}
	
	@GetMapping("/portada/{id}")
	public ResponseEntity<byte[]> fotoPortada (@PathVariable Integer id) throws ErrorServicio{
		try {
		Libro libro = libroServicio.buscarPorId(id).get();
		if (libro.getFoto() == null) {
			throw new ErrorServicio("Libro sin portada");
		}
		byte[] foto = libro.getFoto().getContenido();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		
		return new ResponseEntity<>(foto, headers, HttpStatus.OK);
		}catch(ErrorServicio e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
