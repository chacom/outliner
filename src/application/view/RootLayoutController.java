package application.view;

import java.io.File;
import java.io.IOException;
import java.util.List;

import HolLib.HolLibIF;
import HolLib.HolLibImpl;
import application.JsonExport;
import application.MainApp;
import application.model.DataItem;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class RootLayoutController {

	private MainApp mainApp;
	String lastUsedFilePath = null;

	@FXML
	private void initialize() {
		System.out.println("initialize");
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	@FXML
	private void handleNewFile() {
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select new file");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Outliner files", "*.hol"));
		File selectedFile = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
		if (selectedFile != null) {
			lastUsedFilePath = selectedFile.getPath();
		}
	}
	
	

	@FXML
	private void handleOpenFile() throws Exception {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Outliner files", "*.hol"));
		File selectedFile = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
		if (selectedFile != null) {
			HolLibIF myHolLib = new HolLibImpl();
			
			List<DataItem> items = myHolLib.read(selectedFile.getPath());
			lastUsedFilePath = selectedFile.getPath();
			mainApp.getDataStorage().addChildren(items);
		}
	}

	@FXML
	private void handleSaveAction() throws Exception {
		if(lastUsedFilePath != null){
			HolLibIF myHolLib = new HolLibImpl();
			List<DataItem> data = mainApp.getDataStorage().getData();
			
			myHolLib.write(lastUsedFilePath, data);
		}
			
	}
	
	@FXML
	private void handleExport() throws IOException{
		JsonExport jsonExport = new JsonExport();
		
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Export as JSON");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("JSON", "*.json"));
		
		File selectedFile = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
		if (selectedFile != null) {
			
			List<DataItem> data = mainApp.getDataStorage().getData();
			
			jsonExport.export(selectedFile.getPath(), data);
		}
	}

	@FXML
	private void handleSaveAsAction() throws Exception {
		System.out.println("handleSaveAsAction");
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save file as");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Outliner files", "*.hol"));
		
		File selectedFile = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
		if (selectedFile != null) {
			HolLibIF myHolLib = new HolLibImpl();
			List<DataItem> data = mainApp.getDataStorage().getData();
			myHolLib.write(selectedFile.getPath(), data);
		}
		
		
	}

	@FXML
	private void handleCloseAction() {
		System.out.println("handleCloseAction");
	}
}
