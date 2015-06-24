package com.sponezzis.shtjam2015.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.sponezzis.shtjam2015.*;
import com.sponezzis.shtjam2015.actors.Player;
import com.sponezzis.shtjam2015.systems.PositionSystem;
import com.sponezzis.shtjam2015.systems.RenderSpriteSystem;

/**
 * Created by sponaas on 6/23/15.
 */
public class GameScreen implements Screen {

    private Engine _engine;
    private OrthographicCamera _camera;
    private World _world;
    private ContactManager _contactManager;

    private int _screenWidth, _screenHeight;
    private SpriteBatch _spriteBatch;

    private InputManager _inputManager;

    private Box2DDebugRenderer _debugRenderer;

    @Override
    public void show() {
        _spriteBatch = new SpriteBatch();
        _debugRenderer = new Box2DDebugRenderer();

        _engine = initializeEngine();

        _world = new World(new Vector2(0f, 0f), false);
        BodyFactory.initialize(_world);
        _contactManager = new ContactManager(_engine, _world);

        _screenWidth = Gdx.graphics.getWidth();
        _screenHeight = Gdx.graphics.getHeight();

        _camera = new OrthographicCamera();
        _camera.setToOrtho(false, _screenWidth, _screenHeight);
        _camera.update();

        EntityManager.initialize(_engine, _world);

        _inputManager = new InputManager();
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(_inputManager);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(float delta) {
        Time.update();
        _world.step(1f / 60f, 6, 2);
        Matrix4 debugMatrix = _spriteBatch.getProjectionMatrix().cpy().scale(Constants.METERS_TO_PIXELS, Constants.METERS_TO_PIXELS, 0);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        _camera.position.x = _screenWidth / 2f;
        _camera.position.y = _screenHeight / 2f;
        _camera.update();

        _spriteBatch.begin();
        _engine.update((float) Time.time);
        _spriteBatch.setProjectionMatrix(_camera.combined);
        _spriteBatch.end();

        EntityManager.getInstance().update();

        _debugRenderer.render(_world, debugMatrix);
    }

    @Override
    public void resize(int width, int height) {
        _screenWidth = width;
        _screenHeight = height;

        Entity playerEntity = Player.makePlayerEntity();
        EntityManager.getInstance().addEntity(playerEntity);
        Player player = new Player(playerEntity);
        EntityManager.getInstance().addActor(player);

        // side bounds
        addLevelBounds(0f, 0f, 0f, height * Constants.PIXELS_TO_METERS);
        addLevelBounds(width * Constants.PIXELS_TO_METERS, 0f, width * Constants.PIXELS_TO_METERS, height * Constants.PIXELS_TO_METERS);

        // vert bounds
        addLevelBounds(0f, 0f, width * Constants.PIXELS_TO_METERS, 0f);
        addLevelBounds(0f, height * Constants.PIXELS_TO_METERS, width * Constants.PIXELS_TO_METERS, height * Constants.PIXELS_TO_METERS);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public Engine initializeEngine() {
        Engine engine = new Engine();

        PositionSystem positionSystem = new PositionSystem(0);
        RenderSpriteSystem renderSpriteSystem = new RenderSpriteSystem(_spriteBatch, 1);

        engine.addSystem(positionSystem);
        engine.addSystem(renderSpriteSystem);

        return engine;
    }

    private void addLevelBounds(float x1, float y1, float x2, float y2) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0f, 0f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.filter.categoryBits = Constants.BITMASK_LEVEL_BOUNDS;
        fixtureDef.filter.maskBits = Constants.BITMASK_PLAYER | Constants.BITMASK_PLAYER_BULLET;

        EdgeShape shape = new EdgeShape();
        shape.set(x1, y1, x2, y2);
        fixtureDef.shape = shape;
        fixtureDef.friction = 0f;

        Body body = _world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        shape.dispose();
    }

}
