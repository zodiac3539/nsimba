package kr.ac.korea.www;

import kr.ac.korea.www.util.FileWrite;
import java.util.Properties;
import java.io.IOException;
import kr.ac.korea.www.util.Gamma;
import kr.ac.korea.www.distribute.DistributeClient;
import java.util.Arrays;

public class LongevitySimulator {
    public static int total_user = 650;
    public static int total_simulatetime = 100;
    public static int duration = 5000;
    public static int machine_num = 4;

    public static int i1, i2, i3, i4, i5;
    public static int scenario = 1;

    public static int u;
    public LongevitySimulator() {
    }

    public static void estimateNormal(int kind) {
        String header = "";

        StringBuffer longevity = new StringBuffer();

        longevity.append("Longevity\r\n");

        for(int p=1;p<=total_simulatetime;p++) {
            Machine[] machine = new Machine[machine_num];
            for(int m=0;m<machine_num;m++) {
                machine[m] = new Machine();
            }
            DispatcherServer dispatcher = new DispatcherServer(machine);
            LayerSeven l7 = new LayerSeven(machine, dispatcher);


            int k = 0;
            for(k=0;k<duration;k++) {
                int real_user = (int)Gamma.sampleGamma(total_user, 1);
                VirtualUser[] vuser = new VirtualUser[real_user];

                for(int i=0;i<real_user;i++) {
                    vuser[i] = new VirtualUser();
                    vuser[i].makeUser();

                    switch(kind) {
                        case 1:
                            header = "(RR)";
                            l7.rrRequest(vuser[i]);
                            break;
                        case 2:
                            header = "(HASH)";
                            l7.hashRequest(vuser[i]);
                            break;
                        case 3:
                            header = "(LCF)";
                            l7.lcfRequest(vuser[i]);
                            break;
                        case 4:
                            header = "(SPANIDS)";
                            l7.dynamicRequestPacket(vuser[i]);
                            break;
                        case 5:
                            header = "(CPU)";
                            l7.dynamicRequestCPU(vuser[i]);
                            break;
                    }
                }
                for(int m=0;m<machine.length;m++) {
                    machine[m].calculateState();
                    if(machine[m].getCpu() >= 80 ) machine[m].setEnable(false);
                    machine[m].passSecond();
                }

                boolean oneLive = false;
                for(int m=0;m<machine.length;m++) {
                    oneLive = oneLive | machine[m].isEnable();
                }

                if(!oneLive) break;
            }
            longevity.append(k + "\r\n");
            System.out.print("|");
        }


        FileWrite fw = new FileWrite();

        fw.writeFile("longevity"+header+System.currentTimeMillis()+".csv", longevity.toString());
        //fw.writeFile("cpu"+header+System.currentTimeMillis()+".csv", cpu.toString());
        //fw.writeFile("traffic"+header+System.currentTimeMillis()+".csv", traffic.toString());

        System.out.println("");
        System.out.println("Simulation Completed.");
    }

    public static int estimateTemporal(int kind) {
        String header = "";

        StringBuffer longevity = new StringBuffer();

        longevity.append("Longevity\r\n");

        //for(int p=1;p<=total_simulatetime;p++) {
        Machine[] machine = new Machine[machine_num];
        for(int m=0;m<machine_num;m++) {
            machine[m] = new Machine();
        }
        DispatcherServer dispatcher = new DispatcherServer(machine);
        LayerSeven l7 = new LayerSeven(machine, dispatcher);

        //If you want to change real_user to static value
        //You just modify below line as "int real_user = total_user;"
        //int real_user = (int)Gamma.sampleGammaMean(total_user, 1);
        int real_user = total_user;

        VirtualUser[] vuser = new VirtualUser[real_user];
        int k = 0;
        for(k=0;k<duration;k++) {
            for(int i=0;i<real_user;i++) {

                vuser[i] = new VirtualUser();
                vuser[i].makeUser();

                switch(kind) {
                    case 1:
                        //header = "(RR)";
                        l7.rrRequest(vuser[i]);
                        break;
                    case 2:
                        //header = "(HASH)";
                        l7.hashRequest(vuser[i]);
                        break;
                    case 3:
                        //header = "(LCF)";
                        l7.lcfRequest(vuser[i]);
                        break;
                    case 4:
                        //header = "(SPANIDS)";
                        l7.dynamicRequestPacket(vuser[i]);
                        break;
                    case 5:
                        //header = "(CPU)";
                        l7.dynamicRequestCPU(vuser[i]);
                        break;
                    case 6:
                        //l7.dynamicRequestCPU(vuser[i]);
                        break;
                }

            }
            if(kind == 6) {
                Arrays.sort(vuser);
                for(VirtualUser vu : vuser) {
                    l7.dynamicRequestCPU(vu);
                }
            }

            for(int m=0;m<machine.length;m++) {
                machine[m].calculateState();
                if(machine[m].getCpu() >= 80 ) machine[m].setEnable(false);
                machine[m].passSecond();
            }

            boolean oneLive = false;
            for(int m=0;m<machine.length;m++) {
                oneLive = oneLive | machine[m].isEnable();
            }

            if(!oneLive) break;

        }
        return k;
    }

