package application.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import HolLib.NodeColor;
import application.MainApp;
import application.model.ChangeType;
import application.model.DataItem;
import application.model.DataStorage;
import application.model.ExtTreeItem;
import application.model.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
	
	Color defaultColor = null;
	
	
	@FXML
	private void initialize() {
	
		treeView.setCellFactory(tv -> new TreeCell<ExtTreeItem>() {
			
			private TextField textField;
			
			@Override
	        public void startEdit() {
	            super.startEdit();
	 
	            if (textField == null) {
	                createTextField();
	            }
	            setText(null);
	            setGraphic(textField);
	            textField.selectAll();
	        }
			
			@Override
	        public void updateItem(ExtTreeItem item, boolean empty) {
	            super.updateItem(item, empty);
	 
	            if (empty) {
	                setText(null);
	                setGraphic(null);
	            } else {
	                if (isEditing()) {
	                    if (textField != null) {
	                        textField.setText(getString());
	                    }
	                    setText(null);
	                    setGraphic(textField);
	                } else {
	                	if(defaultColor == null) 
	                	{
	                		defaultColor = (Color) getTextFill();
	                	}
	                	setGraphic(getTreeItem().getGraphic());
	                    setTextFill(getTitleColor(defaultColor, item.getColor()));
	                    setText(getString());
	                }
	            }
	        }
	 
	        private void createTextField() {
	            textField = new TextField(getString());
	          
	            textField.setOnKeyReleased(new EventHandler<Event>() {
	 
	                @Override
					public void handle(Event t) {
						if(t instanceof KeyEvent){
							KeyEvent ke = (KeyEvent) t;
							if (ke.getCode() == KeyCode.ENTER) {
								TreeItem<ExtTreeItem> currItem = treeView.getSelectionModel().getSelectedItem();
								currItem.getValue().setTitle(textField.getText());
								
								commitEdit(currItem.getValue());
								handleTitleChange(currItem);
								treeView.setEditable(false);
								
		                    } else if (ke.getCode() == KeyCode.ESCAPE) {
		                        cancelEdit();
		                        treeView.setEditable(false);
		                    }
						}
					}
	            });
	        }
	        
	        private String getString() {
	            return getItem() == null ? "" : getItem().toString();
	        }

		});

	}

	Color getTitleColor(Color colorDefault, NodeColor color) {
		
		Color funcRes = Color.BLACK;
		
		switch (color) {
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
			case Default:
				funcRes = colorDefault;
				break;
			default:
				funcRes = colorDefault;
				break;
			}
		return funcRes;
	}
	
	@FXML
	private void enableEditable(){
		treeView.setEditable(true);
	}

	@FXML
	private void handleItemSelection(MouseEvent event) {
		Node node = event.getPickResult().getIntersectedNode();
		
		if (node instanceof Text || (node instanceof TreeCell && ((TreeCell) node).getText() != null)) {
			TreeItem<ExtTreeItem> x = (TreeItem<ExtTreeItem>) treeView.getSelectionModel().getSelectedItem();
			if (x != null) {
				UUID id = x.getValue().getId();
				DataStorage storage = mainApp.getDataStorage();
				Optional<String> resText = storage.getText(id);
				textBox.clear();
				if (resText.isPresent()) {
					textBox.appendText(resText.get());
				}
				
				treeView.setEditable(false);
				lastItem = x;
			}
		}
	}

	@FXML
	private void requestContextMenu(ActionEvent event) {
		//System.out.println("requestContextMenu");
	}

	@FXML
	private void setColorBlack(ActionEvent event) {
		generalColorSetting(event, NodeColor.Black);
	}

	@FXML
	private void setColorWhite(ActionEvent event) {
		generalColorSetting(event, NodeColor.White);
	}

	@FXML
	private void setColorYellow(ActionEvent event) {
		generalColorSetting(event, NodeColor.Yellow);
	}

	@FXML
	private void setColorGreen(ActionEvent event) {
		generalColorSetting(event, NodeColor.Green);
	}

	@FXML
	private void setColorBlue(ActionEvent event) {
		generalColorSetting(event, NodeColor.Blue);
	}

	@FXML
	private void setColorRed(ActionEvent event) {
		generalColorSetting(event, NodeColor.Red);
	}

	@FXML
	private void setColorCyan(ActionEvent event) {
		generalColorSetting(event, NodeColor.Cyan);
	}

	@FXML
	private void setColorMagenta(ActionEvent event) {
		generalColorSetting(event, NodeColor.Magenta);
	}
	
	@FXML
	private void setColorDefault(ActionEvent event) {
		generalColorSetting(event, NodeColor.Default);
	}


	@FXML
	private void addChildrenNodeByUser(ActionEvent event) {

		TreeItem<ExtTreeItem> currItem = treeView.getSelectionModel().getSelectedItem();

		Optional<String> name = getNodeName();
		
		if (currItem != null) {
			UUID parentId = currItem.getValue().getId();
			
			addNewNodeInternal(name, parentId, false);
		} else {
			if (mainApp.getDataStorage().listIsEmpty()) {
				// Add root node
				addNewNodeInternal(name, null, false);
			}
		}
	}
	
	@FXML
	private void addSiblingNodeByUser(ActionEvent event) {

		TreeItem<ExtTreeItem> currItem = treeView.getSelectionModel().getSelectedItem();

		Optional<String> name = getNodeName();
		
		if (currItem != null) {
			UUID parentId = currItem.getParent().getValue().getId();
			addNewNodeInternal(name, parentId, true);
			
		} else {
			if (mainApp.getDataStorage().listIsEmpty()) {
				// Add root node
				addNewNodeInternal(name, null, false);
			}
		}
	}
	
	private Optional<String> getNodeName() {
		TextInputDialog input = new TextInputDialog("Node Name");
		input.setHeaderText("Node Name");
		input.setContentText("Please insert the node name");
		Optional<String> name = input.showAndWait();
		return name;
	}

	private void addNewNodeInternal(Optional<String> name, UUID parentId, boolean silbling)  {
		
		name.ifPresent((str) -> {
			mainApp.getDataStorage().addNewNode(str, parentId, silbling);
		});
	}
	
	

	@FXML
	private void removeNode(ActionEvent event) {

		TreeItem<ExtTreeItem> currItem = treeView.getSelectionModel().getSelectedItem();
		if (currItem != null) {
			UUID nodeId = currItem.getValue().getId();
			mainApp.getDataStorage().removeNode(nodeId);
		}
	}

	private void generalColorSetting(ActionEvent event, NodeColor currColor) {
		TreeItem<ExtTreeItem> currItem = treeView.getSelectionModel().getSelectedItem();
		if (currItem != null) {
			ExtTreeItem value = currItem.getValue();
			value.setColor(currColor);
			currItem.setValue(value);
			treeView.refresh();
		}
	}

	@FXML
	private void handleTextChange() {
		TreeItem<ExtTreeItem> currItem = treeView.getSelectionModel().getSelectedItem();
		if (currItem != null) {
			mainApp.getDataStorage().modifyText(currItem.getValue().getId(), textBox.getText());
		}
	}

	private void handleTitleChange(TreeItem<ExtTreeItem> item){
		
		TreeItem<ExtTreeItem> currItem = treeView.getSelectionModel().getSelectedItem();
		if (currItem != null){
			String newTitle = currItem.getValue().toString();
			UUID nodeId = currItem.getValue().getId();
			NodeColor currColor = currItem.getValue().getColor();
			
			mainApp.getDataStorage().modifyTitle(nodeId, newTitle, currColor);
		}
	}

	private void handleRootNotSet(DataItem item) {

		ExtTreeItem extTreeItem = new ExtTreeItem(item.getTitle(), item.getItemId(),
				item.getItemColor());
		TreeItem<ExtTreeItem> newItem = new TreeItem<ExtTreeItem>(extTreeItem);

		root = newItem;
		treeView.setRoot(root);
	}

	public void addNode(DataItem item, UUID parentId) {
		if (root == null) {
			handleRootNotSet(item);
			return;
		}
		
		ExtTreeItem extTreeItem = new ExtTreeItem(item.getTitle(), item.getItemId(),
				item.getItemColor());
		TreeItem<ExtTreeItem> treeItem = new TreeItem<ExtTreeItem>(extTreeItem);
		
		if (parentId == null) {
			root.getChildren().add(treeItem);
		} else {

			TreeItem<ExtTreeItem> foundParent = findItemWithId(root, parentId);

			if(foundParent != null) 
			{
				foundParent.getChildren().add(treeItem);
			}
		}
	}
	
	TreeItem<ExtTreeItem> findItemWithId(TreeItem<ExtTreeItem> lastItem, UUID itemId)
	{
		TreeItem<ExtTreeItem> result = null;
		UUID currId = lastItem.getValue().getId();
		
		if(!currId.equals(itemId)) 
		{
			for(TreeItem<ExtTreeItem> child : lastItem.getChildren()) 
			{
				TreeItem<ExtTreeItem> res = findItemWithId(child,itemId);
				
				if(res != null) 
				{
					result = res;
				}
			}	
		}
		else 
		{
			result = lastItem;
		}
		
		return result;
	}

	private void searchTreeItem(List <TreeItem<ExtTreeItem>> res,TreeItem<ExtTreeItem> baseItem, UUID nodeId) {
		
		if (baseItem.getValue().getId() != nodeId) {
			for (TreeItem<ExtTreeItem> item : baseItem.getChildren()) {
				searchTreeItem(res, item, nodeId);
			}
		} 
		else {
			res.add(baseItem);
		}
		
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		mainApp.getDataStorage().addListener(this);
	}

	@Override
	public void onChange(ChangeType type, DataItem item, UUID parentId) {
				
		if (type == ChangeType.Clear) {
			clearRoot();
		}

		if (type == ChangeType.Add) {
			addNode(item, parentId);
		}
		
		if (type == ChangeType.Remove) {
			ArrayList<TreeItem<ExtTreeItem>> res = new ArrayList<>();
			searchTreeItem(res, root, item.getItemId());

			if (!res.isEmpty()) {
				TreeItem<ExtTreeItem> parentFound = res.get(0).getParent();
				
				if(parentFound != null) {
					parentFound.getChildren().remove(res.get(0));
				}
				else {
					clearRoot();
				}
			}
		}

		treeView.refresh();
	}

	private void clearRoot() {
		treeView.setRoot(null);
		root = null;
	}

	
}
