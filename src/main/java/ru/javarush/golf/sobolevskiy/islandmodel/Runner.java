package ru.javarush.golf.sobolevskiy.islandmodel;

import ru.javarush.golf.sobolevskiy.islandmodel.entities.Game;
import ru.javarush.golf.sobolevskiy.islandmodel.entities.settings.Initializer;
import ru.javarush.golf.sobolevskiy.islandmodel.entities.world.World;
import ru.javarush.golf.sobolevskiy.islandmodel.services.GameWorker;


public class Runner {
    public static void main(String[] args) {
        Initializer initializer = new Initializer();
        World world = initializer.createWorld();
        Game game = new Game(world);
        GameWorker gameWorker = new GameWorker(game);
        gameWorker.start();
    }
}
