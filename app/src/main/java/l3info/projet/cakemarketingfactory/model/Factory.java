package l3info.projet.cakemarketingfactory.model;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

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
        this.currentStocks = new ArrayList<>();
        this.lines = new ArrayList<>();
        for(int i = 0; i<3; ++i) {
            this.lines.add(null);
            this.currentStocks.add(0);
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "Factory{" +
                "factorySpot=" + factorySpot +
                ", capacityLevel=" + capacityLevel +
                ", currentStocks=" + currentStocks +
                ", lines=" + lines +
                '}';
    }

    public static int getCapacityPrice(int capacityLevel)
    {
        return capacityLevel*2500;
    }

    public static long getFactoryPrice(int factorySpot)
    {
        double expo = Math.exp((double)factorySpot);
        long price = (long) (factorySpot*300*expo);
        price = ((price+100)/100)*100;
        return price;
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
