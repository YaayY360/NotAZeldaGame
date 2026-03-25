public class InvincibilityManager {
    private long invincibilityDuration; // en millisecondes
    private long lastDamageTime = 0;

    public InvincibilityManager(long invincibilityDuration) {
        this.invincibilityDuration = invincibilityDuration;
    }

    public void trigger() {
        lastDamageTime = System.currentTimeMillis();
    }

    public boolean isInvincible() {
        return System.currentTimeMillis() - lastDamageTime < invincibilityDuration;
    }
}