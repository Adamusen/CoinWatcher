# JavaCoinWatcher

I created this tool back in the days when GPU cryptocurrency mining was a profitable business and used it so it could real time monitor the profitability of the different altcoins and automatically set the best one for mining.

It has a minimalist GUI that shows the actual values, but most of the time the application was only running on the tray.
It was getting its data from the website coinwarz.com (through analysing the .html code of the site) and could comminacate with the cgminer.exe program (the one used to mine cryptocurrencys) through its own API on the local network. It requires a couple of .txt files, which it uses as configuration files to run properly (settings of the coins and pools you are willing to mine etc.)

Me and my friends were using this tool for months, which I kept updating and improving at the time. Sadly it can't be run properly so easily right now, since most probably the layout of the website it got its data from has changed in the recent years, in addition I'm not into cryptomining anymore as well. Nevertheless the code is there for a showcase.
