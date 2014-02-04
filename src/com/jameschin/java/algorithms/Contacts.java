package com.jameschin.java.algorithms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Contacts
 * Space: O(n)
 * Insert: O(1)
 * Search Best: O(1)
 * Search Worst: O(n)
 * Delete Best: O(1)
 * Delete Worst: O(n) where n is the number of contacts.
 * @author: James Chin <jameslchin@gmail.com>
 */
public final class Contacts {
	private MinHeap<Integer> userIdMinHeap;
	private int nextUserId;
	private HashMap<Integer, Contact> userIdMap;
	private HashMap<String, HashSet<Contact>> displayNameMap;
	private HashMap<String, HashSet<Contact>> firstNameMap;
	private HashMap<String, HashSet<Contact>> lastNameMap;
	private HashMap<String, HashSet<Contact>> emailMap;
	private HashMap<String, HashSet<Contact>> phoneNumberMap;
	
	Contacts() {
		userIdMinHeap = new MinHeap<Integer>();
		userIdMinHeap.add(0);
		nextUserId = 1;
		
		userIdMap = new HashMap<Integer, Contact>();
		displayNameMap = new HashMap<String, HashSet<Contact>>();
		firstNameMap = new HashMap<String, HashSet<Contact>>();
		lastNameMap = new HashMap<String, HashSet<Contact>>();
		emailMap = new HashMap<String, HashSet<Contact>>();
		phoneNumberMap = new HashMap<String, HashSet<Contact>>();
	}
	
	private class Contact {
		int userId;
		String displayName;
		String firstName;
		String lastName;
		String email;
		String phoneNumber;
	}
	
	/**
	 * Adds a new contact into the database.
	 * @param displayName the new contact's display name.
	 * @param firstName the new contact's first name.
	 * @param lastName the new contact's last name.
	 * @param email the new contact's email address.
	 * @param phoneNumber the new contact's phone number.
	 * @return the unique integer ID assigned to the new contact.
	 */
	public int add(String displayName, String firstName, String lastName, String email, String phoneNumber) {
		// construct new contact
		Contact newContact = new Contact();
		newContact.userId = getNextUserId();
		newContact.displayName = displayName;
		newContact.firstName = firstName;
		newContact.lastName = lastName;
		newContact.email = email;
		newContact.phoneNumber = phoneNumber;
		
		// add pointer to newContact to each map
		userIdMap.put(newContact.userId, newContact);
		
		HashSet<Contact> temp;
		
		if (displayName != null) {
			temp = displayNameMap.get(displayName);
			if (temp == null) {
				temp = new HashSet<Contact>();
				displayNameMap.put(displayName, temp);
			}
			temp.add(newContact);
		}
		
		if (firstName != null) {
			temp = firstNameMap.get(firstName);
			if (temp == null) {
				temp = new HashSet<Contact>();
				firstNameMap.put(firstName, temp);
			}
			temp.add(newContact);
		}
		
		if (lastName != null) {
			temp = lastNameMap.get(lastName);
			if (temp == null) {
				temp = new HashSet<Contact>();
				lastNameMap.put(lastName, temp);
			}
			temp.add(newContact);
		}
		
		if (email != null) {
			temp = emailMap.get(email);
			if (temp == null) {
				temp = new HashSet<Contact>();
				emailMap.put(email, temp);
			}
			temp.add(newContact);
		}
		
		if (phoneNumber != null) {
			temp = phoneNumberMap.get(phoneNumber);
			if (temp == null) {
				temp = new HashSet<Contact>();
				phoneNumberMap.put(phoneNumber, temp);
			}
			temp.add(newContact);
		}
		
		return newContact.userId;
	}
	
	/**
	 * Returns the lowest unused integer available.
	 * @return the lowest unused integer available.
	 */
	private int getNextUserId() {
		if (userIdMinHeap.isEmpty())
			userIdMinHeap.add(nextUserId++);
		
		return userIdMinHeap.deleteMin();
	}
	
