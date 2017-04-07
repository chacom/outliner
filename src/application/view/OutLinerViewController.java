package application.view;

import java.util.Optional;

import HolLib.Color;
import application.MainApp;
import application.model.DataStorage;
import application.model.ExtTreeItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;


public class OutLinerViewController {

	private MainApp mainApp;
	
	@FXML
	private TreeView<ExtTreeItem> treeView;
	
	@FXML
	private TextArea textArea;
	
	TreeItem<ExtTreeItem> root;
	
	
	@FXML
	private void initialize() {
		
		System.out.println("Initialize");
		root = new TreeItem<ExtTreeItem>();
		root.setValue(new ExtTreeItem("myRoot", 0));
		treeView.setRoot(root);
		
		textArea = new TextArea();
		textArea.setText("bla");
	}
	
	@FXML
	private void handleItemSelection(MouseEvent event){
		Node node = event.getPickResult().getIntersectedNode();
		if (node instanceof Text || (node instanceof TreeCell && ((TreeCell) node).getText() != null)) {
			TreeItem<ExtTreeItem> x = (TreeItem<ExtTreeItem>) treeView.getSelectionModel().getSelectedItem();
			int id = x.getValue().getId();
			DataStorage storage = mainApp.getDataStorage();
			Optional<String> resText = storage.getText(id);
			if(resText.isPresent()){
				textArea.setText(resText.get());
			}
	    }
	}
	
	public int addNode(String label, Color color, int nodeId, int parentId)
	{
		
		//TODO How to handle the color?
		
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
