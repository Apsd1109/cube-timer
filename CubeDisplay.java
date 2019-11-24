/*
 * Author: Anda Su
 * Teacher: J. Radulovic
 * Jun. 17, 2019
 * This is responsible for taking the current scramble and generating a 2D array of characters to represent the final scrambled state
 */

import java.util.Arrays;

public class CubeDisplay {
	private char[][] w;
	private char[][] y;
	private char[][] g;
	private char[][] b;
	private char[][] r;
	private char[][] o;
	private int faceSize = 3;
	private char[] temp1;
	private char[] temp2;
	private char[] temp3;
	private char[] temp4;
	private char[] temp5;
	private char[] temp6;
	private char[] temp7;
	private char[] temp8;
	
	public int getFaceSize() { // Returns the current cube size
		return faceSize;
	}
	
	public void setFaceSize(int faceSize) { // Sets the cube size to be of size 2x2-5x5
		this.faceSize = faceSize;
		reset();
	}
	
	public void reset(){ // Resets the cube, preparing it to be scrambled
		w = new char[faceSize][faceSize];
		y = new char[faceSize][faceSize];
		g = new char[faceSize][faceSize];
		b = new char[faceSize][faceSize];
		r = new char[faceSize][faceSize];
		o = new char[faceSize][faceSize];
		for(int i = 0; i < faceSize; i++){
			for(int j = 0; j < faceSize; j++){
				w[i][j] = 'w';
				y[i][j] = 'y';
				g[i][j] = 'g';
				b[i][j] = 'b';
				r[i][j] = 'r';
				o[i][j] = 'o';
			}
		}
		clear();
	}
	
	public void clear(){ // Clears the temporary arrays to be used for scrambling
		temp1 = new char[faceSize];
		temp2 = new char[faceSize];
		temp3 = new char[faceSize];
		temp4 = new char[faceSize];
		temp5 = new char[faceSize];
		temp6 = new char[faceSize];
		temp7 = new char[faceSize];
		temp8 = new char[faceSize];
	}
	
