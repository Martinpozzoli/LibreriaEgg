package egg.web.libreria.controladores;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import egg.web.libreria.entidades.Libro;
import egg.web.libreria.errores.ErrorServicio;
import egg.web.libreria.servicios.LibroServicio;

@Controller
@RequestMapping("/home")
public class HomeController {

	@Autowired
	private LibroServicio libroServicio;

	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	@GetMapping(value = "")
	public String contenedorPrincipal(Model model) {
		try {
			List<Libro> libros = libroServicio.mostrarLibros();
			model.addAttribute("libros", libros);
		} catch (ErrorServicio e) {
			e.printStackTrace();
		}
		return "home.html";
	}
	
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	@PostMapping(value = "")
	public String contenedorPrincipal(Model model, @RequestParam @Nullable String busqueda) {
		if (busqueda == null || busqueda.equals("")) {
			String error = "Por favor ingrese el nombre del autor, la editorial o el titulo del libro que busca";
			model.addAttribute("error", error);			
		}else {
			try {
				List<Libro> libros = new ArrayList<Libro>();
				libros.addAll(libroServicio.buscarPorTitulo(busqueda));
				libros.addAll(libroServicio.buscarPorAutor(busqueda));
				libros.addAll(libroServicio.buscarPorEditorial(busqueda));
				model.addAttribute("libros", libros);
				
				if(libros.isEmpty()) {
					String error = "No se encontraron libros que coincidan con la búsqueda";
					model.addAttribute("error", error);	
				}
			} catch (ErrorServicio e) {
				e.printStackTrace();
			}
		}
		return "home.html";
	}
}
