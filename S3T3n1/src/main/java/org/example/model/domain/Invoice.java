package org.example.model.domain;

import java.util.ArrayList;
import java.util.List;

public class Invoice {
	private static int idNextNumber = 1;
	private int id;
	private List<Product> productList;
	private double totalSale = 0.0;
	private String loadedProductList = "";

	public Invoice(int id, String loadedProductList, double totalSale){
		this.id = id;
		idNextNumber = id +1;
		this.loadedProductList = loadedProductList;
		this.totalSale = totalSale;

	}
	public Invoice() {
		id = idNextNumber;
		idNextNumber++;
		productList = new ArrayList();
	}

	public List<Product> getProductList() {
		return productList;
	}

	public double getTotalSale() {
		return totalSale;
	}

	public int getId() {
		return id;
	}
	
	public void addProduct(Product p) {

		productList.add(p);
		totalSale += p.getPrice();
	}
	
	public void removeProduct(Product p) {
		if(productList.contains(p)){
			productList.remove(p);
			totalSale -= p.getPrice();
		}
	}

	public String toString() {

		if(productList == null){
			return "Id = " + this.id + " | [" + this.loadedProductList + "] | TOTAL: " +
					((float) Math.round(this.totalSale * 100) / 100) + "€";
		}else {
			return stringBuilderInAppInvoices();
		}
	}

	public String stringBuilderInAppInvoices(){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Id = ").append(this.id).append(" | [").append(this.loadedProductList);

		for (int i = 0; i < productList.size(); i++) {
			stringBuilder.append("(").append(i + 1).append(")").append(productList.get(i).getName());
			if (i < productList.size() - 1) {
				stringBuilder.append(", ");
			}
		}
		stringBuilder.append("] | TOTAL: ").append(((float) Math.round(this.totalSale * 100) / 100)).append("€");

		return stringBuilder.toString();
	}

}
