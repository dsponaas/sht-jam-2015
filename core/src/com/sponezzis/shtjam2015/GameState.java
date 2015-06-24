package com.sponezzis.shtjam2015;

import com.badlogic.ashley.core.Entity;
import com.sponezzis.shtjam2015.actors.Elephant;

import java.util.Random;

/**
 * Created by sponaas on 6/24/15.
 */
public class GameState {

    private static GameState _instance;

    private float _width, _height;
    private float _enemySpawnTimer;
    private Random _rand;

    private float _levelTimer;
    private int _level;
    public int getLevel()                   { return _level; }

    private int _lives;
    public int getLives()                   { return _lives; }
    public void decrementLives()            { --_lives; }
    public void incrementLives()            { ++_lives; }

    private int _score;
    public int getScore()                   { return _score; }
    public void incrementScore(int val)     { _score += val; }

    public float getWidth()             { return _width; }
    public float getHeight()            { return _height; }

    private GameState(float width, float height) {
        _width = width;
        _height = height;
        _rand = new Random();

        _level = 1;
        _levelTimer = Constants.TIME_PER_LEVEL;

        _lives = 2;

        _enemySpawnTimer = getSpawnTimer();
    }

    public static void initialize(float width, float height) {
        // TODO: we're not going to have anything to dispose, yes?
        _instance = new GameState(width, height);
    }

    public static GameState getInstance() {
        return _instance;
    }

    public void update() {
        _enemySpawnTimer -= (float)Time.time;
        _levelTimer -= (float)Time.time;
        if(_enemySpawnTimer < 0f) {
            spawnElephant();
            _enemySpawnTimer = getSpawnTimer();
        }
        if(_levelTimer < 0f) {
            ++_level;
            _levelTimer = Constants.TIME_PER_LEVEL;
        }
    }

    private float getSpawnTimer() {
        return 300f;
    }

    private void spawnElephant() {
        float direction = _rand.nextBoolean() ? 1f : -1f;
        float xPos = direction > 0f ? 0f : _width;
        float yPos = getRandomFloat(0f, _height);
        Elephant elephant = Elephant.makeElephant(xPos, yPos, direction);
        EntityManager.getInstance().addEntity(elephant.getEntity());
        EntityManager.getInstance().addActor(elephant);
    }

    private float getRandomFloat(float start, float end) {
        return start + ((end - start) * _rand.nextFloat());
    }

}
