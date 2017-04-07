package application.model;

import HolLib.Color;

public class OutLinerNode {

	OutLinerNode prev;
	
	String 	label;
	Color 	labelColor;
	String 	text;
	int 	nodeId;
	
	public OutLinerNode(String label, Color labelColor, String text) {
		this.label = label;
		this.labelColor = labelColor;
		this.text = text;
	}
	
	public void setUiId(int id) { 
		this.nodeId = id;
	}
}
