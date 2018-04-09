import java.net.*;
import java.io.*;

public class APIClass {
	
	private static final int MAXRECEIVESIZE = 65535;
	private static Socket socket = null;
	private static String ip = "127.0.0.1";
	private static String port = "4028";
	
	public static int apiacclv;
	
	public static int getapiacceslv() {
		String answ,msg;
		int lv=-1;
		
		answ=sendcmd("privileged");
		msg=getmsg(answ);
		if (msg.equals("Privileged access OK")) {
			lv=1;
		} else if (msg.equals("Access denied to 'privileged' command")) {
			lv=0;
		} else {
			lv=-1;
		}		
		
		apiacclv=lv;
		return lv;
	}
	
	public static int getpooldb() {
		int pooldb=-1;
		String answ;
		answ=sendcmd("pools");
		if (!answ.equals("")) {
			pooldb=Integer.parseInt(answ.substring(answ.indexOf("Msg=")+4, answ.indexOf(" ", answ.indexOf("Msg=")+4)));
		}
		return pooldb;
	}
	
	public static boolean poolstratactive(int poolnum) {
		String answ;
		boolean active=false;
		
		answ=sendcmd("pools");
		if (!answ.equals("")) {
			int k=-14;
			for (int i=0;i<poolnum;i++) {
				k=answ.indexOf("Stratum Active=", k+15);
			}
			active=answ.substring(k+15, k+19).equals("true");
		}
		
		return active;
	}
	
	public static boolean poolalive(int poolnum) {
		String answ;
		boolean alive=false;
		
		answ=sendcmd("pools");
		if (!answ.equals("")) {
			int k=-7;
			for (int i=0;i<=poolnum;i++) {
				k=answ.indexOf("Status=", k+7);
			}
			alive=answ.substring(k+7, k+12).equals("Alive");
		}
		
		return alive;
	}
	
	public static String getmsg(String ans) {
		String msg=null;
		int b,e;
		
		b=ans.indexOf("Msg=")+4;
		e=ans.indexOf(",", b);
		if (b!=3) {
			msg=ans.substring(b, e);
		} else {
			msg="fail";
		}
		
		return msg;
	}
	
	public static double get5sMhs() {
		String answ;
		double value=0;
		int b,e;
		
		answ=sendcmd("summary");
		if (!answ.equals("")) {
			b=answ.indexOf("MHS 5s=")+7;
			e=answ.indexOf(",", b);
			value=Double.parseDouble(answ.substring(b, e) );
		}
		
		return value;
	}
	
	public static int newpool(String pooldata) throws Exception {
		int pooldb;
		String answ;
		int succes=-1;
		
		if (pooldata!=null) {
			pooldb=getpooldb();
			
			sendcmd("addpool|" + pooldata);

			answ=sendcmd("switchpool|" + String.valueOf(pooldb));
			Thread.sleep(10000);
			
			if (answ.contains("Msg=Switching to pool")) {
				answ=sendcmd("removepool|" + String.valueOf(pooldb-1) );
				if ( getmsg(answ).contains("Removed") ) {
					succes=1;
				} else if (poolalive(pooldb)==true) {
					sendcmd("removepool|" + String.valueOf(pooldb) );
					succes=-1;
				} else {
					sendcmd("removepool|" + String.valueOf(pooldb) );
					succes=0;
				}
			} else {
				succes=-1;
			}
		}
		
		return succes;
	}
	
	public static String sendcmd(String cmd) {
		InetAddress ipa = null;
		int porti = 0;
		
		StringBuffer sb = new StringBuffer();
		char buf[] = new char[MAXRECEIVESIZE];
		int len = 0;	
				
		try
		{
			ipa = InetAddress.getByName(ip);
			porti = Integer.parseInt(port);
			
			socket = new Socket(ipa, porti);
		}
		catch (Exception e)
		{
			System.err.println(e);
		}
		
		try
		{
			PrintStream ps = new PrintStream(socket.getOutputStream());
			InputStreamReader isr = new InputStreamReader(socket.getInputStream());
			
			ps.print(cmd.toLowerCase().toCharArray());
			ps.flush();;
			
			while (0x80085 > 0)
			{
				len = isr.read(buf, 0, MAXRECEIVESIZE);
				if (len < 1)
					break;
				sb.append(buf, 0, len);
				if (buf[len-1] == '\0')
					break;
			}
			
			if (socket != null)
			{
				socket.close();
				socket = null;
			}
		}
		catch (Exception e)
		{
			System.err.println(e);
		}
		
		String result = sb.toString();
		return result;
	}
}
