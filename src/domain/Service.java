package domain;

public class Service {
	
	private String name;
	private double price;
	private String day;
	private String schedule;	
	
	public Service() {
	}
	
	public Service(String name, double price, String day, String schedule) {
		super();
		this.name = name;
		this.price = price;
		this.day = day;
		this.schedule = schedule;		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}	
	
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Service service = (Service) o;

        return name.equals(service.name);
    }

	@Override
	public String toString() {
		return "Alimento: " + getName() + ",  Precio:" + getPrice() + ",  Dia: " + getDay() 
		+ ",  Horario: " + getSchedule();
	}
			
}
