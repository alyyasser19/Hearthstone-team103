package view;
//change
import javafx.scene.control.Button;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import model.cards.minions.Minion;
import model.cards.minions.MinionListener;

public class minionButton extends Button implements Cloneable{
    int hp;
    int attack;

    boolean divine;
    boolean taunt;
    String style;
    String name;
    String description;
    String tauntt="";
    MinionListener listener;

    Minion minion;

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public Minion getMinion() {
        return minion;
    }

    public void setMinion(Minion minion) {
        this.minion = minion;
    }

    public int getHp() {
        return hp;
    }
    public MinionListener getListener(){return listener;}

   public minionButton(Minion minion){
	   this.minion=minion;
	   listener=minion.getListener();
        //ImageView imageFile = new ImageView(new Image(getImage()));
	   Image imageFile = new Image(getImage(),100,100,false,false);
       ImageView imageDecline = new ImageView(imageFile);
	   this.setGraphic(imageDecline);
       this.attack=minion.getAttack();
       this.hp=minion.getCurrentHP();
	   this.divine=minion.isDivine();
	   //this.setPrefSize(100,100);
       if(minion.isTaunt())
           tauntt="Taunt";
	   this.setText( "Name:"+" "+ minion.getName()+"\n" + "Attack: "+minion.getAttack()+"       Health: "+minion.getCurrentHP() +"\n"+""+ tauntt);
	   this.setWrapText(true);
	   this.style =
            "-fx-alignment: center;" +
            //"-fx-text-alignment: justify;"+
            "-fx-content-display: top;"+
            //"-fx-padding: 10px;" +
            "-fx-font-size: 12px;"+
            "-fx-background-position: center;\n"+
                    "    -fx-border-radius: 30;"+
                    "-fx-background-radius: 30, 30;"+
                    "-fx-background-color: rgb(151, 138, 110);"+
                "    -fx-border-color: rgb(12, 11, 10);";
	    this.setStyle(style);
	    this.setLayoutX(200);
        this.setLayoutY(100);
        //this.verifyMinion();
       this.setTextAlignment(TextAlignment.CENTER);
	    //this.setMinSize(420,600); this.setMaxSize(420,600);
       if(minion.isDivine()){
           this.setEffect(new InnerShadow(100, Color.GOLD));
       }
       if(minion.isSleeping()){
           this.setEffect(new InnerShadow(100, Color.BLACK));
       }
       //this.setBackground();
       this.setBackground(new Background(new BackgroundImage(new Image("images/minio.png"),
               BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
               new BackgroundSize(200,100, false, false, false, false))));
    if(minion.isSleeping())
        this.setEffect(new InnerShadow(300,Color.BLACK));
    if(minion.isSleeping() && minion.isDivine())
        this.setEffect(new InnerShadow(300,Color.DARKGOLDENROD));

    }
    public void verifyMinion() {
        if(!minion.isSleeping() || minion.isAttacked())
            this.setEffect(new InnerShadow(0,Color.BLACK));
        if(minion.getName().equals("Sheep")){
            ImageView imageDecline = new ImageView(new Image("images\\cards\\normal\\Sheep.jpg",100,100,false,false));
            this.setGraphic(imageDecline);
        }
        if(minion.isDivine()){
            this.setEffect(new InnerShadow(100, Color.GOLD));}else
            this.setEffect(new InnerShadow(0, Color.WHITE));
        hp=minion.getCurrentHP();

        this.setText( "Name:"+" "+ minion.getName()+"\n" + "Attack: "+minion.getAttack()+"       Health: "+minion.getCurrentHP() +"\n"+""+ tauntt);
        if(minion.isSleeping() || minion.isAttacked())
            this.setEffect(new InnerShadow(300,Color.BLACK) );
        if((minion.isSleeping() || minion.isAttacked()) && minion.isDivine())
            this.setEffect(new InnerShadow(300,Color.DARKGOLDENROD));

  
    }
    public String getImage(){
        Minion cur= minion;
       if (cur.getName().equals("Silver Hand Recruit"))
        return "images\\cards\\normal\\SilverHand.jpg";

        if(cur.getName().equals("Sheep"))
            return "images\\cards\\normal\\Sheep.jpg";

        if(cur.getName().equals("Goldshire Footman"))
            return "images\\cards\\normal\\GoldshireFootman.png";

        if(cur.getName().equals("Bloodfen Raptor"))
            return "images\\cards\\normal\\BloodfenRaptor.jpg";

        if(cur.getName().equals("Stonetusk Boar"))
            return "images\\cards\\normal\\StonetuskBoar.jpg";

        if(cur.getName().equals("Frostwolf Grunt"))
            return "images\\cards\\normal\\FrostwolfGrunt.jpg";

        if(cur.getName().equals("Wolfrider"))
            return "images\\cards\\normal\\Wolfrider.jpg";

        if(cur.getName().equals("Chilwind Yeti"))
            return "images\\cards\\normal\\ChillwindYeti.jpg";

        if(cur.getName().equals("Boulderfist Ogre"))
            return "images\\cards\\normal\\BoulderfistOgre.jpg";

        if(cur.getName().equals("Core Hound"))
            return "images\\cards\\normal\\CoreHound.jpg";

        if(cur.getName().equals("Argent Commander"))
            return "images\\cards\\normal\\ArgentCommander.png";

        if(cur.getName().equals("Sunwalker"))
            return "images\\cards\\normal\\Sunwalker.jpg";

        if(cur.getName().equals("Chromaggus"))
            return "images\\cards\\normal\\Chromaggus.jpg";

        if(cur.getName().equals("The LichKing"))
            return "images\\cards\\normal\\TheLichKing.jpg";

        if(cur.getName().equals("Icehowl"))
            return "images\\cards\\normal\\Icehowl.png";

        if(cur.getName().equals("Colossus of the Moon"))
            return "images\\cards\\normal\\ColossusoftheMoon.jpg";

        if(cur.getName().equals("King Krush"))
            return "images\\cards\\normal\\KingKrush.jpg";

        if(cur.getName().equals("Kalycgos"))
            return "images\\cards\\normal\\Kalycgos.jpg";

        if(cur.getName().equals("Tirion Fordring"))
            return "images\\cards\\normal\\TirionFordring.jpg";

        if(cur.getName().equals("Prophet Velen"))
            return "images\\cards\\normal\\ProphetVelen.jpg";

        if(cur.getName().equals("Wilfred Fizzlebang"))
            return "images\\cards\\normal\\WilfredFizzlebang.jpg";


        return "images\\whitepage.png";

    }
    
}
