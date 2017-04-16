package application.model;

import java.util.UUID;

import HolLib.NodeColor;

public class ExtTreeItem {
	
	String text;
	UUID id;
	int level;
	NodeColor color;
	
	public ExtTreeItem(String text, UUID id, int level, NodeColor color) {
		this.text = text;
		this.id = id;
		this.level = level;
		this.color = color;
	}
	
	public void setTitle(String text){
		this.text = text;
	}
	
	@Override
	public String toString() {
		return text;
	}
	
	
	public UUID getId(){
		return id;
	}
	
	public int getLevel(){
		return level;
	}
	
	public void setColor(NodeColor color) {
		this.color = color;
	}

	public NodeColor getColor()
	{
		return color;
	}

}
