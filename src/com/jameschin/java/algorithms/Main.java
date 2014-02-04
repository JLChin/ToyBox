package com.jameschin.java.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Testing Area
 * @author: James Chin <jameslchin@gmail.com>
 */
public class Main {
	
	public static void main(String[] args) {
		testKnapsack();
	}

	public static <E extends Comparable<E>> boolean isInOrder(E[] array) {
		if (array.length <= 1)
			return true;
		
		for (int i = 1; i < array.length; i++) {
			if (array[i].compareTo(array[i - 1]) < 0) {
				return false;
			}
		}
		return true;
	}
	
	public static <E extends Comparable<E>> boolean isInReverseOrder(E[] array) {
		if (array.length <= 1)
			return true;
		
		for (int i = 1; i < array.length; i++) {
			if (array[i].compareTo(array[i - 1]) > 0) {
				return false;
			}
		}
		return true;
	}
	
	public static void speedComparison() {
		int SIZE = 1000000;
		Timer timer = new Timer();
		Random random = new Random();
		int k = 10; // top k elements
		
		Integer[] array = new Integer[SIZE];
		int[] arrayCopy = new int[SIZE];
		int[] arrayCopy2 = new int[SIZE];
		Integer[] arrayCopy3 = new Integer[SIZE];
		LinkedList<Integer> linkedList = new LinkedList<Integer>();
		for (int i = 0; i < array.length; i++) {
			array[i] = random.nextInt(Integer.MAX_VALUE);
			arrayCopy[i] = array[i];
			arrayCopy2[i] = array[i];
			arrayCopy3[i] = array[i];
			linkedList.add(array[i]);
		}
		
		// RadixSortLSD
		timer.start();
		RadixSortLSD.sort(arrayCopy2);
		timer.stop();
		System.out.println("RadixSortLSD: " + timer.getTime());
		for (int i = arrayCopy2.length - k; i < arrayCopy2.length; i++) {
			System.out.print(arrayCopy2[i] + " ");
		}
		System.out.println();
		timer.reset();
		
		// IntegerTrie
		timer.start();
		IntegerTrie intTrie = new IntegerTrie(arrayCopy);
		int[] sortedIntTrie = intTrie.toArray();
		timer.stop();
		System.out.println("IntegerTrie: " + timer.getTime());
		for (int i = sortedIntTrie.length - k; i < sortedIntTrie.length; i++) {
			System.out.print(sortedIntTrie[i] + " ");
		}
		System.out.println();
		timer.reset();
		
		// Find.getTop()
		Integer[] top;
		timer.start();
		top = Find.getTop(array, k);
		timer.stop();
		System.out.println("getTop: " + timer.getTime());
		for (int i : top) {
			System.out.print(i + " ");
		}
		System.out.println();
		timer.reset();
		
		// TreeSet
		timer.start();
		TreeSet<Integer> treeSet = new TreeSet<Integer>();
		for (int i : array) {
			treeSet.add(i);
		}
		Integer[] sortedTreeSet = treeSet.toArray();
		timer.stop();
		System.out.println("TreeSet: " + timer.getTime());
		for (int i = sortedTreeSet.length - k; i < sortedTreeSet.length; i++) {
			System.out.print(sortedTreeSet[i] + " ");
		}
		System.out.println();
		timer.reset();
		
		// LinkedList MergeSort
		timer.start();
		linkedList.sort();
		Integer[] sortedLinkedList = linkedList.toArray(); 
		timer.stop();
		System.out.println("LinkedList MergeSort: " + timer.getTime());
		for (int i = sortedLinkedList.length - k; i < sortedLinkedList.length; i++) {
			System.out.print(sortedLinkedList[i] + " ");
		}
		System.out.println();
		timer.reset();
		
		// HeapSort
		timer.start();
		HeapSort.sort(array);
		timer.stop();
		System.out.println("HeapSort: " + timer.getTime());
		for (int i = array.length - k; i < array.length; i++) {
			System.out.print(array[i] + " ");
		}
		System.out.println();
		timer.reset();
		
		// RadixSort
		timer.start();
		RadixSort.sort(arrayCopy);
		timer.stop();
		System.out.println("RadixSort: " + timer.getTime());
		for (int i = arrayCopy.length - k; i < arrayCopy.length; i++ ) {
			System.out.print(arrayCopy[i] + " ");
		}
		System.out.println();
		timer.reset();

		// MergeSort
		timer.start();
		MergeSort.sort(arrayCopy3);
		timer.stop();
		System.out.println("MergeSort: " + timer.getTime());
		for (int i = arrayCopy3.length - k; i < arrayCopy3.length; i++) {
			System.out.print(arrayCopy3[i] + " ");
		}
		System.out.println();
		timer.reset();
	}
	
