import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

public class SolutionPanel extends JPanel implements KeyListener{
    private ArrowButton upArrowButton;
    private ArrowButton downArrowButton;
    private ArrowButton leftArrowButton;
    private ArrowButton rightArrowButton;
    private JButton prevButton;
    private JButton nextButton;
    private JButton viewSolutionButton;
    private LinkedList<Directions> path;
    private World world;
    private String message;
    private StatsPanel statsPanel;
    private int currentIndex;
    private Deque<State> stateStore;
    public SolutionPanel(World world, LinkedList<Directions> path, long time, double f){
        // Actual parameters needed by the solutionPanel
        this.world = world;
        this.path = path;
        this.currentIndex = 0;

        // stateStore
        Player clonedPlayer = Clone.clonePlayer(world.getPlayer());
        String[][] clonedArray2D = Clone.cloneArray2D(world.getWorldArray());
        LinkedList<Directions> empty = new LinkedList<Directions>();

        this.stateStore = new ArrayDeque<State>();
        this.stateStore.offerLast(new State(clonedArray2D, clonedPlayer, empty));

        // Default operations
        this.setLayout(null);
        this.setBounds(0,0,SolutionWindow.SOLUTION_FRAME_WIDTH, SolutionWindow.SOLUTION_FRAME_HEIGHT);
        this.setPreferredSize(new Dimension(SolutionWindow.SOLUTION_FRAME_WIDTH, SolutionWindow.SOLUTION_FRAME_HEIGHT));

        // Instantiate normal JButtons
        this.prevButton = new JButton("<- previous");
        this.nextButton = new JButton("next ->");

        /* --------------------------------------------------------------

         statsPanel will be receiving the receiving the:
              Solution:
                  path -> for the actual path generated by BFS (LinkedList)
                  
              Stats:
                  time -> for the actual time it performed BFS method
                  nodesGenerated -> the nodes it enqueued in ArrayDeque 

        -----------------------------------------------------------------*/
        this.statsPanel = new StatsPanel(path, time, f);

        // Instantiate arrow buttons 
        //          -> they have enabled and disabled properties. See defined ArrowButton class
        this.upArrowButton = new ArrowButton("up_arrowkey.png", "up_arrowkey_blur.png");
        this.downArrowButton = new ArrowButton("down_arrowkey.png", "down_arrowkey_blur.png");
        this.leftArrowButton = new ArrowButton("left_arrowkey.png", "left_arrowkey_blur.png");
        this.rightArrowButton = new ArrowButton("right_arrowkey.png", "right_arrowkey_blur.png");

        // Add all of the buttons and panels to this main panel
        this.add(upArrowButton);
        this.add(downArrowButton);
        this.add(leftArrowButton);
        this.add(prevButton);
        this.add(rightArrowButton);
        this.add(nextButton);
        this.add(statsPanel);
        this.message = "Status: Puzzle is still not solved. Follow the highlighted arrowkeys.";

        // Set the locations of each button
        upArrowButton.setBounds(275,75,64,64);
        downArrowButton.setBounds(275,139,64,64);
        leftArrowButton.setBounds(211,139,64,64);
        rightArrowButton.setBounds(339,139,64,64);
        prevButton.setBounds(100,225,100, 40);
        nextButton.setBounds(428,225,100, 40);

        // So that it will not lose focus in the frame
        //      and still accept keyboard inputs
        upArrowButton.setFocusable(false);
        downArrowButton.setFocusable(false);
        leftArrowButton.setFocusable(false);
        rightArrowButton.setFocusable(false);
        prevButton.setFocusable(false);
        nextButton.setFocusable(false);


        // // Which button will be highlighted first
        if(this.path.get(currentIndex) == Directions.UP){
            this.upArrowButton.unsetIcon();
        } else if (this.path.get(currentIndex) == Directions.DOWN){
            this.downArrowButton.unsetIcon();
        } else if (this.path.get(currentIndex) == Directions.LEFT){
            this.leftArrowButton.unsetIcon();
        } else if (this.path.get(currentIndex) == Directions.RIGHT){
            this.rightArrowButton.unsetIcon();
        }

        // Set each action that corresponds to each interface
        //              arrow buttons.
        upArrowButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(!upArrowButton.isDisabled()){
                    Player clonedPlayer = Clone.clonePlayer(world.getPlayer());
                    String[][] clonedArray2D = Clone.cloneArray2D(world.getWorldArray());
                    LinkedList<Directions> empty = new LinkedList<Directions>();
                    stateStore.offerLast(new State(clonedArray2D, clonedPlayer, empty));
                    world.invokeAction(Directions.UP);
                    setNextIcon(Directions.UP); 
                } 
            }
        });
        downArrowButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(!downArrowButton.isDisabled()){
                    Player clonedPlayer = Clone.clonePlayer(world.getPlayer());
                    String[][] clonedArray2D = Clone.cloneArray2D(world.getWorldArray());
                    LinkedList<Directions> empty = new LinkedList<Directions>();
                    stateStore.offerLast(new State(clonedArray2D, clonedPlayer, empty));
                    world.invokeAction(Directions.DOWN);
                    setNextIcon(Directions.DOWN);  
                }
            }
        });
        leftArrowButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(!leftArrowButton.isDisabled()){
                    Player clonedPlayer = Clone.clonePlayer(world.getPlayer());
                    String[][] clonedArray2D = Clone.cloneArray2D(world.getWorldArray());
                    LinkedList<Directions> empty = new LinkedList<Directions>();
                    stateStore.offerLast(new State(clonedArray2D, clonedPlayer, empty));
                    world.invokeAction(Directions.LEFT);
                    setNextIcon(Directions.LEFT); 
                } 
            }
        });
        rightArrowButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(!rightArrowButton.isDisabled()){
                    Player clonedPlayer = Clone.clonePlayer(world.getPlayer());
                    String[][] clonedArray2D = Clone.cloneArray2D(world.getWorldArray());
                    LinkedList<Directions> empty = new LinkedList<Directions>();
                    stateStore.offerLast(new State(clonedArray2D, clonedPlayer, empty)); 
                    world.invokeAction(Directions.RIGHT);
                    setNextIcon(Directions.RIGHT); 
                }
            }
        });

        // Enable up, down, left, right keyboard buttons
        this.addKeyListener(this);
        
        // ask focus for the panel
        this.setFocusable(true);
        this.requestFocusInWindow();

        prevButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(currentIndex <= 0) return;
                updateIcons(path.get(currentIndex));
                currentIndex--;
                Directions nextDir = path.get(currentIndex);
                updateIcons(nextDir);
                State prevState = stateStore.pollLast();
                world.setWorldArray(prevState.getWorldArray());
                world.setPlayer(prevState.getPlayer());                
                world.repaint();                
            }
        });

        nextButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(currentIndex >= path.size()) return;                    
                Directions currDir = path.get(currentIndex);
                setNextIcon(currDir);
                Player clonedPlayer = Clone.clonePlayer(world.getPlayer());
                String[][] clonedArray2D = Clone.cloneArray2D(world.getWorldArray());
                LinkedList<Directions> empty = new LinkedList<Directions>();
                stateStore.offerLast(new State(clonedArray2D, clonedPlayer, empty));
                world.invokeAction(currDir);

            }
        });

    }

    private void updateIcons(Directions dir){
        // updateIcons is a function that will determine 
        //      given a direction, what should be the proper
        //          highlighted buttons
        switch(dir){
            case UP:
                this.upArrowButton.unsetIcon();
                break;
            case DOWN:
                this.downArrowButton.unsetIcon();
                break;
            case LEFT:  
                this.leftArrowButton.unsetIcon();
                break;
            case RIGHT:
                this.rightArrowButton.unsetIcon();
                break;
        }
    }

    private void setNextIcon(Directions dir){
        // setNextIcon is a function that will update the icons for 
        //                  next instructions
        if(this.currentIndex >= this.path.size() - 1){
            updateIcons(dir);
            this.removeKeyListener(this);
            this.message = "Status: Puzzle solved. You may exit this window now.";
            this.repaint();
            return;
        }
        else{
            if(dir.equals(this.path.get(currentIndex))){
                updateIcons(dir);
                currentIndex++; 
                Directions nextDir = this.path.get(currentIndex);
                updateIcons(nextDir);
            }
       }
    }
    public void keyTyped(KeyEvent e){}

    public void keyPressed(KeyEvent e){
        // For keyListener to enable UDLR keyboard buttons
        switch(e.getKeyCode()){
            case KeyEvent.VK_UP:
                if(this.path.get(currentIndex) == Directions.UP){
                    world.invokeAction(Directions.UP);
                    this.setNextIcon(Directions.UP);
                }
                break;
            case KeyEvent.VK_DOWN:
                if(this.path.get(currentIndex) == Directions.DOWN){
                    world.invokeAction(Directions.DOWN);
                    this.setNextIcon(Directions.DOWN);
                }
                break;
            case KeyEvent.VK_LEFT:
                if(this.path.get(currentIndex) == Directions.LEFT){
                    world.invokeAction(Directions.LEFT);
                    this.setNextIcon(Directions.LEFT);
                }
                break;
            case KeyEvent.VK_RIGHT:
                if(this.path.get(currentIndex) == Directions.RIGHT){
                    world.invokeAction(Directions.RIGHT);
                    this.setNextIcon(Directions.RIGHT);
                }
                break;
        }
    }
    public void keyReleased(KeyEvent e){}

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw the background of the solutionPanel
        g2d.drawImage(Main.textureLoader.getTexture(Texture.DEFAULT_PATH, "solutionsBack.jpg").getImage(),0,0,null);
        
        // Change to "somehow" black for background of status bar
        g2d.setColor(new Color(0,0,0,180));
        // Render status bar
        g2d.fillRect(0,0,600,25);

        // Change font color to white
        g2d.setColor(Color.WHITE);
        // Render status string
        g2d.drawString(this.message, 0, 20);
    }
    
}