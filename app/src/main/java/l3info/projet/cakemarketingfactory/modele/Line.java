package l3info.projet.cakemarketingfactory.modele;

import java.io.Serializable;

public class Line implements Serializable {
    private int cakeId;
    private int lvlBelt;
    private int lvlRobot;
    private int lvlOven;

    public Line(int cakeId, int lvlBelt, int lvlRobot,int lvlOven){
        this.cakeId = cakeId;
        this.lvlBelt = lvlBelt;
        this.lvlRobot = lvlRobot;
        this.lvlOven = lvlOven;
    }

    public int getCakeId() { return cakeId; }
    public int getLvlBelt() { return lvlBelt; }
    public int getLvlRobot() { return lvlRobot; }
    public int getLvlOven() { return lvlOven; }

    public void setCakeId(int cakeId) { this.cakeId = cakeId; }
    public void setLvlBelt(int lvlBelt) { this.lvlBelt = lvlBelt; }
    public void setLvlRobot(int lvlRobot) { this.lvlRobot = lvlRobot; }
    public void setLvlOven(int lvlOven) { this.lvlOven = lvlOven; }

    public int getProduction(){ return this.lvlBelt+this.lvlRobot+this.lvlOven; }
}