	public void scramble(String[] scram){ // Takes the scramble that was generated (array of Strings) and scrambles the cube accordingly, just moves the layers involved in the movement
		reset();
		int k;
		for(int i = 0; i < scram.length; i++){
			clear();
			
			// UP MOVES
			if(scram[i].contains("U")) { // STORING THE ARRAYS INTO TEMP VARIABLES
				temp1 = g[0].clone();
				temp2 = r[0].clone();
				temp3 = b[0].clone();
				temp4 = o[0].clone();
				if(scram[i].contains("w")) {
					temp5 = g[1].clone();
					temp6 = r[1].clone();
					temp7 = b[1].clone();
					temp8 = o[1].clone();
				}
				
				if(scram[i].contains("\'")) { // U'
					g[0] = temp4;
					r[0] = temp1;
					b[0] = temp2;
					o[0] = temp3;
					if(scram[i].contains("w")) { // Uw'
						g[1] = temp8;
						r[1] = temp5;
						b[1] = temp6;
						o[1] = temp7;
					}
					w = ccwFace(w);
				}
				else if(scram[i].contains("2")) { // U2
					g[0] = temp3;
					r[0] = temp4;
					b[0] = temp1;
					o[0] = temp2;
					if(scram[i].contains("w")) { // Uw2
						g[1] = temp7;
						r[1] = temp8;
						b[1] = temp5;
						o[1] = temp6;
					}
					w = cw2Face(w);
				}
				else { // U
					g[0] = temp2;
					r[0] = temp3;
					b[0] = temp4;
					o[0] = temp1;
					if(scram[i].contains("w")) { // Uw
						g[1] = temp6;
						r[1] = temp7;
						b[1] = temp8;
						o[1] = temp5;
					}
					w = cwFace(w);
				}
			}
			
			// DOWN MOVES
			if(scram[i].contains("D")) { // STORING THE ARRAYS INTO TEMP VARIABLES
				temp1 = g[faceSize-1].clone();
				temp2 = o[faceSize-1].clone();
				temp3 = b[faceSize-1].clone();
				temp4 = r[faceSize-1].clone();
				if(scram[i].contains("w")) {
					temp5 = g[faceSize-2].clone();
					temp6 = o[faceSize-2].clone();
					temp7 = b[faceSize-2].clone();
					temp8 = r[faceSize-2].clone();
				}
				
				if(scram[i].contains("\'")) { // D'
					g[faceSize-1] = temp4;
					o[faceSize-1] = temp1;
					b[faceSize-1] = temp2;
					r[faceSize-1] = temp3;
					if(scram[i].contains("w")) { // Dw'
						g[faceSize-2] = temp8;
						o[faceSize-2] = temp5;
						b[faceSize-2] = temp6;
						r[faceSize-2] = temp7;
					}
					y = ccwFace(y);
				}
				else if(scram[i].contains("2")) { // D2
					g[faceSize-1] = temp3;
					o[faceSize-1] = temp4;
					b[faceSize-1] = temp1;
					r[faceSize-1] = temp2;
					if(scram[i].contains("w")) { // Dw2
						g[faceSize-2] = temp7;
						o[faceSize-2] = temp8;
						b[faceSize-2] = temp5;
						r[faceSize-2] = temp6;
					}
					y = cw2Face(y);
				}
				else { // D
					g[faceSize-1] = temp2;
					o[faceSize-1] = temp3;
					b[faceSize-1] = temp4;
					r[faceSize-1] = temp1;
					if(scram[i].contains("w")) { // Dw
						g[faceSize-2] = temp6;
						o[faceSize-2] = temp7;
						b[faceSize-2] = temp8;
						r[faceSize-2] = temp5;
					}
					y = cwFace(y);
				}
			}

			// LEFT MOVES
			if(scram[i].contains("L")) { // STORING THE ARRAYS INTO TEMP VARIABLES
				for(int j = 0; j < faceSize; j++){
					temp1[j] = g[j][0];
					temp2[j] = w[j][0];
					temp3[j] = b[j][faceSize-1];
					temp4[j] = y[j][0];
				}
				if(scram[i].contains("w")) {
					for(int j = 0; j < faceSize; j++){
						temp5[j] = g[j][1];
						temp6[j] = w[j][1];
						temp7[j] = b[j][faceSize-2];
						temp8[j] = y[j][1];
					}
				}
				k = faceSize-1;
				
				if(scram[i].contains("\'")) { // L'
					for(int j = 0; j < faceSize; j++){
						g[j][0] = temp4[j];
						w[j][0] = temp1[j];
						b[j][faceSize-1] = temp2[k];
						y[j][0] = temp3[k];
						k--;
					}
					if(scram[i].contains("w")) { // Lw'
						k = faceSize-1;
						for(int j = 0; j < faceSize; j++){
							g[j][1] = temp8[j];
							w[j][1] = temp5[j];
							b[j][faceSize-2] = temp6[k];
							y[j][1] = temp7[k];
							k--;
						}
					}
					o = ccwFace(o);
				}
				else if(scram[i].contains("2")) { // L2
					for(int j = 0; j < faceSize; j++){
						g[j][0] = temp3[k];
						w[j][0] = temp4[j];
						b[j][faceSize-1] = temp1[k];
						y[j][0] = temp2[j];
						k--;
					}
					if(scram[i].contains("w")) { // Lw2
						k = faceSize-1;
						for(int j = 0; j < faceSize; j++){
							g[j][1] = temp7[k];
							w[j][1] = temp8[j];
							b[j][faceSize-2] = temp5[k];
							y[j][1] = temp6[j];
							k--;
						}
					}
					o = cw2Face(o);
				}
				else { // L
					for(int j = 0; j < faceSize; j++){
						g[j][0] = temp2[j];
						w[j][0] = temp3[k];
						b[j][faceSize-1] = temp4[k];
						y[j][0] = temp1[j];
						k--;
					}
					if(scram[i].contains("w")) { // Lw
						k = faceSize-1;
						for(int j = 0; j < faceSize; j++){
							g[j][1] = temp6[j];
							w[j][1] = temp7[k];
							b[j][faceSize-2] = temp8[k];
							y[j][1] = temp5[j];
							k--;
						}
					}
					o = cwFace(o);
				}
			}
			
			//RIGHT MOVES
			if(scram[i].contains("R")) { // STORING THE ARRAYS INTO TEMP VARIABLES
				for(int j = 0; j < faceSize; j++){
					temp1[j] = g[j][faceSize-1];
					temp2[j] = w[j][faceSize-1];
					temp3[j] = b[j][0];
					temp4[j] = y[j][faceSize-1];
				}
				if(scram[i].contains("w")) {
					for(int j = 0; j < faceSize; j++){
						temp5[j] = g[j][faceSize-2];
						temp6[j] = w[j][faceSize-2];
						temp7[j] = b[j][1];
						temp8[j] = y[j][faceSize-2];
					}
				}
				k = faceSize-1;
				
				if(scram[i].contains("\'")) { // R'
					for(int j = 0; j < faceSize; j++){
						g[j][faceSize-1] = temp2[j];
						w[j][faceSize-1] = temp3[k];
						b[j][0] = temp4[k];
						y[j][faceSize-1] = temp1[j];
						k--;
					}
					if(scram[i].contains("w")) { // Rw'
						k = faceSize-1;
						for(int j = 0; j < faceSize; j++){
							g[j][faceSize-2] = temp6[j];
							w[j][faceSize-2] = temp7[k];
							b[j][1] = temp8[k];
							y[j][faceSize-2] = temp5[j];
							k--;
						}
					}
					r = ccwFace(r);
				}
				else if(scram[i].contains("2")) { // R2
					for(int j = 0; j < faceSize; j++){
						g[j][faceSize-1] = temp3[k];
						w[j][faceSize-1] = temp4[j];
						b[j][0] = temp1[k];
						y[j][faceSize-1] = temp2[j];
						k--;
					}
					if(scram[i].contains("w")) { // Rw2
						k = faceSize-1;
						for(int j = 0; j < faceSize; j++){
							g[j][faceSize-2] = temp7[k];
							w[j][faceSize-2] = temp8[j];
							b[j][1] = temp5[k];
							y[j][faceSize-2] = temp6[j];
							k--;
						}
					}
					r = cw2Face(r);
				}
				else { // R
					for(int j = 0; j < faceSize; j++){
						g[j][faceSize-1] = temp4[j];
						w[j][faceSize-1] = temp1[j];
						b[j][0] = temp2[k];
						y[j][faceSize-1] = temp3[k];
						k--;
					}
					if(scram[i].contains("w")) { // Rw
						k = faceSize-1;
						for(int j = 0; j < faceSize; j++){
							g[j][faceSize-2] = temp8[j];
							w[j][faceSize-2] = temp5[j];
							b[j][1] = temp6[k];
							y[j][faceSize-2] = temp7[k];
							k--;
						}
					}
					r = cwFace(r);
				}
			}
			
			//FRONT MOVES
			if(scram[i].contains("F")) { // STORING THE ARRAYS INTO TEMP VARIABLES
				for(int j = 0; j < faceSize; j++) {
					temp1[j] = o[j][faceSize-1];
					temp2[j] = w[faceSize-1][j];
					temp3[j] = r[j][0];
					temp4[j] = y[0][j];
				}
				if(scram[i].contains("w")) {
					for(int j = 0; j < faceSize; j++) {
						temp5[j] = o[j][faceSize-2];
						temp6[j] = w[faceSize-2][j];
						temp7[j] = r[j][1];
						temp8[j] = y[1][j];
					}
				}
				k = faceSize-1;
				
				if(scram[i].contains("\'")) { // F'
					for(int j = 0; j < faceSize; j++) {
						o[j][faceSize-1] = temp2[k];
						w[faceSize-1][j] = temp3[j];
						r[j][0] = temp4[k];
						y[0][j] = temp1[j];
						k--;
					}
					if(scram[i].contains("w")) { // Fw'
						k = faceSize-1;
						for(int j = 0; j < faceSize; j++) {
							o[j][faceSize-2] = temp6[k];
							w[faceSize-2][j] = temp7[j];
							r[j][1] = temp8[k];
							y[1][j] = temp5[j];
							k--;
						}
					}
					g = ccwFace(g);
				}
				else if(scram[i].contains("2")) { // F2
					for(int j = 0; j < faceSize; j++) {
						o[j][faceSize-1] = temp3[k];
						w[faceSize-1][j] = temp4[k];
						r[j][0] = temp1[k];
						y[0][j] = temp2[k];
						k--;
					}
					if(scram[i].contains("w")) { // Fw2
						k = faceSize-1;
						for(int j = 0; j < faceSize; j++) {
							o[j][faceSize-2] = temp7[k];
							w[faceSize-2][j] = temp8[k];
							r[j][1] = temp5[k];
							y[1][j] = temp6[k];
							k--;
						}
					}
					g = cw2Face(g);
				}
				else { // F
					for(int j = 0; j < faceSize; j++) {
						o[j][faceSize-1] = temp4[j];
						w[faceSize-1][j] = temp1[k];
						r[j][0] = temp2[j];
						y[0][j] = temp3[k];
						k--;
					}
					if(scram[i].contains("w")) { // Fw
						k = faceSize-1;
						for(int j = 0; j < faceSize; j++) {
							o[j][faceSize-2] = temp8[j];
							w[faceSize-2][j] = temp5[k];
							r[j][1] = temp6[j];
							y[1][j] = temp7[k];
							k--;
						}
					}
					g = cwFace(g);
				}
			}
			
			
			//BACK MOVES
			if(scram[i].contains("B")) { // STORING THE ARRAYS INTO TEMP VARIABLES
				for(int j = 0; j < faceSize; j++) {
					temp1[j] = o[j][0];
					temp2[j] = w[0][j];
					temp3[j] = r[j][faceSize-1];
					temp4[j] = y[faceSize-1][j];
				}
				if(scram[i].contains("w")) {
					for(int j = 0; j < faceSize; j++) {
						temp5[j] = o[j][1];
						temp6[j] = w[1][j];
						temp7[j] = r[j][faceSize-2];
						temp8[j] = y[faceSize-2][j];
					}
				}
				k = faceSize-1;
				
				if(scram[i].contains("\'")) { // B'
					for(int j = 0; j < faceSize; j++) {
						o[j][0] = temp4[j];
						w[0][j] = temp1[k];
						r[j][faceSize-1] = temp2[j];
						y[faceSize-1][j] = temp3[k];
						k--;
					}
					if(scram[i].contains("w")) { // Bw'
						k = faceSize-1;
						for(int j = 0; j < faceSize; j++) {
							o[j][1] = temp8[j];
							w[1][j] = temp5[k];
							r[j][faceSize-2] = temp6[j];
							y[faceSize-2][j] = temp7[k];
							k--;
						}
					}
					b = ccwFace(b);
				}
				else if(scram[i].contains("2")) { // B2
					for(int j = 0; j < faceSize; j++) {
						o[j][0] = temp3[k];
						w[0][j] = temp4[k];
						r[j][faceSize-1] = temp1[k];
						y[faceSize-1][j] = temp2[k];
						k--;
					}
					if(scram[i].contains("w")) { // Bw2
						k = faceSize-1;
						for(int j = 0; j < faceSize; j++) {
							o[j][1] = temp7[k];
							w[1][j] = temp8[k];
							r[j][faceSize-2] = temp5[k];
							y[faceSize-2][j] = temp6[k];
							k--;
						}
					}
					b = cw2Face(b);
				}
				else { // B
					for(int j = 0; j < faceSize; j++) {
						o[j][0] = temp2[k];
						w[0][j] = temp3[j];
						r[j][faceSize-1] = temp4[k];
						y[faceSize-1][j] = temp1[j];
						k--;
					}
					if(scram[i].contains("w")) { // Bw
						k = faceSize-1;
						for(int j = 0; j < faceSize; j++) {
							o[j][1] = temp6[k];
							w[1][j] = temp7[j];
							r[j][faceSize-2] = temp8[k];
							y[faceSize-2][j] = temp5[j];
							k--;
						}
					}
					b = cwFace(b);
				}
			}
		}
	}
	
	
	public char[][] cwFace(char[][] face){ // Rotates the face CW
		clear();
		temp5 = face[0].clone();
		temp6 = face[faceSize-1].clone();
		if(faceSize > 3){
			temp7 = face[1].clone();
			temp8 = face[faceSize-2].clone();
		}
		int j = faceSize-1;
		for(int i = 0; i < faceSize; i++){
			temp1[i] = face[j][0]; 
			temp2[i] = face[j][faceSize-1];
			if(faceSize>3){
				temp3[i] = face[j][1];
				temp4[i] = face[j][faceSize-2];
			}
			j--;
		}
		
		face[0] = temp1;
		face[faceSize-1] = temp2;
		for(int i = 0; i < faceSize; i++){
			face[i][faceSize-1] = temp5[i];
			face[i][0] = temp6[i];
		}
		
		// FOR 4x4 OR 5x5 CUBES, SWAPS INNER LAYERS
		if(faceSize>3){
			face[1] = temp3;
			face[faceSize-2] = temp4;
			for(int i = 0; i < faceSize; i++){
				face[i][faceSize-2] = temp7[i];
				face[i][1] = temp8[i];
			}
		}
		return face;
	}
	
