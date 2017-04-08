package application.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import HolLib.NodeColor;

public class DataStorage {

	int lastAddNodeId = 0;

	ArrayList<DataItem> nodeList = new ArrayList<>();
	
	ArrayList<ListChangeListener> listeners = new ArrayList<>();

	public DataStorage() {
		nodeList.add(new DataItem("root", NodeColor.Black, ""));

		nodeList.get(0).setItemId(0);
	}

	public void addChild(DataItem node) {

		node.itemId = node.title.hashCode();
		nodeList.add(node);
		for(ListChangeListener listener : listeners){
			listener.onChange(ChangeType.Add, node, 0);
		}

	}
	
	public void addChild(DataItem node, int parentIdx) {
		
		node.itemId = node.title.hashCode();
		nodeList.add(parentIdx + 1, node);
		int parentId = nodeList.get(parentIdx).itemId;
		
		for(ListChangeListener listener : listeners){
			listener.onChange(ChangeType.AddToPresent, node, parentId);
		}

	}

	public void addChildren(List<DataItem> list) {

		for (DataItem item : list) {
			addChild(item);
		}
		nodeList.addAll(list);
	}

	public DataItem getRootNode() {
		return nodeList.get(0);
	}

	public int getLastAddNodeId() {
		return lastAddNodeId;
	}

	public Optional<String> getText(int nodeId) {
		for (DataItem item : nodeList) {
			if (item.itemId == nodeId) {
				return Optional.of(item.itemText);
			}
		}
		return Optional.empty();
	}

	public void cleanList() {
		nodeList.clear();
		lastAddNodeId = 0;
	}
	
	public void addListener(ListChangeListener currListener){
		listeners.add(currListener);
	}
	
	public void modifyText(int nodeId, String newText){
		for(DataItem item : nodeList){
			if(item.getItemId() == nodeId){
				item.itemText = newText;
			}
		}
	}
	
	public void modifyTitle(int nodeId, String title, NodeColor currColor){
		for(DataItem item : nodeList){
			if(item.getItemId() == nodeId){
				item.title = title;
				item.itemColor = currColor;
			}
		}
	}
	
	public void removeNode(int nodeId)
	{	
		Integer searchIdx = null;
		for(int idx = 0; idx < nodeList.size();idx++)
		{
			if(nodeList.get(idx).getItemId() == nodeId){
				searchIdx = idx;
				break;
			}
		}
		
		if(searchIdx != null){
			DataItem temp = nodeList.get(searchIdx);
			nodeList.remove(searchIdx);
			
			for(ListChangeListener listener : listeners){
				listener.onChange(ChangeType.Remove, temp, 0);
			}
		}
		
	}
	
	public void addNewNode(String title, int parentId, int level){
		
		DataItem item = new DataItem(title, NodeColor.Black, "");
		item.itemLevel = level;
		
		Integer searchIdx = null;
		for(int idx = 0; idx < nodeList.size();idx++)
		{
			if(nodeList.get(idx).getItemId() == parentId){
				searchIdx = idx;
				break;
			}
		}
		
		addChild(item,searchIdx);
	}

}
