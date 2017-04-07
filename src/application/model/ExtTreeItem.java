package application.model;

import HolLib.NodeColor;

public class ExtTreeItem {
	
	String text;
	int id;
	int level;
	NodeColor color;
	
	public ExtTreeItem(String text, int id, int level, NodeColor color) {
		this.text = text;
		this.id = id;
		this.level = level;
		this.color = color;
	}
	
	@Override
	public String toString() {
		return text;
	}
	
	
	public int getId(){
		return id;
	}
	
	public int getLevel(){
		return level;
	}
	
	public NodeColor getColor()
	{
		return color;
	}

}
