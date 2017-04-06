package application.view;

import application.MainApp;
import application.model.ExtTreeItem;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;


public class OutLinerViewController {

	private MainApp mainApp;
	
	@FXML
	private TreeView<ExtTreeItem> treeView;
	
	@FXML
	private TextArea textArea;
	
	TreeItem<ExtTreeItem> root;
	
	static int nodeId = 1;
	
	
	@FXML
	private void initialize() {
		
		System.out.println("Initialize");
		root = new TreeItem<ExtTreeItem>();
		root.setValue(new ExtTreeItem("myRoot", 0));
		treeView.setRoot(root);
		
		ExtTreeItem extTreeItemTest = new ExtTreeItem("Test", nodeId);
		TreeItem<ExtTreeItem> treeItem = new TreeItem<ExtTreeItem>(extTreeItemTest);
		
		root.getChildren().add(treeItem);
		
		textArea = new TextArea();
		textArea.setText("bla");
	}
	
	public int addNode(String label, int color, int parentId)
	{
		
		//TODO How to handle the color?
		
		nodeId++;
		if(parentId == 0)
		{
			ExtTreeItem extTreeItem = new ExtTreeItem(label, nodeId);
			TreeItem<ExtTreeItem> treeItem = new TreeItem<ExtTreeItem>(extTreeItem);

			root.getChildren().add(treeItem);
		}
		else
		{
			//TODO handle other cases
		}
		
		return nodeId;
	}
	
	
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
}
