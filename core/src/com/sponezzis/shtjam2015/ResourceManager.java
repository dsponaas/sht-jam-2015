package com.sponezzis.shtjam2015;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

/**
 * Created by sponaas on 6/23/15.
 */
public class ResourceManager {

    private static HashMap<String, Texture> _textures;

    public static void initialize() {
        _textures = new HashMap<String, Texture>();

        _textures.put("splashscreen", new Texture("splashscreen.png"));
        _textures.put("player", new Texture("player.png"));
        _textures.put("bullet", new Texture("bullet.png"));
        _textures.put("elephant", new Texture("elephant.png"));
    }

    public static Texture getTexture(String name) {
        return _textures.get(name);
    }

}
