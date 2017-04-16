package application.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import HolLib.NodeColor;

public class DataStorage {

	int lastAddNodeId = 0;

	ArrayList<DataItem> nodeList = new ArrayList<>();
	
	ArrayList<ListChangeListener> listeners = new ArrayList<>();

	public DataStorage() {

	}

	public void addChild(DataItem node) {

		node.itemId = UUID.randomUUID();
		nodeList.add(node);
		for(ListChangeListener listener : listeners){
			listener.onChange(ChangeType.Add, node, 0);
		}

	}
	
	public void addChild(DataItem node, int parentIdx) {
		
		node.itemId = UUID.randomUUID();
		nodeList.add(parentIdx + 1, node);
		UUID parentId = nodeList.get(parentIdx).itemId;
		
		for(ListChangeListener listener : listeners){
			listener.onChange(ChangeType.AddToPresent, node, parentId);
		}

	}
	
	public boolean listIsEmpty() {
		return nodeList.isEmpty();
	}

	public void addChildren(List<DataItem> list) {

		for (DataItem item : list) {
			addChild(item);
		}
	}

	public DataItem getRootNode() {
		return nodeList.get(0);
	}

	public int getLastAddNodeId() {
		return lastAddNodeId;
	}

	public Optional<String> getText(UUID nodeId) {
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
	
	public void modifyText(UUID nodeId, String newText){
		for(DataItem item : nodeList){
			if(item.getItemId() == nodeId){
				item.itemText = newText;
			}
		}
	}
	
	public void modifyTitle(UUID nodeId, String title, NodeColor currColor){
		for(DataItem item : nodeList){
			if(item.getItemId() == nodeId){
				item.title = title;
				item.itemColor = currColor;
			}
		}
	}
	
	public List<DataItem> getData(){
		return nodeList;
	}
	
	public void removeNode(UUID nodeId)
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
			nodeList.remove(searchIdx.intValue());
			for(ListChangeListener listener : listeners){
				listener.onChange(ChangeType.Remove, temp, 0);
			}
		}
		
	}
	
	public void addNewNode(String title, UUID parentId, int level){
		
		DataItem item = new DataItem(title, NodeColor.Black, "");
		
		if(parentId != null){
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
		else{
			item.itemLevel = 1;
			addChild(item);
		}
		
		
	}

}
