package ru.javarush.golf.sobolevskiy.islandmodel.entities.organisms.animals;

import ru.javarush.golf.sobolevskiy.islandmodel.entities.organisms.Organism;
import ru.javarush.golf.sobolevskiy.islandmodel.entities.organisms.OrganismsCommonSpecs;
import ru.javarush.golf.sobolevskiy.islandmodel.entities.settings.Settings;
import ru.javarush.golf.sobolevskiy.islandmodel.entities.world.Area;
import ru.javarush.golf.sobolevskiy.islandmodel.factories.Factories;
import ru.javarush.golf.sobolevskiy.islandmodel.util.Randomizer;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class Animal extends Organism {

    public Animal() {
        super();
    }

    @Override
    public void multiply(Area area) {
        safeMultiply(area);
    }

    private void safeMultiply(Area area) {
        area.getLock().lock();
        try {
            int sameAnimalTypeQuantity = area.getInhabitants().get(this.getType()).toArray().length;
            int childrenQuantity = this.getChildrenQuantity(area);

            if (childrenQuantity > 0 && sameAnimalTypeQuantity > 1 && Randomizer.getProbability(50)) {
                for (int i = 0; i < childrenQuantity; i++) {
                    if (Randomizer.getProbability(90)) {
                        Organism newAnimal = Factories.createOrganismByType(this.getType());
                        area.addInhabitant(this.getType(), newAnimal);
                    }
                }
            }
        } finally {
            area.getLock().unlock();
        }
    }

    public boolean eat(Area area) {
        return safeEat(area);
    }

    private boolean safeEat(Area area) {
        area.getLock().lock();
        boolean ate = false;

        try {
            Settings settings = Settings.get();
            OrganismsCommonSpecs animalCommonSpecs = settings.getOrganismCommonSpecsByType(this.getType());

            double weightDiff = animalCommonSpecs.getMaxWeight() - this.getWeight();
            double foodRequired = Math.min(weightDiff, animalCommonSpecs.getMaxFoodRequired());
            if (foodRequired > 0) {
                Map<String, Integer> chanceToGetEatMap = settings.getChanceToGetEat().get(this.getType());
                Iterator<Map.Entry<String, Integer>> chanceToGetEatIterator = chanceToGetEatMap.entrySet().iterator();
                while (foodRequired > 0 && chanceToGetEatIterator.hasNext()) {
                    Map.Entry<String, Integer> chanceToGetEat = chanceToGetEatIterator.next();
                    String foodType = chanceToGetEat.getKey();
                    Integer chanceToEat = chanceToGetEat.getValue();

                    Set<Organism> foodSet = area.getInhabitants().get(foodType);
                    if (Randomizer.getProbability(chanceToEat) && foodSet != null && !foodSet.isEmpty()) {
                        Iterator<Organism> foodIterator = foodSet.iterator();
                        if (foodIterator.hasNext()) {
                            Organism food = foodIterator.next();
                            double initialFoodWeight = food.getWeight();
                            double mealWeight = Math.min(foodRequired, initialFoodWeight);
                            foodRequired -= mealWeight;
                            setWeight(getWeight() + mealWeight);
                            food.setWeight(food.getWeight() - mealWeight);

                            double currentFoodWeight = food.getWeight();
                            if (currentFoodWeight < initialFoodWeight / settings.getdeathWeightPercent()) { // съели целиком или до нежизнеспособного состояния
                                foodIterator.remove();
                            }

                            ate = true;
                            if (foodRequired <= 0) {
                                break;
                            }
                        }
                    }
                }
            }
        } finally {
            area.getLock().unlock();
        }

        return ate;
    }

    public void growUp(Area area) {
        safeGrowUp(area);
    }

    private void safeGrowUp(Area area) {
        area.getLock().lock();
        try {
            int growUpPercent = Settings.get().getAnimalGrowUpPercent();
            double weightIncrement = this.getWeight() * growUpPercent / 100;
            this.setWeight(this.getWeight() + weightIncrement);
        } finally {
            area.getLock().unlock();
        }
    }

    public void move(Area area) {
        safeMove(area);
    }

    private void safeMove(Area currentArea) {
        currentArea.getLock().lock();
        try {
            int speed = getSpeed();
            if (speed > 0) {
                Area destinationArea = getNextArea(currentArea, speed);
                Set<Organism> currentAreaOrganisms = currentArea.getInhabitants().get(this.getType());
                currentAreaOrganisms.remove(this);

                Set<Organism> destinationAreaOrganisms = destinationArea.getInhabitants().get(this.getType());
                destinationAreaOrganisms.add(this);
            }
        } finally {
            currentArea.getLock().unlock();
        }
    }

    private int getSpeed() {
        int maxSpeed = Settings.get().getOrganismCommonSpecsByType(this.getType()).getMaxSpeed();
        return Randomizer.getRandom(0, maxSpeed);
    }

    private Area getNextArea(Area currentArea, int currentSpeed) {
        List<Area> nearestAreas = currentArea.getNearestAreas();
        if (currentSpeed > 0) {
            int nextAreaIndex = Randomizer.getRandom(0, nearestAreas.size());
            if (nextAreaIndex == 0) {
                return getNextArea(currentArea, currentSpeed - 1);
            } else {
                return getNextArea(nearestAreas.get(nextAreaIndex - 1), currentSpeed - 1);
            }
        } else {
            return currentArea;
        }
    }
}
