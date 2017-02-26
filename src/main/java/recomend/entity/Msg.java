package recomend.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/2/25.
 */
public class Msg {
    private String info;
    private int code;
    private List<String> result;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<String> getResult() {
        return result;
    }

    public void setResult(List<String> result) {
        this.result = result;
    }

    public Msg(){
        super();
    }

    public Msg(String info, int code, List<String> result){
        super();
        this.info = info;
        this.code = code;
        this.result = result;
    }
}
