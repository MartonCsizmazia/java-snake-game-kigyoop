package com.codecool.snake.entities.snakes;

import com.codecool.snake.DelayedModificationList;
import com.codecool.snake.Globals;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.eventhandler.InputHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;


public class Snake implements Animatable {
    /*private static final*/ float speed = 2;
    public static final double minSpeed = 0.2;
    private int health = 1000;
    public int startParts = 4;

    private SnakeHead head;
    private DelayedModificationList<GameEntity> body;
    public int snakeLength;


    public Snake(Point2D position) {
        head = new SnakeHead(this, position);
        body = new DelayedModificationList<>();

        addPart(startParts);
    }

    public void step() {
        SnakeControl turnDir = getUserInput();
        head.updateRotation(turnDir, speed);

        updateSnakeBodyHistory();
        checkForGameOverConditions();

        body.doPendingModifications();
    }

    public SnakeHead getHead() {
        return head;
    }

    private SnakeControl getUserInput() {
        SnakeControl turnDir = SnakeControl.INVALID;
        if(InputHandler.getInstance().isKeyPressed(KeyCode.LEFT)) turnDir = SnakeControl.TURN_LEFT;
        if(InputHandler.getInstance().isKeyPressed(KeyCode.RIGHT)) turnDir = SnakeControl.TURN_RIGHT;
        return turnDir;
    }

    public void addPart(int numParts) {
        GameEntity parent = getLastPart();
        Point2D position = parent.getPosition();

        for (int i = 0; i < numParts; i++) {
            SnakeBody newBodyPart = new SnakeBody(position);
            body.add(newBodyPart);
        }
        snakeLength = body.lenght();
        Globals.getInstance().display.updateSnakeHeadDrawPosition(head);
    }

    public void changeHealth(int diff) {
        health += diff;
        Text text = Globals.getInstance().game.getHealthText();
        Globals.getInstance().game.getChildren().remove(text);
        Globals.getInstance().game.showHealth();
    }

    public int getHealth(){
        return health;
    }

    private void checkForGameOverConditions() {
        if (head.isOutOfBounds() || health <= 0) {
            System.out.println("Game Over");
            System.out.println(snakeLength);
            Globals.getInstance().stopGame();
            gameOverMessage();
        }
    }

    private void updateSnakeBodyHistory() {
        GameEntity prev = head;
        for(GameEntity currentPart : body.getList()) {
            currentPart.setPosition(prev.getPosition());
            prev = currentPart;
        }
    }

    public GameEntity getLastPart() {
        GameEntity result = body.getLast();

        if(result != null) return result;
        return head;
    }

    private void gameOverMessage(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        if (snakeLength > 0){
            snakeLength -= startParts;
        }
        alert.setContentText("You collected " + snakeLength + " points.");
        alert.show();
    }

    public boolean intersectsEntity(GameEntity entity) {
        if (head.intersects(entity)) return true;
        for (GameEntity bodyPart : body) {
            if (bodyPart.intersects(entity)) return true;
        }
        return false;
    }

    public void slowDown() {
        speed = (float) Math.max(speed - 0.2, Snake.minSpeed);
        //head.turnRate += 1;
    }
}
