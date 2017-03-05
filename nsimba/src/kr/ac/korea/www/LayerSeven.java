package kr.ac.korea.www;

public class LayerSeven {
    private int rrnum = 0;
    Machine[] machine;
    private DispatcherServer dispatcherServer;

    public LayerSeven(Machine[] machine, DispatcherServer dispatcherServer) {
        this.machine = machine;
        this.dispatcherServer = dispatcherServer;
    }

    public void hashRequest(VirtualUser vuser) {
        int mnum = vuser.getIp_dclass() % machine.length;
        machine[mnum].addUser(vuser);
    }

    public void rrRequest(VirtualUser vuser) {
        machine[rrnum].addUser(vuser);
        rrnum++;
        if(rrnum >= machine.length) {
            rrnum = 0;
        }
    }

    public void lcfRequest(VirtualUser vuser) {
        int min = 0;
        int con = Integer.MAX_VALUE;
        for (int i = 0; i < machine.length; i++) {
           if (machine[i].getUserCount() < con && machine[i].isEnable()) {
               min = i;
               con = machine[i].getUserCount();
           }
        }

        machine[min].addUser(vuser);

        for (int i = 0; i < machine.length; i++) {
           machine[i].calculateState();
        }
    }

    public void dynamicRequestPacket(VirtualUser vuser) {
        dispatcherServer.dynamicRequestPacket(vuser);
    }

    public void dynamicRequestCPU(VirtualUser vuser) {
        dispatcherServer.dynamicRequestCPU(vuser);
    }

    public void dynamicRequestCPURR(VirtualUser vuser) {
        boolean isMachineDead = false;
        for(int i=0;i<machine.length;i++) {
            if( !machine[i].isEnable() ) isMachineDead = true;
        }

        if(isMachineDead)
            this.rrRequest(vuser);
        else
            dispatcherServer.dynamicRequestCPU(vuser);
    }

}
