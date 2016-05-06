package com.lucrecious.tojam11.lut.mapping;

import javax.vecmath.Vector2f;

public abstract class DirectionalMap {

    protected Controller.Type type;

    public DirectionalMap(String name, float threshold, Controller.Type type) {
        _name = name;
        _threshold = threshold;
        this.type = type;
    }

    public abstract Vector2f vector();

    private float _threshold;
    public float threshold() {
        return _threshold;
    }

    private String _name;
    public String name() {
        return _name;
    }
}
