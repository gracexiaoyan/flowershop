package com.grace.flowerShop.model;

/**
 * Bundle bean
 * @author Grace xy Wang
 *
 */
public class Bundle {
	// the number of flowers per bundle
	private int numOfFlower;
	// the price of bundle
	private double price;
	
	public Bundle(int numOfFlower, double price){
		this.numOfFlower = numOfFlower;
		this.price = price;
	}
	
	public int getNumOfFlower() {
		return numOfFlower;
	}
	
	public double getPrice() {
		return price;
	}
	
}
