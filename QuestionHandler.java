import java.util.Random;

/**
 * ---------------------------------------------------------------------------
 * File name: QuestionHandler.java
 * Project name: Jeopardy
 * ---------------------------------------------------------------------------
 * Creator's name and email: Forrest Cline, forrestcline@gmail.com
 * Creation Date: Apr 18, 2023
 * ---------------------------------------------------------------------------
 */

/**
 * Enter type purpose here
 *
 * <hr>
 * Date created: Apr 18, 2023
 * <hr>
 * @author Forrest Cline
 */
public class QuestionHandler
{
	String[][][][] questions;
	String[] highScoreData;
	FileManager fileManager;
	
	public QuestionHandler()
	{
		fileManager = new FileManager();
		questions = fileManager.getQuestionsFromFile ( );
		highScoreData = fileManager.getHighScoreInfoFromFile ( );
	}

	public String[] getQuestion (int category, int value)
	{
		Random random = new Random();
		int length = questions[category][value].length;
	    int randomIndex = random.nextInt(length);
	    
	     String[] question = new String[5];
	     question[0] = questions[category][value][randomIndex][0];
	     question[1] = questions[category][value][randomIndex][1];
	     question[2] = questions[category][value][randomIndex][2];
	     question[3] = questions[category][value][randomIndex][3];
	     question[4] = questions[category][value][randomIndex][4];
	     return question;
	}
	
	public int getHighScore ()
	{
	     int highScore = Integer.parseInt(highScoreData[1]);
	     return highScore;
	}
	
	public String getHighScoreName ()
	{
	     String highScore = highScoreData[0];
	     return highScore;
	}
	
	public void newHighScore(String highScoreName, int highScore) 
	{
	    String[] returnHighScore = new String[]{highScoreName, Integer.toString(highScore)};
	    fileManager.writeHighScoreToFile (returnHighScore);
	}

}
