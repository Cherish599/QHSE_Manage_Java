package com.wlhse.service.impl;

import com.wlhse.dao.ElementReviewDao;
import com.wlhse.dto.inDto.ElementReviewDto;
import com.wlhse.dto.outDto.QHSECompanyYearManagerSysElementDto;
import com.wlhse.dto.outDto.QhseEvidenceAttatchDto;
import com.wlhse.exception.WLHSException;
import com.wlhse.service.ElementReviewService;
import com.wlhse.util.R;
import com.wlhse.util.TreeUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ElementReviewServiceImpl implements ElementReviewService {
    @Resource
    private ElementReviewDao elementReviewDao;

    @Resource
    private TreeUtil treeUtil;

    @Value("${RESOURCES_QHSE_ElementInput_Evidence_URL}")
    private String url;

    @Override
    public R query(ElementReviewDto elementReviewDto) {
    List<QHSECompanyYearManagerSysElementDto> lists=elementReviewDao.query(elementReviewDto);
        List<String> codes = new ArrayList<>() ;
        if(lists!=null && !lists.isEmpty()){
            for (int i=0;i<lists.size();i++) codes.add(lists.get(i).getCode().substring(0,lists.get(i).getCode().length() - 3));
            //去重父节点
            for (int i = 0; i < codes.size() - 1; i++) {
                for (int j = codes.size() - 1; j > i; j--) {
                    if (codes.get(j).equals(codes.get(i))) codes.remove(j);
                }
            }
            //查询父节点并插入
            for (String code : codes) {
                lists.add(elementReviewDao.queryParent(code));
            }
        }
        R ok = R.ok();
        ok.put("data", treeUtil.getCurrentQhseElementTree1(lists));
        return ok;
    }

    @Override
    public R queryS(ElementReviewDto elementReviewDto) {
        List<QHSECompanyYearManagerSysElementDto> lists=elementReviewDao.queryS(elementReviewDto);
        List<String> codes = new ArrayList<>() ;
        if(lists!=null && !lists.isEmpty()){
            for (int i=0;i<lists.size();i++) codes.add(lists.get(i).getCode().substring(0,lists.get(i).getCode().length() - 3));
            //去重父节点
            for (int i = 0; i < codes.size() - 1; i++) {
                for (int j = codes.size() - 1; j > i; j--) {
                    if (codes.get(j).equals(codes.get(i))) codes.remove(j);
                }
            }
            //查询父节点并插入
            for (String code : codes) {
                lists.add(elementReviewDao.queryParent(code));
            }
        }
        R ok = R.ok();
        ok.put("data", treeUtil.getCurrentQhseElementTree1(lists));
        return ok;
    }

    @Override
    public R updateStatus(ElementReviewDto elementReviewDto) {
        if (elementReviewDao.update(elementReviewDto) <= 0)
            throw new WLHSException("更新失败");
        return R.ok();
    }


    @Override
    public R queryAll(QhseEvidenceAttatchDto qhseEvidenceAttatchDto) {
        qhseEvidenceAttatchDto.setUrl(url);
        List<QhseEvidenceAttatchDto> qhseEvidenceAttatchDtos = elementReviewDao.queryAll(qhseEvidenceAttatchDto);
        QhseEvidenceAttatchDto returnPojo=new QhseEvidenceAttatchDto();
        if(qhseEvidenceAttatchDtos!=null && !qhseEvidenceAttatchDtos.isEmpty()){
            Long dates[] = new Long[qhseEvidenceAttatchDtos.size()];
            for (int i = 0; i <qhseEvidenceAttatchDtos.size(); i++) {
                dates[i] = qhseEvidenceAttatchDtos.get(i).getUploadTime().getTime();
            }
            Long maxIndex = dates[0];// 定义最大值为该数组的第一个数
            int j,k = 0;
            for (j = 1; j < dates.length; j++) {
                if (maxIndex <=dates[j]){
                    maxIndex = dates[j];
                    k=j;
                }
            }
            returnPojo = qhseEvidenceAttatchDtos.get(k);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("data", returnPojo);
        return R.ok(map);
    }

    @Override
    public QHSECompanyYearManagerSysElementDto queryParent(String code) {
        return elementReviewDao.queryParent(code);
    }
}
