package com.wlhse.service.impl;

import com.wlhse.dao.QhseElementsInputDao;
import com.wlhse.dto.inDto.ElementEvidenceAttachInDto;
import com.wlhse.entity.ElementInputFileInfo;
import com.wlhse.exception.WLHSException;
import com.wlhse.service.QhseElementsInputService;
import com.wlhse.util.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class QhseElmentsInputServiceImpl implements QhseElementsInputService {
    @Resource
    QhseElementsInputDao qhseElementsInputDao;

    @Value("${RESOURCES_QHSE_ElementInput_Evidence_URL}")
    private String url;


    @Override
    public R addElementEvidenceAttach(ElementEvidenceAttachInDto elementEvidenceAttachInDto) {
        if (qhseElementsInputDao.query(elementEvidenceAttachInDto) == null) {
            qhseElementsInputDao.add(elementEvidenceAttachInDto);
            //将附件attach对应id放入elementFileInfo
            String[] strs=elementEvidenceAttachInDto.getAttach().split(";");
            ElementInputFileInfo elementInputFileInfo = new ElementInputFileInfo();
            elementInputFileInfo.setQHSE_CompanyYearManagerSysElementEvidence_ID(elementEvidenceAttachInDto.getEvidenceID());
            for (String str:strs) {
                elementInputFileInfo.setNewElementFileName(str);
                qhseElementsInputDao.updateNewOriginFileName(elementInputFileInfo);
            }
            qhseElementsInputDao.addAttach(elementEvidenceAttachInDto);
            qhseElementsInputDao.updateStatus(elementEvidenceAttachInDto.getId());//更改状态审核
        } else {
            //将附件attach对应id放入elementFileInfo
            String[] strs=elementEvidenceAttachInDto.getAttach().split(";");
            ElementInputFileInfo elementInputFileInfo = new ElementInputFileInfo();
            elementInputFileInfo.setQHSE_CompanyYearManagerSysElementEvidence_ID(elementEvidenceAttachInDto.getEvidenceID());
            for (String str:strs) {
                elementInputFileInfo.setNewElementFileName(str);
                qhseElementsInputDao.updateNewOriginFileName(elementInputFileInfo);
            }
            int i= qhseElementsInputDao.update(elementEvidenceAttachInDto);
            int j = qhseElementsInputDao.updateAttach(elementEvidenceAttachInDto);
            if (i * j < 0) throw new WLHSException("更新失败");
        }
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


