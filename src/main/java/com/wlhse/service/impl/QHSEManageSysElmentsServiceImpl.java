package com.wlhse.service.impl;

import com.alibaba.fastjson.annotation.JSONType;
import com.wlhse.dao.QHSEManageSysElementsDao;
import com.wlhse.entity.QHSECompanySysElementsPojo;
import com.wlhse.entity.QHSEManageSysElements;
import com.wlhse.entity.QhseElementsPojo;
import com.wlhse.exception.WLHSException;
import com.wlhse.service.QHSEManageSysElementsService;
import com.wlhse.util.R;
import com.wlhse.util.TreeUtil;
import com.wlhse.util.state_code.NR;
import org.springframework.stereotype.Service;


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
    //th-------------------------------------更新区------------------------------------------
    //th-查询基本数据表
    @Override
    public R queryAllElement() {
        R ok = R.ok();
        ok.put("data", treeUtil.getQhseElementTree(qhseManageSysElementsDao.queryQhseElements()));
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
    public R queryAllElements(int status) {
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
        }else
        {
            throw new WLHSException("查询失败");
        }

    }

    //th---跟新状态
    @Override
    public String updateElementStatus(QhseElementsPojo rule) {
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
        return NR.r();

    }
    //th---更新内容
    @Override
    public String updateElementcontent(QhseElementsPojo qhseManageSysElement) {
        String code = qhseManageSysElement.getCode();
        Integer len = code.length();
        String status = qhseManageSysElementsDao.querryStatus(code);
        if ("启用".equals(status)) {
            if (len == 15) {
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
        return NR.r();

    }
    //th---添加节点内容
    @Override
    public String addElement(QhseElementsPojo qhseManageSysElement) {
        String parentCode = qhseManageSysElement.getCode();
        try {
            if (parentCode == null || "".equals(parentCode)) {
                String maxCode = qhseManageSysElementsDao.querryLastQHSEChildCode2("");
                Integer maxNum = Integer.parseInt(maxCode);
                Integer num = maxNum + 1;
                String numCode = Integer.toString(num);
                if (num < 10)
                    qhseManageSysElement.setCode("00" + numCode);
                else if (num < 100)
                    qhseManageSysElement.setCode("0" + numCode);
                else
                    qhseManageSysElement.setCode(numCode);
                qhseManageSysElement.setTotalCount(0);
                qhseManageSysElement.setInitialScore(0);
            } else {
                Integer len = parentCode.length();
                if (len < 12) {
                    qhseManageSysElement.setTotalCount(0);
                    qhseManageSysElement.setInitialScore(0);
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
                        else if(num<100)
                            qhseManageSysElement.setCode(parentCode + "0" + numCode);
                        else
                            qhseManageSysElement.setCode(parentCode + numCode);
                    }
                } else if (len == 12) {
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
                        else if(num<100)
                            qhseManageSysElement.setCode(parentCode + "0" + numCode);
                        else
                            qhseManageSysElement.setCode(parentCode + numCode);
                    }

                    Integer len1 = len;
                    Integer initialScore = qhseManageSysElement.getInitialScore();
                    List<String> pCode = new ArrayList<String>();
                    while (len1 > 0) {//所有父节点
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
            return NR.r();
        } catch (Exception e) {
            e.printStackTrace();
            throw new WLHSException("新增失败");
        }

    }


}
