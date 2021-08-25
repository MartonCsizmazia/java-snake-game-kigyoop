package com.codecool.snake.entities.snakes;

import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.Globals;
import com.codecool.snake.Utils;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.enemies.Enemy;
import com.codecool.snake.entities.powerups.SimplePowerUp;
import com.codecool.snake.entities.powerups.BoostPowerUP;
import com.codecool.snake.entities.powerups.LifePowerUp;

import javafx.geometry.Point2D;


public class SnakeHead extends GameEntity implements Interactable {
    public float turnRate = 3;
    private Snake snake;
    public int numOfPartsToAdd = 3;

    public SnakeHead(Snake snake, Point2D position) {
        this.snake = snake;
        setImage(Globals.getInstance().getImage("SnakeHead"));
        setPosition(position);
    }

    public void updateRotation(SnakeControl turnDirection, float speed) {
        double headRotation = getRotate();

        if (turnDirection.equals(SnakeControl.TURN_LEFT)) {
            headRotation = headRotation - turnRate;
        }
        if (turnDirection.equals(SnakeControl.TURN_RIGHT)) {
            headRotation = headRotation + turnRate;
        }

        // set rotation and position
        setRotate(headRotation);
        Point2D heading = Utils.directionToVector(headRotation, speed);
        setX(getX() + heading.getX());
        setY(getY() + heading.getY());
    }

    @Override
    public void apply(GameEntity entity) {

        // all enemies make you slower and less healthy if you eat them, but they will disappear
        if(entity instanceof Enemy) {
            System.out.println(getMessage());
            snake.changeHealth(-((Enemy) entity).getDamage());
            snake.speed = (float) Math.max(snake.speed - 0.2, Snake.minSpeed);
        }

        /* powerups */

        // boost: increase speed more
        if (entity instanceof BoostPowerUP){
            System.out.println(getMessage());
            snake.speed += 0.4;
        } else
        // life: increase health, decrease speed
        if (entity instanceof LifePowerUp){
            System.out.println(getMessage());
            snake.changeHealth(30);
            snake.slowDown();
        } else
        // simple: adds parts, increase speed a bit
        if (entity instanceof SimplePowerUp){
            System.out.println(getMessage());
            snake.addPart(numOfPartsToAdd);
            snake.speed += 0.2;
        }
    }

    @Override
    public String getMessage() {
        return "IMMA SNAEK HED! SPITTIN' MAH WENOM! SPITJU-SPITJU!";
    }
}
