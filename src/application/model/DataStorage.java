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
		dataTree = null;
		transferInfoToListeners(ChangeType.Clear, null, null);
	}
	
	public void addSilbling(DataItem node, UUID parent) {

		node.itemId = UUID.randomUUID();
		addToTree(parent, node, true);
		transferInfoToListeners(ChangeType.Add, node, null);
	}
	
	public void addChild(DataItem node, UUID parent) {
		
		node.itemId = UUID.randomUUID();
		addToTree(parent, node, false);
		transferInfoToListeners(ChangeType.AddToPresent, node, parent);

	}

	private void transferInfoToListeners(ChangeType type, DataItem item, UUID extInfo) {
		for(ListChangeListener listener : listeners){
			listener.onChange(type, item, extInfo);
		}
	}
	
	public boolean listIsEmpty() {
		return (dataTree == null);
	}

	public void addChildren(List<DataItem> list) {

		for (DataItem item : list) {
			addChild(item);
		}
	}

	public DataItem getRootNode() {
		return dataTree.getData();
	}

	public int getLastAddNodeId() {
		return lastAddNodeId;
	}

	public Optional<String> getText(UUID nodeId) {
		
		Optional<String> res = Optional.empty();
		Optional<TreeNode<DataItem>> node = getNodeByUUID(dataTree, nodeId);
		
		if(node.isPresent()) 
		{
			return Optional.ofNullable(node.get().getData().getItemText());
		}
		
		return res;
	}
	
	public void addListener(ListChangeListener currListener){
		listeners.add(currListener);
	}
	
	public void modifyText(UUID nodeId, String newText){
		
		Optional<TreeNode<DataItem>> node = getNodeByUUID(dataTree, nodeId);
		
		node.ifPresent( a -> a.getData().setItemText(newText));
		
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
			
			addChild(item, parentId);
		}
		else{
			addChild(item);
		}
	}
	
	Optional<TreeNode<DataItem>> getNodeByUUID(TreeNode<DataItem> start, UUID nodeId)
	{
		Optional<TreeNode<DataItem>> res = Optional.empty();
		for(TreeNode<DataItem> child : start.getChildren()) 
		{
			if(child.getData().itemId.equals(nodeId)) 
			{
				res = Optional.of(child);
				break;
			}
			else 
			{
				Optional<TreeNode<DataItem>> tempRes = getNodeByUUID(child, nodeId);
				
				if(tempRes.isPresent()) 
				{
					res = Optional.of(tempRes.get());
					break;
				}
			}
		}
		
		return res;
	}
	
	public int calculateLevel(TreeNode<DataItem> start) 
	{
		TreeNode<DataItem> temp = start;
		int cnt = 0;
		
		
		while(temp.getParent() != null) 
		{
			cnt = cnt + 1;
			temp = temp.getParent();
		}
		
		return cnt;
	}
	
	void addToTree(UUID parent, DataItem item, boolean silbling)
	{
		if(dataTree == null) 
		{
			dataTree = new TreeNode<DataItem>(item);
		}
		else 
		{
			Optional<TreeNode<DataItem>> parentNode = getNodeByUUID(dataTree, parent);
			
			if(parentNode.isPresent()) 
			{
				if (silbling) 
				{
					parentNode = Optional.ofNullable(parentNode.get().getParent());
				}
				
				parentNode.ifPresent(a -> a.addChild(item));
				
			}
			
			
		}
		
	}
	
	void createTreeIfAbsent(DataItem item)
	{
		if(dataTree == null) 
		{
			dataTree = new TreeNode<DataItem>(item);
		}
	}

}
