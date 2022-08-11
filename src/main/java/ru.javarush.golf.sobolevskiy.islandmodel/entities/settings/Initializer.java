package ru.javarush.golf.sobolevskiy.islandmodel.entities.settings;

import ru.javarush.golf.sobolevskiy.islandmodel.entities.world.World;
import ru.javarush.island.volokitin.creators.WorldCreator;

public class Initializer {
    public World createWorld() {
        WorldCreator worldCreator = new WorldCreator();
        return worldCreator.createWorld();
    }
}
