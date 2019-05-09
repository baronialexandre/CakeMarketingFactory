package l3info.projet.cakemarketingfactory.modele;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("WeakerAccess")
public class Factory implements Serializable {
    private int factorySpot;
    private int capacityLevel;
    private ArrayList<Integer> currentStocks;
    private ArrayList<Line> lines;

    public Factory(int factorySpot, int capacityLevel, ArrayList<Integer> currentStocks, ArrayList<Line> lineList ){
        this.factorySpot = factorySpot;
        this.capacityLevel = capacityLevel;
        this.currentStocks = currentStocks;
        this.lines = lineList;
    }

    //Constructeur pour le world
    public Factory(Integer factorySpot) {
        this.factorySpot = factorySpot;
        this.capacityLevel = 0;
        this.currentStocks = new ArrayList<Integer>();
        this.lines = new ArrayList<Line>();
        for(int i = 0; i<3; ++i) {
            this.lines.add(null);
            this.currentStocks.add(0);
        }
    }

    @Override
    public String toString() {
        return "Factory{" +
                "factorySpot=" + factorySpot +
                ", capacityLevel=" + capacityLevel +
                ", currentStocks=" + currentStocks +
                ", lines=" + lines +
                '}';
    }

    public int getFactorySpot() { return factorySpot; }
    public int getCapacityLevel() { return capacityLevel; }
    public ArrayList<Integer> getCurrentStocks() { return currentStocks; }
    public ArrayList<Line> getLines() { return lines; }
    public Line getLine(int index) { return lines.get(index); }

    public void setFactorySpot(int factorySpot) { this.factorySpot = factorySpot; }
    public void setCapacityLevel(int capacityLevel) { this.capacityLevel = capacityLevel; }
    public void setCurrentStocks(ArrayList<Integer> stocks) { this.currentStocks = stocks; }
    public void setLines(ArrayList<Line> lineList) { this.lines = lineList; }
}
