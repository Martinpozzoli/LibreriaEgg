package egg.web.libreria.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeControlador {

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
	public String admin() {
		return "admin.html";
	}
}
