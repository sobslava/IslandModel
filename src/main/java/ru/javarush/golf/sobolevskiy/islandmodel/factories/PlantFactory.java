package ru.javarush.golf.sobolevskiy.islandmodel.factories;

import ru.javarush.golf.sobolevskiy.islandmodel.entities.organisms.Organism;
import ru.javarush.golf.sobolevskiy.islandmodel.entities.organisms.plants.Plant;

public class PlantFactory implements ru.javarush.golf.sobolevskiy.islandmodel.factories.OrganismFactory {
    @Override
    public Organism createOrganism() {
        return new Plant();
    }
}
