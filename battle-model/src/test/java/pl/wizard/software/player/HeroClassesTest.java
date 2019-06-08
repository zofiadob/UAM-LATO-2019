package pl.wizard.software.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.wizard.software.creatures.Creature;
import spellbook.SpellBook;

import static org.junit.jupiter.api.Assertions.*;



public class HeroClassesTest {

    private Hero hero1;
    private Creature creature1;


    @BeforeEach
    void prepareHeroes(){
        hero1 = new Hero(1,1,1,1);
        creature1 = new Creature(null, 20,null,0, 0);
    }


    @Test
    void heroShouldHaveAttack2AndDeffence3BecauseHeIsWarrior(){
        hero1.setHeroClass(new Warrior());
        assertEquals(2, hero1.getAttack() );
        assertEquals(3, hero1.getDefence());
    }


    @Test
    void heroShouldHaveIntelligence2BecauseHeIsWizard(){
        hero1.setHeroClass(new Wizard());

        assertEquals(2, hero1.getIntelligence() );
        assertEquals(20, hero1.getActualMana());
    }



    @Test
    void heroShouldHaveCharisma2BecauseHeIsLeader(){
        hero1.setHeroClass(new Leader());

        assertEquals(2, hero1.getCharisma() );
        assertEquals(0.1, hero1.getCriticalChance());
    }



    @Test
    void warriorShouldntCastSpells(){
        hero1.setHeroClass(new Warrior());
        assertThrows(Exception.class, () -> hero1.getHeroClass().castSpell(hero1, creature1));

    }

    @Test
    void warriorShouldHaveAttack1AndIntelligence2BecauseHeBecomesWizardAndHeIsntWarrior(){
        hero1.setHeroClass(new Warrior());
        hero1.setHeroClass(new Wizard());

        assertEquals(1, hero1.getAttack() );
        assertEquals(2, hero1.getIntelligence());
    }

    @Test
    void wizardShouldCastSpellThatDeal4DamageToCreature() throws Exception {
        hero1.setHeroClass(new Wizard());
        hero1.setSpellBook(new SpellBook());

        hero1.getHeroClass().castSpell(hero1, creature1);
        assertEquals(16, creature1.getCurrentHp());

    }

    @Test
    void wizardShouldCastSpell4TimesAndShouldntCastSpell5TimesBecauseOfNoMana() throws Exception {
        hero1.setHeroClass(new Wizard());
        hero1.setSpellBook(new SpellBook());
        hero1.getHeroClass().castSpell(hero1, creature1);
        hero1.getHeroClass().castSpell(hero1, creature1);
        hero1.getHeroClass().castSpell(hero1, creature1);
        hero1.getHeroClass().castSpell(hero1, creature1);
        assertThrows(Exception.class, () -> hero1.getHeroClass().castSpell(hero1, creature1));
        assertTrue(creature1.isAlive());

    }




}
