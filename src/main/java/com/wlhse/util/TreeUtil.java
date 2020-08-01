package com.wlhse.util;

import com.wlhse.dao.CheckListDao;
import com.wlhse.dao.ModuleDao;
import com.wlhse.dao.QHSEManageSysElementsDao;
import com.wlhse.dto.*;
import com.wlhse.dto.inDto.YearElementsDto;
import com.wlhse.dto.outDto.*;
import com.wlhse.entity.*;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;


@Component
//适配器
public class TreeUtil {

    @Resource
    private ModuleDao moduleDao;

    @Resource
    QHSEManageSysElementsDao qhseManageSysElementsDao;

    @Resource
    private CheckListDao checkListDao;

    public List<TreeDto> GetModuleTree(List<ModulePojo> pojos) {
        Map<String, TreeDto> map1 = new TreeMap<>();
        List<Integer> code = new ArrayList<>();
        for (ModulePojo pojo : pojos) {
            TreeDto treeDto = new TreeDto();
            treeDto.setLabel(pojo.getName());
            treeDto.setNodeCode(pojo.getModuleCode());
            map1.put(treeDto.getNodeCode(), treeDto);

            if (code.indexOf(pojo.getModuleCode().length()) == -1)
                code.add(pojo.getModuleCode().length());
        }
        return returnList(map1, code);
    }
    // clone() 修改
    public List<TreeDto> GetRoleModuleTree(List<RoleModulePojo> pojos) {
        Map<String, TreeDto> map1 = new TreeMap<>();
        List<Integer> code = new ArrayList<>();
        for (RoleModulePojo pojo : pojos) {
            TreeDto treeDto = new TreeDto();
            ModulePojo modulePojo = moduleDao.queryByModuleCode(pojo.getModuleCode());
            treeDto.setLabel(modulePojo.getName());
            treeDto.setNodeCode(pojo.getModuleCode());
            map1.put(treeDto.getNodeCode(), treeDto);

            if (code.indexOf(pojo.getModuleCode().length()) == -1)
                code.add(pojo.getModuleCode().length());
        }
        return returnList(map1, code);
    }

    public List<TreeDto> getDataDictTree(List<DataDictPojo> dataDictPojos) {
        Map<String, TreeDto> map1 = new TreeMap<>();
        List<Integer> code = new ArrayList<>();
        for (DataDictPojo pojo : dataDictPojos) {
            TreeDto treeDto = new TreeDto();
            treeDto.setLabel(pojo.getName());
            treeDto.setNodeCode(pojo.getDictCode());
            map1.put(treeDto.getNodeCode(), treeDto);

            if (code.indexOf(pojo.getDictCode().length()) == -1)
                code.add(pojo.getDictCode().length());
        }
        return returnList(map1, code);
    }


    private static final String staus = "停用";


    public List<TreeDto> getCompanyTree(List<CompanyPojo> companyPojo) {
        Map<String, TreeDto> map1 = new TreeMap<>();
        List<Integer> code = new ArrayList<>();
        for (CompanyPojo pojo : companyPojo) {
            TreeDto treeDto = new TreeDto();
            treeDto.setId(pojo.getCompanyCode());
            treeDto.setLabel(pojo.getName());
            treeDto.setNodeCode(pojo.getCompanyCode());
            map1.put(treeDto.getNodeCode(), treeDto);

            if (code.indexOf(pojo.getCompanyCode().length()) == -1)
                code.add(pojo.getCompanyCode().length());
        }
        System.out.println(map1);
        return returnList(map1, code);
    }

    public List<TreeDto> getQhseCompanyTree(List<CompanyPojo> companyPojo) {
        Map<String, TreeDto> map1 = new TreeMap<>();
        List<Integer> code = new ArrayList<>();
        for (CompanyPojo pojo : companyPojo) {
            TreeDto treeDto = new TreeDto();
            treeDto.setId(pojo.getSysCompanyID().toString());
            treeDto.setLabel(pojo.getName());
            treeDto.setNodeCode(pojo.getCompanyCode());
            map1.put(treeDto.getNodeCode(), treeDto);

            if (code.indexOf(pojo.getCompanyCode().length()) == -1)
                code.add(pojo.getCompanyCode().length());
        }
        System.out.println(map1);
        return returnList(map1, code);
    }