	public static void speedComparisonPowerSet() {
		int SIZE = 16;
		Timer timer = new Timer();
		
		Integer[] set1 = new Integer[SIZE];
		for (int i = 0; i < SIZE; i++)
			set1[i] = i;
		
		Set<Integer> set2 = new HashSet<Integer>();
		for (int i = 0; i < SIZE; i++)
			set2.add(i);
		
		// Recursive power set function
		timer.start();
		Set<Set<Integer>> powerset2 = Find.powerSetRecursive(set2);
		timer.stop();
		System.out.println("Recursive power set function: " + timer.getTime());
		System.out.println("Size: " + powerset2.size());
		timer.reset();

		// Non-recursive power set function
		timer.start();
		Integer[][] powerset1 = Find.powerSet(set1);
		timer.stop();
		System.out.println("Non-recursive power set function: " + timer.getTime());
		System.out.println("Size: " + powerset1.length);
		timer.reset();
	}
	
	public static void testAddBinaryNumbers() {
		String num1 = "110";
		String num2 = "01101";
		System.out.println(Miscellaneous.addBinaryNumbers(num1, num2));
	}
	
	public static void testAnagram() {
		String[] array = {"paper", "rats", "rat", "cone", "Star", "hi", "tar", "tsar"};
		
		List<List<String>> result = Anagram.collect(array);
		
		System.out.println(result.toString());
	}
	
	public static void testBoggle() {
		char[][] array = {{'S', 'M', 'E', 'F'}, {'R', 'A', 'T', 'D'}, {'L', 'O', 'N', 'I'}, {'K', 'A', 'F', 'B'}};
		Boggle boggle = new Boggle(array);
		
		System.out.println(boggle.exists("star"));
		System.out.println(boggle.exists("tone"));
		System.out.println(boggle.exists("note"));
		System.out.println(boggle.exists("sand"));
	}
	
	public static void testChess() {
		Chess game = new Chess();
		game.drawBoard();
		game.move('E', 2, 'E', 4);
		game.move('D', 7, 'D', 5);
		game.move('F', 1, 'C', 4);
		game.move('G', 8, 'F', 6);
		game.move('H', 2, 'H', 4);
		game.move('H', 7, 'H', 5);
		game.move('H', 1, 'H', 3);
		game.move('H', 8, 'H', 6);
		game.move('H', 3, 'B', 3);
	}
	
	public static void testCombination() {
		Integer[] array = {1, 2, 3};
		int k = 2;
		boolean replacementAllowed = true;
		boolean permutationsAllowed = false;
		
		List<Integer[]> result = Combination.nCk(array, k, replacementAllowed, permutationsAllowed);
		
		for (Integer[] i : result) {
			for (int j = 0; j < i.length; j++)
				System.out.print(i[j] + " ");
			System.out.println();
		}
	}
	
	public static void testContacts() {
		Contacts contacts = new Contacts();
		contacts.add("JChin", "James", "Chin", "JamesLChin@gmail.com", "310-293-9236");
		contacts.add("JDoe", "John", "Doe", "john@doe.com", "000-000-0000");
		contacts.add("JDoe", "John", "Doe", "john@doe.com", "000-000-0000");
		contacts.remove(1);
		contacts.add("JDoe", "Jane", "Doe", "jane@doe.com", "000-000-0000");
		contacts.add("o_O", "Chuck", "Norris", "chuck@norris.com", "999-999-9999");
		
		contacts.printContacts();
		System.out.println();
		
		contacts.printSearch(null, null, "Doe", null, "000-000-0000");
	}
	
