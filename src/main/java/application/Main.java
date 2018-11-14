package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("GUI.fxml"));
			
					
			/*			
			ArrayList<Cell> CellsLane1 = new ArrayList<>();
			ArrayList<Cell> CellsLane2 = new ArrayList<>();

			for (int i = 0; i < 1900; i++) {
				
				Cell cell = (new Cell(i + 10, 20, 1, 40, Color.GREY));
				CellsLane1.add(cell);
				CellsLane2.add(cell);
			*/
			
	
			
		
			primaryStage.setTitle("TraffIT GUI");
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
