package l3info.projet.cakemarketingfactory.modele;

import java.util.ArrayList;

public class World {
    public ArrayList<Factory> factories = new ArrayList<>();

    public World(ArrayList<Integer> factorySpots) {
        for (Integer factorySpot : factorySpots) {
            factories.add(new Factory(factorySpot));
        }
    }
}
