package com.codecool.snake.entities.enemies;

import com.codecool.snake.Globals;
import com.codecool.snake.entities.Interactable;

/**
 * Does not move. (Poop)
 */
public class SittingEnemy extends Enemy implements Interactable {
    public SittingEnemy() {
        super(20);
        setImage(Globals.getInstance().getImage("EnemyPoop"));
    }

    @Override
    public void step() {
        // does not move
    }

}
