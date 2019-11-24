/*
 * Author: Anda Su
 * Teacher: J. Radulovic
 * Jun. 17, 2019
 * This generates the scrambles used for 2x2-5x5 cubes
 */

public class Scrambler {
	private int cubeSize = 3;
	private String[] scrambles1 = {"U","D","L","R","F","B"};
	private String[] scrambles2 = {"", "w"};
	private String[] scrambles3 = {" ", "\'", "2"};
	private int[] scrambleLength = {0, 0, 11, 20, 46, 60};
	private String[] scramble;
	
	public int getCubeSize() { // This returns the size of the cube thats being used for the scrambler
		return cubeSize;
	}
	
	public void setCubeSize(int cubeSize) { // This sets the size of the cube, since the scramble length and moves vary based on the size
		this.cubeSize = cubeSize;
	}
	
	public String[] getScramble() { // This only returns the array of Strings that represent the moves for the scramble
		return scramble;
	}
	
	public String[] generateScramble() { // This generates a scramble randomly, avoiding repetition of moves, and returns an array of Strings to be used by the CubeDisplay class to execute the moves.  
		scramble = new String[scrambleLength[cubeSize]];
		int r1 = -1;
		int rTemp = -1;
		for(int i = 0; i < scramble.length; i++) {
			while(r1 == rTemp){
				rTemp = (int)(Math.random()*6);
				if(rTemp != r1){
					r1 = rTemp;
					break;
				}
			}
			if(cubeSize < 4) {
				scramble[i] = scrambles1[r1] + scrambles3[(int)(Math.random()*3)];
			}
			else {
				scramble[i] = scrambles1[r1] + scrambles2[(int)(Math.random()*2)]  + scrambles3[(int)(Math.random()*3)];
			}
		}
		return scramble;
	}
	
	public String displayScramble(){ // This returns a String value formatted in a way that it displays nicely on the final program (with spacing and lines)
		generateScramble();
		String ret = "";
		for(int i = 0; i < scrambleLength[cubeSize]; i++){
			ret = ret + " " + scramble[i];
			if(i%27 == 26){
				ret = ret + "\n";
			}
		}
		return ret;
	}
}
