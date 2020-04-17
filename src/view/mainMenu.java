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
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import model.heroes.*;
import net.sf.cglib.asm.$Label;

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
    Clip clip;

    public void start(Stage primaryStage) {
        titleScreen= new Stage();
        primaryStage=titleScreen;
        titleScreen.setResizable(false);
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
        wholeScreen.getChildren().add(new ImageView(new Image("images/Background.jpg",1800,1000,false,false)));
        GridPane characters= new GridPane();
        characters.setPrefSize(600,720);
        Image h=new Image("images\\Rexxar.png",110,110,false,false);
        ImageView hw= new ImageView(h);
        Image m=new Image("images\\Jaina_Proudmoore.png",110,110,false,false);
        ImageView mw= new ImageView(m);
        Image pa=new Image("images\\Uther_Lightbringer.png",110,110,false,false);
        ImageView paw= new ImageView(pa);
        Image p=new Image("images\\Anduin_Wrynn.png",110,110,false,false);
        ImageView pw= new ImageView(p);
        Image w=new Image("images\\Guldan.png",110,110,false,false);
        ImageView ww= new ImageView(w);
        Button mage= new Button("",mw);
        mage.setPrefSize(100,100);
        Button hunter= new Button("",hw);
        hunter.setPrefSize(100,100);
        Button select= new Button("Select");
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
        Label x= new Label("Choose Your Hero: Player 1");
        Font f= new Font("Algerian", 36);
        x.setFont(f);
        StackPane sp= new StackPane();
        sp.getChildren().add(x);
        wholeScreen.setTop(sp);
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
                selectedCharacter= new ImageView(new Image("images\\Jaina_Proudmoore.png",200,720,false,false));
                left.getChildren().add(selectedCharacter);
            }
            if(selected){
                selectedCharacter= new ImageView(new Image("images\\Jaina_Proudmoore.png",200,720,false,false));
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
                selectedCharacter= new ImageView(new Image("images\\Guldan.png",200,720,false,false));
                left.getChildren().add(selectedCharacter);
            }
            if(selected){
                selectedCharacter= new ImageView(new Image("images\\Guldan.png",200,720,false,false));
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
                selectedCharacter= new ImageView(new Image("images\\Uther_Lightbringer.png",200,720,false,false));
                left.getChildren().add(selectedCharacter);
            }
            if(selected){
                selectedCharacter= new ImageView(new Image("images\\Uther_Lightbringer.png",200,720,false,false));
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
                selectedCharacter= new ImageView(new Image("images\\Anduin_Wrynn.png",200,720,false,false));
                left.getChildren().add(selectedCharacter);
            }
            if(selected){
                selectedCharacter= new ImageView(new Image("images\\Anduin_Wrynn.png",200,720,false,false));
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
                selectedCharacter= new ImageView(new Image("images\\Rexxar.png",200,720,false,false));
                left.getChildren().add(selectedCharacter);
            }
            if(selected){
                selectedCharacter= new ImageView(new Image("images\\Rexxar.png",200,720,false,false));
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
                clip.stop();
                Stage ad = new Stage();
                new inGame().start(ad);
                titleScreen.close();

            }
            if(selectedHero!=null){
            if(!selected){
            player1= selectedHero;
            selected=true;
                Label x2= new Label("Choose Your Hero: Player 2");
                Font f2= new Font("Algerian", 36);
                x2.setFont(f2);
                sp.getChildren().remove(x);
                sp.getChildren().add(x2);
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
            clip= AudioSystem.getClip();
            clip.open(a);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

}
