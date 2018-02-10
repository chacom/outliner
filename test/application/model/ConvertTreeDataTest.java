package application.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import HolLib.NodeColor;

public class ConvertTreeDataTest {

	ConvertTreeData conv;
	List<DataItemExt> list;
	
	@Test
	public void testGetList() throws Exception {
		
		DataItem item = new DataItem("base", NodeColor.Red, "BlaTextBase");
		TreeNode<DataItem> nodeRoot = new TreeNode<DataItem>(item);

		item = new DataItem("bla1", NodeColor.Red, "BlaText1");
		TreeNode<DataItem> node1 = nodeRoot.addChild(item);
		
		item = new DataItem("bla11", NodeColor.Red, "BlaText11");
		node1.addChild(item);
		
		item = new DataItem("bla2", NodeColor.Red, "BlaText2");
		TreeNode<DataItem> node2 = nodeRoot.addChild(item);
		
		item = new DataItem("bla21", NodeColor.Red, "BlaText21");
		TreeNode<DataItem> node21 = node2.addChild(item);
		
		item = new DataItem("bla211", NodeColor.Red, "BlaText211");
		node21.addChild(item);
		
		item = new DataItem("bla22", NodeColor.Red, "BlaText22");
		node2.addChild(item);
		
		
		conv = new ConvertTreeData();
		
		list = conv.getList(nodeRoot);
		
		verifyItem(nodeRoot,list.get(0));
		verifyItem(node1,list.get(1));
		verifyItem(node1.getChildren().get(0),list.get(2));
		verifyItem(node2,list.get(3));
		verifyItem(node21,list.get(4));
		verifyItem(node21.getChildren().get(0),list.get(5));
		verifyItem(node2.getChildren().get(1),list.get(6));
		
	}
	
	void verifyItem(TreeNode<DataItem> treeItem, DataItemExt listItem) 
	{
		assertEquals(treeItem.getData().title, listItem.title);
		assertEquals(treeItem.getData().itemText, listItem.itemText);
		assertEquals(treeItem.getData().itemColor, listItem.itemColor);
		assertEquals(treeItem.getData().nodeCnt, listItem.nodeCnt);
		assertEquals(treeItem.getData().itemId, listItem.itemId);
		assertEquals(conv.calculateLevel(treeItem), listItem.level);
	}

	@Test
	public void testGetTree() throws Exception {
		
		ArrayList<DataItemExt> list = new ArrayList<>();
		
		list.add(new DataItemExt("base", NodeColor.Red, "BlaTextBase", 0));
		list.add(new DataItemExt("bla1", NodeColor.Red, "BlaText1", 1));
		list.add(new DataItemExt("bla11", NodeColor.Red, "BlaText11", 2));
		list.add(new DataItemExt("bla2", NodeColor.Red, "BlaText2", 1));
		list.add(new DataItemExt("bla21", NodeColor.Red, "BlaText21", 2));
		list.add(new DataItemExt("bla211", NodeColor.Red, "BlaText211", 3));
		list.add(new DataItemExt("bla22", NodeColor.Red, "BlaText22", 2));
		
		conv = new ConvertTreeData();
		
		TreeNode<DataItem> root = conv.getTree(list);
		
		verifyItem(root, list.get(0));
		verifyItem(root.getChildren().get(0), list.get(1));
		verifyItem(root.getChildren().get(0).getChildren().get(0), list.get(2));
		verifyItem(root.getChildren().get(1), list.get(3));
		verifyItem(root.getChildren().get(1).getChildren().get(0), list.get(4));
		verifyItem(root.getChildren().get(1).getChildren().get(0).getChildren().get(0), list.get(5));
		verifyItem(root.getChildren().get(1).getChildren().get(1), list.get(6));
		
	}
	
	

}
