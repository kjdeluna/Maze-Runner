import java.awt.Dimension;
import javax.swing.JFrame;
import java.util.Scanner;
public class Main {
    
    private final static String TITLE = "Maze Runner";
    public final static int FRAME_WIDTH = 320;
    public final static int FRAME_HEIGHT = 320;
    public final static int TILE_SIZE = 32;
    private static String[] textures;
    public static TextureLoader textureLoader;

    public static void main(String[] args){
        Main.textures = new String[]{ 
            "grass.png",
            "wall.png",
            "water.png",
            "player_up.png",
            "player_down.png",
            "player_left.png",
            "player_right.png",
            "player_win.png"
        };
        Main.textureLoader = new TextureLoader(Texture.DEFAULT_PATH, Main.textures);
        
        
        // ------- Initialize JFrame ---------
        JFrame gameFrame = new JFrame(Main.TITLE);
        gameFrame.setPreferredSize(new Dimension(Main.FRAME_WIDTH, Main.FRAME_HEIGHT));
        gameFrame.setResizable(false);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // --- JFrame initialize end -----------

        World world = new World(10, 10);
        gameFrame.add(world);
        // Pack components to JFrame
        gameFrame.pack();
        
        // Center the frame
        gameFrame.setLocationRelativeTo(null); // Center gameFrame to the screen
        
        gameFrame.setVisible(true);
    }
}