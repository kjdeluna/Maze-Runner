import javax.swing.JButton;
import javax.swing.ImageIcon;

public class ArrowButton extends JButton{
    public static String ICONS_PATH = "resources/icons/";
    private ImageIcon enabledIcon;
    private ImageIcon disabledIcon;
    private boolean disabled;
    public ArrowButton(String enabledIconName, String disabledIconName){
        this.enabledIcon = new ImageIcon(ICONS_PATH + enabledIconName);
        this.disabledIcon = new ImageIcon(ICONS_PATH + disabledIconName);
        this.disabled = true;
        // Initialization
        this.setIcon(this.disabledIcon);
    }

    public boolean isDisabled(){
        return this.disabled;
    }

    public void unsetIcon(){
        if(this.disabled){
            this.setIcon(this.enabledIcon);
        }
        else {
            this.setIcon(this.disabledIcon);
        }
        this.disabled = !this.disabled;
        
    }
}