package ru.javarush.golf.sobolevskiy.islandmodel.entities.settings;

import ru.javarush.golf.sobolevskiy.islandmodel.creators.WorldCreator;
import ru.javarush.golf.sobolevskiy.islandmodel.entities.world.World;

public class Initializer {
    public World createWorld() {
        WorldCreator worldCreator = new WorldCreator();
        return worldCreator.createWorld();
    }
}