    public List<QHSECompanySysElementsPojo> getQHSEReportTree(List<QHSECompanySysElementsPojo> list) {
        Map<String,QHSECompanySysElementsPojo> map=new TreeMap<>();
        List<String> codes=new LinkedList<>();
        for(QHSECompanySysElementsPojo node : list){
            map.put(node.getCode(),node);
            codes.add(node.getCode());
        }
        return returnQHSEList(map,codes);
    }

    public List<TreeDto> getRoleModuleOutDto(List<RoleModuleOutDto> roleModuleOutDtos) {
        Map<String, TreeDto> map1 = new TreeMap<>();
        List<Integer> code = new ArrayList<>();
        for (RoleModuleOutDto pojo : roleModuleOutDtos) {
            TreeDto treeDto = new TreeDto();
            treeDto.setLabel(pojo.getLabel());
            treeDto.setNodeCode(pojo.getModuleCode());
            treeDto.setuRl(pojo.getuRL());
            treeDto.setImg(pojo.getImg());
            map1.put(treeDto.getNodeCode(), treeDto);

            if (code.indexOf(pojo.getModuleCode().length()) == -1)
                code.add(pojo.getModuleCode().length());
        }
        return returnList(map1, code);
}

    public List<TreeDto> returnList(Map<String, TreeDto> map, List<Integer> code) {
        List<TreeDto> result = new ArrayList<>();
        Collections.sort(code);
        for (Map.Entry<String, TreeDto> entry : map.entrySet()) {
            String key = entry.getKey();
            if (key.length() == code.get(0))//第一层
                result.add(entry.getValue());
            else {
                TreeDto treeDto = map.get(key.substring(0, code.get(code.indexOf(key.length()) - 1)));//获取父节点
                if (null == treeDto)
                    continue;
                if (null == treeDto.getChildren()) {
                    List<TreeDto> tmp = new ArrayList<>();
                    tmp.add(entry.getValue());
                    treeDto.setChildren(tmp);
                } else
                    treeDto.getChildren().add(entry.getValue());
            }
        }
        return result;
    }

    public List<MenuOutDto> returnMenuOutDtoList(Map<String, MenuOutDto> map1, int parentLength) {
        List<MenuOutDto> list = new ArrayList<>();
        Map<String, MenuOutDto> treeMap = new TreeMap<String, MenuOutDto>(map1);
        for (Map.Entry<String, MenuOutDto> entry : treeMap.entrySet()) {
            if (entry.getKey().length() == parentLength) {
                list.add(entry.getValue());
            }
        }
        for (Map.Entry<String, MenuOutDto> entry : treeMap.entrySet()) {
            String code = entry.getKey();
            if (code.length() > parentLength) {
                code = code.substring(0, parentLength);
                MenuOutDto parentTree = map1.get(code);
                if (parentTree != null) {
                    if (parentTree.getChildren() != null) {
                        parentTree.getChildren().add(entry.getValue());
                    } else {
                        List<MenuOutDto> list1 = new ArrayList<>();
                        list1.add(entry.getValue());
                        parentTree.setChildren(list1);
                    }

                }
            }
        }
        return list;
    }

    public List<QHSECompanySysElementsPojo> returnQHSEList(Map<String, QHSECompanySysElementsPojo> map, List<String> codes){
        List<QHSECompanySysElementsPojo> result = new ArrayList<>();
        for(String code: codes){
            QHSECompanySysElementsPojo node=map.get(code);
            if(code.length()==2){//第一层
                result.add(node);
            }else{//后面几层
                String parentCode=code.substring(0,code.length()-2);
                map.get(parentCode).getChildNode().add(node);
            }
        }
        return result;
    }

