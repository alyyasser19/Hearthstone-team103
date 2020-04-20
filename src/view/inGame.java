package view;

import engine.Game;
import exceptions.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.cards.Card;
import model.cards.Rarity;
import model.cards.minions.Icehowl;
import model.cards.minions.Minion;
import model.cards.spells.*;
import model.heroes.*;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class inGame extends Application  {


    //Resources
    Clip clip;
    ImageView p1Icon=null;
    ImageView p2Icon=null;
    ImageView p1Power;
    ImageView p2Power;
    Button p1Mana;
    Button p2Mana;
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
        p1=new Mage();
        //verifyHeroP1();
        p2=new Warlock();
        //verifyHeroP2();
        game=new Game(p1,p2);
        end= new Button("END TURN");
        stage=new Stage();
        stage.show();
        stage.setMinHeight(1060);
        stage.setMinWidth(1920);
        p1Icon= new ImageView(new Image("images\\jaina\\Jaina_Proudmoore_30.png",250,300,true,true));
        p2Icon=new ImageView(new Image("images\\Urther\\Uther_Lightbringer_30.png",250,300,true,true));
        verifyHeroP1();
        verifyHeroP2();
        p1Power=new ImageView(new Image("images\\Reinforce_hs.png",100,175,true,true));
        p2Power =new ImageView(new Image("images\\Steady_Shot.png",100,175,true,true));
        BorderPane gamescreen= new BorderPane();

        //Opponent Layout
        BorderPane p2Area= new BorderPane();
        //the hand

        //Validate to be added and the get target method to be implemented
        playerDraw(p2hand,p2Field,p2,p1,p1Field);
        minionButton test=new minionButton(new Minion("Choose you target, you can also attack me but yeah that's a waste of a perfectly fine attack i am just an innocent frame",0, Rarity.BASIC,0,0,false,false,false));
        test.setMinSize(300,192);
        test.setVisible(false);
        p2Field.getChildren().add(test);
        p2hand.setMaxSize(580,192);
        p2Area.setLeft(p2hand);
        HBox bottom=new HBox();
        p2Mana=new Button("Mana:\nCards:");
        p2Mana.setMinWidth(100);
        bottom.getChildren().add(p2Icon);
        bottom.getChildren().add(p2Power);
        p2Area.setBottom(bottom);
        p2Area.setRight(null);
        gamescreen.setTop(p2Area);

        //Current Layout
        BorderPane p1Area= new BorderPane();
        //the place where the hero is

        //Validate to be added and the get target method to be implemented
        playerDraw(p1hand,p1Field,p1,p2,p2Field);
        minionButton test2=new minionButton(new Icehowl());

        test2.setMinSize(300,192);
        test2.setVisible(false);
        p1Field.getChildren().add(test2);
        p1hand.setMaxSize(580,192);
        p1Area.setLeft(p1hand);
        HBox top=new HBox();
        p1Mana=new Button("Mana:\nCards:");
        p1Mana.setMinWidth(100);
        top.getChildren().add(p1Icon);
        top.getChildren().add(p1Power);
        p1Area.setTop(top);
        p1Area.setRight(null);
        gamescreen.setBottom(p1Area);

        //Field Layout
        BorderPane fieldArea= new BorderPane();
        fieldArea.setTop(p2Field);
        fieldArea.setBottom(p1Field);
        VBox V=new VBox();
        p2Mana.setMinSize(100,100);
        p1Mana.setMinSize(100,100);
        V.getChildren().add(p2Mana);
        V.getChildren().add(end);
        V.getChildren().add(p1Mana);
        fieldArea.setRight(V);
        fieldArea.setMinSize(1360,384);
        gamescreen.setCenter(fieldArea);



        Scene gamescene= new Scene(gamescreen,1360,768);
        stage.setScene(gamescene);
        minionTargeted=false;
        end.setOnMouseClicked(e-> {
          if(p1==game.getCurrentHero()){
            try {
                p1.endTurn();
            } catch (FullHandException fullHandException) {
                Card x=fullHandException.getBurned();
                Stage s1 = new Stage();
                s1.initModality(Modality.APPLICATION_MODAL);
                s1.show();
                StackPane sp = new StackPane();
                sp.getChildren().add(new Label("You Have a Full Hand!!\n"+x.getName()+"\n"+x.getManaCost()+"\n"+x.getRarity()));
                Scene sc = new Scene(sp, 200, 200);
                s1.setScene(sc);
                return;


            } catch (CloneNotSupportedException cloneNotSupportedException) {
                cloneNotSupportedException.printStackTrace();
            }
          ///validateHealth
              endTurnDraw(p2,p1,p2hand,p2Field,p1Field);
               }
          else{
              try {
                  p2.endTurn();
              } catch (FullHandException fullHandException) {
                  Card x=fullHandException.getBurned();
                  Stage s1 = new Stage();
                  s1.initModality(Modality.APPLICATION_MODAL);
                  s1.show();
                  StackPane sp = new StackPane();
                  sp.getChildren().add(new Label("You Have a Full Hand!!\n"+x.getName()+"\n"+x.getManaCost()+"\n"+x.getRarity()));
                  Scene sc = new Scene(sp, 200, 200);
                  s1.setScene(sc);
                  return;


              } catch (CloneNotSupportedException cloneNotSupportedException) {
                  cloneNotSupportedException.printStackTrace();
              }
              ///validateHealth
              endTurnDraw(p1,p2,p1hand,p1Field,p2Field);

          }
        });

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
    public void playerDraw(HBox phand,FlowPane pfield,Hero p,Hero pOther,FlowPane pOtherField){
        for(Card cur: p.getHand() ) {
            ImageView a= new ImageView(new Image(getImage(cur),130,200,false,true));
            if (cur instanceof Minion) {
                minionButton finalA5 = new minionButton((Minion) cur);
                a.setOnMouseClicked(e -> {
                    try {
                        p.playMinion((Minion) cur);
                    } catch (NotYourTurnException notYourTurnException) {
                        notYourTurnException= new NotYourTurnException("Not Your Turn!!");
                        exceptionWindow(notYourTurnException);
                        notYourTurnException.printStackTrace();
                        return;
                    } catch (NotEnoughManaException notEnoughManaException) {
                        notEnoughManaException= new NotEnoughManaException("Not Enough Mana!!");
                        exceptionWindow(notEnoughManaException);
                        notEnoughManaException.printStackTrace();
                        return;
                    } catch (FullFieldException fullFieldException) {
                        fullFieldException= new FullFieldException("Your Field is Full!!");
                        exceptionWindow(fullFieldException);
                        fullFieldException.printStackTrace();
                        return;
                    }
                    phand.getChildren().remove(a);
                    finalA5.setOnMouseClicked(ee->{
                        Stage s1=new Stage();
                        s1.initModality(Modality.APPLICATION_MODAL);
                        s1.show();
                        FlowPane oppField= new FlowPane();
                        ImageView opponent = null;
                        if(pOther instanceof Mage)
                            opponent=new ImageView(new Image("images\\Jaina_Proudmoore.png",250,250,true,true));
                        if(pOther instanceof Warlock)
                            opponent=new ImageView(new Image("images\\Guldan.png",250,250,true,true));
                        if(pOther instanceof Priest)
                            opponent=new ImageView(new Image("images\\Anduin_Wrynn.png",250,250,true,true));
                        if(pOther instanceof Paladin)
                            opponent=new ImageView(new Image("images\\Uther_Lightbringer.png",250,250,true,true));
                        if(pOther instanceof Hunter)
                            opponent=new ImageView(new Image("images\\Rexxar.png",250,250,true,true));
                        opponent.setOnMouseClicked(eee->{
                            heroTargeted=true;
                            heroTarget=pOther;
                            s1.close();
                            try {
                                p.attackWithMinion((Minion) cur,pOther);
                               verifyHeroP1();
                               verifyHeroP2();
                                System.out.println(pOther.getCurrentHP());
                            } catch (CannotAttackException cannotAttackException) {
                                cannotAttackException.printStackTrace();
                                exceptionWindow(cannotAttackException);
                                return;
                            } catch (NotYourTurnException notYourTurnException) {
                                notYourTurnException.printStackTrace();
                                exceptionWindow(notYourTurnException);
                                return;
                            } catch (TauntBypassException tauntBypassException) {
                                tauntBypassException.printStackTrace();
                                exceptionWindow(tauntBypassException);
                                return;
                            } catch (NotSummonedException notSummonedException) {
                                notSummonedException.printStackTrace();
                                exceptionWindow(notSummonedException);
                                return;
                            } catch (InvalidTargetException invalidTargetException) {
                                invalidTargetException.printStackTrace();
                                exceptionWindow(invalidTargetException);
                                return;
                            }
                            System.out.println(pOther.getCurrentHP());
                            System.out.println(p1.getCurrentHP());
                            System.out.println(p2.getCurrentHP());
                        });
                        oppField.getChildren().add(opponent);
                        for(Node curr: pOtherField.getChildren()){
                            if(!curr.isVisible())
                                continue;
                            minionButton target= new minionButton(((minionButton)curr).getMinion());
                            target.setOnMouseClicked(eee->{
                                try {
                                    p.attackWithMinion((Minion) cur,target.getMinion());
                                } catch (CannotAttackException cannotAttackException) {
                                    cannotAttackException.printStackTrace();
                                    exceptionWindow(cannotAttackException);
                                    return;
                                } catch (NotYourTurnException notYourTurnException) {
                                    notYourTurnException.printStackTrace();
                                    exceptionWindow(notYourTurnException);
                                    return;
                                } catch (TauntBypassException tauntBypassException) {
                                    tauntBypassException.printStackTrace();
                                    exceptionWindow(tauntBypassException);
                                    return;
                                } catch (InvalidTargetException invalidTargetException) {
                                    invalidTargetException.printStackTrace();
                                    exceptionWindow(invalidTargetException);
                                    return;
                                } catch (NotSummonedException notSummonedException) {
                                    exceptionWindow(notSummonedException);
                                    notSummonedException.printStackTrace();
                                    return;
                                }
                                ((minionButton)curr).verifyMinion();
                                finalA5.verifyMinion();
                                if(finalA5.getHp()==0)
                                    pfield.getChildren().remove(finalA5);
                                if(((minionButton)curr).getHp()==0)
                                    pOtherField.getChildren().remove(((minionButton)curr));
                                s1.close();


                            });
                            oppField.getChildren().add(target);
                        }
                        Scene scene= new Scene(oppField);
                        s1.setScene(scene);

                    });
                    pfield.getChildren().add(finalA5);

                });

            }
            if (cur instanceof Spell) {
                if (cur instanceof AOESpell) {
                    a.setOnMouseClicked(e -> {
                        try {
                            p.castSpell((AOESpell) cur, pOther.getField());
                        } catch (NotYourTurnException notYourTurnException) {
                            notYourTurnException=new NotYourTurnException("Not Your Turn!!");
                            exceptionWindow(notYourTurnException);
                            notYourTurnException.printStackTrace();
                            return;
                        } catch (NotEnoughManaException notEnoughManaException) {
                            notEnoughManaException=new NotEnoughManaException("Not Enough Mana!!");
                            exceptionWindow(notEnoughManaException);
                            notEnoughManaException.printStackTrace();
                            return;
                        }
                        phand.getChildren().remove(a);
                        System.out.println("works?");
                        for(Node curr: pOtherField.getChildren()){
                            if(curr.isVisible()) {
                                ((minionButton) curr).verifyMinion();
                            }
                        }
                        pOtherField.getChildren().removeIf(m->((minionButton)m).getHp()==0 && m.isVisible());
                    });
                }
                if (cur instanceof FieldSpell) {
                    a.setOnMouseClicked(e -> {
                        try {
                            p.castSpell((FieldSpell) cur);
                        } catch (NotYourTurnException notYourTurnException) {
                            notYourTurnException=new NotYourTurnException("Not Your Turn!!");
                            exceptionWindow(notYourTurnException);
                            notYourTurnException.printStackTrace();
                            return;
                        } catch (NotEnoughManaException notEnoughManaException) {
                            notEnoughManaException=new NotEnoughManaException("Not Enough Mana!!");
                            exceptionWindow(notEnoughManaException);
                            notEnoughManaException.printStackTrace();
                            return;
                        }
                        System.out.println("works?");
                        for(Node curr: pfield.getChildren()){
                            ((minionButton) curr).verifyMinion();
                        }
                        phand.getChildren().remove(a);
                    });
                }
                if (cur instanceof HeroTargetSpell && !(cur instanceof MinionTargetSpell)) {
                    a.setOnMouseClicked(e -> {
                        try {
                            p.castSpell(((HeroTargetSpell) cur), pOther);
                        } catch (NotYourTurnException notYourTurnException) {
                            notYourTurnException=new NotYourTurnException("Not Your Turn!!");
                            exceptionWindow(notYourTurnException);
                            notYourTurnException.printStackTrace();
                            return;
                        } catch (NotEnoughManaException notEnoughManaException) {
                            notEnoughManaException=new NotEnoughManaException("Not Enough Mana!!");
                            exceptionWindow(notEnoughManaException);
                            notEnoughManaException.printStackTrace();
                            return;
                        }
                        verifyHeroP1();
                        verifyHeroP2();
                        phand.getChildren().remove(a);
                        System.out.println("works?");
                    });
                }
                if (cur instanceof MinionTargetSpell) {

                    a.setOnMouseClicked(e -> {
                        Stage s1=new Stage();
                        s1.initModality(Modality.APPLICATION_MODAL);

                        s1.show();
                        FlowPane oppField= new FlowPane();
                        Scene scene= new Scene(oppField);
                        s1.setScene(scene);
                        if(cur instanceof HeroTargetSpell){
                        ImageView opponent = null;
                        if(pOther instanceof Mage)
                            opponent=new ImageView(new Image("images\\Jaina_Proudmoore.png",250,250,true,true));
                        if(pOther instanceof Warlock)
                            opponent=new ImageView(new Image("images\\Guldan.png",250,250,true,true));
                        if(pOther instanceof Priest)
                            opponent=new ImageView(new Image("images\\Anduin_Wrynn.png",250,250,true,true));
                        if(pOther instanceof Paladin)
                            opponent=new ImageView(new Image("images\\Uther_Lightbringer.png",250,250,true,true));
                        if(pOther instanceof Hunter)
                            opponent=new ImageView(new Image("images\\Rexxar.png",250,250,true,true));
                        opponent.setOnMouseClicked(eee->{
                            heroTargeted=true;
                            heroTarget=pOther;
                            s1.close();
                            if(heroTargeted){
                                heroTargeted=false;
                                try {
                                    p.castSpell((HeroTargetSpell) cur,pOther);
                                } catch (NotYourTurnException notYourTurnException) {
                                    notYourTurnException.printStackTrace();
                                    exceptionWindow(notYourTurnException);
                                    return;
                                } catch (NotEnoughManaException notEnoughManaException) {
                                    notEnoughManaException.printStackTrace();
                                    exceptionWindow(notEnoughManaException);
                                    return;
                                }
                                phand.getChildren().remove(a);
                                verifyHeroP2();
                                verifyHeroP1();
                            }});
                        oppField.getChildren().add(opponent);}
                        for(Node curr:pOtherField.getChildren()){
                            if(!curr.isVisible())
                                continue;
                            Minion target=((minionButton)curr).getMinion();
                            minionButton obj=new minionButton(target);
                            oppField.getChildren().add(obj);
                            obj.setOnMouseClicked(ee->{
                                minionTargeted=true;
                                minionTarget=target;
                                s1.close();
                                if(minionTargeted){
                                    minionTargeted=false;
                                    try {
                                        p.castSpell((MinionTargetSpell) cur,minionTarget);
                                    } catch (NotYourTurnException notYourTurnException) {
                                        notYourTurnException.printStackTrace();
                                        exceptionWindow(notYourTurnException);
                                        return;
                                    } catch (NotEnoughManaException notEnoughManaException) {
                                        notEnoughManaException.printStackTrace();
                                        exceptionWindow(notEnoughManaException);
                                        return;
                                    } catch (InvalidTargetException invalidTargetException) {
                                        invalidTargetException.printStackTrace();
                                        exceptionWindow(invalidTargetException);
                                        return;
                                    }
                                    ((minionButton) curr).verifyMinion();
                                    if(((minionButton) curr).getHp()==0)
                                        pOtherField.getChildren().remove(curr);
                                    phand.getChildren().remove(a);
                                }

;});
                        }
                    });
                }
                if (cur instanceof LeechingSpell) {

                    a.setOnMouseClicked(e -> {
                        Stage s1=new Stage();
                        s1.initModality(Modality.APPLICATION_MODAL);

                        s1.show();
                        FlowPane oppField= new FlowPane();
                        Scene scene= new Scene(oppField);
                        s1.setScene(scene);
                        if(cur instanceof HeroTargetSpell){
                            ImageView opponent = null;
                            if(pOther instanceof Mage)
                                opponent=new ImageView(new Image("images\\Jaina_Proudmoore.png",250,250,true,true));
                            if(pOther instanceof Warlock)
                                opponent=new ImageView(new Image("images\\Guldan.png",250,250,true,true));
                            if(pOther instanceof Priest)
                                opponent=new ImageView(new Image("images\\Anduin_Wrynn.png",250,250,true,true));
                            if(pOther instanceof Paladin)
                                opponent=new ImageView(new Image("images\\Uther_Lightbringer.png",250,250,true,true));
                            if(pOther instanceof Hunter)
                                opponent=new ImageView(new Image("images\\Rexxar.png",250,250,true,true));
                            opponent.setOnMouseClicked(eee->{
                                heroTargeted=true;
                                heroTarget=pOther;
                                s1.close();
                                if(heroTargeted){
                                    heroTargeted=false;
                                    try {
                                        p.castSpell((HeroTargetSpell) cur,pOther);
                                    } catch (NotYourTurnException notYourTurnException) {
                                        notYourTurnException.printStackTrace();
                                        exceptionWindow(notYourTurnException);
                                        return;
                                    } catch (NotEnoughManaException notEnoughManaException) {
                                        notEnoughManaException.printStackTrace();
                                        exceptionWindow(notEnoughManaException);
                                        return;
                                    }
                                    phand.getChildren().remove(a);
                                    verifyHeroP2();
                                    verifyHeroP1();
                                }});
                            oppField.getChildren().add(opponent);}
                        for(Node curr:pOtherField.getChildren()){
                            if(!curr.isVisible())
                                continue;
                            Minion target=((minionButton)curr).getMinion();
                            minionButton obj=new minionButton(target);
                            oppField.getChildren().add(obj);
                            obj.setOnMouseClicked(ee->{
                                minionTargeted=true;
                                minionTarget=target;
                                s1.close();
                                if(minionTargeted){
                                    minionTargeted=false;
                                    try {
                                        p.castSpell((LeechingSpell) cur,minionTarget);
                                    } catch (NotYourTurnException notYourTurnException) {
                                        notYourTurnException.printStackTrace();
                                        exceptionWindow(notYourTurnException);
                                        return;
                                    } catch (NotEnoughManaException notEnoughManaException) {
                                        notEnoughManaException.printStackTrace();
                                        exceptionWindow(notEnoughManaException);
                                        return;
                                    }
                                    ((minionButton) curr).verifyMinion();
                                    if(((minionButton) curr).getHp()==0)
                                        pOtherField.getChildren().remove(curr);
                                    verifyHeroP1();
                                    verifyHeroP2();
                                    phand.getChildren().remove(a);
                                }



                                });
                        }
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

      if(cur.getName().equals("King Krush"))
          return "images\\cards\\KingKrush.png";

      if(cur.getName().equals("Kalycgos"))
          return "images\\cards\\Kalycgos.png";

      if(cur.getName().equals("Tirion Fordring"))
          return "images\\cards\\TirionFordring.png";

      if(cur.getName().equals("Prophet Velen"))
          return "images\\cards\\ProphetVelen.png";

      if(cur.getName().equals("Wilfred Fizzlebang"))
          return "images\\cards\\WilfredFizzlebang.png";

      if(cur.getName().equals("Curse of Weakness"))
          return "images\\cards\\CurseofWeakness.png";

      if(cur.getName().equals("Divine spirit"))
          return "images\\cards\\Divinespirit.png";

      if(cur.getName().equals("Flamestrike"))
          return "images\\cards\\FlameStrike.png";

      if(cur.getName().equals("Holy Nova"))
          return "images\\cards\\HolyNova.png";

      if(cur.getName().equals("Kill Command"))
          return "images\\cards\\KillCommand.png";

      if(cur.getName().equals("Level Up!"))
          return "images\\cards\\LevelUp.png";

      if(cur.getName().equals("Multi-Shot"))
          return "images\\cards\\MultiShot.png";

      if(cur.getName().equals("Polymorph"))
          return "images\\cards\\Polymorph.png";

      if(cur.getName().equals("Pyroblast"))
          return "images\\cards\\Pyroblast.png";

      if(cur.getName().equals("Seal of Champions"))
          return "images\\cards\\SealOfChampions.png";

      if(cur.getName().equals("Shadow Word: Death"))
          return "images\\cards\\ShadowWordDeath.png";

      if(cur.getName().equals("Siphon Soul"))
          return "images\\cards\\SiphonSoul.png";

      if(cur.getName().equals("Twisting Nether"))
          return "images\\cards\\Twistingnether.png";

      if(cur.getName().equals("Goldshire Footman"))
          return "images\\cards\\GoldshireFootman.png";

      return "images\\whitepage.png";
  }
  public void endTurnDraw(Hero p,Hero pOther,HBox phand,FlowPane pfield,FlowPane pOtherField){
        Card cur= p.getHand().get(p.getHand().size()-1);
        System.out.println(p.getHand());
          ImageView a= new ImageView(new Image(getImage(cur),130,200,false,true));
          if (cur instanceof Minion) {
              minionButton finalA5 = new minionButton((Minion) cur);
              a.setOnMouseClicked(e -> {
                  try {
                      p.playMinion((Minion) cur);
                  } catch (NotYourTurnException notYourTurnException) {
                      notYourTurnException= new NotYourTurnException("Not Your Turn!!");
                      exceptionWindow(notYourTurnException);
                      notYourTurnException.printStackTrace();
                      return;
                  } catch (NotEnoughManaException notEnoughManaException) {
                      notEnoughManaException= new NotEnoughManaException("Not Enough Mana!!");
                      exceptionWindow(notEnoughManaException);
                      notEnoughManaException.printStackTrace();
                      return;
                  } catch (FullFieldException fullFieldException) {
                      fullFieldException= new FullFieldException("Your Field is Full!!");
                      exceptionWindow(fullFieldException);
                      fullFieldException.printStackTrace();
                      return;
                  }
                  phand.getChildren().remove(a);
                  finalA5.setOnMouseClicked(ee->{
                      Stage s1=new Stage();
                      s1.initModality(Modality.APPLICATION_MODAL);
                      s1.show();
                      FlowPane oppField= new FlowPane();
                      ImageView opponent = null;
                      if(pOther instanceof Mage)
                          opponent=new ImageView(new Image("images\\Jaina_Proudmoore.png",250,250,true,true));
                      if(pOther instanceof Warlock)
                          opponent=new ImageView(new Image("images\\Guldan.png",250,250,true,true));
                      if(pOther instanceof Priest)
                          opponent=new ImageView(new Image("images\\Anduin_Wrynn.png",250,250,true,true));
                      if(pOther instanceof Paladin)
                          opponent=new ImageView(new Image("images\\Uther_Lightbringer.png",250,250,true,true));
                      if(pOther instanceof Hunter)
                          opponent=new ImageView(new Image("images\\Rexxar.png",250,250,true,true));
                      opponent.setOnMouseClicked(eee->{
                          heroTargeted=true;
                          heroTarget=pOther;
                          s1.close();
                          try {
                              p.attackWithMinion((Minion) cur,pOther);
                              verifyHeroP1();
                              verifyHeroP2();
                              System.out.println(pOther.getCurrentHP());
                          } catch (CannotAttackException cannotAttackException) {
                              cannotAttackException.printStackTrace();
                              exceptionWindow(cannotAttackException);
                              return;
                          } catch (NotYourTurnException notYourTurnException) {
                              notYourTurnException.printStackTrace();
                              exceptionWindow(notYourTurnException);
                              return;
                          } catch (TauntBypassException tauntBypassException) {
                              tauntBypassException.printStackTrace();
                              exceptionWindow(tauntBypassException);
                              return;
                          } catch (NotSummonedException notSummonedException) {
                              notSummonedException.printStackTrace();
                              exceptionWindow(notSummonedException);
                              return;
                          } catch (InvalidTargetException invalidTargetException) {
                              invalidTargetException.printStackTrace();
                              exceptionWindow(invalidTargetException);
                              return;
                          }
                          System.out.println(pOther.getCurrentHP());
                          System.out.println(p1.getCurrentHP());
                          System.out.println(p2.getCurrentHP());
                      });
                      oppField.getChildren().add(opponent);
                      for(Node curr: pOtherField.getChildren()){
                          if(!curr.isVisible())
                              continue;
                          minionButton target= new minionButton(((minionButton)curr).getMinion());
                          target.setOnMouseClicked(eee->{
                              try {
                                  p.attackWithMinion((Minion) cur,target.getMinion());
                              } catch (CannotAttackException cannotAttackException) {
                                  cannotAttackException.printStackTrace();
                                  exceptionWindow(cannotAttackException);
                                  return;
                              } catch (NotYourTurnException notYourTurnException) {
                                  notYourTurnException.printStackTrace();
                                  exceptionWindow(notYourTurnException);
                                  return;
                              } catch (TauntBypassException tauntBypassException) {
                                  tauntBypassException.printStackTrace();
                                  exceptionWindow(tauntBypassException);
                                  return;
                              } catch (InvalidTargetException invalidTargetException) {
                                  invalidTargetException.printStackTrace();
                                  exceptionWindow(invalidTargetException);
                                  return;
                              } catch (NotSummonedException notSummonedException) {
                                  exceptionWindow(notSummonedException);
                                  notSummonedException.printStackTrace();
                                  return;
                              }
                              ((minionButton)curr).verifyMinion();
                              finalA5.verifyMinion();
                              if(finalA5.getHp()==0)
                                  pfield.getChildren().remove(finalA5);
                              if(((minionButton)curr).getHp()==0)
                                  pOtherField.getChildren().remove(((minionButton)curr));
                              s1.close();


                          });
                          oppField.getChildren().add(target);
                      }
                      Scene scene= new Scene(oppField);
                      s1.setScene(scene);

                  });
                  pfield.getChildren().add(finalA5);

              });

          }
          if (cur instanceof Spell) {
              if (cur instanceof AOESpell) {
                  a.setOnMouseClicked(e -> {
                      try {
                          p.castSpell((AOESpell) cur, pOther.getField());
                      } catch (NotYourTurnException notYourTurnException) {
                          notYourTurnException=new NotYourTurnException("Not Your Turn!!");
                          exceptionWindow(notYourTurnException);
                          notYourTurnException.printStackTrace();
                          return;
                      } catch (NotEnoughManaException notEnoughManaException) {
                          notEnoughManaException=new NotEnoughManaException("Not Enough Mana!!");
                          exceptionWindow(notEnoughManaException);
                          notEnoughManaException.printStackTrace();
                          return;
                      }
                      phand.getChildren().remove(a);
                      System.out.println("works?");
                      for(Node curr: pOtherField.getChildren()){
                          if(curr.isVisible()) {
                              ((minionButton) curr).verifyMinion();
                          }
                      }
                      pOtherField.getChildren().removeIf(m->((minionButton)m).getHp()==0 && m.isVisible());
                        phand.getChildren().remove(a);});
              }
              if (cur instanceof FieldSpell) {
                  a.setOnMouseClicked(e -> {
                      try {
                          p.castSpell((FieldSpell) cur);
                      } catch (NotYourTurnException notYourTurnException) {
                          notYourTurnException=new NotYourTurnException("Not Your Turn!!");
                          exceptionWindow(notYourTurnException);
                          notYourTurnException.printStackTrace();
                          return;
                      } catch (NotEnoughManaException notEnoughManaException) {
                          notEnoughManaException=new NotEnoughManaException("Not Enough Mana!!");
                          exceptionWindow(notEnoughManaException);
                          notEnoughManaException.printStackTrace();
                          return;
                      }
                      System.out.println("works?");
                      for(Node curr: pfield.getChildren()){
                          ((minionButton) curr).verifyMinion();
                      }
                      phand.getChildren().remove(a);
                  });
              }
              if (cur instanceof HeroTargetSpell && !(cur instanceof MinionTargetSpell)) {
                  a.setOnMouseClicked(e -> {
                      try {
                          p.castSpell(((HeroTargetSpell) cur), pOther);
                      } catch (NotYourTurnException notYourTurnException) {
                          notYourTurnException=new NotYourTurnException("Not Your Turn!!");
                          exceptionWindow(notYourTurnException);
                          notYourTurnException.printStackTrace();
                          return;
                      } catch (NotEnoughManaException notEnoughManaException) {
                          notEnoughManaException=new NotEnoughManaException("Not Enough Mana!!");
                          exceptionWindow(notEnoughManaException);
                          notEnoughManaException.printStackTrace();
                          return;
                      }
                      verifyHeroP1();
                      verifyHeroP2();
                      phand.getChildren().remove(a);
                      System.out.println("works?");
                  });
              }
              if (cur instanceof MinionTargetSpell) {

                  a.setOnMouseClicked(e -> {
                      Stage s1=new Stage();
                      s1.initModality(Modality.APPLICATION_MODAL);

                      s1.show();
                      FlowPane oppField= new FlowPane();
                      Scene scene= new Scene(oppField);
                      s1.setScene(scene);
                      if(cur instanceof HeroTargetSpell){
                          ImageView opponent = null;
                          if(pOther instanceof Mage)
                              opponent=new ImageView(new Image("images\\Jaina_Proudmoore.png",250,250,true,true));
                          if(pOther instanceof Warlock)
                              opponent=new ImageView(new Image("images\\Guldan.png",250,250,true,true));
                          if(pOther instanceof Priest)
                              opponent=new ImageView(new Image("images\\Anduin_Wrynn.png",250,250,true,true));
                          if(pOther instanceof Paladin)
                              opponent=new ImageView(new Image("images\\Uther_Lightbringer.png",250,250,true,true));
                          if(pOther instanceof Hunter)
                              opponent=new ImageView(new Image("images\\Rexxar.png",250,250,true,true));
                          opponent.setOnMouseClicked(eee->{
                              heroTargeted=true;
                              heroTarget=pOther;
                              s1.close();
                              if(heroTargeted){
                                  heroTargeted=false;
                                  try {
                                      p.castSpell((HeroTargetSpell) cur,pOther);
                                  } catch (NotYourTurnException notYourTurnException) {
                                      notYourTurnException.printStackTrace();
                                      exceptionWindow(notYourTurnException);
                                      return;
                                  } catch (NotEnoughManaException notEnoughManaException) {
                                      notEnoughManaException.printStackTrace();
                                      exceptionWindow(notEnoughManaException);
                                      return;
                                  }
                                  verifyHeroP2();
                                  verifyHeroP1();
                              }});
                          oppField.getChildren().add(opponent);}
                      for(Node curr:pOtherField.getChildren()){
                          if(!curr.isVisible())
                              continue;
                          Minion target=((minionButton)curr).getMinion();
                          minionButton obj=new minionButton(target);
                          oppField.getChildren().add(obj);
                          obj.setOnMouseClicked(ee->{
                              minionTargeted=true;
                              minionTarget=target;
                              s1.close();
                              if(minionTargeted){
                                  minionTargeted=false;
                                  try {
                                      p.castSpell((MinionTargetSpell) cur,minionTarget);
                                  } catch (NotYourTurnException notYourTurnException) {
                                      notYourTurnException.printStackTrace();
                                      exceptionWindow(notYourTurnException);
                                      return;
                                  } catch (NotEnoughManaException notEnoughManaException) {
                                      notEnoughManaException.printStackTrace();
                                      exceptionWindow(notEnoughManaException);
                                      return;
                                  } catch (InvalidTargetException invalidTargetException) {
                                      invalidTargetException.printStackTrace();
                                      exceptionWindow(invalidTargetException);
                                      return;
                                  }
                                  ((minionButton) curr).verifyMinion();
                                  if(((minionButton) curr).getHp()==0)
                                      pOtherField.getChildren().remove(curr);

                              }

                              ;
                              phand.getChildren().remove(a);});
                      }

});
              }
              if (cur instanceof LeechingSpell) {

                  a.setOnMouseClicked(e -> {
                      Stage s1=new Stage();
                      s1.initModality(Modality.APPLICATION_MODAL);

                      s1.show();
                      FlowPane oppField= new FlowPane();
                      Scene scene= new Scene(oppField);
                      s1.setScene(scene);
                      if(cur instanceof HeroTargetSpell){
                          ImageView opponent = null;
                          if(pOther instanceof Mage)
                              opponent=new ImageView(new Image("images\\Jaina_Proudmoore.png",250,250,true,true));
                          if(pOther instanceof Warlock)
                              opponent=new ImageView(new Image("images\\Guldan.png",250,250,true,true));
                          if(pOther instanceof Priest)
                              opponent=new ImageView(new Image("images\\Anduin_Wrynn.png",250,250,true,true));
                          if(pOther instanceof Paladin)
                              opponent=new ImageView(new Image("images\\Uther_Lightbringer.png",250,250,true,true));
                          if(pOther instanceof Hunter)
                              opponent=new ImageView(new Image("images\\Rexxar.png",250,250,true,true));
                          opponent.setOnMouseClicked(eee->{
                              heroTargeted=true;
                              heroTarget=pOther;
                              s1.close();
                              if(heroTargeted){
                                  heroTargeted=false;
                                  try {
                                      p.castSpell((HeroTargetSpell) cur,pOther);
                                  } catch (NotYourTurnException notYourTurnException) {
                                      notYourTurnException.printStackTrace();
                                      exceptionWindow(notYourTurnException);
                                      return;
                                  } catch (NotEnoughManaException notEnoughManaException) {
                                      notEnoughManaException.printStackTrace();
                                      exceptionWindow(notEnoughManaException);
                                      return;
                                  }
                                  verifyHeroP2();
                                  verifyHeroP1();
                              }});
                          oppField.getChildren().add(opponent);}
                      for(Node curr:pOtherField.getChildren()){
                          if(!curr.isVisible())
                              continue;
                          Minion target=((minionButton)curr).getMinion();
                          minionButton obj=new minionButton(target);
                          oppField.getChildren().add(obj);
                          obj.setOnMouseClicked(ee->{
                              minionTargeted=true;
                              minionTarget=target;
                              s1.close();
                              if(minionTargeted){
                                  minionTargeted=false;
                                  try {
                                      p.castSpell((LeechingSpell) cur,minionTarget);
                                  } catch (NotYourTurnException notYourTurnException) {
                                      notYourTurnException.printStackTrace();
                                      exceptionWindow(notYourTurnException);
                                      return;
                                  } catch (NotEnoughManaException notEnoughManaException) {
                                      notEnoughManaException.printStackTrace();
                                      exceptionWindow(notEnoughManaException);
                                      return;
                                  }
                                  ((minionButton) curr).verifyMinion();
                                  if(((minionButton) curr).getHp()==0)
                                      pOtherField.getChildren().remove(curr);
                                  verifyHeroP1();
                                  verifyHeroP2();
                                  phand.getChildren().remove(a);
                              }



                          });
                      }
                  });
              }
          }
          phand.getChildren().add(a);
      }
//  public void endTurn(){
//
//
//  }
    public void exceptionWindow(Exception e){
        Stage s1=new Stage();
        s1.initModality(Modality.APPLICATION_MODAL);
        s1.show();
        BorderPane sp= new BorderPane();
        VBox v= new VBox();
        VBox v1= new VBox();
        VBox v2= new VBox();
        VBox v3= new VBox();
        VBox v4= new VBox();
        v.setPrefSize(125,125);
        v1.setPrefSize(62.5,250);
        v2.setPrefSize(62.5,250);
        v3.setPrefSize(250,62.5);
        v4.setPrefSize(250,62.5);
        sp.setCenter(v);
        sp.setLeft(v1);
        sp.setRight(v2);
        sp.setTop(v3);
        sp.setBottom(v4);
        Label x=new Label(e.getLocalizedMessage());
        v.getChildren().add(x);
        Scene sc=new Scene(sp,250,250);
        Button okay= new Button("OKAY");
        okay.setOnMouseClicked(e1-> s1.close());
        v.getChildren().add(okay);
        s1.setScene(sc);
    }
    public void target(Hero p,Hero pOther,ImageView opponent,boolean attack){
        Stage s1=new Stage();
        s1.initModality(Modality.APPLICATION_MODAL);
        s1.show();
        FlowPane oppField= new FlowPane();
        opponent.setOnMouseClicked(e->{
            heroTargeted=true;
            heroTarget=pOther;
            s1.close();
        });
        oppField.getChildren().add(opponent);
        for(Minion cur: pOther.getField()){
            minionButton target= new minionButton(cur);
            target.setOnMouseClicked(e->{
                minionTargeted=true;
                minionTarget=cur;
                s1.close();
            });
            oppField.getChildren().add(target);
        }
        Scene scene= new Scene(oppField);
        s1.setScene(scene);
    }
    public void verifyHeroP1(){
        if(p1 instanceof Mage){
            if(p1.getCurrentHP()==30)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_30.png",250,300,true,true));

            if(p1.getCurrentHP()==29)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_29.png",250,300,true,true));

            if(p1.getCurrentHP()==28)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_28.png",250,300,true,true));

            if(p1.getCurrentHP()==27)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_27.png",250,300,true,true));

            if(p1.getCurrentHP()==26)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_26.png",250,300,true,true));

            if(p1.getCurrentHP()==25)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_25.png",250,300,true,true));

            if(p1.getCurrentHP()==24)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_24.png",250,300,true,true));

            if(p1.getCurrentHP()==23)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_23.png",250,300,true,true));

            if(p1.getCurrentHP()==22)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_22.png",250,300,true,true));

            if(p1.getCurrentHP()==21)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_21.png",250,300,true,true));

            if(p1.getCurrentHP()==20)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_20.png",250,300,true,true));

            if(p1.getCurrentHP()==19)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_19.png",250,300,true,true));

            if(p1.getCurrentHP()==18)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_18.png",250,300,true,true));

            if(p1.getCurrentHP()==17)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_17.png",250,300,true,true));

            if(p1.getCurrentHP()==16)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_16.png",250,300,true,true));

            if(p1.getCurrentHP()==15)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_15.png",250,300,true,true));

            if(p1.getCurrentHP()==14)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_14.png",250,300,true,true));

            if(p1.getCurrentHP()==13)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_13.png",250,300,true,true));

            if(p1.getCurrentHP()==12)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_12.png",250,300,true,true));

            if(p1.getCurrentHP()==11)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_11.png",250,300,true,true));

            if(p1.getCurrentHP()==10)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_10.png",250,300,true,true));

            if(p1.getCurrentHP()==9)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_9.png",250,300,true,true));

            if(p1.getCurrentHP()==8)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_8.png",250,300,true,true));

            if(p1.getCurrentHP()==7)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_7.png",250,300,true,true));

            if(p1.getCurrentHP()==6)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_6.png",250,300,true,true));

            if(p1.getCurrentHP()==5)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_5.png",250,300,true,true));

            if(p1.getCurrentHP()==4)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_4.png",250,300,true,true));

            if(p1.getCurrentHP()==3)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_3.png",250,300,true,true));

            if(p1.getCurrentHP()==2)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_2.png",250,300,true,true));

            if(p1.getCurrentHP()==1)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_1.png",250,300,true,true));

            if(p1.getCurrentHP()==0)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_0.png",250,300,true,true));
        }

        if(p1 instanceof Paladin){
            if(p1.getCurrentHP()==30)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_30.png",250,300,true,true));

            if(p1.getCurrentHP()==29)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_29.png",250,300,true,true));

            if(p1.getCurrentHP()==28)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_28.png",250,300,true,true));

            if(p1.getCurrentHP()==27)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_27.png",250,300,true,true));

            if(p1.getCurrentHP()==26)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_26.png",250,300,true,true));

            if(p1.getCurrentHP()==25)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_25.png",250,300,true,true));

            if(p1.getCurrentHP()==24)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_24.png",250,300,true,true));

            if(p1.getCurrentHP()==23)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_23.png",250,300,true,true));

            if(p1.getCurrentHP()==22)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_22.png",250,300,true,true));

            if(p1.getCurrentHP()==21)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_21.png",250,300,true,true));

            if(p1.getCurrentHP()==20)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_20.png",250,300,true,true));

            if(p1.getCurrentHP()==19)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_19.png",250,300,true,true));

            if(p1.getCurrentHP()==18)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_18.png",250,300,true,true));

            if(p1.getCurrentHP()==17)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_17.png",250,300,true,true));

            if(p1.getCurrentHP()==16)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_16.png",250,300,true,true));

            if(p1.getCurrentHP()==15)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_15.png",250,300,true,true));

            if(p1.getCurrentHP()==14)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_14.png",250,300,true,true));

            if(p1.getCurrentHP()==13)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_13.png",250,300,true,true));

            if(p1.getCurrentHP()==12)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_12.png",250,300,true,true));

            if(p1.getCurrentHP()==11)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_11.png",250,300,true,true));

            if(p1.getCurrentHP()==10)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_10.png",250,300,true,true));

            if(p1.getCurrentHP()==9)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_9.png",250,300,true,true));

            if(p1.getCurrentHP()==8)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_8.png",250,300,true,true));

            if(p1.getCurrentHP()==7)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_7.png",250,300,true,true));

            if(p1.getCurrentHP()==6)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_6.png",250,300,true,true));

            if(p1.getCurrentHP()==5)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_5.png",250,300,true,true));

            if(p1.getCurrentHP()==4)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_4.png",250,300,true,true));

            if(p1.getCurrentHP()==3)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_3.png",250,300,true,true));

            if(p1.getCurrentHP()==2)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_2.png",250,300,true,true));

            if(p1.getCurrentHP()==1)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_1.png",250,300,true,true));

            if(p1.getCurrentHP()==0)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_0.png",250,300,true,true));

        }

        if(p1 instanceof Priest){
            if(p1.getCurrentHP()==30)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_30.png",250,300,true,true));

            if(p1.getCurrentHP()==29)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_29.png",250,300,true,true));

            if(p1.getCurrentHP()==28)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_28.png",250,300,true,true));

            if(p1.getCurrentHP()==27)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_27.png",250,300,true,true));

            if(p1.getCurrentHP()==26)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_26.png",250,300,true,true));

            if(p1.getCurrentHP()==25)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_25.png",250,300,true,true));

            if(p1.getCurrentHP()==24)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_24.png",250,300,true,true));

            if(p1.getCurrentHP()==23)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_23.png",250,300,true,true));

            if(p1.getCurrentHP()==22)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_22.png",250,300,true,true));

            if(p1.getCurrentHP()==21)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_21.png",250,300,true,true));

            if(p1.getCurrentHP()==20)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_20.png",250,300,true,true));

            if(p1.getCurrentHP()==19)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_19.png",250,300,true,true));

            if(p1.getCurrentHP()==18)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_18.png",250,300,true,true));

            if(p1.getCurrentHP()==17)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_17.png",250,300,true,true));

            if(p1.getCurrentHP()==16)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_16.png",250,300,true,true));

            if(p1.getCurrentHP()==15)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_15.png",250,300,true,true));

            if(p1.getCurrentHP()==14)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_14.png",250,300,true,true));

            if(p1.getCurrentHP()==13)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_13.png",250,300,true,true));

            if(p1.getCurrentHP()==12)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_12.png",250,300,true,true));

            if(p1.getCurrentHP()==11)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_11.png",250,300,true,true));

            if(p1.getCurrentHP()==10)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_10.png",250,300,true,true));

            if(p1.getCurrentHP()==9)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_9.png",250,300,true,true));

            if(p1.getCurrentHP()==8)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_8.png",250,300,true,true));

            if(p1.getCurrentHP()==7)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_7.png",250,300,true,true));

            if(p1.getCurrentHP()==6)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_6.png",250,300,true,true));

            if(p1.getCurrentHP()==5)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_5.png",250,300,true,true));

            if(p1.getCurrentHP()==4)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_4.png",250,300,true,true));

            if(p1.getCurrentHP()==3)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_3.png",250,300,true,true));

            if(p1.getCurrentHP()==2)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_2.png",250,300,true,true));

            if(p1.getCurrentHP()==1)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_1.png",250,300,true,true));

            if(p1.getCurrentHP()==0)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_0.png",250,300,true,true));


        }
        if(p1 instanceof Warlock){
            if(p1.getCurrentHP()==30)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_30.png",250,300,true,true));

            if(p1.getCurrentHP()==29)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_29.png",250,300,true,true));

            if(p1.getCurrentHP()==28)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_28.png",250,300,true,true));

            if(p1.getCurrentHP()==27)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_27.png",250,300,true,true));

            if(p1.getCurrentHP()==26)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_26.png",250,300,true,true));

            if(p1.getCurrentHP()==25)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_25.png",250,300,true,true));

            if(p1.getCurrentHP()==24)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_24.png",250,300,true,true));

            if(p1.getCurrentHP()==23)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_23.png",250,300,true,true));

            if(p1.getCurrentHP()==22)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_22.png",250,300,true,true));

            if(p1.getCurrentHP()==21)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_21.png",250,300,true,true));

            if(p1.getCurrentHP()==20)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_20.png",250,300,true,true));

            if(p1.getCurrentHP()==19)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_19.png",250,300,true,true));

            if(p1.getCurrentHP()==18)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_18.png",250,300,true,true));

            if(p1.getCurrentHP()==17)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_17.png",250,300,true,true));

            if(p1.getCurrentHP()==16)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_16.png",250,300,true,true));

            if(p1.getCurrentHP()==15)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_15.png",250,300,true,true));

            if(p1.getCurrentHP()==14)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_14.png",250,300,true,true));

            if(p1.getCurrentHP()==13)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_13.png",250,300,true,true));

            if(p1.getCurrentHP()==12)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_12.png",250,300,true,true));

            if(p1.getCurrentHP()==11)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_11.png",250,300,true,true));

            if(p1.getCurrentHP()==10)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_10.png",250,300,true,true));

            if(p1.getCurrentHP()==9)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_9.png",250,300,true,true));

            if(p1.getCurrentHP()==8)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_8.png",250,300,true,true));

            if(p1.getCurrentHP()==7)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_7.png",250,300,true,true));

            if(p1.getCurrentHP()==6)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_6.png",250,300,true,true));

            if(p1.getCurrentHP()==5)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_5.png",250,300,true,true));

            if(p1.getCurrentHP()==4)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_4.png",250,300,true,true));

            if(p1.getCurrentHP()==3)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_3.png",250,300,true,true));

            if(p1.getCurrentHP()==2)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_2.png",250,300,true,true));

            if(p1.getCurrentHP()==1)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_1.png",250,300,true,true));

            if(p1.getCurrentHP()==0)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_0.png",250,300,true,true));


        }
        if(p1 instanceof Hunter){
            if(p1.getCurrentHP()==30)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_30.png",250,300,true,true));

            if(p1.getCurrentHP()==29)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_29.png",250,300,true,true));

            if(p1.getCurrentHP()==28)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_28.png",250,300,true,true));

            if(p1.getCurrentHP()==27)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_27.png",250,300,true,true));

            if(p1.getCurrentHP()==26)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_26.png",250,300,true,true));

            if(p1.getCurrentHP()==25)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_25.png",250,300,true,true));

            if(p1.getCurrentHP()==24)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_24.png",250,300,true,true));

            if(p1.getCurrentHP()==23)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_23.png",250,300,true,true));

            if(p1.getCurrentHP()==22)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_22.png",250,300,true,true));

            if(p1.getCurrentHP()==21)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_21.png",250,300,true,true));

            if(p1.getCurrentHP()==20)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_20.png",250,300,true,true));

            if(p1.getCurrentHP()==19)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_19.png",250,300,true,true));

            if(p1.getCurrentHP()==18)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_18.png",250,300,true,true));

            if(p1.getCurrentHP()==17)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_17.png",250,300,true,true));

            if(p1.getCurrentHP()==16)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_16.png",250,300,true,true));

            if(p1.getCurrentHP()==15)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_15.png",250,300,true,true));

            if(p1.getCurrentHP()==14)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_14.png",250,300,true,true));

            if(p1.getCurrentHP()==13)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_13.png",250,300,true,true));

            if(p1.getCurrentHP()==12)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_12.png",250,300,true,true));

            if(p1.getCurrentHP()==11)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_11.png",250,300,true,true));

            if(p1.getCurrentHP()==10)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_10.png",250,300,true,true));

            if(p1.getCurrentHP()==9)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_9.png",250,300,true,true));

            if(p1.getCurrentHP()==8)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_8.png",250,300,true,true));

            if(p1.getCurrentHP()==7)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_7.png",250,300,true,true));

            if(p1.getCurrentHP()==6)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_6.png",250,300,true,true));

            if(p1.getCurrentHP()==5)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_5.png",250,300,true,true));

            if(p1.getCurrentHP()==4)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_4.png",250,300,true,true));

            if(p1.getCurrentHP()==3)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_3.png",250,300,true,true));

            if(p1.getCurrentHP()==2)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_2.png",250,300,true,true));

            if(p1.getCurrentHP()==1)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_1.png",250,300,true,true));

            if(p1.getCurrentHP()==0)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_0.png",250,300,true,true));


        }
    }

    public void verifyHeroP2(){        if(p2 instanceof Mage){
        if(p2.getCurrentHP()==30)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_30.png",250,300,true,true));

        if(p2.getCurrentHP()==29)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_29.png",250,300,true,true));

        if(p2.getCurrentHP()==28)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_28.png",250,300,true,true));

        if(p2.getCurrentHP()==27)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_27.png",250,300,true,true));

        if(p2.getCurrentHP()==26)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_26.png",250,300,true,true));

        if(p2.getCurrentHP()==25)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_25.png",250,300,true,true));

        if(p2.getCurrentHP()==24)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_24.png",250,300,true,true));

        if(p2.getCurrentHP()==23)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_23.png",250,300,true,true));

        if(p2.getCurrentHP()==22)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_22.png",250,300,true,true));

        if(p2.getCurrentHP()==21)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_21.png",250,300,true,true));

        if(p2.getCurrentHP()==20)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_20.png",250,300,true,true));

        if(p2.getCurrentHP()==19)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_19.png",250,300,true,true));

        if(p2.getCurrentHP()==18)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_18.png",250,300,true,true));

        if(p2.getCurrentHP()==17)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_17.png",250,300,true,true));

        if(p2.getCurrentHP()==16)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_16.png",250,300,true,true));

        if(p2.getCurrentHP()==15)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_15.png",250,300,true,true));

        if(p2.getCurrentHP()==14)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_14.png",250,300,true,true));

        if(p2.getCurrentHP()==13)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_13.png",250,300,true,true));

        if(p2.getCurrentHP()==12)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_12.png",250,300,true,true));

        if(p2.getCurrentHP()==11)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_11.png",250,300,true,true));

        if(p2.getCurrentHP()==10)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_10.png",250,300,true,true));

        if(p2.getCurrentHP()==9)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_9.png",250,300,true,true));

        if(p2.getCurrentHP()==8)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_8.png",250,300,true,true));

        if(p2.getCurrentHP()==7)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_7.png",250,300,true,true));

        if(p2.getCurrentHP()==6)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_6.png",250,300,true,true));

        if(p2.getCurrentHP()==5)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_5.png",250,300,true,true));

        if(p2.getCurrentHP()==4)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_4.png",250,300,true,true));

        if(p2.getCurrentHP()==3)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_3.png",250,300,true,true));

        if(p2.getCurrentHP()==2)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_2.png",250,300,true,true));

        if(p2.getCurrentHP()==1)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_1.png",250,300,true,true));

        if(p2.getCurrentHP()==0)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_0.png",250,300,true,true));
    }

        if(p2 instanceof Paladin){
            if(p2.getCurrentHP()==30)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_30.png",250,300,true,true));

            if(p2.getCurrentHP()==29)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_29.png",250,300,true,true));

            if(p2.getCurrentHP()==28)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_28.png",250,300,true,true));

            if(p2.getCurrentHP()==27)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_27.png",250,300,true,true));

            if(p2.getCurrentHP()==26)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_26.png",250,300,true,true));

            if(p2.getCurrentHP()==25)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_25.png",250,300,true,true));

            if(p2.getCurrentHP()==24)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_24.png",250,300,true,true));

            if(p2.getCurrentHP()==23)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_23.png",250,300,true,true));

            if(p2.getCurrentHP()==22)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_22.png",250,300,true,true));

            if(p2.getCurrentHP()==21)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_21.png",250,300,true,true));

            if(p2.getCurrentHP()==20)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_20.png",250,300,true,true));

            if(p2.getCurrentHP()==19)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_19.png",250,300,true,true));

            if(p2.getCurrentHP()==18)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_18.png",250,300,true,true));

            if(p2.getCurrentHP()==17)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_17.png",250,300,true,true));

            if(p2.getCurrentHP()==16)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_16.png",250,300,true,true));

            if(p2.getCurrentHP()==15)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_15.png",250,300,true,true));

            if(p2.getCurrentHP()==14)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_14.png",250,300,true,true));

            if(p2.getCurrentHP()==13)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_13.png",250,300,true,true));

            if(p2.getCurrentHP()==12)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_12.png",250,300,true,true));

            if(p2.getCurrentHP()==11)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_11.png",250,300,true,true));

            if(p2.getCurrentHP()==10)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_10.png",250,300,true,true));

            if(p2.getCurrentHP()==9)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_9.png",250,300,true,true));

            if(p2.getCurrentHP()==8)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_8.png",250,300,true,true));

            if(p2.getCurrentHP()==7)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_7.png",250,300,true,true));

            if(p2.getCurrentHP()==6)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_6.png",250,300,true,true));

            if(p2.getCurrentHP()==5)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_5.png",250,300,true,true));

            if(p2.getCurrentHP()==4)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_4.png",250,300,true,true));

            if(p2.getCurrentHP()==3)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_3.png",250,300,true,true));

            if(p2.getCurrentHP()==2)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_2.png",250,300,true,true));

            if(p2.getCurrentHP()==1)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_1.png",250,300,true,true));

            if(p2.getCurrentHP()==0)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_0.png",250,300,true,true));

        }

        if(p2 instanceof Priest){
            if(p2.getCurrentHP()==30)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_30.png",250,300,true,true));

            if(p2.getCurrentHP()==29)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_29.png",250,300,true,true));

            if(p2.getCurrentHP()==28)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_28.png",250,300,true,true));

            if(p2.getCurrentHP()==27)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_27.png",250,300,true,true));

            if(p2.getCurrentHP()==26)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_26.png",250,300,true,true));

            if(p2.getCurrentHP()==25)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_25.png",250,300,true,true));

            if(p2.getCurrentHP()==24)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_24.png",250,300,true,true));

            if(p2.getCurrentHP()==23)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_23.png",250,300,true,true));

            if(p2.getCurrentHP()==22)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_22.png",250,300,true,true));

            if(p2.getCurrentHP()==21)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_21.png",250,300,true,true));

            if(p2.getCurrentHP()==20)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_20.png",250,300,true,true));

            if(p2.getCurrentHP()==19)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_19.png",250,300,true,true));

            if(p2.getCurrentHP()==18)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_18.png",250,300,true,true));

            if(p2.getCurrentHP()==17)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_17.png",250,300,true,true));

            if(p2.getCurrentHP()==16)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_16.png",250,300,true,true));

            if(p2.getCurrentHP()==15)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_15.png",250,300,true,true));

            if(p2.getCurrentHP()==14)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_14.png",250,300,true,true));

            if(p2.getCurrentHP()==13)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_13.png",250,300,true,true));

            if(p2.getCurrentHP()==12)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_12.png",250,300,true,true));

            if(p2.getCurrentHP()==11)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_11.png",250,300,true,true));

            if(p2.getCurrentHP()==10)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_10.png",250,300,true,true));

            if(p2.getCurrentHP()==9)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_9.png",250,300,true,true));

            if(p2.getCurrentHP()==8)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_8.png",250,300,true,true));

            if(p2.getCurrentHP()==7)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_7.png",250,300,true,true));

            if(p2.getCurrentHP()==6)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_6.png",250,300,true,true));

            if(p2.getCurrentHP()==5)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_5.png",250,300,true,true));

            if(p2.getCurrentHP()==4)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_4.png",250,300,true,true));

            if(p2.getCurrentHP()==3)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_3.png",250,300,true,true));

            if(p2.getCurrentHP()==2)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_2.png",250,300,true,true));

            if(p2.getCurrentHP()==1)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_1.png",250,300,true,true));

            if(p2.getCurrentHP()==0)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_0.png",250,300,true,true));


        }
        if(p2 instanceof Warlock){
            if(p2.getCurrentHP()==30)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_30.png",250,300,true,true));

            if(p2.getCurrentHP()==29)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_29.png",250,300,true,true));

            if(p2.getCurrentHP()==28)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_28.png",250,300,true,true));

            if(p2.getCurrentHP()==27)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_27.png",250,300,true,true));

            if(p2.getCurrentHP()==26)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_26.png",250,300,true,true));

            if(p2.getCurrentHP()==25)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_25.png",250,300,true,true));

            if(p2.getCurrentHP()==24)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_24.png",250,300,true,true));

            if(p2.getCurrentHP()==23)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_23.png",250,300,true,true));

            if(p2.getCurrentHP()==22)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_22.png",250,300,true,true));

            if(p2.getCurrentHP()==21)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_21.png",250,300,true,true));

            if(p2.getCurrentHP()==20)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_20.png",250,300,true,true));

            if(p2.getCurrentHP()==19)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_19.png",250,300,true,true));

            if(p2.getCurrentHP()==18)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_18.png",250,300,true,true));

            if(p2.getCurrentHP()==17)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_17.png",250,300,true,true));

            if(p2.getCurrentHP()==16)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_16.png",250,300,true,true));

            if(p2.getCurrentHP()==15)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_15.png",250,300,true,true));

            if(p2.getCurrentHP()==14)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_14.png",250,300,true,true));

            if(p2.getCurrentHP()==13)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_13.png",250,300,true,true));

            if(p2.getCurrentHP()==12)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_12.png",250,300,true,true));

            if(p2.getCurrentHP()==11)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_11.png",250,300,true,true));

            if(p2.getCurrentHP()==10)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_10.png",250,300,true,true));

            if(p2.getCurrentHP()==9)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_9.png",250,300,true,true));

            if(p2.getCurrentHP()==8)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_8.png",250,300,true,true));

            if(p2.getCurrentHP()==7)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_7.png",250,300,true,true));

            if(p2.getCurrentHP()==6)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_6.png",250,300,true,true));

            if(p2.getCurrentHP()==5)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_5.png",250,300,true,true));

            if(p2.getCurrentHP()==4)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_4.png",250,300,true,true));

            if(p2.getCurrentHP()==3)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_3.png",250,300,true,true));

            if(p2.getCurrentHP()==2)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_2.png",250,300,true,true));

            if(p2.getCurrentHP()==1)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_1.png",250,300,true,true));

            if(p2.getCurrentHP()==0)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_0.png",250,300,true,true));


        }
        if(p2 instanceof Hunter) {
            if (p2.getCurrentHP() == 30)
                p2Icon.setImage(new Image("images\\rexxar\\Rexxar_30.png", 250, 300, true, true));

            if (p2.getCurrentHP() == 29)
                p2Icon.setImage(new Image("images\\rexxar\\Rexxar_29.png", 250, 300, true, true));

            if (p2.getCurrentHP() == 28)
                p2Icon.setImage(new Image("images\\rexxar\\Rexxar_28.png", 250, 300, true, true));

            if (p2.getCurrentHP() == 27)
                p2Icon.setImage(new Image("images\\rexxar\\Rexxar_27.png", 250, 300, true, true));

            if (p2.getCurrentHP() == 26)
                p2Icon.setImage(new Image("images\\rexxar\\Rexxar_26.png", 250, 300, true, true));

            if (p2.getCurrentHP() == 25)
                p2Icon.setImage(new Image("images\\rexxar\\Rexxar_25.png", 250, 300, true, true));

            if (p2.getCurrentHP() == 24)
                p2Icon.setImage(new Image("images\\rexxar\\Rexxar_24.png", 250, 300, true, true));

            if (p2.getCurrentHP() == 23)
                p2Icon.setImage(new Image("images\\rexxar\\Rexxar_23.png", 250, 300, true, true));

            if (p2.getCurrentHP() == 22)
                p2Icon.setImage(new Image("images\\rexxar\\Rexxar_22.png", 250, 300, true, true));

            if (p2.getCurrentHP() == 21)
                p2Icon.setImage(new Image("images\\rexxar\\Rexxar_21.png", 250, 300, true, true));

            if (p2.getCurrentHP() == 20)
                p2Icon.setImage(new Image("images\\rexxar\\Rexxar_20.png", 250, 300, true, true));

            if (p2.getCurrentHP() == 19)
                p2Icon.setImage(new Image("images\\rexxar\\Rexxar_19.png", 250, 300, true, true));

            if (p2.getCurrentHP() == 18)
                p2Icon.setImage(new Image("images\\rexxar\\Rexxar_18.png", 250, 300, true, true));

            if (p2.getCurrentHP() == 17)
                p2Icon.setImage(new Image("images\\rexxar\\Rexxar_17.png", 250, 300, true, true));

            if (p2.getCurrentHP() == 16)
                p2Icon.setImage(new Image("images\\rexxar\\Rexxar_16.png", 250, 300, true, true));

            if (p2.getCurrentHP() == 15)
                p2Icon.setImage(new Image("images\\rexxar\\Rexxar_15.png", 250, 300, true, true));

            if (p2.getCurrentHP() == 14)
                p2Icon.setImage(new Image("images\\rexxar\\Rexxar_14.png", 250, 300, true, true));

            if (p2.getCurrentHP() == 13)
                p2Icon.setImage(new Image("images\\rexxar\\Rexxar_13.png", 250, 300, true, true));

            if (p2.getCurrentHP() == 12)
                p2Icon.setImage(new Image("images\\rexxar\\Rexxar_12.png", 250, 300, true, true));

            if (p2.getCurrentHP() == 11)
                p2Icon.setImage(new Image("images\\rexxar\\Rexxar_11.png", 250, 300, true, true));

            if (p2.getCurrentHP() == 10)
                p2Icon.setImage(new Image("images\\rexxar\\Rexxar_10.png", 250, 300, true, true));

            if (p2.getCurrentHP() == 9)
                p2Icon.setImage(new Image("images\\rexxar\\Rexxar_9.png", 250, 300, true, true));

            if (p2.getCurrentHP() == 8)
                p2Icon.setImage(new Image("images\\rexxar\\Rexxar_8.png", 250, 300, true, true));

            if (p2.getCurrentHP() == 7)
                p2Icon.setImage(new Image("images\\rexxar\\Rexxar_7.png", 250, 300, true, true));

            if (p2.getCurrentHP() == 6)
                p2Icon.setImage(new Image("images\\rexxar\\Rexxar_6.png", 250, 300, true, true));

            if (p2.getCurrentHP() == 5)
                p2Icon.setImage(new Image("images\\rexxar\\Rexxar_5.png", 250, 300, true, true));

            if (p2.getCurrentHP() == 4)
                p2Icon.setImage(new Image("images\\rexxar\\Rexxar_4.png", 250, 300, true, true));

            if (p2.getCurrentHP() == 3)
                p2Icon.setImage(new Image("images\\rexxar\\Rexxar_3.png", 250, 300, true, true));

            if (p2.getCurrentHP() == 2)
                p2Icon.setImage(new Image("images\\rexxar\\Rexxar_2.png", 250, 300, true, true));

            if (p2.getCurrentHP() == 1)
                p2Icon.setImage(new Image("images\\rexxar\\Rexxar_1.png", 250, 300, true, true));

            if (p2.getCurrentHP() == 0)
                p2Icon.setImage(new Image("images\\rexxar\\Rexxar_0.png", 250, 300, true, true));


        }

    }




}
