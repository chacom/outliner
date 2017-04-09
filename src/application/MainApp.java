package application;
	
import java.util.List;

import HolLib.HolLibIF;
import HolLib.HolLibImpl;
import application.model.DataItem;
import application.model.DataStorage;
import application.view.OutLinerViewController;
import application.view.RootLayoutController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class MainApp extends Application {
	
	
	private Stage primaryStage;
	private BorderPane rootLayout;
	OutLinerViewController 	controllerMainView;
	RootLayoutController 	controllerRootView;
	
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
			
			controllerRootView = loader.getController();
			controllerRootView.setMainApp(this);
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
			
			controllerMainView = loader.getController();
			controllerMainView.setMainApp(this);
			
			testCode();
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) throws Exception {
	
		launch(args);
		
		
		
	}
	
	public DataStorage getDataStorage(){
		return dataStorage;
	}
	
	void testCode() throws Exception{
			
		
	}

	
}
