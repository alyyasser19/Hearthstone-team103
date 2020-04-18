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
    ImageView curIcon;
    ImageView oppIcon;

    //Game Classes
    Hero p1;
    Hero p2;
    Hero Cur;
    Hero Opp;
    Game game;
    Button end;
    Minion minionTarget;
    Hero heroTarget;
    Boolean Targeted;
    ArrayList<Button> oppField;
    ArrayList<Button> curField;

    public void start(Stage stage) throws IOException, CloneNotSupportedException, FullHandException {
        p1=new Hunter();
        p2=new Hunter();
        game=new Game(p1,p2);
        end= new Button("END TURN");
        stage=new Stage();
        stage.show();
        curIcon= new ImageView(new Image("images\\Jaina_Proudmoore.png",200,200,true,true));
        oppIcon=new ImageView(new Image("images\\Uther_Lightbringer.png",200,200,true,true));
        Hero Cur= game.getCurrentHero();
        Hero Opp= game.getOpponent();
        BorderPane gamescreen= new BorderPane();
        BorderPane showOpp= new BorderPane();
        showOpp.getChildren().add(oppIcon);
        showOpp.setMinSize(1360,192);
        gamescreen.setTop(showOpp);
        FlowPane showOppField= new FlowPane();
        showOppField.setHgap(100);
        showOppField.setMinHeight(100);
        showOppField.setMaxSize(800,192);
        showOpp.setBottom(showOppField);
        FlowPane showCurField= new FlowPane();
        showCurField.setMinSize(800,192);
        BorderPane showCur= new BorderPane();
        showCur.setMinSize(1360,192);
        showCur.setTop(showCurField);
        HBox showHand=new HBox();
        showCur.setBottom(showHand);
        showCur.setCenter(curIcon);
        gamescreen.setBottom(showCur);
        StackPane right=new StackPane();
        StackPane left=new StackPane();
        right.getChildren().add(end);
        gamescreen.setRight(right);
        gamescreen.setLeft(left);
        Scene game= new Scene(gamescreen,1360,768);
        stage.setScene(game);
        ArrayList<Card> curhand= Cur.getHand();
        Targeted=false;
        end.setOnMouseClicked(e-> {
            try {
                Cur.endTurn();
            } catch (FullHandException fullHandException) {
                Card x=fullHandException.getBurned();
                Stage s1 = new Stage();
                s1.show();
                StackPane sp = new StackPane();
                sp.getChildren().add(new Label("You Have a Full Hand!!\n"+x.getName()+"\n"+x.getManaCost()+"\n"+x.getRarity()));
                Scene sc = new Scene(sp, 200, 200);
                s1.setScene(sc);

            } catch (CloneNotSupportedException cloneNotSupportedException) {
                cloneNotSupportedException.printStackTrace();
            }
        });
        int i;
        for(i = 0;i<curhand.size();i++){
            Card c= curhand.get(i);
            Button b= new Button(c.getName()+"\n"+c.getManaCost()+"\n"+c.getRarity());
            b.setPrefSize(100,100);
            showHand.getChildren().add(b);
            b.setOnMouseClicked(e->{
                if(c instanceof Minion){
                    Minion m= (Minion) c;
                    showCurField.getChildren().add(b);
                    try {
                        Cur.playMinion(m);
                    } catch (NotEnoughManaException e1){
                        Stage s=new Stage();
                        s.show();
                        StackPane sp= new StackPane();
                        sp.getChildren().add(new Label("Not Enough Mana!!"));
                        Scene sc=new Scene(sp,200,200);
                        s.setScene(sc);
                        showCurField.getChildren().remove(b);
                        showHand.getChildren().add(b);
                    }
                    catch (FullFieldException e1){
                        Stage s=new Stage();
                        s.show();
                        StackPane sp= new StackPane();
                        sp.getChildren().add(new Label("The Field is Full!!"));
                        Scene sc=new Scene(sp,200,200);
                        s.setScene(sc);
                        Stage s1=new Stage();
                        s1.show();
                        showCurField.getChildren().remove(b);
                        showHand.getChildren().add(b);
                    }
                    catch (NotYourTurnException e1){
                        Stage s=new Stage();
                        s.show();
                        StackPane sp= new StackPane();
                        sp.getChildren().add(new Label("Not Your Turn!!"));
                        Scene sc=new Scene(sp,200,200);
                        s.setScene(sc);
                        showCurField.getChildren().remove(b);
                        showHand.getChildren().add(b);
                    }
                }
                else if(c instanceof HeroTargetSpell) {
                    HeroTargetSpell s = (HeroTargetSpell) c;
                    showHand.getChildren().remove(b);
                    try {
                        Hero target=getHeroTarget();
                        Cur.castSpell(s,target);
                    }  catch (NotEnoughManaException e1) {
                        Stage s1 = new Stage();
                        s1.show();
                        StackPane sp = new StackPane();
                        sp.getChildren().add(new Label("Not Enough Mana!!"));
                        Scene sc = new Scene(sp, 200, 200);
                        s1.setScene(sc);
                        showHand.getChildren().add(b);
                    }
                    catch (NotYourTurnException e1){
                    Stage s1=new Stage();
                    s1.show();
                    StackPane sp= new StackPane();
                    sp.getChildren().add(new Label("Not Your Turn!!"));
                    Scene sc=new Scene(sp,200,200);
                    s1.setScene(sc);
                    showHand.getChildren().add(b);
                }
                }
                else if(c instanceof MinionTargetSpell) {
                    MinionTargetSpell s = (MinionTargetSpell) c;
                    showHand.getChildren().remove(b);
                    try {
                        Minion target= getMinionTarget();
                        Cur.castSpell(s,target);
                    } catch (NotEnoughManaException e1) {
                        Stage s1 = new Stage();
                        s1.show();
                        StackPane sp = new StackPane();
                        sp.getChildren().add(new Label("Not Enough Mana!!"));
                        Scene sc = new Scene(sp, 200, 200);
                        s1.setScene(sc);
                        showHand.getChildren().add(b);
                    }
                    catch (NotYourTurnException e1){
                        Stage s1=new Stage();
                        s1.show();
                        StackPane sp= new StackPane();
                        sp.getChildren().add(new Label("Not Your Turn!!"));
                        Scene sc=new Scene(sp,200,200);
                        s1.setScene(sc);
                        showHand.getChildren().add(b);
                    } catch (InvalidTargetException invalidTargetException) {
                        Stage s1=new Stage();
                        s1.show();
                        StackPane sp= new StackPane();
                        sp.getChildren().add(new Label("Invalid Target!!"));
                        Scene sc=new Scene(sp,200,200);
                        s1.setScene(sc);
                        showHand.getChildren().add(b);
                    }
                }
                else if(c instanceof LeechingSpell) {
                    LeechingSpell s = (LeechingSpell) c;
                    showHand.getChildren().remove(b);
                    try {
                        Minion target= getMinionTarget();
                        Cur.castSpell(s,target);
                    } catch (NotEnoughManaException e1) {
                        Stage s1 = new Stage();
                        s1.show();
                        StackPane sp = new StackPane();
                        sp.getChildren().add(new Label("Not Enough Mana!!"));
                        Scene sc = new Scene(sp, 200, 200);
                        s1.setScene(sc);
                        showHand.getChildren().add(b);
                    }
                    catch (NotYourTurnException e1){
                        Stage s1=new Stage();
                        s1.show();
                        StackPane sp= new StackPane();
                        sp.getChildren().add(new Label("Not Your Turn!!"));
                        Scene sc=new Scene(sp,200,200);
                        s1.setScene(sc);
                        showHand.getChildren().add(b);
                    }
                }
                else if(c instanceof FieldSpell) {
                    FieldSpell s = (FieldSpell) c;
                    showHand.getChildren().remove(b);
                    try {
                        Cur.castSpell(s);
                    } catch (NotEnoughManaException e1) {
                        Stage s1 = new Stage();
                        s1.show();
                        StackPane sp = new StackPane();
                        sp.getChildren().add(new Label("Not Enough Mana!!"));
                        Scene sc = new Scene(sp, 200, 200);
                        s1.setScene(sc);
                        showHand.getChildren().add(b);
                    }
                    catch (NotYourTurnException e1){
                        Stage s1=new Stage();
                        s1.show();
                        StackPane sp= new StackPane();
                        sp.getChildren().add(new Label("Not Your Turn!!"));
                        Scene sc=new Scene(sp,200,200);
                        s1.setScene(sc);
                        showHand.getChildren().add(b);
                    }
                }

                else if(c instanceof AOESpell) {
                    AOESpell s = (AOESpell) c;
                    showHand.getChildren().remove(b);
                    try {
                        Cur.castSpell(s,Opp.getField());
                    } catch (NotEnoughManaException e1) {
                        Stage s1 = new Stage();
                        s1.show();
                        StackPane sp = new StackPane();
                        sp.getChildren().add(new Label("Not Enough Mana!!"));
                        Scene sc = new Scene(sp, 200, 200);
                        s1.setScene(sc);
                        showHand.getChildren().add(b);
                    }
                    catch (NotYourTurnException e1){
                        Stage s1=new Stage();
                        s1.show();
                        StackPane sp= new StackPane();
                        sp.getChildren().add(new Label("Not Your Turn!!"));
                        Scene sc=new Scene(sp,200,200);
                        s1.setScene(sc);
                        showHand.getChildren().add(b);
                    }
                }
            });

        }

        for(i=0;i<Cur.getField().size();i++){
            Card c= Cur.getField().get(i);
            Button b= new Button(c.getName()+"\n"+c.getManaCost()+"\n"+c.getRarity());
            b.setPrefSize(60,60);
            Minion m=(Minion) c;
            b.setOnMouseClicked(e->{if(Targeted){
                try {
                    Cur.attackWithMinion(m, getHeroTarget());
                } catch (CannotAttackException cannotAttackException) {
                    Stage s1 = new Stage();
                    s1.show();
                    StackPane sp = new StackPane();
                    sp.getChildren().add(new Label("Cannot Attack!!"));
                    Scene sc = new Scene(sp, 200, 200);
                    s1.setScene(sc);
                } catch (NotYourTurnException notYourTurnException) {
                    Stage s1 = new Stage();
                    s1.show();
                    StackPane sp = new StackPane();
                    sp.getChildren().add(new Label("Not Your Turn!!"));
                    Scene sc = new Scene(sp, 200, 200);
                    s1.setScene(sc);
                } catch (TauntBypassException tauntBypassException) {
                    Stage s1 = new Stage();
                    s1.show();
                    StackPane sp = new StackPane();
                    sp.getChildren().add(new Label("A Minion With Taunt is in the Way!!"));
                    Scene sc = new Scene(sp, 200, 200);
                    s1.setScene(sc);
                } catch (NotSummonedException notSummonedException) {
                    Stage s1 = new Stage();
                    s1.show();
                    StackPane sp = new StackPane();
                    sp.getChildren().add(new Label("Minion Not Summoned!!"));
                    Scene sc = new Scene(sp, 200, 200);
                    s1.setScene(sc);
                } catch (InvalidTargetException invalidTargetException) {
                    Stage s1 = new Stage();
                    s1.show();
                    StackPane sp = new StackPane();
                    sp.getChildren().add(new Label("Invalid Target!!"));
                    Scene sc = new Scene(sp, 200, 200);
                    s1.setScene(sc);
                }
            }
            else{
                try {
                    Cur.attackWithMinion(m, getMinionTarget());
                } catch (CannotAttackException cannotAttackException) {
                    Stage s1 = new Stage();
                    s1.show();
                    StackPane sp = new StackPane();
                    sp.getChildren().add(new Label("Cannot Attack!!"));
                    Scene sc = new Scene(sp, 200, 200);
                    s1.setScene(sc);
                } catch (NotYourTurnException notYourTurnException) {
                    Stage s1 = new Stage();
                    s1.show();
                    StackPane sp = new StackPane();
                    sp.getChildren().add(new Label("Not Your Turn!!"));
                    Scene sc = new Scene(sp, 200, 200);
                    s1.setScene(sc);
                } catch (TauntBypassException tauntBypassException) {
                    Stage s1 = new Stage();
                    s1.show();
                    StackPane sp = new StackPane();
                    sp.getChildren().add(new Label("A Minion With Taunt is in the Way!!"));
                    Scene sc = new Scene(sp, 200, 200);
                    s1.setScene(sc);
                } catch (NotSummonedException notSummonedException) {
                    Stage s1 = new Stage();
                    s1.show();
                    StackPane sp = new StackPane();
                    sp.getChildren().add(new Label("Minion Not Summoned!!"));
                    Scene sc = new Scene(sp, 200, 200);
                    s1.setScene(sc);
                } catch (InvalidTargetException invalidTargetException) {
                    Stage s1 = new Stage();
                    s1.show();
                    StackPane sp = new StackPane();
                    sp.getChildren().add(new Label("Invalid Target!!"));
                    Scene sc = new Scene(sp, 200, 200);
                    s1.setScene(sc);
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
        oppIcon.setOnMouseClicked(e-> heroTarget=game.getOpponent());
        curIcon.setOnMouseClicked(e-> heroTarget= game.getCurrentHero());
        Targeted=true;
        return heroTarget;

    }

    public Minion getMinionTarget(){
        int i;
        for(i=0;i<Cur.getField().size();i++){
            Minion m= Cur.getField().get(i);
            Button b= new Button(m.getName()+"\n"+m.getManaCost()+"\n"+m.getRarity());
            curField.add(b);
            b.setOnMouseClicked(e-> minionTarget=m);
        }
        for(i=0;i<Opp.getField().size();i++){
            Minion m= Opp.getField().get(i);
            Button b= new Button(m.getName()+"\n"+m.getManaCost()+"\n"+m.getRarity());
            oppField.add(b);
            b.setOnMouseClicked(e-> minionTarget=m);
        }
        return minionTarget;
    }


}
