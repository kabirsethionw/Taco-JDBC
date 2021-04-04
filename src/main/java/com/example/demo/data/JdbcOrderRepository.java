package com.example.demo.data;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.example.demo.Order;
import com.example.demo.Tacos;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class JdbcOrderRepository implements OrderRepository {
	
	private SimpleJdbcInsert orderInserter;
	private SimpleJdbcInsert tacoOrderInserter;
	private ObjectMapper objectMapper;
	
	@Autowired
	public JdbcOrderRepository(JdbcTemplate jdbc) {
		this.orderInserter = new SimpleJdbcInsert(jdbc).withTableName("Taco_Order").usingGeneratedKeyColumns("id");
		this.tacoOrderInserter = new SimpleJdbcInsert(jdbc).withTableName("Taco_Order_Tacos");
		this.objectMapper = new ObjectMapper();
	}

	@Override
	public Order save(Order order) {
		order.setPlacedAt(new Date());
		long orderId = saveOrderDetails(order);
		order.setId(orderId);
		List<Tacos> tacos = order.getTacos();
		for(Tacos taco : tacos) {
			saveTacoToOrder(taco, orderId);
		}
		return order;
	}

	private long saveOrderDetails(Order order) {
			@SuppressWarnings("unchecked")
			Map<String, Object> values = objectMapper.convertValue(order, Map.class);
			values.put("placedAt", order.getPlacedAt());
			long orderId = orderInserter.executeAndReturnKey(values).longValue();
			return orderId;
	}
	
	private void saveTacoToOrder(Tacos taco, long orderId) {
		 Map<String, Object> values = new HashMap<>();
		 values.put("tacoOrder", orderId);
		 values.put("taco", taco.getId());
		 tacoOrderInserter.execute(values);
		 }
}
