package com.sponezzis.shtjam2015.actors;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.sponezzis.shtjam2015.*;
import com.sponezzis.shtjam2015.components.*;

/**
 * Created by sponaas on 6/23/15.
 */
public class Player extends Actor {

    private float _shotTimer;
    private ComponentMapper<PlayerDataComponent> _playerDataComponents = ComponentMapper.getFor(PlayerDataComponent.class);
    private ComponentMapper<BodyComponent> _bodyComponents = ComponentMapper.getFor(BodyComponent.class);
    private ComponentMapper<SpriteComponent> _spriteComponents = ComponentMapper.getFor(SpriteComponent.class);

    public Player(Entity entity) {
        super(entity);
        _shotTimer = -1f;
    }

    public static Player makePlayer() {
        Entity entity = new Entity();

        Sprite sprite = new Sprite(ResourceManager.getTexture("player"));
        SpriteComponent spriteComponent = new SpriteComponent(sprite);
        Vector2 position = new Vector2(500f, 500f);

        Body body = BodyFactory.getInstance().generate(entity, "player.json", position);

        PositionComponent playerPositionComponent = new PositionComponent(position.x, position.y);
        BodyComponent playerBodyComponent = new BodyComponent(playerPositionComponent, body);
        RenderComponent renderComponent = new RenderComponent(0);
        PlayerDataComponent playerDataComponent = new PlayerDataComponent();

        entity.add(playerPositionComponent).add(playerBodyComponent).add(spriteComponent).add(renderComponent).add(playerDataComponent);

        Player player = new Player(entity);
        player.setSizeXInPixels(sprite.getWidth());
        player.setSizeYInPixels(sprite.getHeight());

        return player;
    }

    @Override
    public void update() {
        PlayerDataComponent playerDataComponent = _playerDataComponents.get(getEntity());
        if(playerDataComponent.alive) {
            updateMovement();
            updateShooting();
            if(playerDataComponent.invincibilityTime > 0f) {
                playerDataComponent.invincibilityTime -= (float)Time.time;
                if(playerDataComponent.invincibilityTime < 0.01f) {
                    BodyComponent bodyComponent = _bodyComponents.get(getEntity());
                    Fixture fixture = bodyComponent.body.getFixtureList().get(0); // TODO: this only works with single fixture player. should be cool but be aware
                    Filter filter = fixture.getFilterData();
                    filter.maskBits = Constants.BITMASK_LEVEL_BOUNDS | Constants.BITMASK_ENEMY;
                    fixture.setFilterData(filter);
                    playerDataComponent.invincibilityTime = -1f;
                }
            }
        }
        else {
            playerDataComponent.playerDeathTime -= (float)Time.time;
            if(playerDataComponent.playerDeathTime < 0f) {
                if(GameState.getInstance().getLives() > 0) {
                    GameState.getInstance().decrementLives();
                    respawn(playerDataComponent);
                }
                else {
                    // TODO: GAME OVER
                }
            }
        }
    }

