package com.veyrongaming.eternalsemester.weapons;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.veyrongaming.eternalsemester.Enemy;
import com.veyrongaming.eternalsemester.GameWorld;
import com.veyrongaming.eternalsemester.characters.Character;

public class Sword extends Weapon {
    public Sword() {
        super("Sword", 20, 28);
    }

    @Override
    public void attack(Character character, GameWorld gameWorld) {
        float attackRange = 1f;
        ArrayList<Enemy> enemies = gameWorld.getEnemies();

        for (Enemy enemy : enemies) {
            float distance = Vector2.dst(character.getPosition().x, character.getPosition().y, enemy.getPosition().x, enemy.getPosition().y);
            if (distance <= attackRange) {
                enemy.takeDamage(damage);
            }
        }
    }
}
