package link.lycreate.springbooteasyexceldemo.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import link.lycreate.springbooteasyexceldemo.dao.DemoDao;
import link.lycreate.springbooteasyexceldemo.domain.Demo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName UploadDataListener
 * @Description TODO
 * @Author LYcreate
 * @Date 2020/5/1 17:49
 */
public class UploadDataListener extends AnalysisEventListener<Demo> {

    private Logger LOGGER= LoggerFactory.getLogger(this.getClass());

    private static final int BATCH_COUNT = 5;
    List<Demo> list=new ArrayList<Demo>();
    private DemoDao demoDao;

    public UploadDataListener(DemoDao demoDao){
        this.demoDao=demoDao;
    }

    @Override
    public void invoke(Demo demo, AnalysisContext analysisContext) {
        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(demo));
        list.add(demo);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }


    private void saveData() {
        LOGGER.info("{}条数据，开始存储数据库！", list.size());
        for (Demo demo:list){
            demoDao.insert(demo);
        }
        LOGGER.info("存储数据库成功！");
    }
}
