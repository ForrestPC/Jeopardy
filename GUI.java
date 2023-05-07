import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;

/**
 * ---------------------------------------------------------------------------
 * File name: GUI.java
 * Project name: Jeopardy
 * ---------------------------------------------------------------------------
 * Creator's name and email: Forrest Cline, forrestcline@gmail.com
 * Creation Date: Apr 8, 2023
 * ---------------------------------------------------------------------------
 */

/**
 * Enter type purpose here
 *
 * <hr>
 * Date created: Apr 8, 2023
 * <hr>
 * @author Forrest Cline
 */
public class GUI extends JFrame
{
	//Variables for building the GUI
    private final String[] 		columnNames = {"Math","Computer History","Food","Animals","U.S.History","Sports"};
    private final Object[][] 	data = {{"Math","Computer History","Food","Animals","U.S. History","Sports"},
    									{"$200", "$200", "$200", "$200", "$200", "$200"},
    									{"$400", "$400", "$400", "$400", "$400", "$400"},
							            {"$600", "$600", "$600", "$600", "$600", "$600"},
							            {"$800", "$800", "$800", "$800", "$800", "$800"},
							            {"$1000", "$1000", "$1000", "$1000", "$1000", "1000"}};

    private final Border border = BorderFactory.createLineBorder(Color.BLACK);
    private Timer timer;
    private int TIMER_DELAY = 1000;
    private int seconds = 30;
    private int correctAnswerIndex;
    private int scoreValue;
    private String question;
    private String[] answers;
    private JTable table;
    private JPanel mainPanel, questionPanel,scorePanel,endGamePanel;
    private JLabel timerLbl, highScoreNameLbl, highScoreLbl, playerLbl, scoreLbl;
    private JButton[] answerButtons;
    private boolean newHighScore = false;
    private Player player;
    private QuestionHandler questionHandler;
    
    
    public GUI()
    {
    	//Sets the title of title of the Frame
    	super("Jeopardy");
    	
    	// Set the size of the frame and make it visible
        setSize(1200, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
        questionHandler = new QuestionHandler();
        
        //Creates the Main Game Panel and Table
        initializeMainPanel();
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        
        String playerName = JOptionPane.showInputDialog(this, "Please enter your name:");

        while (playerName == null || playerName.trim().isEmpty()) {
            int confirm = JOptionPane.showConfirmDialog(this, "Must enter a username, Do you wish to exit?");

            if (confirm == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(this, "Goodbye!");
               	this.dispose ( );
               	return;
            } else {
                playerName = JOptionPane.showInputDialog(this, "Please enter your name:");
            }
        }

        createPlayer(playerName);

    }
    
   //Initializes the Main Panel
    private void initializeMainPanel()
    {
    	mainPanel = new JPanel(new BorderLayout());
    	initializeTopRibbon();
    	mainPanel.add (topRibbonPanel,BorderLayout.NORTH);
    	initializeBoardTable();
    	mainPanel.add(table, BorderLayout.CENTER);
    	initializeScorePanel();
    	mainPanel.add (scorePanel,BorderLayout.SOUTH);
    }
    
    //initializes the Top Ribbon
    private void initializeTopRibbon()
    {
    	topRibbonPanel = new JPanel(new GridLayout(1,2,10,10));
    	
    	JButton rulesBtn = new JButton("Rules");
    	rulesBtn.setBounds(20,30,50,20);
    	rulesBtn.setBorder (border);
    	topRibbonPanel.add(rulesBtn);
    	rulesBtn.addActionListener(new java.awt.event.ActionListener(){
    		@Override
    		public void actionPerformed(java.awt.event.ActionEvent e) {
    			JOptionPane.showMessageDialog(rootPane, 
    				"1. Enter players name. \n"
    				+ "2. Pick a category and point value. \n"
    				+ "3. Read the question and choose the choice you think is correct. \n"
    				+"4. A dialog box will notify you if your answer is correct or incorrect and add or" 
    				+ " take away points depending on the outcome. \n"
    				+"5. Keep going until all options are answered. \n"
    				+"6. The player with the most points after everything has been answered, wins :)!");
    			}
    		});
    	JButton aboutBtn = new JButton("About");
    	aboutBtn.setBounds(20,30,50,20);
    	aboutBtn.setBorder (border);
    	topRibbonPanel.add(aboutBtn);
    	aboutBtn.addActionListener(new java.awt.event.ActionListener(){
    		@Override
    		public void actionPerformed(java.awt.event.ActionEvent e) {
    			JOptionPane.showMessageDialog(rootPane, 
    				"Jeopardy Game\n Creators:\n Oluwaseyi Ariyo\n Kwame Boahene\n Jada Carter\n Forrest Cline\n Tiffany Cusick\n Jeffrey Corrigan\n Troy Davis");
    			}
    		});
    	
    }
    
    //Creates the Score Panel
    private void initializeScorePanel()
    {
    	scorePanel = new JPanel(new GridLayout(2,2,10,10));
    	
    	highScoreLbl = new JLabel(""+questionHandler.getHighScore());
    	highScoreNameLbl = new JLabel(questionHandler.getHighScoreName());
    	
    	playerLbl = new JLabel("Player: ");
    	scoreLbl = new JLabel("$"+0);
    	
    	highScoreLbl.setFont(new Font("Arial", Font.BOLD, 18));
    	highScoreNameLbl.setFont(new Font("Arial", Font.BOLD, 18));
    	playerLbl.setFont(new Font("Arial", Font.BOLD, 18));
    	scoreLbl.setFont(new Font("Arial", Font.BOLD, 18));
    	
    	highScoreLbl.setHorizontalAlignment (JLabel.CENTER);
    	highScoreNameLbl.setHorizontalAlignment (JLabel.CENTER);
    	playerLbl.setHorizontalAlignment (JLabel.CENTER);
    	scoreLbl.setHorizontalAlignment (JLabel.CENTER);
    	
    	scorePanel.add (highScoreNameLbl);
    	scorePanel.add (playerLbl);
    	scorePanel.add (highScoreLbl);
    	scorePanel.add (scoreLbl);
	
     }	
    
    
    //Creates the Score Panel
    private void initializeScorePanel()
    {
    	scorePanel = new JPanel(new GridLayout(2,2,10,10));
    	
    	highScoreLbl = new JLabel(""+questionHandler.getHighScore());
    	highScoreNameLbl = new JLabel(questionHandler.getHighScoreName());
    	
    	playerLbl = new JLabel("Player: ");
    	scoreLbl = new JLabel("$"+0);
    	
    	highScoreLbl.setFont(new Font("Arial", Font.BOLD, 18));
    	highScoreNameLbl.setFont(new Font("Arial", Font.BOLD, 18));
    	playerLbl.setFont(new Font("Arial", Font.BOLD, 18));
    	scoreLbl.setFont(new Font("Arial", Font.BOLD, 18));
    	
    	highScoreLbl.setHorizontalAlignment (JLabel.CENTER);
    	highScoreNameLbl.setHorizontalAlignment (JLabel.CENTER);
    	playerLbl.setHorizontalAlignment (JLabel.CENTER);
    	scoreLbl.setHorizontalAlignment (JLabel.CENTER);
    	
    	scorePanel.add (highScoreNameLbl);
    	scorePanel.add (playerLbl);
    	scorePanel.add (highScoreLbl);
    	scorePanel.add (scoreLbl);
	
    }
    private void initializeBoardTable()
    {
    	 table = new JTable(data,columnNames) {
             @Override
             public boolean isCellEditable(int row, int column) {
                 return false;
             }

             @Override
             public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                 Component component = super.prepareRenderer(renderer, row, column);
                 component.setForeground(Color.WHITE);
                 component.setBackground(Color.BLUE);
                 ((JLabel) component).setHorizontalAlignment (JLabel.CENTER);
                 if (getValueAt(row, column) == null) {
                     component.setBackground(Color.BLUE);
                 }
                 return component;
             }
         };

         table.setRowHeight(85);
         table.setFont(new Font("Arial", Font.PLAIN, 24));
         table.setFillsViewportHeight(true);
   
         table.addMouseListener(new MyMouseAdapter());
    }
    
