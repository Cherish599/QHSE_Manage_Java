package com.wlhse.service.impl;

import com.wlhse.dao.ProblemDescriptionDao;
import com.wlhse.dto.ProblemDescriptionDto;
import com.wlhse.exception.WLHSException;
import com.wlhse.service.ProblemDescriptionService;
import com.wlhse.util.R;
import com.wlhse.util.state_code.NR;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class ProblemDescriptionServiceImpl implements ProblemDescriptionService {

    @Resource
    private ProblemDescriptionDao problemDescriptionDao;

    @Override
    public R addProblemDescription(ProblemDescriptionDto problemDescriptionDto) {
        if(problemDescriptionDao.addProblemDescription(problemDescriptionDto)<=0)
            throw new WLHSException("新增失败");
        return R.ok();
    }

    @Override
    public R deleteProblemDescription(Integer id) {
        if(problemDescriptionDao.deleteProblemDescription(id)<=0)
            throw new WLHSException("删除失败");
        return R.ok();
    }

    @Override
    public R updateProblemDescription(ProblemDescriptionDto problemDescriptionDto) {
        if(problemDescriptionDao.updateProblemDescription(problemDescriptionDto)<=0)
            throw new WLHSException("更新失败");
        return R.ok();
    }

    @Override
    public String queryProblemDescription(ProblemDescriptionDto problemDescriptionDto) {

        List<ProblemDescriptionDto> list = problemDescriptionDao.queryProblemDescription(problemDescriptionDto);
        return NR.r(list);
    }
}
