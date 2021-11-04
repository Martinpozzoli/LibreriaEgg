package egg.web.libreria.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import egg.web.libreria.errores.ErrorServicio;
import egg.web.libreria.servicios.*;

@Controller
@RequestMapping("/")
public class HomeControlador {

	@Autowired
	AutorServicio autorServicio;

	@Autowired
	EditorialServicio editorialServicio;

	@Autowired
	LibroServicio libroServicio;

	// DIRECCIONES PRINCIPALES-----------------------------

	@GetMapping(value = "/index")
	public String index() {
		return "index.html";
	}

	@GetMapping(value = "/login")
	public String login() {
		return "login.html";
	}

	@GetMapping(value = "/registro")
	public String registro() {
		return "registro.html";
	}

	@GetMapping(value = "/admin")
	public String admin(Model model) {
		try {
			model.addAttribute("autores", autorServicio.mostrarAutores());
		} catch (ErrorServicio e) {
			e.printStackTrace();
		}
		try {
			model.addAttribute("editoriales", editorialServicio.mostrarEditoriales());
		} catch (ErrorServicio e) {
			e.printStackTrace();
		}
		try {
			model.addAttribute("libros", libroServicio.mostrarLibros());
		} catch (ErrorServicio e) {
			e.printStackTrace();
		}
		return "admin.html";
	}

	// ACCIONES-----------------------------------------
	@PostMapping("/registrarautor")
	public String registrarAutor(ModelMap modelo, 
								@RequestParam @Nullable String nombreInput) {
		try {
			autorServicio.crearAutor(nombreInput, true);
		} catch (ErrorServicio e) {
			modelo.put("error", e.getMessage());
			modelo.put("nombre", nombreInput);
			System.out.println("Error al registrar autor" + e.getMessage());
			return "../static/registrarautor.html";
		}
		return "../static/registrarautor.html";
	}
	
	@PostMapping("/registrareditorial")
	public String registrarEditorial(ModelMap modelo, 
								@RequestParam @Nullable String nombreInput) {
		try {
			editorialServicio.crearEditorial(nombreInput, true);
		} catch (ErrorServicio e) {
			modelo.put("error", e.getMessage());
			modelo.put("nombre", nombreInput);
			System.out.println("Error al registrar editorial" + e.getMessage());
			return "../static/registrarautor.html";
		}
		return "../static/registrarautor.html";
	}
	
	@PostMapping("/registrarlibro")
	public String registrarLibro(ModelMap modelo, 
								@RequestParam @Nullable Long inputIsbn, 
								@RequestParam @Nullable String inputTitulo, 
								@RequestParam @Nullable Integer inputAnio, 
								@RequestParam @Nullable Integer inputEjemplares, 
								@RequestParam @Nullable Integer inputEjemplaresPrestados,
								@RequestParam @Nullable Integer inputEjemplaresRestantes, 
								@RequestParam @Nullable Integer inputAutorId, 
								@RequestParam @Nullable Integer inputEditorialId) {
		try {
			libroServicio.crearLibro(inputIsbn, inputTitulo, inputAnio, inputEjemplares, inputEjemplaresPrestados,
					inputEjemplaresRestantes, inputAutorId, inputEditorialId);
		} catch (ErrorServicio e) {
			modelo.put("error", e.getMessage());
			modelo.put("isbn", inputIsbn);
			modelo.put("titulo", inputTitulo);
			modelo.put("anio", inputAnio);
			modelo.put("ejemplares", inputEjemplares);
			modelo.put("ejemplaresPrestados", inputEjemplaresPrestados);
			modelo.put("ejemplaresRestantes", inputEjemplaresRestantes);
			modelo.put("autorId", inputAutorId);
			modelo.put("editorialId", inputEditorialId);
			System.out.println("Error al registrar libro" + e.getMessage());
			return "../static/registrarautor.html";
		}
		return "../static/registrarautor.html";
	}

}
