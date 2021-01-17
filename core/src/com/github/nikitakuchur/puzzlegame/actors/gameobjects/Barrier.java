package com.github.nikitakuchur.puzzlegame.actors.gameobjects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.github.nikitakuchur.puzzlegame.level.Layer;
import com.github.nikitakuchur.puzzlegame.level.Level;
import com.github.nikitakuchur.puzzlegame.physics.EmptyCollider;
import com.github.nikitakuchur.puzzlegame.physics.FullCollider;
import com.github.nikitakuchur.puzzlegame.physics.PhysicalController;
import com.github.nikitakuchur.puzzlegame.physics.PhysicalObject;
import com.github.nikitakuchur.puzzlegame.serialization.Parameters;
import com.github.nikitakuchur.puzzlegame.utils.Context;

public class Barrier extends GameObject implements Switchable, PhysicalObject {

    private final TextureRegion textureRegion;

    private boolean opened;
    private String switchName;

    private PhysicalController physicalController;

    public Barrier(Context context) {
        AssetManager assetManager = context.getAssetManager();
        textureRegion = new TextureRegion(assetManager.get("textures/box/box.png", Texture.class));
    }

    @Override
    public void initialize(Level level) {
        super.initialize(level);
        physicalController = new PhysicalController(this);
        physicalController.freeze();
        if (opened) {
            physicalController.setCollider(EmptyCollider.INSTANCE);
        } else {
            physicalController.setCollider(FullCollider.INSTANCE);
        }
    }

    @Override
    public void onSwitch() {
        if (opened) {
            physicalController.setCollider(FullCollider.INSTANCE);
            clearActions();
            addAction(Actions.fadeIn(0.1f));
        } else {
            physicalController.setCollider(EmptyCollider.INSTANCE);
            clearActions();
            addAction(Actions.fadeOut(0.1f));
        }
        opened = !opened;
    }

    @Override
    public String getSwitch() {
        return switchName;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.setColor(getColor());
        Vector2 position = getActualPosition();
        batch.draw(textureRegion, position.x, position.y, getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        batch.draw(textureRegion, position.x, position.y, getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX() / 2, getScaleY() / 2, getRotation());
    }

    @Override
    public Layer getLayer() {
        return Layer.LAYER_3;
    }

    @Override
    public PhysicalController getPhysicalController() {
        return physicalController;
    }

    @Override
    public Parameters getParameters() {
        Parameters parameters = super.getParameters();
        parameters.put("opened", Boolean.class, opened);
        parameters.put("switch", String.class, switchName);
        return parameters;
    }

    @Override
    public void setParameters(Parameters parameters) {
        super.setParameters(parameters);
        opened = parameters.getValue("opened");
        if (opened) {
            getColor().a = 0.f;
        } else {
            getColor().a = 1.f;
        }
        switchName = parameters.getValue("switch");
    }
}
