package data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import domain.Student;

public class StudentData {

	private static final String fileName = "Student.json";
	private static JsonUtils<Student> jsonUtils = new JsonUtils<>(fileName);

	public static List<Student> studentList = new ArrayList<>();

	public static List<Student> getStudentList() {
		try {
			return jsonUtils.getElements(Student.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getStudentFormat(Student student) {

		return "Carné: " + student.getIdStudent() + "\nEstudiante: " + student.getName() + "\nCorreo Electronico: "
				+ student.getEmail() + "\nTelefono: " + student.getTelephone() + "\nGénero: "
				+ ((student.getGender() == 'M') ? "Masculino" : "Femenino") + "\nFecha de ingreso: "
				+ student.getDateOfEntry() + "\nDinero disponible: " + student.getMoneyAvailable();
	}
	
	public static boolean saveStudent(Student student) {
		try {
			jsonUtils.saveElement(student);
			return true;
		} catch(IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean editStudent(Student student) {
		try {
			jsonUtils.editElements(student);
			return true;
		} catch(IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean deleteStudent(Student student) {
		try {
			jsonUtils.removeElement(student);
			return true;
		} catch(IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean issetStudentByIdStudent(String idStudent, List<Student> studentList) {
		for(Student s : studentList) {
			if(s.getIdStudent().equals(idStudent)) {
				return true;
			}
		}
		return false;
	}

}