	public static void testDirectedGraph() {
		DirectedGraph graph = new DirectedGraph();
		DirectedGraph.Node seven = graph.new Node("7");
		DirectedGraph.Node five = graph.new Node("5");
		DirectedGraph.Node three = graph.new Node("3");
		DirectedGraph.Node eleven = graph.new Node("11");
		DirectedGraph.Node eight = graph.new Node("8");
		DirectedGraph.Node two = graph.new Node("2");
		DirectedGraph.Node nine = graph.new Node("9");
		DirectedGraph.Node ten = graph.new Node("10");
		
		// add edges to nodes
		seven.addEdge(eleven).addEdge(eight);
		five.addEdge(eleven);
		three.addEdge(eight).addEdge(ten);
		eleven.addEdge(two).addEdge(nine).addEdge(ten);
		eight.addEdge(nine);
		
		// add nodes to graph
		graph.addNode(seven).addNode(five).addNode(three).addNode(eleven).addNode(eight).addNode(two).addNode(nine).addNode(ten);
		
		for (String s : graph.topSort()) {
			System.out.print(s + " ");
		}
	}
	
	public static void testFibonacci() {
		System.out.println(Find.fibonacci(14));
	}
	
	public static void testFileOps() {
		System.out.println(FileOps.shortestDistance("C:\\test.txt", 0, 0));
	}
	
	public static void testFrequency() {
		Integer[] array = {1, 1, 2, 2, 2, 3};
		System.out.println(Find.frequency(array, 2));
	}
	
	public static void testGenerateMutations() {
		String string = "fab";
		HashMap<Character, Character[]> hashmap = new HashMap<Character, Character[]>();
		Character[] arrayf = {'F', '4'};
		Character[] arrayb = {'B', '8'};
		hashmap.put('f', arrayf);
		hashmap.put('b', arrayb);
		String[] result = StringOps.generateMutations(string, hashmap);
		
		for (String s : result)
			System.out.print(s + " ");
	}
	
	public static void testGetTop() {
		Integer[] array = {2, 1, 8, 7, 5, 0};
		
		for (int i : Find.getTop(array, 3)) {
			System.out.print(i + " ");
		}
	}
	
	public static void testGraph() {
		// Sparse unconnected graph
		Graph graph = new Graph();
		Graph.Node one = graph.new Node("1");
		Graph.Node two = graph.new Node("2");
		Graph.Node three = graph.new Node("3");
		Graph.Node four = graph.new Node("4");
		Graph.Node five = graph.new Node("5");
		Graph.Node six = graph.new Node("6");
		Graph.Node seven = graph.new Node("7");
		
		one.addEdge(five).addEdge(six);
		two.addEdge(four);
		five.addEdge(six);
		seven.addEdge(one);
		
		graph.addNode(one).addNode(two).addNode(three).addNode(four).addNode(five).addNode(six).addNode(seven);
		
		System.out.println("Sparse Unconnected Graph:");
		graph.cloneGraph().printGraph();
		
		// Densely connected graph
		Graph graph2 = new Graph();
		Graph.Node eight = graph2.new Node("8");
		Graph.Node nine = graph2.new Node("9");
		Graph.Node ten = graph2.new Node("10");
		Graph.Node eleven = graph2.new Node("11");
		
		eight.addEdge(nine).addEdge(ten).addEdge(eleven);
		nine.addEdge(ten).addEdge(eleven);
		ten.addEdge(eleven);
		
		graph2.addNode(eight).addNode(nine).addNode(ten).addNode(eleven);
		
		System.out.println("\nDensely Connected Graph:");
		graph2.cloneGraph().printGraph();
	}
	
	public static void testGrayCode() {
		Find.grayCode(4);
	}
	
