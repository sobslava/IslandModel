package ru.javarush.golf.sobolevskiy.islandmodel.factories;

import ru.javarush.golf.sobolevskiy.islandmodel.entities.organisms.Organism;
import ru.javarush.golf.sobolevskiy.islandmodel.entities.organisms.animals.herbivores.Buffalo;

public class BuffaloFactory implements OrganismFactory {
    @Override
    public Organism createOrganism() {
        return new Buffalo();
    }
}
