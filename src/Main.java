import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
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


        displayZoneFrame = new JFrame("Java Labs");
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
        layeredPane.add(renderEngine, JLayeredPane.DEFAULT_LAYER);  // couche basse

        JPanel uiLayer = new JPanel(null);  // null layout pour setBounds
        uiLayer.setOpaque(false);           // transparent !
        uiLayer.setBounds(0, 0, 400, 600);
        layeredPane.add(uiLayer, JLayeredPane.PALETTE_LAYER);  // couche haute

        JButton startButton = new JButton("Démarrer le jeu");
        startButton.setBounds(120, 300, 160, 40);
        uiLayer.add(startButton);  // bouton dans la couche UI, pas dans renderEngine

        displayZoneFrame.add(layeredPane);
        displayZoneFrame.setVisible(true);

        renderEngine.setLayout(null);
        JButton startButton = new JButton("Démarrer le jeu");
        startButton.setBounds(120, 300, 150, 40); // Position (x, y) et taille (largeur, hauteur)
        startButton.setBackground(Color.BLUE);
        startButton.setOpaque(true);



        startButton.addActionListener(e -> {
            Main.currentState = GameState.PLAYING; // On change l'état
            startButton.setVisible(false);         // On cache le bouton
            displayZoneFrame.requestFocus();       // TRÈS IMPORTANT : on redonne le focus à la fenêtre pour que le clavier remarche
        });

        renderEngine.add(startButton);
        renderEngine.setVisible(true);

        Playground level = new Playground("./data/level1.txt");
        //SolidSprite testSprite = new DynamicSprite(100,100,test,0,0);
        renderEngine.addToRenderList(level.getSpriteList());
        renderEngine.addToRenderList(hero);
        physicEngine.addToMovingSpriteList(hero);
        physicEngine.setEnvironment(level.getSolidSpriteList());

        displayZoneFrame.addKeyListener(gameEngine);
    }

    public static void main (String[] args) throws Exception {
        // write your code here
        Main main = new Main();
    }

}
