package view;

import javafx.application.Application;
import javafx.stage.Stage;
import model.heroes.Hero;

public class inGame extends Application {
    Hero p1;
    Hero p2;
    inGame(Hero p1, Hero p2){
        this.p1=p1;
        this.p2=p2;
    }
    public void start(Stage stage) {

    }
}
