package com.mygdx.puigbros;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class PuigBros extends Game {
	public SpriteBatch batch, textBatch;
	public ShapeRenderer shapeRenderer;
    BitmapFont smallFont, mediumFont, bigFont;
	Texture img;
	public OrthographicCamera camera, textCamera;
    AssetManager manager;

    int lifes;


	@Override
	public void create () {
        manager = new AssetManager();
		batch = new SpriteBatch();
        textBatch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setAutoShapeType(true);

        FreeTypeFontGenerator generator = new
            FreeTypeFontGenerator(Gdx.files.internal("Dogfiles.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new
            FreeTypeFontGenerator.FreeTypeFontParameter();

        params.size = 32;
        params.borderWidth = 2;
        params.borderColor = Color.BLACK;
        params.color = Color.WHITE;
        smallFont = generator.generateFont(params); // font size 22 pixels

        params.size = 42;
        params.borderWidth = 4;
        params.borderColor = Color.BLACK;
        params.color = Color.WHITE;
        mediumFont = generator.generateFont(params); // font size 22 pixels

        params.size = 64;
        params.borderWidth = 8;
        params.color = Color.RED;
        bigFont = generator.generateFont(params); // font size 22 pixels

		img = new Texture("libgdx.png");

		camera = new OrthographicCamera();
		camera.setToOrtho(true, 800, 480);

        textCamera = new OrthographicCamera();
        textCamera.setToOrtho(false, 800, 480);
        textCamera.translate(-400,-240);

        batch.setProjectionMatrix(camera.projection);
        textBatch.setProjectionMatrix(textCamera.projection);

        loadAssets();

        lifes = 3;
		setScreen(new MainMenuScreen(this));
	}

    void loadAssets()
    {
        for(int i = 1; i < 19; i++)
            manager.load("tiles/"+i+".png", Texture.class);
        manager.load("BG.png", Texture.class);

        //GUI
        manager.load("gui/Button-off.png", Texture.class);
        manager.load("gui/Button-on.png", Texture.class);
        manager.load("gui/Left-off.png", Texture.class);
        manager.load("gui/Left-on.png", Texture.class);
        manager.load("gui/Right-off.png", Texture.class);
        manager.load("gui/Right-on.png", Texture.class);
        manager.load("gui/Jump-off.png", Texture.class);
        manager.load("gui/Jump-on.png", Texture.class);
        manager.load("gui/Pause-off.png", Texture.class);
        manager.load("gui/Pause-on.png", Texture.class);


        // Player
        for (int i = 0; i < 10; i++)
        {
            manager.load("player/Idle (" +(i+1)+").png", Texture.class);
        }
        for (int i = 0; i < 8; i++)
        {
            manager.load("player/Run (" +(i+1)+").png", Texture.class);
        }
        for (int i = 0; i < 12; i++)
        {
            manager.load("player/Jump (" +(i+1)+").png", Texture.class);
        }
        for (int i = 0; i < 10; i++)
        {
            manager.load("player/Dead (" +(i+1)+").png", Texture.class);
        }

        //Dino
        for (int i = 0; i < 10; i++)
        {
            manager.load("dino/Walk (" +(i+1)+").png", Texture.class);
        }
        for (int i = 0; i < 8; i++)
        {
            manager.load("dino/Dead (" +(i+1)+").png", Texture.class);
        }

        //PowerUp
        for (int i = 0; i < 7; i++)
        {
            manager.load("powerup/frame000" +i+".png", Texture.class);
        }

        manager.finishLoading();
    }

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
