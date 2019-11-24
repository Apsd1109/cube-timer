/*
 * Author: Anda Su
 * Teacher: J. Radulovic
 * Jun 17, 2019
 * This represents the data stored as one object
 * It is also given functionality to calculate the averages
 */

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.*;

public class Entry {
	private int entryNumber;
	private double time;
	private double ao5;
	private double ao12;
	private List<Double> ao5List = new ArrayList<Double>();
	private List<Double> ao12List = new ArrayList<Double>();
	private double[] ao5Array;
	private double [] ao12Array;
	private String comment;
	private String date;
	private Entry next;
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private double sum;
	
	public Entry(double time){ // This is the constructor for the Entry object at hand, setting its time value and date value
		this.time = time;
		date = simpleDateFormat.format(new Date());
	}
	
	//THESE ARE THE RESPECTIVE GETTER AND SETTER METHODS
	
	public int getEntryNumber() {
		return entryNumber;
	}
	
	public void setEntryNumber(int entryNumber) {
		this.entryNumber = entryNumber;
	}
	
	public double getTime() {
		return time;
	}
	
	public void setTime(double time) {
		this.time = time;
	}
	
	public double getAo5() {
		return ao5;
	}
	
	public void setAo5(double ao5) {
		this.ao5 = ao5;
	}
	
	public double getAo12() {
		return ao12;
	}
	
	public void setAo12(double ao12) {
		this.ao12 = ao12;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}

	public List<Double> getAo5List() {
		return ao5List;
	}

	public void setAo5List(List<Double> ao5List) {
		this.ao5List = ao5List;
	}

	public List<Double> getAo12List() {
		return ao12List;
	}

	public void setAo12List(List<Double> ao12List) {
		this.ao12List = ao12List;
	}
	
	public Entry getNext() {
		return next;
	}

	public void setNext(Entry next) {
		this.next = next;
	}
	
	public void updateAo5() { // After the array of times for the average of 5 is sorted, it is calculated, removes the worst and best times out of the calculation
		if(entryNumber < 5){
			ao5 = 0.0;
		}
		else{
			ao5Array = ArrayUtils.toPrimitive(ao5List.toArray(new Double[5]));
			sort(ao5Array);
			sum = 0;
			for(int i = 1; i < 4; i++) {
				sum+= ao5Array[i];
			}
			ao5 = Math.round(1000*sum/3)/1000.0;
		}
	}
	
	public void updateAo12() { // After the array of times for the average of 12 is sorted, it is calculated, removes the worst and best times out of the calculation
		if(entryNumber < 12){
			ao12 = 0.0;
		}
		else{
			ao12Array = ArrayUtils.toPrimitive(ao12List.toArray(new Double[12]));
			sort(ao12Array);
			sum = 0;
			for(int i = 1; i < 11; i++) {
				sum+= ao12Array[i];
			}
			ao12 = Math.round(1000*sum/10)/1000.0;
		}
	}
	
	public void sort(double[] list) { // This sorts the array (either average of 5 or 12) so that it can be used for calculating the averages, uses insertion sort
		double temp;
		for(int i = 0; i < list.length; i++){
			for(int j = i+1; j < list.length; j++){
				if(list[i] > list[j]){
					temp = list[i];
					list[i] = list[j];
					list[j] = temp;
				}
			}
		}
	}
	 
}
