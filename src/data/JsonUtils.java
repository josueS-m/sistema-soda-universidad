package data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonUtils <T> {

	//Ruta del documento
	private final String filePath; 
	
	//Manejo de json
	private static final ObjectMapper mapper = new ObjectMapper()
			.registerModule(new JavaTimeModule());
	
	public JsonUtils(String route) {
		this.filePath = route;
	}
		
	@SuppressWarnings("unchecked")
	public void saveElement(T t) throws IOException {		
		List<T> temp = getElements((Class<T>) t.getClass());
		
		temp.add(t);
		mapper.writeValue(new File(filePath), temp);
	}
	
	public void saveAllElements(List<T> temp) throws IOException {
		mapper.writeValue(new File(filePath), temp);
    }
	
	public void saveElements(List<T> elements) throws IOException {
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.writeValue(new File(filePath), elements);
	}

	
	public List<T> getElements(Class<T> temp) throws IOException {
		File file = new File(filePath);
		
		if(!file.exists()) {
			return new ArrayList<>();
		}
		return mapper.readValue(file, 
				mapper.getTypeFactory()
				.constructCollectionType(List.class, temp)
				);
	}
	
	@SuppressWarnings("unchecked")
	public void editElements(T t) throws IOException {
		List<T> elements = getElements(((Class<T>) t.getClass()));
		
		for(int i = 0; i < elements.size(); i++) {
			T currentElement = elements.get(i);
			
			if(currentElement.equals(t)) {
				elements.set(i, t);
				break;
			}
		}		
		mapper.writeValue(new File(filePath), elements);
	} 
	
	@SuppressWarnings("unchecked")
	public void removeElement(T t) throws IOException {
        List<T> temp = getElements(((Class<T>) t.getClass()));        
        temp.remove(t);  
        mapper.writeValue(new File(filePath), temp);
    }
}