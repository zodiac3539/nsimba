package kr.ac.korea.www;

public class Statistics {
    public int rr;
    public int hash;
    public int lcf;
    public int spanids;
    public int cpu;

    public boolean isAllMan() {
        if(rr >= 10000 &&
           hash >= 10000 &&
           lcf >= 10000 &&
           spanids >= 10000 &&
           cpu >= 10000) {
            return true;
        }
        else return false;

    }

    public boolean isPoor() {
        if(rr <= 200 &&
           hash <= 200 &&
           lcf <= 200 &&
           spanids <= 200 &&
           cpu <= 200) {
            return true;
        }
        else return false;

    }

    public Statistics() {
    }
}
