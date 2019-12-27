package git.mechanician.user.utils;

/**
 * @ClassName Result
 * @Author GitDatSanvich
 * @Date 2019/12/26 10:34
 **/

public class Result {
    private String returnResult;
    private String exception;
    private String sleepTime;

    public Result() {
    }

    public Result(String returnResult, String exception, String sleepTime) {
        this.returnResult = returnResult;
        this.exception = exception;
        this.sleepTime = sleepTime;
    }

    public String getReturnResult() {
        return returnResult;
    }

    public void setReturnResult(String returnResult) {
        this.returnResult = returnResult;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(String sleepTime) {
        this.sleepTime = sleepTime;
    }
}