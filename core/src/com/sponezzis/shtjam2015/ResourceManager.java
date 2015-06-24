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
    private static BitmapFont _scoreFont;

    public static void initialize() {
        _textures = new HashMap<String, Texture>();

        _textures.put("splashscreen", new Texture("splashscreen.png"));
        _textures.put("player_left", new Texture("player_left.png"));
        _textures.put("player_up", new Texture("player_up.png"));
        _textures.put("player_down", new Texture("player_down.png"));
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
        _textures.put("ground", new Texture("ground.png"));
        _textures.put("gameover", new Texture("gameover.png"));

        initFonts();
    }

    public static Texture getTexture(String name) {
        return _textures.get(name);
    }
    public static BitmapFont getHudFont() { return _hudFont; }
    public static BitmapFont getScoreFont() { return _scoreFont; }

    private static void initFonts() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("comicsans.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        _hudFont = generator.generateFont(parameter);

        FreeTypeFontGenerator.FreeTypeFontParameter parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter2.size = 64;
        _scoreFont = generator.generateFont(parameter2);

        generator.dispose();
    }

}
