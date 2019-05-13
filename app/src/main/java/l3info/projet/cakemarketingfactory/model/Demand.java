package l3info.projet.cakemarketingfactory.model;

import java.io.Serializable;

public class Demand implements Serializable
{
    private int price;

    public Demand(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

}
