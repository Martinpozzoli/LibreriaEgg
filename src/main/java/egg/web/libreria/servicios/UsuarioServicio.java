package egg.web.libreria.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import egg.web.libreria.entidades.Rol;
import egg.web.libreria.entidades.Usuario;
import egg.web.libreria.errores.ErrorServicio;

import egg.web.libreria.repositorios.UsuarioRepositorio;


@Service
public class UsuarioServicio implements UserDetailsService{

	@Autowired
	private UsuarioRepositorio usuarioRepo;
	
	@Transactional
	public void registrarUsuario(long documento, String email, String nombre, String apellido, String telefono, String password1, String password2) throws ErrorServicio {
		
		
		validar(documento,email,nombre,apellido,telefono,password1,password2);
			
		Usuario usuario = new Usuario();
		usuario.setDocumento(documento);
		usuario.setEmail(email);
		usuario.setNombre(nombre);
		usuario.setApellido(apellido);
		usuario.setTelefono(telefono);
		String encriptada = new BCryptPasswordEncoder().encode(password1);
		usuario.setPassword(encriptada);
		
		usuarioRepo.save(usuario);
	}
	
	@Transactional
	public void modificarUsuario(String id, long documento, String email, String nombre, String apellido, String telefono, String password1, String password2) throws ErrorServicio {
	
			validar(documento,email,nombre,apellido,telefono,password1,password2);
		
			Optional<Usuario> respuesta= usuarioRepo.findById(id);
			if (respuesta.isPresent()) {
				
				Usuario usuario = respuesta.get();
				
				usuario.setDocumento(documento);
				usuario.setEmail(email);
				usuario.setNombre(nombre);
				usuario.setApellido(apellido);
				usuario.setTelefono(telefono);
				String encriptada = new BCryptPasswordEncoder().encode(password1);
				usuario.setPassword(encriptada);
				
				usuarioRepo.save(usuario);
			}else {
				throw new ErrorServicio("No se encontró el usuario con id= " + id);
			}	
	}
	
	private void validar(long documento, String email, String nombre, String apellido, String telefono, String password1, String password2) throws ErrorServicio{
		
		if (String.valueOf(documento).isBlank() || String.valueOf(documento).length() != 8) {
			throw new ErrorServicio("DNI sin especificar o incorrecto");
		}
		
		if (email == null || email.isEmpty()) {
			throw new ErrorServicio("Email sin especificar");
		}
		
		if (usuarioRepo.findByEmail(email) != null) {
			throw new ErrorServicio("Este email ya se encuentra en uso");
		}
		
		if (nombre == null || nombre.isEmpty()) {
			throw new ErrorServicio("Nombre sin especificar");
		}
		
		if (apellido == null || apellido.isEmpty()) {
			throw new ErrorServicio("Apellido sin especificar");
		}
		
		if (telefono == null || telefono.isEmpty()) {
			throw new ErrorServicio("Telefono sin especificar");
		}
		
		if (password1 == null || password1.isEmpty()) {
			throw new ErrorServicio("Contraseña sin especificar");
		}
		
		if (!password1.equals(password2)) {
			throw new ErrorServicio("No coinciden las contraseñas");
		}
		
	}
	//SEGURIDAD----------------------------------------
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepo.findByEmail(email);
		if (usuario != null) {
			List<GrantedAuthority> permisos = new ArrayList<GrantedAuthority>();
			GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_"+Rol.USER.name());
			GrantedAuthority p2 = new SimpleGrantedAuthority("ROLE_"+Rol.ADMIN.name());
			if(usuario.getRol() == null || usuario.getRol().equals(Rol.USER)) {
				permisos.add(p1);
			}else if(usuario.getRol().equals(Rol.ADMIN)) {
				permisos.add(p2);
			}
			//Aca voy a guardar los datos de la sesión para que puedan ser utilizados:
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpSession session = attr.getRequest().getSession(true);
			session.setAttribute("usersession", usuario);
			
			User user = new User(usuario.getEmail(), usuario.getPassword(), permisos);
			return user;
		} else {
			return null;
		}
	}
}