    private class MyMouseAdapter extends MouseAdapter
    {
    	 @Override
         public void mouseClicked(MouseEvent e) {
        		 int row = table.rowAtPoint(e.getPoint());
        		 int column = table.columnAtPoint(e.getPoint());
                 if (row >= 1 && column >= 0 && table.getValueAt(row, column) != null) 
                 {
                     table.setValueAt(null, row, column);
                     scoreValue = getScoreValue(row);
                     questionPanel = createQuestionPanel(questionHandler.getQuestion (column, row-1));
                     // Switch to question panel
                     getContentPane().removeAll();
                     getContentPane().add(questionPanel, BorderLayout.CENTER);
                     revalidate();
                     repaint();
                 }
         }
    }
    
    // Creates the Question and Answer Panel
    private JPanel createQuestionPanel(String[] QA) {
        shuffleAnswers(QA);

        JPanel panel = new JPanel(new BorderLayout());

        JTextPane questionPane = new JTextPane();
        questionPane.setText(question);
        questionPane.setBackground(Color.BLUE);
        questionPane.setForeground(Color.WHITE);
        questionPane.setFont(new Font("Arial", Font.BOLD, 40));
        questionPane.setBorder(border);

        // Center the text horizontally
        StyledDocument doc = questionPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        // Center the text vertically
        StyledDocument doc1 = questionPane.getStyledDocument();
        SimpleAttributeSet vertical = new SimpleAttributeSet();
        StyleConstants.setAlignment(vertical, StyleConstants.ALIGN_CENTER);
        StyleConstants.setSpaceAbove(vertical, 0);
        StyleConstants.setSpaceBelow(vertical, 0);
        StyleConstants.setLineSpacing(vertical, .2f);
        Element paragraph = doc1.getParagraphElement(0);
        doc1.setParagraphAttributes(paragraph.getStartOffset(), paragraph.getEndOffset(), vertical, false);

        panel.add(questionPane, BorderLayout.CENTER);

        JPanel answerPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        answerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        answerButtons = new JButton[4];
        for (int i = 0; i < answerButtons.length; i++) {
            answerButtons[i] = new JButton(answers[i]);
            answerButtons[i].setFont(new Font("Arial", Font.PLAIN, 36));
            answerButtons[i].addActionListener(new BtnClicked());
            answerPanel.add(answerButtons[i]);
        }

        newTimer();
        panel.add(timerLbl, BorderLayout.NORTH);
        panel.add(answerPanel, BorderLayout.SOUTH);

        return panel;
    }
    
