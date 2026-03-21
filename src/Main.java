import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Main {

    JFrame displayZoneFrame;

    RenderEngine renderEngine;
    GameEngine gameEngine;
    PhysicEngine physicEngine;

    public static GameState currentState = GameState.TITLE_SCREEN;

    public Main() throws Exception{

        displayZoneFrame = new JFrame("The Legends Of JAVA");
        displayZoneFrame.setSize(400,600);
        displayZoneFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        DynamicSprite hero = new DynamicSprite(200,300,
                ImageIO.read(new File("./img/heroTileSheetLowRes.png")),48,50);

        renderEngine = new RenderEngine(displayZoneFrame);
        physicEngine = new PhysicEngine();
        gameEngine = new GameEngine(hero);

        Timer renderTimer = new Timer(50,(time)-> renderEngine.update());
        Timer gameTimer = new Timer(50,(time)-> gameEngine.update());
        Timer physicTimer = new Timer(50,(time)-> physicEngine.update());

        renderTimer.start();
        gameTimer.start();
        physicTimer.start();

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(400, 600));

        renderEngine.setBounds(0, 0, 400, 600);
        layeredPane.add(renderEngine, JLayeredPane.DEFAULT_LAYER);

        JPanel uiLayer = new JPanel(null);
        uiLayer.setOpaque(false);
        uiLayer.setBounds(0, 0, 400, 600);
        layeredPane.add(uiLayer, JLayeredPane.PALETTE_LAYER);

        // ↓↓↓ BOUTON IMAGE ↓↓↓
        ImageIcon buttonIcon      = new ImageIcon("./img/btn_start.png");
        ImageIcon buttonIconHover = new ImageIcon("./img/btn_start_hover.png"); // optionnel

        JLabel startButton = new JLabel(buttonIcon);
        startButton.setBounds(10, 400, buttonIcon.getIconWidth(), buttonIcon.getIconHeight());
        startButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Main.currentState = GameState.PLAYING;
                startButton.setVisible(false);
                displayZoneFrame.requestFocus();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                startButton.setIcon(buttonIconHover); // effet survol
            }
            @Override
            public void mouseExited(MouseEvent e) {
                startButton.setIcon(buttonIcon);
            }
        });

        uiLayer.add(startButton);
        // ↑↑↑ FIN BOUTON IMAGE ↑↑↑

        displayZoneFrame.add(layeredPane);
        displayZoneFrame.setVisible(true); // toujours en dernier

        Playground level = new Playground("./data/level1.txt");
        renderEngine.addToRenderList(level.getSpriteList());
        renderEngine.addToRenderList(hero);
        physicEngine.addToMovingSpriteList(hero);
        physicEngine.setEnvironment(level.getSolidSpriteList());

        displayZoneFrame.addKeyListener(gameEngine);
    }

    public static void main (String[] args) throws Exception {
        Main main = new Main();
    }
}