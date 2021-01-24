package com.github.nikitakuchur.puzzlegame.ui.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.github.nikitakuchur.puzzlegame.screens.LevelScreen;
import com.github.nikitakuchur.puzzlegame.ui.Menu;
import com.github.nikitakuchur.puzzlegame.ui.MenuStack;
import com.github.nikitakuchur.puzzlegame.utils.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PackMenu extends Menu {

    public PackMenu(Context context, MenuStack menuStack, FileHandle pack) {
        super(context, menuStack);

        AssetManager assetManager = context.getAssetManager();
        BitmapFont font = assetManager.get("ui/fonts/ReemKufi.ttf", BitmapFont.class);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;

        List<FileHandle> levels = Arrays.stream(pack.list())
                .sorted(Comparator.comparing(FileHandle::name))
                .collect(Collectors.toList());

        List<Button> buttons = new ArrayList<>();

        for (FileHandle levelFile : levels) {
            TextButton button = new TextButton(pack.name() + "-" + levelFile.name().split("\\.")[0], textButtonStyle);
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Game game = context.getGame();
                    game.setScreen(new LevelScreen(context, levelFile));
                }
            });
            addActor(button);
            buttons.add(button);
        }
        TextButton backButton = new TextButton("Back", textButtonStyle);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                menuStack.pop();
            }
        });
        this.addActor(backButton);
        buttons.add(backButton);

        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setPosition(
                    -buttons.get(i).getWidth() / 2,
                    buttons.get(0).getHeight() * ((float) buttons.size() / 2) - buttons.get(0).getHeight() * i - 10 * i
            );
        }
    }
}