    public void shuffleAnswers(String[] QA)
    {
		question = QA[0];
		answers = Arrays.copyOfRange (QA, 1, 5);
		correctAnswerIndex = 0;
		List<String> answerList = Arrays.asList(answers);
		Collections.shuffle(answerList);
		correctAnswerIndex = answerList.indexOf(QA[1]);
		answerList.toArray(answers);
    }
    
    private class BtnClicked implements ActionListener
    {
		@Override
		public void actionPerformed (ActionEvent e)
		{
			JButton clickedButton = (JButton) e.getSource();

	        // check if the clicked button is the correct answer button
	        for (int i = 0; i < answerButtons.length; i++) {
	            if (clickedButton == answerButtons[i]) {
	                if (i == correctAnswerIndex) {
	                    // clicked button is the correct answer
	                	timer.stop ( );
	                	updatePlayerScore(true,scoreValue);
	                	closeQuestionPane();
	                	JOptionPane.showMessageDialog(null, "Correct!");
	                	
	                } else {
	                    // clicked button is the wrong answer
	                	timer.stop ( );
	                	updatePlayerScore(false,scoreValue);
	                	closeQuestionPane();
	                	JOptionPane.showMessageDialog(null, "Incorrect!");
	                }
	            }
	        }
		}
    }
    