    private void updateMovement() {
        Vector2 movementInput = new Vector2(0f, 0f);

        if(InputManager.moveRightActive)
            movementInput.x += 1f;
        if(InputManager.moveLeftActive)
            movementInput.x -= 1f;

        if(InputManager.moveUpActive)
            movementInput.y += 1f;
        if(InputManager.moveDownActive)
            movementInput.y -= 1f;

        Vector2 acceleration = new Vector2(movementInput.x * (float) Time.time * Constants.PLAYER_ACCEL,
                movementInput.y * (float) Time.time * Constants.PLAYER_ACCEL);
        acceleration.nor();

        Vector2 velocity = getBody().getLinearVelocity();

        // need to revisit this
        Vector2 frictionAdjustment = new Vector2(0f, 0f);
        if(Math.abs(movementInput.x) < 0.1f) {
            frictionAdjustment.x = (float)Time.time * Constants.FRICTION * -1f * Math.signum(velocity.x);
            if(Math.signum(frictionAdjustment.x + velocity.x) != Math.signum(velocity.x))
                frictionAdjustment.x = -1f * velocity.x;
        }
        if(Math.abs(movementInput.y) < 0.1f) {
            frictionAdjustment.y = (float)Time.time * Constants.FRICTION * -1f * Math.signum(velocity.y);
            if(Math.signum(frictionAdjustment.y + velocity.y) != Math.signum(velocity.y))
                frictionAdjustment.y = -1f * velocity.y;
        }

        Vector2 desiredVelocity = new Vector2(velocity.x + frictionAdjustment.x + acceleration.x,
                velocity.y + frictionAdjustment.y + acceleration.y);

        float desiredSpeed = desiredVelocity.len();
        if(desiredSpeed > Constants.PLAYER_MAXSPEED)
            desiredVelocity.scl(Constants.PLAYER_MAXSPEED / desiredSpeed);

        Vector2 deltaVelocity = new Vector2(desiredVelocity.x - velocity.x, desiredVelocity.y - velocity.y);

        Vector2 impulse = deltaVelocity.scl(getBody().getMass());
        getBody().applyLinearImpulse(impulse.x, impulse.y, getBody().getWorldCenter().x, getBody().getWorldCenter().y, true);
    }

    private void updateShooting()
    {
        if(_shotTimer < 0) {
            boolean shooting = false;
            Vector2 shotDirection = new Vector2(0f, 0f);
            Vector2 pos = getCenterPos();
            if(InputManager.shootingDownActive) {
                shooting = true;
                shotDirection.y = -1f;
                pos.y -= getSizeYInPixels() / 2;
            }
            else if(InputManager.shootingLeftActive) {
                shooting = true;
                shotDirection.x = -1f;
                pos.x -= getSizeXInPixels() / 2;
            }
            else if(InputManager.shootingRightActive) {
                shooting = true;
                shotDirection.x = 1f;
                pos.x += getSizeXInPixels() / 2;
            }
            else if(InputManager.shootingUpActive) {
                shooting = true;
                shotDirection.y = 1f;
                pos.y += getSizeYInPixels() / 2;
            }
            if(shooting) {
                Entity bulletEntity = new Entity();
                SpriteComponent bulletSprite = new SpriteComponent(new Sprite(ResourceManager.getTexture("bullet")));

                PositionComponent bulletPosition = new PositionComponent(pos.x, pos.y);
                Body body = BodyFactory.getInstance().generate(bulletEntity, "bullet.json", new Vector2(pos.x, pos.y));
                BodyComponent bulletBody = new BodyComponent(bulletPosition, body);
                RenderComponent renderComponent = new RenderComponent(0);

                bulletEntity.add(bulletSprite).add(bulletPosition).add(bulletBody).add(renderComponent);
                EntityManager.getInstance().addEntity(bulletEntity);

                shotDirection.scl(Constants.BULLET_SPEED);
                body.applyLinearImpulse(shotDirection.x, shotDirection.y, body.getWorldCenter().x, body.getWorldCenter().y, true);

                _shotTimer = Constants.PLAYER_SHOOTING_COOLDOWN;
            }
        }
        else
            _shotTimer -= Time.time;
    }

    public Vector2 getCenterPos() {
        PositionComponent positionComponent = getPosition();
        return new Vector2(positionComponent.x + (getSizeXInPixels() / 2), positionComponent.y + (getSizeYInPixels() / 2));
    }

    private void respawn(PlayerDataComponent playerDataComponent) {
        playerDataComponent.playerDeathTime = -1f;
        playerDataComponent.invincibilityTime = Constants.PLAYER_INVINCIBILITY_TIME;
        playerDataComponent.alive = true;

        SpriteComponent spriteComponent = _spriteComponents.get(getEntity());
        spriteComponent.sprite = new Sprite(ResourceManager.getTexture("player"));
    }

}
