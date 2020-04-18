package view;

import exceptions.noHeroSelectedException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
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
    Clip clip;
    boolean gameStart;

    public void start(Stage primaryStage) {
        titleScreen= new Stage();
        primaryStage=titleScreen;
        titleScreen.setResizable(false);
        titleScreen.setTitle("hearthstone team 103");
        titleScreen.show();
        Image sss= new Image("images\\start.png",500,100,true,true);
        ImageView start= new ImageView(sss);
        //Button start= new Button();
        //start.setText("Start");
        //start.setPrefSize(200,50);
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
        wholeScreen.getChildren().add(new ImageView(new Image("images/Background.jpg",1800,1000,true,true)));
        GridPane characters= new GridPane();
        characters.setPrefSize(600,720);
        Image h=new Image("images\\Rexxar.png",250,250,true,true);
        ImageView hunter= new ImageView(h);
        Image m=new Image("images\\Jaina_Proudmoore.png",250,250,true,true);
        ImageView mage= new ImageView(m);
        Image pa=new Image("images\\Uther_Lightbringer.png",250,250,true,true);
        ImageView paladin= new ImageView(pa);
        Image p=new Image("images\\Anduin_Wrynn.png",250,250,true,true);
        ImageView priest= new ImageView(p);
        Image w=new Image("images\\Guldan.png",250,250,true,true);
        ImageView warlock= new ImageView(w);
        Image s= new Image("images\\select.png",100,100,true,true);
        ImageView select= new ImageView(s);

        //Button mage= new Button("",mw);
        //mage.setPrefSize(100,100);
        //Button hunter= new Button("",hw);
        //hunter.setPrefSize(100,100);
        //Button select= new Button("Select");
        //select.setPrefSize(100,100);
        //Button paladin= new Button("",paw);
        //paladin.setCenterShape(true);
        //paladin.setCenterShape(true);
        //paladin.setPrefSize(100,100);
        //paladin.setGraphic(paw);
        //Button warlock= new Button("",ww);
        //warlock.setPrefSize(100,100);
        //Button priest= new Button("",pw);
        //priest.setPrefSize(100,100);
        characters.setHgap(6.5);
        characters.setVgap(30);
        characters.add(mage,12,5);
        characters.add(hunter,12,6);
        characters.add(priest,13,5);
        characters.add(paladin,13,6);
        characters.add(warlock,14,5);
        characters.add(select,14,6);
        StackPane left= new StackPane();
        left.setPrefSize(300,720);
        StackPane right= new StackPane();
        right.setPrefSize(300,720);
        Label x= new Label("Choose Your Hero: Player 1");
        x.setFont(new javafx.scene.text.Font("Algerian",36));
        StackPane sp= new StackPane();
        sp.getChildren().add(x);
        wholeScreen.setTop(sp);
        wholeScreen.setLeft(left);
        wholeScreen.setRight(right);
        wholeScreen.setCenter(characters);
        characterSelect= new Scene(wholeScreen);
        GridPane gameScreen= new GridPane();
        Scene game= new Scene(gameScreen);
        BackgroundImage myBI= new BackgroundImage(new Image("images/board.jpg"),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(titleScreen.getWidth(),titleScreen.getHeight(), false, false, true, false));
        gameScreen.setBackground(new Background(myBI));

        start.setOnMouseClicked(e1-> {
            titleScreen.setScene(characterSelect);
        });

        selected=false;
        mage.setOnMouseClicked(e2->{
                try {
                    selectedHero= new Mage();
                } catch (IOException | CloneNotSupportedException ioException) {
                    ioException.printStackTrace();
                }
            if(!selected){
                selectedCharacter= new ImageView(new Image("images\\mage.JPG",300,500,false,false));
                if(!left.getChildren().isEmpty())
                    left.getChildren().remove(0);
                left.getChildren().add(selectedCharacter);
            }
            if(selected){
                selectedCharacter= new ImageView(new Image("images\\mage.JPG",300,500,false,false));
                if(!right.getChildren().isEmpty())
                    right.getChildren().remove(0);
                right.getChildren().add(selectedCharacter);
            }
        });
        warlock.setOnMouseClicked(e3->{
            try {
                selectedHero= new Warlock();
            } catch (IOException | CloneNotSupportedException ioException) {
                ioException.printStackTrace();
            }
            if(!selected){
                selectedCharacter= new ImageView(new Image("images\\warlock.PNG",300,500,false,false));
                if(!left.getChildren().isEmpty())
                    left.getChildren().remove(0);
                left.getChildren().add(selectedCharacter);
            }
            if(selected){
                selectedCharacter= new ImageView(new Image("images\\warlock.PNG",300,500,false,false));
                if(!right.getChildren().isEmpty())
                    right.getChildren().remove(0);
                right.getChildren().add(selectedCharacter);
            }
        });
        paladin.setOnMouseClicked(e4->{
            try {
                selectedHero= new Paladin();
            } catch (IOException | CloneNotSupportedException ioException) {
                ioException.printStackTrace();
            }
            if(!selected){
                selectedCharacter= new ImageView(new Image("images\\paladin.PNG",300,500,false,false));
                if(!left.getChildren().isEmpty())
                    left.getChildren().remove(0);
                left.getChildren().add(selectedCharacter);
            }
            if(selected){
                selectedCharacter= new ImageView(new Image("images\\paladin.PNG",300,500,false,false));
                if(!right.getChildren().isEmpty())
                    right.getChildren().remove(0);
                right.getChildren().add(selectedCharacter);
            }
        });
        priest.setOnMouseClicked(e5->{
            try {
                selectedHero= new Priest();
            } catch (IOException | CloneNotSupportedException ioException) {
                ioException.printStackTrace();
            }
            if(!selected){
                selectedCharacter= new ImageView(new Image("images\\preist.PNG",300,500,false,false));
                if(!left.getChildren().isEmpty())
                    left.getChildren().remove(0);
                left.getChildren().add(selectedCharacter);
            }
            if(selected){
                selectedCharacter= new ImageView(new Image("images\\preist.PNG",300,500,false,false));
                if(!right.getChildren().isEmpty())
                    right.getChildren().remove(0);
                right.getChildren().add(selectedCharacter);
            }
        });
        hunter.setOnMouseClicked(e6->{
            try {
                selectedHero= new Hunter();
            } catch (IOException | CloneNotSupportedException ioException) {
                ioException.printStackTrace();
            }
            if(!selected){
                selectedCharacter= new ImageView(new Image("images\\hunter.PNG",300,500,false,false));
                if(!left.getChildren().isEmpty())
                    left.getChildren().remove(0);
                left.getChildren().add(selectedCharacter);
            }
            if(selected){
                selectedCharacter= new ImageView(new Image("images\\hunter.PNG",300,500,false,false));
               if(!right.getChildren().isEmpty())
                   right.getChildren().remove(0);
                right.getChildren().add(selectedCharacter);
            }
        });
        select.setOnMouseClicked(e1->{
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
                clip.stop();
                gameStart=true;
                titleScreen.setScene(game);
                titleScreen.setFullScreen(true);
                music("sounds/Game.wav");
            }
            if(selectedHero!=null){
            if(!selected){
            player1= selectedHero;
            selected=true;
                Label x2= new Label("Choose Your Hero: Player 2");
                x2.setFont(new javafx.scene.text.Font("Algerian",36));
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