    public static void doSomething(int ustart, int uend, int interval) {
        StringBuffer strb = new StringBuffer();
        for(u=ustart;u<=uend;u=u+interval) {
            strb.append("user,rr,hash,lcf,spanids,cpu\r\n");
            //StatisticsArray sr = new StatisticsArray(u, scenario);
            total_user = u;
            System.out.println("start time : " + System.currentTimeMillis());
            for(int s=0;s<50;s++) {
                Statistics stat = new Statistics();
                Thread t1 = new Thread() {
                    public void run() {
                        DistributeClient dc = new DistributeClient();
                        i1 = dc.getValue("192.168.10.100", 3539, 1, scenario, u);
                        //i1 = estimateTemporal(1);
                    }
                };
                Thread t2 = new Thread() {
                    public void run() {
                        //DistributeClient dc = new DistributeClient();
                        //i2 = dc.getValue("192.168.10.100", 3540, 2, scenario, u);
                        i2 = estimateTemporal(2);
                    }
                };
                Thread t3 = new Thread() {
                    public void run() {
                        i3 = estimateTemporal(3);
                        //DistributeClient dc = new DistributeClient();
                        //i3 = dc.getValue("192.168.10.101", 3541, 3, scenario, u);
                    }
                };
                Thread t4 = new Thread() {
                    public void run() {
                        DistributeClient dc = new DistributeClient();
                        i4 = dc.getValue("192.168.10.101", 3539, 4, scenario, u);
                        //i4 = estimateTemporal(4);
                    }
                };
                Thread t5 = new Thread() {
                    public void run() {
                        DistributeClient dc = new DistributeClient();
                        i5 = dc.getValue("192.168.10.101", 3540, 5, scenario, u);
                        //i5 = estimateTemporal(5);
                    }
                };
                t1.start();
                t2.start();
                t3.start();
                t4.start();
                t5.start();

                try {
                    t1.join();
                    t2.join();
                    t3.join();
                    t4.join();
                    t5.join();
                } catch(Exception ex1) {
                    ex1.printStackTrace();
                }

                stat.rr = i1;
                stat.hash = i2;
                stat.lcf = i3;
                stat.spanids = i4;
                stat.cpu = i5;
                strb.append(u + "," + stat.rr + "," + stat.hash + ","
                            + stat.lcf + "," + stat.spanids + "," + stat.cpu + "\r\n");

                System.out.print("|");

            }
            System.out.println("end time : " + System.currentTimeMillis());

            System.out.println("");
            System.out.println(u + "--------------------------------");

            FileWrite fw = new FileWrite();
            fw.writeFile("s_" + scenario + "_" + u + ".txt", strb.toString());
            strb = new StringBuffer();
        }
    }

    public static void doSomething2(int ustart, int uend, int interval) {
        for(u=ustart;u<=uend;u=u+interval) {
            StringBuffer strb = new StringBuffer();

            strb.append("user,newcpu\r\n");
            total_user = u;
            for (int s = 0; s < 25; s++) {
                Thread t1 = new Thread() {
                    public void run() {
                        i1 = estimateTemporal(6);
                    }
                };
                Thread t2 = new Thread() {
                    public void run() {
                        DistributeClient dc = new DistributeClient();
                        i5 = dc.getValue("192.168.10.101", 3540, 6, scenario, u);
                        //i2 = estimateTemporal(6);
                    }
                };
                t1.start();
                t2.start();
                try {
                    t1.join();
                    t2.join();
                } catch(Exception ex) { ex.printStackTrace(); }

                strb.append(u + "," + i1 + "\r\n");
                strb.append(u + "," + i2 + "\r\n");

                System.out.print("|");
            }
            System.out.println("");
            System.out.println(u + "--------------------------------");

            FileWrite fw = new FileWrite();
            fw.writeFile("sk_" + scenario + "_" + u + ".txt", strb.toString());
        }
    }


    public static void main(String[] args) {
        Properties pro = new Properties();
        try {
            pro.load(Simulator.class.getResource("/kr/ac/korea/www/util/input.properties").openStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        int iduration = Integer.parseInt(pro.getProperty("input.duration"));
        int ipeople = Integer.parseInt(pro.getProperty("input.person"));
        int ikind = Integer.parseInt(pro.getProperty("input.method"));

        total_user = ipeople;
        duration = iduration;

        //total_user = 162;
        for(int i=0;i<50;i++) {
            System.out.print("|");
        }
        System.out.println("");


        scenario = 1;
        doSomething(50, 1000, 50);

        scenario = 2;
        doSomething(50, 1000, 50);

        scenario = 3;
        doSomething(50, 1000, 50);

        scenario = 4;
        doSomething(50, 1000, 50);

    }
}
