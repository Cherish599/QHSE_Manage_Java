package com.wlhse.service.impl;

import com.wlhse.dao.CheckRecordDao;
import com.wlhse.dto.CheckRecordDto;
import com.wlhse.dto.CheckRecordTreeDto;
import com.wlhse.exception.WLHSException;
import com.wlhse.service.CheckRecordService;
import com.wlhse.util.R;
import com.wlhse.util.TreeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CheckRecordServiceImpl implements CheckRecordService {

    @Resource
    private CheckRecordDao checkRecordDao;

    @Resource
    private TreeUtil treeUtil;

    @Override
    public R addCheckRecord(CheckRecordDto checkRecordDto) {
        if ( checkRecordDao.addCheckRecord(checkRecordDto)<= 0)
            throw new WLHSException("新增失败");
        return R.ok();
    }

    @Override
    public R queryAll() {
        R ok = R.ok();
        ok.put("data",checkRecordDao.queryAll());
        return ok;
    }

    @Override
    public R queryAllTree() {
        R ok = R.ok();
        //先查询checkrecord中前4位不同的code
        List<CheckRecordDto> checkRecordDtos = checkRecordDao.queryAll();
        System.out.println(checkRecordDtos);
        List<String> codes = new ArrayList<>();
        for (CheckRecordDto dto:checkRecordDtos) {
            if (codes.indexOf(dto.getCode().substring(0,4)) == -1) {
                codes.add(dto.getCode().substring(0,4));
            }
        }
        System.out.println(codes.toString());
        List<CheckRecordTreeDto> resultDtos = new ArrayList<>();
        for (String code:codes) {
            resultDtos.addAll(checkRecordDao.queryAllByParentCode(code));
        }
        System.out.println(resultDtos);
        ok.put("data", treeUtil.getCheckRecordTree(resultDtos));
        return ok;
    }

    @Override
    public R updateCheckrecord(int id, CheckRecordDto checkRecordDto) {
        checkRecordDto.setCheckRecordID(id);
        if (checkRecordDao.updateCheckRecord(checkRecordDto)<=0)
            throw new WLHSException("更新失败");
        return R.ok();
    }

    @Override
    public R deleteCheckrecord(int id) {
        if (checkRecordDao.deleteCheckRecord(id)<=0)
            throw new WLHSException("删除失败");
        return R.ok();
    }

}
