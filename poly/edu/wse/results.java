package poly.edu.wse;

import java.io.Serializable;

class results implements Serializable{
	__metadata __metadata;
	String ID;
	String Title;
	String Description;
	String DisplayUrl;
	String Url;
	
	/*@Override
	public String toString() {
		String tmp="";
		tmp=tmp+"\n    "+"Title:      "+Title;
		tmp=tmp+"\n    "+"Description:"+Description;
		tmp=tmp+"\n    "+"DisplayUrl: "+DisplayUrl;
		tmp=tmp+"\n    "+"Url:        "+Url;
		return tmp;
	}*/
	
	public String toString() {
		String tmp="";
		tmp=tmp+"<.,.13>"+Title;
		tmp=tmp+"<.,.13>"+Description;
		tmp=tmp+"<.,.13>"+DisplayUrl;
		tmp=tmp+"<.,.13>"+Url;
		return tmp;
	}
}
