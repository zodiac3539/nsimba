package kr.ac.korea.www.distribute;

import java.io.Serializable;

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
public class DistributeVO implements Serializable {
    private int method;
    private int user;
    private int scenario;
    private int result;

    public DistributeVO() {
        result = 0;
    }

    public int getUser() {
        return user;
    }

    public int getScenario() {
        return scenario;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public void setScenario(int scenario) {
        this.scenario = scenario;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getMethod() {
        return method;
    }

    public int getResult() {
        return result;
    }
}
