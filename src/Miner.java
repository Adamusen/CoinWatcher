import java.io.*;

public class Miner {
	public static double maxminerspeed;
	
	public static class Cminedata {
    	private String name;
    	private double mhs;
    	private double time;
    	private double minedc;
    	
    	public Cminedata() { }
    	
    	public Cminedata(String name, double mhs, double time, double minedcoins) {
    		this.name = name;
    		this.mhs = mhs;
    		this.time = time;
    		this.minedc = minedcoins;
    	}

    	public String getName() { return name; }
    	public double getmhs() { return mhs; }
    	public double gettime() { return time; }
    	public double getminedc() { return minedc; }

    	public void setName(String name) { this.name = name; }
    	public void setmhs(double mhs) { this.mhs = mhs; }
    	public void settime(double time) { this.time = time; }
    	public void setminedc(double minedc) { this.minedc = minedc; }
    	
    	public void clear() {
    		name = "";
    		mhs = 0;
    		time = 0;
    		minedc = 0;
    	}
    }
	
	public static void makeminedatalist() {
		String name;
		long currenttime;
		boolean done;
		double time,deltat=0;
		double ddeltat, minedc, cminedc=0;
		double cmhs,mhs;
		
		name=Watcher2.prevbest;
		done=false;
		for (int i=0;i<Watcher2.CoindataList.size();i++) {
			if (Watcher2.CoindataList.get(i).getName().equals(name) ) {
				for (int j=0;j<Watcher2.CminedataList.size();j++) {
					if (name.equals(Watcher2.CminedataList.get(j).getName()) ) {
						currenttime=System.currentTimeMillis();
						deltat=(int) (currenttime-Watcher2.lastasktime );
						ddeltat=deltat/1000;
						cmhs=Watcher2.mspeed*ddeltat;
						mhs=Watcher2.CminedataList.get(j).getmhs()+cmhs;
						time=Watcher2.CminedataList.get(j).gettime()+ddeltat;
						cminedc=ddeltat*(Watcher2.mspeed)*(Watcher2.CoindataList.get(i).getestcact() / 86400);
						minedc=Watcher2.CminedataList.get(j).getminedc()+cminedc;
						
						Watcher2.CminedataList.set(j, new Miner.Cminedata(name, mhs, time, minedc));
						Watcher2.lastasktime=currenttime;
						
						done=true;
					}
					if (done) { break; }
				}
				
				if (!done) {
					currenttime=System.currentTimeMillis();
					deltat=(int) (currenttime-Watcher2.lastasktime );
					ddeltat=deltat/1000;
					cmhs=Watcher2.mspeed*ddeltat;
					mhs=cmhs;
					time=ddeltat;
					cminedc=ddeltat*(Watcher2.mspeed)*(Watcher2.CoindataList.get(i).getestcact() / 86400);
					minedc=cminedc;
					
					Watcher2.CminedataList.add(new Miner.Cminedata(name, mhs, time, minedc));
					Watcher2.lastasktime=currenttime;
					
					done=true;
				}
				
				if (done) { break; }								
			}	
			if (done) { break; }
		}
		
	}
	
	public static void newminer (String pooldat) throws Exception {
		String answ;
		
		answ=APIClass.sendcmd("quit");
		if (!answ.equals("BYE")) {
			killminer();
		}
		
		Thread.sleep(1000);
		
		if (pooldat!=null) {			
			String poolstr;			
			poolstr=DataP.convertpooldattopoolstr(pooldat);			
			startminer(poolstr);
		} else {
			String poolstr;
			poolstr=DataP.convertpooldattopoolstr(Watcher2.CoindataList.get(0).getpooldat());
			startminer(poolstr);
		}
		
		maxminerspeed=0;
	}
	
	public static void killminer() throws Exception {
		Runtime.getRuntime().exec("taskkill /F /IM cgminer.exe");
		Runtime.getRuntime().exec("taskkill /F /IM vertminer.exe");
		Runtime.getRuntime().exec("taskkill /F /IM cmd.exe");
	}
	
	public static void startminer(String poolstr) throws Exception {
		createtempbat(Watcher2.minerset.getpath() + " " + poolstr + " " + Watcher2.minerset.getsett() + "--api-listen --api-allow W:127.0.0.1");
        Runtime.getRuntime().exec("cmd /c start TempWbat.bat");
		//Runtime rt = Runtime.getRuntime();
		//rt.exec((Watcher2.minerset.getpath() + " " + poolstr + " " + Watcher2.minerset.getsett() + " --api-listen --api-allow W:127.0.0.1"),null);
	}

	public static void createtempbat(String command) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("TempWbat.bat"));
			writer.write(command);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Couldnt make tempwbat.bat");
		}
	}
	
	public static void restartminer() throws Exception {
		String answ;
		
		answ=APIClass.sendcmd("restart");
		if (answ.contains("RESTART")) {
			Thread.sleep(10000);
			if (APIClass.get5sMhs()<0) {
				newminer(null);
			}
		} else {
			newminer(null);
		}
		
		maxminerspeed=0;
	}
	
	public static boolean mineralive() throws Exception {
		String line;
		boolean alive=false;
		Process p = Runtime.getRuntime().exec
				(System.getenv("windir") +"\\system32\\"+"tasklist.exe");
		BufferedReader input =
                new BufferedReader(new InputStreamReader(p.getInputStream()));
        while ((line = input.readLine()) != null) {
            if (line.contains("cgminer.exe") || line.contains("vertminer.exe")) {
            	alive=true;
            }
        }
        input.close();	
        return alive;
	}	
	
	public static int minerspeedok(double speed) {		
		int ret=1;
		
		if (speed!=0) {
			if (speed > maxminerspeed) {
				maxminerspeed=speed;
			}
			if ( speed < maxminerspeed*0.75 ) {
				ret=-1;
			}
		} else {
			maxminerspeed=0;
			ret=0;
		}
		
		return ret;
	}
}

