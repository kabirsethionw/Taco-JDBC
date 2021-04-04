package com.example.demo;

import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data // this generated appropriate javabean methods at runtime, all the checkbox there and text field correspond to attributes of Tacos object
public class Tacos {
//Validation API and Hibernate's implementation of Validation API is added to the project as dependencies by springboot's web starter
// declaring validation rules
@NotNull 
private long id; // we don't need to create getter and setter for id and createAt, lombok will handle that. 	
//@NotNull
private Date createdAt;
@NotNull 
@Size(min=5, message="Name must be at least 5 characters long")
private String name;
@Size(min=1, message="You must choose at least 1 ingredient")
private List<Ingredient> ingredients;

}