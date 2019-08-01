package git.mechanician.task;

import com.alibaba.dubbo.common.json.JSONObject;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ExcelListener
 * @Author GitDatSanvich
 * @Date 2019/7/19 16:20
 **/
public class ExcelListener extends AnalysisEventListener {

    private List<Object> datas = new ArrayList<>();

    @Override
    public void invoke(Object o, AnalysisContext analysisContext) {
        JSONObject jsonObject;
        datas.add(o);
        System.out.println("执行!" + datas.size());

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }
}
