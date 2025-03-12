package com.mygdx.puigbros;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class LevelCompleteScreen implements Screen {

    PuigBros game;
    ButtonLayout mainMenu;
    public LevelCompleteScreen(PuigBros game)
    {
        this.game = game;

        mainMenu = new ButtonLayout(game.camera, game.manager, game.mediumFont);
        mainMenu.loadFromJson("endmenu.json");

        game.manager.get("sound/levelcomplete.wav", Sound.class).play();
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
        game.batch.draw(game.manager.get("BG.png", Texture.class), 0, 0, 800, 480, 0,0, 1000, 750, false, true);
        game.batch.end();

        game.textBatch.begin();
        game.bigFont.draw(game.textBatch,"CONGRATULATIONS!", 0, 480 - 60);
        game.smallFont.draw(game.textBatch,"Now create your own game!", 120, 480 - 420);
        game.textBatch.end();

        mainMenu.render(game.batch, game.textBatch);


        if(mainMenu.consumeRelease("Menu"))
        {
            this.dispose();
            game.setScreen(new MainMenuScreen(game));
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
