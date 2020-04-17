package view;

import engine.Game;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.heroes.Hero;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class inGame extends Application  {


    //Resources
    Clip clip;

    //Game Classes
    Hero p1;
    Hero p2;
    Game game;

    public void start(Stage stage) {
        //images are to be added of heroes based on Instance of hero 1 bottom left hero 2 top right
        //if(p1 instanceof xxx)
        //if(p2 instanceof xxx)
        stage=new Stage();
        stage.show();
        GridPane gamescreen= new GridPane();
        Scene game= new Scene(gamescreen);
        stage.setScene(game);
        BackgroundImage myBI= new BackgroundImage(new Image("images/board.jpg"),
               BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(stage.getWidth(),stage.getHeight(), false, false, true, false));
        gamescreen.setBackground(new Background(myBI));
        music("sounds/Game.wav");



    stage.setFullScreen(true);
    }

    public static void main(String[] args) {
        launch(args);
    }
    public void music(String filepath) {
        try {
            AudioInputStream a = AudioSystem.getAudioInputStream(new File(filepath).getAbsoluteFile());
            clip= AudioSystem.getClip();
            clip.open(a);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

}
