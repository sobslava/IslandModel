package ru.javarush.golf.sobolevskiy.islandmodel.factories;

import ru.javarush.golf.sobolevskiy.islandmodel.entities.organisms.Organism;
import ru.javarush.golf.sobolevskiy.islandmodel.entities.organisms.animals.carnivores.Eagle;

public class EagleFactory implements OrganismFactory {
    @Override
    public Organism createOrganism() {
        return new Eagle();
    }
}
