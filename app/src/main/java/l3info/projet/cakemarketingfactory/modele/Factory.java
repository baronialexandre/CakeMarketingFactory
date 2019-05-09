package l3info.projet.cakemarketingfactory.modele;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("WeakerAccess")
public class Factory implements Serializable {
    private int factorySpot;
    private int stockMax;
    private ArrayList<Integer> stocks;
    private ArrayList<Line> lineList;

    public Factory(int factorySpot, int stockMax, ArrayList<Integer> stocks, ArrayList<Line> lineList ){
        this.factorySpot = factorySpot;
        this.stockMax = stockMax;
        this.stocks = stocks;
        this.lineList = lineList;
    }

    //Constructeur pour le world
    public Factory(Integer factorySpot) {
        this.factorySpot = factorySpot;
        this.stockMax = 0;
        this.stocks = null;
        this.lineList = null;
    }

    public int getFactorySpot() { return factorySpot; }
    public int getStockMax() { return stockMax; }
    public ArrayList<Integer> getStocks() { return stocks; }
    public ArrayList<Line> getLineList() { return lineList; }
    public Line getLine(int index) { return lineList.get(index); }

    public void setFactorySpot(int factorySpot) { this.factorySpot = factorySpot; }
    public void setStockMax(int stockMax) { this.stockMax = stockMax; }
    public void setStocks(ArrayList<Integer> stocks) { this.stocks = stocks; }
    public void setLineList(ArrayList<Line> lineList) { this.lineList = lineList; }
}
