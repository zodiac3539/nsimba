package kr.ac.korea.www;

import kr.ac.korea.www.distribute.DistributeClient;

public class Test1 {
    public Test1() {
    }

    public static void main(String[] args) {
        /*
        VirtualUser[] vuser = new VirtualUser[100];
        for(int i=0;i<100;i++) {
            vuser[i] = new VirtualUser();
            vuser[i].makeUser();
            System.out.println("XX.XX." + vuser[i].getIp_cclass() + "." + vuser[i].getIp_dclass());
        }*/

        DistributeClient dc = new DistributeClient();
        int result = dc.getValue("192.168.10.101", 3540, 1, 2, 100);
        System.out.println(result);

    }
}
