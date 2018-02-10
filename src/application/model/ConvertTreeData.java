package application.model;

import java.util.ArrayList;
import java.util.List;

public class ConvertTreeData {
	
	List<DataItemExt> getList(TreeNode<DataItem> tree){
		
		List<DataItemExt> result = new ArrayList<>();
		
		parseTree(tree, result, 0);
		return result;
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
	
	public void parseTree(TreeNode<DataItem> node, List<DataItemExt> list, int level){
		
		DataItemExt temp = new DataItemExt(node.getData(), level);
		list.add(temp);
		
		for ( TreeNode<DataItem> child : node.getChildren()) {
			parseTree(child, list, level + 1);
		}
	} 
	
	public TreeNode<DataItem> getTree(List<DataItemExt> list){
		
		int lastLevel = -1;
		TreeNode<DataItem> node = null;
		TreeNode<DataItem> root = null;
		
		
		for ( DataItemExt item : list) {
			
			if(node == null) {
				node = new TreeNode<DataItem>(item);
				root = node;
				lastLevel = item.level;
				continue;
			}
			
			if(item.level == lastLevel) {
				node = node.getParent().addChild(item);
				lastLevel = item.level;
				continue;
			}
			
			if(item.level > lastLevel) {
				node = node.addChild(item);
				lastLevel = item.level;
				continue;
			}
			
			if(item.level < lastLevel) {
				
				for( int cnt = lastLevel; cnt > item.level; cnt--) {
					node = node.getParent();
				}
				
				try 
				{
					node = node.getParent().addChild(item);
					lastLevel = item.level;	
				}
				catch (Exception e) {
					System.out.println(e.getMessage());
				}
				
			}
			
		}
		return root;
	}
	

}
