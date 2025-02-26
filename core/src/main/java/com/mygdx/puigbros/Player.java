package com.mygdx.puigbros;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Player extends WalkingCharacter {

    static final float JUMP_IMPULSE = -400f;
    static final float RUN_SPEED = 240f;
    static final float RUN_ACCELERATION = 200f;

    Joypad joypad;

    Texture idleTextures[];

    float animationFrame = 0;
    boolean lookLeft = false;

    public Player()
    {
        setBounds(400,40,64, 128);
        loadTextures();
    }

    public void loadTextures()
    {
        idleTextures = new Texture[10];

        for (int i = 0; i < 10; i++)
        {
            idleTextures[i] = new Texture("player/Idle (" +(i+1)+").png");
        }
    }

    public void setJoypad(Joypad joypad) {
        this.joypad = joypad;
    }
    @Override
    public void act(float delta) {
        super.act(delta);

        animationFrame += 10 * delta;
        if (animationFrame >= 10.f) animationFrame -= 10.f;

        if(getX() < getWidth() / 2)
        {
            setX(getWidth() / 2);
        }

        if(!falling && joypad.isPressed("Jump"))
        {
            speed.y = JUMP_IMPULSE;
        }

        if(!falling)
        {
            if(joypad.isPressed("Right"))
            {
                lookLeft = false;
                speed.x += RUN_ACCELERATION * delta;
                if(speed.x > RUN_SPEED)
                {
                    speed.x = RUN_SPEED;
                }
            }
            else if(joypad.isPressed("Left"))
            {
                lookLeft = true;
                speed.x -= RUN_ACCELERATION * delta;
                if(speed.x < -RUN_SPEED)
                {
                    speed.x = -RUN_SPEED;
                }
            }
            else
            {
                speed.x *= 1 - (0.9f*delta);
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(idleTextures[(int)animationFrame], getX() - getWidth()*0.5f - map.scrollX - 42, getY() - getHeight()*0.5f + 16, 128, 128, 0, 0, 669, 569, lookLeft, true);
    }

    public void drawDebug(ShapeRenderer shapes) {
        //super.drawDebug(shapes);

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(Color.NAVY);
        shapes.rect(getX() - getWidth()*0.5f - map.scrollX, getY() - getHeight()*0.5f, getWidth(), getHeight());
        shapes.end();
    }
}
