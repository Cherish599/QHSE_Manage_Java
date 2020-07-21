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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
                List <QHSECompanyYearManagerSysElementDto> parent=elementReviewDao.queryParent(code);
                if(parent!=null && !parent.isEmpty())
                lists.add(parent.get(0));
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
                List <QHSECompanyYearManagerSysElementDto>parent=new ArrayList<>();
                parent=elementReviewDao.queryParent(code);
                if(parent!=null && !parent.isEmpty())
                    lists.add(parent.get(0));
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
    public R queryAll(QhseEvidenceAttatchDto qhseEvidenceAttatchDto) throws ParseException {
        qhseEvidenceAttatchDto.setUrl(url);
        List<QhseEvidenceAttatchDto> qhseEvidenceAttatchDtos = elementReviewDao.queryAll(qhseEvidenceAttatchDto);
        QhseEvidenceAttatchDto returnPojo=new QhseEvidenceAttatchDto();
        if(qhseEvidenceAttatchDtos!=null && !qhseEvidenceAttatchDtos.isEmpty()){
            Long dates[] = new Long[qhseEvidenceAttatchDtos.size()];
            for (int i = 0; i <qhseEvidenceAttatchDtos.size(); i++) {
                DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
                String str=qhseEvidenceAttatchDtos.get(i).getUploadTime();
                Date date=df.parse(str);
                dates[i] = date.getTime();
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
            //拼接图片
            String[] urs = returnPojo.getAttach().split(";");
            String strs = "";
            for (String str:urs) {
                System.out.println(str);
                strs+=url+str+";";
            }
            returnPojo.setAttach(strs);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("data", returnPojo);
        return R.ok(map);
    }

    @Override
    public List<QHSECompanyYearManagerSysElementDto> queryParent(String code) {
        return elementReviewDao.queryParent(code);
    }


}