	public char[][] ccwFace(char[][] face){ // Rotates the face CCW
		clear();
		temp5 = face[0].clone();
		temp6 = face[faceSize-1].clone();
		if(faceSize > 3){
			temp7 = face[1].clone();
			temp8 = face[faceSize-2].clone();
		}
		for(int i = 0; i < faceSize; i++){
			temp1[i] = face[i][0];
			temp2[i] = face[i][faceSize-1];
			if(faceSize>3){
				temp3[i] = face[i][1];
				temp4[i] = face[i][faceSize-2];
			}
		}
		
		face[0] = temp2;
		face[faceSize-1] = temp1;
		int j = faceSize-1;
		for(int i = 0; i < faceSize; i++){
			face[i][faceSize-1] = temp6[j];
			face[i][0] = temp5[j];
			j--;
		}
		
		// FOR 4x4 OR 5x5 CUBES, SWAPS INNER LAYERS
		if(faceSize>3){
			face[1] = temp4;
			face[faceSize-2] = temp3;
			j = faceSize-1;
			for(int i = 0; i < faceSize; i++){
				face[i][faceSize-2] = temp8[j];
				face[i][1] = temp7[j];
				j--;
			}
		}
		return face;
	}
	
	public char[][] cw2Face(char[][] face){ // Rotates face 180
		clear();
		temp1 = face[0].clone();
		temp2 = face[faceSize-1].clone();
		if(faceSize>3) {
			temp3 = face[1].clone();
			temp4 = face[faceSize-2].clone();
		}
		for(int i = 0; i < faceSize; i++) {
			temp5[i] = face[i][0];
			temp6[i] = face[i][faceSize-1];
			if(faceSize>3) {
				temp7[i] = face[i][1];
				temp8[i] = face[i][faceSize-2];
			}
		}
		
		int j = faceSize-1;
		for(int i = 0; i < faceSize; i++) {
			face[0][i] = temp2[j];
			face[faceSize-1][i] = temp1[j];
			face[i][0] = temp6[j];
			face[i][faceSize-1] = temp5[j];
			j--;
		}
		
		// FOR 4x4 OR 5x5 CUBES, SWAPS INNER LAYERS
		if(faceSize>3) { 
			j = faceSize-1;
			for(int i = 0; i < faceSize; i++) {
				face[1][i] = temp4[j];
				face[faceSize-2][i] = temp3[j];
				face[i][1] = temp8[j];
				face[i][faceSize-2] = temp7[j];
				j--;
			}
		}
		return face;
	}
	
	public String displayCube(){ // Returns a String of the scrambled cube as a representation of a 2D array (net of a cube)
		String ret = "";
		for(int i = 0; i < faceSize; i++){
			ret = ret + "\t\t\t";
			ret = ret + Arrays.toString(w[i]);
			ret = ret + "\n";
		}
		ret = ret + "\n";
		for(int i = 0; i < faceSize; i++) {
			ret = ret + Arrays.toString(o[i]) + "\t\t" + Arrays.toString(g[i]) + "\t\t" + Arrays.toString(r[i]) + "\t\t" + Arrays.toString(b[i]);
			ret = ret + "\n";
		}
		ret = ret + "\n";
		for(int i = 0; i < faceSize; i++){
			ret = ret + "\t\t\t";
			ret = ret + Arrays.toString(y[i]);
			ret = ret + "\n";
		}
		return ret;
	}
	
}
