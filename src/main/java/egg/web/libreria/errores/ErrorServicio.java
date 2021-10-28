package egg.web.libreria.errores;

@SuppressWarnings("serial")
public class ErrorServicio extends Exception{

	public ErrorServicio(String msn) {
		super(msn);
	}
}
