package ru.javarush.golf.sobolevskiy.islandmodel.factories;

import ru.javarush.golf.sobolevskiy.islandmodel.entities.organisms.Organism;
import ru.javarush.golf.sobolevskiy.islandmodel.entities.organisms.animals.herbivores.Boar;

public class BoarFactory implements OrganismFactory {
    @Override
    public Organism createOrganism() {
        return new Boar();
    }
}
