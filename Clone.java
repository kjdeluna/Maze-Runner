import java.util.LinkedList;

/* ----------------------------------------------------------------------------
 *
 *  This class will be used to clone the value of a certain class 
 *      - is used to preserve the original values of the parent state
 *
 *  Method name format: clone<class/object>
 *
 * --------------------------------------------------------------------------*/

public final class Clone{

    public static Player clonePlayer(Player src){
        Texture tex = src.getTexture();
        int currRow = src.getCurrRow();
        int currCol = src.getCurrCol();
        Player dest = new Player(currRow, currCol, tex);
        return dest;
    }

    public static String[][] cloneArray2D(String[][] src){
        int length = src.length;
        String[][] target = new String[length][src[0].length];
        for(int i = 0 ; i < length; i++){
            System.arraycopy(src[i], 0, target[i], 0, src[i].length);
        }
        return target;
    }

    public static LinkedList<Directions> clonePath(LinkedList<Directions> src){
        LinkedList<Directions> dest = new LinkedList<Directions>(src);
        return dest;
    }
}