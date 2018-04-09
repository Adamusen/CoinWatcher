import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;

import javax.imageio.ImageIO;

public class Window {
	
	public static JFrame frame = new JFrame("Watcher");
	public static JPanel tab = new JPanel();
	public static JPanel tabheading = new JPanel();
	public static JPanel tabcontent = new JPanel();
	public static JPanel tabsumm = new JPanel();
	public static JPanel tabcontentborder = new JPanel();
	public static JScrollPane tabcontentscroll = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	public static JLabel currcoin = new JLabel();
	
	public static void createWindow() {
		frame.setSize(925, 500);
		frame.setResizable(false);
		frame.setVisible(true);
		
		BorderLayout framelayout = new BorderLayout();
		frame.setLayout(framelayout);
		frame.add(currcoin,BorderLayout.PAGE_START);
		
		BoxLayout boxlayout = new BoxLayout(tab,BoxLayout.PAGE_AXIS);	
		tab.setLayout(boxlayout);
		
		GridLayout tabheadlayout = new GridLayout(0,7);				
		tabheading.setLayout(tabheadlayout);
		Dimension d = new Dimension(920,80);
		tabheading.setMaximumSize(d);
		
		JButton nameB = new JButton("<html><center> Coin Name <br /> Balance </center></html>");
		tabheading.add(nameB);
		JButton excrB = new JButton("<html><center> Exchange rate </center></html>");
		tabheading.add(excrB);
		JButton ndcwB = new JButton("<html><center> Est. coin/day <br /> CoinWarz <br /> Actual </center></html>");
		tabheading.add(ndcwB);
		JButton ndactB = new JButton("<html><center> Network diff <br /> CoinWarz <br /> Actual </center></html>");
		tabheading.add(ndactB);		
		JButton pb = new JButton("<html><center> $/day / <br /> MHash/s <br /> CoinWarz <br /> Actual </center></html>");
		tabheading.add(pb);
		JButton pbB = new JButton("<html><center> BTC/day / <br /> MHash/s <br /> CoinWarz <br /> Actual </center></html>");
		tabheading.add(pbB);
		JButton cdb = new JButton("<html><center> Mined [min] <br /> Worth BTC <br /> avg. m. prof </center></html>");
		tabheading.add(cdb);
		
		GridLayout tabcontentlayout = new GridLayout(0,7);
		tabcontent.setLayout(tabcontentlayout);;
		tabcontent.setBackground(Color.lightGray);
		
		tabcontentborder.setLayout(new BorderLayout());
		tabcontentborder.add(tabcontent, BorderLayout.NORTH);
		
		tabcontentscroll.setViewportView(tabcontentborder);
		tabcontentscroll.getVerticalScrollBar().setUnitIncrement(16);
		tabcontentscroll.setPreferredSize(d);
		
		GridLayout tabsummlayout = new GridLayout(0,6);				
		tabsumm.setLayout(tabsummlayout);
		d = new Dimension(920,70);
		tabsumm.setMaximumSize(d);
		tabsumm.setPreferredSize(d);
		tabsumm.setBackground(Color.GRAY);
				
		tab.add(tabheading);
		tab.add(tabcontentscroll);
		tab.add(tabsumm);
		
		frame.add(tab,BorderLayout.WEST);
	}
	
