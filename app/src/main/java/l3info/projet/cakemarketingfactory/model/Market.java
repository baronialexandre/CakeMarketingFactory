package l3info.projet.cakemarketingfactory.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Market implements Serializable
{
    public ArrayList<Demand>[] demands;

    public Market()
    {
        demands = new ArrayList[3];
        for(int i = 0; i < 3; i++)
        {
            demands[i] = new ArrayList<>();
        }
    }

    public void addDemand(int productId, Demand demand)
    {
        demands[productId].add(demand);
    }

    public void order()
    {
        for(ArrayList<Demand> demandList : demands)
            Collections.reverse(demandList);
    }
}
