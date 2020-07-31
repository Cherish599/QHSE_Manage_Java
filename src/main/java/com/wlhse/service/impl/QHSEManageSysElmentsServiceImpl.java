package com.wlhse.service.impl;

import com.alibaba.fastjson.annotation.JSONType;
import com.wlhse.dao.QHSEManageSysElementsDao;
import com.wlhse.dto.QHSEproblemDiscriptionDto;
import com.wlhse.dto.inDto.YearElementsDto;
import com.wlhse.entity.QHSECompanySysElementsPojo;
import com.wlhse.entity.QHSEManageSysElements;
import com.wlhse.entity.QhseElementsPojo;
import com.wlhse.exception.WLHSException;
import com.wlhse.service.QHSEManageSysElementsService;
import com.wlhse.util.R;
import com.wlhse.util.TreeUtil;
import com.wlhse.util.state_code.NR;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class QHSEManageSysElmentsServiceImpl implements QHSEManageSysElementsService {
    @Resource
    QHSEManageSysElementsDao qhseManageSysElementsDao;

    @Resource
    private TreeUtil treeUtil;
    private String code;

    @Override
    public String querryAllRules(Integer status) {
        try {

            List<QHSECompanySysElementsPojo> list = new ArrayList<QHSECompanySysElementsPojo>();
            if (status == 0) {
                list = qhseManageSysElementsDao.querryQHSEReportElements1();
            } else if (status == 1) {
                list = qhseManageSysElementsDao.querryQHSEReportElements("启用");
            }
            List<QHSECompanySysElementsPojo> list2 = treeUtil.getQHSEReportTree(list);
            return NR.r(list2);
        } catch (Exception e) {
            e.printStackTrace();
            throw new WLHSException("查询失败");
        }
    }

    @Override
    public String addQHSERule(QHSEManageSysElements rule) {
        String parentCode = rule.getCode();
        try {
            if (parentCode == null || "".equals(parentCode)) {
                String maxCode = qhseManageSysElementsDao.querryLastQHSEChildCode("");
                Integer maxNum = Integer.parseInt(maxCode);
                Integer num = maxNum + 1;
                String numCode = Integer.toString(num);
                if (num < 10)
                    rule.setCode('0' + numCode);
                else
                    rule.setCode(numCode);
                rule.setTotalCount(0);
                rule.setInitialScore(0);
            } else {
                Integer len = parentCode.length();
                if (len < 8) {
                    rule.setTotalCount(0);
                    rule.setInitialScore(0);
                    String maxCode = qhseManageSysElementsDao.querryLastQHSEChildCode(parentCode);
                    if (maxCode == null || "".equals(maxCode))
                        rule.setCode(parentCode + "01");
                    else {
                        String lastTwoCode = maxCode.substring(maxCode.length() - 2, maxCode.length());
                        Integer lastTwoNum = Integer.parseInt(lastTwoCode);
                        Integer num = lastTwoNum + 1;
                        String numCode = Integer.toString(num);
                        if (num < 10)
                            rule.setCode(parentCode + '0' + numCode);
                        else
                            rule.setCode(parentCode + numCode);
                    }
                } else if (len == 8) {
                    rule.setTotalCount(1);
                    String maxCode = qhseManageSysElementsDao.querryLastQHSEChildCode(parentCode);
                    if (maxCode == null || "".equals(maxCode))
                        rule.setCode(parentCode + "01");
                    else {
                        String lastTwoCode = maxCode.substring(maxCode.length() - 2, maxCode.length());
                        Integer lastTwoNum = Integer.parseInt(lastTwoCode);
                        Integer num = lastTwoNum + 1;
                        String numCode = Integer.toString(num);
                        if (num < 10)
                            rule.setCode(parentCode + '0' + numCode);
                        else
                            rule.setCode(parentCode + numCode);
                    }

                    Integer len1 = len;
                    Integer initialScore = rule.getInitialScore();
                    List<String> pCode = new ArrayList<String>();
                    while (len1 > 0) {//所有父节点
                        String str = parentCode.substring(0, len1);
                        pCode.add(str);
                        len1 -= 2;
                    }
                    for (int i = 0; i < pCode.size(); i++) {
                        String code = pCode.get(i);
                        if (qhseManageSysElementsDao.addTotalCount(code, 1) <= 0)
                            throw new WLHSException("更新失败");
                        if (qhseManageSysElementsDao.addInitialScore(code, initialScore) <= 0)
                            throw new WLHSException("更新失败");
                    }
                }
            }
            if (qhseManageSysElementsDao.addQHSERule(rule) <= 0)
                throw new WLHSException("新增失败");
            return NR.r();
        } catch (Exception e) {
            e.printStackTrace();
            throw new WLHSException("新增失败");
        }
    }

    @Override
    public String updateStatus(QHSEManageSysElements rule) {
        String code = rule.getCode();
        Integer len = code.length();
        String status = rule.getStatus();
        List<String> list = getParent(code);
        if (status.equals("停用")) {
            setOff("停用", code);
            if (len == 10) {//是叶子节点
                int score = getScore(code);
                for (int i = 0; i < list.size(); i++) {
                    updateScoreCount(list.get(i), score, 1);
                }
            } else {//不是叶子节点
                Integer score = sumScore(code); //score: null
                Integer count = sumCount(code);//count: 0
                toZero(code);//将当前项和子项的分数和项数都归零
                for (int i = 0; i < list.size(); i++) {//该节点所有分母依次减分减项数
                    updateScoreCount(list.get(i), score, count);
                }
            }
        } else if (status.equals("启用")) {
            for (int i = 0; i < list.size(); i++) {
                setOn("启用", list.get(i));
            }
            setOn("启用", code);
            if (len == 10) {
                int score = getScore(code);
                addScoreCount(list, score);
            }
        }
        return NR.r();
    }

    @Override
    public String updateQHSERule(QHSEManageSysElements rule) {
        String code = rule.getCode();
        Integer len = code.length();
        String status = qhseManageSysElementsDao.querryStatus(code);
        if ("启用".equals(status)) {
            if (len == 10) {
                Integer score = qhseManageSysElementsDao.querryScore(code);
                Integer newScore = rule.getInitialScore() - score;
                if (newScore != 0) {
                    List<String> parentCode = new ArrayList<String>();
                    while (len > 2) {//所有父节点
                        len -= 2;
                        String str = code.substring(0, len);
                        parentCode.add(str);
                    }
                    for (int i = 0; i < parentCode.size(); i++) {
                        String pcode = parentCode.get(i);
                        if (qhseManageSysElementsDao.addInitialScore(pcode, newScore) <= 0)
                            throw new WLHSException("更新失败");
                    }
                }
            }
        }
        if (qhseManageSysElementsDao.updateRule(rule) <= 0)
            throw new WLHSException("更新失败");
        return NR.r();
    }

    public void setOff(String str, String code) {
        qhseManageSysElementsDao.setOff(str, code);

    }//本人及其儿子改成停用

    public void setOn(String str, String code) {
        qhseManageSysElementsDao.setOn(str, code);
    }//本人及其父母改成启用

    public List<String> getParent(String code) {
        List<String> list = new ArrayList<String>();
        int len = code.length();
        while (len > 0) {//父节点
            String str = code.substring(0, len);
            list.add(str);
            len -= 3;
        }
        list.remove(0);
        return list;
    }//当前节点的所有父节点 不包含本人

    public Integer getScore(String code) {
        return qhseManageSysElementsDao.getScore(code);//返回这个叶子节点的分数
    }

    //    public void subScoreCount(String code,Integer score){
//        qhseManageSysElementsDao.subSoreCount(code,score);//父节点减去分数score和项数1
//    }
    public int sumScore(String code) {
        return qhseManageSysElementsDao.sumScore(code);//当前所有叶子节点的分数之和
    }

    public int sumCount(String code) {
        return qhseManageSysElementsDao.sumCount(code);//当前所有叶子节点的项数之和
    }

    public void updateScoreCount(String code, Integer score, Integer count) {
        qhseManageSysElementsDao.updateScoreCount(code, score, count);//父母的score count减去
    }

    public void toZero(String code) {
        qhseManageSysElementsDao.toZero(code);//当前项到叶子节点前一项的所有分数和项数都归零
    }

    public void addScoreCount(List<String> list, Integer score) {//所有父母依次加分 项数加一
        for (int i = 0; i < list.size(); i++) {
            qhseManageSysElementsDao.addScoreCount(list.get(i), score);
        }
    }

    //-------------------------------------更新区------------------------------------------
    //th-查询基本数据表
    @Override
    public R queryAllElement() {
        R ok = R.ok();
        ok.put("data", treeUtil.getQhseElementTree(qhseManageSysElementsDao.queryQhseElements()));
        return ok;
    }

    //th-查询年度要素
    @Override
    public R queryYearElement(YearElementsDto yearElementsDto) {
        R ok = R.ok();
        ok.put("data", treeUtil.getQhseYearElementTree(qhseManageSysElementsDao.queryQhseYearElements(yearElementsDto)));
        return ok;
    }

    //th-查询基本数据表两级
    @Override
    public R queryChildElement() {
        R ok = R.ok();
        ok.put("data", treeUtil.getQhseElementTree(qhseManageSysElementsDao.queryQhseChildElements()));
        return ok;
    }

    //th---根据是否启用查询节点
    @Override
    public R queryAllElements(Integer status) {
        if (status == 0) //查启用
        {
            R ok = R.ok();
            ok.put("data", treeUtil.getQhseElementTree(qhseManageSysElementsDao.queryQhseElements()));
            return ok;
        } else if (status == 1) //查所有
        {
            R ok = R.ok();
            ok.put("data", treeUtil.getQhseElementTree(qhseManageSysElementsDao.queryQhseAllElements()));
            return ok;
        } else {
            throw new WLHSException("查询失败");
        }

    }

    //th---跟新状态
    //TODO 最大层数，编码位数
    @Override
    public R updateElementStatus(QhseElementsPojo rule) {
        String code = rule.getCode();
        Integer len = code.length();
        String status = rule.getStatus();
        List<String> list = getParent(code);
        if (status.equals("停用")) {
            setOff("停用", code);
            if (len == 15) {//是叶子节点
                int score = getScore(code);
                for (int i = 0; i < list.size(); i++) {
                    updateScoreCount(list.get(i), score, 1);
                }
            } else {//不是叶子节点
                Integer score = sumScore(code); //score: null
                Integer count = sumCount(code);//count: 0
                toZero(code);//将当前项和子项的分数和项数都归零
                for (int i = 0; i < list.size(); i++) {//该节点所有分母依次减分减项数
                    updateScoreCount(list.get(i), score, count);
                }
            }
        } else if (status.equals("启用")) {
            for (int i = 0; i < list.size(); i++) {
                setOn("启用", list.get(i));
            }
            setOn("启用", code);
            if (len == 15) {
                int score = getScore(code);
                addScoreCount(list, score);
            }
        }
        return R.ok();

    }

    //th---更新内容
    //TODO 最大层数，编码位数
    @Override
    public R updateElementcontent(QhseElementsPojo qhseManageSysElement) {
        String code = qhseManageSysElement.getCode();
        Integer len = code.length();
        String status = qhseManageSysElementsDao.querryStatus(code);
        if ("启用".equals(status)) {
            if (len == 15) //如果是叶子节点
                {
                Integer score = qhseManageSysElementsDao.querryScore(code);
                Integer newScore = qhseManageSysElement.getInitialScore() - score;
                if (newScore != 0) {
                    List<String> parentCode = new ArrayList<String>();
                    while (len > 3) {//所有父节点
                        len -= 3;
                        String str = code.substring(0, len);
                        parentCode.add(str);
                    }
                    for (int i = 0; i < parentCode.size(); i++) {
                        String pcode = parentCode.get(i);
                        if (qhseManageSysElementsDao.addInitialScore(pcode, newScore) <= 0)
                            throw new WLHSException("更新失败");
                    }
                }
            }
        }
        if (qhseManageSysElementsDao.updateElement(qhseManageSysElement) <= 0)
            throw new WLHSException("更新失败");
        return R.ok();

    }

    //th---添加节点内容
    @Override
    public R addElement(QhseElementsPojo qhseManageSysElement) {
        String parentCode = qhseManageSysElement.getCode();//传入的code都是插入的这一级的父节点编码
        try {
            if (parentCode == null || "".equals(parentCode)) //如果是插入的第一级，父节点就是空字符
            {
                String maxCode = qhseManageSysElementsDao.querryLastQHSEChildCode2("");//获得当前插入这一级的最大编码
                Integer maxNum = Integer.parseInt(maxCode);//编码转成数字
                Integer num = maxNum + 1;                 //加一
                String numCode = Integer.toString(num);//数字转成编码
                if (num < 10)                                     //-------数字换成编码，由于编码是三位，可能需要补0；
                    qhseManageSysElement.setCode("00" + numCode);
                else if (num < 100)
                    qhseManageSysElement.setCode("0" + numCode);
                else
                    qhseManageSysElement.setCode(numCode);
                qhseManageSysElement.setTotalCount(0);//设置叶子总是
                qhseManageSysElement.setInitialScore(0);//设置分数
            } else {                                          //如果插入是非第一级和最后一级
                Integer len = parentCode.length();
                if (len < 12) {
                    qhseManageSysElement.setTotalCount(0);
                    qhseManageSysElement.setInitialScore(0);
                    String maxCode = qhseManageSysElementsDao.querryLastQHSEChildCode2(parentCode); //查找插入那级最大编码
                    if (maxCode == null || "".equals(maxCode))//如果还没节点，直接生成
                        qhseManageSysElement.setCode(parentCode + "001");
                    else {                                      //否则最大编码加1
                        String lastTwoCode = maxCode.substring(maxCode.length() - 3, maxCode.length());
                        Integer lastTwoNum = Integer.parseInt(lastTwoCode);
                        Integer num = lastTwoNum + 1;
                        String numCode = Integer.toString(num);
                        if (num < 10)
                            qhseManageSysElement.setCode(parentCode + "00" + numCode);
                        else if (num < 100)
                            qhseManageSysElement.setCode(parentCode + "0" + numCode);
                        else
                            qhseManageSysElement.setCode(parentCode + numCode);
                    }
                } else if (len == 12) {  //插入是叶子节点
                    qhseManageSysElement.setTotalCount(1);
                    String maxCode = qhseManageSysElementsDao.querryLastQHSEChildCode2(parentCode);
                    if (maxCode == null || "".equals(maxCode))
                        qhseManageSysElement.setCode(parentCode + "001");
                    else {
                        String lastTwoCode = maxCode.substring(maxCode.length() - 3, maxCode.length());
                        Integer lastTwoNum = Integer.parseInt(lastTwoCode);
                        Integer num = lastTwoNum + 1;
                        String numCode = Integer.toString(num);
                        if (num < 10)
                            qhseManageSysElement.setCode(parentCode + "00" + numCode);
                        else if (num < 100)
                            qhseManageSysElement.setCode(parentCode + "0" + numCode);
                        else
                            qhseManageSysElement.setCode(parentCode + numCode);
                    }

                    Integer len1 = len;              //修改叶子节点所有的父节点的分数和节点数
                    Integer initialScore = qhseManageSysElement.getInitialScore();
                    List<String> pCode = new ArrayList<String>();
                    while (len1 > 0) {//获得所有父节点
                        String str = parentCode.substring(0, len1);
                        pCode.add(str);
                        len1 -= 3;
                    }
                    for (int i = 0; i < pCode.size(); i++) {
                        String code = pCode.get(i);
                        if (qhseManageSysElementsDao.addTotalCount(code, 1) <= 0)
                            throw new WLHSException("更新失败");
                        if (qhseManageSysElementsDao.addInitialScore(code, initialScore) <= 0)
                            throw new WLHSException("更新失败");
                    }
                }
            }
            if (qhseManageSysElementsDao.addQHSEElement(qhseManageSysElement) <= 0)
                throw new WLHSException("新增失败");
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            throw new WLHSException("新增失败");
        }

    }

    @Override
    public R querryQhseProblemDiscription(String code) {
        R ok = R.ok();
        ok.put("data", qhseManageSysElementsDao.querryDescriptionBycode(code));
        return ok;

    }
    //根据ID删除对应的问题描述
    @Override
    public R deleteQhseProblemDiscription(Integer id) {
        if(qhseManageSysElementsDao.deleteDescriptionById(id)<=0)
            throw new WLHSException("删除失败");
        return R.ok();
    }

    @Override
    public R updateQhseProblemDiscription(QHSEproblemDiscriptionDto qHSEproblemDiscriptionDto) {
        if(qhseManageSysElementsDao.updateDescriptionById(qHSEproblemDiscriptionDto)<=0)
            throw new WLHSException("更新失败");
        return R.ok();
    }

    @Override
    public R addQhseProblemDiscription(QHSEproblemDiscriptionDto qHSEproblemDiscriptionDto) {
        String code=qHSEproblemDiscriptionDto.getCode();
        String description=qHSEproblemDiscriptionDto.getDescription();
        if(qhseManageSysElementsDao.addProblemDescription(code,description)<=0)
            throw new WLHSException("添加失败");
        return R.ok();
    }

    @Transactional
    @Override
    public R addYearElement(YearElementsDto yearElementsDto) {
        try {
            String[] codes = yearElementsDto.getCodes().split(";");
            System.out.println(codes);
            List<YearElementsDto> list = new ArrayList<>();
            Integer id = yearElementsDto.getQhseCompanyYearManagerSysElementTableID();
            String companyCode = yearElementsDto.getCompanyCode();
            String companyName = yearElementsDto.getCompanyName();
            String year = yearElementsDto.getYear();
            Integer len = qhseManageSysElementsDao.findMaxLen();
            //TODO Refactor add new element logic
            //新增先查询tableid是否存在数据，存在先删除再新增，不存在才直接新增
            List<YearElementsDto> yearElementsDtos = qhseManageSysElementsDao.queryByTableID(yearElementsDto.getQhseCompanyYearManagerSysElementTableID());
            if(yearElementsDtos.size()>0) {//删除
                qhseManageSysElementsDao.deleteByTableID(yearElementsDto.getQhseCompanyYearManagerSysElementTableID());
            }
            for (String code : codes) {
                List<YearElementsDto> temp = qhseManageSysElementsDao.queryElementsByCode(code);
                for (int i = 0; i < temp.size(); i++) {
                    if (len.equals(temp.get(i).getCode().length())) {//长度相等为最后一级节点
                        temp.get(i).setStatus("未提供");
                        temp.get(i).setFileCheckStatus("未审核");
                    }
                    temp.get(i).setQhseCompanyYearManagerSysElementTableID(id);
                    temp.get(i).setCompanyCode(companyCode);
                    temp.get(i).setCompanyName(companyName);
                    temp.get(i).setYear(year);
                    list.add(temp.get(i));
                }
            }
            //执行新增操作
            int result = 0;
            for (int i = 0; i < list.size(); i++) {
                result = qhseManageSysElementsDao.addYearElement(list.get(i));
                if (result <= 0) break;
            }
            System.out.println(list);
            if (result <= 0)
                throw new WLHSException("新增失败");
        }catch (Exception e) {
            e.printStackTrace();
            throw new WLHSException("新增失败");
        }
        return R.ok();
    }
}
