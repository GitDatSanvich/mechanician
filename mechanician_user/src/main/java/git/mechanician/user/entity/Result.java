package git.mechanician.user.entity;

import java.io.Serializable;

/**
 * @ClassName Result
 * @Author GitDatSanvich
 * @Date 2019/2/15 19:00
 **/
public class Result implements Serializable {
    /**
     * 返回码
     */
    private Integer code;
    /**
     * 是否成功
     */
    private boolean flag;
    /**
     * 返回消息
     */
    private String massage;
    /**
     * 返回数据
     */
    private Object data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Result() {
    }

    public Result(Integer code, boolean flag, String massage, Object data) {
        this.code = code;
        this.flag = flag;
        this.massage = massage;
        this.data = data;
    }

    public Result(boolean flag, Integer code, String massage, Object data) {
        this.code = code;
        this.flag = flag;
        this.massage = massage;
        this.data = data;
    }

    public Result(Integer code, boolean flag, String massage) {
        this.code = code;
        this.flag = flag;
        this.massage = massage;
    }

    public Result(boolean flag, Integer code, String massage) {
        this.code = code;
        this.flag = flag;
        this.massage = massage;
    }
}