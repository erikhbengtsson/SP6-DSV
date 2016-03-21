package engine;

/**
 * 2D vector for representing physics and positions.
 */
public class Vector2 {
    public double x;
    public double y;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2() {
        this(0, 0);
    }

    public Vector2(Vector2 other) {
        this.x = other.x;
        this.y = other.y;
    }

    /**
     * Add to x and y values.
     *
     * @param x double that will be added to this.x
     * @param y double that will be added to this.y
     */
    public void add(double x, double y) {
        this.x += x;
        this.y += y;
    }

    /**
     * Add to x and y values.
     *
     * @param other Vector2 that will be added
     */
    public void add(Vector2 other) {
        x += other.x;
        y += other.y;
    }

    /**
     * Subtract from x and y values.
     *
     * @param x double that will be subtracted from this.x
     * @param y double that will be subtracted from this.y
     */
    public void subtract(double x, double y) {
        this.x -= x;
        this.y -= y;
    }

    /**
     * Subtract from x and y values.
     *
     * @param other Vector2 that will be subtracted
     */
    public void subtract(Vector2 other) {
        x -= other.x;
        y -= other.y;
    }

    /**
     * Divide x and y values.
     *
     * @param divisor double
     */
    public void divide(double divisor) {
        x /= divisor;
        y /= divisor;
    }

    /**
     * Multiply x and y values.
     *
     * @param multiplier double
     */
    public void multiply(double multiplier) {
        x *= multiplier;
        y *= multiplier;
    }

    /**
     * Reverse x and y values.
     */
    public void reverse() {
        if (x < 0) {
            x = +x;
        } else {
            x = -x;
        }

        if (y < 0) {
            y = +y;
        } else {
            y = -y;
        }
    }

    /**
     * Set new x and y values for the vector.
     *
     * @param x double with new x-value
     * @param y double with new y-value
     */
    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Set new x and y values for the vector.
     *
     * @param other Vector2 with the new values
     */
    public void set(Vector2 other) {
        x = other.x;
        y = other.y;
    }
}