	public static void testHashMap() {
		HashMap<String, String> hashmap = new HashMap<String, String>();
		
		System.out.println("put(\"Nine\", \"9\"): " + hashmap.put("Nine", "9"));
		System.out.println("put(\"Ten\", \"10\"): " + hashmap.put("Ten", "10"));
		System.out.println("put(\"a\", \"single a\"): " + hashmap.put("a", "single a")); // "a" and "q" evaluate to same hash
		System.out.println("put(\"q\", \"single q\"): " + hashmap.put("q", "single q"));
		
		System.out.println("get(\"Nine\") = " + hashmap.get("Nine"));
		System.out.println("get(\"Five\") = " + hashmap.get("Five"));
		System.out.println("get(\"a\") = " + hashmap.get("a"));
		System.out.println("get(\"q\") = " + hashmap.get("q"));
		
		System.out.println("put(\"One\", \"1\"): " + hashmap.put("One", "1"));
		System.out.println("put(\"Two\", \"2\"): " + hashmap.put("Two", "2"));
		System.out.println("put(\"Two\", \"new 2\"): " + hashmap.put("Two", " new 2"));
		System.out.println("put(\"Three\", \"3\"): " + hashmap.put("Three", "3"));
		System.out.println("put(\"Four\", \"4\"): " + hashmap.put("Four", "4"));
		
		System.out.println("get(\"Nine\") = " + hashmap.get("Nine"));
		System.out.println("get(\"Two\") = " + hashmap.get("Two"));
		System.out.println("get(\"a\") = " + hashmap.get("a"));
		System.out.println("get(\"Four\") = " + hashmap.get("Four"));
		System.out.println("get(\"q\") = " + hashmap.get("q"));
		
		System.out.println("remove(\"a\") = " + hashmap.remove("a"));
		System.out.println("remove(\"q\") = " + hashmap.remove("q"));
		System.out.println("remove(\"One\") = " + hashmap.remove("One"));
	}
	
	public static void testHeapSort() {
		Timer timer = new Timer();
		Random random = new Random();
		int k = 10; // top k elements
		Integer[] array = new Integer[1000000];
		
		for (int i = 0; i < array.length; i++)
			array[i] = random.nextInt(Integer.MAX_VALUE);
		
		timer.start();
		HeapSort.sort(array);
		timer.stop();
		System.out.println("HeapSort: " + timer.getTime());
		for (int i = array.length - k; i < array.length; i++ ) {
			System.out.print(array[i] + " ");
		}
		System.out.println();
		timer.reset();
		
		System.out.println("In Order: " + isInOrder(array));
	}
	
	public static void testInsertionSort() {
		Timer timer = new Timer();
		Random random = new Random();
		int k = 10; // top k elements
		Integer[] array = new Integer[100000];
		
		for (int i = 0; i < array.length; i++)
			array[i] = random.nextInt(Integer.MAX_VALUE);
		
		timer.start();
		InsertionSort.sort(array);
		timer.stop();
		System.out.println("InsertionSort: " + timer.getTime());
		for (int i = array.length - k; i < array.length; i++ ) {
			System.out.print(array[i] + " ");
		}
		System.out.println();
		timer.reset();
		
		System.out.println("In Order: " + isInOrder(array));
		
		timer.start();
		InsertionSort.sort(array);
		timer.stop();
		System.out.println("InsertionSort (already sorted): " + timer.getTime());
		for (int i = array.length - k; i < array.length; i++ ) {
			System.out.print(array[i] + " ");
		}
	}
	
	public static void testIntegerTrie() {
		int[] array = {100, 1, 5, 61, 0, 2134567890, 81, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19};
		IntegerTrie trie = new IntegerTrie(array);
		
		int[] sortedArray = trie.toArray();
		for (int i : sortedArray) {
			System.out.println(i);
		}
		
		System.out.println("size() = " + trie.size());
		System.out.println("nextInt() = " + trie.nextInt());
		System.out.println("contains(15) = " + trie.contains(15));
		System.out.println("add(15) = " + trie.add(15));
		System.out.println("remove(15) = " + trie.remove(15));
		System.out.println("contains(15) = " + trie.contains(15));
		System.out.println("size() = " + trie.size());
		System.out.println("nextInt() = " + trie.nextInt());
		System.out.println("clear()");
		trie.clear();
		System.out.println("size() = " + trie.size());
		System.out.println("add(0) = " + trie.add(0));
		System.out.println("nextInt() = " + trie.nextInt());
	}
	
	public static void testIsPalindrome() {
		System.out.println(StringOps.isPalindrome("A man, a plan, a canal: Panama."));
	}
	
	public static void testKnapsack() {
		int[] weights = {12, 2, 1, 4, 1};
		int[] values = {4, 2, 1, 10, 2};
		int maxWeight = 15;
		
		System.out.println("Unbounded max value: " + Knapsack.maxValueUnbounded(values, weights, maxWeight));
		System.out.println("0/1 max value: " + Knapsack.maxValue01(values, weights, maxWeight));
	}
	
