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
        JButton generateMapButton = new JButton("New Map");
        JButton aStarSolveButton = new JButton("A* Solve");
        this.add(generateMapButton);
        this.add(aStarSolveButton);
        generateMapButton.setBounds(0,0,100,30);
        generateMapButton.setFocusable(false);
        aStarSolveButton.setBounds(100, 0, 100,30);
        aStarSolveButton.setFocusable(false);
        generateMapButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                world.generateWorldArray();
            }
        });
        aStarSolveButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                LinkedList<Directions> empty = new LinkedList<Directions>();
                // worldArray, player, path, isWin, goalRow, goalCol
                // empty.add(Directions.LEFT);
                Solver aStarSolver = new Solver(new State(world.getWorldArray(), world.getPlayer(), empty, false, world.getGoalRow(), world.getGoalCol()));
                long startTime = System.currentTimeMillis();
                State resultantState = aStarSolver.aStarSolve();
                long endTime = System.currentTimeMillis();
                System.out.println("That took " + (endTime - startTime) + " milliseconds.");
                if(resultantState != null){
                    SolutionWindow sw = new SolutionWindow(world, resultantState.getPath(), (endTime - startTime), resultantState.getF());
                }
                else System.out.println("No solution found");            
            }
        });
        
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // this.repaint();
    }

}