package ru.javarush.golf.sobolevskiy.islandmodel.entities;

import ru.javarush.golf.sobolevskiy.islandmodel.entities.settings.Settings;
import ru.javarush.golf.sobolevskiy.islandmodel.entities.world.Area;
import ru.javarush.golf.sobolevskiy.islandmodel.entities.world.World;

public record Game(World world) {

    public void showStatistics() {
        Area[][] areas = world.getAreas();
        for (int i = 0; i < areas.length; i++) {
            for (int j = 0; j < areas[i].length; j++) {
                int rowNumber = String.valueOf(Settings.get().getMapRows()).length();
                int colNumber = String.valueOf(Settings.get().getMapCols()).length();
                System.out.printf("%0" + rowNumber + "d/%0" + colNumber + "d    %s\n", i + 1, j + 1, areas[i][j]);
            }
        }
        System.out.println("=".repeat(200));
    }
}
