C:\Adamusen\cgminer-3.7.2-windows\cgminer -o stratum+tcp://emc2.suprnova.cc:3366 -u Adamusen.1 -p 1234 --scrypt --failover-only -o stratum+tcp://pool-eu.hash.so:3339 -u Adamusen.1 -p x -I 13,13 -w 256 -g 2 --thread-concurrency 8192 --gpu-engine 1070 --gpu-memclock 1500 --gpu-powertune -20 --gpu-vddc 1.100 --temp-target 83,80 --auto-fan --gpu-fan 20-70,20-70 --temp-cutoff 90,90 --temp-overheat 87,85 --api-listen --api-allow W:127.0.0.1