package ru.javarush.golf.sobolevskiy.islandmodel.entities.organisms.plants;

import ru.javarush.golf.sobolevskiy.islandmodel.entities.organisms.Organism;
import ru.javarush.golf.sobolevskiy.islandmodel.entities.settings.Settings;
import ru.javarush.golf.sobolevskiy.islandmodel.entities.world.Area;
import ru.javarush.golf.sobolevskiy.islandmodel.factories.Factories;
import ru.javarush.golf.sobolevskiy.islandmodel.util.Randomizer;

public class Plant extends Organism {
    public Plant() {
        super();
    }

    @Override
    public void multiply(Area area) {
        safeMultiply(area);
    }

    private void safeMultiply(Area area) {
        area.getLock().lock();
        try {
            int newPlantsQuantity = this.getChildrenQuantity(area);
            if (newPlantsQuantity > 0) {
                for (int i = 0; i < newPlantsQuantity; i++) {
                    if (Randomizer.getProbability(5)) {
                        Organism newPlant = Factories.createOrganismByType(this.getType());
                        area.addInhabitant(this.getType(), newPlant);
                    }
                }
            }
        } finally {
            area.getLock().unlock();
        }
    }

    public void growUp(Area area) {
        safeGrowUp(area);
    }

    private void safeGrowUp(Area area) {
        area.getLock().lock();
        try {
            int growUpPercent = Settings.get().getPlantGrowUpPercent();
            double weightIncrement = this.getWeight() * growUpPercent / 100;
            this.setWeight(this.getWeight() + weightIncrement);
        } finally {
            area.getLock().unlock();
        }
    }
}
