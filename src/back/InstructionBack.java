package back;

import java.util.Observable;

import principal.CONFIG;

public class InstructionBack extends Observable  {

	
	protected String []txt;
	protected int x, y;
	protected Time tim;
	
	public InstructionBack(int x, int y, String []txt) {
		this.x=x;
		this.y=y;
		this.txt=txt;
		tim= new Time();
		tim.Count(1);
	}	
	
	protected boolean endTime(){
		if(tim.getSeconds()>CONFIG.instructionTime){			
			setChanged();
			notifyObservers();
			clearChanged();			
			return true;	
		}		
		return false;		
	}
	
	public void clear(){
		x=0;
		y=0;
		tim=null;
		txt=null;
	}
}
