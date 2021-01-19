package com.github.nikitakuchur.puzzlegame.ui.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.github.nikitakuchur.puzzlegame.ui.Menu;
import com.github.nikitakuchur.puzzlegame.ui.MenuStack;
import com.github.nikitakuchur.puzzlegame.utils.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PacksMenu extends Menu {

    private static final String LEVELS_DIR = "levels/";

    public PacksMenu(Context context, MenuStack menuStack) {
        super(context, menuStack);

        Image background = new Image((Texture) context.getAssetManager().get("ui/menu/bg1.png"));
        background.setPosition(-(float) Gdx.graphics.getWidth() / 2, -(float) Gdx.graphics.getHeight() / 2);
        background.setWidth(Gdx.graphics.getWidth());
        background.setHeight(Gdx.graphics.getHeight());

        this.addActor(background);

        AssetManager assetManager = context.getAssetManager();
        BitmapFont font = assetManager.get("ui/fonts/Roboto.ttf", BitmapFont.class);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;

        FileHandle levelsDir = Gdx.files.internal(LEVELS_DIR);
        List<FileHandle> dirs = Arrays.stream(levelsDir.list())
                .sorted(Comparator.comparing(FileHandle::name))
                .filter(FileHandle::isDirectory)
                .collect(Collectors.toList());

        List<Button> buttons = new ArrayList<>();

        for (FileHandle dir : dirs) {
            TextButton button = new TextButton("Pack " + dir.name(), textButtonStyle);
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    menuStack.push(new PackMenu(context, menuStack, dir));
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
