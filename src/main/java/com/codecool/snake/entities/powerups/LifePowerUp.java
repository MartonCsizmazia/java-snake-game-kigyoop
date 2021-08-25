package com.codecool.snake.entities.powerups;

import com.codecool.snake.Globals;
import com.codecool.snake.entities.Interactable;
import java.util.Random;

/**
 * Makes you more healthy and slower. (Beer)
 */
public class LifePowerUp extends SimplePowerUp implements Interactable {
    private static Random rnd = new Random();

    public LifePowerUp() {
        setImage(Globals.getInstance().getImage("Beer"));

        setX(rnd.nextDouble() * Globals.WINDOW_WIDTH);
        setY(rnd.nextDouble() * Globals.WINDOW_HEIGHT);
    }

    @Override
    public String getMessage() {
        return "Got life power-up :)";
    }
}