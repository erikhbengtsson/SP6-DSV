package engine.subsystems;

import engine.GameObject;
import engine.Vector2;
import javafx.scene.shape.Circle;

import java.awt.*;

/**
 * Singleton class for handling collisions and forces like gravity.
 */
public class PhysicsManager {
    private static PhysicsManager physicsManager = new PhysicsManager();
    private double gravity;

    private PhysicsManager() {
        gravity = 0;    // Default gravity
    }

    public static PhysicsManager getInstance() {
        return physicsManager;
    }

    /**
     * Checks collision between the surrounding rectangles of two game objects.
     *
     * @param gameObject1 GameObject
     * @param gameObject2 GameObject
     * @return true if the GameObjects collide
     */
    public static boolean rectangleRectangleCollision(GameObject gameObject1, GameObject gameObject2) {
        return gameObject1.getRectangle().intersects(gameObject2.getRectangle());
    }

    /**
     * Checks collision between the surrounding circles of two game objects.
     *
     * @param gameObject1 GameObject
     * @param gameObject2 GameObject
     * @return true if the GameObjects collide
     */
    public static boolean circleCircleCollision(GameObject gameObject1, GameObject gameObject2) {
        Circle c1 = gameObject1.getCircle();
        Circle c2 = gameObject2.getCircle();

        return(c1.getRadius() + c2.getRadius() >=
                pointDistance(c1.getCenterX(), c1.getCenterY(), c2.getCenterX(), c2.getCenterY()));
    }

    /**
     * Helper method for calculating the distance between the center of two circles.
     *
     * @param x1 double
     * @param y1 double
     * @param x2 double
     * @param y2 double
     * @return double with the distance between two points
     */
    private static double pointDistance(double x1, double y1, double x2, double y2) {
        double differenceX = Math.abs(x1 - x2);
        double differenceY = Math.abs(y1 - y2);

        return Math.sqrt(Math.pow(differenceX, 2) + (Math.pow(differenceY, 2)));
    }

    /**
     * Checks collision between a GameObjects surrounding rectangle and a Vector2 point.
     *
     * @param gameObject GameObject
     * @param point Vector2
     * @return true if gameObject and point is colliding
     */
    public static boolean rectanglePointCollision(GameObject gameObject, Vector2 point) {
        Rectangle rectangle = gameObject.getRectangle();

        return(point.x >= rectangle.x && point.x < rectangle.x + rectangle.width
                && point.y >= rectangle.y && point.y < rectangle.y + rectangle.height);
    }

    /**
     * Check collision between two points.
     *
     * @param point1 Vector2
     * @param point2 Vector2
     * @param tolerance int representing the distance between the points that should be considered a collision
     * @return true if distance between point1 and point2 is less the tolerance
     */
    public static boolean pointPointCollision(Vector2 point1, Vector2 point2, int tolerance) {
        return(Math.abs(point1.x - point2.x) <= tolerance && Math.abs(point1.y - point2.y) <= tolerance);
    }

    /**
     * Check collision between a circle and a rectangle.
     *
     * @param rectangle GameObject
     * @param circle GameObject
     * @return true if gameObject1 and gameObject2 intersects
     */
    public static boolean circleRectangleCollision(GameObject rectangle, GameObject circle) {
        Rectangle r = rectangle.getRectangle();
        Circle c = circle.getCircle();
        Vector2 distance = new Vector2();

        // Get the absolute distance between the center of the two game objects
        distance.x = Math.abs(c.getCenterX() - rectangle.getCenter().x);
        distance.y = Math.abs(c.getCenterY() - rectangle.getCenter().y);

        // If distance is greater then circle radius plus half of rectangles width or height, return false
        if (distance.x > (r.width / 2 + c.getRadius())) { return false; }
        if (distance.y > (r.height / 2 + c.getRadius())) { return false; }

        // If Vector2 distance is inside the rectangle, return true
        if (distance.x <= (r.width/2)) { return true; }
        if (distance.y <= (r.height/2)) { return true; }

        double distanceSquared = (Math.pow(distance.x - r.width / 2, 2) +
                Math.pow(distance.y - r.height / 2, 2));

        return (distanceSquared <= Math.pow(c.getRadius(), 2));
    }

    public double getGravity() {
        return gravity;
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }
}
