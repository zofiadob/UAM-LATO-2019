package pl.wizard.software.battlefield;

import com.google.common.collect.Range;
import pl.wizard.software.creatures.Creature;
import pl.wizard.software.player.Hero;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class BattleMap {

    private final Map<Creature, Point> map;
    private final Range<Integer> width;
    private final Range<Integer> height;

    BattleMap(){
        this(new Hero(), new Hero());
    }

    public BattleMap(Hero aHero1, Hero aHero2) {
        map = new HashMap<>();
        width = Range.closed(0,BattleEngine.MAP_MAX_WIDTH);
        height = Range.closed(0,BattleEngine.MAP_MAX_HEIGHT);

        putAllHeroCreaturesIntoMap(aHero1, 0);
        putAllHeroCreaturesIntoMap(aHero2, BattleEngine.MAP_MAX_WIDTH);
    }

    private void putAllHeroCreaturesIntoMap(Hero aHero1, int aAI) {
        for (int i = 0; i < aHero1.getCreatures().size(); i++) {
            map.put(aHero1.getCreatures().get(i), new Point(aAI, 1 + i * 2));
        }
    }

    void put(Creature aCreature, Point aPoint) {
        checkPointIsAllow(aPoint);
        map.put(aCreature, aPoint);
    }

    void move(Creature aCreature, Point aNewPoint) {
        checkPointIsAllow(aNewPoint);
        int moveRange = aCreature.getSpeed();
        Point oldPoint = map.get(aCreature);

        if (aNewPoint.distance(oldPoint) > moveRange) {
            throw new IllegalArgumentException("Creature has only " + moveRange + " speed");
        }

        map.replace(aCreature, aNewPoint);
    }

    private void checkPointIsAllow(Point aPoint) {
        if (map.containsValue(aPoint)) {
            throw new IllegalArgumentException("Field is not empty");
        }
        if(!width.contains((int)aPoint.getX()) || !height.contains((int)aPoint.getY())){
            throw new IllegalArgumentException("You are trying to add creature outside the map!");
        }
    }

    Point getPositionByCreature(Creature aCreature) {
        return map.get(aCreature);
    }

    Optional<Creature> getCreatureByPosition(Point aPosition){
        return map.keySet().stream().filter(creature -> map.get(creature).equals(aPosition)).findAny();
    }

    boolean isMovePossible(Creature aCreature, Point aPoint) {
        return !map.containsValue(aPoint) && (width.contains((int)aPoint.getX()) || height.contains((int)aPoint.getY())) && aPoint.distance(map.get(aCreature)) <= aCreature.getSpeed();
    }
}
