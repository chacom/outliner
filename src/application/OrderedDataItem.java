package application;

import java.util.ArrayList;
import java.util.UUID;

import HolLib.NodeColor;
import application.model.DataItem;

public class OrderedDataItem {

	ArrayList<OrderedDataItem> children = new ArrayList<>();
	
	String itemText;
	int itemLevel;
	UUID itemId;
	String title;
	NodeColor  itemColor;
	int	   nodeCnt;
	
	public OrderedDataItem(DataItem item) {
		
		this.title = item.getTitle();
		this.itemText = item.getItemText();
		this.itemId = item.getItemId();
		this.itemLevel = item.getItemLevel();
		this.nodeCnt = item.getNodeCnt();
		this.itemColor = item.getItemColor();
		
		
	}

	/**
	 * 
	 * @param title
	 * @param itemId
	 * @param itemText
	 * @param itemLevel
	 * @param itemColor
	 * @param nodeCnt
	 */
	public OrderedDataItem(String title, UUID itemId, String itemText, int itemLevel, NodeColor  itemColor, int nodeCnt) {
		
		this.title = title;
		this.itemText = itemText;
		this.itemId = itemId;
		this.itemLevel = itemLevel;
		this.nodeCnt = nodeCnt;
		this.itemColor = itemColor;
	}
}
