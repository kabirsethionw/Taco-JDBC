package com.example.demo.data;

import org.springframework.stereotype.Repository;

import com.example.demo.Ingredient;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@Repository // automatically discovered by spring component-scanning and instantiated as bean in spring app context
public class JdbcIngredientRepository implements IngredientRepository { // when JdbcIngredientRepository bean is created, spring injects
	// the JdbcTemple via @Autowired annotated constructor below
	// the JdbcTemplate instance variable assigned to the class variable is used by methods below to query/manipulate the db 
	private JdbcTemplate jdbc;
	
	@Autowired
	public JdbcIngredientRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public Ingredient findOne(String id) {
		// TODO Auto-generated method stub
		return jdbc.queryForObject(
				"select id, name, type from Ingredient where id=?",
				this::mapRowToIngredient
				,id);
	}

	@Override
	public Iterable<Ingredient> findAll() {
		// TODO Auto-generated method stub
		return jdbc.query("select id, name, type from Ingredient", this::mapRowToIngredient);
	}

	@Override
	public Ingredient save(Ingredient ingredient) {
		// TODO Auto-generated method stub
		jdbc.update("insert int Ingredient (id, name, type) values (?,?,?)",
				ingredient.getId(),
				ingredient.getName(),
				ingredient.getType().toString()
			);
		return ingredient;
	}
	
	private Ingredient mapRowToIngredient(ResultSet res, int rowNum) throws SQLException {
		return new Ingredient(res.getString("id"), res.getString("name"), Ingredient.Type.valueOf(res.getString("type")));
	}
	
	// if we use RowMapper, pre java 8 row mapper
//	@Override
//	public Ingredient findOne(String id) {
//	 return jdbc.queryForObject(
//	 "select id, name, type from Ingredient where id=?",
//	 new RowMapper<Ingredient>() {
//	 public Ingredient mapRow(ResultSet rs, int rowNum)
//	 throws SQLException {
//	 return new Ingredient(
//	 rs.getString("id"),
//	 rs.getString("name"),
//	 Ingredient.Type.valueOf(rs.getString("type")));
//	 };
//	 }, id);
//	}

}
