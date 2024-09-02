package domain;

import java.time.LocalDate;

public class Student extends Person{
	
	private String idStudent;
	private boolean isActive;
	private LocalDate dateOfEntry;
	private double moneyAvailable;
	
	

	public Student(String idStudent, String name, String email, int telephone, char gender, boolean isActive,
			LocalDate dateOfEntry, double moneyAvailable) {
		super(name, email, telephone, gender);
		this.idStudent = idStudent;
		this.isActive = isActive;
		this.dateOfEntry = dateOfEntry;
		this.moneyAvailable = moneyAvailable;
	}	

	public String getIdStudent() {
		return idStudent;
	}

	public void setIdStudent(String idStudent) {
		this.idStudent = idStudent;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public LocalDate getDateOfEntry() {
		return dateOfEntry;
	}

	public void setDateOfEntry(LocalDate dateOfEntry) {
		this.dateOfEntry = dateOfEntry;
	}

	public double getMoneyAvailable() {
		return moneyAvailable;
	}

	public void setMoneyAvailable(double moneyAvailable) {
		this.moneyAvailable = moneyAvailable;
	}

	@Override
	public String toString() {
		return "Carné: " + getIdStudent() + super.toString() + ",  Activo: " + (isActive()?1:0) + ",  Fecha de Ingreso: " + getDateOfEntry()
				+ ",  Dinero Disponible: " + getMoneyAvailable();
	}	
	
}
