package application.view;

import java.util.Optional;

import com.sun.java.swing.plaf.motif.MotifBorders.FocusBorder;

import HolLib.NodeColor;
import application.MainApp;
import application.model.ChangeType;
import application.model.DataItem;
import application.model.DataStorage;
import application.model.ExtTreeItem;
import application.model.ListChangeListener;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ContextMenuBuilder;
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
			if(x != null){
				int id = x.getValue().getId();
				DataStorage storage = mainApp.getDataStorage();
				Optional<String> resText = storage.getText(id);
				textBox.clear();
				if(resText.isPresent()){
					textBox.appendText(resText.get());
				}
			}
	    }
	}
	
	@FXML
	private void requestContextMenu(ActionEvent event)
	{
		System.out.println("requestContextMenu");
	}
	
	@FXML
	private void setColorBlack(ActionEvent event){
		generalColorSetting(event,NodeColor.Black);
	}
	
	@FXML
	private void setColorWhite(ActionEvent event){
		generalColorSetting(event,NodeColor.White);
	}

	@FXML
	private void setColorYellow(ActionEvent event){
		generalColorSetting(event,NodeColor.Yellow);
	}

	@FXML
	private void setColorGreen(ActionEvent event){
		generalColorSetting(event,NodeColor.Green);
	}

	@FXML
	private void setColorBlue(ActionEvent event){
		generalColorSetting(event,NodeColor.Blue);
	}

	@FXML
	private void setColorRed(ActionEvent event){
		generalColorSetting(event,NodeColor.Red);
	}

	@FXML
	private void setColorCyan(ActionEvent event){
		generalColorSetting(event,NodeColor.Cyan);
	}

	@FXML
	private void setColorMagenta(ActionEvent event){
		generalColorSetting(event,NodeColor.Magenta);
	}
	
	
	@FXML
	private void addNodeByUser(ActionEvent event){
		
		TreeItem<ExtTreeItem> currItem = treeView.getSelectionModel().getSelectedItem();
		int parentId = currItem.getValue().getId();
		int parentLevel = currItem.getValue().getLevel();
		
		mainApp.getDataStorage().addNewNode("Dummy", parentId, parentLevel + 1);
	}
	
	@FXML
	private void removeNode(ActionEvent event){
		
		TreeItem<ExtTreeItem> currItem = treeView.getSelectionModel().getSelectedItem();
		
		int nodeId = currItem.getValue().getId();
		mainApp.getDataStorage().removeNode(nodeId);
	}
	
	private void generalColorSetting(ActionEvent event, NodeColor currColor)
	{
		TreeItem<ExtTreeItem> currItem = treeView.getSelectionModel().getSelectedItem();
		ExtTreeItem value = currItem.getValue();
		value.setColor(currColor);
		currItem.setValue(value);
		treeView.refresh();
	}
	
	
	@FXML
	private void handleTextChange() {
		TreeItem<ExtTreeItem> currItem = treeView.getSelectionModel().getSelectedItem();
		
		mainApp.getDataStorage().modifyText(currItem.getValue().getId(), textBox.getText());
	}
	
	@FXML
	private void handleTitleChange(){
		TreeItem<ExtTreeItem> currItem = treeView.getSelectionModel().getSelectedItem();
		
		String newTitle = currItem.getValue().toString();
		int nodeId = currItem.getValue().getId();
		NodeColor currColor = currItem.getValue().getColor();
		
		mainApp.getDataStorage().modifyTitle(nodeId, newTitle, currColor);
	}
	

	
	public void addNode(DataItem item, int parentId)
	{
		if(root == null){
			handleRootNotSet(item);
			return;
		}
		
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
	
	private void handleRootNotSet(DataItem item){
		
		ExtTreeItem extTreeItem = new ExtTreeItem(item.getTitle(), item.getItemId(),item.getItemLevel(),item.getItemColor());
		TreeItem<ExtTreeItem> newItem = new TreeItem<ExtTreeItem>(extTreeItem);
		
		root = newItem;
		treeView.setRoot(root);
		lastItem = newItem;
	}
	
	public void addNodeWithLogic(DataItem item) {
		
		ExtTreeItem extTreeItem = new ExtTreeItem(item.getTitle(), item.getItemId(),item.getItemLevel(),item.getItemColor());
		TreeItem<ExtTreeItem> newItem = new TreeItem<ExtTreeItem>(extTreeItem);
		
		if(root == null){
			handleRootNotSet(item);
			return;
		}
		
		int currLevel = lastItem.getValue().getLevel();
		
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
				
				TreeItem<ExtTreeItem> tempParent = parent;
				
				if(parent.getValue().getLevel() >= item.getItemLevel())
				{
					parent = parent.getParent();
				}
				else {
					parent.getChildren().add(newItem);
					lastItem = newItem;
					matchFound = true;
				}
				
				if((tempParent == root) && (!matchFound)){
					System.out.println("No match found: " + item.getItemLevel());
					break;
				}
				
			}
		}
	}
	
	private  TreeItem<ExtTreeItem> searchTreeItem(TreeItem<ExtTreeItem> baseItem, int nodeId)
	{
		TreeItem<ExtTreeItem> funcRes = null;
		
		if(baseItem.getValue().getId() != nodeId){
			for (TreeItem<ExtTreeItem> item : baseItem.getChildren()){
				funcRes = searchTreeItem(item, nodeId);
				if(funcRes != null){
					return funcRes;
				}
			}
		}
		else{
			return baseItem;
		}
		
		return funcRes;
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		mainApp.getDataStorage().addListener(this);
	}

	@Override
	public void onChange(ChangeType type, DataItem item, int extInfo) {
		if(type == ChangeType.Add)
		{
			addNodeWithLogic(item);
			
		}
		
		if(type == ChangeType.AddToPresent)
		{
			int tempId = extInfo;
			TreeItem<ExtTreeItem> searchItem = searchTreeItem(root,tempId);
			
			int parentId = searchItem.getValue().getId();
			addNode(item, parentId);
		}
		
		if(type == ChangeType.Remove)
		{
			TreeItem<ExtTreeItem> foundItem = searchTreeItem(root,item.getItemId());
			
			if(foundItem != null){
				TreeItem<ExtTreeItem> parent = foundItem.getParent();
				parent.getChildren().remove(foundItem);
			}
		}
		
		treeView.refresh();
		
	}
}