    //th---checkListTree
    public List<CheckListTreeDto> returnCheckList(Map<String, CheckListTreeDto> map, List<Integer> code) {
        List<CheckListTreeDto> result = new ArrayList<>();
        Collections.sort(code);
        for (Map.Entry<String, CheckListTreeDto> entry : map.entrySet()) {
            String key = entry.getKey();
            if (key.length() == code.get(0))
                result.add(entry.getValue());
            else {
                CheckListTreeDto treeDto = map.get(key.substring(0, code.get(code.indexOf(key.length()) - 1)));
                if (null == treeDto)
                    continue;
                if (null == treeDto.getChildren()) {
                    List<CheckListTreeDto> tmp = new ArrayList<>();
                    tmp.add(entry.getValue());
                    treeDto.setChildren(tmp);
                } else {
                    //找到节点中长度最长的那一个
                    CheckListTreeDto value = entry.getValue();
                    treeDto.getChildren().add(entry.getValue());
                }
            }
        }
        return result;
    }

    public List<CheckListTreeDto> getCheckListTree(List<CheckListDto> checkListDtos) {
        Map<String, CheckListTreeDto> map1 = new TreeMap<>();
        List<Integer> code = new ArrayList<>();
        for (CheckListDto pojo : checkListDtos) {
            CheckListTreeDto checkListTreeDto = new CheckListTreeDto();
            checkListTreeDto.setCheckListID(pojo.getCheckListID());
            checkListTreeDto.setCheckListCode(pojo.getCheckListCode());
            checkListTreeDto.setCheckListName(pojo.getCheckListName());
            checkListTreeDto.setAttribute(pojo.getAttribute());
            checkListTreeDto.setParentName(pojo.getParentName());
            checkListTreeDto.setIsChildNode(pojo.getIsChildNode());
            checkListTreeDto.setStatus(pojo.getStatus());
            map1.put(checkListTreeDto.getCheckListCode(), checkListTreeDto);

            //同一层节点长度一样
            if (code.indexOf(pojo.getCheckListCode().length()) == -1)
                code.add(pojo.getCheckListCode().length());
        }
        return returnCheckList(map1, code);
    }

    //new menu module

    public List<MenuOutDto> returnMenuList(Map<String, MenuOutDto> map, List<Integer> code) {
        List<MenuOutDto> result = new ArrayList<>();
        Collections.sort(code);
        for (Map.Entry<String, MenuOutDto> entry : map.entrySet()) {
            String key = entry.getKey();
            if (key.length() == code.get(0))
                result.add(entry.getValue());
            else {
                MenuOutDto treeDto = map.get(key.substring(0, code.get(code.indexOf(key.length()) - 1)));
                if (null == treeDto)
                    continue;
                if (null == treeDto.getChildren()) {
                    List<MenuOutDto> tmp = new ArrayList<>();
                    tmp.add(entry.getValue());
                    treeDto.setChildren(tmp);
                } else
                    treeDto.getChildren().add(entry.getValue());
            }
        }
        return result;
    }

    public List<MenuOutDto> getMenuTree(List<ModulePojo> list) {
        Map<String, MenuOutDto> map1 = new TreeMap<>();
        List<Integer> code = new ArrayList<>();
        for (ModulePojo pojo : list) {
            MenuOutDto menuOutDto = new MenuOutDto();
            menuOutDto.setRouteName(pojo.getUrl());
            menuOutDto.setIcon(pojo.getImg());
            menuOutDto.setTitle(pojo.getName());
            menuOutDto.setCode(pojo.getModuleCode());
            map1.put(pojo.getModuleCode(), menuOutDto);

            //同一层节点长度一样
            if (code.indexOf(pojo.getModuleCode().length()) == -1)
                code.add(pojo.getModuleCode().length());
        }
        return returnMenuList(map1, code);
    }


