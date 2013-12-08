package com.jameschin.java.algorithms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FileOps
 * @author: James Chin <JamesLChin@gmail.com>
 */
public final class FileOps {
	
	/**
	 * Given a file of (x, y) coordinates, returns the shortest distance from the specified current coordinates.
	 * Space: O(1)
	 * Time: O(n) where n is the number of coordinates.
	 * @param filename file of (x, y) coordinates.
	 * @param x current x coordinate.
	 * @param y current y coordinate.
	 * @return the shortest distance from the specified current coordinates.
	 */
	public static double shortestDistance(String filename, double x, double y) {
		double x1 = 0;
		double y1 = 0;
		double shortest = Double.MAX_VALUE;
		
		String regex = "[0-9]+\\.*[0-9]*";
		Pattern pattern = Pattern.compile(regex);
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String currentLine;
			
			while ((currentLine = br.readLine()) != null) {
				Matcher matcher = pattern.matcher(currentLine);
				if (matcher.find())
					x1 = Double.valueOf(matcher.group());
				if (matcher.find())
					y1 = Double.valueOf(matcher.group());
				
				x1 = x1 - x;
				y1 = y1 - y;
				double current = Math.sqrt(x1 * x1 + y1 * y1);
				
				if (current < shortest)
					shortest = current;
			}
			
			if (br != null)
				br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return shortest;
	}
}