	/**
	 * Remove the contact from the database, if it exists.
	 * @param userId the user ID to remove.
	 * @return true if the contact was found in the database and removed, false otherwise.
	 */
	public boolean remove(int userId) {
		Contact contact = userIdMap.get(userId);
		
		if (contact == null)
			return false;
		
		userIdMap.remove(userId);
		
		if (contact.displayName != null)
			displayNameMap.get(contact.displayName).remove(contact);
		
		if (contact.firstName != null)
			firstNameMap.get(contact.firstName).remove(contact);
		
		if (contact.lastName != null)
			lastNameMap.get(contact.lastName).remove(contact);
		
		if (contact.email != null)
			emailMap.get(contact.email).remove(contact);
		
		if (contact.phoneNumber != null)
			phoneNumberMap.get(contact.phoneNumber).remove(contact);
		
		// return the user ID to be reused
		userIdMinHeap.add(userId);
		
		return true;
	}
	
	private Set<Contact> search(String displayName, String firstName, String lastName, String email, String phoneNumber) {
		Set<Contact> result = null;
		Set<Contact> temp;
		Set<Contact> newResult;
		boolean flag = false;
		
		if (displayName != null) {
			result = displayNameMap.get(displayName);
			if (result == null)
				return null;
			flag = true;
		}
			
		if (firstName != null) {
			if (flag == false) {
				result = firstNameMap.get(firstName);
				if (result == null)
					return null;
				flag = true;
			} else {
				temp = firstNameMap.get(firstName);
				if (temp == null)
					return null;
				newResult = new HashSet<Contact>();
				for (Contact c : result) {
					if (temp.contains(c))
						newResult.add(c);
				}
				result = newResult;
			}
		}
		
		if (lastName != null) {
			if (flag == false) {
				result = lastNameMap.get(lastName);
				if (result == null)
					return null;
				flag = true;
			} else {
				temp = lastNameMap.get(lastName);
				if (temp == null)
					return null;
				newResult = new HashSet<Contact>();
				for (Contact c : result) {
					if (temp.contains(c))
						newResult.add(c);
				}
				result = newResult;
			}
		}
		
		if (email != null) {
			if (flag == false) {
				result = emailMap.get(email);
				if (result == null)
					return null;
				flag = true;
			} else {
				temp = emailMap.get(email);
				if (temp == null)
					return null;
				newResult = new HashSet<Contact>();
				for (Contact c : result) {
					if (temp.contains(c))
						newResult.add(c);
				}
				result = newResult;
			}
		}
		
		if (phoneNumber != null) {
			if (flag == false) {
				result = phoneNumberMap.get(phoneNumber);
				if (result == null)
					return null;
				flag = true;
			} else {
				temp = phoneNumberMap.get(phoneNumber);
				if (temp == null)
					return null;
				newResult = new HashSet<Contact>();
				for (Contact c : result) {
					if (temp.contains(c))
						newResult.add(c);
				}
				result = newResult;
			}
		}
		
		return result;
	}
	
	public void printSearch(String displayName, String firstName, String lastName, String email, String phoneNumber) {
		Set<Contact> result = search(displayName, firstName, lastName, email, phoneNumber);
		
		if (result == null || result.isEmpty()) {
			System.out.println("No results found.");
			return;
		}
		
		for (Contact c : result) {
			System.out.print(c.userId + " ");
			System.out.print(c.displayName + " ");
			System.out.print(c.firstName + " ");
			System.out.print(c.lastName + " ");
			System.out.print(c.email + " ");
			System.out.print(c.phoneNumber + " ");
			System.out.println();
		}
	}
	
	public void printContacts() {
		for (Map.Entry<Integer, Contact> entry : userIdMap.entrySet()) {
			System.out.print(entry.getKey() + " ");
			System.out.print(entry.getValue().displayName + " ");
			System.out.print(entry.getValue().firstName + " ");
			System.out.print(entry.getValue().lastName + " ");
			System.out.print(entry.getValue().email + " ");
			System.out.print(entry.getValue().phoneNumber + " ");
			System.out.println();
		}
	}
}