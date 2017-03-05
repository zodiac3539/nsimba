package kr.ac.korea.www.distribute;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.ObjectInputStream;
import kr.ac.korea.www.LongevitySimulator;
import java.io.ObjectOutputStream;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DistributeServer extends Thread {
    private int port = 0;

    public DistributeServer() {
    }

    public DistributeServer(int _port) {
        port = _port;
    }

    public void run() {
        ServerSocket soc = null;
        try { soc = new ServerSocket(port); } catch(Exception ex) { ex.printStackTrace(); }
        Socket ss;
        while (true) {
            try {
                System.out.println("Waiting Server Connect.");
                ss = soc.accept();
                System.out.println("Accepted");
                ObjectInputStream ois = new ObjectInputStream(ss.getInputStream());
                DistributeVO vo = (DistributeVO) ois.readObject();
                System.out.println("read end");

                LongevitySimulator.scenario = vo.getScenario();
                LongevitySimulator.total_user = vo.getUser();
                LongevitySimulator.duration = 10000;

                int result = LongevitySimulator.estimateTemporal(vo.getMethod());
                vo.setResult(result);

                ObjectOutputStream oos = new ObjectOutputStream(ss.getOutputStream());
                oos.writeObject(vo);
                System.out.println("Write End : " + vo.getResult());

                ois.close();
                oos.close();
                ss.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
         }
    }

    public static void main(String[] args) {
        DistributeServer ds1 = new DistributeServer(3539);
        ds1.start();
        DistributeServer ds2 = new DistributeServer(3540);
        ds2.start();
        DistributeServer ds3 = new DistributeServer(3541);
        ds3.start();
        try {
            ds1.join();
            ds2.join();
            ds3.join();

        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
