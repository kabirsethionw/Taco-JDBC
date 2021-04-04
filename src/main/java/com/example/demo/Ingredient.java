package com.example.demo;
import lombok.RequiredArgsConstructor;
import lombok.Data; //the @Data annotation at the class level is provided by Lombok and tellsLombok to generate all of those missing methods as well as a constructor that acceptsall final properties as arguments.
//lombok generates all the getters, setters, constructors, overloades toString(), hashCode() and equals()

@Data
@RequiredArgsConstructor
public class Ingredient {
	private final String id;
	private final String name;
	private final Type type;
	
	public static enum Type {
		WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
	}
}
