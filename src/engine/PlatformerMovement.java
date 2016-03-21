package engine;

import game.Game;

/**
 * This class implements basic platformer or side scroller movement.
 */
public class PlatformerMovement {
    private boolean movingLeft, movingRight, jumping, falling, facingRight;
    private double acceleration, deceleration, maxSpeed, jumpStart, maxFallingSpeed, decelerateJumpSpeed;

    public PlatformerMovement(double acceleration, double deceleration, double maxSpeed, double jumpStart,
                              double maxFallingSpeed, double decelerateJumpSpeed) {
        this.acceleration = acceleration;
        this.deceleration = deceleration;
        this.maxSpeed = maxSpeed;
        this.jumpStart = jumpStart;
        this.maxFallingSpeed = maxFallingSpeed;
        this.decelerateJumpSpeed = decelerateJumpSpeed;
        falling = true;
        facingRight = true;
    }

    /**
     * Update movement.
     *
     * @param delta double
     * @param velocity Vector2
     */
    public void update(double delta, Vector2 velocity) {
        updateWalking(delta, velocity);
        updateFalling(delta, velocity);
        updateJumping(delta, velocity);
        setDirection(velocity);
    }

    /**
     * Update walking by adding acceleration and subtracting deceleration to Vector2 velocity.
     *
     * @param delta double
     * @param velocity Vector2
     */
    private void updateWalking(double delta, Vector2 velocity) {
        if (movingLeft) {
            velocity.x -= acceleration * delta;
            if (velocity.x < -maxSpeed) {
                velocity.x = -maxSpeed;
            }
        } else if (movingRight) {
            velocity.x += acceleration * delta;
            if (velocity.x > maxSpeed) {
                velocity.x = maxSpeed;
            }
        } else {
            if (velocity.x > 0) {
                velocity.x -= deceleration * delta;
                if (velocity.x < 0) {
                    velocity.x = 0;
                }
            } else if (velocity.x < 0) {
                velocity.x += deceleration * delta;
                if (velocity.x > 0) {
                    velocity.x = 0;
                }
            }
        }
    }

    /**
     * Update jumping.
     *
     * @param delta double
     * @param velocity Vector2
     */
    private void updateJumping(double delta, Vector2 velocity) {
        if (jumping && !falling) {
            velocity.y = jumpStart * delta;
            falling = true;
        }
    }

    /**
     * Update falling by applying gravity.
     *
     * @param delta double
     * @param velocity Vector2
     */
    private void updateFalling(double delta, Vector2 velocity) {
        if (falling) {
            velocity.y += Game.physicsManager.getGravity() * delta;
            if (velocity.y > maxFallingSpeed) {
                velocity.y = maxFallingSpeed;
            }
            if (velocity.y > 0) {   // Going down
                jumping = false;
            }
            if (velocity.y < 0 && !jumping) {   // Going up but jump button released
                velocity.y += decelerateJumpSpeed * delta;
            }
        }
    }

    /**
     * Set the velocity in which the GameObject should be facing.
     *
     * @param velocity Vector2
     */
    private void setDirection(Vector2 velocity) {
        if (velocity.x < 0) {
            facingRight = false;
        }
        if (velocity.x > 0) {
            facingRight = true;
        }
    }


    // Getter methods

    public boolean isMovingLeft() {
        return movingLeft;
    }

    public boolean isMovingRight() {
        return movingRight;
    }

    public boolean isJumping() {
        return jumping;
    }

    public boolean isFacingRight() {
        return facingRight;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public double getDeceleration() {
        return deceleration;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public double getJumpStart() {
        return jumpStart;
    }

    public double getMaxFallingSpeed() {
        return maxFallingSpeed;
    }

    public double getDecelerateJumpSpeed() {
        return decelerateJumpSpeed;
    }

    public boolean isFalling() {
        return falling;
    }

    // Setter methods

    public void setFacingRight(boolean facingRight) {
        this.facingRight = facingRight;
    }

    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }

    public void setDeceleration(double deceleration) {
        this.deceleration = deceleration;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setJumpStart(double jumpStart) {
        this.jumpStart = jumpStart;
    }

    public void setMaxFallingSpeed(double maxFallingSpeed) {
        this.maxFallingSpeed = maxFallingSpeed;
    }

    public void setDecelerateJumpSpeed(double decelerateJumpSpeed) {
        this.decelerateJumpSpeed = decelerateJumpSpeed;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }
}
