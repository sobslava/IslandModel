package ru.javarush.golf.sobolevskiy.islandmodel.entities.world;

public class World {
    private final Area[][] areas;

    public World(int rows, int cols) {
        areas = new Area[rows][cols];
    }

    public Area[][] getAreas() {
        return areas;
    }
}
