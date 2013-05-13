package poly.edu.wse;

import java.io.Serializable;

class __metadata implements Serializable{
	String uri;
	String type;
	@Override
	public String toString() {
		String tmp="";
		tmp=tmp+"\n\t"+uri;
		tmp=tmp+"\n\t"+type;
		return tmp;
	}
}
