package com.lucrecious.tojam11.lut;

import javax.vecmath.Matrix3f;
import javax.vecmath.Vector3f;

public class Math {
    public static int boolToInt(boolean bool) {
        return bool ? 1 : 0;
    }

    public static Vector3f matrix3fByVector3f(Matrix3f matrix, Vector3f vector) {
        Vector3f row = new Vector3f();

        matrix.getRow(0, row);
        float x = row.dot(vector);

        matrix.getRow(1, row);
        float y = row.dot(vector);

        matrix.getRow(2, row);
        float z = row.dot(vector);

        return new Vector3f(x, y, z);
    }

    public static Vector3f project(Vector3f magnitude, Vector3f direction) {
        float directionLength = direction.length();
        return direction.mul(magnitude.dot(direction) / (directionLength * directionLength));
    }

    public static float boundedExponential(float vStretch, float hCompression, float vUp, float hLeft, float timeSec) {
        return (float)(vStretch * (1 - java.lang.Math.exp(-hCompression*(timeSec + hLeft)))) + vUp;
    }

    public static Vector3f closestPointOnLine(Vector3f start, Vector3f dir, Vector3f point) {
        float t = (point.x * start.x + point.y * start.y + point.z * start.z) /
                -(point.x * dir.x + point.y * dir.y + point.z * dir.z);

        return start.plus(dir.mul(t));
    }
}
