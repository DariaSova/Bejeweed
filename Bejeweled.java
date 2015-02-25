/*
* Author: Daria Sova
* Program Name: Bejeweled.java
*/

import java.util.*;
import java.io.*;

public class Bejeweled
{
	//static variable to keep the track of the	userÕs score during the game
	public static int score=0;
	//main method
	public static void main (String [] args)
	{
		System.out.println("How to play: type a row and column index, followed by the direction to move it: u (up), r (right), d (down), l (left)");
		//scanner object to read user's commands
		Scanner scn = new Scanner(System.in);
		//create an array as playing field 8x8
		char field [] [] = new char [8][8];
		//array of all possible chars
		char options [] ={'*','$','@','+','!','&'};
		//call the function to fill the field with random characters
		populateRandom(field,options);
		//check for horizontal sets of free and delete all of them
		checkHorizontalSets(field,options);
		//check for vertical sets of free and delete all of them
		checkVerticalSets(field, options);
		//print the field
		drawGrid(field);
		//call the function checking if there are still possible movements
		checkPossibilities(field);
		//variables to store the user input
		int row = 0;
		int col = 0;
		String cmd=null;
		//boolean variable to check for a quit command
		boolean flag = true;
		//while user doesn't enter 'q'
		while(flag)
		{
			System.out.println("Please enter move: ");
			System.out.println("<row> <column> <direction to move>, or 'q' to quit");
			System.out.println("Your current score: "+score);
			//read the first command
			cmd = scn.next();
			//if its 'q' for exit
			if (cmd.equals("q"))
			{
				System.out.println("Thank you!");
				//break the loop and terminate the program running
				flag = false;
			}
			//if it's not 'q' then it should be row number
			else
			{
				//convert string to integer
				row = Integer.parseInt(cmd);
				//read other commands
				col = scn.nextInt();
				cmd = scn.next();
				//call the function that returns true if future move is within the grid
				if(isWithinGrid(row,col,cmd,field)==true)
				{
					//swap elements
					swap(row,col,cmd,field);
					//call the function that returns false if users move does not make a set of three
					if(updateGrid(field,options)==false)
					{
						System.out.println("Sorry, but this move will not give you a set of three characters");
						//swap element back so like nothing was changed
						swap(row,col,cmd,field);
					}
					//call the function that prints current field
					drawGrid(field);
				} 
				//if the move is outside the field
				else
				{
					System.out.println("This move is impossible. Please try again:");
				}
			}
		}
	}
	