    //th---基础数据表
    public List<QhseElementsOutDto> returnQhseElementList(Map<String, QhseElementsOutDto> map, List<Integer> code) {
        List<QhseElementsOutDto> result = new ArrayList<>();
        Collections.sort(code);
        for (Map.Entry<String, QhseElementsOutDto> entry : map.entrySet()) {
            String key = entry.getKey();
            if (key.length() == code.get(0))
                result.add(entry.getValue());
            else {
                QhseElementsOutDto treeDto = map.get(key.substring(0, code.get(code.indexOf(key.length()) - 1)));
                if (null == treeDto)
                    continue;
                if (null == treeDto.getChildNode()) {
                    List<QhseElementsOutDto> tmp = new ArrayList<>();
                    tmp.add(entry.getValue());
                    treeDto.setChildNode(tmp);
                } else
                    treeDto.getChildNode().add(entry.getValue());
            }
        }
        return result;
    }

    public List<QhseElementsOutDto> getQhseElementTree(List<QhseElementsPojo> qhseElementsPojos) {
        Map<String, QhseElementsOutDto> map1 = new TreeMap<>();
        List<Integer> code = new ArrayList<>();
        for (QhseElementsPojo pojo : qhseElementsPojos) {
            QhseElementsOutDto qhseElementsOutDto = new QhseElementsOutDto();
            qhseElementsOutDto.setAuditMode(pojo.getAuditMode());
            qhseElementsOutDto.setCode(pojo.getCode());
            qhseElementsOutDto.setContent(pojo.getContent());
            qhseElementsOutDto.setTotalCount(pojo.getTotalCount());
            qhseElementsOutDto.setFormula(pojo.getFormula());
            qhseElementsOutDto.setInitialScore(pojo.getInitialScore());
            qhseElementsOutDto.setName(pojo.getName());
            qhseElementsOutDto.setStatus(pojo.getStatus());
            qhseElementsOutDto.setId(pojo.getQhseManagerSysElementID());
            map1.put(qhseElementsOutDto.getCode(), qhseElementsOutDto);

            //同一层节点长度一样
            if (code.indexOf(pojo.getCode().length()) == -1)
                code.add(pojo.getCode().length());
        }
        return returnQhseElementList(map1, code);
    }

    public List<QhseElementsOutDto> getQhseElementTreeForExcel(List<QhseElementsPojo> qhseElementsPojos) {
        Map<String, QhseElementsOutDto> map1 = new TreeMap<>();
        List<Integer> code = new ArrayList<>();
        for (QhseElementsPojo pojo : qhseElementsPojos) {
            QhseElementsOutDto qhseElementsOutDto = new QhseElementsOutDto();
            qhseElementsOutDto.setAuditMode(pojo.getAuditMode());
            qhseElementsOutDto.setCode(pojo.getCode());
            qhseElementsOutDto.setContent(pojo.getContent());
            qhseElementsOutDto.setTotalCount(pojo.getTotalCount());
            qhseElementsOutDto.setFormula(pojo.getFormula());
            qhseElementsOutDto.setInitialScore(pojo.getInitialScore());
            qhseElementsOutDto.setName(pojo.getName());
            qhseElementsOutDto.setStatus(pojo.getStatus());
            qhseElementsOutDto.setId(pojo.getQhseManagerSysElementID());
            if(pojo.getCode().length()==15)//加入问题描述字段
            {
                qhseElementsOutDto.setProblemDescription(getProblemDescriptionByCode(pojo.getCode()));
            }
            map1.put(qhseElementsOutDto.getCode(), qhseElementsOutDto);

            //同一层节点长度一样
            if (code.indexOf(pojo.getCode().length()) == -1)
                code.add(pojo.getCode().length());
        }
        return returnQhseElementList(map1, code);
    }
    public String getProblemDescriptionByCode(String code)//拼接获得问题描述字段
    {
        List<QHSEproblemDiscriptionDto> DiscriptionList=qhseManageSysElementsDao.querryDescriptionBycode(code);
        String problemDescription="";
        for (int i = 0; i < DiscriptionList.size(); i++)
        {
            problemDescription+=(i+1+"."+DiscriptionList.get(i).getDescription());
        }
        return problemDescription;
    }

