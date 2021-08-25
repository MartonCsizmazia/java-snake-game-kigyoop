package com.codecool.snake.entities.enemies;

import com.codecool.snake.Globals;
import com.codecool.snake.Utils;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.geometry.Point2D;

import java.util.Random;

public abstract class Enemy extends GameEntity implements Animatable, Interactable {
    protected static Random rnd = new Random();
    private final int damage;
    private Point2D heading;
    private double direction;
    private int speed;

    public Enemy(int damage) {
        this.damage = damage;
        setUpPosition();
    }

    public int getDamage() {
        return damage;
    }

    protected void updateHeading() {
        //Default empty
    }

    protected void setHeading(Point2D heading) {
        this.heading = heading;
    }

    protected void setUpPosition() {
        setX(rnd.nextDouble() * Globals.WINDOW_WIDTH);
        setY(rnd.nextDouble() * Globals.WINDOW_HEIGHT);
    }

    public Point2D getHeading() {
        return heading;
    }

    public double getDirection() {
        return direction;
    }

    public void setDirectionAndHeading(double direction) {
        this.direction = direction;
        this.heading = Utils.directionToVector(direction, speed);
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeedAndHeading(int speed) {
        this.speed = speed;
        this.heading = Utils.directionToVector(direction, speed);
    }

    public void setFullHeading(double direction, int speed) {
        this.direction = direction;
        this.speed = speed;
        this.heading = Utils.directionToVector(direction, speed);
    }

    protected void actionDisappear() {
       destroy();
    }



    @Override
    public void step() {
        updateHeading();
        if (isOutOfBounds()) {
            actionDisappear();
        }
        setX(getX() + heading.getX());
        setY(getY() + heading.getY());
    }

    @Override
    public void apply(GameEntity entity) {
        if (entity instanceof SnakeHead) {
            destroy();
        }
    }

    @Override
    public String getMessage() {
        return (getDamage() + " damage");
    }
}