	//method that fills	the	2D array grid with random chars taken from the options array.	
	public static void populateRandom(char[][] grid, char[] options) 
	{
		Random rnd = new Random();
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				//fill every cell with random char from options array
				grid[i][j]=options[rnd.nextInt(6)];
			}
		}
	}

	//method that draws	the	current	state of the grid to the console window
	public static void drawGrid(char[][] grid) 
	{
		for(int i =1;i<=8;i++)
		{
			//print indexes for columns from 1 to 8
			System.out.print("\t"+i);
		}
		System.out.println();
		for(int i=0;i<8;i++)
		{
			//print index of the row starting at 1
			System.out.print(i+1+"\t");
			for(int j=0;j<8;j++)
			{
				//print every char from the field array
				System.out.print(grid[i][j]+"\t");
			}
			//new line
			System.out.println("");
		}
	}
	
	//method that checks if the move is possible and returns a boolean indicating whether the proposed move is within the valid	array indexes
	public static boolean isWithinGrid(int row, int col, String cmd, char[][] grid) 
	{
		//set returning value to true
		boolean x=true;
		//if indexes are outside of an array
		if(row<=0||row>8||col<=0||col>8)
		{
			x =false;
		}
		//if the move is outside field array array
		else if(cmd.equals("r")&&col>=8||cmd.equals("l")&&col<=1||cmd.equals("u")&&row<=1||cmd.equals("d")&&row>=8)
		{
			//set returning value to false
			x=false;
		}
		return x;
	}

	//method that swaps	two	items in the grid according to the	command
	public static void swap(int row, int col, String cmd, char[][] grid) 
	{
		//temporary char variable for swapping 
		char temp=' ';
		//swap according to command direction
		if(cmd.equals("l"))
		{
			//put one of the swapping values into temp variable
			temp=grid[row-1][col-2];
			//replace that value with a new one
			grid[row-1][col-2]=grid[row-1][col-1];
			//replace second value for the temp 
			grid[row-1][col-1]=temp;
		}
		else if(cmd.equals("r"))
		{
			temp=grid[row-1][col];
			grid[row-1][col]=grid[row-1][col-1];
			grid[row-1][col-1]=temp;
		}
		else if(cmd.equals("u"))
		{
			temp=grid[row-2][col-1];
			grid[row-2][col-1]=grid[row-1][col-1];
			grid[row-1][col-1]=temp;
		}
		else if(cmd.equals("d"))
		{
			temp=grid[row][col-1];
			grid[row][col-1]=grid[row-1][col-1];
			grid[row-1][col-1]=temp;
		}
	}

	//method that goes through the whole grid and checks for the horizontal sets of three same characters
	//then delete all of them, move everything down and fills free spots with new random values
	//returns true if at least one set was found
	public static boolean checkHorizontalSets(char[][] grid, char[] options)
	{
		//returning value is set to false
		boolean x=false;
		Random rnd = new Random();
			//for every row
			for(int i=0;i<8;i++)
			{
				//compare sets of three characters starting from the first one
				for(int j=0;j<=5;j++)
				{
					//if three the same are found
					if(grid[i][j]==grid[i][j+1]&&grid[i][j]==grid[i][j+2])
					{
						//replace them by uppers ones
						//shift everything down
						for(i=i;i>0;i--)
						{
							grid[i][j]=grid[i-1][j];
							grid[i][j+1]=grid[i-1][j+1];
							grid[i][j+2]=grid[i-1][j+2];
							
						}					
						//replace first row items with the same column indexes with new random chars
						for(int a=j;a<=(j+2);a++)
						{
							grid[0][j]=options[rnd.nextInt(6)];
						}
						//check for sets of three after random filling
						checkHorizontalSets(grid,options);
						checkVerticalSets(grid,options);
						//check for possible moves
						checkPossibilities(grid);
						//set x to true as set of three was found
						x=true;
					}
				}
			}
		//return x value
		return x;
	}
	
	//method that goes through the whole grid and checks for the vertical sets of three same characters
	//then delete all of them, move everything down in this column and fills free spots with new random values
	//returns true if a set was found
	public static boolean checkVerticalSets(char[][] grid, char[] options)
	{
		//returning value is set to false
		boolean x=false;
		Random rnd = new Random();
		//for every column
		for(int j=0;j<8;j++)
		{
			//compare sets of three characters in first column starting from the first row
			for(int i=0;i<=5;i++)
			{
				//if three the same are found
				if(grid[i][j]==grid[i+1][j]&&grid[i][j]==grid[i+2][j])
				{
					//replace them by uppers ones
					//shift everything down
					for(i=i+2;i>2;i--)
					{
						grid[i][j]=grid[i-3][j];			
					}
								
					//replace first row items with the same column indexes with new random chars
					for(i=i;i>=0;i--)
					{
						grid[i][j]=options[rnd.nextInt(6)];
					}
					//check for sets of three after random filling
					checkVerticalSets(grid,options);
					checkHorizontalSets(grid,options);
					//check for possible moves
					checkPossibilities(grid);
					//set x to true as set of three was found
					x=true;
					}
				}
		}
		return x;
	}

	//method that calls checkHorizontalSets&checkVerticalSets checking for sets of 3 in grid,removes them	all, drops items from above	down, and	
	//fills	the	empty slots	with random	chars taken	from the options array.	Returns	a	boolean	that	
	//is true if something in the grid changed and false otherwise	
	public static boolean updateGrid(char[][] grid, char[] options)
	{
		//call checkSet function to check for horizontal sets of three
		boolean x=checkHorizontalSets(grid, options);
		//if a set is found
		if(x==true)
		{
			//increment users score by one each time set of 3 horizontal is found
			//only counts sets resulted from users move
			score++;
		}
		//if not then check for possible vertical sets
		else
		{
			x=checkVerticalSets(grid,options);
			//if a vertical set is found
			if(x==true)
			{
				//increment users score by one each time set of 3 vertical is found
				//only counts sets resulted from users move
				score++;
			}
		}
		//return the result of checkSet execution
		return x;
	}
	
	//finction that checks possible	moves that will make a set
	public static void checkPossibilities(char [] [] grid)
	{
		//call method that returns false if there are no more possible moves result in a set
		if(isPossible(grid)==false)
		{
			//give a message to a user
			System.out.println("Sorry, but you lost because there are no possible moves that will make a set :(");
			//exit the program
			System.exit(0);
		}
	}
	
	//method that calls function to check possible vertical and horizontal moves that will make a set of three
	//return false if there are no more possible moves result in a set
	public static boolean isPossible (char [][] grid)
	{
		//assign returning value to false
		boolean x=false;
		//string of all possible commands
		String [] cmdArray = {"l","r","u","d"};
		//for each row
		for(int i=0;i<8;i++)
		{
			//for each column
			for(int j=0;j<8;j++)
			{
				//for each possible command l,r,u or d
				for(int k=0;k<4;k++)
				{
					//if this is within a grid
					if(isWithinGrid(i+1,j+1,cmdArray[k],grid)==true)
					{
						//swap current element according to command
						swap(i+1,j+1,cmdArray[k],grid);
						//check if that made at least one horizontal set
						if(isHorizontalPossible(grid)==true)
						{
							//if yes, swap back and return true
							swap(i+1,j+1,cmdArray[k],grid);
							return true;
						}
						//if no then check if that made at least one vertical set
						else if(isVerticalPossible(grid)==true)
						{
							//if yes, swap back and return true
							swap(i+1,j+1,cmdArray[k],grid);
							return true;
						}
						//if no set resulted from the swap
						//swap back and check next command
						swap(i+1,j+1,cmdArray[k],grid);
					}
					
				}
				
			}
		}
		//return false if no moves are possible
		return x;
	}
	
	//method that checks for possible horizontal sets
	public static boolean isHorizontalPossible (char [][] grid)
	{
		//returning value is set to false
		boolean x=false;
		//for every row
		for(int i=0;i<8;i++)
		{
			//compare sets of three characters starting from the first one
			for(int j=0;j<=5;j++)
			{
				//if three the same are found
				if(grid[i][j]==grid[i][j+1]&&grid[i][j]==grid[i][j+2])
				{
					//return true
					return true;
				}
			}
		}
		//return x value
		return x;		
	}
	
	//method that checks for possible vertical sets
	public static boolean isVerticalPossible (char [][] grid)
	{
		//returning value is set to false
		boolean x=false;
		Random rnd = new Random();
		//for every column
		for(int j=0;j<8;j++)
		{
			//compare sets of three characters in first column starting from the first row
			for(int i=0;i<=5;i++)
			{
				//if three the same are found
				if(grid[i][j]==grid[i+1][j]&&grid[i][j]==grid[i+2][j])
				{
					return true;
				}
			}
		}
		return x;
	}
	
}