	public static void testLeftShift() {
		String[] array = {"zero", "one", "two", "three", "four", "five"};
		
		for (String s : array) {
			System.out.print(s + " ");
		}
		
		Miscellaneous.leftShift(array, 1);
		
		System.out.println();
		for (String s : array) {
			System.out.print(s + " ");
		}
	}
	
	public static void testLinkedList() {
		Timer timer = new Timer();
		Random random = new Random();
		int k = 10; // top k elements
		LinkedList<Integer> linkedlist = new LinkedList<Integer>();
		
		for (int i = 0; i < 1000000; i++) {
			linkedlist.add(random.nextInt(Integer.MAX_VALUE));
		}
		
		timer.start();
		linkedlist.sort();
		Integer[] array = linkedlist.toArray();
		timer.stop();
		
		System.out.println("LinkedList MergeSort: " + timer.getTime());
		for (int i = array.length - k; i < array.length; i++ ) {
			System.out.print(array[i] + " ");
		}
		System.out.println();
		System.out.println("In Order: " + isInOrder(array));
	}
	
	public static void testMartialArtsStaff() {
		MartialArtsStaff.findCuts("41111921111119");
		System.out.println();
		MartialArtsStaff.findCuts("131251141231");
	}
	
	public static void testMaxHeap() {
		MaxHeap<Integer> heap = new MaxHeap<Integer>();
		heap.add(6).add(5).add(3).add(1).add(8).add(7).add(2).add(4);
		
		Integer[] array = new Integer[8];
		heap.toArray(array);
		for (int i : array) {
			System.out.print(i + " ");
		}
		System.out.println();
		
		System.out.println("Deleted max: " + heap.deleteMax());
		for (int i : heap.toArray()) {
			System.out.print(i + " ");
		}
		System.out.println();
		
		System.out.println("findMax() = " + heap.findMax());
		System.out.println("size() = " + heap.size());
		
		System.out.println("remove(2): " + heap.remove(2));
		for (int i : heap.toArray()) {
			System.out.print(i + " ");
		}
		System.out.println();
	}
	
	public static void testMergeSort() {
		Timer timer = new Timer();
		Random random = new Random();
		int k = 10; // top k elements
		Integer[] array = new Integer[1000000];
		
		for (int i = 0; i < array.length; i++)
			array[i] = random.nextInt(Integer.MAX_VALUE);
		
		timer.start();
		MergeSort.sort(array);
		timer.stop();
		System.out.println("MergeSort: " + timer.getTime());
		for (int i = array.length - k; i < array.length; i++ ) {
			System.out.print(array[i] + " ");
		}
		System.out.println();
		timer.reset();
		
		System.out.println("In Order: " + isInOrder(array));
		
		timer.start();
		MergeSort.sort(array);
		timer.stop();
		System.out.println("MergeSort (already sorted): " + timer.getTime());
		for (int i = array.length - k; i < array.length; i++ ) {
			System.out.print(array[i] + " ");
		}
	}
	
	public static void testMinHeap() {
		MinHeap<Integer> heap = new MinHeap<Integer>();
		heap.add(6).add(5).add(3).add(1).add(8).add(7).add(2).add(4);
		Integer[] array = new Integer[8];
		heap.toArray(array);
		for (int i : array) {
			System.out.print(i + " ");
		}
		System.out.println();
		
		System.out.println("Deleted min: " + heap.deleteMin());
		for (int i : heap.toArray()) {
			System.out.print(i + " ");
		}
		System.out.println();
		
		System.out.println("findMin() = " + heap.findMin());
		System.out.println("size() = " + heap.size());
		
		System.out.println("remove(2): " + heap.remove(2));
		
		for (int i : heap.toArray()) {
			System.out.print(i + " ");
		}
		System.out.println();
	}
	
	public static void testPermutations() {
		Integer[] array = {1, 2, 3, 4};
		
		List<Integer[]> result = Combination.permutations(array);
		
		for (Integer[] i : result) {
			for (int j = 0; j < i.length; j++)
				System.out.print(i[j] + " ");
			System.out.println();
		}
	}
	
	public static void testPivotOfRotated() {
		Integer[] array = {2, 3, 4, 5, 6, 7, 0, 1};
		System.out.println(Find.pivotOfRotated(array));
	}
	
