package com.wlhse.test;

import com.wlhse.service.UploadService;
import com.wlhse.util.SortCodeUtil;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/*.xml"})
public class Test {

    @Resource
    private UploadService uploadService;

    @Resource
    private SortCodeUtil sortCodeUtil;

    //导入excel采用！！！！！
//    @org.junit.Test
//    public void test1() throws Exception {
//        //从本地将excel存入db
//        uploadService.uploadCheckList("F:\\java后台安装工具\\TomCat\\webapps\\CheckList\\cc9120ee-5ca1-44bb-bc09-dcb2f0c0de32.xls");
//    }

    @org.junit.Test
    public void test2() throws Exception {
//        String code=sortCodeUtil.getMaxCodeString("00010001");
        List<String> list=new ArrayList<>();
        list.add("0001");
        list.add("0003");
        list.add("0004");
        list.add("0002");
        System.out.println(sortCodeUtil.getMaxCode(list));
    }
}
