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

    public float getWidth()             { return _width; }
    public float getHeight()            { return _height; }

    private GameState(float width, float height) {
        _width = width;
        _height = height;
        _rand = new Random();

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
        if(_enemySpawnTimer < 0f) {
            spawnElephant();
            _enemySpawnTimer = getSpawnTimer();
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
