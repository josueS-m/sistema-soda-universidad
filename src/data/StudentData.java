package data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import domain.Student;

public class StudentData {

	private static final String fileName = "Student.json";
	private static JsonUtils<Student> jsonUtils = new JsonUtils<>(fileName);

	public static List<Student> studentList = new ArrayList<>();

	//Obtiene la lista de estudiantes del archivo
	public static List<Student> getStudentList() {
		try {
			return jsonUtils.getElements(Student.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getStudentFormat(Student student) {
        return "Carné: " + student.getIdStudent() + "\nEstudiante: " + student.getName() +
               "\nCorreo Electronico: " + student.getEmail() + "\nTelefono: " + student.getTelephone() +
               "\nGénero: " + ((student.getGender() == 'M') ? "Masculino" : "Femenino") +
               "\nFecha de ingreso: " + student.getDateOfEntry() +
               "\nDinero disponible: " + student.getMoneyAvailable();
    }
	
	// Guarda un nuevo estudiante en el archivo 
	public static boolean saveStudent(Student student) {
		try {
			jsonUtils.saveElement(student);
			return true;
		} catch(IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// Edita la información de un estudiante existente en el archivo 
	public static boolean editStudent(Student student) {
		try {
			jsonUtils.editElements(student);
			return true;
		} catch(IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// Elimina un estudiante del archivo 
	public static boolean deleteStudent(Student student) {
		try {
			jsonUtils.removeElement(student);
			return true;
		} catch(IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// Verifica si existe un estudiante en una lista dada por su carné
	public static boolean issetStudentByIdStudent(String idStudent, List<Student> studentList) {
		for(Student s : studentList) {
			if(s.getIdStudent().equals(idStudent)) {
				return true;
			}
		}
		return false;
	}	
	
	// Obtiene un estudiante por su carné desde el archivo 
	public static Student getStudentById(String idStudent) {
	    List<Student> students = getStudentList();
	    for (Student student : students) {
	        if (student.getIdStudent().equals(idStudent)) {
	            return student;
	        }
	    }
	    return null; 
	}
	
	// Actualiza el saldo de un estudiante en el archivo 
	public static boolean updateStudentBalance(Student updatedStudent) {
        List<Student> students = getStudentList();
        
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getIdStudent().equals(updatedStudent.getIdStudent())) {
                students.set(i, updatedStudent);
                try {
                    jsonUtils.saveElements(students); 
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        return false;
    }
}
