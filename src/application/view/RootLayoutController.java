package application.view;

import java.io.File;
import java.io.IOException;
import java.util.List;

import HolLib.HolLibIF;
import HolLib.HolLibImpl;
import application.AnkiExport;
import application.AsciiDocTable;
import application.CsvExport;
import application.JsonExport;
import application.MainApp;
import application.MarkdownExport;
import application.model.DataItemExt;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class RootLayoutController {

	String lastPath = null;
	
	private MainApp mainApp;
	String lastUsedFilePath = null;

	@FXML
	private void initialize() {
	
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
			mainApp.getDataStorage().clearData();
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
			mainApp.getDataStorage().clearData();
			HolLibIF myHolLib = new HolLibImpl();
			
			List<DataItemExt> items = myHolLib.read(selectedFile.getPath());
			lastUsedFilePath = selectedFile.getPath();
			mainApp.getDataStorage().createTree(items);
		}
	}

	@FXML
	private void handleSaveAction() throws Exception {
		if(lastUsedFilePath != null){
			HolLibIF myHolLib = new HolLibImpl();
			List<DataItemExt> data = mainApp.getDataStorage().getData();
			
			myHolLib.write(lastUsedFilePath, data);
		}
	}
	
	@FXML
	private void handleExport() throws IOException{
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Export as JSON");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("JSON", "*.json"));
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Markdown", "*.md"));
		fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV special", "*.csv"));
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Asciidoc table special", "*.adoc"));
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Anki", "*.txt"));
		
		File selectedFile = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
		if (selectedFile != null) {
			
			List<DataItemExt> data = mainApp.getDataStorage().getData();
			
			if(selectedFile.getPath().contains(".json")) 
			{
				JsonExport jsonExport = new JsonExport();
				jsonExport.export(selectedFile.getPath(), data);
			}
			
			if(selectedFile.getPath().contains(".md")) 
			{
				MarkdownExport mdExport = new MarkdownExport();
				mdExport.export(selectedFile.getPath(), data);
			}
			
			if(selectedFile.getPath().contains(".csv")) 
			{
				CsvExport mdExport = new CsvExport();
				mdExport.export(selectedFile.getPath(), data);
			}
			
			if(selectedFile.getPath().contains(".adoc")) 
			{
				AsciiDocTable mdExport = new AsciiDocTable();
				mdExport.export(selectedFile.getPath(), data);
			}
			
			if(selectedFile.getPath().contains(".txt")) 
			{
				AnkiExport mdExport = new AnkiExport();
				mdExport.export(selectedFile.getPath(), data);
			}
						
		}
	}

	@FXML
	private void handleSaveAsAction() throws Exception {
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save file as");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Outliner files", "*.hol"));
		
		File selectedFile = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
		if (selectedFile != null) {
			HolLibIF myHolLib = new HolLibImpl();
			List<DataItemExt> data = mainApp.getDataStorage().getData();
			myHolLib.write(selectedFile.getPath(), data);
		}	
	}

	@FXML
	private void handleCloseAction() {
		mainApp.getPrimaryStage().close();
	}
	
}
