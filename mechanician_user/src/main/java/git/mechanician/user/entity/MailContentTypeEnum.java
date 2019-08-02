package git.mechanician.user.entity;

import java.io.Serializable;

/**
 * @ClassName MailContentTypeEnum
 * @Author GitDatSanvich
 * @Date 2019/6/11 14:20
 **/

public enum MailContentTypeEnum implements Serializable {
    //html格式
    HTML("text/html;charset=UTF-8"),
    //文字格式
    TEXT("text"),
    ;
    private String value;

    MailContentTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