    //th---年度要素
    public List<QhseYearElementsOutDto> returnQhseYearElementList(Map<String, QhseYearElementsOutDto> map, List<Integer> code) {
        List<QhseYearElementsOutDto> result = new ArrayList<>();
        Collections.sort(code);
        for (Map.Entry<String, QhseYearElementsOutDto> entry : map.entrySet()) {
            String key = entry.getKey();
            if (key.length() == code.get(0))
                result.add(entry.getValue());
            else {
                QhseYearElementsOutDto treeDto = map.get(key.substring(0, code.get(code.indexOf(key.length()) - 1)));
                if (null == treeDto)
                    continue;
                if (null == treeDto.getChildNode()) {
                    List<QhseYearElementsOutDto> tmp = new ArrayList<>();
                    tmp.add(entry.getValue());
                    treeDto.setChildNode(tmp);
                } else
                    treeDto.getChildNode().add(entry.getValue());
            }
        }
        return result;
    }

    public List<QhseYearElementsOutDto> getQhseYearElementTree(List<YearElementsDto> qhseElementsPojos) {
        Map<String, QhseYearElementsOutDto> map1 = new TreeMap<>();
        List<Integer> code = new ArrayList<>();
        for (YearElementsDto pojo : qhseElementsPojos) {
            QhseYearElementsOutDto qhseElementsOutDto = new QhseYearElementsOutDto();
            qhseElementsOutDto.setAuditMode(pojo.getAuditMode());
            qhseElementsOutDto.setCode(pojo.getCode());
            qhseElementsOutDto.setContent(pojo.getContent());
            qhseElementsOutDto.setTotalCount(pojo.getTotalCount());
            qhseElementsOutDto.setFormula(pojo.getFormula());
            qhseElementsOutDto.setInitialScore(pojo.getInitialScore());
            qhseElementsOutDto.setName(pojo.getName());
            qhseElementsOutDto.setStatus(pojo.getStatus());
            qhseElementsOutDto.setId(pojo.getQhseCompanyYearManagerSysElementID());
            qhseElementsOutDto.setTableID(pojo.getQhseCompanyYearManagerSysElementTableID());
            qhseElementsOutDto.setCompanyCode(pojo.getCompanyCode());
            qhseElementsOutDto.setCompanyName(pojo.getCompanyName());
            qhseElementsOutDto.setYear(pojo.getYear());
            qhseElementsOutDto.setFileCheckStatus(pojo.getFileCheckStatus());
            map1.put(qhseElementsOutDto.getCode(), qhseElementsOutDto);

            //同一层节点长度一样
            if (code.indexOf(pojo.getCode().length()) == -1)
                code.add(pojo.getCode().length());
        }
        return returnQhseYearElementList(map1, code);
    }


    //th/lhl---单位年度管理体系要素表根据当前登陆人和年度单位查询
    public List<QhseElementsOutDto> returnCurrentQhseElementList(Map<String, QhseElementsOutDto> map, List<Integer> code) {
        List<QhseElementsOutDto> result = new ArrayList<>();
        Collections.sort(code);
        for (Map.Entry<String, QhseElementsOutDto> entry : map.entrySet()) {
            String key = entry.getKey();
            if (key.length() == code.get(0))
                result.add(entry.getValue());
            else {
                QhseElementsOutDto treeDto = map.get(key.substring(0, code.get(code.indexOf(key.length()) - 1)));
                if (null == treeDto)
                    continue;
                if (null == treeDto.getChildNode()) {
                    List<QhseElementsOutDto> tmp = new ArrayList<>();
                    tmp.add(entry.getValue());
                    treeDto.setChildNode(tmp);
                } else
                    treeDto.getChildNode().add(entry.getValue());
            }
        }
        return result;
    }

