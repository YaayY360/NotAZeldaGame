import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class RenderEngine extends JPanel implements Engine{
    private ArrayList<Displayable> renderList;

    public RenderEngine(JFrame jFrame) {
        renderList = new ArrayList<>();
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

        switch (Main.currentState){
            case TITLE_SCREEN:
                g.setColor(Color.BLACK);
                g.fillRect(0,0, getWidth(), getHeight());
                g.setColor(Color.WHITE);
                g.drawString("MON SUPER DONJON",130,200);
                break;

            case PLAYING:
                for (Displayable renderObject:renderList) {
                    renderObject.draw(g);
                }
                break;

            case GAME_OVER:
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setColor(Color.RED);
                g.drawString("GAME OVER", 160, 200);
                break;
        }
    }

    @Override
    public void update(){
        this.repaint();
    }

}






