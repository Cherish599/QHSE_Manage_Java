package com.wlhse.service.impl;

import com.wlhse.dao.ProblemDescriptionDao;
import com.wlhse.dao.QualityProblemDescriptionDao;
import com.wlhse.dto.QualityProblemDescriptionDto;
import com.wlhse.exception.WLHSException;
import com.wlhse.service.ProblemDescriptionService;
import com.wlhse.service.QualityProblemDescriptionService;
import com.wlhse.util.R;
import com.wlhse.util.state_code.NR;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class QualityProblemDescriptionServiceImpl implements QualityProblemDescriptionService {

    @Resource
    private QualityProblemDescriptionDao qualityProblemDescriptionDao;

    @Override
    public R addProblemDescription(QualityProblemDescriptionDto problemDescriptionDto) {
        try {
            qualityProblemDescriptionDao.addProblemDescription(problemDescriptionDto);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("添加失败");
        }
        return R.ok();
    }

    @Override
    public R deleteProblemDescription(QualityProblemDescriptionDto problemDescriptionDto) {

        try {
            qualityProblemDescriptionDao.deleteProblemDescription(problemDescriptionDto);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("删除失败");
        }
        return R.ok();
    }

    @Override
    public R updateProblemDescription(QualityProblemDescriptionDto problemDescriptionDto) {
        try {
            qualityProblemDescriptionDao.updateProblemDescription(problemDescriptionDto);
        }catch (Exception e) {
            e.printStackTrace();
            return R.error("更新失败");
        }
        return R.ok();
    }

    @Override
    public String queryProblemDescription(QualityProblemDescriptionDto problemDescriptionDto) {
        List<QualityProblemDescriptionDto> list = qualityProblemDescriptionDao.queryProblemDescription(problemDescriptionDto);
        return NR.r(list);
    }
}
