package domain;

import java.time.LocalDate;

public class Recharge {
	
	private String idStudent;
	private double amount;
	private LocalDate rechargeDate;
	
	public Recharge(String idStudent, double amount, LocalDate rechargeDate) {
		super();
		this.idStudent = idStudent;
		this.amount = amount;
		this.rechargeDate = rechargeDate;
	}

	public String getIdStudent() {
		return idStudent;
	}

	public void setIdStudent(String idStudent) {
		this.idStudent = idStudent;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LocalDate getRechargeDate() {
		return rechargeDate;
	}

	public void setRechargeDate(LocalDate rechargeDate) {
		this.rechargeDate = rechargeDate;
	}
	
	@Override
	public String toString() {
		return "Carné: " + getIdStudent() + ",  recarga: " + getAmount() + ",  fecha de recarga: "
				+ getRechargeDate();
	}
	
	

}
