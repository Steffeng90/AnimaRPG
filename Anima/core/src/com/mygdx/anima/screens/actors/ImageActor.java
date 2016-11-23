package com.mygdx.anima.screens.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.ColorAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.rotateTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;

/**
 * Created by Steffen on 23.11.2016.
 */

public class ImageActor extends Image {
    @Override

    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(this.getColor());
        ((TextureRegionDrawable)getDrawable()).draw(batch,getX(),getY(),
                getOriginX(),getOriginY(),
                getWidth(),getHeight(),
                getScaleX(),getScaleY(),getRotation());
    }

    public ImageActor(Texture texture){
        super(texture);
        setBounds(getX(),getY(),getWidth(),getHeight());

        addListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                switch (keycode) {
                    case Input.Keys.NUM_1:
                        MoveToAction moveToAction = new MoveToAction();
                        moveToAction.setPosition(200f, 200f);
                        moveToAction.setDuration(5f);
                        ImageActor.this.addAction(moveToAction);
                        break;
                    case Input.Keys.NUM_2:
                        MoveByAction mba=new MoveByAction();
                        mba.setAmount(-200f,0f);
                        mba.setDuration(3f);
                        ImageActor.this.addAction(mba);
                        break;
                    case Input.Keys.NUM_3:
                        ColorAction colAct=new ColorAction();
                        colAct.setEndColor(Color.PURPLE);
                        colAct.setDuration(2f);
                        ImageActor.this.addAction(colAct);
                        break;
                    case Input.Keys.NUM_4:
                        MoveToAction mta=new MoveToAction();
                        mta.setPosition(Gdx.graphics.getWidth()-200f,Gdx.graphics.getHeight()-200);
                        mta.setDuration(3f);

                        ScaleByAction sba=new ScaleByAction();
                        sba.setAmount(2f);
                        sba.setDuration(3f);

                        RotateToAction rta=new RotateToAction();
                        rta.setRotation(90f);
                        rta.setDuration(3f);

                        ParallelAction pa=new ParallelAction(mta,sba,rta);

                        ImageActor.this.addAction(pa);
                        break;
                    case Input.Keys.NUM_5:
                        MoveToAction mta2=new MoveToAction();
                        mta2.setPosition(getWidth()-200f,getHeight()-200);
                        mta2.setDuration(2f);

                        ScaleByAction sba2=new ScaleByAction();
                        sba2.setAmount(2f);
                        sba2.setDuration(2f);

                        RotateToAction rta2=new RotateToAction();
                        rta2.setRotation(90f);
                        rta2.setDuration(2f);

                       SequenceAction sqa=new SequenceAction(mta2,sba2,rta2);

                        ImageActor.this.addAction(sqa);
                        break;
                    case Input.Keys.NUM_6:
                        RunnableAction runA=new RunnableAction();
                        runA.setRunnable(new Runnable() {
                            @Override
                            public void run() {
                                ImageActor.this.setPosition(0f,0f);
                                ImageActor.this.setRotation(0f);
                                ImageActor.this.setScale(1f);

                            }
                        });
                        ImageActor.this.addAction(runA);
                        break;
                    case Input.Keys.SPACE:
                        addAction(parallel(
                                moveTo(200f,200f),
                                scaleTo(1,5),
                                rotateTo(45)
                        ));

                }

                return true;
            }
        });
    }
}
