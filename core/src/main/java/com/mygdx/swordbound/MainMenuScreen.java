package com.mygdx.swordbound;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

public class MainMenuScreen implements Screen
{
    SwordBound game;
    ButtonLayout mainMenu;
    public MainMenuScreen(SwordBound game)
    {
        this.game = game;

        mainMenu = new ButtonLayout(game.camera, game.manager, game.mediumFont);
        mainMenu.loadFromJson("mainmenu.json");
    }

    @Override
    public void show() {

    }


    @Override
    public void render(float delta) {

        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        game.textBatch.setProjectionMatrix(game.textCamera.combined);

        game.batch.begin();
        game.batch.draw(game.manager.get("guiV2/mainmenu/backgroundPC.png", Texture.class), 0, 0, 1200, 800, 0,0, 1000, 750, false, true);
        game.batch.end();

        game.textBatch.begin();
        game.bigFont.draw(game.textBatch,"SWORDBOUND", 115, 480 - 60);
        game.textBatch.end();

        mainMenu.render(game.batch, game.textBatch);


        // Start the game!
        if(mainMenu.consumeRelease("Start"))
        {
            game.playerHealth = 4;
            game.setScreen(new GameScreen(game, "Level1.json"));
            this.dispose();
        }
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
