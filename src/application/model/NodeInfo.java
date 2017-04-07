package application.model;

import HolLib.NodeColor;

public class NodeInfo {
	String title;
	NodeColor  itemColor;
	int	   nodeCnt;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public NodeColor getItemColor() {
		return itemColor;
	}
	public void setItemColor(NodeColor itemColor) {
		this.itemColor = itemColor;
	}
	public int getNodeCnt() {
		return nodeCnt;
	}
	public void setNodeCnt(int nodeCnt) {
		this.nodeCnt = nodeCnt;
	}
	
	

}
