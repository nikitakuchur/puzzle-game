package com.triateq.gravitymaze.game.gameobjects;

import com.triateq.gravitymaze.core.game.GameObject;
import com.triateq.gravitymaze.core.serialization.annotations.Parameter;

public class LevelProperties extends GameObject {

    private int moves = 0;

    @Parameter
    private int maxMoves = 0;

    public int getMoves() {
        return moves;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }

    public int getMaxMoves() {
        return maxMoves;
    }
}
