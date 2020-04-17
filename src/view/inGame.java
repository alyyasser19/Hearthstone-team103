package view;

import engine.Game;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.heroes.Hero;

public class inGame extends Application  {
    Hero p1;
    Hero p2;
    Game game;

    public void start(Stage stage) {
        //images are to be added of heroes based on Instance of hero 1 bottom left hero 2 top right
        //if(p1 instanceof xxx)
        //if(p2 instanceof xxx)
        stage=new Stage();
        stage.show();
        BorderPane wholeScreen= new BorderPane();
        FlowPane test= new FlowPane();
        Scene game= new Scene(wholeScreen);
        wholeScreen.setCenter(test);
        stage.setScene(game);
        BackgroundImage myBI= new BackgroundImage(new Image("images/board.jpg"),
               BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(stage.getWidth(),stage.getHeight(), false, false, true, false));
        wholeScreen.setBackground(new Background(myBI));



    stage.setFullScreen(true);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
