package com.mygdx.puigbros;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Player extends WalkingCharacter {

    static final float JUMP_IMPULSE = -600f;
    static final float RUN_SPEED = 240f;
    static final float BRAKE_SPEED = 120f;
    static final float STOP_SPEED = 5f;
    static final float RUN_ACCELERATION = 200f;
    static final float INVULNERABILITY_DURATION = 20f;

    AssetManager manager;
    ButtonLayout joypad;

    Texture currentFrame;

    float animationFrame = 0;
    boolean lookLeft = false;
    float invulnerability = 0f;

    public Player(AssetManager manager)
    {
        setBounds(400,40,48, 112);
        this.manager = manager;
        currentFrame = manager.get("player/Idle (1).png", Texture.class);
        invulnerability = 0.f;

    }

    public void setJoypad(ButtonLayout joypad) {
        this.joypad = joypad;
    }
    @Override
    public void act(float delta) {
        super.act(delta);

        if(getY() > map.height * TileMap.TILE_SIZE)
        {
            kill();
        }

        if(getX() < getWidth() / 2)
        {
            setX(getWidth() / 2);
        }

        if(dead)
        {
            animationFrame += 10.f*delta;
            int frameTexture = (int)animationFrame+1;
            if(frameTexture > 10)
                frameTexture = 10;
            currentFrame = manager.get("player/Dead ("+frameTexture+").png", Texture.class);

            speed.x = 0f;
        }
        else
        {
            if(invulnerability > 0.f)
                invulnerability -= delta;

            if(falling)
            {
                if(speed.y < 0)
                {
                    float base_impulse = -JUMP_IMPULSE;
                    float current_impulse = -speed.y;
                    animationFrame = 0 + ((base_impulse - current_impulse) / 32);
                    if (animationFrame > 8) animationFrame = 8;
                }
                else
                {
                    animationFrame = 9 + (speed.y / 64);
                    if (animationFrame > 11) animationFrame = 11;
                }
                currentFrame = manager.get("player/Jump ("+(int)(animationFrame+1)+").png", Texture.class);


            }
            else if((speed.x < 0.1f && speed.x > -0.1f))
            {
                // Idle
                animationFrame += 10 * delta;
                if (animationFrame >= 10.f) animationFrame -= 10.f;
                currentFrame = manager.get("player/Idle ("+(int)(animationFrame+1)+").png", Texture.class);

            }
            else
            {
                // Walk
                animationFrame += 10 * delta;
                if (animationFrame >= 8.f) animationFrame -= 8.f;
                currentFrame = manager.get("player/Run ("+(int)(animationFrame+1)+").png", Texture.class);

            }

            if(!falling && joypad.consumePush("Jump"))
            {
                jump(1.f);
                manager.get("sound/jump.wav", Sound.class).play();
            }

            if(!falling) {
                if (joypad.isPressed("Right")) {
                    lookLeft = false;
                    speed.x += RUN_ACCELERATION * delta;
                    if (speed.x > RUN_SPEED) {
                        speed.x = RUN_SPEED;
                    }
                } else if (joypad.isPressed("Left")) {
                    lookLeft = true;
                    speed.x -= RUN_ACCELERATION * delta;
                    if (speed.x < -RUN_SPEED) {
                        speed.x = -RUN_SPEED;
                    }
                } else {
                    if(speed.x < 0f)
                    {
                        if(speed.x < -STOP_SPEED) {
                            speed.x += delta * BRAKE_SPEED;
                        }
                        else
                        {
                            speed.x = 0f;
                        }
                    }
                    else if (speed.x > 0f)
                    {
                        if(speed.x > STOP_SPEED) {
                            speed.x -= delta * BRAKE_SPEED;
                        }
                        else
                        {
                            speed.x = 0f;
                        }
                    }
                    /*speed.x *= 1 - (0.99f * delta);
                    if (speed.x < STOP_SPEED && speed.x >= -STOP_SPEED) {
                        speed.x = 0;
                    }*/
                }
            }
        }
    }

    public void jump(float strength)
    {
        speed.y = JUMP_IMPULSE * strength;
    }

    @Override
    public void kill()
    {
        if(!dead) {
            super.kill();
            animationFrame = 0;
        }
    }

    public void getInvulnerability()
    {
        invulnerability = INVULNERABILITY_DURATION;
    }

    public boolean hasInvulnerability()
    {
        return invulnerability > 0.f;
    }

    float getAnimationFrame()
    {
        return animationFrame;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if(invulnerability > 0.f && (int)(invulnerability/0.125f)%2 == 0)
            return;

        batch.draw(currentFrame, getX() - getWidth()*0.5f - map.scrollX - (lookLeft ? 28 : 50), getY() - getHeight()*0.5f, 128, 128, 0, 0, 669, 569, lookLeft, true);
    }

    public void drawDebug(ShapeRenderer shapes) {
        //super.drawDebug(shapes);

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(Color.NAVY);
        shapes.rect(getX() - getWidth()*0.5f - map.scrollX, getY() - getHeight()*0.5f, getWidth(), getHeight());
        shapes.end();
    }
}
