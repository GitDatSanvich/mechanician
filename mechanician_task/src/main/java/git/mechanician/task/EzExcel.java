package git.mechanician.task;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.support.ExcelTypeEnum;

import java.io.IOException;
import java.io.InputStream;

import static org.apache.tomcat.util.file.ConfigFileLoader.getInputStream;

/**
 * @ClassName EzExcel
 * @Author GitDatSandwich
 * @Date 2019/7/19 16:17
 **/
public class EzExcel {
    public static void main(String[] args) throws IOException {
        InputStream inputStream = getInputStream("F:\\FFOutput\\录入用户模板.xls");
        try {
            // 解析每行结果在listener中处理
            ExcelListener listener = new ExcelListener();
            ExcelReader excelReader = new ExcelReader(inputStream, ExcelTypeEnum.XLS, User.class, listener);
            excelReader.read();
        } catch (Exception ignored) {
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
