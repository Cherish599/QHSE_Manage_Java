package com.wlhse.service.impl;


import com.wlhse.cache.JedisClient;
import com.wlhse.dao.QHSEManageSysElementsDao;
import com.wlhse.dao.QHSETaskDao;
import com.wlhse.dao.QhseElementsInputDao;
import com.wlhse.dto.TaskStatusDto;
import com.wlhse.dto.inDto.ElementEvidenceAttachInDto;
import com.wlhse.entity.ElementInputFileInfo;
import com.wlhse.exception.WLHSException;
import com.wlhse.service.QhseElementsInputService;
import com.wlhse.util.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class QhseElmentsInputServiceImpl implements QhseElementsInputService {
    @Resource
    QhseElementsInputDao qhseElementsInputDao;

    @Value("${RESOURCES_QHSE_ElementInput_Evidence_URL}")
    private String url;

    @Resource
    JedisClient jedisClient;
    @Resource
    QHSEManageSysElementsDao elementsDao;
    @Resource
    QHSETaskDao taskDao;

    @Override
    @Transactional
    public R addElementEvidenceAttach(ElementEvidenceAttachInDto elementEvidenceAttachInDto) {
        //第一次录入
        //获取tableId
        int tableId = qhseElementsInputDao.getQHSEYearManagerTableIdByElementId(elementEvidenceAttachInDto.getId());
        if (qhseElementsInputDao.query(elementEvidenceAttachInDto) == null) {
            qhseElementsInputDao.add(elementEvidenceAttachInDto);
            //将附件attach对应id放入elementFileInfo
            if(elementEvidenceAttachInDto.getAttach()!=null&&!"".equals(elementEvidenceAttachInDto.getAttach())) {
            String[] strs=elementEvidenceAttachInDto.getAttach().split(";");
            ElementInputFileInfo elementInputFileInfo = new ElementInputFileInfo();
            elementInputFileInfo.setQHSE_CompanyYearManagerSysElementEvidence_ID(elementEvidenceAttachInDto.getEvidenceID());
                for (String str : strs) {
                    elementInputFileInfo.setNewElementFileName(str);
                    qhseElementsInputDao.updateNewOriginFileName(elementInputFileInfo);
                }
            }
            qhseElementsInputDao.addAttach(elementEvidenceAttachInDto);
            if (jedisClient.get("TInput"+tableId)==null){
                jedisClient.set("TInput"+tableId,String.valueOf(1));
            }
            else {
                jedisClient.set("TInput"+tableId,String.valueOf(Integer.valueOf(jedisClient.get("TInput"+tableId))+1));
            }
            if (jedisClient.get("T"+tableId)==null){
                int allLeafNodeNumber = elementsDao.getAllLeafNodeNumber(tableId);
                jedisClient.set("T"+tableId,String.valueOf(allLeafNodeNumber));
            }
            else {
                if (jedisClient.get("TInput"+tableId).equals(jedisClient.get("T"+tableId))){
                    //所有要素证据录入完成，更改任务状态
                    TaskStatusDto taskStatusDto=new TaskStatusDto(tableId,"审核中");
                    taskDao.updateTaskStatusByTableId(taskStatusDto);
                }
            }
        } else {
            //打回后重新录入
            //将附件attach对应id放入elementFileInfo
            String s = jedisClient.get("TNoInput" + tableId);
            if(s!=null&&!"".equals(s)){
                jedisClient.set("TNoInput"+tableId,Integer.valueOf(s)-1+"");
                if(Integer.valueOf(s)-1==0){
                    //打回的元素全部重新录入完毕
                    taskDao.updateCheckStatus(tableId,null);
                    //清除缓存
                    jedisClient.delManyCahce("TNoInput"+tableId,0);
                }
            }

            if(elementEvidenceAttachInDto.getAttach()!=null&&!"".equals(elementEvidenceAttachInDto.getAttach())) {
                String[] strs = elementEvidenceAttachInDto.getAttach().split(";");
                ElementInputFileInfo elementInputFileInfo = new ElementInputFileInfo();
                elementInputFileInfo.setQHSE_CompanyYearManagerSysElementEvidence_ID(elementEvidenceAttachInDto.getEvidenceID());
                for (String str : strs) {
                    elementInputFileInfo.setNewElementFileName(str);
                    qhseElementsInputDao.updateNewOriginFileName(elementInputFileInfo);
                }
            }
            int i= qhseElementsInputDao.update(elementEvidenceAttachInDto);
            int j = qhseElementsInputDao.updateAttach(elementEvidenceAttachInDto);
            if (i * j < 0) throw new WLHSException("更新失败");
        }
        //qhseElementsInputDao.updateStatus(elementEvidenceAttachInDto.getId());//需求改为提交后状态变为审核
        return R.ok();
    }

    @Override
    public R queryAll(ElementEvidenceAttachInDto elementEvidenceAttachInDto) {
        ElementEvidenceAttachInDto elementEvidenceAttachInDtos=qhseElementsInputDao.query(elementEvidenceAttachInDto);
       if(elementEvidenceAttachInDtos!=null) elementEvidenceAttachInDtos.setUrl(url);
        R ok = R.ok();
        ok.put("data",elementEvidenceAttachInDtos);
        return ok;
    }

    @Override
    public String queryOriginFileName(String newElementFileName) {

        return qhseElementsInputDao.queryOriginFileName(newElementFileName);
    }

    @Override
    public void insertNewOriginFileName(ElementInputFileInfo elementInputFileInfo) {
       qhseElementsInputDao.insertNewOriginFileName(elementInputFileInfo);
    }


}


