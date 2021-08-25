package com.codecool.snake;

import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.enemies.ChasingEnemy;
import com.codecool.snake.entities.enemies.PatrollingEnemy;
import com.codecool.snake.entities.enemies.SimpleEnemy;
import com.codecool.snake.entities.enemies.SittingEnemy;
import com.codecool.snake.entities.powerups.BoostPowerUP;
import com.codecool.snake.entities.powerups.LifePowerUp;
import com.codecool.snake.entities.powerups.SimplePowerUp;
import com.codecool.snake.entities.snakes.Snake;
import com.codecool.snake.Utils;

import java.util.List;
import java.util.Random;

public class GameLoop {
    private Snake snake;
    private boolean running = false;
    int frame;

    public GameLoop(Snake snake) {
        this.snake = snake;
    }

    public void start() {
        running = true;
    }

    public void stop() {
        running = false;
    }

    public void step() {

        if (running) {
            snake.step();
            for (GameEntity gameObject : Globals.getInstance().display.getObjectList()) {
                if (gameObject instanceof Animatable) {
                    ((Animatable) gameObject).step();
                }
            }
            checkCollisions();
            addNewItemsRandomly();
        }
        Globals.getInstance().display.frameFinished();
    }

    private void addNewItemsRandomly() {
        // new powerups:
        if (Utils.getRandomForEvent(20)) {
            new BoostPowerUP();
        }
        if (Utils.getRandomForEvent(10)) {
            new LifePowerUp();
        }
        if (Utils.getRandomForEvent(10)) {
            new SimplePowerUp();
        }

        // new enemies
        if (Utils.getRandomForEvent(20)) {
            Globals.getInstance().game.spawnEnemy(ChasingEnemy.class);
        }
        if (Utils.getRandomForEvent(20)) {
            Globals.getInstance().game.spawnEnemy(PatrollingEnemy.class);
        }
        if (Utils.getRandomForEvent(20)) {
            Globals.getInstance().game.spawnEnemy(SimpleEnemy.class);
        }
        if (Utils.getRandomForEvent(20)) {
            Globals.getInstance().game.spawnEnemy(SittingEnemy.class);
        }
    }

    private void checkCollisions() {
        List<GameEntity> gameObjs = Globals.getInstance().display.getObjectList();
        for (int idxToCheck = 0; idxToCheck < gameObjs.size(); ++idxToCheck) {
            GameEntity objToCheck = gameObjs.get(idxToCheck);
            if (objToCheck instanceof Interactable) {
                for (int otherObjIdx = idxToCheck + 1; otherObjIdx < gameObjs.size(); ++otherObjIdx) {
                    GameEntity otherObj = gameObjs.get(otherObjIdx);
                    if (otherObj instanceof Interactable) {
                        if (objToCheck.intersects(otherObj)) {
                            ((Interactable) objToCheck).apply(otherObj);
                            ((Interactable) otherObj).apply(objToCheck);
                        }
                    }
                }
            }
        }
    }
}
