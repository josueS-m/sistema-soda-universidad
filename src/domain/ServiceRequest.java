package domain;

import java.util.List;

import data.StudentData;

public class ServiceRequest {
	
	private Student student;
	private List<Service> services;
	
	public ServiceRequest(Student student, List<Service> services) {
		super();
		this.student = student;
		this.services = services;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public List<Service> getServices() {
		return services;
	}

	public void setServices(List<Service> services) {
		this.services = services;
	}
	
	public double calculeTotal() {
        return services.stream().mapToDouble(Service::getPrice).sum();
    }

    public boolean validateFunds() {
        return student.getMoneyAvailable() >= calculeTotal();
    }

    public void confirmarRequest() throws Exception {
        if (!validateFunds()) {
            throw new Exception("Fondos insuficientes.");
        }

        student.setMoneyAvailable(student.getMoneyAvailable() - calculeTotal());
        StudentData.saveStudent(student);
    }    
   
	@Override
	public String toString() {
		return "Solicitud de " + getStudent() + ",  Servicios" + getServices();
	}

}
