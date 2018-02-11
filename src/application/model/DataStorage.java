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
		transferInfoToListeners(ChangeType.Add, node, parent);
	}
	
	public void addChild(DataItem node, UUID parent) {
		
		node.itemId = UUID.randomUUID();
		addToTree(parent, node, false);
		transferInfoToListeners(ChangeType.Add, node, parent);

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
		else {
			transferInfoToListeners(ChangeType.Add, node.getData(), null);
		}

		for (TreeNode<DataItem> child : node.getChildren()) {
			publishNewNode(child);
		}
	}

	public Optional<String> getText(UUID nodeId) {
		
		ArrayList<TreeNode<DataItem>> res = getNodeByUUID(dataTree, nodeId);
		
		if(!res.isEmpty()) 
		{
			return Optional.ofNullable(res.get(0).getData().itemText);
		}
		
		return Optional.empty();
	}
	
	public void addListener(ListChangeListener currListener){
		listeners.add(currListener);
	}
	
	public void modifyText(UUID nodeId, String newText){
		
		ArrayList<TreeNode<DataItem>> res = getNodeByUUID(dataTree, nodeId);
		
		if(!res.isEmpty()) 
		{
			res.get(0).getData().setItemText(newText);
		}
	}
	
	public void modifyTitle(UUID nodeId, String title, NodeColor currColor){
		
		ArrayList<TreeNode<DataItem>> res = getNodeByUUID(dataTree, nodeId);
		
		if(!res.isEmpty()) 
		{
			res.get(0).getData().setTitle(title);
			res.get(0).getData().setItemColor(currColor);
			
		}
		
	}
	
	public List<DataItemExt> getData(){
		ConvertTreeData conv = new ConvertTreeData();
		
		return conv.getList(dataTree);
	}
	
	public void removeNode(UUID nodeId)
	{	
		
		ArrayList<TreeNode<DataItem>> res = getNodeByUUID(dataTree, nodeId);
		
		if(!res.isEmpty()) 
		{	
			TreeNode<DataItem> temp = res.get(0);
			temp.removeChild(temp);
			transferInfoToListeners(ChangeType.Remove, temp.getData(), null);
		}
	}
	
	public void addNewNode(String title, UUID parentId, boolean silbling){
		
		DataItem item = new DataItem(title, NodeColor.Black, "");
		
		if(silbling) {
			addSilbling(item, parentId);
		}
		else {
			addChild(item, parentId);
		}
	
	}
	
	void findNodeByUUID(ArrayList<TreeNode<DataItem>> res, TreeNode<DataItem> node, UUID nodeId){
		
		//System.out.println("Search: " + nodeId);
		//System.out.println("Find: " + node.getData().getItemId());

		if(node.getData().itemId.equals(nodeId)) {
			res.add(node);
		}
		else {
			for(TreeNode<DataItem> child : node.getChildren()) 
			{
				findNodeByUUID(res, child, nodeId);				
			}
		}
	}
	
	ArrayList<TreeNode<DataItem>> getNodeByUUID(TreeNode<DataItem> start, UUID nodeId)
	{
		ArrayList<TreeNode<DataItem>> res = new ArrayList<>();

		findNodeByUUID(res, start, nodeId);
		
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
			ArrayList<TreeNode<DataItem>> res = getNodeByUUID(dataTree, parent);
			
			if(!res.isEmpty()) 
			{
				if (silbling) 
				{
					res.get(0).getParent().addChild(item);
				}
				else {
					res.get(0).addChild(item);
				}
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
