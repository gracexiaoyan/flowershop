package com.grace.flowerShop.service;

import java.util.ArrayList;
import java.util.List;

import com.grace.flowerShop.data.StockFlower;
import com.grace.flowerShop.model.Bundle;
import com.grace.flowerShop.model.FlowerCode;

/**
 * This is service class to retrieve data
 * In this project, data is stored in a HashMap
 * In real project, data could be stored in database or files
 * 
 * @author Grace xy wang
 *
 */
public class DataService {
	/**
	 * get the info of bundle by code and order number
	 * @param code
	 * @param orderCount
	 * @return the info of bundle ordered by flower number descent
	 */
	public static List<Bundle> getBundleInfoByCode(FlowerCode code, int orderCount){
		List<Bundle> bundleList = StockFlower.stockMap.get(code);
		if(null == bundleList || bundleList.size() < 1){
			return null;
		}
		List<Bundle> resultList = new ArrayList<Bundle>();
		// filter out bundles which have flowers more than ordered
		for(Bundle bundle : bundleList){
			if(bundle.getNumOfFlower() <= orderCount){
				resultList.add(bundle);
			}
		}
		return resultList;
	}
}
