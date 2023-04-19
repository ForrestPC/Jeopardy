import java.io.File;
import javax.swing.JOptionPane;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;

/**
 * ---------------------------------------------------------------------------
 * File name: FileManager.java
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
public class FileManager
{
	private String questionFileContents;
	private String highScoreFileContents;
	
	public FileManager()
	{
		readQuestionsFile();
		readHighScoreFile();
	}
	
	private void readQuestionsFile()
	{
		File file = new File("src/Questions.txt");
		StringBuilder sb = new StringBuilder();

		try (Scanner scanner = new Scanner(file, StandardCharsets.UTF_8.name())) {
		    while (scanner.hasNextLine()) {
		        sb.append(scanner.nextLine()).append("\n");
		    }
		} catch (IOException e) {
		    JOptionPane.showMessageDialog(null, "Unable to Locate the Questions","ERROR", JOptionPane.ERROR_MESSAGE);
		}

		questionFileContents = sb.toString();

	}
	
	private void readHighScoreFile()
	{
		File file = new File("src/HighScore.txt");

		try (Scanner scanner = new Scanner(file, StandardCharsets.UTF_8.name()))
		{
			highScoreFileContents = scanner.nextLine();
		} 
		catch (IOException e)
		{
		    JOptionPane.showMessageDialog(null, "Unable to Locate the Questions","ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private int getCategoryIndex (String category) //{"Math","Computer History","Food","Animals","U.S.History","Sports"}
	{
		switch(category)
		{
			case "Math": return 0;
			case "Computer History": return 1;
			case "Food": return 2;
			case "Animals": return 3;
			case "U.S. History": return 4;
			case "Sports" : return 5;
			default : return -1;
		}
	}
	
	private int getValueIndex (String value)
	{
		switch(value)
		{
			case "200": return 0;
			case "400": return 1;
			case "600": return 2;
			case "800": return 3;
			case "1000": return 4;
			default : return -1;
		}
	}
	
	public String[][][][] getQuestionsFromFile()
	{
	    // Split the raw data into lines
	    String[] lines = questionFileContents.split("\\r?\\n");

	    // Initialize the array with known sizes for the first two dimensions
	    String[][][][] myArray = new String[6][5][10][5];

	    // Keep track of the current question index for each category and difficulty level
	    int[][] currentIndex = new int[6][5];

	    // Process each line of the data
	    for (String line : lines) {
	        String[] parts = line.split("%");

	        // Extract the category, value, question, and answers from the line
	        String category = parts[0];
	        String value = parts[1];
	        String question = parts[2];
	        String[] answers = Arrays.copyOfRange(parts, 4, parts.length);

	        // Check if we need to expand the array for this category and difficulty level
	        if (currentIndex[getCategoryIndex(category)][getValueIndex(value)] >= myArray[getCategoryIndex(category)][getValueIndex(value)].length) {
	            myArray[getCategoryIndex(category)][getValueIndex(value)] = Arrays.copyOf(myArray[getCategoryIndex(category)][getValueIndex(value)], myArray[getCategoryIndex(category)][getValueIndex(value)].length * 2);
	        }

	        // Add the question and answers to the array
	        myArray[getCategoryIndex(category)][getValueIndex(value)][currentIndex[getCategoryIndex(category)][getValueIndex(value)]][0] = question;
	        for (int i = 0; i < answers.length; i++) {
	            myArray[getCategoryIndex(category)][getValueIndex(value)][currentIndex[getCategoryIndex(category)][getValueIndex(value)]][i+1] = answers[i];
	        }

	        // Increment the current index for this category and difficulty level
	        currentIndex[getCategoryIndex(category)][getValueIndex(value)]++;
	    }
	    
	    // Trim the array to remove any unused entries
	    for (int i = 0; i < myArray.length; i++) {
	        for (int j = 0; j < myArray[i].length; j++) {
	            myArray[i][j] = Arrays.copyOf(myArray[i][j], currentIndex[i][j]);
	        }
	    }
	    
	    return myArray;
	}

	
	public String[] getHighScoreInfoFromFile()
	{
		String[] lines = highScoreFileContents.split("%");
		
		return lines;
		
	}
	
	public void writeHighScoreToFile(String[] newHighScoreData)
	{
		try {
		    File file = new File("src/HighScore.txt");
		    FileWriter writer = new FileWriter(file, false); // false means overwrite
		    String data = newHighScoreData[0]+"%"+newHighScoreData[1];
		    writer.write(data);
		    writer.close();
		} catch (IOException e) {
		    JOptionPane.showMessageDialog(null, "Unable to write to file","ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
}


