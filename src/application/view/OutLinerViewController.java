package application.view;

import java.util.Optional;

import HolLib.NodeColor;
import application.MainApp;
import application.model.ChangeType;
import application.model.DataItem;
import application.model.DataStorage;
import application.model.ExtTreeItem;
import application.model.ListChangeListener;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


public class OutLinerViewController implements ListChangeListener {

	private MainApp mainApp;
	
	@FXML
	private TreeView<ExtTreeItem> treeView;
	
	@FXML
	private TextArea textBox;
	
	TreeItem<ExtTreeItem> root;
	
	TreeItem<ExtTreeItem> lastItem;
	
	
	@FXML
	private void initialize() {
		
		System.out.println("Initialize");
		root = new TreeItem<ExtTreeItem>();
		root.setValue(new ExtTreeItem("myRoot", 0,0,NodeColor.Black));
		treeView.setRoot(root);
		lastItem = root;
		textBox.setText("bla");
		
		treeView.setCellFactory(tv ->  new TreeCell<ExtTreeItem>() {
		    @Override
		    public void updateItem(ExtTreeItem item, boolean empty) {
		        super.updateItem(item, empty);
		        if (empty) {
		            setText("");
		        } else {
		            setText(item.toString()); // appropriate text for item
		            setTextFill(getTitleColor(item.getColor()));
		        }
		    }
		});
		
	}
	
	Color getTitleColor(NodeColor color){
		Color funcRes = Color.BLACK;
		switch(color){
			case Black:
				funcRes = Color.BLACK;
				break;
			case Blue:
				funcRes = Color.BLUE;
				break;
			case Cyan:
				funcRes = Color.CYAN;
				break;
			case Green:
				funcRes = Color.GREEN;
				break;
			case Magenta:
				funcRes = Color.MAGENTA;
				break;
			case Red:
				funcRes = Color.RED;
				break;
			case White:
				funcRes = Color.WHITE;
				break;
			case Yellow:
				funcRes = Color.YELLOW;
				break;
			default:
				funcRes = Color.BLACK;
				break;
		}
		
		return funcRes;
	}
	
	@FXML
	private void handleItemSelection(MouseEvent event){
		Node node = event.getPickResult().getIntersectedNode();
		if (node instanceof Text || (node instanceof TreeCell && ((TreeCell) node).getText() != null)) {
			TreeItem<ExtTreeItem> x = (TreeItem<ExtTreeItem>) treeView.getSelectionModel().getSelectedItem();
			int id = x.getValue().getId();
			DataStorage storage = mainApp.getDataStorage();
			Optional<String> resText = storage.getText(id);
			textBox.clear();
			if(resText.isPresent()){
				System.out.println(resText.get());
				textBox.appendText(resText.get());
			}
	    }
	}
	
	public void addNode(DataItem item, int parentId)
	{
		
		//TODO How to handle the color?
		
		if(parentId == 0)
		{
			ExtTreeItem extTreeItem = new ExtTreeItem(item.getTitle(), item.getItemId(),item.getItemLevel(),item.getItemColor());
			TreeItem<ExtTreeItem> treeItem = new TreeItem<ExtTreeItem>(extTreeItem);

			root.getChildren().add(treeItem);
		}
		else
		{
			addRecursiv(root,item,parentId);
		}
	}
	
	private void addRecursiv(TreeItem<ExtTreeItem> treeItem, DataItem item, int parentId){
		if(treeItem.getValue().getId() != parentId){
			ObservableList<TreeItem<ExtTreeItem>> children = treeItem.getChildren();
			
			for(TreeItem<ExtTreeItem> child : children){
				addRecursiv(child, item, parentId);
			}
		}
		else {
			ExtTreeItem extTreeItem = new ExtTreeItem(item.getTitle(), item.getItemId(),item.getItemLevel(),item.getItemColor());
			TreeItem<ExtTreeItem> newItem = new TreeItem<ExtTreeItem>(extTreeItem);
			treeItem.getChildren().add(newItem);
		}
	}
	
	public void addNodeWithLogic(DataItem item) {
		
		int currLevel = lastItem.getValue().getLevel();
		
	
		ExtTreeItem extTreeItem = new ExtTreeItem(item.getTitle(), item.getItemId(),item.getItemLevel(),item.getItemColor());
		TreeItem<ExtTreeItem> newItem = new TreeItem<ExtTreeItem>(extTreeItem);
		
		if(currLevel == item.getItemLevel()){
			TreeItem<ExtTreeItem> parent = lastItem.getParent();
			
			parent.getChildren().add(newItem);
			lastItem = newItem;
		}
		
		if(currLevel < item.getItemLevel()){
			lastItem.getChildren().add(newItem);
			lastItem = newItem;
		}
		
		if(currLevel > item.getItemLevel()){
			boolean matchFound = false;
			TreeItem<ExtTreeItem> parent = lastItem.getParent();
			
			while(!matchFound){
				
				if(parent == root){
					System.out.println("No match found: " + item.getItemLevel());
					break;
				}
				
				if(parent.getValue().getLevel() >= item.getItemLevel())
				{
					parent = parent.getParent();
				}
				else {
					parent.getChildren().add(newItem);
					lastItem = newItem;
					matchFound = true;
				}
				
			}
		}
	}
	
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		mainApp.getDataStorage().addListener(this);
	}

	@Override
	public void onChange(ChangeType type, DataItem item) {
		if(type == ChangeType.Add)
		{
			addNodeWithLogic(item);
		}
		
	}
}
