package egg.web.libreria.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import egg.web.libreria.errores.ErrorServicio;
import egg.web.libreria.servicios.AutorServicio;
import egg.web.libreria.servicios.EditorialServicio;
import egg.web.libreria.servicios.LibroServicio;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	AutorServicio autorServicio;

	@Autowired
	EditorialServicio editorialServicio;

	@Autowired
	LibroServicio libroServicio;

	//SE LLENAN LAS TABLAS-------------------------------------
	
	@GetMapping(value = "")
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
	
	// Registros--------------------------

	@PostMapping("/registrarautor")
	public String registrarAutor(ModelMap modelo, @RequestParam @Nullable String nombreInput) {
		try {
			autorServicio.crearAutor(nombreInput, true);
		} catch (ErrorServicio e) {
			modelo.put("error", e.getMessage());
			modelo.put("nombre", nombreInput);
			System.out.println("Error al registrar autor" + e.getMessage());
			return "../static/registrar.html";
		}
		return "../static/registrar.html";
	}

	@PostMapping("/registrareditorial")
	public String registrarEditorial(ModelMap modelo, @RequestParam @Nullable String nombreInput) {
		try {
			editorialServicio.crearEditorial(nombreInput, true);
		} catch (ErrorServicio e) {
			modelo.put("error", e.getMessage());
			modelo.put("nombre", nombreInput);
			System.out.println("Error al registrar editorial" + e.getMessage());
			return "../static/registrar.html";
		}
		return "../static/registrar.html";
	}

	@PostMapping("/registrarlibro")
	public String registrarLibro(ModelMap modelo, 
								@RequestParam @Nullable MultipartFile inputFoto,
								@RequestParam @Nullable Long inputIsbn,
								@RequestParam @Nullable String inputTitulo, 
								@RequestParam @Nullable Integer inputAnio,
								@RequestParam @Nullable Integer inputEjemplares, 
								@RequestParam @Nullable Integer inputEjemplaresPrestados,
								@RequestParam @Nullable Integer inputEjemplaresRestantes, 
								@RequestParam @Nullable Integer inputAutorId,
								@RequestParam @Nullable Integer inputEditorialId) {
		try {
			libroServicio.crearLibro(inputFoto, inputIsbn, inputTitulo, inputAnio, inputEjemplares, inputEjemplaresPrestados,
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
			return "../static/registrar.html";
		}
		return "../static/registrar.html";
	}

	// Ediciones------------------------------------

	@PostMapping("/editarautor")
	public String editarAutor(ModelMap modelo, @RequestParam @Nullable Integer idInput,
			@RequestParam String nombreInput) {
		try {
			autorServicio.modificarAutor(idInput, nombreInput, true);
		} catch (ErrorServicio e) {
			modelo.put("error", e.getMessage());
			modelo.put("id", idInput);
			modelo.put("nombre", nombreInput);
			System.out.println("Error al modificar autor" + e.getMessage());
			return "../static/registrar.html";
		}
		return "../static/registrar.html";
	}

	@PostMapping("/editareditorial")
	public String editarEditorial(ModelMap modelo, 
									@RequestParam @Nullable Integer idInput,
									@RequestParam String nombreInput) {
		try {
			editorialServicio.modificarEditorial(idInput, nombreInput, true);
		} catch (ErrorServicio e) {
			modelo.put("error", e.getMessage());
			modelo.put("id", idInput);
			modelo.put("nombre", nombreInput);
			System.out.println("Error al modificar editorial" + e.getMessage());
			return "../static/registrar.html";
		}
		return "../static/registrar.html";
	}

	@PostMapping("/editarlibro")
	public String editarLibro(ModelMap modelo,
								@RequestParam @Nullable MultipartFile inputFoto,
								@RequestParam Integer inputId,
								@RequestParam @Nullable Long inputIsbn,
								@RequestParam @Nullable String inputTitulo, 
								@RequestParam @Nullable Integer inputAnio,
								@RequestParam @Nullable Integer inputEjemplares, 
								@RequestParam @Nullable Integer inputEjemplaresPrestados,
								@RequestParam @Nullable Integer inputEjemplaresRestantes, 
								@RequestParam @Nullable Integer inputAutorId,
								@RequestParam @Nullable Integer inputEditorialId) {
		try {
			libroServicio.modificarLibro(inputFoto, inputId, inputIsbn, inputTitulo, inputAnio, inputEjemplares, inputEjemplaresPrestados,
					inputEjemplaresRestantes, inputAutorId, inputEditorialId);
		} catch (ErrorServicio e) {
			modelo.put("error", e.getMessage());
			modelo.put("id", inputId);
			modelo.put("isbn", inputIsbn);
			modelo.put("titulo", inputTitulo);
			modelo.put("anio", inputAnio);
			modelo.put("ejemplares", inputEjemplares);
			modelo.put("ejemplaresPrestados", inputEjemplaresPrestados);
			modelo.put("ejemplaresRestantes", inputEjemplaresRestantes);
			modelo.put("autorId", inputAutorId);
			modelo.put("editorialId", inputEditorialId);
			System.out.println("Error al modificar autor" + e.getMessage());
			return "../static/registrar.html";
		}
		return "../static/registrar.html";
	}
	
	//ALTA Y BAJA--------------------------------------------
	
	@GetMapping("/alta-baja-autor/{id}")
	public String altaBajaA(ModelMap modelo,
							@PathVariable("id") Integer id) {
		System.out.println(id);
		try {
			autorServicio.darAltaBajaAutor(id);
		} catch (ErrorServicio e) {
			modelo.put("error", e.getMessage());
			System.out.println("Error al dar de alta/baja " + e.getMessage());
			return "../static/registrar.html";
		}
		return "../static/registrar.html";
	}
	
	@GetMapping("/alta-baja-editorial/{id}")
	public String altaBajaE(ModelMap modelo, 
							@PathVariable("id") Integer id) {
		try {
			editorialServicio.darAltaBajaEditorial(id);
		} catch (ErrorServicio e) {
			modelo.put("error", e.getMessage());
			System.out.println("Error al dar de alta/baja " + e.getMessage());
			return "../static/registrar.html";
		}
		return "../static/registrar.html";
	}
	
	@GetMapping("/alta-baja-libro/{id}")
	public String altaBajaL(ModelMap modelo, 
							@PathVariable("id") Integer id) {
		try {
			libroServicio.darAltaBajaLibro(id);
		} catch (ErrorServicio e) {
			modelo.put("error", e.getMessage());
			System.out.println("Error al dar de alta/baja " + e.getMessage());
			return "../static/registrar.html";
		}
		return "../static/registrar.html";
	}
}
