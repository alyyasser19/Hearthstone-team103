package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.heroes.Hero;

import javax.sound.sampled.*;
import java.io.*;

public class mainMenu extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    Stage titleScreen;
    Scene menu;
    Scene characterSelect;
    Hero selectedHero;
    boolean player1Select;

    public void start(Stage primaryStage) {
        titleScreen= new Stage();
        primaryStage=titleScreen;
        titleScreen.setTitle("hearthstone team 103");
        titleScreen.show();
        Button start= new Button();
        start.setText("start");
        start.setPrefSize(200,50);
        titleScreen.sizeToScene();
        StackPane main= new StackPane();
        Image Bg= new Image("images/main menu.jpg");
        ImageView a= new ImageView(Bg);
        a.fitWidthProperty().bind(main.widthProperty());
        a.fitHeightProperty().bind(main.heightProperty());
        main.getChildren().add(a);
        main.getChildren().add(start);
        menu= new Scene(main,1200, 720);
        titleScreen.setScene(menu);
        music("sounds/menu.wav");
        BorderPane wholeScreen= new BorderPane();
        GridPane characters= new GridPane();
        Button mage= new Button("Jaina");
        mage.setPrefSize(100,100);
        Button hunter= new Button("Rexar");
        hunter.setPrefSize(100,100);
        Button warrior= new Button("Garosh");
        warrior.setPrefSize(100,100);
        Button paladin= new Button("Urther");
        paladin.setPrefSize(100,100);
        Button warlock= new Button("Gul'dan");
        warlock.setPrefSize(100,100);
        Button priest= new Button("Auduin");
        priest.setPrefSize(100,100);
        characters.setHgap(30);
        characters.setVgap(30);
        characters.add(mage,17,7);
        characters.add(hunter,17,8);
        characters.add(warrior,18,7);
        characters.add(paladin,18,8);
        characters.add(warlock,19,7);
        characters.add(priest,19,8);
        characters.setPrefSize(1200,720);
        wholeScreen.setCenter(characters);
        characterSelect= new Scene(wholeScreen);
        start.setOnMouseClicked(e->{
            titleScreen.setScene(characterSelect);
        });

        primaryStage.setMaximized(true);
    }
    public void music(String filepath) {
        try {
            AudioInputStream a = AudioSystem.getAudioInputStream(new File(filepath).getAbsoluteFile());
            Clip clip= AudioSystem.getClip();
            clip.open(a);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }
}
