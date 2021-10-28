package egg.web.libreria.controladores;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import egg.web.libreria.repositorios.AutorRepositorio;

@Controller
@RequestMapping("/admin")
public class AutorControlador {
	
	@Autowired
	AutorRepositorio autorRepo;

	@GetMapping(value = "/admin")
	public String lista(Model model) {
		model.addAttribute("list", autorRepo.findAll());
		System.out.println(autorRepo.findAll().toString());
		return "admin.html";
	}
	
}
