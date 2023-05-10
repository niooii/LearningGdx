package com.niooi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
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
    AssetManager manager = new AssetManager();

    private OrthogonalTiledMapRenderer tmRenderer;


    public GameScreen(OrthographicCamera cam){
        this.cam = cam;
        this.batch = new SpriteBatch();
        this.world = new World(new Vector2(0, 0), false);
        this.debugRenderer = new Box2DDebugRenderer();
//        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
//        manager.load("testmap.tmx", TiledMap.class);
        this.tmRenderer = new OrthogonalTiledMapRenderer(new TmxMapLoader().load("testmap.tmx"));
        cam.zoom = 4;
    }

    private void update(){
        world.step(1/60f, 6, 2);

        camUpdate();

        batch.setProjectionMatrix(cam.combined);

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    private void handleInput() {

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            cam.translate(-3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            cam.translate(3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            cam.translate(0, -3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            cam.translate(0, 3, 0);
        }

    if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
        cam.zoom += 0.1;
    }

        if (Gdx.input.isKeyPressed(Input.Keys.X)) {
            cam.zoom -= 0.1;
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
        cam.translate(-3, 0, 0);

        tmRenderer.setView(cam);
        tmRenderer.render();

        batch.begin();

        batch.end();
        handleInput();
        debugRenderer.render(world, cam.combined.scl(PPM));
    }
}
