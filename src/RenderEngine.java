import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class RenderEngine extends JPanel implements Engine{
    private ArrayList<Displayable> renderList;
    private Image titleBackground;
    private int frameCount = 0;
    private long lastTime = System.currentTimeMillis();
    private int fps = 0;


    public RenderEngine(JFrame jFrame) {
        setBackground(Color.BLACK);  // ← fond noir au lieu de blanc
        setPreferredSize(jFrame.getSize());
        renderList = new ArrayList<>();
        try {
            titleBackground = ImageIO.read(new File("./img/title_background.png"));
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void addToRenderList(Displayable displayable){
        if (!renderList.contains(displayable)){
            renderList.add(displayable);
        }
    }

    public void addToRenderList(ArrayList<Displayable> displayable){
        if (!renderList.contains(displayable)){
            renderList.addAll(displayable);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        switch (Main.currentState) {
            case TITLE_SCREEN:
                // Fond image si chargée, sinon fond noir de secours
                if (titleBackground != null) {
                    g.drawImage(titleBackground, 0, 0, getWidth(), getHeight(), this);
                }
                else {
                    g.setColor(Color.BLACK);
                    g.fillRect(0, 0, getWidth(), getHeight());
                }

                // ← paintChildren OBLIGATOIRE pour que le bouton s'affiche
                paintChildren(g);
                break;

            case PLAYING:
                for (Displayable renderObject : renderList) {
                    renderObject.draw(g);
                }
                paintChildren(g);

                //calcul des FPS
                frameCount++;
                long now = System.currentTimeMillis();
                if (now - lastTime >= 1000) {
                    fps = frameCount;
                    frameCount = 0;
                    lastTime = now;
                }

                // Affichage FPS
                g.setColor(Color.YELLOW);
                g.setFont(new Font("Arial", Font.BOLD, 14));
                g.drawString("FPS : " + fps, 1780, 20);

                paintChildren(g);
                break;

            case GAME_OVER:
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setColor(Color.RED);
                g.setFont(new Font("Arial", Font.BOLD, 32));
                g.drawString("GAME OVER", 130, 200);
                paintChildren(g);
                break;
        }


    }

    @Override
    public void update(){
        this.repaint();
    }

}






