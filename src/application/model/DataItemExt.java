package application.model;

import HolLib.NodeColor;

public class DataItemExt extends DataItem{
	
	int level;
	
	public DataItemExt(String title, NodeColor color, String text, int index) {
		super(title,color, text);
		this.level = index;
	}
	
	public DataItemExt(DataItemExt srcItem){
		super((DataItem) srcItem);
		this.level = srcItem.level;
	}
	
	public DataItemExt(DataItem srcItem, int level){
		super(srcItem);
		this.level = level;
	}
	
	public DataItemExt() 
	{
		super();
	}
	

	public int getItemLevel() {
		return level;
	}
	
	public void setItemLevel(int level) {
		this.level = level;
	}
	
	

}
