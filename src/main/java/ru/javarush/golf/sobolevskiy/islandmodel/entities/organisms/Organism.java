package ru.javarush.golf.sobolevskiy.islandmodel.entities.organisms;

import ru.javarush.golf.sobolevskiy.islandmodel.entities.organisms.animals.Animal;
import ru.javarush.golf.sobolevskiy.islandmodel.entities.settings.Settings;
import ru.javarush.golf.sobolevskiy.islandmodel.entities.world.Area;
import ru.javarush.golf.sobolevskiy.islandmodel.util.Randomizer;

public abstract class Organism {
    private final String type = this.getClass().getSimpleName();
    private double weight;

    protected Organism() {
        OrganismsCommonSpecs organismCommonSpecs = Settings.get().getOrganismCommonSpecsByType(this.type);
        this.weight = Randomizer.getRandom(organismCommonSpecs.getMaxWeight() / 2.0D, organismCommonSpecs.getMaxWeight());
    }

    public abstract void multiply(Area area);

    public abstract void growUp(Area area);

    public String getType() {
        return type;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getChildrenQuantity(Area area) {
        Settings settings = Settings.get();
        Integer childrenQuantity = settings.getOrganismsChildrenQuantity().get(getType());
        int maxQuantityInArea = settings.getOrganismCommonSpecsByType(getType()).getMaxQuantity();
        int newBornQuantity = Randomizer.getRandom(0, Math.min(childrenQuantity, maxQuantityInArea));
        int sameOrganismTypeQuantity = area.getInhabitants().get(this.getType()).toArray().length;

        return Math.min(maxQuantityInArea - sameOrganismTypeQuantity, newBornQuantity);
    }

    public void starve(Area area) {
        safeStarve(area);
    }

    private void safeStarve(Area area) {
        area.getLock().lock();
        try {
            OrganismsCommonSpecs organismCommonSpecs = Settings.get().getOrganismCommonSpecsByType(this.getType());
            int weightLossPercent;
            if (this instanceof Animal) {
                weightLossPercent = 20;
            } else {
                weightLossPercent = 1;
            }

            double weightLoss = organismCommonSpecs.getMaxWeight() * weightLossPercent / 100;
            this.setWeight(this.getWeight() - weightLoss);
            if (this.getWeight() < organismCommonSpecs.getMaxWeight() * 0.3) {
                area.getInhabitants().get(this.getType()).remove(this);
            }
        } finally {
            area.getLock().unlock();
        }
    }
}
