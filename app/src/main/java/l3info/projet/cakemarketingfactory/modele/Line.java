package l3info.projet.cakemarketingfactory.modele;

import java.io.Serializable;
import java.util.ArrayList;

public class Line implements Serializable {
    private int cakeId;
    private ArrayList<Integer> machineLevels;

    public Line(int cakeId, ArrayList<Integer> machineLevels){
        this.cakeId = cakeId;
        this.machineLevels = machineLevels;
    }

    public Line(int beltLevel, int robotLevel, int ovenLevel, int productId){
        this.cakeId = productId;
        ArrayList<Integer> machineLevels = new ArrayList<Integer>();
        machineLevels.add(beltLevel);
        machineLevels.add(robotLevel);
        machineLevels.add(ovenLevel);
        this.machineLevels = machineLevels;
    }

    public int getCakeId() { return cakeId; }
    public ArrayList<Integer> getAllMachineLevels() { return machineLevels; }
    public int getMachineLevel( int index) { return machineLevels.get(index); }

    public void setCakeId(int cakeId) { this.cakeId = cakeId; }
    public void setMachineLevel( int index, int lvl ) { this.machineLevels.set(index,lvl); }

    public int getProduction(){ return this.machineLevels.get(0)+this.machineLevels.get(1)+this.machineLevels.get(2); }

    @Override
    public String toString() {
        return "Line{" +
                "cakeId=" + cakeId +
                ", machineLevels=" + machineLevels +
                '}';
    }
}
