package com.sponezzis.shtjam2015;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.physics.box2d.*;
import com.sponezzis.shtjam2015.components.BodyComponent;

/**
 * Created by sponaas on 6/23/15.
 */
public class ContactManager implements ContactListener {

    private Engine _engine;
    private World _world;
    private ComponentMapper<BodyComponent> _bodyComponents = ComponentMapper.getFor(BodyComponent.class);

    public ContactManager(Engine engine, World world) {
        _engine = engine;
        _world = world;
        _world.setContactListener(this);
    }

    @Override
    public void beginContact(Contact contact) {

    }

    @Override
    public void endContact(Contact contact) {}

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}
}
