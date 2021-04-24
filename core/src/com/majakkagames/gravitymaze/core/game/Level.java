package com.majakkagames.gravitymaze.core.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.IntMap;
import com.majakkagames.gravitymaze.core.serialization.Parameters;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.majakkagames.gravitymaze.core.game.GameObjectStore.GameObjectEvent.*;

public final class Level extends GameObject implements Disposable {

    private static final int DEFAULT_LAYERS_NUMBER = 10;

    private final Context context;

    private boolean initialized;

    private final GameObjectStore store = new GameObjectStore(this);
    private final IntMap<Group> groups = new IntMap<>();

    private boolean pause;

    public Level(Context context) {
        this.context = context;

        IntStream.range(0, DEFAULT_LAYERS_NUMBER).forEach(layer -> {
            Group group = new Group();
            groups.put(layer, group);
            addActor(group);
        });

        store.addEventHandler(e -> {
            GameObject gameObject = e.getGameObject();
            int layer = gameObject.getLayer();
            if (e.getType() == Type.ADD) {
                groups.get(layer).addActor(gameObject);
            } else if (e.getType() == Type.REMOVE) {
                groups.get(layer).removeActor(gameObject);
            }
        });

        groups.values().forEach(this::addActor);
    }

    public void initialize() {
        store.initialize();
        initialized = true;
    }

    @Override
    public void act(float delta) {
        if (!initialized) {
            throw new IllegalStateException("Level object must be initialized!");
        }
        if (!pause) {
            super.act(delta);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (!initialized) {
            throw new IllegalStateException("Level object must be initialized!");
        }
        super.draw(batch, parentAlpha);
    }

    public Context getContext() {
        return context;
    }

    public GameObjectStore getGameObjectStore() {
        return store;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public boolean onPause() {
        return pause;
    }

    @Override
    public Parameters getParameters() {
        Parameters parameters = new Parameters();
        GameObject[] gameObjects = store.getGameObjects().toArray(new GameObject[0]);
        parameters.put("gameObjects", gameObjects.getClass(), gameObjects);
        return parameters;
    }

    @Override
    public void setParameters(Parameters parameters) {
        GameObject[] gameObjects = parameters.getValue("gameObjects");

        store.clear();
        clearChildren();
        groups.values().forEach(this::addActor);

        Stream.of(gameObjects).forEach(store::add);
    }

    @Override
    public void dispose() {
        store.getGameObjects(Disposable.class).forEach(Disposable::dispose);
    }
}
