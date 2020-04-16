package view;

import exceptions.noHeroSelectedException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.heroes.*;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class mainMenu extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private Stage titleScreen;
    private Scene menu;
    private Scene characterSelect;
    private Hero selectedHero;
    private Hero player1;
    private Hero player2;
    private ImageView selectedCharacter;
    boolean selected;

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
        wholeScreen.setPrefSize(1200,720);
        GridPane characters= new GridPane();
        Button mage= new Button("Jaina");
        mage.setPrefSize(100,100);
        Button hunter= new Button("Rexar");
        hunter.setPrefSize(100,100);
        Button select= new Button("select!");
        select.setPrefSize(100,100);
        Button paladin= new Button("Urther");
        paladin.setPrefSize(100,100);
        Button warlock= new Button("Gul'dan");
        warlock.setPrefSize(100,100);
        Button priest= new Button("Auduin");
        priest.setPrefSize(100,100);
        characters.setHgap(30);
        characters.setVgap(30);

        //Farida add an Image to every Hero
        characters.add(mage,17,7);
        characters.add(hunter,17,8);
        characters.add(priest,18,7);
        characters.add(paladin,18,8);
        characters.add(warlock,19,7);
        characters.add(select,19,8);
        HBox left= new HBox();
        HBox right=new HBox();
        wholeScreen.setCenter(characters);
        wholeScreen.setLeft(left);
        wholeScreen.setRight(right);
        characterSelect= new Scene(wholeScreen);
        start.setOnMouseClicked(e->{
            titleScreen.setScene(characterSelect);
        });

        //Hero Selection
        selected=false;
        mage.setOnMouseClicked(e->{
            if(!selected){
            try {
                selectedHero= new Mage();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (CloneNotSupportedException cloneNotSupportedException) {
                cloneNotSupportedException.printStackTrace();
            }
            //Farida: insert Character image to the left.
            //selectedCharacter= new ImageView(new Image("images/main menu.jpg"));
            //left.getChildren().add(selectedCharacter);
                                 }
        });
        mage.setOnMouseClicked(e->{
                try {
                    selectedHero= new Mage();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (CloneNotSupportedException cloneNotSupportedException) {
                    cloneNotSupportedException.printStackTrace();
                }
            if(!selected){
                //Farida: insert Character image to the left.
                //selectedCharacter= new ImageView(new Image("images/main menu.jpg"));
                //left.getChildren().add(selectedCharacter);
            }
            if(selected){
                //Farida: insert Character image to the right.
                //selectedCharacter= new ImageView(new Image("images/main menu.jpg"));
                //left.getChildren().add(selectedCharacter);
            }
        });
        warlock.setOnMouseClicked(e->{
            try {
                selectedHero= new Warlock();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (CloneNotSupportedException cloneNotSupportedException) {
                cloneNotSupportedException.printStackTrace();
            }
            if(!selected){
                //Farida: insert Character image to the left.
                //selectedCharacter= new ImageView(new Image("images/main menu.jpg"));
                //left.getChildren().add(selectedCharacter);
            }
            if(selected){
                //Farida: insert Character image to the right.
                //selectedCharacter= new ImageView(new Image("images/main menu.jpg"));
                //left.getChildren().add(selectedCharacter);
            }
        });
        paladin.setOnMouseClicked(e->{
            try {
                selectedHero= new Paladin();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (CloneNotSupportedException cloneNotSupportedException) {
                cloneNotSupportedException.printStackTrace();
            }
            if(!selected){
                //Farida: insert Character image to the left.
                //selectedCharacter= new ImageView(new Image("images/main menu.jpg"));
                //left.getChildren().add(selectedCharacter);
            }
            if(selected){
                //Farida: insert Character image to the right.
                //selectedCharacter= new ImageView(new Image("images/main menu.jpg"));
                //left.getChildren().add(selectedCharacter);
            }
        });
        priest.setOnMouseClicked(e->{
            try {
                selectedHero= new Priest();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (CloneNotSupportedException cloneNotSupportedException) {
                cloneNotSupportedException.printStackTrace();
            }
            if(!selected){
                //Farida: insert Character image to the left.
                //selectedCharacter= new ImageView(new Image("images/main menu.jpg"));
                //left.getChildren().add(selectedCharacter);
            }
            if(selected){
                //Farida: insert Character image to the right.
                //selectedCharacter= new ImageView(new Image("images/main menu.jpg"));
                //left.getChildren().add(selectedCharacter);
            }
        });
        hunter.setOnMouseClicked(e->{
            try {
                selectedHero= new Hunter();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (CloneNotSupportedException cloneNotSupportedException) {
                cloneNotSupportedException.printStackTrace();
            }
            if(!selected){
                //Farida: insert Character image to the left.
                //selectedCharacter= new ImageView(new Image("images/main menu.jpg"));
                //left.getChildren().add(selectedCharacter);
            }
            if(selected){
                //Farida: insert Character image to the right.
                //selectedCharacter= new ImageView(new Image("images/main menu.jpg"));
                //left.getChildren().add(selectedCharacter);
            }
        });
        //Farida: play Sound when select Button is pressed
        select.setOnMouseClicked(e->{
            if(selected) {
                player2 = selectedHero;
                new inGame(player1,player2);
                titleScreen.hide();

            }
            if(selectedHero!=null){
            if(!selected){
            player1= selectedHero;
            selected=true;
            selectedHero=null;}

        }
            else
                try {
                    throw new noHeroSelectedException("Select a Hero");
                } catch (exceptions.noHeroSelectedException noHeroSelectedException) {
                    noHeroSelectedException.printStackTrace();
                }

                }
        );




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
