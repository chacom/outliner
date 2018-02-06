package application.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import HolLib.NodeColor;


public class DataStorage {

	int lastAddNodeId = 0;

	//List<DataItem> nodeList = new ArrayList<>();
	
	TreeNode<DataItem> dataTree;
	List<ListChangeListener> listeners = new ArrayList<>();

	public DataStorage() {

	}

	public void clearData() {
		nodeList = new ArrayList<>();
		transferInfoToListeners(ChangeType.Clear, null, null);
	}
	
	public void addChild(DataItem node) {

		node.itemId = UUID.randomUUID();
		nodeList.add(node);
	
		transferInfoToListeners(ChangeType.Add, node, null);
	
	}
	
	public void addChild(DataItem node, int parentIdx) {
		
		node.itemId = UUID.randomUUID();
		nodeList.add(parentIdx + 1, node);
		UUID parentId = nodeList.get(parentIdx).itemId;
		
		transferInfoToListeners(ChangeType.AddToPresent, node, parentId);

	}

	private void transferInfoToListeners(ChangeType type, DataItem item, UUID extInfo) {
		for(ListChangeListener listener : listeners){
			listener.onChange(type, item, extInfo);
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
			
			transferInfoToListeners(ChangeType.Remove, temp, null);
		}
		
	}
	
	public void addNewNode(String title, UUID parentId){
		
		DataItem item = new DataItem(title, NodeColor.Black, "");
		
		if(parentId != null){
			
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
			addChild(item);
		}
	}
	
	public int calculateLevel(DataItem item) 
	{
		int cnt = 0;
		
		while(item.g)
		//TODO 
		
		return 0;
	}

}
