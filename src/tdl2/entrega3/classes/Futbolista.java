package tdl2.entrega3.classes;

public class Futbolista {
	private int ID;
	private String nombre;
	private String apellido;
	private int docId;
	private int telefono;
	private String email;
	private Pais pais;

	public Futbolista() {
		super();
	}

	public Futbolista(int ID, String nombre, String apellido, int docId, int telefono, String email, Pais pais) {
		super();
		this.ID = ID;
		this.nombre = nombre;
		this.apellido = apellido;
		this.docId = docId;
		this.telefono = telefono;
		this.email = email;
		this.pais = pais;
	}

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public int getDocId() {
		return docId;
	}

	public void setDocId(int docId) {
		this.docId = docId;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public void setPais(String nombre, String idioma) {
		Pais p = new Pais(nombre, idioma);
		this.pais = p;
	}
}
