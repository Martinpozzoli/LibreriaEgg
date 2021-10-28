package egg.web.libreria;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import egg.web.libreria.entidades.Autor;
import egg.web.libreria.servicios.AutorServicio;

@SpringBootApplication
@ComponentScan({"egg.web.libreria"})
@EntityScan("egg.web.libreria")
@EnableJpaRepositories("egg.web.libreria")
public class LibreriaApplication implements CommandLineRunner{

		
	public static void main(String[] args) {
		SpringApplication.run(LibreriaApplication.class, args);
	
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		AutorServicio as = new AutorServicio();
		
		as.crearAutor("Superman", false);
		List<Autor> lista = as.mostrarAutores();
		System.out.println(lista.toString());
	}

}
