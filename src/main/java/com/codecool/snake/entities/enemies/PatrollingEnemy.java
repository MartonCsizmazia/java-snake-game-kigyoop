package com.codecool.snake.entities.enemies;

import com.codecool.snake.Globals;

/**
 * Moves in a circle. (Pigeon)
 */
public class PatrollingEnemy extends Enemy {

    public PatrollingEnemy() {
        super(10);
        setImage(Globals.getInstance().getImage("EnemyBird"));
        setFullHeading(rnd.nextDouble() * 360, 1);
    }

    @Override
    protected void updateHeading() {
        setDirectionAndHeading(getDirection() + 0.5);
    }

    @Override
    protected void actionDisappear() {
        setUpPosition();
    }
}
