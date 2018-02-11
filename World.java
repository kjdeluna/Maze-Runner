import javax.swing.JPanel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import java.util.Random;
public class World extends JPanel implements KeyListener{
    public static int ROWS;
    public static int COLS;
    public final static String WALL = "W";
    public final static String VISITED_TILE = "v";
    public final static String GOAL_TILE = "G";
    public final static String UNVISITED_TILE = "t";
    public final static String PLAYER = "P"; 
    private String[][] worldArray;
    private Player player;
    private boolean solved;
    private int goalRow;
    private int goalCol;
    public World(int n){
        this.ROWS = n;
        this.COLS = n;
        this.solved = false;
        this.worldArray = new String[ROWS][COLS];
        this.generateWorldArray();
        // this.readFile("map.in");
        this.setLayout(null);
        this.setBounds(0,0, Main.FRAME_WIDTH, Main.FRAME_HEIGHT-50);
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
        this.setLayout(null);
    }
    
    public void generateWorldArray(){
        boolean playerGenerated = false;
        boolean goalGenerated = false;
        String[] choices = new String[]{
            "P",
            "t",
            "W",
            "G"
        };
        Random rand = new Random();
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                if(i == ROWS - 1 && j == COLS-2){
                    if(!playerGenerated){
                        playerGenerated = true;
                        this.player = new Player(i,j);
                        this.worldArray[i][j] = PLAYER;
                        continue;
                    }
                }
                else if(i == ROWS-1 && j == COLS-1){
                    if(!goalGenerated){
                        this.goalRow = i;
                        this.goalCol = j;
                        goalGenerated = true;
                        this.worldArray[i][j] = GOAL_TILE;
                        continue;
                    }
                }
                int randNum = rand.nextInt(choices.length);
                if(choices[randNum] == PLAYER){
                    if(!playerGenerated){
                        playerGenerated = true;
                        this.player = new Player(i,j);
                        this.worldArray[i][j] = PLAYER;
                    }
                    else{
                        j--;
                        continue;
                    }
                }
                else if(choices[randNum] == GOAL_TILE){
                    if(!goalGenerated){
                        this.goalRow = i;
                        this.goalCol = j;
                        goalGenerated = true;
                        this.worldArray[i][j] = GOAL_TILE;
                    }
                    else{
                        j--;
                        continue;
                    }
                }
                else{
                    this.worldArray[i][j] = choices[randNum];
                }
            }
        }
        this.repaint();
    }

    public String up(Player player){
        return this.worldArray[player.getCurrRow()-1][player.getCurrCol()];
    }

    public String down(Player player){
        return this.worldArray[player.getCurrRow()+1][player.getCurrCol()];
    }
    public String left(Player player){
        return this.worldArray[player.getCurrRow()][player.getCurrCol()-1];
    }
    public String right(Player player){
        return this.worldArray[player.getCurrRow()][player.getCurrCol()+1];
    }

    public void keyPressed(KeyEvent e){
        switch(e.getKeyCode()){
            case KeyEvent.VK_UP: 
                invokeAction(Directions.UP);
                break;
            case KeyEvent.VK_DOWN:
                invokeAction(Directions.DOWN);
                break;
            case KeyEvent.VK_LEFT:
                invokeAction(Directions.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                invokeAction(Directions.RIGHT);
                break;
        }
    }
    public void keyTyped(KeyEvent e){}
    public void keyReleased(KeyEvent e){}

    private void invokeAction(Directions dir){
        if(solved) return;
        String nextObject = "";
        int currPlayerRow = this.player.getCurrRow();
        int currPlayerCol = this.player.getCurrCol();
        int nextPlayerRow = currPlayerRow;
        int nextPlayerCol = currPlayerCol;
        Directions headed = null;
        boolean outOfBounds = false;
        switch(dir){
            case UP:    
                this.switchPlayerTexture(Directions.UP);
                nextPlayerRow -= 1;
                outOfBounds = nextPlayerRow < 0 ? true : false;
                if(outOfBounds) return; 
                nextObject = up(this.player);
                headed = Directions.UP;
                break;
            case DOWN: 
                this.switchPlayerTexture(Directions.DOWN);
                nextPlayerRow += 1;
                outOfBounds = nextPlayerRow > ROWS - 1 ? true : false;
                System.out.println(outOfBounds);
                if(outOfBounds) return;
                nextObject = down(this.player);
                headed = Directions.DOWN;
                break;
            case LEFT:  
                this.switchPlayerTexture(Directions.LEFT);
                nextPlayerCol -= 1;
                outOfBounds = nextPlayerCol < 0 ? true : false;
                if(outOfBounds) return;
                nextObject = left(this.player);
                headed = Directions.LEFT;
                break;
            case RIGHT:
                this.switchPlayerTexture(Directions.RIGHT);
                nextPlayerCol += 1;
                outOfBounds = nextPlayerCol > COLS - 1 ? true : false;
                if(outOfBounds) return;
                nextObject = right(this.player);
                headed = Directions.RIGHT;
                break;
        }

        if( nextObject.equals(VISITED_TILE)
            || nextObject.equals(UNVISITED_TILE) )
        {
            this.worldArray[currPlayerRow][currPlayerCol] = this.player.getPrevValue();
            this.player.setPrevValue(VISITED_TILE);
            this.worldArray[nextPlayerRow][nextPlayerCol] = PLAYER;
            this.movePlayer(headed);
        }
        // Winning condition
        else if( nextObject.equals(GOAL_TILE) ){
            this.worldArray[currPlayerRow][currPlayerCol] = this.player.getPrevValue();
            this.player.setPrevValue(GOAL_TILE);
            this.worldArray[nextPlayerRow][nextPlayerCol] = PLAYER;
            this.movePlayer(headed);
            this.solved = true;
        }
        this.repaint();
        if(solved) JOptionPane.showMessageDialog(null, "You win");
    }

    private void movePlayer(Directions dir){
        switch(dir){
            case UP: 
                this.player.moveUp();
                break;
            case DOWN:
                this.player.moveDown();
                break;
            case LEFT:
                this.player.moveLeft();
                break;
            case RIGHT:
                this.player.moveRight();
        }
    }
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
                    if(this.worldArray[i][j].equals(PLAYER)) this.player = new Player(i, j);
                    else if(this.worldArray[i][j].equals(GOAL_TILE)){
                        this.goalRow = i;
                        this.goalCol = j;
                    }
                }
                i += 1;
            }
            this.repaint();
        } catch (Exception e){
            e. printStackTrace();
        }
    }

    public int getGoalRow(){
        return this.goalRow;
    }

    public int getGoalCol(){
        return this.goalCol;
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

    public String[][] getWorldArray(){
        return this.worldArray;
    }

    public Player getPlayer(){
        return this.player;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // System.out.println("");
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                // System.out.print(this.worldArray[i][j] + " ");
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
                        // Main.textureLoader.getTexture(Texture.DEFAULT_PATH, "grass.png").render(g2d, j * Main.TILE_SIZE, i * Main.TILE_SIZE);
                        switch(this.player.getPrevValue()){
                            case VISITED_TILE:
                                Main.textureLoader.getTexture(Texture.DEFAULT_PATH, "grass.png").render(g2d, j * Main.TILE_SIZE, i * Main.TILE_SIZE);
                                break;
                            case GOAL_TILE:
                                Main.textureLoader.getTexture(Texture.DEFAULT_PATH, "water.png").render(g2d, j * Main.TILE_SIZE, i * Main.TILE_SIZE);
                                break;
                        }
                        this.player.getTexture().render(g2d, j * Main.TILE_SIZE, i * Main.TILE_SIZE);
                        break;
                    case GOAL_TILE:
                        Main.textureLoader.getTexture(Texture.DEFAULT_PATH, "water.png").render(g2d, j * Main.TILE_SIZE, i * Main.TILE_SIZE);
                        break;

                }
            }
                // System.out.println("");
        }
    }
}