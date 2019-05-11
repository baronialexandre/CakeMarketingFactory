package l3info.projet.cakemarketingfactory.model;

import java.io.Serializable;

public class Demand implements Serializable
{
    private String demandDate;
    private int price;

    public Demand(String demandDate, int price) {
        this.demandDate = demandDate;
        this.price = price;
    }

    public String getDemandDate() {
        return demandDate;
    }

    public void setDemandDate(String demandDate) {
        this.demandDate = demandDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
