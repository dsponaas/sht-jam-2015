package com.sponezzis.shtjam2015.actors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.sponezzis.shtjam2015.*;
import com.sponezzis.shtjam2015.components.*;

/**
 * Created by sponaas on 6/23/15.
 */
public class Player extends Actor {

    private float _shotTimer;
    private final float halfSizeX = 20f; // TODO: highly hackish but i'll probly leave it as is
    private final float halfSizeY = 20f; // TODO: highly hackish but i'll probly leave it as is

    public Player(Entity entity) {
        super(entity);
        _shotTimer = -1f;
    }

    public static Entity makePlayerEntity() {
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
        return entity;
    }

    @Override
    public void update() {
        updateMovement();
        updateShooting();
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
                pos.y -= halfSizeY;
            }
            else if(InputManager.shootingLeftActive) {
                shooting = true;
                shotDirection.x = -1f;
                pos.x -= halfSizeX;
            }
            else if(InputManager.shootingRightActive) {
                shooting = true;
                shotDirection.x = 1f;
                pos.x += halfSizeX;
            }
            else if(InputManager.shootingUpActive) {
                shooting = true;
                shotDirection.y = 1f;
                pos.y += halfSizeY;
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
        return new Vector2(positionComponent.x + halfSizeX, positionComponent.y + halfSizeY);
    }

}
