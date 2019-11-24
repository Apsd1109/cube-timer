/*
 * Author: Anda Su
 * Teacher: J. Radulovic
 * Jun. 17, 2019
 * Implementation of the list ADT
 * Whenever the list is updated (either an element is added or removed), it will check for changes to averages, calculate, and update them
 */

import java.util.ArrayList;
import java.util.List;

public class Listz implements ListADT<Entry> {
	private int listSize = 0;
	private Entry root;
	private Entry temp;
	private List<Entry> listOfEntries = new ArrayList<Entry>();
	
	public void add(Entry e) { // Adds an element into the list, and updates the averages
		e.setEntryNumber(listSize+1);
		if(listSize == 0){
			root = e;
		}
		else{
			temp = get(listSize-1);
			temp.setNext(e);
		}
		listSize++;
		listOfEntries.add(e);
		updateAverages();
	}

	public void remove(int i) { // Removes an element at the specified index in the list, updates the averages
		temp = root;
		if(i == 0){
			root = temp.getNext();
			temp = root;
			for(int j = 1; j < listSize; j++) {
				temp.setEntryNumber(temp.getEntryNumber()-1);
				temp = temp.getNext();
			}
		}
		else{
			Entry tempPrev = root;
			for(int j = 0; j < i; j++){
				temp = temp.getNext();
			}
			for(int j = 0; j < i-1; j++){
				tempPrev = tempPrev.getNext();
			}
			tempPrev.setNext(temp.getNext());
			temp.setNext(null);
			for(int j = i; j < listSize-1; j++) {
				tempPrev = tempPrev.getNext(); 
				tempPrev.setEntryNumber(tempPrev.getEntryNumber()-1);
			}
		}
		listSize--;
		listOfEntries.remove(i);
		updateAverages();
	}
	
	public void removeLatest() { // Removes the latest entry in the list, updates the averages
		temp = get(listSize-2);
		temp.setNext(null);
		listSize--;
		listOfEntries.remove(listSize);
	}
	
	public void removeAll(){ // Removes all entries in the list
		root = null;
		listSize = 0;
		listOfEntries.removeAll(listOfEntries);
	}

	public Entry get(int i) { // Returns an Entry object
		temp = root;
		for(int j = 0; j < i; j++){
			temp = temp.getNext();
		}
		return temp;
	}

	public int size() { // Returns the size of the list as an integer value
		return listSize;
	}
	
	public void updateAverages(){ // Adds the time values into each Entry object's lists of averages, and then calculates the average by calling each Entry object's method to do so
		if(listSize >= 5){
			for(int i = listSize-1; i >= 4; i--){
				listOfEntries.get(i).getAo5List().clear();
				listOfEntries.get(i).getAo5List().add(listOfEntries.get(i).getTime());
				listOfEntries.get(i).getAo5List().add(listOfEntries.get(i-1).getTime());
				listOfEntries.get(i).getAo5List().add(listOfEntries.get(i-2).getTime());
				listOfEntries.get(i).getAo5List().add(listOfEntries.get(i-3).getTime());
				listOfEntries.get(i).getAo5List().add(listOfEntries.get(i-4).getTime());
			}
		}
		if(listSize >= 12){
			for(int i = listSize-1; i >= 11; i--){
				listOfEntries.get(i).getAo12List().clear();
				listOfEntries.get(i).getAo12List().add(listOfEntries.get(i).getTime());
				listOfEntries.get(i).getAo12List().add(listOfEntries.get(i-1).getTime());
				listOfEntries.get(i).getAo12List().add(listOfEntries.get(i-2).getTime());
				listOfEntries.get(i).getAo12List().add(listOfEntries.get(i-3).getTime());
				listOfEntries.get(i).getAo12List().add(listOfEntries.get(i-4).getTime());
				listOfEntries.get(i).getAo12List().add(listOfEntries.get(i-5).getTime());
				listOfEntries.get(i).getAo12List().add(listOfEntries.get(i-6).getTime());
				listOfEntries.get(i).getAo12List().add(listOfEntries.get(i-7).getTime());
				listOfEntries.get(i).getAo12List().add(listOfEntries.get(i-8).getTime());
				listOfEntries.get(i).getAo12List().add(listOfEntries.get(i-9).getTime());
				listOfEntries.get(i).getAo12List().add(listOfEntries.get(i-10).getTime());
				listOfEntries.get(i).getAo12List().add(listOfEntries.get(i-11).getTime());
			}
		}
		
		temp = root;
		for(int i = 0; i < listSize; i++){
			temp.updateAo5();
			temp.updateAo12();
			temp = temp.getNext();
		}
	}

}
