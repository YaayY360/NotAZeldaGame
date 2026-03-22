import java.awt.*;

public class HeartsDisplay implements Displayable {

    private DynamicSprite hero;

    public HeartsDisplay(DynamicSprite hero) {
        this.hero = hero;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int heartSize = 28;
        int spacing = 36;
        int startX = 10;
        int startY = 10; // position fixe en bas de l'écran
        int totalHearts = hero.getMaxHalfHearts() / 2;

        for (int i = 0; i < totalHearts; i++) {
            int x = startX + i * spacing;
            int filled = hero.getHalfHearts() - (i * 2);

            if (filled >= 2) {
                drawHeart(g2, x, startY, heartSize, Color.RED);
            } else if (filled == 1) {
                drawHeart(g2, x, startY, heartSize, Color.DARK_GRAY);
                drawHalfHeart(g2, x, startY, heartSize, Color.RED);
            } else {
                drawHeart(g2, x, startY, heartSize, Color.DARK_GRAY);
            }
        }
    }

    private void drawHeart(Graphics2D g2, int x, int y, int size, Color color) {
        g2.setColor(color);
        int half = size / 2;
        int quarter = size / 4;
        g2.fillOval(x, y, half, half);
        g2.fillOval(x + half, y, half, half);
        int[] xPoints = {x, x + size / 2, x + size};
        int[] yPoints = {y + quarter + 2, y + size, y + quarter + 2};
        g2.fillPolygon(xPoints, yPoints, 3);
        // Contour
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(1.2f));
        g2.drawOval(x, y, half, half);
        g2.drawOval(x + half, y, half, half);
        g2.drawPolygon(xPoints, yPoints, 3);
    }

    private void drawHalfHeart(Graphics2D g2, int x, int y, int size, Color color) {
        Shape oldClip = g2.getClip();
        g2.setClip(x, y, size / 2, size);
        drawHeart(g2, x, y, size, color);
        g2.setClip(oldClip);
    }
}