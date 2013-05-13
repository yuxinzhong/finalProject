package poly.edu.wse;

import java.io.Serializable;


class d implements Serializable{
	results[] results;
	String __next;
	
	@Override
	public String toString() {
		String tmp="";
		for(int i=0;i<results.length;i++){
			tmp=tmp+"!>>>>!"+"results"+(i+1)+":"+results[i].toString();
		}
		return tmp;
	}
	
}
