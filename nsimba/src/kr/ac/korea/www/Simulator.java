package kr.ac.korea.www;

import kr.ac.korea.www.util.FileWrite;
import java.util.Properties;
import java.io.*;
import kr.ac.korea.www.util.Gamma;

public class Simulator {
    public static int total_user = 650;
    public static int total_simulatetime = 50;
    public static int duration = 5000;
    public static int machine_num = 2;

    public Simulator() {
    }


    public static void estimateNormal(int kind) {
        String header = "";

        StringBuffer user = new StringBuffer();
        StringBuffer cpu = new StringBuffer();
        StringBuffer traffic = new StringBuffer();

        user.append("USER1,USER2,GAP\r\n");
        cpu.append("CPU1,CPU2,GAP\r\n");
        traffic.append("TRAFFIC1,TRAFFIC2,GAP\r\n");

        //for(int p=1;p<=total_simulatetime;p++) {
            Machine[] machine = new Machine[machine_num];
            for(int m=0;m<machine_num;m++) {
                machine[m] = new Machine();
            }
            DispatcherServer dispatcher = new DispatcherServer(machine);
            LayerSeven l7 = new LayerSeven(machine, dispatcher);

            for(int k=0;k<duration;k++) {
                total_user = (int)Gamma.sampleGamma(100, 1);
                VirtualUser[] vuser = new VirtualUser[total_user];
                if(true) for(int i=0;i<total_user;i++) {
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
                    }
                }
                machine[0].calculateState();
                machine[1].calculateState();

                //System.out.println("user1,user2");
                user.append(machine[0].getUserCount() + "," + machine[1].getUserCount() + "," + Math.abs(machine[0].getUserCount() - machine[1].getUserCount()) + "\r\n");
                cpu.append(machine[0].getCpu() + "," + machine[1].getCpu() + "," + Math.abs(machine[0].getCpu() - machine[1].getCpu()) + "\r\n");
                traffic.append(machine[0].getPacket() + "," + machine[1].getPacket() + "," + Math.abs(machine[0].getPacket() - machine[1].getPacket()) + "\r\n");

                machine[0].passSecond();
                machine[1].passSecond();

                //System.out.println(k);
            }

        //}

        FileWrite fw = new FileWrite();

        fw.writeFile("user"+header+System.currentTimeMillis()+".csv", user.toString());
        fw.writeFile("cpu"+header+System.currentTimeMillis()+".csv", cpu.toString());
        fw.writeFile("traffic"+header+System.currentTimeMillis()+".csv", traffic.toString());

        System.out.println("Simulation Completed.");
    }

    /*
    public static void estimateRamp(int kind) {
        String header = "";

        StringBuffer user = new StringBuffer();
        StringBuffer cpu = new StringBuffer();
        StringBuffer traffic = new StringBuffer();

        user.append("USER1,USER2,GAP\r\n");
        cpu.append("CPU1,CPU2,GAP\r\n");
        traffic.append("TRAFFIC1,TRAFFIC2,GAP\r\n");

        for(int p=1;p<=total_simulatetime;p++) {
            Machine machine1 = new Machine();
            Machine machine2 = new Machine();
            DispatcherServer dispatcher = new DispatcherServer(machine1, machine2);

            LayerSeven l7 = new LayerSeven(machine1, machine2, dispatcher);

            VirtualUser[] vuser = new VirtualUser[total_user];

            int user_limit = 0;
            for(int k=0;k<100;k++) {
                if(user_limit < total_user) user_limit = user_limit + 2;
                for(int i=0;i<user_limit;i++) {
                    vuser[i] = new VirtualUser();
                    vuser[i].makeUser();

                    switch(kind) {
                        case 1:
                            header = "(static)";
                            l7.staticRequest(vuser[i]);
                            break;
                        case 2:
                            header = "(spanids)";
                            l7.dynamicRequestPacket(vuser[i]);
                            break;
                        case 3:
                            header = "(cpu)";
                            l7.dynamicRequestCPU(vuser[i]);
                            break;
                        default:
                            header = "(static)";
                            l7.staticRequest(vuser[i]);
                            break;
                    }
                }
                machine1.calculateState();
                machine2.calculateState();

                //System.out.println("user1,user2");
                user.append(machine1.getUserCount() + "," + machine2.getUserCount() + "," + Math.abs(machine1.getUserCount() - machine2.getUserCount()) + "\r\n");
                cpu.append(machine1.getCpu() + "," + machine2.getCpu() + "," + Math.abs(machine1.getCpu() - machine2.getCpu()) + "\r\n");
                traffic.append(machine1.getPacket() + "," + machine2.getPacket() + "," + Math.abs(machine1.getPacket() - machine2.getPacket()) + "\r\n");

                machine1.passSecond();
                machine2.passSecond();

            }
        }

        FileWrite fw = new FileWrite();

        fw.writeFile("user"+header+System.currentTimeMillis()+".csv", user.toString());
        fw.writeFile("cpu"+header+System.currentTimeMillis()+".csv", cpu.toString());
        fw.writeFile("traffic"+header+System.currentTimeMillis()+".csv", traffic.toString());

        System.out.println("Simulation Completed.");

    }*/

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

        //static
        //estimateNormal(ikind);
        //dynamic - Packet
        //estimateNormal(3);
        //dynamic - CPU
        estimateNormal(1);
        estimateNormal(2);
        estimateNormal(3);

        //estimateRamp(2);
        //estimateRamp(3);
    }
}
