package kr.ac.korea.www;

import kr.ac.korea.www.util.Gamma;
import kr.ac.korea.www.util.IUserConstants.Pagekind;

public class RequestedPage {
    protected Pagekind classification;
    protected int page_kind;
    //The kind of Page
    //1 - static
    //2 - dynamic - no database connection
    //3 - dynamic - database connection
    //4 - Image 01
    //5 - Image 02

    protected double page_traffic;
    //The traffic of page which expresses in kilobyte.

    protected double cpu;
    //CPU usage
    //static(including image, and streaming) = traffic * 0.045
    //dynamic(no database connection) = traffic * 0.1075
    //dynamic(database connection) = traffic * 0.1625
    protected int session_time;
    protected double real_time;

    //세션 시간은 순수하게 USER의 NETWORK SPEED에 의해 결정된다.
    public RequestedPage(int k) {
        //Nothing to do!
    }

    public RequestedPage() {
        double r1 = Math.random();
        double r2 = Math.random();

        /*
        double r3 = Math.random();
        double r4 = Math.random();
        r3 = r3 * 10;
        r3 = r3 + 1;
        r4 = r4 * (100 - 10);
        r4 = r4 + 10;*/

        double r3 = 0;
        double r4 = 0;

        while(r3 > 0 && r4 > 0) {
            r3 = Gamma.sampleGamma(2.5, 2);
            r4 = Gamma.sampleGamma(10, 10);
        }

        //double network_speed = r3 + 0.5;
        double network_speed = 1;

        //this.page_kind = (int)(r1 * 3) + 1;
        //Page Kind를 고려한다.
        if(r1 < 0.36) {
            this.page_kind = 3;
            this.page_traffic = 1555;
            this.cpu = 0.041;
            this.session_time = (int)r4;
            this.classification = Pagekind.Dynamic;
        } else if(r1 >= 0.37 && r1 < 0.45) {
            this.page_kind = 2;
            this.page_traffic = 1556;
            this.cpu = 0.021;
            this.session_time = (int)r4;
            this.classification = Pagekind.Dynamic;
        } else if(r1 >= 0.45 && r1 < 0.70) {
            this.page_kind = 1;
            this.page_traffic = 1558;
            this.cpu = 0.010;
            this.session_time = (int)r3;
            this.classification = Pagekind.Static;
        } else if(r1 >= 0.70 && r1 < 0.85) {
            this.page_kind = 4;
            this.page_traffic = 31617;
            this.cpu = 0.010;
            this.session_time = (int)r3;
            this.classification = Pagekind.Static;
        } else {
            this.page_kind = 5;
            this.page_traffic = 27248;
            this.cpu = 0.055;
            this.session_time = (int)r3;
            this.classification = Pagekind.Static;
        }
        if(this.session_time < 1) this.session_time = 1;
        //this.session_time = (int)r3;
    }

    public void division() {
        if(this.session_time > 0) {
            this.cpu = this.cpu / this.session_time;
            this.page_traffic = this.page_traffic / this.session_time;
        }
    }

    public double getPage_traffic() {
        return page_traffic;
    }

    public double getCpu() {
        return cpu;
    }

    public int getSession_time() {
        return session_time;
    }

    public Pagekind getClassification() {
        return classification;
    }
}
