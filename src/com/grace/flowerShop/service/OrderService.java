package com.grace.flowerShop.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.grace.flowerShop.model.Bundle;
import com.grace.flowerShop.model.FlowerCode;

/**
 * This class parses orders, calculate bundles and return result
 * 
 * @author Grace xy Wang
 *
 */
public class OrderService {
	/**
	 * get the bundles info by orders, if there are orders have wrong format then offer clients warning info
	 * @param orders an array of orders
	 * @return a string that shows warning info or bundles info
	 */
	public String getBundlesByOrder(String[] orders){
		StringBuilder orderResult = new StringBuilder();
		for(String order : orders){
			// parse orders' format
			String[] singleOrders = order.split(" ");
			// if order doesn't have the right format
			if(singleOrders.length != 2){
				orderResult.append("This Order is wrong: ").append(order).append("\n");
			}
			else{
				int orderCount = 0;
				FlowerCode code = null;
				try{
					orderCount = Integer.parseInt(singleOrders[0]);
					code = FlowerCode.valueOf(singleOrders[1].toUpperCase());
				}catch(NumberFormatException e){
					orderResult.append("This Order has wrong number of flower: ").append(order).append("\n");
					continue;
				}catch(IllegalArgumentException e1){
					orderResult.append("This Order has wrong code of flower: ").append(order).append("\n");
					continue;
				}
				if(orderCount != 0 && code != null){
					// get bundles info
					Stack<BundlesCount> stack = getBundlesByOrder(code, orderCount);
					// if there are no appropriate bundles, give clients warning info.
					if(stack == null || stack.isEmpty()){
						orderResult.append("Cannot offer you the appropriate bundles, please change your order: ").append(order).append("\n");
						continue;
					}
					// return receipt
					orderResult.append(orderCount).append(" ").append(code.toString());
					orderResult.append(printOrder(stack)).append("\n");
				}
			}
		}
		return orderResult.toString();
	}
	
	/**
	 * get bundle info by order
	 * @param code the code of flower
	 * @param orderCount the ordered number
	 * @return Stack<BundleCount> return the appropriate numbers of bundles
	 */
	private Stack<BundlesCount> getBundlesByOrder(FlowerCode code, int orderCount){
		List<Bundle> bundleList = DataService.getBundleInfoByCode(code, orderCount);
		if(null == bundleList || bundleList.size() == 0){
			return null;
		}
		
		Stack<BundlesCount> stack = new Stack<BundlesCount>();
		while(!bundleList.isEmpty()){
			if(!calcuBundles(stack, bundleList, orderCount)){
				stack.clear();
				bundleList.remove(0);
			}
			else{
				break;
			}
		}
		
		return stack;
	}
	
	/**
	 * use recursion to calculate the number of bundles
	 * @param stack a stack to restore bundle info
	 * @param bundleList 
	 * @param orderCount the order number
	 * @return
	 */
	private boolean calcuBundles(Stack<BundlesCount> stack,List<Bundle> bundleList, int orderCount){
		// remove bundles which has flowers more than the order number
		List<Bundle> suitableList = new ArrayList<Bundle>();
		for(int i = 0; i < bundleList.size(); i++){
			if(bundleList.get(i).getNumOfFlower() <= orderCount){
				suitableList.add(bundleList.get(i));
			}
		}
		if(suitableList.isEmpty()){
			return false;
		}
		
		// get the first bundle, which has the most number of flowers
		Bundle bundle = suitableList.get(0);
		int bundleNum = (int)Math.floor(orderCount/bundle.getNumOfFlower());
		stack.push(new BundlesCount(bundleNum, bundle));
		// the due number of order
		int dueOrderCount = orderCount - (bundle.getNumOfFlower()*bundleNum);
		if(dueOrderCount == 0){
			return true;
		}
		else{
			/**
			 * if the due number of order is greater than the minimum flower number of bundle,
			 * then calculate bundles using due number
			 */
			if(dueOrderCount >= suitableList.get(suitableList.size()-1).getNumOfFlower()){
				return calcuBundles(stack, suitableList, dueOrderCount);
			}
			else{
				BundlesCount bc = stack.pop();
				// if there is only one bundle left, there is no way to calculate
				if(suitableList.size() == 1){
					return false;
				}
				else{
					// pop a bundle from stack
					if(bc.getNumOfBundle() > 1){
						bc.setNumOfBundle(bc.getNumOfBundle() - 1);
						stack.push(bc);
					}
					dueOrderCount = dueOrderCount + bc.getBundle().getNumOfFlower();
					suitableList.remove(0);
					return calcuBundles(stack, suitableList, dueOrderCount);
				}
			}
			
		}
	}
	
	/**
	 * get the receipt info
	 * @param stack bundles info
	 * @return
	 */
	private String printOrder(Stack<BundlesCount> stack){		
		StringBuilder strDetail = new StringBuilder();
		BigDecimal amount = new BigDecimal(0);
		while(!stack.isEmpty()){
			BundlesCount bc = stack.pop();
			BigDecimal bd = new BigDecimal(bc.getNumOfBundle());
			amount = amount.add(bd.multiply(new BigDecimal(Double.toString(bc.getBundle().getPrice()))));
			
			strDetail.append("\n\t").append(bc.getNumOfBundle()).append(" X ")
						.append(bc.getBundle().getNumOfFlower())
						.append(" $").append(bc.getBundle().getPrice());
		}
		StringBuilder strResult = new StringBuilder();
		strResult.append(" $").append(amount.doubleValue()).append(strDetail);
		return strResult.toString();
	}
	
	/**
	 * private class, to restore the number of bundles and bundle info
	 * 
	 * @author Grace xy Wang
	 *
	 */
	private class BundlesCount{
		int numOfBundles;		
		Bundle bundle;
		
		BundlesCount(int numOfBundles, Bundle bundle){
			this.numOfBundles = numOfBundles;
			this.bundle = bundle;
		}
		
		public int getNumOfBundle() {
			return numOfBundles;
		}
		public void setNumOfBundle(int numOfBundles) {
			this.numOfBundles = numOfBundles;
		}

		public Bundle getBundle() {
			return bundle;
		}
	}
	
}
