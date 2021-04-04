package com.example.demo.data;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import com.example.demo.Ingredient;
import com.example.demo.Tacos;

@Repository
public class JdbcTacoRepository implements TacoRepository{
	@Autowired 
	private JdbcTemplate jdbc; 
	
	public JdbcTacoRepository() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Tacos save(Tacos design) {
		long tacoId = saveTacoInfo(design);
		design.setId(tacoId);
		for(Ingredient ingredient: design.getIngredients()) {
			saveIngredientToTaco(ingredient, tacoId);
		}
		return design;
		
	}
	
	private long saveTacoInfo(Tacos taco) {
		taco.setCreatedAt(new Date());
		PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
				"insert into Taco(name, createdAt) values (?,?)",
				Types.VARCHAR, Types.TIMESTAMP
				);
		pscf.setReturnGeneratedKeys(true);
		PreparedStatementCreator psc	=	pscf.newPreparedStatementCreator(Arrays.asList(taco.getName(), new Timestamp(taco.getCreatedAt().getTime())));
		KeyHolder key = new GeneratedKeyHolder(); 
		jdbc.update(psc,key); 
		return key.getKey().longValue(); 
	}
	
	private void saveIngredientToTaco(Ingredient ingredient, long tacoId) {
		jdbc.update(
				"insert into Taco_Ingredients(taco, ingredient) values (?,?)",
				tacoId, ingredient.getId()
				);
	}
}
