package egg.web.libreria.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

	// ACCIONES-----------------------------------------


}
