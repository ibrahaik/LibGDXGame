package com.mygdx.puigbros;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;


public class LoadingScreen implements Screen {

    PuigBros game;
    float loadProgress;

    LoadingScreen(PuigBros game)
    {
        this.game = game;
        AssetManager  manager = game.manager;

        // Add assets for loading

        // Tiles
        for(int i = 1; i < 19; i++)
            manager.load("tiles/"+i+".png", Texture.class);

        // Background image
        manager.load("BG.png", Texture.class);

        //GUI
        manager.load("gui/Button-off.png", Texture.class);
        manager.load("gui/Button-on.png", Texture.class);
        manager.load("guiV2/izquierdaon.png", Texture.class);
        manager.load("guiV2/izquierdaoff.png", Texture.class);
        manager.load("guiV2/derechaon.png", Texture.class);
        manager.load("guiV2/derechaoff.png", Texture.class);
        manager.load("guiV2/pausaoff.png", Texture.class);
        manager.load("guiV2/pausaon.png", Texture.class);
        manager.load("guiV2/xboxoff.png", Texture.class);
        manager.load("guiV2/xboxon.png", Texture.class);
        manager.load("guiV2/button_xbox_digital_a_4.png", Texture.class);
        manager.load("guiV2/button_xbox_digital_a_6.png", Texture.class);



        // Player
        for (int i = 0; i < 10; i++)
        {
            manager.load("player/Idle (" +(i+1)+").png", Texture.class);
        }
        for (int i = 0; i < 12; i++) {
            String numberStr = String.format("%03d", i); // formato 3 dígitos con ceros a la izquierda
            manager.load("player/Valkyria/running/0_Valkyrie_Running_" + numberStr + ".png", Texture.class);
        }

        for (int i = 0; i < 6; i++)
        {
            String numberStr = String.format("%03d", i); // formato 3 dígitos con ceros a la izquierda
            manager.load("player/Valkyria/jumpingLoop/0_Valkyrie_Jump Loop_" + numberStr + ".png", Texture.class);
        }

        for (int i = 0; i < 6; i++)
        {
            String numberStr = String.format("%03d", i); // formato 3 dígitos con ceros a la izquierda
            manager.load("player/Valkyria/jumpingStart/0_Valkyrie_Jump Start_" + numberStr + ".png", Texture.class);
        }

        for (int i = 0; i < 15; i++)
        {
            String numberStr = String.format("%03d", i); // formato 3 dígitos con ceros a la izquierda
            manager.load("player/Valkyria/Dying/0_Valkyrie_Dying_" + numberStr + ".png", Texture.class);        }

        for (int i = 0; i < 12; i++)
        {
            String numberStr = String.format("%03d", i); // formato 3 dígitos con ceros a la izquierda
            manager.load("player/Valkyria/slashing/0_Valkyrie_Slashing_" + numberStr + ".png", Texture.class);        }

        // Goblin

        for (int i = 0; i < 24; i++)
        {
            String numberStr = String.format("%03d", i); // formato 3 dígitos con ceros a la izquierda
            manager.load("enemies/goblin/walking/0_Goblin_Walking_" + numberStr + ".png", Texture.class);        }

        for (int i = 0; i < 15; i++)
        {
            String numberStr = String.format("%03d", i); // formato 3 dígitos con ceros a la izquierda
            manager.load("enemies/goblin/dying/0_Goblin_Dying_" + numberStr + ".png", Texture.class);        }

       // Ogre

        for (int i = 0; i < 15; i++)
        {
            String numberStr = String.format("%03d", i); // formato 3 dígitos con ceros a la izquierda
            manager.load("enemies/ogre/dying/0_Ogre_Dying_" + numberStr + ".png", Texture.class);        }

        for (int i = 0; i < 12; i++)
        {
            String numberStr = String.format("%03d", i); // formato 3 dígitos con ceros a la izquierda
            manager.load("enemies/ogre/slashing/0_Ogre_Slashing_" + numberStr + ".png", Texture.class);        }

        for (int i = 0; i < 24; i++)
        {
            String numberStr = String.format("%03d", i); // formato 3 dígitos con ceros a la izquierda
            manager.load("enemies/ogre/walking/0_Ogre_Walking_" + numberStr + ".png", Texture.class);        }

        // Orco

        for (int i = 0; i < 15; i++)
        {
            String numberStr = String.format("%03d", i); // formato 3 dígitos con ceros a la izquierda
            manager.load("enemies/orco/dying/0_Orc_Dying_" + numberStr + ".png", Texture.class);        }

        for (int i = 0; i < 12; i++)
        {
            String numberStr = String.format("%03d", i); // formato 3 dígitos con ceros a la izquierda
            manager.load("enemies/orco/runSlashing/0_Orc_Run Slashing_" + numberStr + ".png", Texture.class);        }

        for (int i = 0; i < 12; i++)
        {
            String numberStr = String.format("%03d", i); // formato 3 dígitos con ceros a la izquierda
            manager.load("enemies/orco/slashing/0_Orc_Slashing_" + numberStr + ".png", Texture.class);        }

        for (int i = 0; i < 24; i++)
        {
            String numberStr = String.format("%03d", i); // formato 3 dígitos con ceros a la izquierda
            manager.load("enemies/orco/walking/0_Orc_Walking_" + numberStr + ".png", Texture.class);        }

        //PowerUp
        for (int i = 0; i < 7; i++)
        {
            manager.load("powerup/frame000" +i+".png", Texture.class);
        }

        // Sounds
        manager.load("sound/music.mp3", Music.class);
        manager.load("sound/loselife.wav", Sound.class);
        manager.load("sound/kill.wav", Sound.class);
        manager.load("sound/jump.wav", Sound.class);
        manager.load("sound/powerup.wav", Sound.class);
        manager.load("sound/levelcomplete.wav", Sound.class);
        manager.load("sound/hit-flesh-01-266311.wav", Sound.class);
        manager.load("sound/sword-hit-7160.wav", Sound.class);
        manager.load("sound/hurt.wav", Sound.class);
        manager.load("sound/death.wav", Sound.class);

        loadProgress = 0f;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        // Render step =============================================
        float currentLoadProgress = game.manager.getProgress();
        if(currentLoadProgress > loadProgress + 0.05f)
        {
            loadProgress = currentLoadProgress;

            game.camera.update();
            game.batch.setProjectionMatrix(game.camera.combined);
            game.textBatch.setProjectionMatrix(game.textCamera.combined);
            game.shapeRenderer.setProjectionMatrix(game.camera.combined);

            ScreenUtils.clear(Color.BLACK);

            // Progress bar
            game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            game.shapeRenderer.setColor(Color.YELLOW);
            game.shapeRenderer.rect(90, 290, 620, 100);
            game.shapeRenderer.setColor(Color.BLACK);
            game.shapeRenderer.rect(100, 300, 600, 80);
            game.shapeRenderer.setColor(Color.ORANGE);
            game.shapeRenderer.rect(110, 310, 580 * loadProgress, 60);
            game.shapeRenderer.end();

            game.textBatch.begin();
            game.bigFont.draw(game.textBatch, "CARREGANT...", 120, 340);
            game.mediumFont.draw(game.textBatch, (int) (loadProgress * 100.f) + "%", 360, 160);
            game.textBatch.end();

        }

        // Update step ====================================
        if(game.manager.update())
        {
            game.setScreen(new MainMenuScreen(game));
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
