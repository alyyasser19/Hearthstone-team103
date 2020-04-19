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
    FlowPane p1Field=new FlowPane();
    FlowPane p2Field= new FlowPane();

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
    boolean handler;


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
        p1Icon= new ImageView(new Image("images\\jaina\\Jaina_Proudmoore_30.png",200,200,true,true));
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
        playerDraw(p2hand,p2Field,p2,p1);
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
        p1Field.setMinSize(800,192);
        BorderPane showCur= new BorderPane();
        showCur.setMinSize(1360,192);
        showCur.setTop(p1Field);
        playerDraw(p1hand,p1Field,p1,p2);
        showCur.setBottom(p1hand);
        showCur.setCenter(p1Icon);
        gamescreen.setBottom(showCur);
        StackPane right=new StackPane();
        StackPane left=new StackPane();
        right.getChildren().add(end);
        gamescreen.setRight(right);
        gamescreen.setLeft(left);
        Scene gamescene= new Scene(gamescreen,1360,768);
        stage.setScene(gamescene);
        ArrayList<Card> curhand= p1.getHand();
        minionTargeted=false;
        end.setOnMouseClicked(e-> {
          if(p1==game.getCurrentHero()){
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
          ///validateHealth
              endTurnDraw(p2,p1,p2hand,p2Field);
               }
          else{
              try {
                  p2.endTurn();
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
              ///validateHealth
              endTurnDraw(p1,p2,p1hand,p1Field);

          }
        });
       // int i;
        //for(i = 0;i<curhand.size();i++){
          //  Card c= curhand.get(i);
            //Button b= new Button(c.getName()+"\n"+c.getManaCost()+"\n"+c.getRarity());
           // b.setPrefSize(100,100);
           // showHand.getChildren().add(b);
          //  b.setOnMouseClicked(e->{
//                if(c instanceof Minion){
//                    Minion m= (Minion) c;
//                    showCurField.getChildren().add(b);
//                    try {
//                        p1.playMinion(m);
//                    } catch (NotEnoughManaException e1){
//                        Stage s=new Stage();
//                        s.show();
//                        StackPane sp= new StackPane();
//                        sp.getChildren().add(new Label("Not Enough Mana!!"));
//                        Scene sc=new Scene(sp,200,200);
//                        s.setScene(sc);
//                        showCurField.getChildren().remove(b);
//                        showHand.getChildren().add(b);
//                    }
//                    catch (FullFieldException e1){
//                        Stage s=new Stage();
//                        s.show();
//                        StackPane sp= new StackPane();
//                        sp.getChildren().add(new Label("The Field is Full!!"));
//                        Scene sc=new Scene(sp,200,200);
//                        s.setScene(sc);
//                        Stage s1=new Stage();
//                        s1.show();
//                        showCurField.getChildren().remove(b);
//                        showHand.getChildren().add(b);
//                    }
//                    catch (NotYourTurnException e1){
//                        Stage s=new Stage();
//                        s.show();
//                        StackPane sp= new StackPane();
//                        sp.getChildren().add(new Label("Not Your Turn!!"));
//                        Scene sc=new Scene(sp,200,200);
//                        s.setScene(sc);
//                        showCurField.getChildren().remove(b);
//                        showHand.getChildren().add(b);
//                    }
//                }
//                else if(c instanceof HeroTargetSpell) {
//                    HeroTargetSpell s = (HeroTargetSpell) c;
//                    showHand.getChildren().remove(b);
//                    try {
//                        Hero target=getHeroTarget();
//                        p1.castSpell(s,target);
//                    }  catch (NotEnoughManaException e1) {
//                        Stage s1 = new Stage();
//                        s1.show();
//                        StackPane sp = new StackPane();
//                        sp.getChildren().add(new Label("Not Enough Mana!!"));
//                        Scene sc = new Scene(sp, 200, 200);
//                        s1.setScene(sc);
//                        showHand.getChildren().add(b);
//                    }
//                    catch (NotYourTurnException e1){
//                    Stage s1=new Stage();
//                    s1.show();
//                    StackPane sp= new StackPane();
//                    sp.getChildren().add(new Label("Not Your Turn!!"));
//                    Scene sc=new Scene(sp,200,200);
//                    s1.setScene(sc);
//                    showHand.getChildren().add(b);
//                }
//                }
//                else if(c instanceof MinionTargetSpell) {
//                    MinionTargetSpell s = (MinionTargetSpell) c;
//                    showHand.getChildren().remove(b);
//                    try {
//                        Minion target= getMinionTarget();
//                        p1.castSpell(s,target);
//                    } catch (NotEnoughManaException e1) {
//                        Stage s1 = new Stage();
//                        s1.show();
//                        StackPane sp = new StackPane();
//                        sp.getChildren().add(new Label("Not Enough Mana!!"));
//                        Scene sc = new Scene(sp, 200, 200);
//                        s1.setScene(sc);
//                        showHand.getChildren().add(b);
//                    }
//                    catch (NotYourTurnException e1){
//                        Stage s1=new Stage();
//                        s1.show();
//                        StackPane sp= new StackPane();
//                        sp.getChildren().add(new Label("Not Your Turn!!"));
//                        Scene sc=new Scene(sp,200,200);
//                        s1.setScene(sc);
//                        showHand.getChildren().add(b);
//                    } catch (InvalidTargetException invalidTargetException) {
//                        Stage s1=new Stage();
//                        s1.show();
//                        StackPane sp= new StackPane();
//                        sp.getChildren().add(new Label("Invalid Target!!"));
//                        Scene sc=new Scene(sp,200,200);
//                        s1.setScene(sc);
//                        showHand.getChildren().add(b);
//                    }
//                }
//                else if(c instanceof LeechingSpell) {
//                    LeechingSpell s = (LeechingSpell) c;
//                    showHand.getChildren().remove(b);
//                    try {
//                        Minion target= getMinionTarget();
//                        p1.castSpell(s,target);
//                    } catch (NotEnoughManaException e1) {
//                        Stage s1 = new Stage();
//                        s1.show();
//                        StackPane sp = new StackPane();
//                        sp.getChildren().add(new Label("Not Enough Mana!!"));
//                        Scene sc = new Scene(sp, 200, 200);
//                        s1.setScene(sc);
//                        showHand.getChildren().add(b);
//                    }
//                    catch (NotYourTurnException e1){
//                        Stage s1=new Stage();
//                        s1.show();
//                        StackPane sp= new StackPane();
//                        sp.getChildren().add(new Label("Not Your Turn!!"));
//                        Scene sc=new Scene(sp,200,200);
//                        s1.setScene(sc);
//                        showHand.getChildren().add(b);
//                    }
//                }
//                else if(c instanceof FieldSpell) {
//                    FieldSpell s = (FieldSpell) c;
//                    showHand.getChildren().remove(b);
//                    try {
//                        p1.castSpell(s);
//                    } catch (NotEnoughManaException e1) {
//                        Stage s1 = new Stage();
//                        s1.show();
//                        StackPane sp = new StackPane();
//                        sp.getChildren().add(new Label("Not Enough Mana!!"));
//                        Scene sc = new Scene(sp, 200, 200);
//                        s1.setScene(sc);
//                        showHand.getChildren().add(b);
//                    }
//                    catch (NotYourTurnException e1){
//                        Stage s1=new Stage();
//                        s1.show();
//                        StackPane sp= new StackPane();
//                        sp.getChildren().add(new Label("Not Your Turn!!"));
//                        Scene sc=new Scene(sp,200,200);
//                        s1.setScene(sc);
//                        showHand.getChildren().add(b);
//                    }
//                }
//
//                else if(c instanceof AOESpell) {
//                    AOESpell s = (AOESpell) c;
//                    showHand.getChildren().remove(b);
//                    try {
//                        p1.castSpell(s,p2.getField());
//                    } catch (NotEnoughManaException e1) {
//                        Stage s1 = new Stage();
//                        s1.show();
//                        StackPane sp = new StackPane();
//                        sp.getChildren().add(new Label("Not Enough Mana!!"));
//                        Scene sc = new Scene(sp, 200, 200);
//                        s1.setScene(sc);
//                        showHand.getChildren().add(b);
//                    }
//                    catch (NotYourTurnException e1){
//                        Stage s1=new Stage();
//                        s1.show();
//                        StackPane sp= new StackPane();
//                        sp.getChildren().add(new Label("Not Your Turn!!"));
//                        Scene sc=new Scene(sp,200,200);
//                        s1.setScene(sc);
//                        showHand.getChildren().add(b);
//                    }
//                }
//            });
//
//        }

//        for(i=0;i<p1.getField().size();i++){
//            Card c= p1.getField().get(i);
//            Button b= new Button(c.getName()+"\n"+c.getManaCost()+"\n"+c.getRarity());
//            b.setPrefSize(60,60);
//            Minion m=(Minion) c;
//            b.setOnMouseClicked(e->{if(heroTargeted){
//                try {
//                    p1.attackWithMinion(m, getHeroTarget());
//                } catch (CannotAttackException cannotAttackException) {
//                    Stage s1 = new Stage();
//                    s1.show();
//                    StackPane sp = new StackPane();
//                    sp.getChildren().add(new Label("Cannot Attack!!"));
//                    Scene sc = new Scene(sp, 200, 200);
//                    s1.setScene(sc);
//                } catch (NotYourTurnException notYourTurnException) {
//                    Stage s1 = new Stage();
//                    s1.show();
//                    StackPane sp = new StackPane();
//                    sp.getChildren().add(new Label("Not Your Turn!!"));
//                    Scene sc = new Scene(sp, 200, 200);
//                    s1.setScene(sc);
//                } catch (TauntBypassException tauntBypassException) {
//                    Stage s1 = new Stage();
//                    s1.show();
//                    StackPane sp = new StackPane();
//                    sp.getChildren().add(new Label("A Minion With Taunt is in the Way!!"));
//                    Scene sc = new Scene(sp, 200, 200);
//                    s1.setScene(sc);
//                } catch (NotSummonedException notSummonedException) {
//                    Stage s1 = new Stage();
//                    s1.show();
//                    StackPane sp = new StackPane();
//                    sp.getChildren().add(new Label("Minion Not Summoned!!"));
//                    Scene sc = new Scene(sp, 200, 200);
//                    s1.setScene(sc);
//                } catch (InvalidTargetException invalidTargetException) {
//                    Stage s1 = new Stage();
//                    s1.show();
//                    StackPane sp = new StackPane();
//                    sp.getChildren().add(new Label("Invalid Target!!"));
//                    Scene sc = new Scene(sp, 200, 200);
//                    s1.setScene(sc);
//                }
//            }
//            else{
//                try {
//                    p1.attackWithMinion(m, getMinionTarget());
//                } catch (CannotAttackException cannotAttackException) {
//                    Stage s1 = new Stage();
//                    s1.show();
//                    StackPane sp = new StackPane();
//                    sp.getChildren().add(new Label("Cannot Attack!!"));
//                    Scene sc = new Scene(sp, 200, 200);
//                    s1.setScene(sc);
//                } catch (NotYourTurnException notYourTurnException) {
//                    Stage s1 = new Stage();
//                    s1.show();
//                    StackPane sp = new StackPane();
//                    sp.getChildren().add(new Label("Not Your Turn!!"));
//                    Scene sc = new Scene(sp, 200, 200);
//                    s1.setScene(sc);
//                } catch (TauntBypassException tauntBypassException) {
//                    Stage s1 = new Stage();
//                    s1.show();
//                    StackPane sp = new StackPane();
//                    sp.getChildren().add(new Label("A Minion With Taunt is in the Way!!"));
//                    Scene sc = new Scene(sp, 200, 200);
//                    s1.setScene(sc);
//                } catch (NotSummonedException notSummonedException) {
//                    Stage s1 = new Stage();
//                    s1.show();
//                    StackPane sp = new StackPane();
//                    sp.getChildren().add(new Label("Minion Not Summoned!!"));
//                    Scene sc = new Scene(sp, 200, 200);
//                    s1.setScene(sc);
//                } catch (InvalidTargetException invalidTargetException) {
//                    Stage s1 = new Stage();
//                    s1.show();
//                    StackPane sp = new StackPane();
//                    sp.getChildren().add(new Label("Invalid Target!!"));
//                    Scene sc = new Scene(sp, 200, 200);
//                    s1.setScene(sc);
//                }
//            }
//            });
//
//        }
//
//
//
//
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
    public void playerDraw(HBox phand,FlowPane pfield,Hero p,Hero pOther){
        for(Card cur: p.getHand() ) {
            ImageView a= new ImageView(new Image(getImage(cur),130,200,false,true));
            if (cur instanceof Minion) {
                ImageView finalA5 = a;
                a.setOnMouseClicked(e -> {
                    try {
                        p.playMinion((Minion) cur);
                    } catch (NotYourTurnException notYourTurnException) {
                        notYourTurnException.printStackTrace();
                        return;
                    } catch (NotEnoughManaException notEnoughManaException) {
                        notEnoughManaException.printStackTrace();
                        return;
                    } catch (FullFieldException fullFieldException) {
                        fullFieldException.printStackTrace();
                        return;
                    }
                    pfield.getChildren().add(finalA5);
                });

            }
            if (cur instanceof Spell) {
                if (cur instanceof AOESpell) {
                    ImageView finalA = a;
                    a.setOnMouseClicked(e -> {
                        try {
                            p.castSpell((AOESpell) cur, pOther.getField());
                        } catch (NotYourTurnException notYourTurnException) {
                            notYourTurnException.printStackTrace();
                            return;
                        } catch (NotEnoughManaException notEnoughManaException) {
                            notEnoughManaException.printStackTrace();
                            return;
                        }
                        phand.getChildren().remove(finalA);
                    });
                }
                if (cur instanceof FieldSpell) {
                    ImageView finalA1 = a;
                    a.setOnMouseClicked(e -> {
                        try {
                            p.castSpell((FieldSpell) cur);
                        } catch (NotYourTurnException notYourTurnException) {
                            notYourTurnException.printStackTrace();
                            return;
                        } catch (NotEnoughManaException notEnoughManaException) {
                            notEnoughManaException.printStackTrace();
                            return;
                        }
                        phand.getChildren().remove(finalA1);
                    });
                }
                if (cur instanceof HeroTargetSpell) {
                    ImageView finalA2 = a;
                    a.setOnMouseClicked(e -> {
                        try {
                            p.castSpell(((HeroTargetSpell) cur), pOther);
                        } catch (NotYourTurnException notYourTurnException) {
                            notYourTurnException.printStackTrace();
                            return;
                        } catch (NotEnoughManaException notEnoughManaException) {
                            notEnoughManaException.printStackTrace();
                            return;
                        }
                        phand.getChildren().remove(finalA2);
                    });
                }
                if (cur instanceof MinionTargetSpell) {
                    getTarget();
                    ImageView finalA3 = a;
                    a.setOnMouseClicked(e -> {
                        try {
                            p.castSpell((MinionTargetSpell) cur, minionTarget);
                        } catch (NotYourTurnException notYourTurnException) {
                            notYourTurnException.printStackTrace();
                            return;
                        } catch (NotEnoughManaException notEnoughManaException) {
                            notEnoughManaException.printStackTrace();
                            return;
                        } catch (InvalidTargetException invalidTargetException) {
                            invalidTargetException.printStackTrace();
                            return;
                        }
                        phand.getChildren().remove(finalA3);
                    });
                }
                if (cur instanceof LeechingSpell) {
                    getTarget();
                    ImageView finalA4 = a;
                    a.setOnMouseClicked(e -> {
                        try {
                            p.castSpell((LeechingSpell) cur, minionTarget);
                        } catch (NotYourTurnException notYourTurnException) {
                            notYourTurnException.printStackTrace();
                            return;
                        } catch (NotEnoughManaException notEnoughManaException) {
                            notEnoughManaException.printStackTrace();
                            return;
                        }
                        phand.getChildren().remove(finalA4);
                    });
                }
            }
            phand.getChildren().add(a);
        }
    }
  public void Attack(){

  }
  public String getImage(Card cur){
        if(cur.getName().equals("Goldshire Footman"))
            return "images\\cards\\GoldshireFootman.png";

      if(cur.getName().equals("Bloodfen Raptor"))
          return "images\\cards\\BloodfenRaptor.png";

      if(cur.getName().equals("Stonetusk Boar"))
          return "images\\cards\\StonetuskBoar.png";

      if(cur.getName().equals("Frostwolf Grunt"))
          return "images\\cards\\FrostwolfGrunt.png";

      if(cur.getName().equals("Wolfrider"))
          return "images\\cards\\Wolfrider.png";

      if(cur.getName().equals("Chilwind Yeti"))
          return "images\\cards\\ChillwindYeti.png";

      if(cur.getName().equals("Boulderfist Ogre"))
          return "images\\cards\\BoulderfistOgre.png";

      if(cur.getName().equals("Core Hound"))
          return "images\\cards\\CoreHound.png";

      if(cur.getName().equals("Argent Commander"))
          return "images\\cards\\ArgentCommander.png";

      if(cur.getName().equals("Sunwalker"))
          return "images\\cards\\Sunwalker.png";

      if(cur.getName().equals("Chromaggus"))
          return "images\\cards\\Chromaggus.png";

      if(cur.getName().equals("The LichKing"))
          return "images\\cards\\TheLichKing.png";

      if(cur.getName().equals("Icehowl"))
          return "images\\cards\\Icehowl.png";

      if(cur.getName().equals("Colossus of the Moon"))
          return "images\\cards\\ColossusoftheMoon.png";

      if(cur.getName().equals("Goldshire Footman"))
          return "images\\cards\\GoldshireFootman.png";
      if(cur.getName().equals("Goldshire Footman"))
          return "images\\cards\\GoldshireFootman.png";
      if(cur.getName().equals("Goldshire Footman"))
          return "images\\cards\\GoldshireFootman.png";
      if(cur.getName().equals("Goldshire Footman"))
          return "images\\cards\\GoldshireFootman.png";
      if(cur.getName().equals("Goldshire Footman"))
          return "images\\cards\\GoldshireFootman.png";
      if(cur.getName().equals("Goldshire Footman"))
          return "images\\cards\\GoldshireFootman.png";
      if(cur.getName().equals("Goldshire Footman"))
          return "images\\cards\\GoldshireFootman.png";
      if(cur.getName().equals("Goldshire Footman"))
          return "images\\cards\\GoldshireFootman.png";
      if(cur.getName().equals("Goldshire Footman"))
          return "images\\cards\\GoldshireFootman.png";
      if(cur.getName().equals("Goldshire Footman"))
          return "images\\cards\\GoldshireFootman.png";
      if(cur.getName().equals("Goldshire Footman"))
          return "images\\cards\\GoldshireFootman.png";
      if(cur.getName().equals("Goldshire Footman"))
          return "images\\cards\\GoldshireFootman.png";
      if(cur.getName().equals("Goldshire Footman"))
          return "images\\cards\\GoldshireFootman.png";
      if(cur.getName().equals("Goldshire Footman"))
          return "images\\cards\\GoldshireFootman.png";
      if(cur.getName().equals("Goldshire Footman"))
          return "images\\cards\\GoldshireFootman.png";
      if(cur.getName().equals("Goldshire Footman"))
          return "images\\cards\\GoldshireFootman.png";
      if(cur.getName().equals("Goldshire Footman"))
          return "images\\cards\\GoldshireFootman.png";
      if(cur.getName().equals("Goldshire Footman"))
          return "images\\cards\\GoldshireFootman.png";
      if(cur.getName().equals("Goldshire Footman"))
          return "images\\cards\\GoldshireFootman.png";

      return "images\\whitepage.png";
  }
  public void endTurnDraw(Hero p,Hero pOther,HBox phand,FlowPane pfield){
        Card cur= p.getHand().get(p.getHand().size()-1);
      ImageView a= new ImageView(new Image(getImage(cur),130,200,false,true));
      if (cur instanceof Minion) {
          ImageView finalA5 = a;
          a.setOnMouseClicked(e -> {
              try {
                  p.playMinion((Minion) cur);
              } catch (NotYourTurnException notYourTurnException) {
                  notYourTurnException.printStackTrace();
                  return;
              } catch (NotEnoughManaException notEnoughManaException) {
                  notEnoughManaException.printStackTrace();
                  return;
              } catch (FullFieldException fullFieldException) {
                  fullFieldException.printStackTrace();
                  return;
              }
              pfield.getChildren().add(finalA5);
          });

      }
      if (cur instanceof Spell) {
          if (cur instanceof AOESpell) {
              ImageView finalA = a;
              a.setOnMouseClicked(e -> {
                  try {
                      p.castSpell((AOESpell) cur, pOther.getField());
                  } catch (NotYourTurnException notYourTurnException) {
                      notYourTurnException.printStackTrace();
                      return;
                  } catch (NotEnoughManaException notEnoughManaException) {
                      notEnoughManaException.printStackTrace();
                      return;
                  }
                  phand.getChildren().remove(finalA);
              });
          }
          if (cur instanceof FieldSpell) {
              ImageView finalA1 = a;
              a.setOnMouseClicked(e -> {
                  try {
                      p.castSpell((FieldSpell) cur);
                  } catch (NotYourTurnException notYourTurnException) {
                      notYourTurnException.printStackTrace();
                      return;
                  } catch (NotEnoughManaException notEnoughManaException) {
                      notEnoughManaException.printStackTrace();
                      return;
                  }
                  phand.getChildren().remove(finalA1);
              });
          }
          if (cur instanceof HeroTargetSpell) {
              ImageView finalA2 = a;
              a.setOnMouseClicked(e -> {
                  try {
                      p.castSpell(((HeroTargetSpell) cur), pOther);
                  } catch (NotYourTurnException notYourTurnException) {
                      notYourTurnException.printStackTrace();
                      return;
                  } catch (NotEnoughManaException notEnoughManaException) {
                      notEnoughManaException.printStackTrace();
                      return;
                  }
                  phand.getChildren().remove(finalA2);
              });
          }
          if (cur instanceof MinionTargetSpell) {
              getTarget();
              ImageView finalA3 = a;
              a.setOnMouseClicked(e -> {
                  try {
                      p.castSpell((MinionTargetSpell) cur, minionTarget);
                  } catch (NotYourTurnException notYourTurnException) {
                      notYourTurnException.printStackTrace();
                      return;
                  } catch (NotEnoughManaException notEnoughManaException) {
                      notEnoughManaException.printStackTrace();
                      return;
                  } catch (InvalidTargetException invalidTargetException) {
                      invalidTargetException.printStackTrace();
                      return;
                  }
                  phand.getChildren().remove(finalA3);
              });
          }
          if (cur instanceof LeechingSpell) {
              getTarget();
              ImageView finalA4 = a;
              a.setOnMouseClicked(e -> {
                  try {
                      p.castSpell((LeechingSpell) cur, minionTarget);
                  } catch (NotYourTurnException notYourTurnException) {
                      notYourTurnException.printStackTrace();
                      return;
                  } catch (NotEnoughManaException notEnoughManaException) {
                      notEnoughManaException.printStackTrace();
                      return;
                  }
                  phand.getChildren().remove(finalA4);
              });
          }
      }
      phand.getChildren().add(a);
  }
//  public void endTurn(){
//
//
//  }


}
