package application.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import HolLib.NodeColor;


public class DataStorage {

	int lastAddNodeId = 0;

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

	private void transferInfoToListeners(ChangeType type, DataItem item, UUID parent) {
		for(ListChangeListener listener : listeners){
			listener.onChange(type, item, parent);
		}
	}
	
	public boolean listIsEmpty() {
		return (dataTree == null);
	}

	public void createTree(List<DataItemExt> list) {

		ConvertTreeData conv = new ConvertTreeData();
		
		dataTree = conv.getTree(list);
		
		publishNewNode(dataTree);
		
	}

	public DataItem getRootNode() {
		return dataTree.getData();
	}

	public int getLastAddNodeId() {
		return lastAddNodeId;
	}
	
	void publishNewNode(TreeNode<DataItem> node) 
	{
		if(node.getParent() != null) {
			transferInfoToListeners(ChangeType.Add, node.getData(), node.getParent().getData().getItemId());
		}

		for (TreeNode<DataItem> child : node.getChildren()) {
			publishNewNode(child);
		}
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
		Optional<TreeNode<DataItem>> item = getNodeByUUID(dataTree, nodeId);
		
		if(item.isPresent()) 
		{
			item.get().getData().setTitle(title);
			item.get().getData().setItemColor(currColor);
			
		}
		
	}
	
	public List<DataItemExt> getData(){
		ConvertTreeData conv = new ConvertTreeData();
		
		return conv.getList(dataTree);
	}
	
	public void removeNode(UUID nodeId)
	{	
		Optional<TreeNode<DataItem>> item = getNodeByUUID(dataTree, nodeId);
		
		if(item.isPresent()) 
		{
			TreeNode<DataItem> x = item.get().removeChild(item.get());
			transferInfoToListeners(ChangeType.Remove, x.getData(), null);
		}
	}
	
	public void addNewNode(String title, UUID parentId){
		
		DataItem item = new DataItem(title, NodeColor.Black, "");
		
		addChild(item, parentId);
	
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
