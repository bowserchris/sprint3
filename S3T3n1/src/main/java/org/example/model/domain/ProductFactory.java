package org.example.model.domain;

public class ProductFactory {
	
	/*
	//abstract factory that processes the input on what object to create, 
	 * tree, flower or decoration, and then calls the relevant factory specific to the object
	 * afterwards further options can be implemented on what specific product format
	 * is required (and further parameters can be included to know which type is 
	 * needed and implemented)
	*/

	
	public <T> Product createProduct(String type, String name, double price, T value) {
		Product product = null;		
		switch(type) {
			case "1", "Tree":
				return new Tree(name, price, (float) value);
			case "2", "Flower":
				return new Flower(name, price, (String) value);
			case "3", "Decoration":
				return new Decoration(name, price, (boolean) value);
			default: 
				return product;
		}
	}
	
}
