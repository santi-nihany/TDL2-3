package tdl2.entrega3.classes;

public class Pais {
	private String nombre;
	private String idioma;
	private int id;

	public Pais() {
		super();
	}

	public Pais(int id, String nombre, String idioma) {
		super();
		this.nombre = nombre;
		this.idioma = idioma;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
}
