package egg.web.libreria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import egg.web.libreria.servicios.UsuarioServicio;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SeguridadConfiguracion extends WebSecurityConfigurerAdapter{

	@Autowired
	private UsuarioServicio usuarioServicio;
	
	@Autowired
	public void ConfigureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(usuarioServicio)
		.passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Override
    protected void configure(HttpSecurity http) throws Exception{
		
		http.headers().frameOptions().sameOrigin().and()
				.authorizeRequests()
					.antMatchers("/css/**", "/js/**", "/img/**", "/index", "/").permitAll()
					.antMatchers("/admin/**").hasRole("ADMIN")
				.and().formLogin()
					.loginPage("/login")
						.loginProcessingUrl("/logincheck")
						.usernameParameter("useremail")
						.passwordParameter("password")
						.defaultSuccessUrl("/home")
						.permitAll()
					.and().logout()
						.logoutUrl("/logout")
						.logoutSuccessUrl("/")
						.permitAll().and().csrf().disable();
		
		
//	 //para desactivar el login obligatorio de momento
//     http.httpBasic().disable();
//     //para evitar errores en la redireccion a distintas url sin permisos
//     http.csrf().disable();
    }
}
