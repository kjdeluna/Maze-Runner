import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Queue;
import java.util.Set;
import java.util.HashMap;
import java.util.Arrays;
public class Solver{
    private State initialState;
	public static Comparator<State> fValueComparator = new Comparator<State>(){
		
		@Override
		public int compare(State s1, State s2) {
            if(s1.getF() < s2.getF()) return -1;
            else if(s1.getF() > s2.getF()) return 1;
            else return 0;
        }
	};

    public Solver(State initialState){
        this.initialState = initialState;
        // LinkedList<Directions> store = this.Actions(initialState);
        // System.out.println(store.size());
        State solution = this.aStarSolve(this.initialState);
        LinkedList<Directions> path = solution.getPath();
        for(Directions dir : path){
            System.out.println(dir);
        }
    }

    private boolean contains(Queue<State> openList, State toBeInspected){
        return false;
    }

    private State aStarSolve(State initialState){
        Queue<State> openList = new PriorityQueue<>(11, fValueComparator);
        HashMap<String,State> closedList = new HashMap<String,State>();
        openList.add(initialState);
        while(openList.peek() != null){
            State s = openList.poll();
            System.out.println(s.getF());
            for(int i = 0; i < World.ROWS; i++){
                for(int j = 0; j < World.COLS; j++){
                    System.out.print(s.getWorldArray()[i][j] + " ");
                }
            System.out.println("");
            }
            closedList.put(Arrays.deepToString(s.getWorldArray()), s);
            if(GoalTest(s)) return s;
            for(Directions dir : Actions(s)){
                State x = Result(s,dir);
                String[][] world = x.getWorldArray();

                // printing
                // System.out.println("");
                // System.out.println(dir);
                // for(int i = 0; i < World.ROWS; i++){
                //     for(int j = 0; j < World.COLS; j++){
                //         System.out.print(world[i][j] + " ");
                //     }
                //     System.out.println("");
                // }


                String key = Arrays.deepToString(x.getWorldArray());
                if( (!closedList.containsKey(key))
                   || (closedList.containsKey(key) 
                   && x.getG() < closedList.get(key).getG()) ){
                       openList.add(x);
                   }
                
            }
        }
        return null;
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
        Player currentStatePlayer = Clone.clonePlayer(currentState.getPlayer());
        String[][] currentStateWorldArray = Clone.cloneArray2D(currentState.getWorldArray());
        LinkedList<Directions> currentStatePath = Clone.clonePath(currentState.getPath());
        String nextObject = "";
        int currPlayerRow = currentStatePlayer.getCurrRow();
        int currPlayerCol = currentStatePlayer.getCurrCol();
        int nextPlayerRow = currPlayerRow;
        int nextPlayerCol = currPlayerCol;
        boolean ezWin = false;
        switch(dir){
            case UP:    
                nextPlayerRow -= 1;
                nextObject = up(currentStateWorldArray, currentStatePlayer);
                currentStatePath.add(Directions.UP);
                break;
            case DOWN: 
                nextPlayerRow += 1;
                nextObject = down(currentStateWorldArray, currentStatePlayer);
                currentStatePath.add(Directions.DOWN);
                break;
            case LEFT:  
                nextPlayerCol -= 1;
                nextObject = left(currentStateWorldArray, currentStatePlayer);
                currentStatePath.add(Directions.LEFT);
                break;
            case RIGHT:
                nextPlayerCol += 1;
                nextObject = right(currentStateWorldArray, currentStatePlayer);
                currentStatePath.add(Directions.RIGHT);
                break;
        }

        if(nextObject.equals(World.UNVISITED_TILE) )
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
            ezWin = true;
        }
        this.movePlayer(currentStatePlayer, dir);
        State clonedState = new State(currentStateWorldArray, currentStatePlayer, currentStatePath, 
                ezWin, currentState.getGoalRow(), currentState.getGoalCol());
        return clonedState;
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
}