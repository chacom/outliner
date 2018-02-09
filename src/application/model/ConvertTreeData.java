package application.model;

import java.util.List;

public class ConvertTreeData {
	
	List<DataItemExt> getList(TreeNode<DataItem> tree){
	
		return null;
	}
	
	void parseTree(TreeNode<DataItem> node, List<DataItemExt> list, int level){
		
		DataItemExt temp = new DataItemExt(node.getData(), level);
		list.add(temp);
		
		for ( TreeNode<DataItem> child : node.getChildren()) {
			parseTree(child, list, level + 1);
		}
	} 
	
	TreeNode<DataItem> getTree(List<DataItemExt> list){
		
		int lastLevel = -1;
		TreeNode<DataItem> node = null;
		
		
		for ( DataItemExt item : list) {
			
			if(node == null) {
				node = new TreeNode<DataItem>(item);
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
				
				node.addChild(item);
			}
			
		}
		return null;
	}
	

}
