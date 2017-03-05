package kr.ac.korea.www.distribute;

import java.net.Socket;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

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
public class DistributeClient {
    public DistributeClient() {
    }

    public int getValue(String server, int port, int method, int scenario, int user) {
        try {
            Socket ss = new Socket(server, port);

            DistributeVO vo = new DistributeVO();
            vo.setScenario(scenario);
            vo.setUser(user);
            vo.setMethod(method);

            ObjectOutputStream oos = new ObjectOutputStream(ss.getOutputStream());
            oos.writeObject(vo);

            ObjectInputStream ois = new ObjectInputStream(ss.getInputStream());
            vo = (DistributeVO)ois.readObject();;

            oos.close();
            ois.close();
            ss.close();

            return vo.getResult();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }
}
