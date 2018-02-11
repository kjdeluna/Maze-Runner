import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.util.LinkedList;

public class SolutionWindow{
    private static final String SOLUTION_TITLE = "Solution";
    public static final int SOLUTION_FRAME_WIDTH = 600;
    public static final int SOLUTION_FRAME_HEIGHT = 560;
    public SolutionWindow(World world, LinkedList<Directions> path, long time, double f){
        JFrame solutionFrame = new JFrame(SolutionWindow.SOLUTION_TITLE);
        solutionFrame.setPreferredSize(new Dimension(SOLUTION_FRAME_WIDTH, SOLUTION_FRAME_HEIGHT));
        solutionFrame.setResizable(false);
        solutionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        SolutionPanel sp = new SolutionPanel(world, path, time, f);
        solutionFrame.add(sp);

        solutionFrame.pack();
        solutionFrame.setVisible(true);
    }


}