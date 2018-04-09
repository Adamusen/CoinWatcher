import java.util.*;

import javax.swing.JOptionPane;

public class Watcher2 {
    public static String prevbest="null",newbest="null";
    public static boolean stop=false; 
    public static double mspeed;
    public static long lastasktime=System.currentTimeMillis();
    
    public static List<CoinWarz.CWdata> CWdataList = new ArrayList<CoinWarz.CWdata>();
    public static List<DataP.CoinBatdata> CBdataList = new ArrayList<DataP.CoinBatdata>();
    public static List<DataP.Coindata> CoindataList = new ArrayList<DataP.Coindata>();
    public static List<Miner.Cminedata> CminedataList = new ArrayList<Miner.Cminedata>();
    public static DataP.cgminer minerset = new DataP.cgminer();
    
    public static void init() {
		boolean b;
    	
    	try {
			Window.createWindow();
			Window.createTray();			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		b=DataP.getcgminerdata();
		if (!b) {
			JOptionPane.showMessageDialog(null, "Couldn\'t find cgminer.txt or Error in it!");
			System.exit(0);
		}
		b=DataP.getcoinbatdatalist();
		if (!b) {
			JOptionPane.showMessageDialog(null, "Couldn\'t find coinlist.txt or Error in it!");
			System.exit(0);
		}
		
		DataP.readminerdatalist();

	}
	
	public static void main(String[] args) throws Exception {
		init();

		int wi=0;
		while (stop!=true) {
        	if (wi % 10 == 0) {
        		CoinWarz.getcwdata();
        	}
			DataP.makecoindatalist();
			DataP.sortCoindatabyprofact();
        	newbest=CoindataList.get(0).getName();
        	mspeed=APIClass.get5sMhs();
        	Miner.makeminedatalist();
        	Window.currcoin.setText("Currently mining: " + newbest + " with " + mspeed + " Mhs/s speed.");
        	Window.updatetab();        	
       	
        	if (!Miner.mineralive()) {
        		Miner.newminer(null);
        	} else {		
        		if (prevbest!=newbest) {
        			int succ;
        			succ=APIClass.newpool(CoindataList.get(0).getpooldat() );
        			if (succ==-1) {
        				Miner.newminer(null);
        			}
        		}
        		        		
        		if (Miner.minerspeedok(mspeed)==-1 ) {
        			Miner.restartminer();
        		}
        		
        	}
        	prevbest=newbest;
        	Thread.sleep(5000);
        	wi++;
        	
        	if (wi % 30 == 0) {
        		DataP.writeminerdatalist();
        	}
        }
        
        
		//CoinWarz.printcwcode();
		/*DataP.getcgminerdata();
		CoinWarz.getcwdata();
		DataP.getcoinbatdatalist();
		DataP.makecoindatalist();
		DataP.sortCoindatabyprofact();
		
		DataP.Coindata coinasd = new DataP.Coindata();*/
		/*(int i=0;i<Watcher2.c;i++) {
			coinasd=CoindataList.get(i);
		
			System.out.print(coinasd.getName() + " ");
			//System.out.print(coinasd.getpooldat() + " ");
			System.out.print(coinasd.getnetdiffcw() + " ");
			System.out.print(coinasd.getnetdiffact() + " ");
			System.out.print(coinasd.getprofcw() + " ");
			System.out.println(coinasd.getprofact() + " ");
			//System.out.print(coinasd.getprofBcw() + " ");
			//System.out.println(coinasd.getprofBact() );
		}*/
		/*System.out.println(minerset.getpath());
		System.out.println(minerset.getsett());
		Miner.newminer(null);*/
		
		//CoinWarz.printcwcode();
		/*Coindata asd = new Coindata("",.0,.0,.0);
		asd.setName("kek");
		Coindata asf = new Coindata();
		asf.setName("lol");
		System.out.println(asd.getName());
		System.out.println(asf.getName());
		System.out.println(asf.getnetdiff());*/
		//System.out.println(api.getpooldb());
		//System.out.println(api.apiacceslv());
		//System.out.println(api.sendcmd("addpool|stratum+tcp://lite.wemineltc.com:3334,Adamusen.1,1234"));
		//System.out.println(api.sendcmd("removepool|0"));
		//System.out.println(api.sendcmd("removepool|2"));
		//System.out.println(api.sendcmd("switchpool|2"));
		//System.out.println(api.sendcmd("pools"));
		//Thread.sleep(3000);
		//System.out.println(api.sendcmd("pools"));
		//System.out.println(api.poolactive(1));
		
		/*for (int i=0;i<batlist.size();i++) {
    		System.out.println(batlist.get(i));
    	}*/
    }
}