package domain;

public class Person {

	protected String name;
	protected String email;
	protected int telephone;
	protected char gender;
	
	public Person() {}

	public Person(String name, String email, int telephone, char gender) {
		super();
		this.name = name;
		this.email = email;
		this.telephone = telephone;
		this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getTelephone() {
		return telephone;
	}

	public void setTelephone(int telephone) {
		this.telephone = telephone;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {

		return "Nombre: " + getName() + ",  Correo Electrónico: " + getEmail() + ",  Teléfono: " + getTelephone()
				+ ",  Género: " + getGender();
	}

}