    public List<QhseElementsOutDto> getCurrentQhseElementTree(List<QHSECompanyYearManagerSysElementDto> qhseElementsPojos) {
        Map<String, QhseElementsOutDto> map1 = new TreeMap<>();
        List<Integer> code = new ArrayList<>();
        for (QHSECompanyYearManagerSysElementDto pojo : qhseElementsPojos) {
            QhseElementsOutDto qhseElementsOutDto = new QhseElementsOutDto();
            qhseElementsOutDto.setAuditMode(pojo.getAuditMode());
            qhseElementsOutDto.setCode(pojo.getCode());
            qhseElementsOutDto.setContent(pojo.getContent());
            qhseElementsOutDto.setTotalCount(pojo.getTotalCount());
            qhseElementsOutDto.setFormula(pojo.getFormula());
            qhseElementsOutDto.setInitialScore(pojo.getInitialScore());
            qhseElementsOutDto.setProblemDescription(pojo.getProblemDescription());
            qhseElementsOutDto.setName(pojo.getName());
            qhseElementsOutDto.setStatus(pojo.getStatus());
            qhseElementsOutDto.setId(pojo.getqHSE_CompanyYearManagerSysElement_ID());
            //System.out.println(pojo.getQhseManagerSysElementID());
            map1.put(qhseElementsOutDto.getCode(), qhseElementsOutDto);

            //同一层节点长度一样
            if (code.indexOf(pojo.getCode().length()) == -1)
                code.add(pojo.getCode().length());
        }
        return returnCurrentQhseElementList(map1, code);
    }

    //年度要素表树
    public List<QHSECompanyYearManagerSysElementDto> returnCurrentQhseElementList1(Map<String, QHSECompanyYearManagerSysElementDto> map, List<Integer> code) {
        List<QHSECompanyYearManagerSysElementDto> result = new ArrayList<>();
        Collections.sort(code);
        for (Map.Entry<String, QHSECompanyYearManagerSysElementDto> entry : map.entrySet()) {
            String key = entry.getKey();
            if (key.length() == code.get(0))
                result.add(entry.getValue());
            else {
                QHSECompanyYearManagerSysElementDto treeDto = map.get(key.substring(0, code.get(code.indexOf(key.length()) - 1)));
                if (null == treeDto)
                    continue;
                if (null == treeDto.getChildNode()) {
                    List<QHSECompanyYearManagerSysElementDto> tmp = new ArrayList<>();
                    tmp.add(entry.getValue());
                    treeDto.setChildNode(tmp);
                } else
                    treeDto.getChildNode().add(entry.getValue());
            }
        }
        return result;
    }

    public List<QHSECompanyYearManagerSysElementDto> getCurrentQhseElementTree1(List<QHSECompanyYearManagerSysElementDto> qhseElementsPojos) {
        Map<String, QHSECompanyYearManagerSysElementDto> map1 = new TreeMap<>();
        List<Integer> code = new ArrayList<>();
        for (QHSECompanyYearManagerSysElementDto pojo : qhseElementsPojos) {
            map1.put(pojo.getCode(), pojo);
            //存入长度相同的数字
            if (code.indexOf(pojo.getCode().length()) == -1)
                code.add(pojo.getCode().length());
        }
        return returnCurrentQhseElementList1(map1, code);
    }

    //checkrecord树状显示
    public List<CheckRecordTreeOutDto> returnCheckRecordTree(Map<String, CheckRecordTreeOutDto> map, List<Integer> code) {
        List<CheckRecordTreeOutDto> result = new ArrayList<>();
        Collections.sort(code);
        for (Map.Entry<String, CheckRecordTreeOutDto> entry : map.entrySet()) {
            String key = entry.getKey();
            if (key.length() == code.get(0))
                result.add(entry.getValue());
            else {
                CheckRecordTreeOutDto treeDto = map.get(key.substring(0, code.get(code.indexOf(key.length()) - 1)));
                if (null == treeDto)
                    continue;
                if (null == treeDto.getChildNode()) {
                    List<CheckRecordTreeOutDto> tmp = new ArrayList<>();
                    tmp.add(entry.getValue());
                    treeDto.setChildNode(tmp);
                } else
                    treeDto.getChildNode().add(entry.getValue());
            }
        }
        return result;
    }

