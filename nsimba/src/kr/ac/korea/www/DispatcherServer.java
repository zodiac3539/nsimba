package kr.ac.korea.www;

public class DispatcherServer {
    Machine[] machine;

    public DispatcherServer(Machine[] machine) {
        this.machine = machine;
    }

    public void dynamicRequestPacket(VirtualUser vuser) {
        int min = 0;
        double packet = machine[0].getPacket();
        for(int i=0;i<machine.length;i++) {
            if(machine[i].getPacket() < packet && machine[i].isEnable() ) {
                min = i;
                packet = machine[i].getPacket();
            }
        }

        machine[min].addUser(vuser);

        for(int i=0;i<machine.length;i++) {
            machine[i].calculateState();
        }
    }

    public void dynamicRequestCPU(VirtualUser vuser) {
        int min = 0;
        double cpu = machine[0].getCpu();
        for (int i = 0; i < machine.length; i++) {
           if (machine[i].getCpu() < cpu && machine[i].isEnable()) {
               min = i;
               cpu = machine[i].getCpu();
           }
        }

        machine[min].addUser(vuser);

        for (int i = 0; i < machine.length; i++) {
           machine[i].calculateState();
        }
    }
}
