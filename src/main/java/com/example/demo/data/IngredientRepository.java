package com.example.demo.data;

import com.example.demo.Ingredient;

public interface IngredientRepository {
	Ingredient findOne(String id);
	Iterable<Ingredient> findAll();
	Ingredient save(Ingredient ingredient);
}
