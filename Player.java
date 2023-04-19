/**
 * ---------------------------------------------------------------------------
 * File name: Player.java
 * Project name: Jeopardy
 * ---------------------------------------------------------------------------
 * Creator's name and email: Forrest Cline, forrestcline@gmail.com
 * Creation Date: Apr 16, 2023
 * ---------------------------------------------------------------------------
 */

/**
 * Enter type purpose here
 *
 * <hr>
 * Date created: Apr 16, 2023
 * <hr>
 * @author Forrest Cline
 */
public class Player
{
	private String userName;
	private int score;
	
	public Player(String userName)
	{
		this.userName = userName;
	}
	
	public int getScore()
	{
		return score;
	}
	
	public void addPoints(int points)
	{
		score = score + points;
	}
	
	public void subtractPoints(int points)
	{
		score = score - points;
	}
	
	public String getUserName()
	{
		return userName;
	}
	
}
