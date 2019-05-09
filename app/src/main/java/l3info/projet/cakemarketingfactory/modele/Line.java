package l3info.projet.cakemarketingfactory.modele;

import java.io.Serializable;
import java.util.ArrayList;

public class Line implements Serializable {
    private int cakeId;
    private ArrayList<Integer> lvl;

    public Line(int cakeId, ArrayList<Integer> lvl){
        this.cakeId = cakeId;
        this.lvl = lvl;
    }

    public int getCakeId() { return cakeId; }
    public ArrayList<Integer> getAllLvl() { return lvl; }
    public int getLvl( int index) { return lvl.get(index); }

    public void setCakeId(int cakeId) { this.cakeId = cakeId; }
    public void setLvl( int index, int lvl ) { this.lvl.set(index,lvl); }

    public int getProduction(){ return this.lvl.get(0)+this.lvl.get(1)+this.lvl.get(2); }
}
