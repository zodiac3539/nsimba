package kr.ac.korea.www;

import kr.ac.korea.www.util.Gamma;
import kr.ac.korea.www.util.IUserConstants.Pagekind;

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
public class RequestedPage4 extends RequestedPage {
    //1 - img01  0.010  31617
    //2 - img02  0.010  27248
    //3 - img03  0.020  56515
    //4 - s2_01  0.055  815
    //5 - static 0.010  1558
    //6 - s2_03  0.050  91
    //7 - dynamic0.041  1555

    public RequestedPage4()
    {
        super(4);
        double r1 = Math.random();
        double r2 = Math.random();

        /*
        double r3 = Math.random();
        double r4 = Math.random();
        r3 = r3 * 10;
        r3 = r3 + 1;
        r4 = r4 * (100 - 10);
        r4 = r4 + 10;
        */
        double r3 = Gamma.sampleGamma(2.5, 2);
        double r4 = Gamma.sampleGamma(10, 10);

        //double network_speed = r3 + 0.5;
        double network_speed = 1;

        //this.page_kind = (int)(r1 * 3) + 1;
        //Page Kind를 고려한다.
        if(r1 < 0.175) {
            this.page_kind = 1;
            this.page_traffic = 31617;
            this.cpu = 0.010;
            this.session_time = (int)r3;
            this.classification = Pagekind.Static;
        } else if(r1 >= 0.175 && r1 < 0.35) {
            this.page_kind = 2;
            this.page_traffic = 27248;
            this.cpu = 0.010;
            this.session_time = (int)r3;
            this.classification = Pagekind.Static;
        } else if(r1 >= 0.35 && r1 < 0.525) {
            this.page_kind = 3;
            this.page_traffic = 56515;
            this.cpu = 0.020;
            this.session_time = (int)r3;
            this.classification = Pagekind.Static;
        } else if(r1 >= 0.525 && r1 < 0.7) {
            this.page_kind = 5;
            this.page_traffic = 1558;
            this.cpu = 0.010;
            this.session_time = (int)r4;
            this.classification = Pagekind.Static;
        } else if(r1 >= 0.7 && r1 < 0.8) {
            this.page_kind = 6;
            this.page_traffic = 91;
            this.cpu = 0.050;
            this.session_time = (int)r4;
            this.classification = Pagekind.Dynamic;
        } else if(r1 >= 0.8 && r1 < 0.9) {
            this.page_kind = 7;
            this.page_traffic = 92;
            this.cpu = 0.050;
            this.session_time = (int)r4;
            this.classification = Pagekind.Dynamic;
        } else {
            this.page_kind = 4;
            this.page_traffic = 1555;
            this.cpu = 0.041;
            this.session_time = (int)r4;
            this.classification = Pagekind.Dynamic;
        }
        if(this.session_time < 1) this.session_time = 1;
    }
}
