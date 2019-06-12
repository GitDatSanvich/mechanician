package git.mechanician.user.utils;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @ClassName PropertiesUtil
 * @Author GitDatSanvich
 * @Date 2019/6/11 14:20
 **/
public class PropertiesUtil implements Serializable {
    private final ResourceBundle resource;
    private final String fileName;

    /**
     * 构造函数实例化部分对象，获取文件资源对象
     *
     * @param fileName
     */
    public PropertiesUtil(String fileName) {
        this.fileName = fileName;
        Locale locale = new Locale("zh", "CN");
        this.resource = ResourceBundle.getBundle(this.fileName, locale);
    }

    /**
     * 根据传入的key获取对象的值 2016年12月17日 上午10:19:55 getValue
     *
     * @param key properties文件对应的key
     * @return String 解析后的对应key的值
     */
    public String getValue(String key) {
        String message = this.resource.getString(key);
        return message;
    }

    /**
     * 获取properties文件内的所有key值<br>
     * 2016年12月17日 上午10:21:20 getKeys
     *
     * @return
     */
    public Enumeration<String> getKeys() {
        return resource.getKeys();
    }
}
