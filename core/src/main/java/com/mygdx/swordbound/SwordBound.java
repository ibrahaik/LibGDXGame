package com.mygdx.swordbound;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class SwordBound extends Game
{
	public SpriteBatch batch, textBatch;
	public ShapeRenderer shapeRenderer;
    BitmapFont smallFont, mediumFont, bigFont;
	public OrthographicCamera camera, textCamera;
    AssetManager manager;

    public int playerHealth = 4;


	@Override
	public void create ()
    {
        manager = new AssetManager();
		batch = new SpriteBatch();
        textBatch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setAutoShapeType(true);

        // Fonts
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
        params.color = new Color(0.8f, 0.8f, 0.6f, 1); // Un dorado suave o marfil
        params.borderColor = new Color(0.2f, 0.2f, 0.2f, 1); // Gris oscuro discreto

        bigFont = generator.generateFont(params);
        // font size 22 pixels

        // Cameras
		camera = new OrthographicCamera();
		camera.setToOrtho(true, 800, 480);

        textCamera = new OrthographicCamera();
        textCamera.setToOrtho(false, 800, 480);
        textCamera.translate(-400,-240);

        batch.setProjectionMatrix(camera.projection);
        textBatch.setProjectionMatrix(textCamera.projection);


		setScreen(new LoadingScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