	public static void updatetab() {
		double worthsumm=0, tminsumm=0, mhssumm=0;
		
		tabcontent.removeAll();
		tabsumm.removeAll();
		
		for (int i=0; i<Watcher2.CoindataList.size();i++) {
			if (!Watcher2.CoindataList.get(i).equals(null)) {
				JLabel jname = new JLabel();
				jname.setHorizontalAlignment(SwingConstants.CENTER);	
				String name = Watcher2.CoindataList.get(i).getName();
				double mined=0;
				
				String tmin="0 m",worth="0 BTC",avgprof="0 BTC/d";
				for (int z=0;z<Watcher2.CminedataList.size();z++) {
					if (name.equals(Watcher2.CminedataList.get(z).getName()) ) {
						mined=Watcher2.CminedataList.get(z).getminedc();
						tmin=String.format("%.2f", (Watcher2.CminedataList.get(z).gettime() /60) ) + " m";
						worth=String.format("%.8f", (Watcher2.CminedataList.get(z).getminedc() * Watcher2.CoindataList.get(i).getexcrate() ) ) + " BTC";
						avgprof=String.format("%.5f", (Watcher2.CminedataList.get(z).getminedc() * Watcher2.CoindataList.get(i).getexcrate() * 1440 / (Watcher2.CminedataList.get(z).gettime() /60) ) / (Watcher2.CminedataList.get(z).getmhs()/Watcher2.CminedataList.get(z).gettime()) ) + " BTC/d";
						
						mhssumm+=Watcher2.CminedataList.get(z).getmhs();
						tminsumm+=Watcher2.CminedataList.get(z).gettime() /60;
						worthsumm+=Watcher2.CminedataList.get(z).getminedc() * Watcher2.CoindataList.get(i).getexcrate();
					}
				}
				jname.setText("<html><center> " + name + " <br /> " + mined + " </center></html>");
				tabcontent.add(jname);
				
				JLabel jexcr = new JLabel();
				jexcr.setHorizontalAlignment(SwingConstants.CENTER);			
				jexcr.setText(String.format("%.8f", Watcher2.CoindataList.get(i).getexcrate() ));
				tabcontent.add(jexcr);
				
				JLabel jestc = new JLabel();
				jestc.setHorizontalAlignment(SwingConstants.CENTER);
				String estccw = String.format("%.4f", Watcher2.CoindataList.get(i).getestccw() );
				String estcact = String.format("%.4f", Watcher2.CoindataList.get(i).getestcact() );
				jestc.setText("<html><center> " + estccw + " <br /> " + estcact + " </center></html>");
				tabcontent.add(jestc);
				
				JLabel jndact = new JLabel();
				jndact.setHorizontalAlignment(SwingConstants.CENTER);
				String netdiffcw = String.valueOf(Watcher2.CoindataList.get(i).getnetdiffcw() );
				String netdiffact = String.valueOf(Watcher2.CoindataList.get(i).getnetdiffact() );
				jndact.setText("<html><center> " + netdiffcw + " <br /> " + netdiffact + " </center></html>");
				tabcontent.add(jndact);
				
				JLabel jpd = new JLabel();
				jpd.setHorizontalAlignment(SwingConstants.CENTER);
				String profcw = String.format("%.2f", Watcher2.CoindataList.get(i).getprofcw() ) + " $";
				String profact = String.format("%.2f", Watcher2.CoindataList.get(i).getprofact() ) + " $";
				jpd.setText("<html><center> " + profcw + " <br /> " + profact + " </center></html>");
				tabcontent.add(jpd);			
								
				
				JLabel jpb = new JLabel();
				jpb.setHorizontalAlignment(SwingConstants.CENTER);		
				String profbcw = String.format("%.5f", Watcher2.CoindataList.get(i).getprofBcw() );
				String profbact = String.format("%.5f", Watcher2.CoindataList.get(i).getprofBact() );
				jpb.setText("<html><center> " + profbcw + " <br /> " + profbact + " </center></html>");
				tabcontent.add(jpb);
				
				JLabel cdb = new JLabel();
				cdb.setHorizontalAlignment(SwingConstants.CENTER);		
				cdb.setText("<html><center> " + tmin + " <br /> " + worth + " <br /> " + avgprof + " </center></html>");
				tabcontent.add(cdb);
				
				Dimension d = new Dimension(920,50*Watcher2.CoindataList.size());
				tabcontent.setPreferredSize(d);
			}
			
		}
		
		JLabel btcsumm = new JLabel();
		btcsumm.setHorizontalAlignment(SwingConstants.CENTER);
		String btcs = String.format("%.8f", worthsumm) + " BTC";
		btcsumm.setText("<html><center> BTC summ  <br /> " + btcs + " </center></html>");
		tabsumm.add(btcsumm);
		
		JLabel dsumm = new JLabel();
		dsumm.setHorizontalAlignment(SwingConstants.CENTER);
		String sdsumm = String.format("%.3f", worthsumm * CoinWarz.BTCprice) + " $";
		dsumm.setText("<html><center> Total $ <br /> " + sdsumm + " </center></html>");
		tabsumm.add(dsumm);
		
		JLabel jtmin = new JLabel();
		jtmin.setHorizontalAlignment(SwingConstants.CENTER);
		String stmin = String.format("%.2f", tminsumm) + " min";
		jtmin.setText("<html><center> Total time  <br /> " + stmin + " </center></html>");
		tabsumm.add(jtmin);
		
		JLabel javgd = new JLabel();
		javgd.setHorizontalAlignment(SwingConstants.CENTER);
		String savgd = String.format("%.2f", worthsumm * CoinWarz.BTCprice * 1440 / tminsumm) + " $";
		String savgdpm = String.format("%.2f", worthsumm * CoinWarz.BTCprice * 1440 / tminsumm / (mhssumm/(tminsumm*60)) ) + " $";
		javgd.setText("<html><center> Avg. $/day <br /> " + savgd + " <br /> " + savgdpm + " </center></html>");
		tabsumm.add(javgd);
		
		JLabel javgbtc = new JLabel();
		javgbtc.setHorizontalAlignment(SwingConstants.CENTER);
		String savgbtc = String.format("%.5f", worthsumm * 1440 / tminsumm) + " BTC";
		String savgbtcpm = String.format("%.5f", worthsumm * 1440 / tminsumm / (mhssumm/(tminsumm*60)) ) + " BTC";
		javgbtc.setText("<html><center> Avg. BTC/day <br /> " + savgbtc + " <br /> " + savgbtcpm + " </center></html>");
		tabsumm.add(javgbtc);
		
		JLabel jbtcp = new JLabel();
		jbtcp.setHorizontalAlignment(SwingConstants.CENTER);
		String sbtcp = String.format("%.2f", CoinWarz.BTCprice) + " $/BTC";
		jbtcp.setText("<html><center> BTC price <br /> Bitstamp <br /> " + sbtcp + " </center></html>");
		tabsumm.add(jbtcp);
		
		tab.validate();
		tab.repaint();
	}
	
	public static void createTray() {
		final PopupMenu popup = new PopupMenu();	
		
		TrayIcon trayIcon = null;
		try {
			trayIcon = new TrayIcon(ImageIO.read(new File("data/trayicon.jpg")));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}		
		trayIcon.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		frame.setVisible(true);
        	}
        });
		
        final SystemTray tray = SystemTray.getSystemTray();
        
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		DataP.writeminerdatalist();
        		System.exit(0);
        	}
        });
        
        popup.add(exitItem);
        
        trayIcon.setPopupMenu(popup);
        
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
        }
	}
}
