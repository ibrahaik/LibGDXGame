package com.mygdx.puigbros;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Turtle extends WalkingCharacter
{

    static final float RUN_SPEED = 120f;
    static final float RUN_ACCELERATION = 200f;
    boolean lookRight = false;
    public Turtle(int x, int y)
    {
        setBounds(x,y,64, 64);
    }


    @Override
    public void act(float delta) {
        super.act(delta);

        if(!falling)
        {
            if(lookRight)
            {
                speed.x += RUN_ACCELERATION * delta;
                if(speed.x > RUN_SPEED)
                {
                    speed.x = RUN_SPEED;
                }
            }
            else
            {
                speed.x -= RUN_ACCELERATION * delta;
                if(speed.x < -RUN_SPEED)
                {
                    speed.x = -RUN_SPEED;
                }
            }
        }
    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {
        //super.drawDebug(shapes);

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(Color.GREEN);
        shapes.rect(getX() - getWidth()*0.5f - map.scrollX, getY() - getHeight()*0.5f, getWidth(), getHeight());
        shapes.end();
    }

}
