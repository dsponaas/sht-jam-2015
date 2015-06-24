package com.sponezzis.shtjam2015;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.HashMap;

/**
 * Created by sponaas on 6/23/15.
 */
public class ResourceManager {

    private static HashMap<String, Texture> _textures;
    private static BitmapFont _hudFont;

    public static void initialize() {
        _textures = new HashMap<String, Texture>();

        _textures.put("splashscreen", new Texture("splashscreen.png"));
        _textures.put("player", new Texture("player.png"));
        _textures.put("deadplayer", new Texture("deadplayer.png"));
        _textures.put("bullet", new Texture("bullet.png"));
        _textures.put("elephant", new Texture("elephant.png"));
        _textures.put("powerup_1up", new Texture("powerup_1up.png"));
        _textures.put("powerup_rpd", new Texture("powerup_rpd.png"));
        _textures.put("powerup_rpd_small", new Texture("powerup_rpd_small.png"));
        _textures.put("powerup_sprd", new Texture("powerup_sprd.png"));
        _textures.put("powerup_sprd_small", new Texture("powerup_sprd_small.png"));
        _textures.put("powerup_2x", new Texture("powerup_2x.png"));
        _textures.put("powerup_2x_small", new Texture("powerup_2x_small.png"));

        initHudFont();
    }

    public static Texture getTexture(String name) {
        return _textures.get(name);
    }
    public static BitmapFont getHudFont() { return _hudFont; }

    private static void initHudFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("comicsans.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        _hudFont = generator.generateFont(parameter);

        generator.dispose();
    }

}
