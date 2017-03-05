package kr.ac.korea.www;

public class StatisticsArray {
    int user = 0;
    int scenario = 0;
    int push = 0;
    private Statistics[] stat;

    public double rr_mean = 0;
    public double hash_mean = 0;
    public double lcf_mean = 0;
    public double spanids_mean = 0;
    public double cpu_mean = 0;

    public double rr_var = 0;
    public double hash_var = 0;
    public double lcf_var = 0;
    public double spanids_var = 0;
    public double cpu_var = 0;

    private int TOTAL_TIMES = 10;

    public String writeIt() {
        for(int i=0;i<TOTAL_TIMES;i++) {
            rr_mean = rr_mean + stat[i].rr;
            hash_mean = hash_mean + stat[i].hash;
            lcf_mean = lcf_mean + stat[i].lcf;
            spanids_mean = spanids_mean + stat[i].spanids;
            cpu_mean = cpu_mean + stat[i].cpu;
        }
        rr_mean = rr_mean / TOTAL_TIMES;
        hash_mean = hash_mean / TOTAL_TIMES;
        lcf_mean = lcf_mean / TOTAL_TIMES;
        spanids_mean = spanids_mean / TOTAL_TIMES;
        cpu_mean = cpu_mean / TOTAL_TIMES;

        for(int i=0;i<TOTAL_TIMES;i++) {
            rr_var = rr_var + (stat[i].rr - rr_mean) * (stat[i].rr - rr_mean);
            hash_var = hash_var + (stat[i].hash - hash_mean) * (stat[i].hash - hash_mean);
            lcf_var = lcf_var + (stat[i].lcf - lcf_mean) * (stat[i].lcf - lcf_mean);
            spanids_var = spanids_var + (stat[i].spanids - spanids_mean) * (stat[i].spanids - spanids_mean);
            cpu_var = cpu_var + (stat[i].cpu - cpu_mean) * (stat[i].cpu - cpu_mean);
        }
        rr_var = rr_var / TOTAL_TIMES;
        hash_var = hash_var / TOTAL_TIMES;
        lcf_var = lcf_var / TOTAL_TIMES;
        spanids_var = spanids_var / TOTAL_TIMES;
        cpu_var = cpu_var / TOTAL_TIMES;


        double min = 0;
        double max = 0;
        StringBuffer strb = new StringBuffer();
        strb.append(user + ",");
        min = rr_mean - ( 1.96 * Math.sqrt(rr_var) / Math.sqrt(TOTAL_TIMES));
        max = rr_mean + ( 1.96 * Math.sqrt(rr_var) / Math.sqrt(TOTAL_TIMES));
        strb.append(min + "~" + max + ",");
        min = hash_mean - ( 1.96 * Math.sqrt(hash_var) / Math.sqrt(TOTAL_TIMES));
        max = hash_mean + ( 1.96 * Math.sqrt(hash_var) / Math.sqrt(TOTAL_TIMES));
        strb.append(min + "~" + max + ",");
        min = lcf_mean - ( 1.96 * Math.sqrt(lcf_var) / Math.sqrt(TOTAL_TIMES));
        max = lcf_mean + ( 1.96 * Math.sqrt(lcf_var) / Math.sqrt(TOTAL_TIMES));
        strb.append(min + "~" + max + ",");
        min = spanids_mean - ( 1.96 * Math.sqrt(spanids_var) / Math.sqrt(TOTAL_TIMES));
        max = spanids_mean + ( 1.96 * Math.sqrt(spanids_var) / Math.sqrt(TOTAL_TIMES));
        strb.append(min + "~" + max + ",");
        min = cpu_mean - ( 1.96 * Math.sqrt(cpu_var) / Math.sqrt(TOTAL_TIMES));
        max = cpu_mean + ( 1.96 * Math.sqrt(cpu_var) / Math.sqrt(TOTAL_TIMES));
        strb.append(min + "~" + max + "\r\n");

        return strb.toString();
    }

    public StatisticsArray(int user, int scenario) {
        this.user = user;
        this.scenario = scenario;
        stat = new Statistics[TOTAL_TIMES];
    }

    public void add(Statistics ss) {
        stat[push] = ss;
        push++;
    }

    public boolean isNeedNotWrite() {
        for(int i=0;i<TOTAL_TIMES;i++) {
            if( stat[i] != null && !stat[i].isAllMan() ) return false;
        }
        return true;
    }

    public boolean isBreak() {
        for(int i=0;i<TOTAL_TIMES;i++) {
            if( stat[i] != null && stat[i].isPoor() ) return true;
        }
        return false;
    }
}
