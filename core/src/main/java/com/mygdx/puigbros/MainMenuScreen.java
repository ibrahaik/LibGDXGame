package com.mygdx.puigbros;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen
{
    PuigBros game;
    ButtonLayout mainMenu;
    public MainMenuScreen(PuigBros game)
    {
        this.game = game;

        mainMenu = new ButtonLayout(game.camera, game.manager);
        mainMenu.loadFromJson("mainmenu.json");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);

        game.batch.begin();
        game.batch.draw(game.manager.get("BG.png", Texture.class), 0, 0, 800, 480, 0,0, 1000, 750, false, true);
        game.batch.end();

        mainMenu.render(game.batch);

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
