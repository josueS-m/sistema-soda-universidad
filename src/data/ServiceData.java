package data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import domain.Service;

public class ServiceData {
	
	//Guarda el servicio basado en el día y horario del servicio
	public static boolean saveService(Service service) {
		
        String fileName = getFileName(service.getDay(), service.getSchedule());        
        JsonUtils<Service> jsonUtils = new JsonUtils<>(fileName);
        
        try {
            jsonUtils.saveElement(service);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

	public static String getServiceFormat(Service service) {
		return "Alimento: " + service.getName() + "\nPrecio: " + service.getPrice() + 
				"\nDia" + service.getDay() + "\nHorario" + service.getSchedule();
	}

	// Obtiene la lista de servicios basados en el día y horario
    public static List<Service> getServicesByDayAndSchedule(String day, String schedule) {
    	
    	 String fileName = getFileName(day, schedule);
         JsonUtils<Service> jsonUtils = new JsonUtils<>(fileName);
         
         try {
        	 System.out.println("getServicesByDayAndSchedule = true");
             return jsonUtils.getElements(Service.class);              
         } catch (IOException e) {
             e.printStackTrace();
             return new ArrayList<>();
         }
    }
    
    // Edita un servicio en el archivo
    public static boolean editService(Service service) {
        String fileName = getFileName(service.getDay(), service.getSchedule());
        JsonUtils<Service> jsonUtils = new JsonUtils<>(fileName);
        try {
            jsonUtils.editElements(service);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Elimina el servicio del archivo
    public static boolean removeService(Service service) {
        String fileName = getFileName(service.getDay(), service.getSchedule());
        JsonUtils<Service> jsonUtils = new JsonUtils<>(fileName);
        
        try {
            jsonUtils.removeElement(service);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Genera el nombre del archivo
    public static String getFileName(String day, String schedule) {
        String dayInEnglish = translateDayToEnglish(day);
        String scheduleInEnglish = schedule.equals("Desayuno") ? "breakfast" : "lunch";
        return dayInEnglish + "_" + scheduleInEnglish + ".json";
    }

    // Traduce el día de la semana
    public static String translateDayToEnglish(String day) {
    	
        switch (day.toLowerCase()) {
            case "lunes":
                return "monday";
            case "martes":
                return "tuesday";
            case "miércoles":
                return "wednesday";
            case "jueves":
                return "thursday";
            case "viernes":
                return "friday";
            default:
                return "";
        }
    }
}
