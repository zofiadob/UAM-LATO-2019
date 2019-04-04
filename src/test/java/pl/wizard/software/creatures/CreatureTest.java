package pl.wizard.software.creatures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreatureTest {

    Creature centaur;
    Creature imp;


    @BeforeEach
    void prepareCreatures(){
        centaur = prepareCentaur();
        imp = prepareImp();
    }

    private Creature prepareImp() {
        return new Creature("Imp",4,2,3);
    }

    private Creature prepareCentaur() {
        return new Creature("Centaur", 10,5,3);
    }


    @Test
    void impShouldLostTwoHpAfterCentaurAttack(){
        centaur.attack(imp);

        assertEquals(2, imp.getCurrentHp());
    }

    @Test
    void centaurShouldLostOnlyOneHpBecauseHisDefenseIsGreaterThanImpAttack(){
        imp.attack(centaur);

        assertEquals(9, centaur.getCurrentHp());
    }

    @Test
    void impShouldCounterAttack(){
        imp.attack(centaur);

        //then
        assertEquals(9, centaur.getCurrentHp());
        assertEquals(2, imp.getCurrentHp());
    }

    @Test
    void impShouldCounterAttackOnlyOnce(){
        centaur.attack(imp);
        centaur.attack(imp);

        assertEquals(9, centaur.getCurrentHp());
    }

    @Test
    void shootingCreatureShouldNotBeCounterAttacked(){
        Creature shootingCreature = new Creature("Shooting", 10, 5, 3, true);

        shootingCreature.attack(imp);

        assertEquals(shootingCreature.getMaxHp(), shootingCreature.getCurrentHp());
    }



}