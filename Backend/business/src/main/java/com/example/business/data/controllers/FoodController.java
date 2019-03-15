package com.example.business.data.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.business.data.entities.Food;
import com.example.business.data.services.FoodService;

@RestController
@RequestMapping(value="/foods")
public class FoodController {
	
	@Autowired
	FoodService foodService;
	
	@RequestMapping(method = RequestMethod.GET, path = "old/{food_id}")
	@ResponseBody
	public Optional<Food> getFood_OLD(@PathVariable int food_id){
		return foodService.oldGetFood(food_id);
	}

	@GetMapping("old/all")
	public Iterable<Food> getAllFood_OLD() {
		return foodService.oldGetAllFood();
	}

	/**
	 *
	 * @param food_id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/{food_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONObject getFoodJSONObject(@PathVariable int food_id) {
		return foodService.jsonGetFood(food_id);
	}
	
	/**
	 * 
	 * @return JSONObject 
	 */
	@RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getAllFoodJSONObject()  {
		return foodService.jsonGetAllFood();
	}

	/**
	 * Currently just takes food Object. Might need to be a JSONObject I parse if more info is required.
	 * @param newFood
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	private JSONObject createFood(@RequestBody Food newFood) {
		return foodService.createFood(newFood);
	}
	
	/**
	 * Deletes the food given their unique id
	 * @param food_id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.DELETE, path = "/delete/{food_id}", produces = MediaType.APPLICATION_JSON_VALUE) 
	@ResponseBody
	private JSONObject deleteFood(@PathVariable int food_id) {
		return foodService.deleteFood(food_id);
	}
	
	/**
	 * @param food To edit
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, path = "/edit/{food_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	private JSONObject editFood(@RequestBody Food newFood, @PathVariable int food_id) {
		return foodService.editFood(newFood, food_id);
	}
}
