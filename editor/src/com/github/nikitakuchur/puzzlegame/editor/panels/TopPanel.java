package com.github.nikitakuchur.puzzlegame.editor.panels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.github.nikitakuchur.puzzlegame.editor.EditorApplication;
import com.github.nikitakuchur.puzzlegame.game.Level;
import com.github.nikitakuchur.puzzlegame.utils.LevelLoader;

import javax.swing.*;

public class TopPanel extends JPanel {

    private final EditorApplication app;

    public TopPanel(EditorApplication app) {
        this.app = app;

        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(e -> Gdx.app.postRunnable(() -> {
            Level level = LevelLoader.load(Gdx.files.internal("levels/sample.json"));
            app.getEditableLevel().setLevel(level);
        }));

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            Level level = app.getEditableLevel().getLevel();

            Json json = new Json(JsonWriter.OutputType.json);
            String text = json.prettyPrint(json.toJson(level));

            FileHandle file = Gdx.files.local("levels/sample.json");
            file.writeString(text, false);
        });

        JButton playButton = new JButton("Play");
        JButton stopButton = new JButton("Stop");

        add(loadButton);
        add(saveButton);
        add(playButton);
        add(stopButton);
    }
}
