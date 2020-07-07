package com.wlhse.controller;

import com.wlhse.cache.JedisClient;
import com.wlhse.dao.ModuleDao;
import com.wlhse.dto.inDto.InterfaceModuleInDto;
import com.wlhse.util.SortCodeUtil;
import com.wlhse.util.state_code.CodeDict;
import com.wlhse.util.state_code.NR;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

//自定义拦截器 AOP
public class APIInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private JedisClient jedisClient;

    @Resource
    private SortCodeUtil sortCodeUtil;

    @Resource
    private ModuleDao moduleDao;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");
        try {
            String token = request.getHeader("Authorization");

            String userId = "";
            if (StringUtils.isNotBlank(token)) {
                userId = jedisClient.get(token);
            }
            String servletPath = request.getServletPath();
            String method = request.getMethod();
            InterfaceModuleInDto interfaceModuleInDto = new InterfaceModuleInDto(Integer.parseInt(userId), sortCodeUtil.getNoNumberString(servletPath), method);
            int count = moduleDao.getInterfaceCountByEmpId(interfaceModuleInDto);
            if (StringUtils.isNotBlank(userId) && count >= 1) {
                //spring请求的链式执行顺序为Filter-->拦截器-->controller
                if (!("GET".equals(method))) {
                    logger.info("userID:" + userId + " url:" + servletPath + " method:" + method);
                }
                return true;
            } else {
                PrintWriter pw = response.getWriter();
                pw.write(NR.r(CodeDict.ILLEGAL_FAIL, 0, 0, null, null, 0, 0));
                return false;
            }
        } catch (Exception e) {
            PrintWriter pw = response.getWriter();
            pw.write(NR.r(CodeDict.ILLEGAL_FAIL, 0, 0, null, null, 0, 0));
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }
}
