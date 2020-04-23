package tests;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.cards.Rarity;
import model.cards.minions.Minion;
import view.minionButton;

public class test extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		minionButton aa= new minionButton(new Minion("Icehowl",1, Rarity.BASIC,1,1,true,true,true));
		aa.setPrefSize(150,150);
		aa.verifyMinion();
		Rectangle rect = new Rectangle(0, 0, 100, 100);
		Tooltip t = new Tooltip("A Square");
		Tooltip.install(rect, t);
		Tooltip.install(aa,new Tooltip("dadas"));
		stage.setTitle("HEHE");
		Button a=new Button();
		a.setText("appear");
		HBox layout = new HBox();
		//layout.getChildren().add(rect);
		//layout.getChildren().add(aa);
		Button btn = new Button("button");
		//btn.setTooltip(new Tooltip("This is a tooltip"));
		Tooltip ada = new Tooltip();
		ada.setMinSize(100,100);
		ada.setText("jafsfjklasf");
		ada.setAutoFix(true);
		ada.setForceIntegerRenderScale(true);
		ada.setOpacity(100);
		ada.setShowDuration(Duration.hours(1));
		Tooltip.install(btn, new Tooltip("btnSettingsOkt")); //This is for showing
		//btn.setTooltip(ada);
		layout.getChildren().add(btn);
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