    private void closeQuestionPane()
    {
    	if(isTableEmpty(table))
    	{
    		endGame();
    	}
    	else {
    		// Switch back to table panel
        	getContentPane().removeAll();
            getContentPane().add(mainPanel, BorderLayout.CENTER);
            revalidate();
            repaint();
    	}
    }
    
    private void newTimer()
    {
    	if(timer != null)
    		timer.stop ( );
    	seconds = 30;
    	timerLbl = new JLabel(""+seconds);
    	timerLbl.setBackground (Color.RED);
    	timerLbl.setOpaque (false);
    	timerLbl.setFont(new Font("Arial", Font.PLAIN, 36));
    	timerLbl.setHorizontalAlignment (JLabel.CENTER);
    	timer = new Timer(TIMER_DELAY, e->{
        	seconds--;
        	timerLbl.setText(""+seconds);
        	if(seconds <=15)
        	{
        		if(seconds % 2 == 0)
        			timerLbl.setOpaque (true);
        		else
        			timerLbl.setOpaque (false);
        	}
        	if(seconds <= 0) {
        		timer.stop ( );
        		// Switch back to table panel
        		updatePlayerScore(false,scoreValue);
        		closeQuestionPane();
        		JOptionPane.showMessageDialog(null, "Ran Out of Time!");
        	}
        });
        timer.start ( );
    }
    
    private int getScoreValue (int row)
    {
    	switch(row)
    	{
    		case 1: return 200;
    		case 2: return 400;
    		case 3: return 600;
    		case 4: return 800;
    		case 5: return 1000;
    	}
		return 0;
    }
    
    private boolean isTableEmpty (JTable table) {
        for (int row = 1; row < table.getRowCount(); row++) {
            for (int col = 0; col < table.getColumnCount(); col++) {
                if (table.getValueAt(row, col) != null) {
                    return false; // found a non-null value
                }
            }
        }
        return true; // all values are null
    }
    
    
    private void createPlayer (String userName)
    {
    	player = new Player(userName);
    	playerLbl.setText (player.getUserName());
    }
    
    
   
    private void updatePlayerScore(boolean correct, int value)
    {
    	if(correct)
    		player.addPoints(value);
    	else
    		player.subtractPoints(value);
    	scoreLbl.setText("Score: "+player.getScore());
    	
    }
   
    
    private void checkHighScore(int playerScore)
    {
    	if(playerScore > questionHandler.getHighScore())
    	{
    		highScoreLbl.setText ("$"+playerScore);
    		highScoreNameLbl.setText ("New High Score: "+ player.getUserName());
    		newHighScore = true;
    		questionHandler.newHighScore(player.getUserName(),player.getScore());
    	}
    }
    
    private void endGame()
    {
    		endGamePanel = new JPanel(new BorderLayout());
    		checkHighScore(player.getScore ( ));
    		scorePanel.removeAll ( );
    		if(newHighScore)
    		{
    			scorePanel.add (highScoreNameLbl);
    			scorePanel.add (highScoreLbl);
    			highScoreNameLbl.setFont(new Font("Arial", Font.BOLD, 36));
    			highScoreLbl.setFont(new Font("Arial", Font.BOLD, 36));
    		}
    		else
    		{
    			scorePanel.add (playerLbl);
    			scorePanel.add (scoreLbl);
    			playerLbl.setFont(new Font("Arial", Font.BOLD, 36));
    			scoreLbl.setFont(new Font("Arial", Font.BOLD, 36));
    		}
    		JLabel endofGame = new JLabel("Game Over! Thank you for Playing our Game");
	    	endofGame.setHorizontalAlignment (JLabel.CENTER);
	        endofGame.setFont(new Font("Arial", Font.BOLD, 48));
	    	endGamePanel.add (endofGame, BorderLayout.NORTH);
	    	endGamePanel.add (scorePanel,BorderLayout.CENTER);
	    	getContentPane().removeAll();
	        getContentPane().add(endGamePanel, BorderLayout.CENTER);
	        revalidate();
	        repaint();
    }
    
}
