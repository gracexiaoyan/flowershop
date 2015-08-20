package com.grace.flowerShop.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.grace.flowerShop.model.Bundle;
import com.grace.flowerShop.model.FlowerCode;

/**
 * This class is not functional, it is created to store data.
 * In real project, data could be stored in database or files.
 * 
 * @author Grace xy wang
 *
 */
public class StockFlower {
	public static Map<FlowerCode, List<Bundle>> stockMap = new HashMap<FlowerCode, List<Bundle>>();
	 
	static{		
		List<Bundle> roseList = new ArrayList<Bundle>();		
		Bundle bundleRose10 = new Bundle(10, 12.99);
		Bundle bundleRose5 = new Bundle(5, 6.99);
		roseList.add(bundleRose10);
		roseList.add(bundleRose5);		
		stockMap.put(FlowerCode.R12, roseList);
		
		List<Bundle> LilyList = new ArrayList<Bundle>();
		Bundle bundleLily9 = new Bundle(9, 24.95);
		Bundle bundleLily6 = new Bundle(6, 16.95);
		Bundle bundleLily3 = new Bundle(3, 9.95);
		LilyList.add(bundleLily9);
		LilyList.add(bundleLily6);
		LilyList.add(bundleLily3);
		stockMap.put(FlowerCode.L09, LilyList);
		
		List<Bundle> tulipList = new ArrayList<Bundle>();
		Bundle bundleTulip9 = new Bundle(9, 16.99);
		Bundle bundleTulip5 = new Bundle(5, 9.95);
		Bundle bundleTulip3 = new Bundle(3, 5.95);
		tulipList.add(bundleTulip9);
		tulipList.add(bundleTulip5);
		tulipList.add(bundleTulip3);
		stockMap.put(FlowerCode.T58, tulipList);
	}
	
}
