package com.wlhse.service.impl;

import com.wlhse.dao.QHSEManageSysElementsDao;
import com.wlhse.dao.QualityElementReviewDao;
import com.wlhse.dto.inDto.YearElementsDto;
import com.wlhse.entity.QualityInputAttachPojo;
import com.wlhse.entity.QualityManergerSysElementPojo;
import com.wlhse.service.QualityElementReviewServer;
import com.wlhse.util.R;
import com.wlhse.util.TreeUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author:melon
 * Origin:2020/10/5
 **/
@Service
public class QualityElementReviewServiceImp  implements QualityElementReviewServer {
    @Resource
    QualityElementReviewDao qualityElementReviewDao;
    @Resource
    TreeUtil treeUtil;
    @Value("${RESOURCES_Quality_ElementInput_Evidence_URL}")
    private String url;
    @Resource
    QHSEManageSysElementsDao qhseManageSysElementsDao;

    @Override
    public R query(String companyCode, String year) {
        List<QualityManergerSysElementPojo> lists = qualityElementReviewDao.query(companyCode,year);
        for (QualityManergerSysElementPojo pojo:lists) {
            int sums=qhseManageSysElementsDao.querySchedule1(pojo.getCode(),pojo.getCompanyCode(),pojo.getYear());
            int num=qhseManageSysElementsDao.querySchdules1(pojo.getCode(),pojo.getCompanyCode(),pojo.getYear());
            int num1=sums-num;
            if(pojo.getCode().length()!=12)//树的最大编码
                pojo.setSchedule(num1+"/"+sums);
        }
        R ok = R.ok();
       return ok.put("data",treeUtil.getCurrentQualityElementTree(lists));
    }

    @Override
    public R queryAttach(Integer id) {
        QualityInputAttachPojo qualityInputAttachPojo=qualityElementReviewDao.queryAttach(id);
        qualityInputAttachPojo.setUrl(url);
        return R.ok().put("data",qualityInputAttachPojo);
    }

    @Override
    public R insertAttach(QualityInputAttachPojo qualityInputAttachPojo) {
        QualityInputAttachPojo pojo=qualityElementReviewDao.queryAttach(qualityInputAttachPojo.getQuality_CompanyYearManagerSysElement_ID());
        if(pojo==null) qualityElementReviewDao.insertAttach(qualityInputAttachPojo);
        else  qualityElementReviewDao.updateAttach(qualityInputAttachPojo);
        return R.ok();
    }


}
