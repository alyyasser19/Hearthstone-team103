package view;

import engine.Game;
import exceptions.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.cards.Card;
import model.cards.minions.Minion;
import model.cards.spells.*;
import model.heroes.Hero;
import model.heroes.Mage;
import model.heroes.Paladin;
import model.heroes.Warlock;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.heroes.*;

import javax.sound.sampled.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class inGame extends Application  {


    //Resources
    Clip clip;
    ImageView p1Icon;
    ImageView p2Icon;

    //Game Classes
    Hero p1;
    Hero p2;
    Game game;

    Minion minionTarget;
    Hero heroTarget;
    boolean targeting;
    ArrayList<Minion> p1Field;
    ArrayList<Minion> p2Field;

    public void start(Stage stage) throws IOException, CloneNotSupportedException, FullHandException {
        p1=new Mage();
        p2=new Paladin();
        Game g=new Game(p1,p2);
        stage=new Stage();
        stage.show();
        p1Icon= new ImageView("images\\Jaina_Proudmoore.png");
        p2Icon=new ImageView("images\\Uther_Lightbringer.png");
        BorderPane gamescreen= new BorderPane();
        FlowPane hand= new FlowPane();
        hand.setPrefSize(1360,192);
        BorderPane Top=new BorderPane();
        Top.setPrefSize(1360,384);
        BorderPane Bottom=new BorderPane();
        Bottom.setPrefSize(1360,384);
        gamescreen.setTop(Top);
        gamescreen.setBottom(Bottom);
        Bottom.setBottom(hand);
        Scene game= new Scene(gamescreen,1360,768);
        stage.setScene(game);
        ArrayList<Card> test= p1.getHand();
        targeting=false;
        int i;
        for(i = 0;i<test.size();i++){
            Card c= test.get(i);
            Button b= new Button(c.getName()+"\n"+c.getManaCost()+"\n"+c.getRarity());
            b.setPrefSize(60,60);
            hand.getChildren().add(b);
            b.setOnMouseClicked(e->{
                if(c instanceof Minion){
                    Minion m= (Minion) c;
                    try {
                        p1Field.add(m);
                        p1.playMinion(m);
                    } catch (NotYourTurnException | NotEnoughManaException | FullFieldException notYourTurnException) {
                        notYourTurnException.printStackTrace();
                    }
                }
                else if(c instanceof HeroTargetSpell) {
                    HeroTargetSpell s = (HeroTargetSpell) c;
                    try {
                        Hero target=getHeroTarget();
                        p1.castSpell(s,target);
                    } catch (NotYourTurnException | NotEnoughManaException notYourTurnException) {
                        notYourTurnException.printStackTrace();
                    }
                }
                else if(c instanceof MinionTargetSpell) {
                    MinionTargetSpell s = (MinionTargetSpell) c;
                    try {
                        Minion target= getMinionTarget();
                        p1.castSpell(s,target);
                    } catch (NotYourTurnException | NotEnoughManaException | InvalidTargetException notYourTurnException) {
                        notYourTurnException.printStackTrace();
                    }
                }
                else if(c instanceof LeechingSpell) {
                    LeechingSpell s = (LeechingSpell) c;
                    try {
                        Minion target= getMinionTarget();
                        p1.castSpell(s,target);
                    } catch (NotYourTurnException | NotEnoughManaException notYourTurnException) {
                        notYourTurnException.printStackTrace();
                    }
                }
                else if(c instanceof FieldSpell) {
                    FieldSpell s = (FieldSpell) c;
                    try {
                        p1.castSpell(s);
                    } catch (NotYourTurnException | NotEnoughManaException notYourTurnException) {
                        notYourTurnException.printStackTrace();
                    }
                }

                else if(c instanceof AOESpell) {
                    AOESpell s = (AOESpell) c;
                    try {
                        p1.castSpell(s,p2.getField());
                    } catch (NotYourTurnException | NotEnoughManaException notYourTurnException) {
                        notYourTurnException.printStackTrace();
                    }
                }
            });

        }




        BackgroundImage myBI= new BackgroundImage(new Image("images/board.jpg"),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(stage.getWidth(),stage.getHeight(), false, false, true, false));
        gamescreen.setBackground(new Background(myBI));
        music("sounds/Game.wav");
        gamescreen.setMinSize(400, 200);
        gamescreen.setPadding(new Insets(10, 10, 10, 10));
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

    public Hero getHeroTarget(){
        targeting=true;
        p1Icon.setOnMouseClicked(e-> heroTarget=p1);
        p2Icon.setOnMouseClicked(e-> heroTarget=p2);
        return heroTarget;

    }

    public Minion getMinionTarget(){
        int i;
        return null;
    }


}
