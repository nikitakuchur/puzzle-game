package com.github.nikitakuchur.puzzlegame.editor.commands;

import com.badlogic.gdx.utils.Disposable;
import com.github.nikitakuchur.puzzlegame.actors.gameobjects.GameObject;
import com.github.nikitakuchur.puzzlegame.actors.gameobjects.GameObjectStore;

public class RemoveGameObjectCommand implements Command {

    private final GameObject gameObject;
    private final GameObjectStore gameObjectStore;
    private boolean removed;

    public RemoveGameObjectCommand(GameObject gameObject, GameObjectStore gameObjectStore) {
        this.gameObject = gameObject;
        this.gameObjectStore = gameObjectStore;
    }

    @Override
    public void execute() {
        gameObjectStore.remove(gameObject);
        removed = true;
    }

    @Override
    public void unexecute() {
        gameObjectStore.add(gameObject);
        removed = false;
    }

    @Override
    public void dispose() {
        if (removed && gameObject instanceof Disposable) {
            ((Disposable) gameObject).dispose();
        }
    }
}
