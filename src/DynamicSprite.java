import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class DynamicSprite extends SolidSprite implements Damageable{
    private Direction direction = Direction.EAST;
    private double speed = 5;
    private double timeBetweenFrame = 250;
    private boolean isWalking =true;
    private final int spriteSheetNumberOfColumn = 10;

    private int halfHearts = 6;      // 3 cœurs = 6 demi-cœurs
    private final int maxHalfHearts = 6;
    private final InvincibilityManager invincibilityManager = new InvincibilityManager(2000); // ← 2 secondes

    public int getHalfHearts() { return halfHearts; }
    public int getMaxHalfHearts() { return maxHalfHearts; }

    public void takeDamage() {
        halfHearts = Math.max(0, halfHearts - 1); // 1 dégât = 1 demi-cœur
        if (halfHearts == 0) Main.currentState = GameState.GAME_OVER;
    }

    // ← takeDamage prend maintenant un int et vérifie l'invincibilité
    @Override
    public void takeDamage(int amount) {
        if (!isInvincible()) {
            halfHearts = Math.max(0, halfHearts - amount);
            invincibilityManager.trigger();  // ← déclenche l'invincibilité
            if (halfHearts == 0) Main.currentState = GameState.GAME_OVER;
        }
    }

    // ← nouvelle méthode requise par Damageable
    @Override
    public boolean isInvincible() {
        return invincibilityManager.isInvincible();
    }

    public DynamicSprite(double x, double y, Image image, double width, double height) {
        super(x, y, image, width, height);
    }

    private boolean isMovingPossible(ArrayList<Sprite> environment){
        Rectangle2D.Double moved = new Rectangle2D.Double();
        switch(direction){
            case EAST: moved.setRect(super.getHitBox().getX()+speed,super.getHitBox().getY(),
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case WEST:  moved.setRect(super.getHitBox().getX()-speed,super.getHitBox().getY(),
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case NORTH:  moved.setRect(super.getHitBox().getX(),super.getHitBox().getY()-speed,
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case SOUTH:  moved.setRect(super.getHitBox().getX(),super.getHitBox().getY()+speed,
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
        }

        for (Sprite s : environment){
            if ((s instanceof SolidSprite) && (s!=this)){
                if (((SolidSprite) s).intersect(moved)){
                    return false;
                }
            }
        }
        return true;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    private void move(){
        switch (direction){
            case NORTH -> {
                this.y-=speed;
            }
            case SOUTH -> {
                this.y+=speed;
            }
            case EAST -> {
                this.x+=speed;
            }
            case WEST -> {
                this.x-=speed;
            }
        }
    }

    public void moveIfPossible(ArrayList<Sprite> environment){
        if (isMovingPossible(environment)){
            move();
        }
    }

    @Override
    public void draw(Graphics g) {
        // Clignote toutes les 100ms pendant l'invincibilité
        if (isInvincible() && (System.currentTimeMillis() / 100) % 2 == 0) return;

        int index = (int) (System.currentTimeMillis() / timeBetweenFrame % spriteSheetNumberOfColumn);
        g.drawImage(image, (int) x, (int) y, (int) (x + width), (int) (y + height),
                (int) (index * this.width), (int) (direction.getFrameLineNumber() * height),
                (int) ((index + 1) * this.width), (int) ((direction.getFrameLineNumber() + 1) * this.height), null);
    }
}

