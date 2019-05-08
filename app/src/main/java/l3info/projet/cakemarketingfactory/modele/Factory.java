package l3info.projet.cakemarketingfactory.modele;

import java.util.ArrayList;

public class Factory {
    private int factorySlot;
    private int stockMax;
    private ArrayList<Integer> stocks;
    private ArrayList<Line> lineList;

    public Factory(int factorySlot, int stockMax, ArrayList<Integer> stocks, ArrayList<Line> lineList ){
        this.factorySlot = factorySlot;
        this.stockMax = stockMax;
        this.stocks = stocks;
        this.lineList = lineList;
    }

    public int getFactorySlot() { return factorySlot; }
    public int getStockMax() { return stockMax; }
    public ArrayList<Integer> getStocks() { return stocks; }
    public ArrayList<Line> getLineList() { return lineList; }

    public void setFactorySlot(int factorySlot) { this.factorySlot = factorySlot; }
    public void setStockMax(int stockMax) { this.stockMax = stockMax; }
    public void setStocks(ArrayList<Integer> stocks) { this.stocks = stocks; }
    public void setLineList(ArrayList<Line> lineList) { this.lineList = lineList; }
}
