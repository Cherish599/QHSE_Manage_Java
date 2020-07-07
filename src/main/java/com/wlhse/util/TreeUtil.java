package com.wlhse.util;

import com.wlhse.dao.ModuleDao;
import com.wlhse.dto.CheckListDto;
import com.wlhse.dto.CheckListTreeDto;
import com.wlhse.dto.TreeDto;
import com.wlhse.dto.outDto.MenuOutDto;
import com.wlhse.dto.outDto.RoleModuleOutDto;
import com.wlhse.entity.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;


@Component
//适配器
public class TreeUtil {

    @Resource
    private ModuleDao moduleDao;

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

    //checkListTree
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
                } else
                    treeDto.getChildren().add(entry.getValue());
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
            checkListTreeDto.setCheckContent(pojo.getCheckContent());
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


}
