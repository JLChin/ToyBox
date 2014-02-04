package com.jameschin.java.algorithms;

import java.util.HashMap;

/**
 * MartialArtsStaff
 * TODO Faster solution using condensed table lookup.
 * O(n^2 log n) by generating a 2D table where each successive row combines into half as many sections.
 * O(n^5/2) by splitting the branch into squareroot(branch length) sections.
 * In each case, the innermost loop can be accomplished in log n or n^1/2 time.
 * @author: James Chin <jameslchin@gmail.com>
 */
public final class MartialArtsStaff {
	
	/**
	 * Space: O(n)
	 * Time: O(n^3)
	 * @param branch string of digits [1-9] representing the mass distribution of the branch.
	 */
	public static void findCuts(String branch) {
		if (branch == null || branch.length() < 2)
			return;
		
		// structure representing each cut, which will be mapped in a hash table with the key "weight center-of-gravity"
		class WCG {
			int startIndex;
			int endIndex;
			
			WCG(int s, int e) {
				startIndex = s;
				endIndex = e;
			}
		}
		
		int length = branch.length();
		int weight, currWeight, currSum;
		double currCoG;
		String key;
		WCG found;
		
		// iterate through each length of cut, starting with the largest possible
		for (int cutLength = length / 2; cutLength > 0; cutLength--) {
			HashMap<String, WCG> hashmap = new HashMap<String, WCG>(); // map is recycled after each cutLength
			
			// i is the starting index of each cut trial, check every cutLength from left to right
			for (int i = 0; i + cutLength - 1 < length; i++) {
				// calculate weight and center of gravity of current cut
				currWeight = 0;
				currSum = 0;
				currCoG = 0;
				key = "";
				
				for (int j = i, offset = 1; j < i + cutLength; j++, offset++) {
					weight = Integer.valueOf(branch.substring(j, j + 1));
					currWeight += weight;
					currSum += weight * offset;
				}
				
				currCoG = (double) currSum / currWeight;
				key += currWeight;
				key += " ";
				key += currCoG;
				
				// check hashmap for matching weight and center of gravity for this cutLength, and that they do not overlap
				found = hashmap.get(key);
				if (found != null && found.endIndex < i) {
					// success
					System.out.print(found.startIndex + " " + i + " " + cutLength);
					return;
				}
				
				key = "";
				key += currWeight;
				key += " ";
				key += cutLength - currCoG + 1;
				
				// check reverse orientation
				found = hashmap.get(key);
				if (found != null && found.endIndex < i) {
					// success
					System.out.print(found.startIndex + " " + i + " " + cutLength);
					return;
				}
				
				// memo this cut otherwise
				hashmap.put(key, new WCG(i, i + cutLength - 1));
			}
		}
		
		// fail
		System.out.println("Not found");
	}

}