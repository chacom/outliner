package application.view;

import java.io.File;
import java.util.List;

import HolLib.HolLibIF;
import HolLib.HolLibImpl;
import application.MainApp;
import application.model.DataItem;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class RootLayoutController {

	private MainApp mainApp;

	@FXML
	private void initialize() {
		System.out.println("initialize");
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	private void handleOpenFile() throws Exception {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Outliner files", "*.hol"));
		File selectedFile = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
		if (selectedFile != null) {
			HolLibIF myHolLib = new HolLibImpl();
			System.out.println(System.getProperty("user.dir"));
			
			List<DataItem> items = myHolLib.read(selectedFile.getPath());
			
			mainApp.getDataStorage().addChildren(items);
		}
	}

	@FXML
	private void handleSaveAction() {
		System.out.println("handleSaveAction");
	}

	@FXML
	private void handleSaveAsAction() {
		System.out.println("handleSaveAsAction");
	}

	@FXML
	private void handleCloseAction() {
		System.out.println("handleCloseAction");
	}
}
