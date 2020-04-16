package tests;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class test extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("HEHE");
		Button a=new Button();
		a.setText("appear");
		StackPane layout = new StackPane();
		layout.getChildren().add(a);
		Scene bla= new Scene(layout, 720, 360);
		stage.setScene(bla);
		stage.show();
		a.setPrefWidth(500);
		Text text= new Text("YOO");;
		a.setOnMouseEntered(e -> {
			layout.getChildren().add(text);
			layout.getChildren().get(1).setLayoutX(300);
		});
		a.setOnMouseExited(e ->{
			layout.getChildren().remove(text);
		});
		a.setOnAction(e -> System.out.println("haha"));

	}
}
