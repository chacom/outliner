package application.model;

import java.util.ArrayList;
import java.util.Optional;

import HolLib.Color;

public class DataStorage {

	int lastNodeId = 0;
	int lastAddNodeId = 0;
	
	ArrayList<OutLinerNode> nodeList = new ArrayList<>();
	
	
	public DataStorage() {
		nodeList.add(new OutLinerNode("root",Color.Black,""));
		nodeList.get(0).setUiId(0);
	}
	
	
	public void addChild(OutLinerNode node, int parentId){
		
		for(int idx = 0; idx < nodeList.size(); idx++){
			if(nodeList.get(idx).nodeId == parentId){
				
				node.nodeId = node.nodeId;
				node.prev = nodeList.get(idx);
				nodeList.add(node);
				break;
			}
		}
	}
	
	public OutLinerNode getRootNode(){
		return nodeList.get(0);
	}
	
	public int getLastAddNodeId(){
		return lastAddNodeId;
	}
	
	public Optional<String> getText(int nodeId){
		for(OutLinerNode item : nodeList){
			if(item.nodeId == nodeId){
				return Optional.of(item.text);
			}
		}
		return Optional.empty();
	}
	
	
}
