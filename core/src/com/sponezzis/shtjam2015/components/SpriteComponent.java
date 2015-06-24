package com.sponezzis.shtjam2015.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by sponaas on 6/23/15.
 */
public class SpriteComponent extends Component {

    public Sprite sprite;

    public SpriteComponent(Sprite spriteInit) { sprite = spriteInit; }

}
