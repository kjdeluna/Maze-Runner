public class Player{
    private int currRow;
    private int currCol;
    private Texture texture;

    public Player(int currRow, int currCol){
        this.currRow = currRow;
        this.currCol = currCol;
        this.texture = Main.textureLoader.getTexture(Texture.DEFAULT_PATH, "player_down.png");
    }

    public Player(int currRow, int currCol, Texture tex){
        this.currRow = currRow;
        this.currCol = currCol;
        this.texture = tex;
    }

    public int getCurrRow(){
        return this.currRow;
    }

    public int getCurrCol(){
        return this.currCol;
    }

    public Texture getTexture(){
        return this.texture;
    }
    public void setTexture(Texture newTexture){
        this.texture = newTexture;
    }
}