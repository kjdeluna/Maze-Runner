import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.util.LinkedList;
public class OptionsPanel extends JPanel{
    private World world;
    public OptionsPanel(World world){
        this.world = world;
        this.setLayout(null);
        this.setPreferredSize(new Dimension(Main.FRAME_WIDTH, 50));
        this.setOpaque(true);
        this.setBackground(Color.BLACK);

        JButton aStarSolveButton = new JButton("A* Solve");
        this.add(aStarSolveButton);
        aStarSolveButton.setBounds(0, 0, 100,30);
        aStarSolveButton.setFocusable(false);
        aStarSolveButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                LinkedList<Directions> empty = new LinkedList<Directions>();
                Solver aStarSolver = new Solver(new State(world.getWorldArray(), world.getPlayer(), empty));
            }
        });
        
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // this.repaint();
    }

}