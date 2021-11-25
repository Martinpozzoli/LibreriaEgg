package egg.web.libreria.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import egg.web.libreria.errores.ErrorServicio;
import egg.web.libreria.servicios.*;

@Controller
@RequestMapping("/")
public class MainController {

	@Autowired
	AutorServicio autorServicio;

	@Autowired
	EditorialServicio editorialServicio;

	@Autowired
	LibroServicio libroServicio;
	
	@Autowired
	UsuarioServicio usuarioServicio;

	// DIRECCIONES PRINCIPALES-----------------------------

	@GetMapping(value = "/index")
	public String index() {
		return "index.html";
	}

	@GetMapping(value = "/registro")
	public String registro() {
		return "registro.html";
	}

	// ACCIONES-----------------------------------------
	
	@GetMapping(value = "/login")
	public String login(@RequestParam(required = false) String error, ModelMap model) {
		if(error != null) {
			model.put("error", "Correo o contraseña incorrectos");
		}
		return "login.html";
	}

	@PostMapping("/registrar")
	public String registrar(ModelMap modelo,
							@RequestParam @Nullable Long inputDocumento,
							@RequestParam @Nullable String inputEmail,
							@RequestParam @Nullable String inputNombre, 
							@RequestParam @Nullable String inputApellido,
							@RequestParam @Nullable String inputTelefono,
							@RequestParam @Nullable String inputPassword1, 
							@RequestParam @Nullable String inputPassword2) {
		try {
			usuarioServicio.registrarUsuario(inputDocumento,inputEmail,inputNombre,inputApellido,inputTelefono,inputPassword1, inputPassword2);
		} catch (ErrorServicio e) {
			modelo.put("error", e.getMessage());
			modelo.put("documento", inputDocumento);
			modelo.put("email", inputEmail);
			modelo.put("nombre", inputNombre);
			modelo.put("apellido", inputApellido);
			modelo.put("telefono", inputTelefono);
			modelo.put("pass1", inputPassword1);
			modelo.put("pass2", inputPassword2);
			return "registro.html";
		}
		return "index.html";
	}
}