    public List<CheckRecordTreeOutDto> getCheckRecordTree(List<CheckRecordTreeDto> checkRecordTreeDtos) {
        Map<String, CheckRecordTreeOutDto> map1 = new TreeMap<>();
        List<Integer> code = new ArrayList<>();
        for (CheckRecordTreeDto pojo : checkRecordTreeDtos) {
            CheckRecordTreeOutDto checkRecordTreeOutDto = new CheckRecordTreeOutDto();
            checkRecordTreeOutDto.setCheckDate(pojo.getCheckDate());
            checkRecordTreeOutDto.setCheckListCode(pojo.getCheckListCode());
            checkRecordTreeOutDto.setCheckListName(pojo.getCheckListName());
            checkRecordTreeOutDto.setCheckRecordID(pojo.getCheckRecordID());
            checkRecordTreeOutDto.setCompanyCode(pojo.getCompanyCode());
            checkRecordTreeOutDto.setCompanyName(pojo.getCompanyName());
            checkRecordTreeOutDto.setCheckType(pojo.getCheckType());
            checkRecordTreeOutDto.setProblems(pojo.getProblems());
            checkRecordTreeOutDto.setPass(pojo.getPass());
            checkRecordTreeOutDto.setCheckPersonId(pojo.getCheckPersonId());
            checkRecordTreeOutDto.setCheckPerson(pojo.getCheckPerson());
            checkRecordTreeOutDto.setCheckTypeCode(pojo.getCheckTypeCode());
            //System.out.println(pojo.getQhseManagerSysElementID());
            map1.put(checkRecordTreeOutDto.getCheckListCode(), checkRecordTreeOutDto);

            //同一层节点长度一样
            if (code.indexOf(pojo.getCheckListCode().length()) == -1)
                code.add(pojo.getCheckListCode().length());
        }
        return returnCheckRecordTree(map1, code);
    }

    //factor树状显示
    public List<FactorOutDto2> returnFactoryTree(Map<String, FactorOutDto2> map, List<Integer> code) {
        List<FactorOutDto2> result = new ArrayList<>();
        Collections.sort(code);
        for (Map.Entry<String, FactorOutDto2> entry : map.entrySet()) {
            String key = entry.getKey();
            if (key.length() == code.get(0))
                result.add(entry.getValue());
            else {
                FactorOutDto2 treeDto = map.get(key.substring(0, code.get(code.indexOf(key.length()) - 1)));
                if (null == treeDto)
                    continue;
                if (null == treeDto.getChildNode()) {
                    List<FactorOutDto2> tmp = new ArrayList<>();
                    tmp.add(entry.getValue());
                    treeDto.setChildNode(tmp);
                } else
                    treeDto.getChildNode().add(entry.getValue());
            }
        }
        return result;
    }

    public List<FactorOutDto2> getFactoryTree(List<FactorOutDto> checkRecordTreeDtos) {
        Map<String, FactorOutDto2> map1 = new TreeMap<>();
        List<Integer> code = new ArrayList<>();
        for (FactorOutDto pojo : checkRecordTreeDtos) {
            FactorOutDto2 factorOutDto2 = new FactorOutDto2();
            factorOutDto2.setId(pojo.getId());
            factorOutDto2.setFactorCode(pojo.getFactorCode());
            factorOutDto2.setFactorID(pojo.getFactorID());
            factorOutDto2.setName(pojo.getName());
            factorOutDto2.setRight(pojo.getRigth());
            factorOutDto2.setFactorHseCode(pojo.getFactorHseCode());
            factorOutDto2.setFactorObserverCode(pojo.getFactorObserverCode());
            factorOutDto2.setFactorSourceCode(pojo.getFactorSourceCode());
            factorOutDto2.setFactorDepartmentCode(pojo.getFactorDepartmentCode());
            map1.put(factorOutDto2.getFactorCode(), factorOutDto2);

            //同一层节点长度一样
            if (code.indexOf(pojo.getFactorCode().length()) == -1)
                code.add(pojo.getFactorCode().length());
        }
        return returnFactoryTree(map1, code);
    }


}
