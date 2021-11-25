package egg.web.libreria.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
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
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
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

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/registrarautor")
	public String registrarAutor(ModelMap modelo, @RequestParam @Nullable String nombreInput) {
		try {
			autorServicio.crearAutor(nombreInput, true);
		} catch (ErrorServicio e) {
			modelo.put("error", e.getMessage());
			modelo.put("nombre", nombreInput);
			System.out.println("Error al registrar autor" + e.getMessage());
			return "redirect:/admin";
		}
		return "redirect:/admin";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/registrareditorial")
	public String registrarEditorial(ModelMap modelo, @RequestParam @Nullable String nombreInput) {
		try {
			editorialServicio.crearEditorial(nombreInput, true);
		} catch (ErrorServicio e) {
			modelo.put("error", e.getMessage());
			modelo.put("nombre", nombreInput);
			System.out.println("Error al registrar editorial" + e.getMessage());
			return "redirect:/admin";
		}
		return "redirect:/admin";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/registrarlibro")
	public String registrarLibro(ModelMap modelo, 
								@RequestParam(value = "inputFoto", required = false) MultipartFile inputFoto,
								@RequestParam @Nullable Long inputIsbn,
								@RequestParam @Nullable String inputTitulo, 
								@RequestParam @Nullable Integer inputAnio,
								@RequestParam @Nullable Integer inputEjemplares, 
								@RequestParam @Nullable Integer inputEjemplaresPrestados,
								@RequestParam @Nullable Integer inputAutorId,
								@RequestParam @Nullable Integer inputEditorialId,
								@RequestParam @Nullable String inputSinopsis) {
		try {
			libroServicio.crearLibro(inputFoto, inputIsbn, inputTitulo, inputAnio, inputEjemplares, inputEjemplaresPrestados,
					inputAutorId, inputEditorialId, inputSinopsis);
		} catch (ErrorServicio e) {
			modelo.put("error", e.getMessage());
			modelo.put("isbn", inputIsbn);
			modelo.put("titulo", inputTitulo);
			modelo.put("anio", inputAnio);
			modelo.put("ejemplares", inputEjemplares);
			modelo.put("ejemplaresPrestados", inputEjemplaresPrestados);
			modelo.put("autorId", inputAutorId);
			modelo.put("editorialId", inputEditorialId);
			modelo.put("sinopsis", inputSinopsis);
			System.out.println("Error al registrar libro" + e.getMessage());
			return "redirect:/admin";
		}
		return "redirect:/admin";
	}

	// Ediciones------------------------------------

	@PreAuthorize("hasRole('ROLE_ADMIN')")
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
			return "redirect:/admin";
		}
		return "redirect:/admin";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
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
			return "redirect:/admin";
		}
		return "redirect:/admin";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/editarlibro")
	public String editarLibro(ModelMap modelo,
								@RequestParam @Nullable MultipartFile inputFoto,
								@RequestParam Integer inputId,
								@RequestParam @Nullable Long inputIsbn,
								@RequestParam @Nullable String inputTitulo, 
								@RequestParam @Nullable Integer inputAnio,
								@RequestParam @Nullable Integer inputEjemplares, 
								@RequestParam @Nullable Integer inputEjemplaresPrestados,
								@RequestParam @Nullable Integer inputAutorId,
								@RequestParam @Nullable Integer inputEditorialId,
								@RequestParam @Nullable String inputSinopsis) {
		try {
			libroServicio.modificarLibro(inputFoto, inputId, inputIsbn, inputTitulo, inputAnio, inputEjemplares, inputEjemplaresPrestados,
					inputAutorId, inputEditorialId, inputSinopsis);
		} catch (ErrorServicio e) {
			modelo.put("error", e.getMessage());
			modelo.put("id", inputId);
			modelo.put("isbn", inputIsbn);
			modelo.put("titulo", inputTitulo);
			modelo.put("anio", inputAnio);
			modelo.put("ejemplares", inputEjemplares);
			modelo.put("ejemplaresPrestados", inputEjemplaresPrestados);
			modelo.put("autorId", inputAutorId);
			modelo.put("editorialId", inputEditorialId);
			modelo.put("sinopsis", inputSinopsis);
			
			System.out.println("Error al modificar autor" + e.getMessage());
			return "redirect:/admin";
		}
		return "redirect:/admin";
	}
	
	//ALTA Y BAJA--------------------------------------------
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/alta-baja-autor/{id}")
	public String altaBajaA(ModelMap modelo,
							@PathVariable("id") Integer id) {
		try {
			autorServicio.darAltaBajaAutor(id);
		} catch (ErrorServicio e) {
			modelo.put("error", e.getMessage());
			System.out.println("Error al dar de alta/baja " + e.getMessage());
			return "redirect:/admin";
		}
		return "redirect:/admin";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/alta-baja-editorial/{id}")
	public String altaBajaE(ModelMap modelo, 
							@PathVariable("id") Integer id) {
		try {
			editorialServicio.darAltaBajaEditorial(id);
		} catch (ErrorServicio e) {
			modelo.put("error", e.getMessage());
			System.out.println("Error al dar de alta/baja " + e.getMessage());
			return "redirect:/admin";
		}
		return "redirect:/admin";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/alta-baja-libro/{id}")
	public String altaBajaL(ModelMap modelo, 
							@PathVariable("id") Integer id) {
		try {
			libroServicio.darAltaBajaLibro(id);
		} catch (ErrorServicio e) {
			modelo.put("error", e.getMessage());
			System.out.println("Error al dar de alta/baja " + e.getMessage());
			return "redirect:/admin";
		}
		return "redirect:/admin";
	}
}
