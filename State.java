import java.util.LinkedList;


/* -------------------------------------------------------------------------------------------
 *
 *  This class will be used to manage the State for each movement performed 
 *      Getters:
 *          getWorldArray() -> returns the 2D array (String[][])
 *          getPath() -> returns the current path taken by the player (LinkedList<Directions>)
 *          getPlayer() -> returns the player of that current state (Player)
 *      Other methods:
 *          printPaths() -> print the path taken by the player (void)
 *          addPath() -> add a direction to the current path taken by player (void)
 *
 * -------------------------------------------------------------------------------------------*/

public class State{
    private String[][] worldArray = new String[World.ROWS][World.COLS];
    private Player player;
    private int goalRow;
    private int goalCol;
    private LinkedList<Directions> path;
    private int g; // path cost
    private boolean win;
    public State(String[][] worldArray, Player player, LinkedList<Directions> path, boolean win, int goalRow, int goalCol){
        this.worldArray = worldArray;
        this.player = player;           // contains the rows and cols of the player 
        this.path = path;
        this.win = win;
        this.goalRow = goalRow;
        this.goalCol = goalCol;
    }
    public boolean getWin(){
        return this.win;
    }
    public void gameWon(boolean val){
        this.win = val;
    }
    public String[][] getWorldArray(){
        return this.worldArray;
    }
    
    public LinkedList<Directions> getPath(){
        return this.path;
    }
    
    public Player getPlayer(){
        return this.player;
    }
    
    public void printPaths(){
        System.out.println("Printing result:");
        for(Directions direction : path){
            System.out.println(direction);
        }
    }

    public void addPath(Directions direction){
        this.path.add(direction);
    }

    public int PathCost(){
        return this.path.size();
    }
}