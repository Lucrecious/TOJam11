package com.lucrecious.tojam11.lut;

import com.badlogic.gdx.math.collision.BoundingBox;
import com.nilunder.bdx.GameObject;

public abstract class DimensionalGameObject extends GameObject {
    private Float _height;
    public float height() {
        if (_height == null) {
            BoundingBox box = new BoundingBox();
            modelInstance.model.calculateBoundingBox(box);
            _height = box.getHeight() / 2;
        }

        return _height;
    }

    private Float _depth;
    public float depth() {
        if (_depth == null) {
            BoundingBox box = new BoundingBox();
            modelInstance.model.calculateBoundingBox(box);
            _depth = box.getDepth() / 2;
        }

        return _depth;
    }

    private Float _width;

    public float width() {
        if (_width == null) {
            BoundingBox box = new BoundingBox();
            modelInstance.model.calculateBoundingBox(box);
            _width = box.getWidth() / 2;
        }

        return _width;
    }

}
