package com.sponezzis.shtjam2015.actors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.sponezzis.shtjam2015.BodyFactory;
import com.sponezzis.shtjam2015.Constants;
import com.sponezzis.shtjam2015.ResourceManager;
import com.sponezzis.shtjam2015.components.*;

/**
 * Created by sponaas on 6/24/15.
 */
public class Elephant extends Actor {

    public Elephant(Entity entity) {
        super(entity);
    }

    public static Entity makeElephantEntity(float xPos, float yPos, float direction) {
        Entity entity = new Entity();

        RenderComponent renderComponent = new RenderComponent(0);

        Vector2 position = new Vector2(xPos, yPos);
        PositionComponent positionComponent = new PositionComponent(position.x, position.y);

        Sprite sprite = new Sprite(ResourceManager.getTexture("elephant"));
        SpriteComponent spriteComponent = new SpriteComponent(sprite);

        Body body = BodyFactory.getInstance().generate(entity, "elephant.json", position);
        BodyComponent bodyComponent = new BodyComponent(positionComponent, body);

        EnemyDataComponent enemyDataComponent = new EnemyDataComponent(Constants.POINTS_ELEPHANT);

        entity.add(renderComponent).add(positionComponent).add(spriteComponent).add(bodyComponent).add(enemyDataComponent);

        body.applyLinearImpulse(direction * Constants.SPEED_ELEPHANT, 0f, body.getWorldCenter().x, body.getWorldCenter().y, true);

        return entity;
    }

    @Override
    public void update() {

    }

}