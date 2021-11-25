package egg.web.libreria.controladores;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import egg.web.libreria.entidades.Prestamo;
import egg.web.libreria.entidades.Usuario;
import egg.web.libreria.errores.ErrorServicio;
import egg.web.libreria.servicios.PrestamoServicio;

@Controller
@RequestMapping("/prestamo")
public class PrestamoController {

	@Autowired
	private PrestamoServicio prestamoServicio;
	
	@Autowired
	LibroController libroController;
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/all")
	public String prestamos(Model model) {
		try {
			List<Prestamo> prestamos = prestamoServicio.mostrarPrestamos();
			model.addAttribute("prestamos", prestamos);
		}catch(ErrorServicio e) {
			e.printStackTrace();
		}
		return "prestamo.html";
	}
	
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	@GetMapping("/")
	public String detalles(HttpSession session, @RequestParam String id, Model model) throws ErrorServicio{
		Usuario user = (Usuario) session.getAttribute("usersession");
		if(user == null || !user.getId().equals(id)) {
			return "redirect:/home";
		}
		
		List<Prestamo> prestamos = prestamoServicio.buscarPorId(id);
		model.addAttribute("prestamos", prestamos);
		
		return "prestamo.html";
	}
	
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	@GetMapping("/pedir/{idLibro}/{idUsuario}")
	public String pedir(HttpSession session, @PathVariable Integer idLibro, @PathVariable String idUsuario, ModelMap model) throws ErrorServicio{
		Usuario user = (Usuario) session.getAttribute("usersession");
		if(user == null || !user.getId().equals(idUsuario)) {
			return "redirect:/home";
		}
		try {
			prestamoServicio.nuevoPrestamo(idUsuario, idLibro);
		}catch(ErrorServicio e) {
			model.put("error", e.getMessage());
			return libroController.detalles(idLibro, model);
		}
		return "redirect:/prestamo/?id=" + idUsuario;
	}
	
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	@GetMapping("/devolver/{idPrestamo}/{idUsuario}")
	public String devolver(HttpSession session, @PathVariable String idPrestamo, @PathVariable String idUsuario) throws ErrorServicio{
		Usuario user = (Usuario) session.getAttribute("usersession");
		if(user == null || !user.getId().equals(idUsuario)) {
			return "redirect:/home";
		}
		
		prestamoServicio.removerPrestamo(idPrestamo);
		
		return "redirect:/prestamo/?id=" + idUsuario;
	}
}
