package tacos;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Ingredient {

	@Id
	private final String id;
	private final String name;
	private final Type type;

	public enum Type {
		WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
	}
}
