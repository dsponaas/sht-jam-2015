package com.sponezzis.shtjam2015.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.sponezzis.shtjam2015.components.RenderComponent;

import java.util.Comparator;

/**
 * Created by sponaas on 6/23/15.
 */
public class RenderSystemZComparator implements Comparator<Entity> {

    private ComponentMapper<RenderComponent> _renderComponents = ComponentMapper.getFor(RenderComponent.class);

    @Override
    public int compare(Entity e1, Entity e2) {
        return (int)Math.signum(_renderComponents.get(e1).order - _renderComponents.get(e2).order);
    }

}