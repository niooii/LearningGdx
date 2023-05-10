package com.niooi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import static com.niooi.game.helper.Constants.PPM;

public class GameScreen extends ScreenAdapter {

    private OrthographicCamera cam;
    private SpriteBatch batch;
    private World world;
    private Box2DDebugRenderer debugRenderer;

    public GameScreen(OrthographicCamera cam){
        this.cam = cam;
        this.batch = new SpriteBatch();
        this.world = new World(new Vector2(0, 0), false);
        this.debugRenderer = new Box2DDebugRenderer();
    }

    private void update(){
        world.step(1/60f, 6, 2);

        camUpdate();

        batch.setProjectionMatrix(cam.combined);

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    private void camUpdate(){
        cam.position.scl(new Vector3(0, 0, 0));
        cam.update();
    }

    @Override
    public void render(float delta) {
        this.update();

        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.end();
        debugRenderer.render(world, cam.combined.scl(PPM));
    }
}
