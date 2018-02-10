import javax.swing.JPanel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
public class World extends JPanel implements KeyListener{
    public static int ROWS;
    public static int COLS;
    private final static String WALL = "W";
    private final static String VISITED_TILE = "v";
    private final static String GOAL_TILE = "G";
    private final static String UNVISITED_TILE = "t";
    private final static String PLAYER = "P"; 
    private String[][] worldArray;
    private Player player;
    public World(int rows, int cols){
        this.ROWS = rows;
        this.COLS = cols;
        this.worldArray = new String[rows][cols];
        this.readFile("map.in");
        this.setLayout(null);
        this.setBounds(0,0, Main.FRAME_WIDTH, Main.FRAME_HEIGHT);
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
        this.setLayout(null);
    }
    
    public void keyPressed(KeyEvent e){}
    public void keyTyped(KeyEvent e){}
    public void keyReleased(KeyEvent e){}
    public void readFile(String filename){
        try{
            int i = 0;
            String[] lineRead;
            String line;
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            while((line = br.readLine()) != null){
                lineRead = line.split(" ");
                for(int j = 0; j < COLS; j++){
                    this.worldArray[i][j] = lineRead[j];
                    if(this.worldArray[i][j].equals(World.PLAYER)) this.player = new Player(i, j);
                }
                i += 1;
            }
            this.repaint();
        } catch (Exception e){
            e. printStackTrace();
        }
    }

    private void switchPlayerTexture(Directions dir){
        switch(dir){
            case UP:
                this.player.setTexture(Main.textureLoader.getTexture(Texture.DEFAULT_PATH, "player_up.png"));
                break;
            case DOWN:
                this.player.setTexture(Main.textureLoader.getTexture(Texture.DEFAULT_PATH, "player_down.png"));
                break;
            case LEFT:
                this.player.setTexture(Main.textureLoader.getTexture(Texture.DEFAULT_PATH, "player_left.png"));
                break;
            case RIGHT:
                this.player.setTexture(Main.textureLoader.getTexture(Texture.DEFAULT_PATH, "player_right.png"));
                break;
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for(int i = 0; i < World.ROWS; i++){
            for(int j = 0; j < World.COLS; j++){
                switch(this.worldArray[i][j]){
                    case WALL: 
                        Main.textureLoader.getTexture(Texture.DEFAULT_PATH, "wall.png").render(g2d, j * Main.TILE_SIZE, i * Main.TILE_SIZE);
                        break;
                    case VISITED_TILE:
                        Main.textureLoader.getTexture(Texture.DEFAULT_PATH, "grass.png").render(g2d, j * Main.TILE_SIZE, i * Main.TILE_SIZE);
                        break;
                    case UNVISITED_TILE:
                        Main.textureLoader.getTexture(Texture.DEFAULT_PATH, "grass.png").render(g2d, j * Main.TILE_SIZE, i * Main.TILE_SIZE);
                        break;
                    case PLAYER:
                        Main.textureLoader.getTexture(Texture.DEFAULT_PATH, "grass.png").render(g2d, j * Main.TILE_SIZE, i * Main.TILE_SIZE);
                        this.player.getTexture().render(g2d, j * Main.TILE_SIZE, i * Main.TILE_SIZE);
                        break;
                    case GOAL_TILE:
                        Main.textureLoader.getTexture(Texture.DEFAULT_PATH, "water.png").render(g2d, j * Main.TILE_SIZE, i * Main.TILE_SIZE);
                        break;

                }
            }
        }
    }
}