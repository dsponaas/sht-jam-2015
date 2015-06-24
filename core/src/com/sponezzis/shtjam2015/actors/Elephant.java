package com.sponezzis.shtjam2015.actors;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.sponezzis.shtjam2015.*;
import com.sponezzis.shtjam2015.components.*;

/**
 * Created by sponaas on 6/24/15.
 */
public class Elephant extends Actor {

    private float _direction;
    private ComponentMapper<EnemyDataComponent> _enemyDataComponents = ComponentMapper.getFor(EnemyDataComponent.class);

    public Elephant(Entity entity, float direction) {
        super(entity);
        _direction = direction;
    }

    public static Elephant makeElephant(float xPos, float yPos, float direction) {
        Entity entity = new Entity();

        RenderComponent renderComponent = new RenderComponent(1000 - (int)yPos);

        Vector2 position = new Vector2(xPos, yPos);
        PositionComponent positionComponent = new PositionComponent(position.x, position.y);

        Sprite sprite = new Sprite(ResourceManager.getTexture("elephant"));
        if(direction > 0f)
            sprite.flip(true, false);
        SpriteComponent spriteComponent = new SpriteComponent(sprite);

        Body body = BodyFactory.getInstance().generate(entity, "elephant.json", position);
        BodyComponent bodyComponent = new BodyComponent(positionComponent, body);

        EnemyDataComponent enemyDataComponent = new EnemyDataComponent(Constants.POINTS_ELEPHANT);

        entity.add(renderComponent).add(positionComponent).add(spriteComponent).add(bodyComponent).add(enemyDataComponent);

        body.applyLinearImpulse(direction * Constants.SPEED_ELEPHANT, 0f, body.getWorldCenter().x, body.getWorldCenter().y, true);

        Elephant elephant = new Elephant(entity, direction);
        elephant.setSizeXInPixels(sprite.getWidth());
        elephant.setSizeYInPixels(sprite.getHeight());

        return elephant;
    }

    @Override
    public void update() {
        if((_direction < 0f) && (getPosition().x < (0f - getSizeXInPixels()))) {
            EntityManager.getInstance().destroyEntity(getEntity());
            EntityManager.getInstance().removeActor(this);
            return;
        }
        if((_direction > 0f) && (getPosition().x > GameState.getInstance().getWidth())) {
            EntityManager.getInstance().destroyEntity(getEntity());
            EntityManager.getInstance().removeActor(this);
            return;
        }

        EnemyDataComponent enemyDataComponent = _enemyDataComponents.get(getEntity());
        if(null == enemyDataComponent) {
            EntityManager.getInstance().destroyEntity(getEntity());
            EntityManager.getInstance().removeActor(this);
        }
    }

}