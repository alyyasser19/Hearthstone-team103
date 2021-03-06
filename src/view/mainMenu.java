//A TINY THING! HEY THERE ;)
//one more thing
package view;

import engine.Game;
import engine.GameListener;
import exceptions.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.cards.Card;
import model.cards.minions.Icehowl;
import model.cards.minions.Minion;
import model.cards.spells.*;
import model.heroes.*;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class mainMenu extends Application implements GameListener {

    public static void main(String[] args) {
        launch(args);
    }

    private Stage titleScreen;
    private Scene menu;
    private Scene characterSelect;
    private BorderPane p1Area;
    private BorderPane p2Area;


    //Game Classes
    Hero selectedHero;
    Hero p1;
    Hero p2;
    Game game;
    Minion attacker;
    Minion minionTarget;
    Hero heroTarget;

    int frustration;

    boolean selected;
    boolean gameStart;
    boolean mute=false;
    boolean Targeting= false;
    boolean handler;
    boolean minionTargeted;
    boolean heroTargeted;
    boolean play=false;

    Clip Fx;
    Clip Voice;
    Clip clip;

    private ImageView selectedCharacter;
    ImageView end;
    ImageView p1Icon=null;
    ImageView p2Icon=null;
    ImageView p1Power;
    ImageView p2Power;
    Button p1Mana;
    Button p2Mana;
    //Buttons
    HBox p1hand= new HBox();
    HBox p2hand= new HBox();
    HBox oppHand=new HBox();
    FlowPane p1Field=new FlowPane();
    FlowPane p2Field= new FlowPane();

    Stage stage;
    Scene gamescene;

    public void start(Stage primaryStage) {
        titleScreen= new Stage();
        primaryStage=titleScreen;
        titleScreen.setResizable(false);
        titleScreen.setTitle("hearthstone team 103");
        titleScreen.show();
        Image sss= new Image("images\\start.png",380,100,false,false);
        ImageView start= new ImageView(sss);
        ImageView Mute= new ImageView(new Image("images\\Mute.png",380,100,false,false));
        ImageView Exit= new ImageView(new Image("images\\Exit.png",380,100,false,false));
        Exit.setX(440);
        Exit.setY(478);
        Mute.setX(436);
        Mute.setY(364);
        start.setX(436);
        start.setY(244);
        Exit.setOnMouseEntered(e->{Exit.setEffect(new InnerShadow(50,Color.WHITESMOKE));
            playOnce("sounds/Hover.wav");});
        Exit.setOnMouseExited(e->{Exit.setEffect(new InnerShadow(0,Color.WHITESMOKE));});
        Exit.setOnMouseClicked(e->{titleScreen.close();
            playOnce("sounds/Accept.wav");});
        Mute.setOnMouseEntered(e->{Mute.setEffect(new InnerShadow(50,Color.WHITESMOKE));
            playOnce("sounds/Hover.wav");});
        Mute.setOnMouseExited(e->{Mute.setEffect(new InnerShadow(0,Color.WHITESMOKE));});
        Mute.setOnMouseClicked(e->{playOnce("sounds/Accept.wav");
            if(mute==false){
                clip.stop();
                mute=true;}
            else{
                music("sounds/menu.wav");
                mute=false;}
        });
        start.setOnMouseEntered(e->{start.setEffect(new InnerShadow(50,Color.WHITESMOKE));
            playOnce("sounds/Hover.wav");});
        start.setOnMouseExited(e->{start.setEffect(new InnerShadow(0,Color.WHITESMOKE));});
        //Button start= new Button();
        //start.setText("Start");
        //start.setPrefSize(200,50);
        titleScreen.sizeToScene();
        AnchorPane main= new AnchorPane();
        Image Bg= new Image("images/main menu.jpg");
        ImageView a= new ImageView(Bg);
        a.fitWidthProperty().bind(main.widthProperty());
        a.fitHeightProperty().bind(main.heightProperty());
        main.getChildren().add(a);
        main.getChildren().add(start);
        main.getChildren().add(Mute);
        main.getChildren().add(Exit);
        menu= new Scene(main,1200, 720);
        menu.setCursor(new ImageCursor(new Image("images\\mouse.png",250,250,true,true)));
        titleScreen.setScene(menu);
        music("sounds/menu.wav");
        BorderPane wholeScreen= new BorderPane();
        wholeScreen.getChildren().add(new ImageView(new Image("images/Background.png",1200,720,false,false)));
        GridPane characters= new GridPane();
        characters.setPrefSize(600,720);
        Image h=new Image("images\\Rexxar.gif",250,250,true,true);
        ImageView hunter= new ImageView(h);
        hunter.setOnMouseEntered(e->{hunter.setEffect(new InnerShadow(50,Color.WHITESMOKE));
            playOnce("sounds/Hover.wav");});
        hunter.setOnMouseExited(e->{hunter.setEffect(new InnerShadow(0,Color.WHITESMOKE));});
        hunter.setOnMouseClicked(e->playOnce("sounds/Accept.wav"));
        Image m=new Image("images\\JainaProudmoore.gif",250,250,true,true);
        ImageView mage= new ImageView(m);
        mage.setOnMouseEntered(e->{mage.setEffect(new InnerShadow(50,Color.WHITESMOKE));
            playOnce("sounds/Hover.wav");});
        mage.setOnMouseExited(e->{mage.setEffect(new InnerShadow(0,Color.WHITESMOKE));});
        mage.setOnMouseClicked(e->playOnce("sounds/Accept.wav"));
        Image pa=new Image("images\\UtherLightbringer.gif",250,250,true,true);
        ImageView paladin= new ImageView(pa);
        paladin.setOnMouseEntered(e->{paladin.setEffect(new InnerShadow(50,Color.WHITESMOKE));
            playOnce("sounds/Hover.wav");});
        paladin.setOnMouseExited(e->{paladin.setEffect(new InnerShadow(0,Color.WHITESMOKE));});
        Image p=new Image("images\\AnduinWrynn.gif",250,250,true,true);
        ImageView priest= new ImageView(p);
        priest.setOnMouseEntered(e->{priest.setEffect(new InnerShadow(50,Color.WHITESMOKE));
            playOnce("sounds/Hover.wav");});
        priest.setOnMouseExited(e->{priest.setEffect(new InnerShadow(0,Color.WHITESMOKE));});
        Image w=new Image("images\\Guldan.gif",250,250,true,true);
        ImageView warlock= new ImageView(w);
        warlock.setOnMouseEntered(e->{warlock.setEffect(new InnerShadow(50,Color.WHITESMOKE));
            playOnce("sounds/Hover.wav");});
        warlock.setOnMouseExited(e->{warlock.setEffect(new InnerShadow(0,Color.WHITESMOKE));});
        Image s= new Image("images\\select.png",140,140,false,false);
        ImageView select= new ImageView(s);
        select.setOnMouseEntered(e->{select.setEffect(new InnerShadow(50,Color.WHITESMOKE));
            playOnce("sounds/Hover.wav");});
        select.setOnMouseExited(e->{select.setEffect(new InnerShadow(0,Color.WHITESMOKE));});
        characters.setHgap(6.5);
        characters.setVgap(30);
        characters.add(mage,10,1);
        characters.add(hunter,10,2);
        characters.add(priest,11,1);
        characters.add(paladin,11,2);
        characters.add(warlock,12,1);
        characters.add(select,12,2);
        StackPane left= new StackPane();
        left.setPrefSize(300,720);
        StackPane right= new StackPane();
        right.setPrefSize(300,720);
        ImageView x= new ImageView(new Image("images\\p1Choose.PNG",1200,100,false,false));
        //x.setFont(new javafx.scene.text.Font("Algerian",36));
        StackPane sp= new StackPane();
        sp.setMaxSize(1200,100);
        sp.getChildren().add(x);
        wholeScreen.setTop(sp);
        wholeScreen.setLeft(left);
        wholeScreen.setRight(right);
        wholeScreen.setCenter(characters);
        characterSelect= new Scene(wholeScreen,1200,720);
        characterSelect.setCursor(new ImageCursor(new Image("images\\mouse.png",250,250,true,true)));
        GridPane gameScreen= new GridPane();
        Scene game= new Scene(gameScreen);
        game.setCursor(new ImageCursor(new Image("images\\mouse.png",250,250,true,true)));
        BackgroundImage myBI= new BackgroundImage(new Image("images/board.png"),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(titleScreen.getWidth(),titleScreen.getHeight(), false, false, true, false));
        gameScreen.setBackground(new Background(myBI));
        wholeScreen.setPrefSize(1200,720);

        start.setOnMouseClicked(e1-> {
            playOnce("sounds/Accept.wav");
            start.setEffect(new InnerShadow(50,Color.BLACK));
            titleScreen.setScene(characterSelect);
        });

        selected=false;
        mage.setOnMouseClicked(e2->{
            try {
                selectedHero= new Mage();
                playOnceVoice("sounds/jaina.wav");
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
                playOnceVoice("sounds/guldan.wav");
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
                playOnceVoice("sounds/uther.wav");
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
                playOnceVoice("sounds/aunduin.wav");
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
                playOnceVoice("sounds/rexxar.wav");
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
            playOnce("sounds/Accept.wav");

            if(selected) {
                if(selectedHero==null){                try {
                    throw new noHeroSelectedException("Please Select a Hero");
                } catch (exceptions.noHeroSelectedException noHeroSelectedException) {
                    exceptionWindow(noHeroSelectedException);
                    noHeroSelectedException.printStackTrace();
                }

                }
                if(selectedHero!=null){
                    p2 = selectedHero;
                    clip.stop();
                    gameStart=true;
                    titleScreen.close();
                    try {
                        game();
                    } catch (FullHandException e) {
                        e.printStackTrace();
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }}
            }
            if(!selected){
                if(selectedHero==null){
                    try {
                        throw new noHeroSelectedException("Please Select a Hero");
                    } catch (exceptions.noHeroSelectedException noHeroSelectedException) {
                        exceptionWindow(noHeroSelectedException);
                        noHeroSelectedException.printStackTrace();
                    }}
                if(selectedHero!=null){
                    p1= selectedHero;
                    selected=true;
                    x.setImage(new Image("images\\p2Choose.PNG",1200,100,false,false));
                    //x2.setFont(new javafx.scene.text.Font("Algerian",36));

                    selectedHero=null;
                }


            }}
        );




    }

    public void music(String filepath) {
        try {
            AudioInputStream a = AudioSystem.getAudioInputStream(new File(filepath).getAbsoluteFile());
            clip= AudioSystem.getClip();
            clip.open(a);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-6.5f);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }





    public void playerDraw(HBox phand,FlowPane pfield,Hero p,Hero pOther,FlowPane pOtherField){
        for(Card cur: p.getHand() ) {
            ImageView a= new ImageView(new Image(getImage(cur),130,200,false,true));
            a.setOnMouseEntered(e->{if(cur.getManaCost()<=p.getCurrentManaCrystals() && p==game.getCurrentHero())
                                    a.setEffect(new InnerShadow(50,Color.GREEN));
                                    else
                                    a.setEffect(new InnerShadow(50,Color.RED));});
            a.setOnMouseExited(e->a.setEffect(new InnerShadow(0,Color.WHITESMOKE)));
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
                    playOnce("sounds/card.wav");
                    verifyMana();
                    finalA5.setOnMouseEntered(enter->{
                        if(Targeting && finalA5.getListener()!=game.getCurrentHero()){
                            finalA5.setEffect(new InnerShadow(100,Color.RED));
                            playOnce("sounds/twitch.wav");
                        }
                    });

                    finalA5.setOnMouseExited(exit->{
                        if(Targeting && finalA5.getListener()!=game.getCurrentHero())
                        finalA5.verifyMinion();

                    });
                    finalA5.setOnMouseClicked(ee->{
                        if(Targeting && finalA5.getListener()!=game.getCurrentHero()){
                            Targeting=false;
                            try {
                                game.getCurrentHero().attackWithMinion(attacker,finalA5.getMinion());
                            } catch (CannotAttackException cannotAttackException) {
                                cannotAttackException.printStackTrace();
                                exceptionWindow(cannotAttackException);
                                gamescene.setCursor(new ImageCursor(new Image("images\\attack.png",200,200,false,false)));
                                finalA5.verifyMinion();

                                return;
                            } catch (NotYourTurnException notYourTurnException) {
                                notYourTurnException.printStackTrace();
                                exceptionWindow(notYourTurnException);
                                gamescene.setCursor(new ImageCursor(new Image("images\\attack.png",200,200,false,false)));
                                finalA5.verifyMinion();


                                return;
                            } catch (TauntBypassException tauntBypassException) {
                                tauntBypassException.printStackTrace();
                                exceptionWindow(tauntBypassException);
                                gamescene.setCursor(new ImageCursor(new Image("images\\attack.png",200,200,false,false)));
                                finalA5.verifyMinion();


                                return;
                            } catch (InvalidTargetException invalidTargetException) {
                                invalidTargetException.printStackTrace();
                                exceptionWindow(invalidTargetException);
                                Targeting=false;

                                return;
                            } catch (NotSummonedException notSummonedException) {
                                notSummonedException.printStackTrace();
                                exceptionWindow(notSummonedException);
                                gamescene.setCursor(new ImageCursor(new Image("images\\attack.png",200,200,false,false)));
                                finalA5.verifyMinion();

                                return;
                            }
                            playOnce("sounds/attack.wav");
                            p1VerifyMinions();
                            p2VerifyMinions();
                            gamescene.setCursor(new ImageCursor(new Image("images\\mouse.png")));
                        }
                        else{
                            finalA5.setEffect(new InnerShadow(100, Color.GREEN));
                            playOnce("sounds/sattack.wav");
                            Targeting=true;
                            attacker=finalA5.getMinion();
                            gamescene.setCursor(new ImageCursor(new Image("images\\attack.png",200,200,false,false)));
                        }
                    });
                    pfield.getChildren().add(finalA5);

                });

            }
            if (cur instanceof Spell) {
                Tooltip t = new Tooltip(((Spell) cur).getDescription());
                Tooltip.install(a, t);
                t.setMinSize(50,50);
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
                        playOnce("sounds/card.wav");
                        phand.getChildren().remove(a);
                        verifyMana();
                        verifyHeroP2();
                        verifyHeroP1();
                        p2VerifyMinions();
                        p1VerifyMinions();
                        verifyMana();
                        verifyMana();
                        verifyHeroP2();
                        verifyHeroP1();
                        p2VerifyMinions();
                        p1VerifyMinions();
                        System.out.println("works?");

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
                        playOnce("sounds/card.wav");
                        System.out.println("works?");
                        verifyMana();
                        p1VerifyMinions();
                        p2VerifyMinions();
                        verifyHeroP1();
                        verifyHeroP2();
                        verifyMana();
                        verifyHeroP2();
                        verifyHeroP1();
                        p2VerifyMinions();
                        p1VerifyMinions();


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
                        playOnce("sounds/card.wav");
                        verifyMana();
                        verifyHeroP2();
                        verifyHeroP1();
                        p2VerifyMinions();
                        p1VerifyMinions();
                        phand.getChildren().remove(a);
                        verifyMana();
                        verifyMana();
                        verifyMana();
                        verifyHeroP2();
                        verifyHeroP1();
                        p2VerifyMinions();
                        p1VerifyMinions();
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
                        scene.setCursor(new ImageCursor(new Image("images\\mouse.png",250,250,true,true)));
                        oppField.setPrefSize(1500,700);
                        BackgroundImage b= new BackgroundImage(new Image("images/spellsBG.jpg"),
                                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                                new BackgroundSize(oppField.getWidth(),oppField.getHeight(), false, false, true, false));
                        oppField.setBackground(new Background(b));
                        s1.setScene(scene);
                        Label opp=new Label("opponent");
                        opp.setTextFill(Color.web("#FFD700"));
                        oppField.getChildren().add(opp);
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
                                    playOnce("sounds/card.wav");
                                    phand.getChildren().remove(a);
                                    verifyMana();
                                    verifyHeroP2();
                                    verifyHeroP1();
                                    verifyMana();
                                    verifyMana();
                                    verifyHeroP2();
                                    verifyHeroP1();
                                    p2VerifyMinions();
                                    p1VerifyMinions();
                                }});
                            oppField.getChildren().add(opponent);}
                        for(Node curr:pOtherField.getChildren()){
                            if(!curr.isVisible() || curr instanceof ImageView)
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
                                    verifyMana();
                                    verifyMana();
                                    verifyHeroP2();
                                    verifyHeroP1();
                                    p2VerifyMinions();
                                    p1VerifyMinions();
                                }

                                ;});
                        }
                        Label ally= new Label("Friendly");
                        ally.setTextFill(Color.web("#FFD700"));
                        oppField.getChildren().add(ally);
                        if(cur instanceof HeroTargetSpell){
                            ImageView hero= null;
                            if(p instanceof Mage)
                                hero=new ImageView(new Image("images\\Jaina_Proudmoore.png",250,250,true,true));
                            if(p instanceof Warlock)
                                hero=new ImageView(new Image("images\\Guldan.png",250,250,true,true));
                            if(p instanceof Priest)
                                hero=new ImageView(new Image("images\\Anduin_Wrynn.png",250,250,true,true));
                            if(p instanceof Paladin)
                                hero=new ImageView(new Image("images\\Uther_Lightbringer.png",250,250,true,true));
                            if(p instanceof Hunter)
                                hero=new ImageView(new Image("images\\Rexxar.png",250,250,true,true));
                            oppField.getChildren().add(hero);
                            hero.setOnMouseClicked(ee->{
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
                                    verifyMana();
                                    verifyHeroP2();
                                    verifyHeroP1();
                                    verifyMana();
                                    verifyMana();
                                    verifyHeroP2();
                                    verifyHeroP1();
                                    p2VerifyMinions();
                                    p1VerifyMinions();
                                }});}
                        for(Node curr:pfield.getChildren()){
                            if(!curr.isVisible() || curr instanceof ImageView)
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
                                    verifyMana();
                                    verifyMana();
                                    verifyHeroP2();
                                    verifyHeroP1();
                                    p2VerifyMinions();
                                    p1VerifyMinions();
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
                        scene.setCursor(new ImageCursor(new Image("images\\mouse.png",250,250,true,true)));
                        oppField.setPrefSize(1500,700);
                        BackgroundImage b= new BackgroundImage(new Image("images/spellsBG.jpg"),
                                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                                new BackgroundSize(oppField.getWidth(),oppField.getHeight(), false, false, true, false));
                        oppField.setBackground(new Background(b));
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
                                    playOnce("sounds/card.wav");
                                    phand.getChildren().remove(a);
                                    verifyMana();
                                    verifyHeroP2();
                                    verifyHeroP1();
                                }});
                            oppField.getChildren().add(opponent);}
                        for(Node curr:pOtherField.getChildren()){
                            if(!curr.isVisible() || curr instanceof ImageView)
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
                                        if(p==p1){
                                            p1Mana.setText("Mana:"+ p.getCurrentHP()+"\nCardsleft"+p.getHand().size());
                                        }
                                        if(p==p2){
                                            p2Mana.setText("Mana:"+ p.getCurrentHP()+"\nCardsleft"+p.getHand().size());
                                        }
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
                                    playOnce("sounds/card.wav");
                                    phand.getChildren().remove(a);
                                    verifyMana();
                                    verifyMana();
                                    verifyHeroP2();
                                    verifyHeroP1();
                                    p2VerifyMinions();
                                    p1VerifyMinions();
                                }



                            });
                        }
                    });
                }
            }
            if (cur instanceof Spell){
            Tooltip t=new Tooltip(((Spell) cur).getDescription());
            Tooltip.install(a,t);
            }
            phand.getChildren().add(a);
        }
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
            return "images\\cards\\CurseOfWeakness.png";

        if(cur.getName().equals("Divine Spirit"))
            return "images\\cards\\DivineSpirit.png";

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
            return "images\\cards\\TwistingNether.png";

        if(cur.getName().equals("Sheep"))
            return "images\\cards\\Sheep.png";

        return "images\\whitepage.png";
    }
    public void endTurnDraw(Hero p,Hero pOther,HBox phand,FlowPane pfield,FlowPane pOtherField){
        Card cur= p.getHand().get(p.getHand().size()-1);
        System.out.println(p.getHand());
        ImageView a= new ImageView(new Image(getImage(cur),130,200,false,true));
        a.setOnMouseEntered(e->{if(cur.getManaCost()<=p.getCurrentManaCrystals()  && p==game.getCurrentHero())
            a.setEffect(new InnerShadow(50,Color.GREEN));
        else
            a.setEffect(new InnerShadow(50,Color.RED));});
        a.setOnMouseExited(e->a.setEffect(new InnerShadow(0,Color.WHITESMOKE)));
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
                playOnce("sounds/card.wav");
                verifyMana();
                finalA5.setOnMouseEntered(enter->{
                    if(Targeting && finalA5.getListener()!=game.getCurrentHero()){
                        finalA5.setEffect(new InnerShadow(100,Color.RED));
                        playOnce("sounds/twitch.wav");
                    }
                });

                finalA5.setOnMouseExited(exit->{
                    if(Targeting && finalA5.getListener()!=game.getCurrentHero())
                        finalA5.verifyMinion();

                });
                finalA5.setOnMouseClicked(ee->{
                    if(Targeting && finalA5.getListener()!=game.getCurrentHero()){
                        Targeting=false;
                        try {
                            game.getCurrentHero().attackWithMinion(attacker,finalA5.getMinion());
                        } catch (CannotAttackException cannotAttackException) {
                            cannotAttackException.printStackTrace();
                            exceptionWindow(cannotAttackException);
                            gamescene.setCursor(new ImageCursor(new Image("images\\attack.png",200,200,false,false)));
                            finalA5.verifyMinion();

                            return;
                        } catch (NotYourTurnException notYourTurnException) {
                            notYourTurnException.printStackTrace();
                            exceptionWindow(notYourTurnException);
                            gamescene.setCursor(new ImageCursor(new Image("images\\attack.png",200,200,false,false)));
                            finalA5.verifyMinion();


                            return;
                        } catch (TauntBypassException tauntBypassException) {
                            tauntBypassException.printStackTrace();
                            exceptionWindow(tauntBypassException);
                            gamescene.setCursor(new ImageCursor(new Image("images\\attack.png",200,200,false,false)));
                            finalA5.verifyMinion();


                            return;
                        } catch (InvalidTargetException invalidTargetException) {
                            invalidTargetException.printStackTrace();
                            exceptionWindow(invalidTargetException);
                            Targeting=false;

                            return;
                        } catch (NotSummonedException notSummonedException) {
                            notSummonedException.printStackTrace();
                            exceptionWindow(notSummonedException);
                            gamescene.setCursor(new ImageCursor(new Image("images\\attack.png",200,200,false,false)));
                            finalA5.verifyMinion();

                            return;
                        }
                        playOnce("sounds/attack.wav");
                        p1VerifyMinions();
                        p2VerifyMinions();
                        gamescene.setCursor(new ImageCursor(new Image("images\\mouse.png")));
                    }
                    else{
                        finalA5.setEffect(new InnerShadow(100, Color.GREEN));
                        playOnce("sounds/sattack.wav");
                        Targeting=true;
                        attacker=finalA5.getMinion();
                        gamescene.setCursor(new ImageCursor(new Image("images\\attack.png",200,200,false,false)));
                    }
                });
                pfield.getChildren().add(finalA5);

            });

        }
        if (cur instanceof Spell) {
            Tooltip t = new Tooltip(((Spell) cur).getDescription());
            t.setShowDelay(Duration.ONE);
            Tooltip.install(a, t);
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
                    playOnce("sounds/card.wav");
                    System.out.println("works?");
                    verifyMana();
                    verifyHeroP2();
                    verifyHeroP1();
                    p2VerifyMinions();
                    p1VerifyMinions();
                    verifyMana();
                    verifyMana();
                    verifyHeroP2();
                    verifyHeroP1();
                    p2VerifyMinions();
                    p1VerifyMinions();
                    phand.getChildren().remove(a);
                    playOnce("sounds/card.wav");
                    verifyMana();});
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

                    phand.getChildren().remove(a);
                    playOnce("sounds/card.wav");
                    verifyMana();
                    p1VerifyMinions();
                    p2VerifyMinions();
                    verifyHeroP1();
                    verifyHeroP2();
                    verifyMana();
                    p1VerifyMinions();
                    p2VerifyMinions();
                    verifyHeroP1();
                    verifyHeroP2();
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
                    playOnce("sounds/card.wav");
                    verifyMana();
                    System.out.println("works?");
                });
            }
            if (cur instanceof MinionTargetSpell) {

                a.setOnMouseClicked(e -> {
                    Stage s1=new Stage();
                    s1.initModality(Modality.APPLICATION_MODAL);
                    s1.show();
                    FlowPane oppField= new FlowPane();
                    oppField.setPrefSize(1500,700);
                    BackgroundImage b= new BackgroundImage(new Image("images/spellsBG.jpg"),
                            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                            new BackgroundSize(oppField.getWidth(),oppField.getHeight(), false, false, true, false));
                    oppField.setBackground(new Background(b));
                    Label opp=new Label("opponent");
                    opp.setTextFill(Color.web("#FFD700"));
                    oppField.getChildren().add(opp);
                    Scene scene= new Scene(oppField);
                    scene.setCursor(new ImageCursor(new Image("images\\mouse.png",250,250,true,true)));
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
                                verifyMana();
                                phand.getChildren().remove(a);
                                playOnce("sounds/card.wav");
                            }});
                        oppField.getChildren().add(opponent);}
                    for(Node curr:pOtherField.getChildren()){
                        if(!curr.isVisible() || curr instanceof ImageView)
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
                            phand.getChildren().remove(a);
                            playOnce("sounds/card.wav");
                            verifyMana();
                            verifyMana();
                            verifyHeroP2();
                            verifyHeroP1();
                            p2VerifyMinions();
                            p1VerifyMinions();;});
                    }
                    Label ally= new Label("Friendly");
                    ally.setTextFill(Color.web("#FFD700"));
                    oppField.getChildren().add(ally);
                    if(cur instanceof HeroTargetSpell){
                        ImageView hero= null;
                        if(p instanceof Mage)
                            hero=new ImageView(new Image("images\\Jaina_Proudmoore.png",250,250,true,true));
                        if(p instanceof Warlock)
                            hero=new ImageView(new Image("images\\Guldan.png",250,250,true,true));
                        if(p instanceof Priest)
                            hero=new ImageView(new Image("images\\Anduin_Wrynn.png",250,250,true,true));
                        if(p instanceof Paladin)
                            hero=new ImageView(new Image("images\\Uther_Lightbringer.png",250,250,true,true));
                        if(p instanceof Hunter)
                            hero=new ImageView(new Image("images\\Rexxar.png",250,250,true,true));
                        oppField.getChildren().add(hero);
                        hero.setOnMouseClicked(ee->{
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
                                playOnce("sounds/card.wav");
                                verifyMana();
                                verifyHeroP2();
                                verifyHeroP1();
                            }});}
                    for(Node curr:pfield.getChildren()){
                        if(!curr.isVisible() || curr instanceof ImageView)
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
                                playOnce("sounds/card.wav");
                                verifyMana();
                                verifyMana();
                                verifyHeroP2();
                                verifyHeroP1();
                                p2VerifyMinions();
                                p1VerifyMinions();
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
                    oppField.setPrefSize(1500,700);
                    BackgroundImage b= new BackgroundImage(new Image("images/spellsBG.jpg"),
                            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                            new BackgroundSize(oppField.getWidth(),oppField.getHeight(), false, false, true, false));
                    oppField.setBackground(new Background(b));
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
                                verifyMana();
                                verifyHeroP2();
                                verifyHeroP1();
                                p2VerifyMinions();
                                p1VerifyMinions();
                            }});
                        oppField.getChildren().add(opponent);}
                    for(Node curr:pOtherField.getChildren()){
                        if(!curr.isVisible() || curr instanceof ImageView)
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
                                verifyMana();
                                verifyHeroP2();
                                verifyHeroP1();
                                p2VerifyMinions();
                                p1VerifyMinions();
                                phand.getChildren().remove(a);
                                playOnce("sounds/card.wav");
                                verifyMana();
                            }



                        });
                    }
                });
            }
        }
        if (cur instanceof Spell){
            Tooltip t=new Tooltip(((Spell) cur).getDescription());
            t.setShowDelay(Duration.ONE);
            Tooltip.install(a,t);}


        phand.getChildren().add(a);
        verifyMana();
        verifyMana();
        verifyHeroP2();
        verifyHeroP1();
        p2VerifyMinions();
        p1VerifyMinions();
        for (Node curr:pfield.getChildren()){
            if(curr instanceof minionButton)
                if(((minionButton) curr).getMinion().getName().equals("Chromaggus"))
                    if(p.getHand().size()!=10)
                    chroDraw(p,pOther,phand,pfield,pOtherField);
        }
    }
    public void chroDraw(Hero p,Hero pOther,HBox phand,FlowPane pfield,FlowPane pOtherField){
        {
            Card cur= p.getHand().get(p.getHand().size()-2);
            System.out.println(p.getHand());
            ImageView a= new ImageView(new Image(getImage(cur),130,200,false,true));
            a.setOnMouseEntered(e->{if(cur.getManaCost()<=p.getCurrentManaCrystals()  && p==game.getCurrentHero())
                a.setEffect(new InnerShadow(50,Color.GREEN));
            else
                a.setEffect(new InnerShadow(50,Color.RED));});
            a.setOnMouseExited(e->a.setEffect(new InnerShadow(0,Color.WHITESMOKE)));
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
                    playOnce("sounds/card.wav");
                    verifyMana();
                    finalA5.setOnMouseEntered(enter->{
                        if(Targeting && finalA5.getListener()!=game.getCurrentHero()){
                            finalA5.setEffect(new InnerShadow(100,Color.RED));
                            playOnce("sounds/twitch.wav");
                        }
                    });

                    finalA5.setOnMouseExited(exit->{
                        if(Targeting && finalA5.getListener()!=game.getCurrentHero())
                        finalA5.verifyMinion();

                    });
                    finalA5.setOnMouseClicked(ee->{
                        if(Targeting && finalA5.getListener()!=game.getCurrentHero()){
                            Targeting=false;
                            try {
                                game.getCurrentHero().attackWithMinion(attacker,finalA5.getMinion());
                            } catch (CannotAttackException cannotAttackException) {
                                cannotAttackException.printStackTrace();
                                exceptionWindow(cannotAttackException);
                                gamescene.setCursor(new ImageCursor(new Image("images\\attack.png",200,200,false,false)));
                                finalA5.verifyMinion();

                                return;
                            } catch (NotYourTurnException notYourTurnException) {
                                notYourTurnException.printStackTrace();
                                exceptionWindow(notYourTurnException);
                                gamescene.setCursor(new ImageCursor(new Image("images\\attack.png",200,200,false,false)));
                                finalA5.verifyMinion();


                                return;
                            } catch (TauntBypassException tauntBypassException) {
                                tauntBypassException.printStackTrace();
                                exceptionWindow(tauntBypassException);
                                gamescene.setCursor(new ImageCursor(new Image("images\\attack.png",200,200,false,false)));
                                finalA5.verifyMinion();


                                return;
                            } catch (InvalidTargetException invalidTargetException) {
                                invalidTargetException.printStackTrace();
                                exceptionWindow(invalidTargetException);
                                Targeting=false;

                                return;
                            } catch (NotSummonedException notSummonedException) {
                                notSummonedException.printStackTrace();
                                exceptionWindow(notSummonedException);
                                gamescene.setCursor(new ImageCursor(new Image("images\\attack.png",200,200,false,false)));
                                finalA5.verifyMinion();

                                return;
                            }
                            playOnce("sounds/attack.wav");
                            p1VerifyMinions();
                            p2VerifyMinions();
                            gamescene.setCursor(new ImageCursor(new Image("images\\mouse.png")));
                        }
                        else{
                            finalA5.setEffect(new InnerShadow(100, Color.GREEN));
                            playOnce("sounds/sattack.wav");
                            Targeting=true;
                            attacker=finalA5.getMinion();
                            gamescene.setCursor(new ImageCursor(new Image("images\\attack.png",200,200,false,false)));
                        }
                    });
                    pfield.getChildren().add(finalA5);

                });

            }
            if (cur instanceof Spell) {
                Tooltip t = new Tooltip(((Spell) cur).getDescription());
                t.setShowDelay(Duration.ONE);
                Tooltip.install(a, t);
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
                        playOnce("sounds/card.wav");
                        System.out.println("works?");
                        verifyMana();
                        verifyHeroP2();
                        verifyHeroP1();
                        p2VerifyMinions();
                        p1VerifyMinions();
                        verifyMana();
                        verifyMana();
                        verifyHeroP2();
                        verifyHeroP1();
                        p2VerifyMinions();
                        p1VerifyMinions();
                        phand.getChildren().remove(a);
                        playOnce("sounds/card.wav");
                        verifyMana();});
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

                        phand.getChildren().remove(a);
                        playOnce("sounds/card.wav");
                        verifyMana();
                        p1VerifyMinions();
                        p2VerifyMinions();
                        verifyHeroP1();
                        verifyHeroP2();
                        verifyMana();
                        p1VerifyMinions();
                        p2VerifyMinions();
                        verifyHeroP1();
                        verifyHeroP2();
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
                        playOnce("sounds/card.wav");
                        verifyMana();
                        System.out.println("works?");
                    });
                }
                if (cur instanceof MinionTargetSpell) {

                    a.setOnMouseClicked(e -> {
                        Stage s1=new Stage();
                        s1.initModality(Modality.APPLICATION_MODAL);
                        s1.show();
                        FlowPane oppField= new FlowPane();
                        oppField.setPrefSize(1500,700);
                        BackgroundImage b= new BackgroundImage(new Image("images/spellsBG.jpg"),
                                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                                new BackgroundSize(oppField.getWidth(),oppField.getHeight(), false, false, true, false));
                        oppField.setBackground(new Background(b));
                        Label opp=new Label("opponent");
                        opp.setTextFill(Color.web("#FFD700"));
                        oppField.getChildren().add(opp);
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
                                    verifyMana();
                                    phand.getChildren().remove(a);
                                    playOnce("sounds/card.wav");
                                }});
                            oppField.getChildren().add(opponent);}
                        for(Node curr:pOtherField.getChildren()){
                            if(!curr.isVisible() || curr instanceof ImageView)
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
                                phand.getChildren().remove(a);
                                playOnce("sounds/card.wav");
                                verifyMana();
                                verifyMana();
                                verifyHeroP2();
                                verifyHeroP1();
                                p2VerifyMinions();
                                p1VerifyMinions();;});
                        }
                        Label ally= new Label("Friendly");
                        ally.setTextFill(Color.web("#FFD700"));
                        oppField.getChildren().add(ally);
                        if(cur instanceof HeroTargetSpell){
                            ImageView hero= null;
                            if(p instanceof Mage)
                                hero=new ImageView(new Image("images\\Jaina_Proudmoore.png",250,250,true,true));
                            if(p instanceof Warlock)
                                hero=new ImageView(new Image("images\\Guldan.png",250,250,true,true));
                            if(p instanceof Priest)
                                hero=new ImageView(new Image("images\\Anduin_Wrynn.png",250,250,true,true));
                            if(p instanceof Paladin)
                                hero=new ImageView(new Image("images\\Uther_Lightbringer.png",250,250,true,true));
                            if(p instanceof Hunter)
                                hero=new ImageView(new Image("images\\Rexxar.png",250,250,true,true));
                            oppField.getChildren().add(hero);
                            hero.setOnMouseClicked(ee->{
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
                                    playOnce("sounds/card.wav");
                                    verifyMana();
                                    verifyHeroP2();
                                    verifyHeroP1();
                                }});}
                        for(Node curr:pfield.getChildren()){
                            if(!curr.isVisible() || curr instanceof ImageView)
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
                                    playOnce("sounds/card.wav");
                                    verifyMana();
                                    verifyMana();
                                    verifyHeroP2();
                                    verifyHeroP1();
                                    p2VerifyMinions();
                                    p1VerifyMinions();
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
                        oppField.setPrefSize(1500,700);
                        BackgroundImage b= new BackgroundImage(new Image("images/spellsBG.jpg"),
                                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                                new BackgroundSize(oppField.getWidth(),oppField.getHeight(), false, false, true, false));
                        oppField.setBackground(new Background(b));
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
                                    verifyMana();
                                    verifyHeroP2();
                                    verifyHeroP1();
                                    p2VerifyMinions();
                                    p1VerifyMinions();
                                }});
                            oppField.getChildren().add(opponent);}
                        for(Node curr:pOtherField.getChildren()){
                            if(!curr.isVisible() || curr instanceof ImageView)
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
                                    verifyMana();
                                    verifyHeroP2();
                                    verifyHeroP1();
                                    p2VerifyMinions();
                                    p1VerifyMinions();
                                    phand.getChildren().remove(a);
                                    playOnce("sounds/card.wav");
                                    verifyMana();
                                }



                            });
                        }
                    });
                }
            }
            if (cur instanceof Spell){
                Tooltip t=new Tooltip(((Spell) cur).getDescription());
                t.setShowDelay(Duration.ONE);
                Tooltip.install(a,t);}


            phand.getChildren().add(a);
            verifyMana();
            verifyMana();
            verifyHeroP2();
            verifyHeroP1();
            p2VerifyMinions();
            p1VerifyMinions();

        }
    }
    public void exceptionWindow(Exception e){
        if(play){
        gamescene.setCursor(new ImageCursor(new Image("images\\mouse.png")));
        p1VerifyMinions();
        p2VerifyMinions();
        verifyMana();}
        gamescene.setCursor(new ImageCursor(new Image("images\\mouse.png")));
        Stage s1=new Stage();
        s1.initModality(Modality.APPLICATION_MODAL);
        s1.show();
        s1.setMinWidth(300);
        s1.setMinHeight(300);
        VBox v= new VBox();
        BackgroundImage b= new BackgroundImage(new Image("images\\ExceptionBG.jpg"),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(v.getWidth(),v.getHeight(), false, false, true, false));
        v.setBackground(new Background(b));
        Label x=new Label(e.getLocalizedMessage());
        x.setTextFill(Color.web("#FAEBD7"));
        x.setMinSize(200,200);
        v.getChildren().add(x);
        v.setAlignment(Pos.CENTER);
        Scene sc=new Scene(v,300,300);
        Button okay= new Button("OKAY");
        okay.setAlignment(Pos.CENTER);
        okay.setOnMouseClicked(e1-> s1.close());
        v.getChildren().add(okay);
        s1.setScene(sc);
        if(e instanceof FullFieldException){
            playOnce("sounds/fullField.wav");
        }
        if(e instanceof FullHandException){
            playOnce("sounds/fullHand.wav");
        }
        if(e instanceof CannotAttackException){
            if(e.getLocalizedMessage()=="Give this minion a turn to get ready"){
                playOnce("sounds/sleep.wav");}
            else{
                playOnce("sounds/attacked.wav");
            }
        }
        if(e instanceof NotEnoughManaException){
            playOnce("sounds/mana.wav");
        }
        if(e instanceof TauntBypassException){
            playOnce("sounds/Taunt.wav");
        }
        if(e instanceof FullFieldException){
            playOnce("sounds/fullField.wav");
        }
        if(!(e instanceof FullFieldException || e instanceof TauntBypassException || e instanceof NotEnoughManaException || e instanceof CannotAttackException) || e instanceof FullFieldException ||e instanceof FullHandException){
            playOnce("sounds/invalid.wav");
        }
        gamescene.setCursor(new ImageCursor(new Image("images\\mouse.png")));
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
        if(p1==game.getCurrentHero()){
            p1Icon.setEffect(new DropShadow(50, Color.WHITESMOKE));
          }else
            p1Icon.setEffect(new DropShadow(0, Color.DEEPSKYBLUE));

        if(p1 instanceof Mage){
            if(p1.getCurrentHP()==30)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_30.png",250,270,false,false));

            if(p1.getCurrentHP()==29)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_29.png",250,270,false,false));

            if(p1.getCurrentHP()==28)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_28.png",250,270,false,false));

            if(p1.getCurrentHP()==27)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_27.png",250,270,false,false));

            if(p1.getCurrentHP()==26)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_26.png",250,270,false,false));

            if(p1.getCurrentHP()==25)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_25.png",250,270,false,false));

            if(p1.getCurrentHP()==24)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_24.png",250,270,false,false));

            if(p1.getCurrentHP()==23)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_23.png",250,270,false,false));

            if(p1.getCurrentHP()==22)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_22.png",250,270,false,false));

            if(p1.getCurrentHP()==21)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_21.png",250,270,false,false));

            if(p1.getCurrentHP()==20)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_20.png",250,270,false,false));

            if(p1.getCurrentHP()==19)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_19.png",250,270,false,false));

            if(p1.getCurrentHP()==18)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_18.png",250,270,false,false));

            if(p1.getCurrentHP()==17)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_17.png",250,270,false,false));

            if(p1.getCurrentHP()==16)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_16.png",250,270,false,false));

            if(p1.getCurrentHP()==15)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_15.png",250,270,false,false));

            if(p1.getCurrentHP()==14)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_14.png",250,270,false,false));

            if(p1.getCurrentHP()==13)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_13.png",250,270,false,false));

            if(p1.getCurrentHP()==12)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_12.png",250,270,false,false));

            if(p1.getCurrentHP()==11)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_11.png",250,270,false,false));

            if(p1.getCurrentHP()==10)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_10.png",250,270,false,false));

            if(p1.getCurrentHP()==9)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_9.png",250,270,false,false));

            if(p1.getCurrentHP()==8)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_8.png",250,270,false,false));

            if(p1.getCurrentHP()==7)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_7.png",250,270,false,false));

            if(p1.getCurrentHP()==6)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_6.png",250,270,false,false));

            if(p1.getCurrentHP()==5)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_5.png",250,270,false,false));

            if(p1.getCurrentHP()==4)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_4.png",250,270,false,false));

            if(p1.getCurrentHP()==3)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_3.png",250,270,false,false));

            if(p1.getCurrentHP()==2)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_2.png",250,270,false,false));

            if(p1.getCurrentHP()==1)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_1.png",250,270,false,false));

            if(p1.getCurrentHP()==0)
                p1Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_0.png",250,270,false,false));
        }

        if(p1 instanceof Paladin){
            if(p1.getCurrentHP()==30)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_30.png",250,270,false,false));

            if(p1.getCurrentHP()==29)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_29.png",250,270,false,false));

            if(p1.getCurrentHP()==28)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_28.png",250,270,false,false));

            if(p1.getCurrentHP()==27)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_27.png",250,270,false,false));

            if(p1.getCurrentHP()==26)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_26.png",250,270,false,false));

            if(p1.getCurrentHP()==25)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_25.png",250,270,false,false));

            if(p1.getCurrentHP()==24)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_24.png",250,270,false,false));

            if(p1.getCurrentHP()==23)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_23.png",250,270,false,false));

            if(p1.getCurrentHP()==22)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_22.png",250,270,false,false));

            if(p1.getCurrentHP()==21)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_21.png",250,270,false,false));

            if(p1.getCurrentHP()==20)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_20.png",250,270,false,false));

            if(p1.getCurrentHP()==19)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_19.png",250,270,false,false));

            if(p1.getCurrentHP()==18)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_18.png",250,270,false,false));

            if(p1.getCurrentHP()==17)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_17.png",250,270,false,false));

            if(p1.getCurrentHP()==16)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_16.png",250,270,false,false));

            if(p1.getCurrentHP()==15)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_15.png",250,270,false,false));

            if(p1.getCurrentHP()==14)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_14.png",250,270,false,false));

            if(p1.getCurrentHP()==13)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_13.png",250,270,false,false));

            if(p1.getCurrentHP()==12)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_12.png",250,270,false,false));

            if(p1.getCurrentHP()==11)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_11.png",250,270,false,false));

            if(p1.getCurrentHP()==10)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_10.png",250,270,false,false));

            if(p1.getCurrentHP()==9)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_9.png",250,270,false,false));

            if(p1.getCurrentHP()==8)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_8.png",250,270,false,false));

            if(p1.getCurrentHP()==7)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_7.png",250,270,false,false));

            if(p1.getCurrentHP()==6)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_6.png",250,270,false,false));

            if(p1.getCurrentHP()==5)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_5.png",250,270,false,false));

            if(p1.getCurrentHP()==4)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_4.png",250,270,false,false));

            if(p1.getCurrentHP()==3)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_3.png",250,270,false,false));

            if(p1.getCurrentHP()==2)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_2.png",250,270,false,false));

            if(p1.getCurrentHP()==1)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_1.png",250,270,false,false));

            if(p1.getCurrentHP()==0)
                p1Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_0.png",250,270,false,false));

        }

        if(p1 instanceof Priest){
            if(p1.getCurrentHP()==30)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_30.png",250,270,false,false));

            if(p1.getCurrentHP()==29)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_29.png",250,270,false,false));

            if(p1.getCurrentHP()==28)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_28.png",250,270,false,false));

            if(p1.getCurrentHP()==27)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_27.png",250,270,false,false));

            if(p1.getCurrentHP()==26)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_26.png",250,270,false,false));

            if(p1.getCurrentHP()==25)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_25.png",250,270,false,false));

            if(p1.getCurrentHP()==24)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_24.png",250,270,false,false));

            if(p1.getCurrentHP()==23)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_23.png",250,270,false,false));

            if(p1.getCurrentHP()==22)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_22.png",250,270,false,false));

            if(p1.getCurrentHP()==21)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_21.png",250,270,false,false));

            if(p1.getCurrentHP()==20)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_20.png",250,270,false,false));

            if(p1.getCurrentHP()==19)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_19.png",250,270,false,false));

            if(p1.getCurrentHP()==18)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_18.png",250,270,false,false));

            if(p1.getCurrentHP()==17)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_17.png",250,270,false,false));

            if(p1.getCurrentHP()==16)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_16.png",250,270,false,false));

            if(p1.getCurrentHP()==15)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_15.png",250,270,false,false));

            if(p1.getCurrentHP()==14)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_14.png",250,270,false,false));

            if(p1.getCurrentHP()==13)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_13.png",250,270,false,false));

            if(p1.getCurrentHP()==12)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_12.png",250,270,false,false));

            if(p1.getCurrentHP()==11)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_11.png",250,270,false,false));

            if(p1.getCurrentHP()==10)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_10.png",250,270,false,false));

            if(p1.getCurrentHP()==9)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_9.png",250,270,false,false));

            if(p1.getCurrentHP()==8)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_8.png",250,270,false,false));

            if(p1.getCurrentHP()==7)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_7.png",250,270,false,false));

            if(p1.getCurrentHP()==6)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_6.png",250,270,false,false));

            if(p1.getCurrentHP()==5)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_5.png",250,270,false,false));

            if(p1.getCurrentHP()==4)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_4.png",250,270,false,false));

            if(p1.getCurrentHP()==3)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_3.png",250,270,false,false));

            if(p1.getCurrentHP()==2)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_2.png",250,270,false,false));

            if(p1.getCurrentHP()==1)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_1.png",250,270,false,false));

            if(p1.getCurrentHP()==0)
                p1Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_0.png",250,270,false,false));


        }
        if(p1 instanceof Warlock){
            if(p1.getCurrentHP()==30)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_30.png",250,270,false,false));

            if(p1.getCurrentHP()==29)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_29.png",250,270,false,false));

            if(p1.getCurrentHP()==28)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_28.png",250,270,false,false));

            if(p1.getCurrentHP()==27)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_27.png",250,270,false,false));

            if(p1.getCurrentHP()==26)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_26.png",250,270,false,false));

            if(p1.getCurrentHP()==25)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_25.png",250,270,false,false));

            if(p1.getCurrentHP()==24)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_24.png",250,270,false,false));

            if(p1.getCurrentHP()==23)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_23.png",250,270,false,false));

            if(p1.getCurrentHP()==22)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_22.png",250,270,false,false));

            if(p1.getCurrentHP()==21)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_21.png",250,270,false,false));

            if(p1.getCurrentHP()==20)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_20.png",250,270,false,false));

            if(p1.getCurrentHP()==19)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_19.png",250,270,false,false));

            if(p1.getCurrentHP()==18)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_18.png",250,270,false,false));

            if(p1.getCurrentHP()==17)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_17.png",250,270,false,false));

            if(p1.getCurrentHP()==16)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_16.png",250,270,false,false));

            if(p1.getCurrentHP()==15)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_15.png",250,270,false,false));

            if(p1.getCurrentHP()==14)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_14.png",250,270,false,false));

            if(p1.getCurrentHP()==13)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_13.png",250,270,false,false));

            if(p1.getCurrentHP()==12)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_12.png",250,270,false,false));

            if(p1.getCurrentHP()==11)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_11.png",250,270,false,false));

            if(p1.getCurrentHP()==10)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_10.png",250,270,false,false));

            if(p1.getCurrentHP()==9)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_9.png",250,270,false,false));

            if(p1.getCurrentHP()==8)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_8.png",250,270,false,false));

            if(p1.getCurrentHP()==7)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_7.png",250,270,false,false));

            if(p1.getCurrentHP()==6)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_6.png",250,270,false,false));

            if(p1.getCurrentHP()==5)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_5.png",250,270,false,false));

            if(p1.getCurrentHP()==4)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_4.png",250,270,false,false));

            if(p1.getCurrentHP()==3)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_3.png",250,270,false,false));

            if(p1.getCurrentHP()==2)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_2.png",250,270,false,false));

            if(p1.getCurrentHP()==1)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_1.png",250,270,false,false));

            if(p1.getCurrentHP()==0)
                p1Icon.setImage(new Image("images\\Guldan\\Guldan_0.png",250,270,false,false));


        }
        if(p1 instanceof Hunter){
            if(p1.getCurrentHP()==30)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_30.png",250,270,false,false));

            if(p1.getCurrentHP()==29)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_29.png",250,270,false,false));

            if(p1.getCurrentHP()==28)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_28.png",250,270,false,false));

            if(p1.getCurrentHP()==27)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_27.png",250,270,false,false));

            if(p1.getCurrentHP()==26)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_26.png",250,270,false,false));

            if(p1.getCurrentHP()==25)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_25.png",250,270,false,false));

            if(p1.getCurrentHP()==24)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_24.png",250,270,false,false));

            if(p1.getCurrentHP()==23)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_23.png",250,270,false,false));

            if(p1.getCurrentHP()==22)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_22.png",250,270,false,false));

            if(p1.getCurrentHP()==21)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_21.png",250,270,false,false));

            if(p1.getCurrentHP()==20)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_20.png",250,270,false,false));

            if(p1.getCurrentHP()==19)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_19.png",250,270,false,false));

            if(p1.getCurrentHP()==18)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_18.png",250,270,false,false));

            if(p1.getCurrentHP()==17)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_17.png",250,270,false,false));

            if(p1.getCurrentHP()==16)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_16.png",250,270,false,false));

            if(p1.getCurrentHP()==15)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_15.png",250,270,false,false));

            if(p1.getCurrentHP()==14)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_14.png",250,270,false,false));

            if(p1.getCurrentHP()==13)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_13.png",250,270,false,false));

            if(p1.getCurrentHP()==12)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_12.png",250,270,false,false));

            if(p1.getCurrentHP()==11)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_11.png",250,270,false,false));

            if(p1.getCurrentHP()==10)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_10.png",250,270,false,false));

            if(p1.getCurrentHP()==9)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_9.png",250,270,false,false));

            if(p1.getCurrentHP()==8)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_8.png",250,270,false,false));

            if(p1.getCurrentHP()==7)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_7.png",250,270,false,false));

            if(p1.getCurrentHP()==6)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_6.png",250,270,false,false));

            if(p1.getCurrentHP()==5)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_5.png",250,270,false,false));

            if(p1.getCurrentHP()==4)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_4.png",250,270,false,false));

            if(p1.getCurrentHP()==3)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_3.png",250,270,false,false));

            if(p1.getCurrentHP()==2)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_2.png",250,270,false,false));

            if(p1.getCurrentHP()==1)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_1.png",250,270,false,false));

            if(p1.getCurrentHP()==0)
                p1Icon.setImage(new Image("images\\rexxar\\Rexxar_0.png",250,270,false,false));


        }
    }

    public void verifyHeroP2(){
        if(p2==game.getCurrentHero()){
            p2Icon.setEffect(new DropShadow(50, Color.WHITESMOKE));
        }else
            p2Icon.setEffect(new DropShadow(0, Color.DEEPSKYBLUE));


        if(p2 instanceof Mage){
            if(p2.getCurrentHP()==30)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_30.png",250,270,false,false));

        if(p2.getCurrentHP()==29)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_29.png",250,270,false,false));

        if(p2.getCurrentHP()==28)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_28.png",250,270,false,false));

        if(p2.getCurrentHP()==27)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_27.png",250,270,false,false));

        if(p2.getCurrentHP()==26)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_26.png",250,270,false,false));

        if(p2.getCurrentHP()==25)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_25.png",250,270,false,false));

        if(p2.getCurrentHP()==24)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_24.png",250,270,false,false));

        if(p2.getCurrentHP()==23)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_23.png",250,270,false,false));

        if(p2.getCurrentHP()==22)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_22.png",250,270,false,false));

        if(p2.getCurrentHP()==21)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_21.png",250,270,false,false));

        if(p2.getCurrentHP()==20)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_20.png",250,270,false,false));

        if(p2.getCurrentHP()==19)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_19.png",250,270,false,false));

        if(p2.getCurrentHP()==18)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_18.png",250,270,false,false));

        if(p2.getCurrentHP()==17)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_17.png",250,270,false,false));

        if(p2.getCurrentHP()==16)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_16.png",250,270,false,false));

        if(p2.getCurrentHP()==15)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_15.png",250,270,false,false));

        if(p2.getCurrentHP()==14)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_14.png",250,270,false,false));

        if(p2.getCurrentHP()==13)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_13.png",250,270,false,false));

        if(p2.getCurrentHP()==12)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_12.png",250,270,false,false));

        if(p2.getCurrentHP()==11)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_11.png",250,270,false,false));

        if(p2.getCurrentHP()==10)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_10.png",250,270,false,false));

        if(p2.getCurrentHP()==9)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_9.png",250,270,false,false));

        if(p2.getCurrentHP()==8)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_8.png",250,270,false,false));

        if(p2.getCurrentHP()==7)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_7.png",250,270,false,false));

        if(p2.getCurrentHP()==6)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_6.png",250,270,false,false));

        if(p2.getCurrentHP()==5)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_5.png",250,270,false,false));

        if(p2.getCurrentHP()==4)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_4.png",250,270,false,false));

        if(p2.getCurrentHP()==3)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_3.png",250,270,false,false));

        if(p2.getCurrentHP()==2)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_2.png",250,270,false,false));

        if(p2.getCurrentHP()==1)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_1.png",250,270,false,false));

        if(p2.getCurrentHP()==0)
            p2Icon.setImage(new Image("images\\jaina\\Jaina_Proudmoore_0.png",250,270,false,false));
    }

        if(p2 instanceof Paladin){
            if(p2.getCurrentHP()==30)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_30.png",250,270,false,false));

            if(p2.getCurrentHP()==29)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_29.png",250,270,false,false));

            if(p2.getCurrentHP()==28)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_28.png",250,270,false,false));

            if(p2.getCurrentHP()==27)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_27.png",250,270,false,false));

            if(p2.getCurrentHP()==26)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_26.png",250,270,false,false));

            if(p2.getCurrentHP()==25)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_25.png",250,270,false,false));

            if(p2.getCurrentHP()==24)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_24.png",250,270,false,false));

            if(p2.getCurrentHP()==23)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_23.png",250,270,false,false));

            if(p2.getCurrentHP()==22)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_22.png",250,270,false,false));

            if(p2.getCurrentHP()==21)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_21.png",250,270,false,false));

            if(p2.getCurrentHP()==20)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_20.png",250,270,false,false));

            if(p2.getCurrentHP()==19)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_19.png",250,270,false,false));

            if(p2.getCurrentHP()==18)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_18.png",250,270,false,false));

            if(p2.getCurrentHP()==17)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_17.png",250,270,false,false));

            if(p2.getCurrentHP()==16)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_16.png",250,270,false,false));

            if(p2.getCurrentHP()==15)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_15.png",250,270,false,false));

            if(p2.getCurrentHP()==14)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_14.png",250,270,false,false));

            if(p2.getCurrentHP()==13)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_13.png",250,270,false,false));

            if(p2.getCurrentHP()==12)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_12.png",250,270,false,false));

            if(p2.getCurrentHP()==11)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_11.png",250,270,false,false));

            if(p2.getCurrentHP()==10)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_10.png",250,270,false,false));

            if(p2.getCurrentHP()==9)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_9.png",250,270,false,false));

            if(p2.getCurrentHP()==8)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_8.png",250,270,false,false));

            if(p2.getCurrentHP()==7)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_7.png",250,270,false,false));

            if(p2.getCurrentHP()==6)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_6.png",250,270,false,false));

            if(p2.getCurrentHP()==5)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_5.png",250,270,false,false));

            if(p2.getCurrentHP()==4)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_4.png",250,270,false,false));

            if(p2.getCurrentHP()==3)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_3.png",250,270,false,false));

            if(p2.getCurrentHP()==2)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_2.png",250,270,false,false));

            if(p2.getCurrentHP()==1)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_1.png",250,270,false,false));

            if(p2.getCurrentHP()==0)
                p2Icon.setImage(new Image("images\\Urther\\Uther_Lightbringer_0.png",250,270,false,false));

        }

        if(p2 instanceof Priest){
            if(p2.getCurrentHP()==30)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_30.png",250,270,false,false));

            if(p2.getCurrentHP()==29)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_29.png",250,270,false,false));

            if(p2.getCurrentHP()==28)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_28.png",250,270,false,false));

            if(p2.getCurrentHP()==27)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_27.png",250,270,false,false));

            if(p2.getCurrentHP()==26)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_26.png",250,270,false,false));

            if(p2.getCurrentHP()==25)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_25.png",250,270,false,false));

            if(p2.getCurrentHP()==24)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_24.png",250,270,false,false));

            if(p2.getCurrentHP()==23)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_23.png",250,270,false,false));

            if(p2.getCurrentHP()==22)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_22.png",250,270,false,false));

            if(p2.getCurrentHP()==21)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_21.png",250,270,false,false));

            if(p2.getCurrentHP()==20)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_20.png",250,270,false,false));

            if(p2.getCurrentHP()==19)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_19.png",250,270,false,false));

            if(p2.getCurrentHP()==18)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_18.png",250,270,false,false));

            if(p2.getCurrentHP()==17)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_17.png",250,270,false,false));

            if(p2.getCurrentHP()==16)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_16.png",250,270,false,false));

            if(p2.getCurrentHP()==15)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_15.png",250,270,false,false));

            if(p2.getCurrentHP()==14)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_14.png",250,270,false,false));

            if(p2.getCurrentHP()==13)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_13.png",250,270,false,false));

            if(p2.getCurrentHP()==12)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_12.png",250,270,false,false));

            if(p2.getCurrentHP()==11)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_11.png",250,270,false,false));

            if(p2.getCurrentHP()==10)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_10.png",250,270,false,false));

            if(p2.getCurrentHP()==9)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_9.png",250,270,false,false));

            if(p2.getCurrentHP()==8)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_8.png",250,270,false,false));

            if(p2.getCurrentHP()==7)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_7.png",250,270,false,false));

            if(p2.getCurrentHP()==6)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_6.png",250,270,false,false));

            if(p2.getCurrentHP()==5)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_5.png",250,270,false,false));

            if(p2.getCurrentHP()==4)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_4.png",250,270,false,false));

            if(p2.getCurrentHP()==3)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_3.png",250,270,false,false));

            if(p2.getCurrentHP()==2)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_2.png",250,270,false,false));

            if(p2.getCurrentHP()==1)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_1.png",250,270,false,false));

            if(p2.getCurrentHP()==0)
                p2Icon.setImage(new Image("images\\anduin\\Anduin_Wrynn_0.png",250,270,false,false));


        }
        if(p2 instanceof Warlock){
            if(p2.getCurrentHP()==30)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_30.png",250,270,false,false));

            if(p2.getCurrentHP()==29)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_29.png",250,270,false,false));

            if(p2.getCurrentHP()==28)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_28.png",250,270,false,false));

            if(p2.getCurrentHP()==27)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_27.png",250,270,false,false));

            if(p2.getCurrentHP()==26)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_26.png",250,270,false,false));

            if(p2.getCurrentHP()==25)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_25.png",250,270,false,false));

            if(p2.getCurrentHP()==24)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_24.png",250,270,false,false));

            if(p2.getCurrentHP()==23)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_23.png",250,270,false,false));

            if(p2.getCurrentHP()==22)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_22.png",250,270,false,false));

            if(p2.getCurrentHP()==21)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_21.png",250,270,false,false));

            if(p2.getCurrentHP()==20)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_20.png",250,270,false,false));

            if(p2.getCurrentHP()==19)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_19.png",250,270,false,false));

            if(p2.getCurrentHP()==18)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_18.png",250,270,false,false));

            if(p2.getCurrentHP()==17)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_17.png",250,270,false,false));

            if(p2.getCurrentHP()==16)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_16.png",250,270,false,false));

            if(p2.getCurrentHP()==15)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_15.png",250,270,false,false));

            if(p2.getCurrentHP()==14)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_14.png",250,270,false,false));

            if(p2.getCurrentHP()==13)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_13.png",250,270,false,false));

            if(p2.getCurrentHP()==12)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_12.png",250,270,false,false));

            if(p2.getCurrentHP()==11)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_11.png",250,270,false,false));

            if(p2.getCurrentHP()==10)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_10.png",250,270,false,false));

            if(p2.getCurrentHP()==9)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_9.png",250,270,false,false));

            if(p2.getCurrentHP()==8)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_8.png",250,270,false,false));

            if(p2.getCurrentHP()==7)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_7.png",250,270,false,false));

            if(p2.getCurrentHP()==6)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_6.png",250,270,false,false));

            if(p2.getCurrentHP()==5)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_5.png",250,270,false,false));

            if(p2.getCurrentHP()==4)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_4.png",250,270,false,false));

            if(p2.getCurrentHP()==3)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_3.png",250,270,false,false));

            if(p2.getCurrentHP()==2)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_2.png",250,270,false,false));

            if(p2.getCurrentHP()==1)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_1.png",250,270,false,false));

            if(p2.getCurrentHP()==0)
                p2Icon.setImage(new Image("images\\Guldan\\Guldan_0.png",250,270,false,false));


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
    public void verifyMana(){
        if(game.getCurrentHero()==p1)
         p1Mana.setText("Current Mana:"+ p1.getCurrentManaCrystals()+"\nMax Mana:"+ p1.getTotalManaCrystals()+"\nCards Left:"+p1.getDeck().size()+"\nYour Turn");
        else
            p1Mana.setText("Current Mana:"+ p1.getCurrentManaCrystals()+"\nMax Mana:"+ p1.getTotalManaCrystals()+"\nCards Left:"+p1.getDeck().size()+"\nOpponent's Turn");
        if(game.getCurrentHero()==p2)
            p2Mana.setText("Current Mana:"+ p2.getCurrentManaCrystals()+"\nMax Mana:"+ p2.getTotalManaCrystals()+"\nCards Left:"+p2.getDeck().size()+"\nYour Turn");
        else
            p2Mana.setText("Current Mana:"+ p2.getCurrentManaCrystals()+"\nMax Mana:"+ p2.getTotalManaCrystals()+"\nCards Left:"+p2.getDeck().size()+"\nOpponent's Turn");
        p1VerifyMinions();
        p2VerifyMinions();
        verifyHeroP1();
        verifyHeroP2();



    }
    public void getPowerImage(){
        if(p1 instanceof Mage)
            p1Power.setImage(new Image("images\\Fireblast.png",300,192,true,true));
        if(p1 instanceof Warlock)
            p1Power.setImage(new Image("images\\Life_Tap.png",300,192,true,true));
        if(p1 instanceof Paladin)
            p1Power.setImage(new Image("images\\Reinforce_hs.png",300,192,true,true));
        if(p1 instanceof Priest)
            p1Power.setImage(new Image("images\\Lesser_Heal.png",300,192,true,true));
        if(p1 instanceof Hunter)
            p1Power.setImage(new Image("images\\Steady_Shot.png",300,192,true,true));


        if(p2 instanceof Mage)
            p2Power.setImage(new Image("images\\Fireblast.png",300,192,true,true));
        if(p2 instanceof Warlock)
            p2Power.setImage(new Image("images\\Life_Tap.png",300,192,true,true));
        if(p2 instanceof Paladin)
            p2Power.setImage(new Image("images\\Reinforce_hs.png",300,192,true,true));
        if(p2 instanceof Priest)
            p2Power.setImage(new Image("images\\Lesser_Heal.png",300,192,true,true));
        if(p2 instanceof Hunter)
            p2Power.setImage(new Image("images\\Steady_Shot.png",300,192,true,true));
    }
    public void p1VerifyMinions(){
        if(!p1Field.getChildren().isEmpty()){
        int i=0;
        int size= p1Field.getChildren().size();
        while(i<size){
            Node cur= p1Field.getChildren().get(i);
            if(cur instanceof minionButton) {
                ((minionButton) cur).verifyMinion();
                if(((minionButton) cur).getHp()==0)
                    p1Field.getChildren().remove(i);
                size=p1Field.getChildren().size();
            }
            i++;}
        if(p1Field.getChildren().get(p1Field.getChildren().size()-1) instanceof minionButton){
           if( ((minionButton) p1Field.getChildren().get(p1Field.getChildren().size()-1)).getHp()==0)
               p1Field.getChildren().remove(p1Field.getChildren().size()-1);
        }}
//        for(Node cur: p1Field.getChildren()){
//            if(cur instanceof minionButton) {
//                ((minionButton) cur).verifyMinion();
//
//            }}
        // p1Field.getChildren().removeIf(m->((minionButton)m).getHp()==0);
    }
    public void p2VerifyMinions(){
        if(!p2Field.getChildren().isEmpty()){
        int i=0;
        int size= p2Field.getChildren().size();
        while(i<size){
            Node cur= p2Field.getChildren().get(i);
            if(cur instanceof minionButton) {
                ((minionButton) cur).verifyMinion();
                if(((minionButton) cur).getHp()==0)
                    p2Field.getChildren().remove(i);
                size=p2Field.getChildren().size();
            }
            i++;}
        if(p2Field.getChildren().get(p2Field.getChildren().size()-1) instanceof minionButton){
            if( ((minionButton) p2Field.getChildren().get(p2Field.getChildren().size()-1)).getHp()==0)
                p2Field.getChildren().remove(p2Field.getChildren().size()-1);
        }}

//        for(Node cur: p2Field.getChildren()){
//            if(cur instanceof minionButton) {
//                ((minionButton) cur).verifyMinion();
//            }
    }
    public void game() throws FullHandException, CloneNotSupportedException {
        play=true;
        playOnce("sounds/Start.wav");
        stage=new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        game=new Game(p1,p2);
        game.setListener(this);
        end= new ImageView(new Image("images\\resources\\endTurn.png",200,100,false,false));
//        stage.setMinHeight(1060);
//        stage.setMinWidth(1920);
//        stage.setMaxHeight(1080);
//        stage.setMaxWidth(1920);
        //HeroIcon
        p1Icon= new ImageView(new Image("images\\jaina\\Jaina_Proudmoore_30.png",250,270,false,false));
        p1Icon.setOnMouseEntered(e->{
            if(Targeting){
                p1Icon.setEffect(new InnerShadow(100,Color.RED));
                playOnce("sounds/twitch.wav");
            }
        });
        p1Icon.setOnMouseExited(e->{
            if(Targeting){
                p1Icon.setEffect(new InnerShadow(0,Color.RED));
            }
        });
        p1Icon.setOnMouseClicked(e->{
            if(Targeting){
                Targeting=false;
            try {
                game.getCurrentHero().attackWithMinion(attacker,p1);
            } catch (CannotAttackException cannotAttackException) {
                exceptionWindow(cannotAttackException);
                cannotAttackException.printStackTrace();
                return;
            } catch (NotYourTurnException notYourTurnException) {
                exceptionWindow(notYourTurnException);
                notYourTurnException.printStackTrace();


                return;
            } catch (TauntBypassException tauntBypassException) {
                exceptionWindow(tauntBypassException);
                tauntBypassException.printStackTrace();


                return;
            } catch (NotSummonedException notSummonedException) {
                exceptionWindow(notSummonedException);
                notSummonedException.printStackTrace();

                return;
            } catch (InvalidTargetException invalidTargetException) {
                exceptionWindow(invalidTargetException);
                invalidTargetException.printStackTrace();
                return;
            }
                playOnce("sounds/attack.wav");
                Verify();

            }
        });

        p2Icon=new ImageView(new Image("images\\Urther\\Uther_Lightbringer_30.png",250,270,false,false));
        p2Icon.setOnMouseEntered(e->{
            if(Targeting){
                p2Icon.setEffect(new InnerShadow(100,Color.RED));
                playOnce("sounds/twitch.wav");
            }
        });
        p2Icon.setOnMouseExited(e->{
            if(Targeting){
                p2Icon.setEffect(new InnerShadow(0,Color.RED));
            }
        });
        p2Icon.setOnMouseClicked(e->{
            if(Targeting){
                Targeting=false;
                try {
                    game.getCurrentHero().attackWithMinion(attacker,p2);
                } catch (CannotAttackException cannotAttackException) {
                    exceptionWindow(cannotAttackException);
                    cannotAttackException.printStackTrace();
                    Targeting=false;

                    return;
                } catch (NotYourTurnException notYourTurnException) {
                    exceptionWindow(notYourTurnException);
                    notYourTurnException.printStackTrace();
                    return;
                } catch (TauntBypassException tauntBypassException) {
                    exceptionWindow(tauntBypassException);
                    tauntBypassException.printStackTrace();
                    Targeting=false;

                    return;
                } catch (NotSummonedException notSummonedException) {
                    exceptionWindow(notSummonedException);
                    notSummonedException.printStackTrace();
                    Targeting=false;


                    return;
                } catch (InvalidTargetException invalidTargetException) {
                    exceptionWindow(invalidTargetException);
                    invalidTargetException.printStackTrace();
                    Targeting=false;

                    return;
                }
                playOnce("sounds/attack.wav");
                Verify();
            }
        });
        verifyHeroP1();
        verifyHeroP2();
        //__________________________________________________________________________________________________________

        //Hero Power
        p1Power=new ImageView(new Image("images\\Fireblast.png",300,192,true,true));
        p2Power =new ImageView(new Image("images\\Steady_Shot.png",100,175,true,true));
        getPowerImage();
        p1Power.setOnMouseEntered(e->{  p1Power.setOnMouseEntered(ee->{if(p1.getCurrentManaCrystals()>=2 && p1.isHeroPowerUsed()==false&& p1==game.getCurrentHero())
            p1Power.setEffect(new InnerShadow(50,Color.GREEN));
        else
            p1Power.setEffect(new InnerShadow(50,Color.RED));});});
        p1Power.setOnMouseExited(e->p1Power.setEffect(new InnerShadow(0,Color.GREEN)));

        p2Power.setOnMouseEntered(e->{  p2Power.setOnMouseEntered(ee->{if(p2.getCurrentManaCrystals()>=2 && p2.isHeroPowerUsed()==false&& p2==game.getCurrentHero())
            p2Power.setEffect(new InnerShadow(50,Color.GREEN));
        else
            p2Power.setEffect(new InnerShadow(50,Color.RED));});});
        p2Power.setOnMouseExited(e->p2Power.setEffect(new InnerShadow(0,Color.GREEN)));
        //__________________________________________________________________________________________
        //___________________________________________________________________________________________________________


        p1Power.setOnMouseClicked(e->{
            if(!(p1 instanceof Paladin))
            playOnce("sounds/power.wav");
            else if(p1 instanceof Paladin)
                playOnce("sounds/Urther_power.wav");


            if(p1 instanceof Warlock) {
                try {
                    p1.useHeroPower();
                } catch (NotEnoughManaException notEnoughManaException) {
                    exceptionWindow(notEnoughManaException);
                    notEnoughManaException.printStackTrace();
                    return;
                } catch (HeroPowerAlreadyUsedException heroPowerAlreadyUsedException) {
                    exceptionWindow(heroPowerAlreadyUsedException);
                    heroPowerAlreadyUsedException.printStackTrace();
                    return;
                } catch (NotYourTurnException notYourTurnException) {
                    notYourTurnException.printStackTrace();
                    return;
                } catch (FullHandException fullHandException) {
                    exceptionWindow(fullHandException);
                    fullHandException.printStackTrace();
                    return;
                } catch (CloneNotSupportedException cloneNotSupportedException) {
                    exceptionWindow(cloneNotSupportedException);
                    cloneNotSupportedException.printStackTrace();
                    return;
                } catch (FullFieldException fullFieldException) {
                    exceptionWindow(fullFieldException);
                    fullFieldException.printStackTrace();
                    return;
                }catch (IndexOutOfBoundsException indexOutOfBoundsException){
                    exceptionWindow(indexOutOfBoundsException);
                    return;
                }
                endTurnDraw(p1,p2,p1hand,p1Field,p2Field);
                verifyHeroP1();
                verifyHeroP2();
                verifyMana();
            }
            if(p1 instanceof Hunter){
                try {
                    p1.useHeroPower();
                } catch (NotEnoughManaException notEnoughManaException) {
                    exceptionWindow(notEnoughManaException);
                    notEnoughManaException.printStackTrace();
                    return;
                } catch (HeroPowerAlreadyUsedException heroPowerAlreadyUsedException) {
                    exceptionWindow(heroPowerAlreadyUsedException);
                    heroPowerAlreadyUsedException.printStackTrace();
                    return;
                } catch (NotYourTurnException notYourTurnException) {
                    notYourTurnException.printStackTrace();
                    return;
                } catch (FullHandException fullHandException) {
                    exceptionWindow(fullHandException);
                    fullHandException.printStackTrace();
                    return;
                } catch (CloneNotSupportedException cloneNotSupportedException) {
                    exceptionWindow(cloneNotSupportedException);
                    cloneNotSupportedException.printStackTrace();
                    return;
                } catch (FullFieldException fullFieldException) {
                    exceptionWindow(fullFieldException);
                    fullFieldException.printStackTrace();
                    return;
                }catch (IndexOutOfBoundsException indexOutOfBoundsException){
                    exceptionWindow(indexOutOfBoundsException);
                    return;
                }
                verifyHeroP2();
                verifyHeroP1();
                verifyMana();
            }
            if(p1 instanceof Paladin){
                try {
                    p1.useHeroPower();
                } catch (NotEnoughManaException notEnoughManaException) {
                    exceptionWindow(notEnoughManaException);
                    notEnoughManaException.printStackTrace();
                    return;
                } catch (HeroPowerAlreadyUsedException heroPowerAlreadyUsedException) {
                    exceptionWindow(heroPowerAlreadyUsedException);
                    heroPowerAlreadyUsedException.printStackTrace();
                    return;
                } catch (NotYourTurnException notYourTurnException) {
                    notYourTurnException.printStackTrace();
                    return;
                } catch (FullHandException fullHandException) {
                    exceptionWindow(fullHandException);
                    fullHandException.printStackTrace();
                    return;
                } catch (CloneNotSupportedException cloneNotSupportedException) {
                    exceptionWindow(cloneNotSupportedException);
                    cloneNotSupportedException.printStackTrace();
                    return;
                } catch (FullFieldException fullFieldException) {
                    exceptionWindow(fullFieldException);
                    fullFieldException.printStackTrace();
                    return;
                }catch (IndexOutOfBoundsException indexOutOfBoundsException){
                    exceptionWindow(indexOutOfBoundsException);
                    return;
                }
                Minion SilverHand=p1.getField().get(p1.getField().size()-1);
                minionButton Silver= new minionButton(SilverHand);
                Silver.setOnMouseEntered(enter->{
                    if(Targeting && Silver.getListener()!=game.getCurrentHero()){
                        Silver.setEffect(new InnerShadow(100,Color.RED));
                        playOnce("sounds/twitch.wav");
                    }
                });
                Silver.setOnMouseExited(exit->{
                    if(Targeting&& Silver.getListener()!=game.getCurrentHero())
                    Silver.verifyMinion();

                });
                Silver.setOnMouseClicked(ee->{
                    if(Targeting && Silver.getListener()!=game.getCurrentHero()){
                        Targeting=false;
                        try {
                            game.getCurrentHero().attackWithMinion(attacker,Silver.getMinion());
                        } catch (CannotAttackException cannotAttackException) {
                            cannotAttackException.printStackTrace();
                            exceptionWindow(cannotAttackException);
                            Silver.verifyMinion();
                            gamescene.setCursor(new ImageCursor(new Image("images\\mouse.png")));

                            return;
                        } catch (NotYourTurnException notYourTurnException) {
                            notYourTurnException.printStackTrace();
                            exceptionWindow(notYourTurnException);
                            Silver.verifyMinion();
                            gamescene.setCursor(new ImageCursor(new Image("images\\mouse.png")));

                            return;
                        } catch (TauntBypassException tauntBypassException) {
                            tauntBypassException.printStackTrace();
                            exceptionWindow(tauntBypassException);
                            Silver.verifyMinion();
                            gamescene.setCursor(new ImageCursor(new Image("images\\mouse.png")));

                            return;
                        } catch (InvalidTargetException invalidTargetException) {
                            invalidTargetException.printStackTrace();
                            exceptionWindow(invalidTargetException);
                            Silver.verifyMinion();
                            gamescene.setCursor(new ImageCursor(new Image("images\\mouse.png")));

                            return;
                        } catch (NotSummonedException notSummonedException) {
                            notSummonedException.printStackTrace();
                            exceptionWindow(notSummonedException);
                            Silver.verifyMinion();
                            gamescene.setCursor(new ImageCursor(new Image("images\\mouse.png")));

                            return;
                        }
                        playOnce("sounds/attack.wav");
                        p1VerifyMinions();
                        p2VerifyMinions();

                    }
                    else{
                        Silver.setEffect(new InnerShadow(100, Color.GREEN));
                        playOnce("sounds/sattack.wav");
                        Targeting=true;
                        attacker=Silver.getMinion();
                        gamescene.setCursor(new ImageCursor(new Image("images\\attack.png",200,200,false,false)));
                    }
                });
                p1Field.getChildren().add(Silver);
                verifyMana();
                verifyHeroP1();
                verifyHeroP2();
            }
            if(p1 instanceof Priest) {
                Stage s1 = new Stage();
                FlowPane screen = new FlowPane();
                Scene scene= new Scene(screen);
                screen.setPrefSize(1500,700);
                BackgroundImage b= new BackgroundImage(new Image("images/spellsBG.jpg"),
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                        new BackgroundSize(screen.getWidth(),screen.getHeight(), false, false, true, false));
                screen.setBackground(new Background(b));
                s1.setScene(scene);
                s1.show();
                Image p = new Image("images\\Anduin_Wrynn.png", 250, 250, true, true);
                ImageView priest = new ImageView(p);
                screen.getChildren().add(priest);
                priest.setOnMouseClicked(ee -> {
                    try {
                        ((Priest) p1).useHeroPower(p1);
                    } catch (NotEnoughManaException notEnoughManaException) {
                        exceptionWindow(notEnoughManaException);
                        notEnoughManaException.printStackTrace();
                        return;
                    } catch (HeroPowerAlreadyUsedException heroPowerAlreadyUsedException) {
                        exceptionWindow(heroPowerAlreadyUsedException);
                        heroPowerAlreadyUsedException.printStackTrace();
                        return;
                    } catch (NotYourTurnException notYourTurnException) {
                        notYourTurnException.printStackTrace();
                        return;
                    } catch (FullHandException fullHandException) {
                        exceptionWindow(fullHandException);
                        fullHandException.printStackTrace();
                        return;
                    } catch (CloneNotSupportedException cloneNotSupportedException) {
                        exceptionWindow(cloneNotSupportedException);
                        cloneNotSupportedException.printStackTrace();
                        return;
                    } catch (FullFieldException fullFieldException) {
                        exceptionWindow(fullFieldException);
                        fullFieldException.printStackTrace();
                        return;
                    } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                        exceptionWindow(indexOutOfBoundsException);
                        return;
                    }
                    s1.close();
                    verifyHeroP2();
                    verifyHeroP1();
                    verifyMana();
                });

                for (Node cur : p1Field.getChildren()) {
                    if (!(cur instanceof ImageView) && cur.isVisible()) {
                        Minion target = ((minionButton) cur).getMinion();
                        minionButton button = new minionButton(target);
                        button.setOnMouseClicked(ee -> {
                            minionTarget = target;
                            try {
                                ((Priest) p1).useHeroPower(target);
                            } catch (NotEnoughManaException notEnoughManaException) {
                                exceptionWindow(notEnoughManaException);
                                notEnoughManaException.printStackTrace();
                                return;
                            } catch (HeroPowerAlreadyUsedException heroPowerAlreadyUsedException) {
                                exceptionWindow(heroPowerAlreadyUsedException);
                                heroPowerAlreadyUsedException.printStackTrace();
                                return;
                            } catch (NotYourTurnException notYourTurnException) {
                                notYourTurnException.printStackTrace();
                                return;
                            } catch (FullHandException fullHandException) {
                                exceptionWindow(fullHandException);
                                fullHandException.printStackTrace();
                                return;
                            } catch (CloneNotSupportedException cloneNotSupportedException) {
                                exceptionWindow(cloneNotSupportedException);
                                cloneNotSupportedException.printStackTrace();
                                return;
                            } catch (FullFieldException fullFieldException) {
                                exceptionWindow(fullFieldException);
                                fullFieldException.printStackTrace();
                                return;
                            } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                                exceptionWindow(indexOutOfBoundsException);
                                return;
                            }
                            p1VerifyMinions();
                            p2VerifyMinions();
                            verifyMana();
                            s1.close();
                        });
                        screen.getChildren().add(button);
                    }
                }
            }
            if(p1 instanceof Mage){            Stage s1 = new Stage();
                FlowPane screen = new FlowPane();
                screen.setPrefSize(1500,700);
                BackgroundImage b= new BackgroundImage(new Image("images/spellsBG.jpg"),
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                        new BackgroundSize(screen.getWidth(),screen.getHeight(), false, false, true, false));
                screen.setBackground(new Background(b));
                Scene scene= new Scene(screen);
                s1.setScene(scene);
                s1.show();
                ImageView opponent = null;
                if(p2 instanceof Mage)
                    opponent=new ImageView(new Image("images\\Jaina_Proudmoore.png",250,250,true,true));
                if(p2 instanceof Warlock)
                    opponent=new ImageView(new Image("images\\Guldan.png",250,250,true,true));
                if(p2 instanceof Priest)
                    opponent=new ImageView(new Image("images\\Anduin_Wrynn.png",250,250,true,true));
                if(p2 instanceof Paladin)
                    opponent=new ImageView(new Image("images\\Uther_Lightbringer.png",250,250,true,true));
                if(p2 instanceof Hunter)
                    opponent=new ImageView(new Image("images\\Rexxar.png",250,250,true,true));
                screen.getChildren().add(opponent);
                opponent.setOnMouseClicked(ee -> {
                    try {
                        ((Mage) p1).useHeroPower(p2);
                    } catch (NotEnoughManaException notEnoughManaException) {
                        exceptionWindow(notEnoughManaException);
                        notEnoughManaException.printStackTrace();
                        return;
                    } catch (HeroPowerAlreadyUsedException heroPowerAlreadyUsedException) {
                        exceptionWindow(heroPowerAlreadyUsedException);
                        heroPowerAlreadyUsedException.printStackTrace();
                        return;
                    } catch (NotYourTurnException notYourTurnException) {
                        notYourTurnException.printStackTrace();
                        return;
                    } catch (FullHandException fullHandException) {
                        exceptionWindow(fullHandException);
                        fullHandException.printStackTrace();
                        return;
                    } catch (CloneNotSupportedException cloneNotSupportedException) {
                        exceptionWindow(cloneNotSupportedException);
                        cloneNotSupportedException.printStackTrace();
                        return;
                    } catch (FullFieldException fullFieldException) {
                        exceptionWindow(fullFieldException);
                        fullFieldException.printStackTrace();
                        return;
                    } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                        exceptionWindow(indexOutOfBoundsException);
                        return;
                    }
                    s1.close();
                    verifyHeroP2();
                    verifyHeroP1();
                    verifyMana();
                });

                for (Node cur : p2Field.getChildren()) {
                    if (!(cur instanceof ImageView) && cur.isVisible()) {
                        Minion target = ((minionButton) cur).getMinion();
                        minionButton button = new minionButton(target);
                        button.setOnMouseClicked(ee -> {
                            minionTarget = target;
                            try {
                                ((Mage) p1).useHeroPower(target);
                            } catch (NotEnoughManaException notEnoughManaException) {
                                exceptionWindow(notEnoughManaException);
                                notEnoughManaException.printStackTrace();
                                return;
                            } catch (HeroPowerAlreadyUsedException heroPowerAlreadyUsedException) {
                                exceptionWindow(heroPowerAlreadyUsedException);
                                heroPowerAlreadyUsedException.printStackTrace();
                                return;
                            } catch (NotYourTurnException notYourTurnException) {
                                notYourTurnException.printStackTrace();
                                return;
                            } catch (FullHandException fullHandException) {
                                exceptionWindow(fullHandException);
                                fullHandException.printStackTrace();
                                return;
                            } catch (CloneNotSupportedException cloneNotSupportedException) {
                                exceptionWindow(cloneNotSupportedException);
                                cloneNotSupportedException.printStackTrace();
                                return;
                            } catch (FullFieldException fullFieldException) {
                                exceptionWindow(fullFieldException);
                                fullFieldException.printStackTrace();
                                return;
                            } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                                exceptionWindow(indexOutOfBoundsException);
                                return;
                            }
                            s1.close();
                            p1VerifyMinions();
                            p2VerifyMinions();
                            verifyMana();
                        });
                        screen.getChildren().add(button);
                    }}

            }


        });

        p2Power.setOnMouseClicked(e->{
            if(!(p2 instanceof Paladin))
                playOnce("sounds/power.wav");
            else if(p2 instanceof Paladin)
                playOnce("sounds/Urther_power.wav");
            if(p2 instanceof Warlock) {
            try {
                p2.useHeroPower();
            } catch (NotEnoughManaException notEnoughManaException) {
                exceptionWindow(notEnoughManaException);
                notEnoughManaException.printStackTrace();
                return;
            } catch (HeroPowerAlreadyUsedException heroPowerAlreadyUsedException) {
                exceptionWindow(heroPowerAlreadyUsedException);
                heroPowerAlreadyUsedException.printStackTrace();
                return;
            } catch (NotYourTurnException notYourTurnException) {
                notYourTurnException.printStackTrace();
                return;
            } catch (FullHandException fullHandException) {
                exceptionWindow(fullHandException);
                fullHandException.printStackTrace();
                return;
            } catch (CloneNotSupportedException cloneNotSupportedException) {
                exceptionWindow(cloneNotSupportedException);
                cloneNotSupportedException.printStackTrace();
                return;
            } catch (FullFieldException fullFieldException) {
                exceptionWindow(fullFieldException);
                fullFieldException.printStackTrace();
                return;
            }catch (IndexOutOfBoundsException indexOutOfBoundsException){
                exceptionWindow(indexOutOfBoundsException);
                return;
            }
            endTurnDraw(p2,p1,p2hand,p2Field,p1Field);
            verifyHeroP1();
            verifyHeroP2();
            verifyMana();
        }
            if(p2 instanceof Hunter){
                try {
                    p2.useHeroPower();
                } catch (NotEnoughManaException notEnoughManaException) {
                    exceptionWindow(notEnoughManaException);
                    notEnoughManaException.printStackTrace();
                    return;
                } catch (HeroPowerAlreadyUsedException heroPowerAlreadyUsedException) {
                    exceptionWindow(heroPowerAlreadyUsedException);
                    heroPowerAlreadyUsedException.printStackTrace();
                    return;
                } catch (NotYourTurnException notYourTurnException) {
                    notYourTurnException.printStackTrace();
                    return;
                } catch (FullHandException fullHandException) {
                    exceptionWindow(fullHandException);
                    fullHandException.printStackTrace();
                    return;
                } catch (CloneNotSupportedException cloneNotSupportedException) {
                    exceptionWindow(cloneNotSupportedException);
                    cloneNotSupportedException.printStackTrace();
                    return;
                } catch (FullFieldException fullFieldException) {
                    exceptionWindow(fullFieldException);
                    fullFieldException.printStackTrace();
                    return;
                }catch (IndexOutOfBoundsException indexOutOfBoundsException){
                    exceptionWindow(indexOutOfBoundsException);
                    return;
                }
                verifyHeroP2();
                verifyHeroP1();
                verifyMana();
            }
            if(p2 instanceof Paladin){
                try {
                    p2.useHeroPower();
                } catch (NotEnoughManaException notEnoughManaException) {
                    exceptionWindow(notEnoughManaException);
                    notEnoughManaException.printStackTrace();
                    return;
                } catch (HeroPowerAlreadyUsedException heroPowerAlreadyUsedException) {
                    exceptionWindow(heroPowerAlreadyUsedException);
                    heroPowerAlreadyUsedException.printStackTrace();
                    return;
                } catch (NotYourTurnException notYourTurnException) {
                    notYourTurnException.printStackTrace();
                    return;
                } catch (FullHandException fullHandException) {
                    exceptionWindow(fullHandException);
                    fullHandException.printStackTrace();
                    return;
                } catch (CloneNotSupportedException cloneNotSupportedException) {
                    exceptionWindow(cloneNotSupportedException);
                    cloneNotSupportedException.printStackTrace();
                    return;
                } catch (FullFieldException fullFieldException) {
                    exceptionWindow(fullFieldException);
                    fullFieldException.printStackTrace();
                    return;
                }catch (IndexOutOfBoundsException indexOutOfBoundsException){
                    exceptionWindow(indexOutOfBoundsException);
                    return;
                }
                Minion SilverHand=p2.getField().get(p2.getField().size()-1);
                minionButton Silver= new minionButton(SilverHand);
                Silver.setOnMouseEntered(enter->{
                    if(Targeting && Silver.getListener()!=game.getCurrentHero()){
                        Silver.setEffect(new InnerShadow(100,Color.RED));
                        playOnce("sounds/twitch.wav");
                    }
                });
                Silver.setOnMouseExited(exit->{
                    if(Targeting&& Silver.getListener()!=game.getCurrentHero())
                    Silver.verifyMinion();

                });
                Silver.setOnMouseClicked(ee->{
                    if(Targeting&& Silver.getListener()!=game.getCurrentHero()){
                        Targeting=false;
                        try {
                            game.getCurrentHero().attackWithMinion(attacker,Silver.getMinion());
                        } catch (CannotAttackException cannotAttackException) {
                            cannotAttackException.printStackTrace();
                            exceptionWindow(cannotAttackException);
                            gamescene.setCursor(new ImageCursor(new Image("images\\attack.png",200,200,false,false)));
                            Silver.verifyMinion();
                            return;

                        } catch (NotYourTurnException notYourTurnException) {
                            notYourTurnException.printStackTrace();
                            exceptionWindow(notYourTurnException);
                            Silver.verifyMinion();
                            gamescene.setCursor(new ImageCursor(new Image("images\\mouse.png")));
                            return;
                        } catch (TauntBypassException tauntBypassException) {
                            tauntBypassException.printStackTrace();
                            exceptionWindow(tauntBypassException);
                            Silver.verifyMinion();
                            gamescene.setCursor(new ImageCursor(new Image("images\\mouse.png")));

                            return;
                        } catch (InvalidTargetException invalidTargetException) {
                            invalidTargetException.printStackTrace();
                            exceptionWindow(invalidTargetException);
                            Silver.verifyMinion();
                            gamescene.setCursor(new ImageCursor(new Image("images\\mouse.png")));

                            return;
                        } catch (NotSummonedException notSummonedException) {
                            notSummonedException.printStackTrace();
                            exceptionWindow(notSummonedException);
                            Silver.verifyMinion();
                            gamescene.setCursor(new ImageCursor(new Image("images\\mouse.png")));
                            return;
                        }
                        playOnce("sounds/attack.wav");
                        p1VerifyMinions();
                        p2VerifyMinions();

                    }
                    else{
                        Silver.setEffect(new InnerShadow(100, Color.GREEN));
                        Targeting=true;
                        playOnce("sounds/sattack.wav");
                        attacker=Silver.getMinion();
                        gamescene.setCursor(new ImageCursor(new Image("images\\attack.png",200,200,false,false)));
                    }
                });
                p2Field.getChildren().add(Silver);
                verifyMana();
                verifyHeroP1();
                verifyHeroP2();
            }
            if(p2 instanceof Priest) {
                Stage s1 = new Stage();
                FlowPane screen = new FlowPane();
                screen.setPrefSize(1500,700);
                BackgroundImage b= new BackgroundImage(new Image("images/spellsBG.jpg"),
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                        new BackgroundSize(screen.getWidth(),screen.getHeight(), false, false, true, false));
                screen.setBackground(new Background(b));
                Scene scene= new Scene(screen);
                s1.setScene(scene);
                s1.show();
                Image p = new Image("images\\Anduin_Wrynn.png", 250, 250, true, true);
                ImageView priest = new ImageView(p);
                screen.getChildren().add(priest);
                priest.setOnMouseClicked(ee -> {
                    try {
                        ((Priest) p2).useHeroPower(p2);
                    } catch (NotEnoughManaException notEnoughManaException) {
                        exceptionWindow(notEnoughManaException);
                        notEnoughManaException.printStackTrace();
                        return;
                    } catch (HeroPowerAlreadyUsedException heroPowerAlreadyUsedException) {
                        exceptionWindow(heroPowerAlreadyUsedException);
                        heroPowerAlreadyUsedException.printStackTrace();
                        return;
                    } catch (NotYourTurnException notYourTurnException) {
                        notYourTurnException.printStackTrace();
                        return;
                    } catch (FullHandException fullHandException) {
                        exceptionWindow(fullHandException);
                        fullHandException.printStackTrace();
                        return;
                    } catch (CloneNotSupportedException cloneNotSupportedException) {
                        exceptionWindow(cloneNotSupportedException);
                        cloneNotSupportedException.printStackTrace();
                        return;
                    } catch (FullFieldException fullFieldException) {
                        exceptionWindow(fullFieldException);
                        fullFieldException.printStackTrace();
                        return;
                    } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                        exceptionWindow(indexOutOfBoundsException);
                        return;
                    }
                    s1.close();
                    verifyHeroP2();
                    verifyHeroP1();
                    verifyMana();
                });

                for (Node cur : p2Field.getChildren()) {
                    if (!(cur instanceof ImageView) && cur.isVisible()) {
                        Minion target = ((minionButton) cur).getMinion();
                        minionButton button = new minionButton(target);
                        button.setOnMouseClicked(ee -> {
                            minionTarget = target;
                            try {
                                ((Priest) p2).useHeroPower(target);
                            } catch (NotEnoughManaException notEnoughManaException) {
                                exceptionWindow(notEnoughManaException);
                                notEnoughManaException.printStackTrace();
                                return;
                            } catch (HeroPowerAlreadyUsedException heroPowerAlreadyUsedException) {
                                exceptionWindow(heroPowerAlreadyUsedException);
                                heroPowerAlreadyUsedException.printStackTrace();
                                return;
                            } catch (NotYourTurnException notYourTurnException) {
                                notYourTurnException.printStackTrace();
                                return;
                            } catch (FullHandException fullHandException) {
                                exceptionWindow(fullHandException);
                                fullHandException.printStackTrace();
                                return;
                            } catch (CloneNotSupportedException cloneNotSupportedException) {
                                exceptionWindow(cloneNotSupportedException);
                                cloneNotSupportedException.printStackTrace();
                                return;
                            } catch (FullFieldException fullFieldException) {
                                exceptionWindow(fullFieldException);
                                fullFieldException.printStackTrace();
                                return;
                            } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                                exceptionWindow(indexOutOfBoundsException);
                                return;
                            }
                            p1VerifyMinions();
                            p2VerifyMinions();
                            verifyMana();
                            s1.close();
                        });
                        screen.getChildren().add(button);
                    }
                }
            }
            if(p2 instanceof Mage){            Stage s1 = new Stage();
                FlowPane screen = new FlowPane();
                screen.setPrefSize(1500,700);
                BackgroundImage b= new BackgroundImage(new Image("images/spellsBG.jpg"),
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                        new BackgroundSize(screen.getWidth(),screen.getHeight(), false, false, true, false));
                screen.setBackground(new Background(b));
                Scene scene= new Scene(screen);
                s1.setScene(scene);
                s1.show();
                ImageView opponent = null;
                if(p1 instanceof Mage)
                    opponent=new ImageView(new Image("images\\Jaina_Proudmoore.png",250,250,true,true));
                if(p1 instanceof Warlock)
                    opponent=new ImageView(new Image("images\\Guldan.png",250,250,true,true));
                if(p1 instanceof Priest)
                    opponent=new ImageView(new Image("images\\Anduin_Wrynn.png",250,250,true,true));
                if(p1 instanceof Paladin)
                    opponent=new ImageView(new Image("images\\Uther_Lightbringer.png",250,250,true,true));
                if(p1 instanceof Hunter)
                    opponent=new ImageView(new Image("images\\Rexxar.png",250,250,true,true));
                screen.getChildren().add(opponent);
                opponent.setOnMouseClicked(ee -> {
                    try {
                        ((Mage) p2).useHeroPower(p1);
                    } catch (NotEnoughManaException notEnoughManaException) {
                        exceptionWindow(notEnoughManaException);
                        notEnoughManaException.printStackTrace();
                        return;
                    } catch (HeroPowerAlreadyUsedException heroPowerAlreadyUsedException) {
                        exceptionWindow(heroPowerAlreadyUsedException);
                        heroPowerAlreadyUsedException.printStackTrace();
                        return;
                    } catch (NotYourTurnException notYourTurnException) {
                        notYourTurnException.printStackTrace();
                        return;
                    } catch (FullHandException fullHandException) {
                        exceptionWindow(fullHandException);
                        fullHandException.printStackTrace();
                        return;
                    } catch (CloneNotSupportedException cloneNotSupportedException) {
                        exceptionWindow(cloneNotSupportedException);
                        cloneNotSupportedException.printStackTrace();
                        return;
                    } catch (FullFieldException fullFieldException) {
                        exceptionWindow(fullFieldException);
                        fullFieldException.printStackTrace();
                        return;
                    } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                        exceptionWindow(indexOutOfBoundsException);
                        return;
                    }
                    s1.close();
                    verifyHeroP2();
                    verifyHeroP1();
                    verifyMana();
                });

                for (Node cur : p1Field.getChildren()) {
                    if (!(cur instanceof ImageView) && cur.isVisible()) {
                        Minion target = ((minionButton) cur).getMinion();
                        minionButton button = new minionButton(target);
                        button.setOnMouseClicked(ee -> {
                            minionTarget = target;
                            try {
                                ((Mage) p2).useHeroPower(target);
                            } catch (NotEnoughManaException notEnoughManaException) {
                                exceptionWindow(notEnoughManaException);
                                notEnoughManaException.printStackTrace();
                                return;
                            } catch (HeroPowerAlreadyUsedException heroPowerAlreadyUsedException) {
                                exceptionWindow(heroPowerAlreadyUsedException);
                                heroPowerAlreadyUsedException.printStackTrace();
                                return;
                            } catch (NotYourTurnException notYourTurnException) {
                                notYourTurnException.printStackTrace();
                                return;
                            } catch (FullHandException fullHandException) {
                                exceptionWindow(fullHandException);
                                fullHandException.printStackTrace();
                                return;
                            } catch (CloneNotSupportedException cloneNotSupportedException) {
                                exceptionWindow(cloneNotSupportedException);
                                cloneNotSupportedException.printStackTrace();
                                return;
                            } catch (FullFieldException fullFieldException) {
                                exceptionWindow(fullFieldException);
                                fullFieldException.printStackTrace();
                                return;
                            } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                                exceptionWindow(indexOutOfBoundsException);
                                return;
                            }
                            p1VerifyMinions();
                            p2VerifyMinions();
                            verifyMana();
                            s1.close();
                        });
                        screen.getChildren().add(button);
                    }}

            }


        });



        BorderPane gamescreen= new BorderPane();

        //Opponent Layout
         p2Area= new BorderPane();
        //the hand

        //Validate to be added and the get target method to be implemented
        Button inv= new Button();
        inv.setPrefSize(50,200);
        inv.setMinSize(50,200);
        inv.setVisible(true);

        playerDraw(p2hand,p2Field,p2,p1,p1Field);
        Button test= new Button();
        test.setPrefSize(0.1,430);
        test.setVisible(false);

       p2Field.getChildren().add(p2Icon);
        p2Field.getChildren().add(p2Power);
        p2Field.getChildren().add(test);
        p2hand.setMaxSize(580,200);
        p2Area.setLeft(p2hand);
        HBox bottom=new HBox();
        p2Mana=new Button("Current Mana:"+p2.getCurrentManaCrystals()+"\nMax Mana:"+ p2.getTotalManaCrystals()+"\nCards Left:"+p2.getDeck().size());
        p2Mana.setMinWidth(100);

        p2Area.setBottom(bottom);
        p2Area.setRight(null);
        gamescreen.setTop(p2Area);

        //Current Layout
         p1Area= new BorderPane();
        //the place where the hero is


        //Validate to be added and the get target method to be implemented
        playerDraw(p1hand,p1Field,p1,p2,p2Field);
        minionButton test2=new minionButton(new Icehowl());

        test2.setMinSize(200,192);
        test2.setVisible(false);


        oppHand.setMaxSize(580,192);
        p1hand.setMaxSize(580,192);
        p1Area.setLeft(p1hand);
        HBox top=new HBox();
        p1Mana=new Button("Current Mana:"+p1.getCurrentManaCrystals()+"\nMax Mana:"+ p1.getTotalManaCrystals()+"\nCards Left:"+p1.getDeck().size());
        p1Mana.setMinWidth(100);
        verifyMana();
       // p1Mana.setShape(new Circle(20));



        p1Area.setRight(null);
        gamescreen.setBottom(p1Area);

        //Field Layout
        BorderPane fieldArea= new BorderPane();


        fieldArea.setTop(p2Field);
        fieldArea.setBottom(p1Field);
        VBox V=new VBox();
        p2Mana.setMinSize(200,75);  // change the p1mana and p2mana size from (200,100) to (200,75)
        p1Mana.setMinSize(200,75);
        p2Mana.setBackground(new Background(new BackgroundImage(new Image("images/resources/Border.png"),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(200,100, false, false, false, false))));
        p1Mana.setBackground(new Background(new BackgroundImage(new Image("images/resources/Border.png"),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(200,100, false, false, false, false))));
        p2Mana.setStyle("-fx-font-family: Sketch Gothic School;");
       // p2Mana.setMinWidth(100);
        verifyMana();
        //p2Mana.setShape(new Circle(20));
