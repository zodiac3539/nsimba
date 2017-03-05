package kr.ac.korea.www;

public class VirtualUser implements Comparable<VirtualUser>{
    private int ip_cclass;
    //User's IP of C class
    private int ip_dclass;
    //User's IP of D class
    private RequestedPage requested_page;
    //Requested Page
    private int session_time;
    //Session Duration Time
    private double network_speed;

    public VirtualUser() {

    }

    public void makeUser() {
        //for uniform distribution
        double r1 = Math.random();
        double r2 = Math.random();
        double r3 = Math.random();

        ip_cclass = (int)((r1 * 255) + 1);
        ip_dclass = (int)((r2 * 255) + 1);

        //������ ����Ÿ�ӿ� ������ �ִ� ��Ҵ�
        //1. SYN PACKET DROP RATE
        //2. NETWORK SPEED
        //3. USER FRUSTRATION�� ������,
        //���⼭�� ������ �ɸ��� workload�� �����ϱ� ���� �����̹Ƿ� �����ϰ� 2���� ����Ѵ�.
        if(LongevitySimulator.scenario == 1) {
            requested_page = new RequestedPage();
        } else if (LongevitySimulator.scenario == 2) {
            requested_page = new RequestedPage2();
        } else if (LongevitySimulator.scenario == 3) {
            requested_page = new RequestedPage3();
        } else if (LongevitySimulator.scenario == 4) {
            requested_page = new RequestedPage4();
        }
        session_time = requested_page.getSession_time();
        requested_page.division();
    }

    public RequestedPage getRequested_page() {
        return requested_page;
    }

    public int getIp_dclass() {
        return ip_dclass;
    }

    public int getIp_cclass() {
        return ip_cclass;
    }

    public int getSession_time() {
        return session_time;
    }

    public void minusSessionTime() {
        session_time = session_time - 1;
    }

    public int compareTo(VirtualUser vu) {
        return this.getRequested_page().getClassification().ordinal() -
                vu.getRequested_page().getClassification().ordinal();
    }
}
