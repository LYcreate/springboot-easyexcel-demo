package link.lycreate.springbooteasyexceldemo.controller;

import com.alibaba.excel.EasyExcel;
import link.lycreate.springbooteasyexceldemo.dao.DemoDao;
import link.lycreate.springbooteasyexceldemo.domain.Demo;
import link.lycreate.springbooteasyexceldemo.listener.UploadDataListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @ClassName ExcelDemoController
 * @Description TODO
 * @Author LYcreate
 * @Date 2020/5/1 17:40
 */
@Controller
public class ExcelDemoController {

    @Resource
    private DemoDao demoDao;

    @RequestMapping(path="/upload",method = RequestMethod.POST)
    public String uploadExcel(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        EasyExcel.read(file.getInputStream(), Demo.class,new UploadDataListener(demoDao)).sheet().doRead();
        return "/uploadStatus";
    }
}
