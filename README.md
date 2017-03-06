# A web server Simulator
This source code was used as a web server simulator when I wrote the paper "Dynamic Resource Management for Longevity in Web Server Systems" with professor Kim in Korea university.
Usually, when an user visit the web site, its dwelling time follows Gamma distribution, putting pressure on the web server differently.
This web server simulator assumes that there are dynamic web pages, like JSP, or static web pages, like images or HTMLs. Each type of file puts pressure on a web server differently.
Please read the paper first to get yourself familiar with the codes. Without the understanding on a web server and how it works, this code wouldn't make sense to you.
http://zodiac3539.cafe24.com/1st_dissertation.pdf 

#Server / Client
Unfortunately, the simulation needs a lot of calculation. I ran this code for three days to gather what I needed. In order to speed up my process, I used distributed computing techniques. You can use a multiple of computers to speed up the process. This class uses simple TCP server / client model and thread programming. However, it would be a great starting point to understand network programming in Java and the basic of distributed computing in Java environment too.
I personally used three laptops to pull off the calculations. (At that time, there was no AWS). I could speed up the process a lot, I met the deadline of the paper, successfully presenting it to IEEE.

