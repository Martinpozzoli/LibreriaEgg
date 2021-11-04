package egg.web.libreria;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SeguridadConfiguracion extends WebSecurityConfigurerAdapter{

//	@Autowired
//	UsuarioServicio usuarioServicio;
//	
//	@Autowired
//	public void ConfigureGlobal(AuthenticationManagerBuilder auth) throws Exception{
//		auth.userDetailsService(usuarioServicio)
//		.passwordEncoder(new BCryptPasswordEncoder());
//	}
//	
	@Override
    protected void configure(HttpSecurity http) throws Exception{
//		
//		http.headers().frameOptions().sameOrigin().and()
//				.authorizeRequests()
//					.antMatchers("/css/*", "/js/*", "/img/*")
//					.permitAll()
//				.and().formLogin()
//					.loginPage("/login")
//						.loginProcessingUrl("/logincheck")
//						.usernameParameter("email")
//						.passwordParameter("password")
//						.defaultSuccessUrl("/home")
//						.permitAll()
//					.and().logout()
//						.logoutUrl("/logout")
//						.logoutSuccessUrl("/login?logout")
//						.permitAll().and().csrf().disable();
	 //para desactivar el login obligatorio de momento
     http.httpBasic().disable();
     //para evitar errores en la redireccion a distintas url sin permisos
     http.csrf().disable();
    }
}