	public static void testPoker() {
		String[] playerNames = {"James Chin", "Kaori Oda", "Keiko Kubota", "Hitoshi Sakimoto"};
		Poker poker = new Poker(playerNames);
		poker.startGame();
	}
	
	public static void testPositionOf() {
		Integer[] array = {0, 1, 2, 3, 4, 5, 6};
		System.out.println(Find.positionOf(array, 2));
	}
	
	public static void testPositionOfRotated() {
		Integer[] array = {2, 3, 4, 5, 6, 0, 1};
		System.out.println(Find.positionOfRotated(array, 0));
	}
	
	public static void testPowerSet() {
		Integer[] set = {1, 2, 3};
		Integer[][] powerset = Find.powerSet(set);
		
		for (Integer[] i : powerset) {
			System.out.print("[");
			for (Integer j : i)
				System.out.print(j);
			System.out.println("]");
		}
	}
	
	public static void testPowerSetRecursive() {
		Set<Integer> set = new HashSet<Integer>();
		set.add(1);
		set.add(2);
		set.add(3);
		
		Set<Set<Integer>> powerset = Find.powerSetRecursive(set);
		
		for (Set<Integer> s : powerset)
			System.out.println(s.toString());
	}
	
	public static void testPrimes() {
		int[] results = Find.primes(1000000);
		
		System.out.println(results.length);
	}
	
	public static void testProducts() {
		int[] factors = {2, 5, 1};
		int[] products = Find.products(factors, 10);
		
		for (int i : products) {
			System.out.println(i);
		}
	}
	
	public static void testQuickSort() {
		Timer timer = new Timer();
		Random random = new Random();
		int k = 10; // top k elements
		Integer[] array = new Integer[1000000];
		
		for (int i = 0; i < array.length; i++)
			array[i] = random.nextInt(Integer.MAX_VALUE);
		
		timer.start();
		QuickSort.sort(array);
		timer.stop();
		System.out.println("QuickSort: " + timer.getTime());
		for (int i = array.length - k; i < array.length; i++ ) {
			System.out.print(array[i] + " ");
		}
		System.out.println();
		timer.reset();
		
		System.out.println("In Order: " + isInOrder(array));
	}
	
	public static void testRadixSort() {
		Timer timer = new Timer();
		Random random = new Random();
		int k = 10; // top k elements
		int[] array = new int[1000000];
		
		for (int i = 0; i < array.length; i++)
			array[i] = random.nextInt(Integer.MAX_VALUE);
		
		timer.start();
		RadixSort.sort(array);
		timer.stop();
		System.out.println("RadixSort: " + timer.getTime());
		for (int i = array.length - k; i < array.length; i++ ) {
			System.out.print(array[i] + " ");
		}
		
		boolean inOrder = true;
		for (int i = 1; i < array.length; i++) {
			if (array[i] < array[i - 1]) {
				inOrder = false;
				break;
			}
		}
		System.out.println("\nIn Order: " + inOrder);
	}
	
	public static void testRadixSortAlphaNum() {
		String[] array = {"8-ball", "7 monkeys", "8 smart people", "cloud 9", "snuffleupagus", "Bad Cat", "Bad", "clouds", "apple", "bat", "a", "cat", "ball", "b"};
		RadixSortAlphaNum.sort(array);
		
		for (String s : array) {
			System.out.println(s);
		}
	}
	
	public static void testRadixSortLengthLexico() {
		String[] array = {"8-ball", "7 monkeys", "8 smart people", "cloud 9", "snuffleupagus", "Bad Cat", "Bad", "clouds", "apple", "bat", "a", "cat", "ball", "b"};
		RadixSortLengthLexico.sort(array);
		
		for (String s : array) {
			System.out.println(s);
		}
	}
	
	public static void testRadixSortLSD() {
		Timer timer = new Timer();
		Random random = new Random();
		int k = 10; // top k elements
		int[] array = new int[1000000];
		
		for (int i = 0; i < array.length; i++)
			array[i] = random.nextInt(Integer.MAX_VALUE);
		
		timer.start();
		RadixSortLSD.sort(array);
		timer.stop();
		System.out.println("RadixSortLSD: " + timer.getTime());
		for (int i = array.length - k; i < array.length; i++ ) {
			System.out.print(array[i] + " ");
		}
		
		boolean inOrder = true;
		for (int i = 1; i < array.length; i++) {
			if (array[i] < array[i - 1]) {
				inOrder = false;
				break;
			}
		}
		System.out.println("\nIn Order: " + inOrder);
	}
	
