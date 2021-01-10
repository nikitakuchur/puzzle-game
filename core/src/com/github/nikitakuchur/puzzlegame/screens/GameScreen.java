package com.github.nikitakuchur.puzzlegame.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.github.nikitakuchur.puzzlegame.utils.Context;

public abstract class GameScreen extends ScreenAdapter {

    private final Context context;

    protected GameScreen(Context context) {
        this.context = context;
    }

    /**
     * Returns the context.
     */
    public Context getContext() {
        return context;
    }
}
