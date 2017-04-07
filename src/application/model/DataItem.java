package application.model;

import HolLib.Color;

public class DataItem extends NodeInfo{
	String itemText;
	int itemLevel;
	int itemId;
	
	
	public DataItem(String title, Color color, String text) {
		this.itemColor = color;
		this.title = title;
		this.itemText = text;
	}
	
	public DataItem(DataItem srcItem){
		itemText 	= srcItem.itemText; 
		itemId   	= srcItem.itemId;
		itemLevel	= srcItem.itemLevel;
		itemColor	= srcItem.itemColor;
		title		= srcItem.title;
		nodeCnt 	= srcItem.nodeCnt;
	}
	
	public DataItem() {
	}
	
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getItemText() {
		return itemText;
	}
	public void setItemText(String itemText) {
		this.itemText = itemText;
	}
	public int getItemLevel() {
		return itemLevel;
	}
	public void setItemLevel(int itemLevel) {
		this.itemLevel = itemLevel;
	}
	
	

}
