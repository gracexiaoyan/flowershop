package com.grace.flowerShop.test;

import org.junit.Assert;
import org.junit.Test;

import com.grace.flowerShop.service.OrderService;

/**
 * unit test class
 * @author Grace xy Wang
 *
 */
public class TestFlowerShop {
	OrderService os = new OrderService();
	
	@Test
	public void testRightOrders(){
		String[] orders = {"15 L09","26 T58","10 R12"};
		String result = os.getBundlesByOrder(orders);
		Assert.assertEquals("15 L09 $41.9\n\t1 X 6 $16.95\n\t1 X 9 $24.95\n26 T58 $49.88\n\t1 X 3 $5.95\n\t1 X 5 $9.95\n\t2 X 9 $16.99\n10 R12 $12.99\n\t1 X 10 $12.99\n", result);
	}
	
	@Test
	public void testRightSingleOrder(){
		String[] orders = {"30 R12"};
		String result = os.getBundlesByOrder(orders);
		Assert.assertEquals("30 R12 $38.97\n\t3 X 10 $12.99\n", result);
	}
	
	@Test
	public void testMixOrders(){
		String[] orders = {"15 L09","7 T58","10 R12"};
		String result = os.getBundlesByOrder(orders);
		Assert.assertEquals("15 L09 $41.9\n\t1 X 6 $16.95\n\t1 X 9 $24.95\nCannot offer you the appropriate bundles, please change your order: 7 T58\n10 R12 $12.99\n\t1 X 10 $12.99\n", result); 
	}
	
	@Test
	public void testWrongOrder(){
		String[] orders = {"13 R12"};
		String result = os.getBundlesByOrder(orders);
		Assert.assertEquals("Cannot offer you the appropriate bundles, please change your order: 13 R12\n", result);
	}
	
	public static void main(String[] args){
		OrderService os = new OrderService();
		System.out.println(os.getBundlesByOrder(new String[]{"15 l09","26 T58","10 R12"}));
		System.out.println(os.getBundlesByOrder(new String[]{"30 R12"}));
		System.out.println(os.getBundlesByOrder(new String[]{"15 l09","7 T58","10 R12"}));
		System.out.println(os.getBundlesByOrder(new String[]{"13 R12"}));
	}
}
