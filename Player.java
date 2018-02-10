public class Player{
    private int currRow;
    private int currCol;
    private Texture texture;
    private String prevValue;
    public Player(int currRow, int currCol){
        this.currRow = currRow;
        this.currCol = currCol;
        this.texture = Main.textureLoader.getTexture(Texture.DEFAULT_PATH, "player_down.png");
        this.prevValue = "v";
    }

    public Player(int currRow, int currCol, Texture tex){
        this.prevValue = "v";
        this.currRow = currRow;
        this.currCol = currCol;
        this.texture = tex;
    }


    public void moveUp(){
        this.currRow -= 1;
    }

    public void moveDown(){
        this.currRow += 1;
    }

    public void moveLeft(){
        this.currCol -= 1;
    }

    public void moveRight(){
        this.currCol += 1;
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
    public void setPrevValue(String val){
        this.prevValue = val;
    }
    public String getPrevValue(){
        return this.prevValue;
    }
}