package view;

import engine.Game;
import exceptions.FullHandException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.heroes.Hero;
import model.heroes.Paladin;
import model.heroes.Warlock;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class inGame extends Application  {


    //Resources
    Clip clip;
    ImageView p1Icon;
    ImageView p2Icon;

    //Game Classes
    Hero p1;
    Hero p2;
    Game game;

    public void start(Stage stage) throws IOException, CloneNotSupportedException, FullHandException {
        p1= new Paladin();
        p2 = new Warlock();
        Game main=new Game(p1,p2);
        p1Icon= new ImageView(new Image("images\\Uther_Lightbringer.png",150,200,true,true));
        //images are to be added of heroes based on Instance of hero 1 bottom left hero 2 top right
        //if(p1 instanceof xxx)
        //if(p2 instanceof xxx)
        stage=new Stage();
        stage.show();
        GridPane gamescreen= new GridPane();
        Scene game= new Scene(gamescreen,1360,768);
        stage.setScene(game);
        BackgroundImage myBI= new BackgroundImage(new Image("images/board.jpg"),
               BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(stage.getWidth(),stage.getHeight(), false, false, true, false));
        gamescreen.setBackground(new Background(myBI));
        music("sounds/Game.wav");
        gamescreen.setMinSize(400, 200);
        gamescreen.setPadding(new Insets(10, 10, 10, 10));
        gamescreen.setVgap(5);
        gamescreen.setHgap(5);
        gamescreen.add(p1Icon,109,99);
        stage.setResizable(false);




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
