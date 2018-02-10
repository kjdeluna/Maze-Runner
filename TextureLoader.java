import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import javax.imageio.ImageIO;

public class TextureLoader{
    private HashMap<String, Texture> textureMap;

    // Overloaded constructors
    public TextureLoader(String[] textures){
        this.textureMap = new HashMap<String, Texture>();
        this.loadTextures(Texture.DEFAULT_PATH, textures);
    }
    public TextureLoader(String path, String[] textures){
        this.textureMap = new HashMap<String, Texture>();
        this.loadTextures(path, textures);
    }

    // Methods
    public Texture getTexture(String path, String filename){
        Texture oldTexture = this.textureMap.get(filename);
        if(oldTexture == null){
            System.out.println("Loading texture . . . ");
            try{
                // If image is not yet loaded, load the image
                    // then put it to the HashMap
                BufferedImage newTexture = ImageIO.read(new File(path + filename));
                Texture tex = new Texture(newTexture);
                this.textureMap.put(filename, tex);
                System.out.println("Successfully loaded " + path + filename);
                return tex;
            } catch (IOException e) {
                System.out.println("Loading " + path + filename + " failed");
                e.printStackTrace();
            }
        } 
        // If image is already loaded, return the image
        else {
            return oldTexture;
        }
    return oldTexture;
    }

    public void loadTextures(String path, String[] textures){
        System.out.println("Loading textures . . .");
        for(String imageName : textures){
            try{
                BufferedImage newTexture = ImageIO.read(new File(path + imageName));
                this.textureMap.put(imageName, new Texture(newTexture));
                System.out.println("Successfully loaded " + path + imageName);
            } catch (IOException e){
                System.out.println("Loading " + path + imageName + " failed");
                e.printStackTrace();
            }
        }
    }
}