package view;

import engine.Game;
import exceptions.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.cards.Card;
import model.cards.minions.Minion;
import model.cards.spells.*;
import model.heroes.Hero;
import model.heroes.Hunter;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class inGame extends Application  {


    //Resources
    Clip clip;
    ImageView p1Icon;
    ImageView p2Icon;
    ImageView p1Power;
    ImageView p2Power;

    //Buttons
    HBox p1hand= new HBox();
    HBox p2hand= new HBox();
    HBox p1Field=new HBox();
    HBox p2Field= new HBox();

    //Game Classes
    Hero p1;
    Hero p2;
    Game game;
    Button end;
    Minion minionTarget;
    Hero heroTarget;
    Boolean Targeteing= false;
    boolean heroTargeted=false;
    boolean minionTargeted=false;


    public void start(Stage stage) throws IOException, CloneNotSupportedException, FullHandException {
        Button blank= new Button("test");
        blank.setPrefSize(100,100);
        //blank.setVisible(true);
        p1=new Hunter();
        p2=new Hunter();
        game=new Game(p1,p2);
        end= new Button("END TURN");
        stage=new Stage();
        stage.show();
        p1Icon= new ImageView(new Image("images\\Jaina_Proudmoore.png",200,200,true,true));
        p2Icon=new ImageView(new Image("images\\Urther\\Uther_Lightbringer_30.png",200,200,true,true));
        p1Power=new ImageView(new Image("images\\Reinforce_hs.png",200,200,true,true));
        p2Icon=new ImageView(new Image("images\\Fireball.png",200,200,true,true));
        BorderPane gamescreen= new BorderPane();

        //Opponent Layout
        BorderPane p2Area= new BorderPane();
        //the place where the hero is
        GridPane oppCenter= new GridPane();
        oppCenter.add(p2Icon ,10,10);
        p2Area.setRight(oppCenter);
        //the hand

        //Validate to be added and the get target method to be implemented
        for(Card cur: p2.getHand() ) {
            Button a= new Button(cur.getName()+"\n"+cur.getManaCost()+"\n"+cur.getRarity());
            a.setPrefSize(100,100);
            if (cur instanceof Minion) {
                Button finalA5 = a;
                a.setOnMouseClicked(e -> {
                    try {
                        p2.playMinion((Minion) cur);
                    } catch (NotYourTurnException notYourTurnException) {
                        notYourTurnException.printStackTrace();
                    } catch (NotEnoughManaException notEnoughManaException) {
                        notEnoughManaException.printStackTrace();
                    } catch (FullFieldException fullFieldException) {
                        fullFieldException.printStackTrace();
                    }
                    p2Field.getChildren().add(finalA5);
                });

            }
            if (cur instanceof Spell) {
                if (cur instanceof AOESpell) {
                    Button finalA = a;
                    a.setOnMouseClicked(e -> {
                        try {
                            p2.castSpell((AOESpell) cur, p1.getField());
                        } catch (NotYourTurnException notYourTurnException) {
                            notYourTurnException.printStackTrace();
                        } catch (NotEnoughManaException notEnoughManaException) {
                            notEnoughManaException.printStackTrace();
                        }
                        p2hand.getChildren().remove(finalA);
                    });
                }
                if (cur instanceof FieldSpell) {
                    a = new Button(cur.toString());
                    Button finalA1 = a;
                    a.setOnMouseClicked(e -> {
                        try {
                            p2.castSpell((FieldSpell) cur);
                        } catch (NotYourTurnException notYourTurnException) {
                            notYourTurnException.printStackTrace();
                        } catch (NotEnoughManaException notEnoughManaException) {
                            notEnoughManaException.printStackTrace();
                        }
                        p2hand.getChildren().remove(finalA1);
                    });
                }
                if (cur instanceof HeroTargetSpell) {
                    Button finalA2 = a;
                    a.setOnMouseClicked(e -> {
                        try {
                            p2.castSpell(((HeroTargetSpell) cur), p1);
                        } catch (NotYourTurnException notYourTurnException) {
                            notYourTurnException.printStackTrace();
                        } catch (NotEnoughManaException notEnoughManaException) {
                            notEnoughManaException.printStackTrace();
                        }
                        p2hand.getChildren().remove(finalA2);
                    });
                }
                if (cur instanceof MinionTargetSpell) {
                    getTarget();
                    Button finalA3 = a;
                    a.setOnMouseClicked(e -> {
                        try {
                            p2.castSpell((MinionTargetSpell) cur, minionTarget);
                        } catch (NotYourTurnException notYourTurnException) {
                            notYourTurnException.printStackTrace();
                        } catch (NotEnoughManaException notEnoughManaException) {
                            notEnoughManaException.printStackTrace();
                        } catch (InvalidTargetException invalidTargetException) {
                            invalidTargetException.printStackTrace();
                        }
                        p2hand.getChildren().remove(finalA3);
                    });
                }
                if (cur instanceof LeechingSpell) {
                    getTarget();
                    Button finalA4 = a;
                    a.setOnMouseClicked(e -> {
                        try {
                            p2.castSpell((LeechingSpell) cur, minionTarget);
                        } catch (NotYourTurnException notYourTurnException) {
                            notYourTurnException.printStackTrace();
                        } catch (NotEnoughManaException notEnoughManaException) {
                            notEnoughManaException.printStackTrace();
                        }
                        p2hand.getChildren().remove(finalA4);
                    });
                }
            }
            p2hand.getChildren().add(a);
        }
        p2Area.setLeft(p2hand);
        p2Area.setCenter(p2Icon);
        p2Field.getChildren().add(blank);
        p2Area.setBottom(p2Field);


        p2Area.setMinSize(1360,192);
        gamescreen.setTop(p2Area);






        FlowPane showOppField= new FlowPane();
        showOppField.setHgap(100);
        showOppField.setMinHeight(100);
        showOppField.setMaxSize(800,192);
        p2Area.setBottom(showOppField);
        FlowPane showCurField= new FlowPane();
        showCurField.setMinSize(800,192);
        BorderPane showCur= new BorderPane();
        showCur.setMinSize(1360,192);
        showCur.setTop(showCurField);
        HBox showHand=new HBox();
        showCur.setBottom(showHand);
        showCur.setCenter(p1Icon);
        gamescreen.setBottom(showCur);
        StackPane right=new StackPane();
        StackPane left=new StackPane();
        right.getChildren().add(end);
        gamescreen.setRight(right);
        gamescreen.setLeft(left);
        Scene game= new Scene(gamescreen,1360,768);
        stage.setScene(game);
        ArrayList<Card> curhand= p1.getHand();
        minionTargeted=false;
        end.setOnMouseClicked(e-> {
            try {
                p1.endTurn();
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
                        p1.playMinion(m);
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
                        p1.castSpell(s,target);
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
                        p1.castSpell(s,target);
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
                        p1.castSpell(s,target);
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
                        p1.castSpell(s);
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
                        p1.castSpell(s,p2.getField());
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

        for(i=0;i<p1.getField().size();i++){
            Card c= p1.getField().get(i);
            Button b= new Button(c.getName()+"\n"+c.getManaCost()+"\n"+c.getRarity());
            b.setPrefSize(60,60);
            Minion m=(Minion) c;
            b.setOnMouseClicked(e->{if(heroTargeted){
                try {
                    p1.attackWithMinion(m, getHeroTarget());
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
                    p1.attackWithMinion(m, getMinionTarget());
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

    public void getTarget(){

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
        p2Icon.setOnMouseClicked(e-> heroTarget=game.getOpponent());
        p1Icon.setOnMouseClicked(e-> heroTarget= game.getCurrentHero());
        heroTargeted=true;
        return heroTarget;

    }

    public Minion getMinionTarget(){
        int i;
        for(i=0;i<p1.getField().size();i++){
            Minion m= p1.getField().get(i);
            Button b= new Button(m.getName()+"\n"+m.getManaCost()+"\n"+m.getRarity());
            //p2Field.add(b);
            b.setOnMouseClicked(e-> minionTarget=m);
        }
        for(i=0;i<p2.getField().size();i++){
            Minion m= p2.getField().get(i);
            Button b= new Button(m.getName()+"\n"+m.getManaCost()+"\n"+m.getRarity());
            //p2Field.add(b);
            b.setOnMouseClicked(e-> minionTarget=m);
        }
        return minionTarget;
    }


}