	public static void testRandomWeighted() {
		Timer timer = new Timer();
		int[] weights = {3514, 6486, 1, 2, 7816, 2181};
		RandomWeighted rw = new RandomWeighted(weights);
		
		int[] rollTracker = new int[weights.length];
		int i;
		
		timer.start();
		for (i = 0; i < 2000000; i++) {
			rollTracker[rw.nextInt()]++;
		}
		timer.stop();
		
		System.out.println(i + " rolls (Time " + timer.getTime() + "):");
		for (int j : rollTracker) {
			System.out.print(j + " ");
		}
	}
	
	public static void testReplace() {
		String string = "oldWord oldString of oldWordoldWord with oldWoroldWord";
		
		// convert from string to array
		ArrayList<Character> array = new ArrayList<Character>();
		for (int i = 0; i < string.length(); i++) {
			array.add(string.charAt(i));
		}
		
		System.out.println(StringOps.replace(array, "oldWord", "newWord!!"));
		
		for (char c : array)
			System.out.print(c);
	}
	
	public static void testReverseSubstring() {
		String string = "oldWords";
		System.out.println(StringOps.reverseSubstring(string, 3, 7));
	}
	
	public static void testRightShift() {
		String[] array = {"zero", "one", "two", "three", "four", "five"};
		
		for (String s : array) {
			System.out.print(s + " ");
		}
		
		Miscellaneous.rightShift(array, 2);
		
		System.out.println();
		for (String s : array) {
			System.out.print(s + " ");
		}
	}
	
	public static void testRomanToInt() {
		System.out.println("MCMLIIV " + Find.romanToInt("MCMLIIV"));
		System.out.println("MCMXC " + Find.romanToInt("MCMXC"));
		System.out.println("mmviii " + Find.romanToInt("mmviii"));
		System.out.println("MMMMCMXCIX " + Find.romanToInt("MMMMCMXCIX"));
	}
	
	public static void testSearchSortedMatrix() {
		Integer[][] matrix = {{1, 4, 7, 11},
				{2, 5, 8, 12},
				{3, 6, 10, 16},
				{10, 13, 14, 17}};
		
		System.out.println(Find.searchSortedMatrix(matrix, 9));
	}
	
	public static void testSelectionSort() {
		Integer[] array = {2, 1, 8, 7, 5, 0};
		SelectionSort.sort(array, 1, 4);
		
		for (int i : array) {
			System.out.print(i + " ");
		}
	}
	
	public static void testStringOpsPermutations() {
		String string = "caab";
		String[] array = StringOps.permutations(string);
		
		for (String s : array)
			System.out.println(s);
	}
	
	public static void testSubsets() {
		Integer[] array = {3, 1, 0, 1, 3, 11};
		Find.subsets(array);
	}
	
	public static void testSubstring() {
		String string = "ABC ABCDAB ABCDABCDABDE";
		String substr = "ABCDABD";
		System.out.println(StringOps.substring(string, substr));
	}
	
	public static void testTimer() {
		Timer timer = new Timer();
		System.out.println(timer.getTime());
		
		timer.start();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		timer.stop();
		
		System.out.println("stop() approx 5 secs later:");
		System.out.println(timer.getTime());
		
		timer.start();
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("no stop() approx another 8 secs later:");
		System.out.println(timer.getTime());
		
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		timer.stop();
		
		System.out.println("stop() approx another 4 secs later:");
		System.out.println(timer.getTime());
		
		timer.reset();
		System.out.println("Reset:");
		System.out.println(timer.getTime());
	}
	
