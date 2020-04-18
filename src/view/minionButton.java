package view;

import javafx.scene.control.Button;
import model.cards.minions.Minion;

public class minionButton extends Button {
    int hp;
    int attack;

    boolean divine;
    boolean taunt;

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
        this.attack=minion.getAttack();
        this.hp=minion.getCurrentHP();
        this.divine=minion.isDivine();
        this.taunt=minion.isTaunt();
        this.name=minion.getName();
        this.setPrefSize(100,100);
        this.setText(minion.getName());
    }
    public void verifyMinion(){

    }


    }
