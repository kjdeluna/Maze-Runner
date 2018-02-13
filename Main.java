import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.util.Scanner;

public class Main {
    
    private final static String TITLE = "Maze Runner";
    public static int FRAME_WIDTH;
    public static int FRAME_HEIGHT;
    public final static int TILE_SIZE = 32;
    private static String[] textures;
    public static TextureLoader textureLoader;
    public static JFrame gameFrame;
    public static void main(String[] args){
        Main.textures = new String[]{ 
            "grass.png",
            "rose.png",
            "wall.png",
            "water.png",
            "player_up.png",
            "player_down.png",
            "player_left.png",
            "player_right.png",
            "player_win.png"
        };
        Main.textureLoader = new TextureLoader(Texture.DEFAULT_PATH, Main.textures);
        gameFrame = new JFrame(Main.TITLE);
        Main.createFrame();
        gameFrame.setLocationRelativeTo(null);
    }

    public static void createFrame(){
        try{
            String stringedInput = JOptionPane.showInputDialog("Please input N: ");
            int n = Integer.parseInt(stringedInput);
            gameFrame.getContentPane().removeAll();
            FRAME_HEIGHT = n * TILE_SIZE + 30;
            FRAME_WIDTH = n * TILE_SIZE;
            gameFrame.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
            gameFrame.setResizable(false);
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setLayout(null);
            World world = new World(n);
            OptionsPanel optionsPanel = new OptionsPanel(world);
            world.setBounds(0,0, FRAME_WIDTH, FRAME_HEIGHT-30);
            optionsPanel.setBounds(0, FRAME_HEIGHT-30, Main.FRAME_WIDTH, 30);
            gameFrame.add(world);
            gameFrame.add(optionsPanel);
            // Pack components to JFrame
            // Center the frame
            gameFrame.pack();
            gameFrame.revalidate();
            gameFrame.repaint();
            // gameFrame.setLocationRelativeTo(null); // Center gameFrame to the screen
            gameFrame.setVisible(true);
        } catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Invalid input!");
        }

    }
}