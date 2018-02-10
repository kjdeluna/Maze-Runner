import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Texture{
    public static final String DEFAULT_PATH = "resources/textures/";
    private BufferedImage texture;  
    public Texture(BufferedImage loadedTexture){
      this.texture = loadedTexture;
  }
 
  public void render(Graphics2D g2d, double x, double y){
    g2d.drawImage(this.texture, (int) x, (int) y, null);
  }

  public BufferedImage getImage(){
    return this.texture;
  }
}