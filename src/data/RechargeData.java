package data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import domain.Recharge;

public class RechargeData {

    private static final String fileName = "student_recharges.json";
    private static JsonUtils<Recharge> jsonUtils = new JsonUtils<>(fileName);

    public static List<Recharge> rechargeList = new ArrayList<>();
    
    public static List<Recharge> getRechargeList() {
        try {
            return jsonUtils.getElements(Recharge.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
   
    public static boolean saveRecharge(Recharge recharge) {
        try {
            jsonUtils.saveElement(recharge);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // obtener datos de recargas por carne de estudiante(con stream)
    public static List<Recharge> getRechargesByStudentId(String studentId) {
        try {
            List<Recharge> allRecharges = jsonUtils.getElements(Recharge.class);
            return allRecharges.stream()
                    .filter(recharge -> studentId.equals(recharge.getIdStudent()))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

