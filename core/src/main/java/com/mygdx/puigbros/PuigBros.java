package com.mygdx.puigbros;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;

public class PuigBros extends Game {
	public SpriteBatch batch;
	public ShapeRenderer shapeRenderer;
	Texture img;
	public OrthographicCamera camera;
    AssetManager manager;


	@Override
	public void create () {
        manager = new AssetManager();
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setAutoShapeType(true);

		img = new Texture("libgdx.png");

		camera = new OrthographicCamera();
		camera.setToOrtho(true, 800, 480);

        batch.setProjectionMatrix(camera.projection);

        loadAssets();

		setScreen(new GameScreen(this));
	}

    void loadAssets()
    {
        for(int i = 1; i < 19; i++)
            manager.load("tiles/"+i+".png", Texture.class);
        manager.load("BG.png", Texture.class);

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
