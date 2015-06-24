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

    public Player(Entity entity) {
        super(entity);
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

        Vector2 acceleration = new Vector2(movementInput.x * (float) Time.time * Constants.PLAYER_ACCEL_HORIZ,
                movementInput.y * (float) Time.time * Constants.PLAYER_ACCEL_VERT);

        Vector2 velocity = getBody().getLinearVelocity();

        Vector2 frictionAdjustment = new Vector2(0f, 0f);
        if(Math.abs(movementInput.x) < 0.1f) {
            frictionAdjustment.x = (float)Time.time * Constants.AIR_FRICTION * -1f * Math.signum(velocity.x);
            if(Math.signum(frictionAdjustment.x + velocity.x) != Math.signum(velocity.x))
                frictionAdjustment.x = -1f * velocity.x;
        }
        if(Math.abs(movementInput.y) < 0.1f) {
            frictionAdjustment.y = (float)Time.time * Constants.AIR_FRICTION * -1f * Math.signum(velocity.y);
            if(Math.signum(frictionAdjustment.y + velocity.y) != Math.signum(velocity.y))
                frictionAdjustment.y = -1f * velocity.y;
        }

        Vector2 desiredVelocity = new Vector2(velocity.x + frictionAdjustment.x + acceleration.x,
                velocity.y + frictionAdjustment.y + acceleration.y);

        if(Math.abs(desiredVelocity.x) > Constants.PLAYER_MAXSPEED_HORIZ)
            desiredVelocity.x = Math.signum(desiredVelocity.x) * Constants.PLAYER_MAXSPEED_HORIZ;
        if(Math.abs(desiredVelocity.y) > Constants.PLAYER_MAXSPEED_VERT)
            desiredVelocity.y = Math.signum(desiredVelocity.y) * Constants.PLAYER_MAXSPEED_VERT;

        Gdx.app.log(Constants.LOG_TAG, "x:" + desiredVelocity.x + "  y:" + desiredVelocity.y);

        Vector2 deltaVelocity = new Vector2(desiredVelocity.x - velocity.x, desiredVelocity.y - velocity.y);

        Vector2 impulse = deltaVelocity.scl(getBody().getMass());
        getBody().applyLinearImpulse(impulse.x, impulse.y, getBody().getWorldCenter().x, getBody().getWorldCenter().y, true);
    }

}
