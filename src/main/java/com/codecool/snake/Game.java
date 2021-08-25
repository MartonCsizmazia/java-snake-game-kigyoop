package com.codecool.snake;

import com.codecool.snake.entities.enemies.*;
import com.codecool.snake.entities.powerups.SimplePowerUp;
import com.codecool.snake.entities.powerups.BoostPowerUP;
import com.codecool.snake.entities.powerups.LifePowerUp;
import com.codecool.snake.entities.snakes.Snake;
import com.codecool.snake.eventhandler.ClickRestartHandler;
import com.codecool.snake.eventhandler.InputHandler;

import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


public class Game extends Pane {
    private Snake snake = null;
    private GameTimer gameTimer = new GameTimer();
    private Text healthText = null;


    public Game() {
        Globals.getInstance().game = this;
        Globals.getInstance().display = new Display(this);
        Globals.getInstance().setupResources();


        init();
        this.healthText = new Text(10, 50, "Health:" + snake.getHealth());
    }

    public void init() {
        spawnSnake();
        spawnEnemies();
        spawnSimplePowerUps(4);
        spawnBoostPowerUPs(2);
        spawnLifePowerUPs(1);

        GameLoop gameLoop = new GameLoop(snake);
        Globals.getInstance().setGameLoop(gameLoop);
        gameTimer.setup(gameLoop::step);
        gameTimer.play();
    }

    public void start() {
        setupInputHandling();
        setRestartButton(Globals.getInstance().game);
        showHealth();
        Globals.getInstance().startGame();
    }

    private void spawnSnake() {
        snake = new Snake(new Point2D(500, 500));
    }

    private void spawnEnemies() {
        // SimpleEnemy: 5, PatrollingEnemy: 3, ChasingEnemy: 2, SittingEnemy: 2
        spawnMultipleEnemies(SimpleEnemy.class, 5);
        spawnMultipleEnemies(PatrollingEnemy.class, 3);
        spawnMultipleEnemies(ChasingEnemy.class, 2);
        spawnMultipleEnemies(SittingEnemy.class, 2);
    }

    /**
     * Create the given number of enemies of the given Enemy subclass.
     *
     * They won't be on the snake.
     * @param typeOfEnemy class of the enemies needed
     */
    private void spawnMultipleEnemies(Class<? extends Enemy> typeOfEnemy, int numberOfEnemies) {
        for (int i = 0; i < numberOfEnemies; ++i) {
            spawnEnemy(typeOfEnemy);
        }
    }

    /**
     * Create an enemy of the given Enemy subclass.
     *
     * It won't intersect the snake.
     * @param typeOfEnemy class of the enemy needed
     */
    public void spawnEnemy(Class<? extends Enemy> typeOfEnemy) {
        Enemy enemy = null;
        boolean notYet = true;
        while (notYet) {
            Constructor<Enemy> constructor = null;
            // get according enemy constructor
            try {
                constructor = (Constructor<Enemy>) typeOfEnemy.getConstructor();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            // instantiate enemy
            try {
                enemy = constructor.newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            // check if intersects with snake
            if (snake.intersectsEntity(enemy)) {
                enemy.destroy();
            } else {
                notYet = false;
            }
        }
    }


    private void spawnSimplePowerUps(int numberOfPowerUps) {
        for (int i = 0; i < numberOfPowerUps; ++i) new SimplePowerUp();
    }

    private void spawnBoostPowerUPs(int numberOfPowerUps) {
        for (int i = 0; i < numberOfPowerUps; ++i) new BoostPowerUP();
    }

    private void spawnLifePowerUPs(int numberOfPowerUps) {
        for (int i = 0; i < numberOfPowerUps; ++i) new LifePowerUp();
    }

    private void setupInputHandling() {
        Scene scene = getScene();
        scene.setOnKeyPressed(event -> InputHandler.getInstance().setKeyPressed(event.getCode()));
        scene.setOnKeyReleased(event -> InputHandler.getInstance().setKeyReleased(event.getCode()));
    }

    public void setRestartButton(Game game) {
        Button button = new Button("Restart");
        game.getChildren().add(button);
        ClickRestartHandler clickHandler = new ClickRestartHandler();
        button.setOnAction(clickHandler);
        game.requestFocus();
    }

    public void showHealth() {
        healthText.setText("Health: " + snake.getHealth());
        this.getChildren().add(healthText);
    }

    public Text getHealthText() {
        return healthText;
    }

    public Snake getSnake() {
        return snake;
    }

    public void setTableBackground(Image streetBackground) {
        setBackground(new Background(new BackgroundImage(streetBackground,
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
    }
}

