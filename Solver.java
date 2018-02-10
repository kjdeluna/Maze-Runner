import java.util.LinkedList;

public class Solver{
    private State initialState;
    public Solver(State initialState){
        this.initialState = initialState;
        LinkedList<Directions> store = this.Actions(initialState);
        System.out.println(store.size());
        for(Directions direction : store){
            System.out.println(direction);
        }
    }

    public String up(String[][] worldArray, Player player){
        return worldArray[player.getCurrRow()-1][player.getCurrCol()];
    }

    public String down(String[][] worldArray, Player player){
        return worldArray[player.getCurrRow()+1][player.getCurrCol()];
    }
    public String left(String[][] worldArray, Player player){
        return worldArray[player.getCurrRow()][player.getCurrCol()-1];
    }
    public String right(String[][] worldArray, Player player){
        return worldArray[player.getCurrRow()][player.getCurrCol()+1];
    }

    private LinkedList<Directions> Actions(State initialState){
        LinkedList<Directions> possibleActions = new LinkedList<Directions>();
        String[][] referenceToWorldArray = initialState.getWorldArray();
        int currRow = initialState.getPlayer().getCurrRow();
        int currCol = initialState.getPlayer().getCurrCol();
        // up
        boolean outOfBounds = currRow - 1 < 0 ? true : false;
        if( !outOfBounds && (referenceToWorldArray[currRow - 1][currCol].equals(World.UNVISITED_TILE) 
                || referenceToWorldArray[currRow - 1][currCol].equals(World.GOAL_TILE)) ){
            possibleActions.add(Directions.UP);
        }
        // down
        outOfBounds = currRow + 1 > World.ROWS - 1 ? true : false;
        if( !outOfBounds && (referenceToWorldArray[currRow + 1][currCol].equals(World.UNVISITED_TILE)
                || referenceToWorldArray[currRow + 1][currCol].equals(World.GOAL_TILE)) ){
            possibleActions.add(Directions.DOWN);
        }
        // left
        outOfBounds = currCol - 1 < 0 ? true : false;
        if( !outOfBounds && (referenceToWorldArray[currRow][currCol - 1].equals(World.UNVISITED_TILE) 
                || referenceToWorldArray[currRow][currCol - 1].equals(World.GOAL_TILE)) ){
            possibleActions.add(Directions.LEFT);
        }
        // right
        outOfBounds = currCol + 1 > World.COLS - 1 ? true : false;
        if( !outOfBounds && (referenceToWorldArray[currRow][currCol + 1].equals(World.UNVISITED_TILE) 
                || referenceToWorldArray[currRow][currCol + 1].equals(World.GOAL_TILE)) ){
            possibleActions.add(Directions.RIGHT);
        }
        return possibleActions;
    }

    private State Result(State currentState, Directions dir){
        Player currentStatePlayer = currentState.getPlayer();
        String[][] currentStateWorldArray = currentState.getWorldArray();
        String nextObject = "";
        int currPlayerRow = currentStatePlayer.getCurrRow();
        int currPlayerCol = currentStatePlayer.getCurrCol();
        int nextPlayerRow = currPlayerRow;
        int nextPlayerCol = currPlayerCol;
        switch(dir){
            case UP:    
                nextPlayerRow -= 1;
                nextObject = up(currentStateWorldArray, currentStatePlayer);
                break;
            case DOWN: 
                nextPlayerRow += 1;
                nextObject = down(currentStateWorldArray, currentStatePlayer);
                break;
            case LEFT:  
                nextPlayerCol -= 1;
                nextObject = left(currentStateWorldArray, currentStatePlayer);
                break;
            case RIGHT:
                nextPlayerCol += 1;
                nextObject = right(currentStateWorldArray, currentStatePlayer);
                break;
        }

        if( nextObject.equals(World.VISITED_TILE)
            || nextObject.equals(World.UNVISITED_TILE) )
        {
            currentStateWorldArray[currPlayerRow][currPlayerCol] = currentStatePlayer.getPrevValue();
            currentStatePlayer.setPrevValue(World.VISITED_TILE);
            currentStateWorldArray[nextPlayerRow][nextPlayerCol] = World.PLAYER;
        }
        // Winning condition
        else if( nextObject.equals(World.GOAL_TILE) ){
            currentStateWorldArray[currPlayerRow][currPlayerCol] = currentStatePlayer.getPrevValue();
            currentStatePlayer.setPrevValue(World.GOAL_TILE);
            currentStateWorldArray[nextPlayerRow][nextPlayerCol] = World.PLAYER;
            currentState.gameWon(true);
        }
        this.movePlayer(currentStatePlayer, dir);
        return null;
    }

    private void movePlayer(Player player, Directions dir){
        switch(dir){
            case UP: 
                player.moveUp();
                break;
            case DOWN:
                player.moveDown();
                break;
            case LEFT:
                player.moveLeft();
                break;
            case RIGHT:
                player.moveRight();
        }
    }

    private boolean GoalTest(State currentState){
        return currentState.getWin();
    }

    private State removeMinF(){
        return initialState;
    }
}