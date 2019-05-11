package l3info.projet.cakemarketingfactory.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Market implements Serializable
{
    public ArrayList<ArrayList<Demand>> demands;

    public Market()
    {
        demands = new ArrayList<>();
        for(int i = 0; i < 3; i++)
        {
            demands.add(i, new ArrayList<>());
        }
    }

    public void addDemand(int productId, Demand demand)
    {
        demands.get(productId).add(demand);
    }

    public void order()
    {
        for(ArrayList<Demand> demandList : demands)
            Collections.reverse(demandList);
    }
}
