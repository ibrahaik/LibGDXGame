package com.mygdx.puigbros;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Player extends WalkingCharacter {

    static final float JUMP_IMPULSE = -400f;
    static final float RUN_SPEED = 240f;
    static final float RUN_ACCELERATION = 200f;

    Joypad joypad;

    public Player()
    {
        setBounds(400,40,64, 128);
    }

    public void setJoypad(Joypad joypad) {
        this.joypad = joypad;
    }
    @Override
    public void act(float delta) {
        super.act(delta);

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
                speed.x += RUN_ACCELERATION * delta;
                if(speed.x > RUN_SPEED)
                {
                    speed.x = RUN_SPEED;
                }
            }
            else if(joypad.isPressed("Left"))
            {
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
    public void drawDebug(ShapeRenderer shapes) {
        //super.drawDebug(shapes);

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(Color.NAVY);
        shapes.rect(getX() - getWidth()*0.5f - map.scrollX, getY() - getHeight()*0.5f, getWidth(), getHeight());
        shapes.end();
    }
}
