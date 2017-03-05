package kr.ac.korea.www;

import kr.ac.korea.www.util.Gamma;
import kr.ac.korea.www.util.IUserConstants.Pagekind;

public class RequestedPage2 extends RequestedPage{
    //page_kind
    //1 - img01  0.010  31617
    //2 - img02  0.010  27248
    //3 - img03  0.020  56515
    //4 - s2_01  0.055  815
    //5 - s2_02  0.050  90
    //6 - s2_03  0.050  91
    //7 - s2_04  0.050  92

    //세션 시간은 순수하게 USER의 NETWORK SPEED에 의해 결정된다.
    public RequestedPage2() {
        super(2);
        double r1 = Math.random();
        double r2 = Math.random();

        double r3 = Gamma.sampleGamma(2.5, 2);
        double r4 = Gamma.sampleGamma(10, 10);

        /*
        r3 = r3 * 10;
        r3 = r3 + 1;
        r4 = r4 * (100 - 10);
        r4 = r4 + 10;*/
        //double network_speed = r3 + 0.5;
        double network_speed = 1;

        //this.page_kind = (int)(r1 * 3) + 1;
        //Page Kind를 고려한다.
        if(r1 < 0.1) {
            this.page_kind = 1;
            this.page_traffic = 31617;
            this.cpu = 0.010;
            this.session_time = (int)r3;
            this.classification = Pagekind.Static;
        } else if(r1 >= 0.1 && r1 < 0.2) {
            this.page_kind = 2;
            this.page_traffic = 27248;
            this.cpu = 0.010;
            this.session_time = (int)r3;
            this.classification = Pagekind.Static;
        } else if(r1 >= 0.2 && r1 < 0.3) {
            this.page_kind = 3;
            this.page_traffic = 56515;
            this.cpu = 0.020;
            this.session_time = (int)r3;
            this.classification = Pagekind.Static;
        } else if(r1 >= 0.3 && r1 < 0.45) {
            this.page_kind = 5;
            this.page_traffic = 90;
            this.cpu = 0.050;
            this.session_time = (int)r4;
            this.classification = Pagekind.Dynamic;
        } else if(r1 >= 0.45 && r1 < 0.6) {
            this.page_kind = 6;
            this.page_traffic = 91;
            this.cpu = 0.050;
            this.session_time = (int)r4;
            this.classification = Pagekind.Dynamic;
        } else if(r1 >= 0.6 && r1 < 0.75) {
            this.page_kind = 7;
            this.page_traffic = 92;
            this.cpu = 0.050;
            this.session_time = (int)r4;
            this.classification = Pagekind.Dynamic;
        } else {
            this.page_kind = 4;
            this.page_traffic = 815;
            this.cpu = 0.055;
            this.session_time = (int)r4;
            this.classification = Pagekind.Dynamic;
        }
        if(this.session_time < 1) this.session_time = 1;
    }
}
