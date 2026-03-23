import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Main {

    JFrame displayZoneFrame;
    RenderEngine renderEngine;
    GameEngine gameEngine;
    PhysicEngine physicEngine;

    public static GameState currentState = GameState.TITLE_SCREEN;

    public Main() throws Exception {

        displayZoneFrame = new JFrame("The Legends Of JAVA");
        displayZoneFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        displayZoneFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        displayZoneFrame.getContentPane().setBackground(Color.BLACK);
        displayZoneFrame.getContentPane().setLayout(new BorderLayout());

        DynamicSprite hero = new DynamicSprite(200, 300,
                ImageIO.read(new File("./img/heroTileSheetLowRes.png")), 48, 50);

        renderEngine = new RenderEngine(displayZoneFrame);
        physicEngine = new PhysicEngine();
        gameEngine = new GameEngine(hero);

        Timer renderTimer = new Timer(50, (time) -> renderEngine.update());
        Timer gameTimer = new Timer(50, (time) -> gameEngine.update());
        Timer physicTimer = new Timer(50, (time) -> physicEngine.update());

        renderTimer.start();
        gameTimer.start();
        physicTimer.start();

        // RenderEngine remplit toute la fenêtre
        displayZoneFrame.getContentPane().add(renderEngine, BorderLayout.CENTER);

        // Bouton positionné directement dans le renderEngine
        renderEngine.setLayout(null);
        ImageIcon buttonIcon      = new ImageIcon("./img/btn_start.png");
        ImageIcon buttonIconHover = new ImageIcon("./img/btn_start_hover.png");

        JLabel startButton = new JLabel(buttonIcon);
        startButton.setBounds(1300, 750, buttonIcon.getIconWidth(), buttonIcon.getIconHeight());
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
                startButton.setIcon(buttonIconHover);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                startButton.setIcon(buttonIcon);
            }
        });

        renderEngine.add(startButton);

        displayZoneFrame.setVisible(true); // toujours en dernier

        Playground level = new Playground("./data/level1.txt");
        renderEngine.addToRenderList(level.getSpriteList());
        renderEngine.addToRenderList(hero);
        physicEngine.addToMovingSpriteList(hero);
        physicEngine.setEnvironment(level.getSolidSpriteList());

        displayZoneFrame.addKeyListener(gameEngine);

        HeartsDisplay heartsDisplay = new HeartsDisplay(hero);
        renderEngine.addToRenderList(heartsDisplay);
    }

    public static void main(String[] args) throws Exception {
        Main main = new Main();
    }
}