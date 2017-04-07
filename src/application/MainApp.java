package application;
	
import java.util.List;

import com.sun.prism.paint.Color;

import HolLib.DataItem;
import HolLib.HolLibIF;
import HolLib.HolLibImpl;
import application.model.DataStorage;
import application.model.OutLinerNode;
import application.view.OutLinerViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class MainApp extends Application {
	
	
	private Stage primaryStage;
	private BorderPane rootLayout;
	OutLinerViewController controller;
	DataStorage dataStorage = new DataStorage();
	
	@Override
	public void start(Stage primaryStage) {
		
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("OutLiner");
		
		initRootLayout();
		
		showOutLiner();
	}
	
	public void initRootLayout() {
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		
	}
	
	public void showOutLiner()
	{
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/MainView.fxml"));
			AnchorPane outLinerView = (AnchorPane) loader.load();
			
			rootLayout.setCenter(outLinerView);
			
			controller = loader.getController();
			controller.setMainApp(this);
			
			testCode();
		}
		catch (Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) throws Exception {
	
		launch(args);
		System.out.println(System.getProperty("user.dir"));
		
		
	}
	
	public DataStorage getDataStorage(){
		return dataStorage;
	}
	
	void testCode() throws Exception{
		HolLibIF myHolLib = new HolLibImpl(); 
		List<DataItem> items = myHolLib.read("test.hol");
		
		for(DataItem item : items){
			OutLinerNode nodeItem = new OutLinerNode(item.getTitle(), item.getItemColor(), item.getItemText());
			dataStorage.addChild(nodeItem, 0);
			controller.addNode(item.getTitle(), item.getItemColor(),item.getItemId(), 0);
		}

	}
}
