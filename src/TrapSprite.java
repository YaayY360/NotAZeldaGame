import java.awt.*;

public class TrapSprite extends SolidSprite {

    private int damage;

    public TrapSprite(double x, double y, Image image, double width, double height, int damage) {
        super(x, y, image, width, height);
        this.damage = damage;
    }

    public void applyDamageIfColliding(Damageable target) {
        if (target instanceof SolidSprite targetSprite) {
            if (this.getHitBox().intersects(targetSprite.getHitBox())) {
                target.takeDamage(damage);
            }
        }
    }
}