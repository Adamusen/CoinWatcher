import java.io.*;
import java.net.*;

public class CoinWarz {	
	
	public static double BTCprice = 0;
	
	public static class CWdata {
    	private String name;
    	private double netdiff;
    	private double estc;
    	private double excrate;
    	private double prof;
    	private double profB;
    	
    	public CWdata() { }
    	
    	public CWdata(String name, double netdiff, double estc, double excrate, double prof, double profB) {
    		this.name = name;
    		this.netdiff = netdiff;
    		this.estc = estc;
    		this.excrate = excrate;
    		this.prof = prof;
    		this.profB = profB;
    	}

    	public String getName() { return name; }
    	public double getnetdiff() { return netdiff; }
    	public double getestc() { return estc; }
    	public double getexcrate() { return excrate; }
    	public double getprof() { return prof; }
    	public double getprofB() { return profB; }

    	public void setName(String name) { this.name = name; }
    	public void setnetdiff(double netdiff) { this.netdiff = netdiff; }
    	public void setestc(double estc) { this.estc = estc; }
    	public void setexcrate(double excrate) { this.excrate = excrate; }
    	public void setprof(double prof) { this.prof = prof; }
    	public void setprofB(double profB) { this.profB = profB; }
    	
    	public void clear() {
    		name = "";
    		netdiff = 0;
    		estc = 0;
    		excrate = 0;
    		prof = 0;
    		profB = 0;
    	}
    }
	
	public static void getcwdata() {
    	try {
    		URL coinwarz = new URL("http://www.coinwarz.com/cryptocurrency/?sha256hr=1000.00&sha256p=500.00&sha256pc=0.1000&scrypthr=1000.00&scryptp=500.00&scryptpc=0.1800&scryptnhr=450&scryptnp=0.00&scryptnpc=0.0000&sha256c=false&scryptc=true&e=Bitstamp");
    		URLConnection yc = coinwarz.openConnection();
    		BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
    		String inputLine, str, Cname="";
    		int index,length,k=-2;
    		double dv, Cnetdiff=0, Cestc=0, Cexcrate=0, Cprof=0, CprofB=0;
    		char ch;
       		
    		Watcher2.CWdataList.clear();
    		
    		while ((inputLine = in.readLine()) != null) {
    			if (k==-1) {
    				str=inputLine.replaceAll(" ", "");
    				index=str.indexOf(">$");
    				length=str.indexOf("</span>", index);
    				dv=Double.parseDouble(str.substring(index+2, length));
    				BTCprice=dv;
    				k++;
    			}   			
    			
    			if (k==-2) {
    				if (inputLine.contains("Bitstamp.png") ) {
    					k++;
    				}   				
    			}
    			
    			if (k==0) {
    				index=inputLine.indexOf(")</b></a>");
    				if (index!=-1) {
    					length=0;           	
    					do {
    						length++;
    						ch=inputLine.charAt(index-1-length);           		
    					} while (ch!='(');
    					str=inputLine.substring(index-length, index);
    					Cname=str;
    					k++;
    				}   				
    			}
    			
    			if (k==1) {
    				index=inputLine.indexOf("<span class=\"bold\">");
    				if (index!=-1) {
    					length=0;
    					do {
    						length++;
    						ch=inputLine.charAt(index+19+length);           		
    					} while (ch!='<');
    					str=inputLine.substring(index+19, index+19+length);
    					str=str.replaceAll(",", "");
    					dv=Double.parseDouble(str);
    					Cnetdiff=dv;
    					k++;
    				}
    			}

    			if (k==3) {
    				index=inputLine.indexOf("/");
    				str=inputLine.substring(0, index);    					
    				str=str.replaceAll(" ", "");
    				str=str.replaceAll(",", "");
    				Cestc=Double.parseDouble(str);
    				k++;				
    			}
    			
    			if (k==2) {
    				if (inputLine.contains("<td style=\"min-width: 100px; width: 100px;\">")) {
    					k++;
    				}
    			}
    			
    			if (k==4) {
    				index=inputLine.indexOf("class=\"link\"><b>");
    				if (index!=-1) {
    					length=0;
    					do {
    						length++;
    						ch=inputLine.charAt(index+16+length);           		
    					} while (ch!='<');
    					str=inputLine.substring(index+16, index+16+length);
    					str=str.replaceAll(",", "");
    					dv=Double.parseDouble(str);
    					Cexcrate=dv;
    					k++;
    				}
    			}
    			
    			if (k==5) {
    				index=inputLine.indexOf("$");
    				if (index!=-1) {
    					length=0;
    					do {
    						length++;
    						ch=inputLine.charAt(index+1+length);
    					} while (ch!='<' && ch!=' ');
    					str=inputLine.substring(index+1, index+1+length);
    					str=str.replaceAll(",", "");
    					dv=Double.parseDouble(str);
    					Cprof=dv;
    					k++;
    				}
    			}
    			
    			if (k==7) {    						
    				str=inputLine.replaceAll(",", "");
    				str=str.replaceAll(",", "");
    				dv=1/Double.parseDouble(str);
    				CprofB=dv; 				
    				Watcher2.CWdataList.add(new CoinWarz.CWdata(Cname,Cnetdiff,Cestc,Cexcrate,Cprof,CprofB));
    				Cname="";
    				Cnetdiff=0;
    				Cestc=0;
    				Cexcrate=0;
    				Cprof=0;
    				CprofB=0;
    				
    				k=0;
    			}
    			
    			if (k==6) {   				
    				if (inputLine.contains("<td class=\"btcDays\" style=\"min-width: 78px; width: 78px;\">") ) {
    					k++;
    				}
    		
    			}
    			
    		}	   	
    	
    	} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Couldnt connect to Coinwarz.com");
    	}

    }
	
	public static void printcwcode() {
		try {
			URL coinwarz = new URL("http://www.coinwarz.com/cryptocurrency/?sha256hr=1000.00&sha256p=500.00&sha256pc=0.1000&scrypthr=1000.00&scryptp=500.00&scryptpc=0.1800&scryptnhr=450&scryptnp=0.00&scryptnpc=0.0000&sha256c=false&scryptc=true&e=Bitstamp");
			URLConnection yc = coinwarz.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			String inputLine;
			BufferedWriter writer = new BufferedWriter(new FileWriter("coinwarz.txt"));
			while ((inputLine = in.readLine()) != null) { 
				writer.write(inputLine);
				writer.newLine();
			}
			in.close();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Couldnt connect to Coinwarz.com or write to txt file.");
		}	
	}

}
