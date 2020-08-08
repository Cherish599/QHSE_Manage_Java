package com.wlhse.test;

import com.wlhse.dao.CheckListDao;
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
    @Resource
    private CheckListDao checkListDao;

    //导入excel采用！！！！！
//    @org.junit.Test
//    public void test1() throws Exception {
//        //从本地将excel存入db
//        uploadService.uploadCheckList("F:\\java后台安装工具\\TomCat\\webapps\\CheckList\\cc9120ee-5ca1-44bb-bc09-dcb2f0c0de32.xls");
//    }

    @org.junit.Test
    public void test2() throws Exception {
//        String code=sortCodeUtil.getMaxCodeString("00010001");
        List<String> list = new ArrayList<>();
        list.add("0001");
        list.add("0003");
        list.add("0004");
        list.add("0002");
        System.out.println(sortCodeUtil.getMaxCode(list));
    }

    @org.junit.Test
    public void test3() throws Exception {
        List<String> list = checkListDao.querryAllCheckListCode();
        for (String t : list) {
            System.out.println(t);
        }
    }

    @org.junit.Test
    public void insertProblemDescriptionTest() {
        // String[] description=problemDescription.split("([1-9][0-9]{0,1})");//用0-99的数字打断，中间为正则表达式
        //先把该code的问题描述全部删除，再添加。
        String description = "1专职安全监管人员未取得国家注册安全工程师资格1.5%2.注安占专职人员的比例等于20% 3注安占专职人员的比例在20%以下 4.其他";
        if(description.startsWith("1") )
        {
            String[] s=description.split("1",2);
            for(int i=2;s[1].contains(String.valueOf(i));i++)
            {
                description=s[1];
                s=description.split(String.valueOf(i),2);
                if(s[0].startsWith("."))
                    System.out.println(s[0].substring(1));
                else
                    System.out.println(s[0]);

            }
            if(s[1].startsWith("."))
                System.out.println(s[1].substring(1));
            else
                System.out.println(s[1]);
        }
        else{
            System.out.println("第一项的序号不是：1");
        }
    }

}
