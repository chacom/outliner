package application.model;

import HolLib.Color;

public class NodeInfo {
	String title;
	Color  itemColor;
	int	   nodeCnt;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Color getItemColor() {
		return itemColor;
	}
	public void setItemColor(Color itemColor) {
		this.itemColor = itemColor;
	}
	public int getNodeCnt() {
		return nodeCnt;
	}
	public void setNodeCnt(int nodeCnt) {
		this.nodeCnt = nodeCnt;
	}
	
	

}
