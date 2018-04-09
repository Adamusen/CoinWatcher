import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class DataP {
	
	public static class CoinBatdata {
    	private String name;
    	private String pooldat;
    	private String diffapi;
    	
    	public CoinBatdata() { }
    	
    	public CoinBatdata(String name, String pooldat, String diffapi) {
    		this.name = name;
    		this.pooldat = pooldat;
    		this.diffapi = diffapi;
    	}

    	public String getName() { return name; }
    	public String getpooldat() { return pooldat; }
    	public String getdiffapi() { return diffapi; }

    	public void setName(String name) { this.name = name; }
    	public void setpooldat(String pooldat) { this.pooldat = pooldat; }
    	public void setexcrate(String diffapi) { this.diffapi = diffapi; }
    	
    	public void clear() {
    		name = "";
    		pooldat = "";
    		diffapi = "";
    	}
    }
	
	public static class Coindata {
		private String name;
		private String pooldat;
		private double excrate;
		private double netdiffcw;
    	private double netdiffact;
    	private double estccw;
    	private double estcact;
    	private double profcw;
    	private double profact;
    	private double profBcw;
    	private double profBact;
    	
    	public Coindata() { }
    	
    	public Coindata(String name, String pooldat, double excrate, double netdiffcw, double netdiffact, double estccw, double estcact, double profcw, double profact, double profBcw, double profBact) {
    		this.name = name;
    		this.pooldat = pooldat;
    		this.excrate = excrate;
    		this.netdiffcw = netdiffcw;
    		this.netdiffact = netdiffact;
    		this.estccw = estccw;
    		this.estcact = estcact;
    		this.profcw = profcw;
    		this.profact = profact;
    		this.profBcw = profBcw;
    		this.profBact = profBact;  
    	}
    	
    	public String getName() { return name; }
    	public String getpooldat() { return pooldat; }
    	public double getexcrate() { return excrate; }
    	public double getnetdiffcw() { return netdiffcw; }
    	public double getnetdiffact() { return netdiffact; }
    	public double getestccw() { return estccw; }
    	public double getestcact() { return estcact; }
    	public double getprofcw() { return profcw; }
    	public double getprofact() { return profact; }
    	public double getprofBcw() { return profBcw; }
    	public double getprofBact() { return profBact; }
    	
    	public void setName(String name) { this.name = name; }
    	public void setpooldat(String pooldat) { this.pooldat = pooldat; }
    	public void setexcrate(double excrate) { this.excrate = excrate; }
    	public void setnetdiffcw(double netdiffcw) { this.netdiffcw = netdiffcw; }
    	public void setnetdiffact(double netdiffact) { this.netdiffact = netdiffact; }
    	public void setestccw(double estccw) { this.estcact = estccw; }
    	public void setestcact(double estcact) { this.estcact = estcact; }
    	public void setprofcw(double profcw) { this.profcw = profcw; }
    	public void setprofBcw(double profBcw) { this.profBcw = profBcw; }
    	public void setprofBact(double profBact) { this.profBact = profBact; }
    	
    	public void clear() {
    		name = "";
    		pooldat = "";
    		excrate = 0;
    		netdiffcw = 0;
    		netdiffact = 0;
    		estccw = 0;
    		estcact = 0;
    		profcw = 0;
    		profact = 0;
    		profBcw = 0;
    		profBact = 0;
    	}
    	
	}
	
	public static class cgminer {
		private String path;
		private String sett;
		
		public cgminer() { }
		
		public cgminer(String path, String sett) {
			this.path = path;
			this.sett = sett;
		}
		
		public String getpath() { return path; }
    	public String getsett() { return sett; }
    	
    	public void setpath(String path) { this.path = path; }
    	public void setsett(String sett) { this.sett = sett; }
   
	}
	
	public static boolean getcoinbatdatalist() {
		String inputLine;
		int nli,urlsi,urlli,usi,uli,psi,pli,dsi,dli;
		
		File file = new File("coinlist.txt");
		Watcher2.CBdataList.clear();
		
		try {
			   FileReader reader = new FileReader(file);
			   BufferedReader br = new BufferedReader(reader);			 			   
			   
			   while ((inputLine = br.readLine()) != null) {
				   String name,pooldat,diffapi="";
				   
				   nli=inputLine.indexOf("::");
				   urlsi=inputLine.indexOf("-o");
				   urlli=inputLine.indexOf(" ", urlsi+3);
				   usi=inputLine.indexOf("-u");
				   uli=inputLine.indexOf(" ", usi+3);
				   psi=inputLine.indexOf("-p");
				   pli=inputLine.indexOf(" ", psi+3);
				   dsi=inputLine.indexOf("-diffapi");
				   dli=inputLine.indexOf(" ", dsi+9);
				   
				   if (urlsi!=-1 && urlli==-1) {
					   urlli=inputLine.length();
				   }
				   if (usi!=-1 && uli==-1) {
					   uli=inputLine.length();
				   }
				   if (psi!=-1 && pli==-1) {
					   pli=inputLine.length();
				   }
				   if (dsi!=-1 && dli==-1) {
					   dli=inputLine.length();
				   }
				   
				   if (nli!=-1 && urlsi!=-1 && usi!=-1 && psi!=-1) {
					   name=inputLine.substring(0, nli);
					   pooldat=inputLine.substring(urlsi+3,urlli) + "," + inputLine.substring(usi+3,uli) + "," + inputLine.substring(psi+3,pli);
					   if (dsi!=-1 && dli!=-1) {
						   diffapi=inputLine.substring(dsi+9,dli);
					   }
					   Watcher2.CBdataList.add(new DataP.CoinBatdata(name,pooldat,diffapi) );
				   }
				   
			   }
			   
			   br.close();
			   reader.close();		       
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		return true;
	}
	
	public static void readminerdatalist() {
		String inputLine;
		int nli,mli,sli,cli;
		
		File file = new File("data/miningdata.txt");
		Watcher2.CminedataList.clear();
		
		try {
			FileReader reader = new FileReader(file);
			BufferedReader br = new BufferedReader(reader);
			
			while ((inputLine = br.readLine()) != null) {
				String name;
				double mhs,s,c;
				
				nli=inputLine.indexOf("::");
				mli=inputLine.indexOf("mhs", nli+2);
				sli=inputLine.indexOf("s", mli+3);
				cli=inputLine.indexOf("c", sli+1);
				
				if (nli!=-1 && mli!=-1 && sli!=-1 && cli!=-1) {					
					name=inputLine.substring(0, nli);
					mhs=Double.parseDouble(inputLine.substring(nli+3, mli) );
					s=Double.parseDouble(inputLine.substring(mli+4, sli) );
					c=Double.parseDouble(inputLine.substring(sli+2, cli) );
					
					Watcher2.CminedataList.add(new Miner.Cminedata(name, mhs, s, c));
				}
			}
			
			br.close();
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Couldnt open miningdata file.");
		}
	}
	
	public static void writeminerdatalist() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("data/miningdata.txt"));
			for (int i=0;i<Watcher2.CminedataList.size();i++) {
				String str="";
				str+=Watcher2.CminedataList.get(i).getName() + ":: ";
				str+=Watcher2.CminedataList.get(i).getmhs() + "mhs ";
				str+=Watcher2.CminedataList.get(i).gettime() + "s ";
				str+=Watcher2.CminedataList.get(i).getminedc() + "c";
				writer.write(str);
				writer.newLine();
			}
			
			writer.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Couldnt write miningdata to file.");
		}	
		
	}
	
	public static void makecoindatalist() {
		String name,pooldat;
		double excrate,netdiffcw,netdiffact,estccw,estcact,profcw,profact,profBcw,profBact;
		double dv;
		
		Watcher2.CoindataList.clear();
		
		for (int i=0;i<Watcher2.CBdataList.size();i++) {
			boolean done=false;
			int k=0;
			do {
				if (Watcher2.CWdataList.get(k).getName().equals(Watcher2.CBdataList.get(i).getName()) ) {
					name=Watcher2.CBdataList.get(i).getName();
					pooldat=Watcher2.CBdataList.get(i).getpooldat();
					excrate=Watcher2.CWdataList.get(k).getexcrate();
					netdiffcw=Watcher2.CWdataList.get(k).getnetdiff();
					estccw=Watcher2.CWdataList.get(k).getestc();
					estcact=Watcher2.CWdataList.get(k).getestc();
					profcw=Watcher2.CWdataList.get(k).getprof();
					profBcw=Watcher2.CWdataList.get(k).getprofB();
					netdiffact=netdiffcw;
					profact=profcw;
					profBact=profBcw;
					
					dv=getrealnetdiff(Watcher2.CBdataList.get(i).getdiffapi() );
					if (dv!=-1) {
						netdiffact=dv;
						estcact=estccw* (netdiffcw/netdiffact);
						profact=profcw* (netdiffcw/netdiffact);
						profBact=profBcw* (netdiffcw/netdiffact);
					}
					
					Watcher2.CoindataList.add(new Coindata(name,pooldat,excrate,netdiffcw,netdiffact,estccw,estcact,profcw,profact,profBcw,profBact) );
					
					done=true;
				}
				
				k++;
			} while ( k < Watcher2.CWdataList.size() && !done) ;
		}
	}
	
	public static double getrealnetdiff(String url) {
		if (!url.equals("")) {			
			try {
				String inputLine, str;
				int index, endindex;
				
				URL apiurl = new URL(url);
				URLConnection yc = apiurl.openConnection();
				BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));	
				
				while ((inputLine = in.readLine()) != null) { 
					index=inputLine.indexOf("\"networkdiff\":");
					if (index !=-1 ) {
						endindex=inputLine.indexOf(",", index+14);
						str=inputLine.substring(index+14, endindex);
						return Double.parseDouble(str);
					}
				}
				
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Couldnt connect to some realnetdiffAPI with URL: " + url);
			}
			
		}
		
		return -1;
	}
	
	public static void sortCoindatabyprofact() {
		double curr, max;
		int index;
		
		DataP.Coindata tempcoin = new DataP.Coindata();	
		
		for (int i=0;i<Watcher2.CoindataList.size()-1;i++) {
			index=-1;
			max=Watcher2.CoindataList.get(i).getprofact();
			for (int k=i+1;k<Watcher2.CoindataList.size();k++) {
				curr=Watcher2.CoindataList.get(k).getprofact();
				if (curr>max ) {
					index=k;
					max=curr;
				}
			}
			if (index!=-1) {
				tempcoin = Watcher2.CoindataList.get(i);
				Watcher2.CoindataList.set(i, Watcher2.CoindataList.get(index) );
				Watcher2.CoindataList.set(index, tempcoin);	
			}
			
		}
		
	}
	
	public static boolean getcgminerdata() {
		String inputLine, sett="";
		
		File file = new File("cgminer.txt");
		
		try {
			   FileReader reader = new FileReader(file);
			   BufferedReader br = new BufferedReader(reader);
			   
			   while ((inputLine = br.readLine()) != null) {
				   if (inputLine.contains("cgminer") || inputLine.contains("vertminer") ) {
					   Watcher2.minerset.setpath(inputLine);
				   } else {
					   sett+=inputLine + " ";
				   }				
			   }
			   Watcher2.minerset.setsett(sett);
			   
			   br.close();
			   reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public static String convertpooldattopoolstr(String pooldat) {
		String poolstr;
		
		int fi=pooldat.indexOf(",");
		int si=pooldat.indexOf(",", fi+1);
		
		poolstr="-o " + pooldat.substring(0, fi) + " -u " + pooldat.substring(fi+1, si) + " -p " + pooldat.substring(si+1, pooldat.length());
		
		return poolstr;
	}
	
	
}
