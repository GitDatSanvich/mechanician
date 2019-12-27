package git.mechanician.task;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

/**
 * @author GitDatSanvich
 * @ClassName User
 * @Date 2019/7/19 16:24
 **/
public class User extends BaseRowModel {
    @ExcelProperty(index = 0)
    private String username;
    @ExcelProperty(index = 1)
    private String phone;
    @ExcelProperty(index = 2)
    private String Id;
}
