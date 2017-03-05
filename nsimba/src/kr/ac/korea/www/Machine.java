package kr.ac.korea.www;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Iterator;

public class Machine {
    //private int userCount;
    private List<VirtualUser> userList;
    private double cpu;
    private double packet;
    private boolean enable = true;

    public Machine() {
        userList = new LinkedList<VirtualUser>();
        cpu = 0;
        packet = 0;
    }

    public void addUser(VirtualUser vuser) {
        userList.add(vuser);
        cpu = cpu + vuser.getRequested_page().getCpu();
        packet = packet + vuser.getRequested_page().getPage_traffic();
    }

    public int getUserCount() {
        return userList.size();
    }

    public void setEnable(boolean _enable) {
        enable = _enable;
    }

    public boolean isEnable() {
        return enable;
    }

    public void passSecond() {
        ListIterator<VirtualUser> li = userList.listIterator();
        while(li.hasNext()) {
            VirtualUser vuser = li.next();
            vuser.minusSessionTime();
            if(vuser.getSession_time() < 1) {
                //CASE : user expires their session time
                cpu = cpu - vuser.getRequested_page().getCpu();
                packet = packet - vuser.getRequested_page().getPage_traffic();

                li.remove();
            }
        }

    }
    public void calculateState() {
        /*
        cpu = 0;
        packet = 0;
        Iterator it = userList.iterator();
        while(it.hasNext()) {
            VirtualUser vuser = (VirtualUser)it.next();
            cpu = cpu + vuser.getRequested_page().getCpu();
            packet = packet + vuser.getRequested_page().getPage_traffic();
        }*/
    }

    public double getPacket() {
        return packet;
    }

    public double getCpu() {
        return cpu;
    }
}
