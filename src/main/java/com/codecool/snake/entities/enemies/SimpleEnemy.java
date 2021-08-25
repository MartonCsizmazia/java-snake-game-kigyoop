package com.codecool.snake.entities.enemies;


import com.codecool.snake.Globals;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.entities.Interactable;

/**
 * Moves back and forth between edges of the field. (Scooter)
 */
public class SimpleEnemy extends Enemy implements Animatable, Interactable {

    public SimpleEnemy() {
        super(10);
        setImage(Globals.getInstance().getImage("EnemyScooter"));
        setFullHeading(rnd.nextDouble() * 360, 1);
    }


    @Override
    protected void actionDisappear() {
        setDirectionAndHeading((getDirection() + 180) % 360);
    }
}
