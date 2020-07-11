package com.wlhse.service.impl;

import com.wlhse.dao.ElementReviewDao;
import com.wlhse.dto.inDto.ElementReviewDto;
import com.wlhse.dto.getDto.EmployeeDto;
import com.wlhse.dto.outDto.QHSECompanyYearManagerSysElementDto;
import com.wlhse.dto.outDto.QhseEvidenceAttatchDto;
import com.wlhse.exception.WLHSException;
import com.wlhse.service.ElementReviewService;
import com.wlhse.util.R;
import com.wlhse.util.TreeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ElementReviewServiceImpl implements ElementReviewService {
    @Resource
    private ElementReviewDao elementReviewDao;

    @Resource
    private TreeUtil treeUtil;

    @Override
    public R query(ElementReviewDto elementReviewDto) {
        R ok = R.ok();
        ok.put("data", treeUtil.getCurrentQhseElementTree1(elementReviewDao.query(elementReviewDto)));
        return ok;
    }

    @Override
    public R updateStatus(ElementReviewDto elementReviewDto) {
        if (elementReviewDao.update(elementReviewDto) <= 0)
            throw new WLHSException("更新失败");
        return R.ok();
    }

    @Override
    public R queryAll(Integer id) {
        QhseEvidenceAttatchDto qhseEvidenceAttatchDto = elementReviewDao.queryAll(id);
        Map<String, Object> map = new HashMap<>();
        map.put("data", qhseEvidenceAttatchDto);
        return R.ok(map);
    }
}
