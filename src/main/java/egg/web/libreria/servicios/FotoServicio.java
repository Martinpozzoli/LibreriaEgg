package egg.web.libreria.servicios;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import egg.web.libreria.entidades.Foto;
import egg.web.libreria.errores.ErrorServicio;
import egg.web.libreria.repositorios.FotoRepositorio;


@Service
public class FotoServicio {

	@Autowired
	private FotoRepositorio fotoRepo;
	
	public Foto guardar(MultipartFile archivo) throws ErrorServicio{
		if(archivo != null) {
			try {
				Foto foto = new Foto();
				foto.setMime(archivo.getContentType());
				foto.setNombre(archivo.getName());
				foto.setContenido(archivo.getBytes());
				
				return fotoRepo.save(foto);
			}catch(Exception e) {
				System.out.println("No se pudo guardar la foto " + e.getMessage());
			}
		}
		return null;
	}
	
	public Foto actualizar(Integer idFoto, MultipartFile archivo) throws ErrorServicio{
		if(archivo != null) {
			try {
				Foto foto = new Foto();
				if(idFoto != null) {
					Optional<Foto> respuesta = fotoRepo.findById(idFoto);
					if(respuesta.isPresent()) {
						foto = respuesta.get();
					}
				}
				foto.setMime(archivo.getContentType());
				foto.setNombre(archivo.getName());
				foto.setContenido(archivo.getBytes());
				
				return fotoRepo.save(foto);
			}catch(Exception e) {
				System.out.println("No se pudo actualizar la foto " + e.getMessage());
			}
		}
		return null;
	}
}
