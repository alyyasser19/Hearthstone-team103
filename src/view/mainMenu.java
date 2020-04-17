package view;

import exceptions.noHeroSelectedException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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
        start.setText("Start");
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
        characters.setPrefSize(600,720);
        Image h=new Image("images/Uther.png",110,110,false,false);
        ImageView hw= new ImageView(h);
        Image m=new Image("images/Uther.png",110,110,false,false);
        ImageView mw= new ImageView(m);
        Image pa=new Image("images/Uther.png",110,110,false,false);
        ImageView paw= new ImageView(pa);
        Image p=new Image("images/Uther.png",110,110,false,false);
        ImageView pw= new ImageView(p);
        Image w=new Image("images/Uther.png",110,110,false,false);
        ImageView ww= new ImageView(w);
        Image s=new Image("images/Uther.png",110,110,false,false);
        ImageView sw= new ImageView(s);
        Button mage= new Button("",mw);
        mage.setPrefSize(100,100);
        Button hunter= new Button("",hw);
        hunter.setPrefSize(100,100);
        Button select= new Button("",sw);
        select.setPrefSize(100,100);
        Button paladin= new Button("",paw);
        paladin.setPrefSize(100,100);
        Button warlock= new Button("",ww);
        warlock.setPrefSize(100,100);
        Button priest= new Button("",pw);
        priest.setPrefSize(100,100);
        characters.setHgap(6.5);
        characters.setVgap(30);
        characters.add(mage,16,7);
        characters.add(hunter,16,8);
        characters.add(priest,17,7);
        characters.add(paladin,17,8);
        characters.add(warlock,18,7);
        characters.add(select,18,8);
        StackPane left= new StackPane();
        left.setPrefSize(300,720);
        StackPane right= new StackPane();
        right.setPrefSize(300,720);
        wholeScreen.setLeft(left);
        wholeScreen.setRight(right);
        wholeScreen.setCenter(characters);
        characterSelect= new Scene(wholeScreen);
        start.setOnMouseClicked(e-> titleScreen.setScene(characterSelect));

        selected=false;
        mage.setOnMouseClicked(e->{
                try {
                    selectedHero= new Mage();
                } catch (IOException | CloneNotSupportedException ioException) {
                    ioException.printStackTrace();
                }
            if(!selected){
                selectedCharacter= new ImageView(new Image("images/main menu.jpg",200,720,false,false));
                left.getChildren().add(selectedCharacter);
            }
            if(selected){
                selectedCharacter= new ImageView(new Image("images/main menu.jpg",200,720,false,false));
                right.getChildren().add(selectedCharacter);
            }
        });
        warlock.setOnMouseClicked(e->{
            try {
                selectedHero= new Warlock();
            } catch (IOException | CloneNotSupportedException ioException) {
                ioException.printStackTrace();
            }
            if(!selected){
                selectedCharacter= new ImageView(new Image("images/main menu.jpg",200,720,false,false));
                left.getChildren().add(selectedCharacter);
            }
            if(selected){
                selectedCharacter= new ImageView(new Image("images/main menu.jpg",200,720,false,false));
                right.getChildren().add(selectedCharacter);
            }
        });
        paladin.setOnMouseClicked(e->{
            try {
                selectedHero= new Paladin();
            } catch (IOException | CloneNotSupportedException ioException) {
                ioException.printStackTrace();
            }
            if(!selected){
                selectedCharacter= new ImageView(new Image("images/main menu.jpg",200,720,false,false));
                left.getChildren().add(selectedCharacter);
            }
            if(selected){
                selectedCharacter= new ImageView(new Image("images/main menu.jpg",200,720,false,false));
                right.getChildren().add(selectedCharacter);
            }
        });
        priest.setOnMouseClicked(e->{
            try {
                selectedHero= new Priest();
            } catch (IOException | CloneNotSupportedException ioException) {
                ioException.printStackTrace();
            }
            if(!selected){
                selectedCharacter= new ImageView(new Image("images/main menu.jpg",200,720,false,false));
                left.getChildren().add(selectedCharacter);
            }
            if(selected){
                selectedCharacter= new ImageView(new Image("images/main menu.jpg",200,720,false,false));
                right.getChildren().add(selectedCharacter);
            }
        });
        hunter.setOnMouseClicked(e->{
            try {
                selectedHero= new Hunter();
            } catch (IOException | CloneNotSupportedException ioException) {
                ioException.printStackTrace();
            }
            if(!selected){
                selectedCharacter= new ImageView(new Image("images/main menu.jpg",200,720,false,false));
                left.getChildren().add(selectedCharacter);
            }
            if(selected){
                selectedCharacter= new ImageView(new Image("images/main menu.jpg",200,720,false,false));
                right.getChildren().add(selectedCharacter);
            }
        });
        //Farida: play Sound when select Button is pressed
        select.setOnMouseClicked(e->{
            try {
                AudioInputStream ding = AudioSystem.getAudioInputStream(new File("sounds/Ding-sound-effect.wav").getAbsoluteFile());
                Clip clip= AudioSystem.getClip();
                clip.open(ding);
                clip.start();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException error) {
                error.printStackTrace();
            }
            if(selected) {
                player2 = selectedHero;
                //try {
                    //new inGame(player1,player2);
                    //Application.launch(inGame.class,args);
                //}// catch (FullHandException fullHandException) {
                   // fullHandException.printStackTrace();
                //} //catch (CloneNotSupportedException cloneNotSupportedException) {
                   // cloneNotSupportedException.printStackTrace();
                //}
                
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
