package view;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.cards.minions.Minion;

public class minionButton extends Button {
    int hp;
    int attack;

    boolean divine;
    boolean taunt;
    String style;
    String name;
    String description;

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

   public minionButton(Minion minion){
	   this.minion=minion;
        //ImageView imageFile = new ImageView(new Image(getImage()));
	   Image imageFile = new Image("images\\cards\\normal\\Icehowl.png",100,100,false,false);
       ImageView imageDecline = new ImageView(imageFile);
	   this.setGraphic(imageDecline);
       this.attack=minion.getAttack();
       this.hp=minion.getCurrentHP();
	   this.divine=minion.isDivine();
	   //this.setPrefSize(100,100);
	   this.setText("Attack: "+minion.getAttack()+"       Health: "+minion.getCurrentHP() +"\n   Divine: "+String.valueOf(minion.isDivine()));
	   this.setWrapText(true);
	   this.style =
            "-fx-alignment: center;" +
            //"-fx-text-alignment: justify;"+
            "-fx-content-display: top;"+ 
            //"-fx-padding: 10px;" +
            "-fx-font-size: 10px;"+
            "-fx-background-position: center;";
	    this.setStyle(style);
	    this.setLayoutX(200);
        this.setLayoutY(100);
        //this.verifyMinion();
	    //this.setMinSize(420,600); this.setMaxSize(420,600);
    }
    public void verifyMinion() {

        this.setText("Attack: "+minion.getAttack()+"       Health: "+minion.getCurrentHP() +"\n   Divine: "+String.valueOf(minion.isDivine()));

  
    }
    public String getImage(){
        Minion cur= minion;
        if(cur.getName().equals("Goldshire Footman"))
            return "images\\cards\\normal\\GoldshireFootman.png";

        if(cur.getName().equals("Bloodfen Raptor"))
            return "images\\cards\\normal\\BloodfenRaptor.jpg";

        if(cur.getName().equals("Stonetusk Boar"))
            return "images\\cards\\normal\\StonetuskBoar.png";

        if(cur.getName().equals("Frostwolf Grunt"))
            return "images\\cards\\normal\\FrostwolfGrunt.png";

        if(cur.getName().equals("Wolfrider"))
            return "images\\cards\\normal\\Wolfrider.png";

        if(cur.getName().equals("Chilwind Yeti"))
            return "images\\cards\\normal\\ChillwindYeti.jpg";

        if(cur.getName().equals("Boulderfist Ogre"))
            return "images\\cards\\normal\\BoulderfistOgre.jpg";

        if(cur.getName().equals("Core Hound"))
            return "images\\cards\\normal\\CoreHound.png";

        if(cur.getName().equals("Argent Commander"))
            return "images\\cards\\normal\\ArgentCommander.png";

        if(cur.getName().equals("Sunwalker"))
            return "images\\cards\\normal\\Sunwalker.png";

        if(cur.getName().equals("Chromaggus"))
            return "images\\cards\\normal\\Chromaggus.png";

        if(cur.getName().equals("The LichKing"))
            return "images\\cards\\normal\\TheLichKing.png";

        if(cur.getName().equals("Icehowl"))
            return "images\\cards\\normal\\Icehowl.png";

        if(cur.getName().equals("Colossus of the Moon"))
            return "images\\cards\\normal\\ColossusoftheMoon.png";

        if(cur.getName().equals("King Krush"))
            return "images\\cards\\normal\\KingKrush.png";

        if(cur.getName().equals("Kalycgos"))
            return "images\\cards\\normal\\Kalycgos.png";

        if(cur.getName().equals("Tirion Fordring"))
            return "images\\cards\\normal\\TirionFordring.png";

        if(cur.getName().equals("Prophet Velen"))
            return "images\\cards\\normal\\ProphetVelen.png";

        if(cur.getName().equals("Wilfred Fizzlebang"))
            return "images\\cards\\normal\\WilfredFizzlebang.png";


        return "images\\whitepage.png";

    }
    
}
