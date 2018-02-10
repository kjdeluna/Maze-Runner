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

    private State removeMinF(){
        return initialState;
    }
}