//        p2Mana.setStyle("    -fx-text-fill: rgb(72, 47, 0);\n" +
//                "    -fx-border-color: rgb(72, 47, 0);\n" +
//                "    -fx-border-radius: 15;\n" +
//                "-fx-background-radius: 16.4, 15;"+
//                "-fx-background-color: rgb(215, 138, 16);");
        Button exit= new Button("exit");
       end.setOnMouseEntered(e->{end.setEffect(new InnerShadow(100,Color.GRAY));});
       end.setOnMouseExited(e->{end.setEffect(new InnerShadow(0,Color.BLACK));});
        V.getChildren().add(exit);
       V.getChildren().add(p2Mana);
        V.getChildren().add(end);
        V.getChildren().add(p1Mana);
        VBox exitA= new VBox();
        exitA.setMaxSize(100,50);
        end.setFitHeight(50);    //add these 2 lines right after part where end button is created
        end.setFitWidth(200);

        exit.setBackground(new Background(new BackgroundImage(new Image("images/resources/Border.png"),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(200,100, false, false, false, false))));
        exit.setPrefSize(200,100);
        exit.setOnMouseEntered(e->{exit.setEffect(new InnerShadow(100,Color.GRAY));});
        exit.setOnMouseExited(e->{exit.setEffect(new InnerShadow(0,Color.BLACK));});
        exit.setOnMouseClicked(e->stage.close());
        fieldArea.setRight(V);
        fieldArea.setMinSize(1360,384);
        gamescreen.setCenter(fieldArea);

        end.setOnMouseReleased(e->{end.setEffect(new InnerShadow(100,Color.WHITESMOKE));});

        gamescene= new Scene(gamescreen,1360,768);
        stage.setScene(gamescene);
        gamescene.setCursor(new ImageCursor(new Image("images\\mouse.png",250,250,true,true)));
        end.setOnMouseClicked(e-> {
            gamescene.setCursor(new ImageCursor(new Image("images\\mouse.png")));
            Targeting=false;
            playOnce("sounds/end.wav");
            verifyHeroP1();
            verifyHeroP2();
            verifyMana();
            p2VerifyMinions();
            p1VerifyMinions();
            Boolean draw=true;
            cardFlip();
            end.setEffect(new InnerShadow(100,Color.BLACK));
            verifyHeroP2();
            verifyHeroP1();
            p1VerifyMinions();
            p2VerifyMinions();
            if(p1==game.getCurrentHero()){
                try {
                    p1.endTurn();
                } catch (FullHandException fullHandException) {
                    Card x=fullHandException.getBurned();
                    fullHandException=new FullHandException("You Have a Full Hand!!\n"+x.getName()+"\n"+x.getManaCost()+"\n"+x.getRarity(),x);
                    exceptionWindow(fullHandException);
                    draw=false;

                } catch (CloneNotSupportedException cloneNotSupportedException) {
                    cloneNotSupportedException.printStackTrace();
                }
                verifyMana();
                verifyHeroP2();
                verifyHeroP1();
                if(!p2.getDeck().isEmpty()&& draw)
                    endTurnDraw(p2,p1,p2hand,p2Field,p1Field);
            }
            else{
                try {
                    p2.endTurn();
                } catch (FullHandException fullHandException) {
                    Card x=fullHandException.getBurned();
                    fullHandException=new FullHandException("You Have a Full Hand!!\n"+x.getName()+"\n"+x.getManaCost()+"\n"+x.getRarity(),x);
                    exceptionWindow(fullHandException);
                    draw=false;

                } catch (CloneNotSupportedException cloneNotSupportedException) {
                    cloneNotSupportedException.printStackTrace();
                }
                verifyMana();
                verifyHeroP2();
                verifyHeroP1();
                if(!p1.getDeck().isEmpty()&& draw)
                    endTurnDraw(p1,p2,p1hand,p1Field,p2Field);

            }
            cardFlip();});

        BackgroundImage myBI= new BackgroundImage(new Image("images/board.png"),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(stage.getWidth(),stage.getHeight(), false, false, true, false));
        gamescreen.setBackground(new Background(myBI));
        music("sounds/Game.wav");
        gamescreen.setMinSize(400, 200);
        gamescreen.setPadding(new Insets(10, 10, 10, 10));
        //stage.setFullScreen(true);
       // stage.setMaximized(true);
        System.out.println(stage.getHeight());
        System.out.println(stage.getWidth());
        cardFlip();

        p1Field.setHgap(5);
        p2Field.setHgap(5);
            stage.setOnHiding(e->e.consume());
            stage.setIconified(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setFullScreenExitHint("");
    stage.setMaximized(true);
    stage.show();
    test2.setPrefSize(50,50);
        Button ad=new Button();
        ad.setVisible(false);
        ad.setPrefSize(0.1,450);
        top.setMaxSize(50,50);
        top.getChildren().add(test2);
        //p1Area.setTop(top);
        p1Field.getChildren().add(p1Icon);
        p1Field.getChildren().add(p1Power);


        p1Field.getChildren().add(ad);


    }


    @Override
    public void onGameOver() {
        clip.stop();
        music("sounds/victory.wav");
        String winner = null;
        String winnerName = null;
        ImageView icon=new ImageView();
        if(p1.getCurrentHP()!=0){
            winner="Player 1";
            if(p1 instanceof Mage){
                icon.setImage(new Image("images\\Jaina_Proudmoore.png",250,250,true,true));
                winnerName="Jaina Proudmoore";}
            else if(p1 instanceof Warlock){
                icon.setImage(new Image("images\\Guldan.png",250,250,true,true));
                winnerName="Gul'dan";}
            else if(p1 instanceof Priest){
                icon.setImage(new Image("images\\Anduin_Wrynn.png",250,250,true,true));
                winnerName="Anduin Wrynn";}
            else if(p1 instanceof Paladin){
                icon.setImage(new Image("images\\Uther_Lightbringer.png",250,250,true,true));
                winnerName="Uther Lightbringer";}
            else{
                icon.setImage(new Image("images\\Rexxar.png",250,250,true,true));
                winnerName="Rexxar";}
        }
         if (p1.getCurrentHP()==0){
            winner="Player 2";
            if(p2 instanceof Mage){
                icon.setImage(new Image("images\\Jaina_Proudmoore.png",250,250,true,true));
                winnerName="Jaina Proudmoore";}
            else if(p2 instanceof Warlock){
                icon.setImage(new Image("images\\Guldan.png",250,250,true,true));
                winnerName="Gul'dan";}
            else if(p2 instanceof Priest){
                icon.setImage(new Image("images\\Anduin_Wrynn.png",250,250,true,true));
                winnerName="Anduin Wrynn";}
            else if(p2 instanceof Paladin){
                icon.setImage(new Image("images\\Uther_Lightbringer.png",250,250,true,true));
                winnerName="Uther Lightbringer";}
            else if(p2 instanceof Hunter){
                icon.setImage(new Image("images\\Rexxar.png",250,250,true,true));
                winnerName="Rexxar";}}
            Label l= new Label("WINNER:"+winner);
            Label l2= new Label(winnerName);
            stage.close();
            Stage s1=new Stage();
            s1.initModality(Modality.APPLICATION_MODAL);
            s1.show();
            s1.setMinWidth(300);
            s1.setMinHeight(300);
            VBox v = new VBox();
            BackgroundImage b= new BackgroundImage(new Image("images/878332.jpg"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                    new BackgroundSize(s1.getWidth(),s1.getHeight(), false, false, true, false));
            v.setBackground(new Background(b));
            v.setAlignment(Pos.CENTER);
            Scene sc=new Scene(v,400,400);
            Button close= new Button("CLOSE");
            close.setAlignment(Pos.CENTER);
            close.setOnMouseClicked(e1-> s1.close());
            v.getChildren().add(l);
            v.getChildren().add(l2);
            v.getChildren().add(icon);
            v.getChildren().add(close);
            Button play= new Button("play again");
            play.setOnMouseClicked(e->{
                s1.close();
                clip.stop();
                start(new Stage());
            });
        v.getChildren().add(play);
            s1.setScene(sc);

            p1Field= new FlowPane();
            p1hand=new HBox();
            p1Area=new BorderPane();
            p1Mana=new Button();

        p2Field= new FlowPane();
        p2hand=new HBox();
        p2Area=new BorderPane();
        p2Mana=new Button();

    

}
public void cardFlip(){
        if(p1==game.getCurrentHero()){
            oppHand= new HBox();
            oppHand.setSpacing(5);
            for(Card a:p2.getHand()){
                ImageView cur= new ImageView(new Image(getHeroBack(p2),130,200,false,true));
                oppHand.getChildren().add(cur);
            }
            p2Area.setLeft(oppHand);
            p1Area.setLeft(p1hand);
        }
    if(p2==game.getCurrentHero()){
        oppHand= new HBox();
        oppHand.setSpacing(5);
        for(Card a:p1.getHand()){
            ImageView cur= new ImageView(new Image(getHeroBack(p1),130,200,false,true));
            oppHand.getChildren().add(cur);
        }
        p2Area.setLeft(p2hand);
        p1Area.setLeft(oppHand);
    }
}


public String getHeroBack(Hero p){
        if(p instanceof Paladin)
            return "images\\cardBacks\\paladin.png";
        if(p instanceof Mage)
            return "images\\cardBacks\\mage.png";
        if(p instanceof Priest)
            return "images\\cardBacks\\priest.png";
        if(p instanceof Warlock)
            return  "images\\cardBacks\\warlock.png";
        if(p instanceof Hunter)
            return "images\\cardBacks\\hunter.png";
    return "images\\whitepage.png";

}
public void playOnceVoice(String filePath){
        if(Voice!=null)
            if(Voice.isRunning()||Voice.isActive()||Voice.isOpen())
                Voice.stop();

    try {

        AudioInputStream ding = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        Voice= AudioSystem.getClip();
        if(Voice.isRunning()){
            System.out.println(Voice.isActive());
            Voice.stop();
        }
        Voice.open(ding);
        Voice.start();
    } catch (UnsupportedAudioFileException | LineUnavailableException | IOException error) {
        error.printStackTrace();
    }
}
    public void playOnce(String filePath){


        try {

            AudioInputStream ding = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
            Fx= AudioSystem.getClip();
            if(Fx.isRunning()){
                System.out.println(Fx.isActive());
                Fx.stop();
            }
            Fx.open(ding);
            Fx.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException error) {
            error.printStackTrace();
        }
    }
    public void Verify(){
        verifyHeroP2();
        verifyHeroP1();
        p1VerifyMinions();
        p2VerifyMinions();
        if(!Targeting)
        gamescene.setCursor(new ImageCursor(new Image("images\\mouse.png",250,250,true,true)));
    }

}