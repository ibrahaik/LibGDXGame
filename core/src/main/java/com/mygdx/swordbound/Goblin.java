package com.mygdx.swordbound;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Goblin extends WalkingCharacter {

    static final float RUN_SPEED = 100f;
    static final float RUN_ACCELERATION = 180f;
    static final float DISCOVER_DISTANCE = 500f;

    AssetManager manager;
    Texture currentFrame;
    Player player;

    boolean discovered;
    float animationFrame = 0;

    public Goblin(int x, int y, AssetManager manager, Player player) {
        setBounds(x, y, 64, 116);
        this.manager = manager;
        this.player = player;
        currentFrame = manager.get("enemies/goblin/walking/0_Goblin_Walking_000.png", Texture.class);
        lookLeft = true;
        discovered = false;
    }

    @Override
    public void act(float delta) {
        if (Math.abs(player.getX() - getX()) < DISCOVER_DISTANCE) discovered = true;
        if (!discovered) return;

        super.act(delta);

        if (dead) {
            animationFrame += delta * 30.f; // velocidad de animación de muerte
            speed.x = 0f;

            if (animationFrame >= 15) {
                this.remove(); // eliminar el goblin tras la animación
            } else {
                int frame = (int) animationFrame;
                String frameStr = String.format("%03d", frame);
                currentFrame = manager.get("enemies/goblin/dying/0_Goblin_Dying_" + frameStr + ".png", Texture.class);
            }
        } else if (!falling) {

            animationFrame += delta * 10.f;
            if (animationFrame >= 24.f) animationFrame = 0;
            int frame = (int) animationFrame;
            String frameStr = String.format("%03d", frame);
            currentFrame = manager.get("enemies/goblin/walking/0_Goblin_Walking_" + frameStr + ".png", Texture.class);

            // Movimiento
            if (!lookLeft) {
                speed.x += RUN_ACCELERATION * delta;
                if (speed.x > RUN_SPEED) speed.x = RUN_SPEED;
                if (map.isSolid((int) (getX() + getWidth() / 2 + delta * speed.x), (int) getY()))
                    lookLeft = true;
            } else {
                speed.x -= RUN_ACCELERATION * delta;
                if (speed.x < -RUN_SPEED) speed.x = -RUN_SPEED;
                if (map.isSolid((int) (getX() - getWidth() / 2 + delta * speed.x), (int) getY()))
                    lookLeft = false;
            }
        } else {
            currentFrame = manager.get("enemies/goblin/walking/0_Goblin_Walking_000.png", Texture.class);
        }
    }

    @Override
    public void kill() {
        super.kill();
        animationFrame = 0f;
        manager.get("sound/hit-flesh-01-266311.wav", Sound.class).play(1.0f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(currentFrame, getX() - getWidth() * 0.5f - map.scrollX - (lookLeft ? 44 : 12),
            getY() - getHeight() * 0.5f, 128, 128, 0, 0, 680, 472, lookLeft, true);
    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {
        shapes.setColor(Color.GREEN);
        shapes.rect(getX() - getWidth() * 0.5f - map.scrollX, getY() - getHeight() * 0.5f, getWidth(), getHeight());
    }
}
