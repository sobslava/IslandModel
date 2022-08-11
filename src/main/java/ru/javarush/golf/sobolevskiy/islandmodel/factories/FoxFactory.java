package ru.javarush.golf.sobolevskiy.islandmodel.factories;

import ru.javarush.golf.sobolevskiy.islandmodel.entities.organisms.Organism;
import ru.javarush.golf.sobolevskiy.islandmodel.entities.organisms.animals.carnivores.Fox;

public class FoxFactory implements OrganismFactory {
    @Override
    public Organism createOrganism() {
        return new Fox();
    }
}
