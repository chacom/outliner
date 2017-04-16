package application.model;

import java.util.UUID;

import HolLib.NodeColor;

public class DataItem extends NodeInfo{
	String itemText;
	int itemLevel;
	UUID itemId;
	
	
	public DataItem(String title, NodeColor color, String text) {
		this.itemColor = color;
		this.title = title;
		this.itemText = text;
		this.itemId = UUID.randomUUID();
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
	
	public UUID getItemId() {
		return itemId;
	}
	public void setItemId(UUID itemId) {
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
