package fi.oulu.tol.sqat.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fi.oulu.tol.sqat.GildedRose;
import fi.oulu.tol.sqat.Item;

public class GildedRoseTest {

// Example scenarios for testing
//   Item("+5 Dexterity Vest", 10, 20));
//   Item("Aged Brie", 2, 0));
//   Item("Elixir of the Mongoose", 5, 7));
//   Item("Sulfuras, Hand of Ragnaros", 0, 80));
//   Item("Backstage passes to a TAFKAL80ETC concert", 15, 20));
//   Item("Conjured Mana Cake", 3, 6));

	//Normal Item reduce quality and sellin as day passed
	@Test
	public void testUpdateEndOfDay_Quality_Cake_6_5() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Conjured Mana Cake", 6, 6) );
		
		// Act
		store.updateEndOfDay();
			
		// Assert
		List<Item> items = store.getItems();
		Item itemCake = items.get(0);
		assertEquals("Quality of cake should decrease", 5, itemCake.getQuality());
		//fail("Test not implemented");
	}
	
	@Test
	public void testUpdateEndOfDay_SellIn_Cake_4_3() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Conjured Mana Cake", 4, 3) );
		
		// Act
		store.updateEndOfDay();
			
		// Assert
		List<Item> items = store.getItems();
		Item itemCake = items.get(0);
		assertEquals("SellIn of cake should decrease", 3, itemCake.getSellIn());
		//fail("Test not implemented");
	}
	
	@Test
	public void testUpdateEndOfDay_Quality_Elixir_1_0() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Conjured Mana Cake", 6, 6) );
		store.addItem(new Item("Elixir of the Mongoose", 1, 1) );
		
		// Act
		store.updateEndOfDay();
			
		// Assert
		List<Item> items = store.getItems();
		Item itemElixir = items.get(1);
		assertEquals("Quality of elixir (second item) should decrease", 0, itemElixir.getQuality());
	}
	
	//Quality never goes negative
	@Test
	public void testUpdateEndOfDay_Quality_Elixir_0_0() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Elixir of the Mongoose", 1, 0) );
		
		// Act
		store.updateEndOfDay();
			
		// Assert
		List<Item> items = store.getItems();
		Item itemElixir = items.get(0);
		assertEquals("Quality should never go negative", 0, itemElixir.getQuality());
	}
	
	//Quality degrade twice as fast once SellIn passed
	@Test
	public void testUpdateEndOfDay_Vest_Quality_5_3_SellIn_Passed() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("+5 Dexterity Vest", 0, 5) );
		
		// Act
		store.updateEndOfDay();
		
		// Assert
		List<Item> items = store.getItems();
		Item itemVast = items.get(0);
		assertEquals("Quality should decrease by 2 once SellIn passed", 3, itemVast.getQuality());
	}
	
	//Quality is never more than 50
	@Test
	public void testUpdateEndOfDay_AgedBrie_Quality_50_50() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Aged Brie", 10, 50) );
		
		// Act
		store.updateEndOfDay();
		
		// Assert
		List<Item> items = store.getItems();
		Item itemBrie = items.get(0);
		assertEquals("Quality should not exceed 50", 50, itemBrie.getQuality());
	}
	
	//Quality is never negative
	@Test
	public void testUpdateEndOfDay_Vest_Quality_0_0() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("+5 Dexterity Vest", 2, 0) );
		
		// Act
		store.updateEndOfDay();
		
		// Assert
		List<Item> items = store.getItems();
		Item itemVast = items.get(0);
		assertEquals("Quality should not go negative", 0, itemVast.getQuality());
	}
	
	//Aged Brie increase quality as day passed
	@Test
	public void testUpdateEndOfDay_AgedBrie_Quality_10_11() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Aged Brie", 2, 10) );
		
		// Act
		store.updateEndOfDay();
		
		// Assert
		List<Item> items = store.getItems();
		Item itemBrie = items.get(0);
		assertEquals("Aged Brie quality should increase", 11, itemBrie.getQuality());
	}
	
	//Aged Brie increase quality as day passed
	@Test
	public void testUpdateEndOfDay_AgedBrie_Quality_10_11_AfterSellIN() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Aged Brie", 0, 10) );
		
		// Act
		store.updateEndOfDay();
		
		// Assert
		List<Item> items = store.getItems();
		Item itemBrie = items.get(0);
		assertEquals("Aged Brie quality should increase even after SellIn", 11, itemBrie.getQuality());
	}
    
	//Sulfuras quality does not change
	@Test
	public void testUpdateEndOfDay_Sulfuras_Quality_80_80() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Sulfuras, Hand of Ragnaros", 5, 80) );
		
		// Act
		store.updateEndOfDay();
		
		// Assert
		List<Item> items = store.getItems();
		Item itemSulfuras = items.get(0);
		assertEquals("Quality of Sulfuras should not change", 80, itemSulfuras.getQuality());
	}

	//Sulfuras sellin does not change
	@Test
	public void testUpdateEndOfDay_Sulfuras_SellIn_80_80() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Sulfuras, Hand of Ragnaros", 5, 80) );
		
		// Act
		store.updateEndOfDay();
		
		// Assert
		List<Item> items = store.getItems();
		Item itemSulfuras = items.get(0);
		assertEquals("SellIn of Sulfuras should not change", 5, itemSulfuras.getSellIn());
	}	
	
	//Backstage pass increase quality by 1 if more than 10 days left
	@Test
	public void testUpdateEndOfDay_Backstage_Quality_Morethan10Days() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Backstage passes to a TAFKAL80ETC concert", 12, 10) );
		
		// Act
		store.updateEndOfDay();
		
		// Assert
		List<Item> items = store.getItems();
		Item itemBackstage = items.get(0);
		assertEquals("Backstage quality should increase by 1", 11, itemBackstage.getQuality());
	}
	
	//Backstage pass increase quality by 2 if less than 10 days left
	@Test
	public void testUpdateEndOfDay_Backstage_Quality_10DaysOrLess() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Backstage passes to a TAFKAL80ETC concert", 10, 10) );
		
		// Act
		store.updateEndOfDay();
		
		// Assert
		List<Item> items = store.getItems();
		Item itemBackstage = items.get(0);
		assertEquals("Backstage quality should increase by 2", 12, itemBackstage.getQuality());
	}
	
	//Backstage pass increase quality by 3 if less than 5 days left
	@Test
	public void testUpdateEndOfDay_Backstage_Quality_5DaysOrLess() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Backstage passes to a TAFKAL80ETC concert", 5, 12) );
		
		// Act
		store.updateEndOfDay();
		
		// Assert
		List<Item> items = store.getItems();
		Item itemBackstage = items.get(0);
		assertEquals("Backstage quality should increase by 3", 15, itemBackstage.getQuality());
	}
	
	//Backstage quality is 0 if SellIn is 0
	@Test
	public void testUpdateEndOfDay_Backstage_Quality_0Days() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Backstage passes to a TAFKAL80ETC concert", 0, 12) );
		
		// Act
		store.updateEndOfDay();
		
		// Assert
		List<Item> items = store.getItems();
		Item itemBackstage = items.get(0);
		assertEquals("Backstage pass should reset to 0", 0, itemBackstage.getQuality());
	}   
}
