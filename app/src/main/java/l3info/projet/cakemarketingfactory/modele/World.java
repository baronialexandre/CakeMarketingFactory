package l3info.projet.cakemarketingfactory.modele;

import java.io.Serializable;
import java.util.ArrayList;

public class World implements Serializable {
    public ArrayList<Factory> factories = new ArrayList<>();

    public World(ArrayList<Integer> factorySpots) {
        for (Integer factorySpot : factorySpots) {
            factories.add(new Factory(factorySpot));
        }
    }
}