	public static void testTreeMap() {
		TreeMap<Integer, String> bst = new TreeMap<Integer, String>();

		bst.put(7, "seven");
		bst.put(4, "four");
		bst.put(3, "three");
		bst.put(6, "six");
		bst.put(5, "five");
		bst.put(11, "eleven");
		bst.put(12, "twelve");
		bst.put(9, "nine");
		System.out.println(bst.put(9, "newNine"));
		bst.put(10, "ten");
		
		String[] list = bst.toArray();
		for (String s : list)
			System.out.print(s + " ");
		System.out.println();
		
		System.out.println("containsKey(-1): " + bst.containsKey(-1));
		System.out.println("containsKey(3): " + bst.containsKey(3));
		System.out.println("containsKey(4): " + bst.containsKey(4));
		System.out.println("size(): " + bst.size());
		System.out.println("isEmpty: " + bst.isEmpty());
		System.out.println("get(2): " + bst.get(2));
		System.out.println("get(6): " + bst.get(6));
		System.out.println("firstKey(): " + bst.firstKey());
		System.out.println("lastKey(): " + bst.lastKey());
		System.out.println("remove(7): " + bst.remove(7));
		System.out.println("remove(12): "+ bst.remove(12));
		System.out.println("remove(3): " + bst.remove(3));
		System.out.println("remove(5): " + bst.remove(5));
		System.out.println("remove(5): " + bst.remove(5));
		System.out.println("removeMin(): " + bst.removeMin());
		System.out.println("removeMax(): " + bst.removeMax());
		System.out.println("size(): " + bst.size());
		
		list = bst.toArray();
		for (String s : list)
			System.out.print(s + " ");
		System.out.println();
		
		System.out.println("get(7) = " + bst.get(7));
		System.out.println("get(9) = " + bst.get(9));
		System.out.println("get(10) = " + bst.get(10));
	}
	
	public static void testTreeSet() {
		TreeSet<Integer> bst = new TreeSet<Integer>();

		bst.add(7);
		bst.add(4);
		bst.add(3);
		bst.add(6);
		bst.add(5);
		bst.add(11);
		bst.add(12);
		bst.add(9);
		bst.add(10);
		
		Integer[] list = bst.toArray();
		for (int num : list)
			System.out.print(num + " ");
		System.out.println();
		
		System.out.println("contains(-1): " + bst.contains(-1));
		System.out.println("contains(3): " + bst.contains(3));
		System.out.println("add(4): " + bst.add(4));
		System.out.println("size(): " + bst.size());
		System.out.println("isEmpty: " + bst.isEmpty());
		System.out.println("get(0): " + bst.get(0));
		System.out.println("get(1): " + bst.get(1));
		System.out.println("get(2): " + bst.get(2));
		System.out.println("get(3): " + bst.get(3));
		System.out.println("get(4): " + bst.get(4));
		System.out.println("get(5): " + bst.get(5));
		System.out.println("get(6): " + bst.get(6));
		System.out.println("get(7): " + bst.get(7));
		System.out.println("get(8): " + bst.get(8));
		System.out.println("first(): " + bst.first());
		System.out.println("last(): " + bst.last());
		System.out.println("remove(7): " + bst.remove(7));
		System.out.println("remove(12): " + bst.remove(12));
		System.out.println("remove(3): " + bst.remove(3));
		System.out.println("remove(5): " + bst.remove(5));
		System.out.println("remove(5): " + bst.remove(5));
		System.out.println("removeMin(): " + bst.removeMin());
		System.out.println("removeMax(): " + bst.removeMax());
		System.out.println("Size: " + bst.size());
		
		list = bst.toArray();
		for (int num : list)
			System.out.print(num + " ");
	}
	
	public static void testTrie() {
		Trie trie = new Trie();
		System.out.println("isEmpty() = " + trie.isEmpty());
		trie.add("Google");
		trie.add("Google");
		trie.add("James Chin");
		trie.add("Jam");
		trie.add("Intelligence");
		trie.add("234.hjk.com/3h24.txt");
		
		System.out.println("contains(\"Google\") = " + trie.contains("Google"));
		System.out.println("contains(\"Intel\") = " + trie.contains("Intel"));
		System.out.println("contains(\"James\") = " + trie.contains("James"));
		System.out.println("contains(\"James Chin\") = " + trie.contains("James Chin"));
		System.out.println("remove(\"James\") = " + trie.remove("James"));
		System.out.println("remove(\"James Chin\") = " + trie.remove("James Chin"));
		
		for (String entry : trie.toArray()) {
			System.out.println(entry);
		}
		System.out.println("isEmpty() = " + trie.isEmpty());
		System.out.println("size() = " + trie.size());
	}
}