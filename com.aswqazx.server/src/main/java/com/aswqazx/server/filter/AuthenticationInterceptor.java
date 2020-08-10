package com.aswqazx.server.filter;

import com.aswqazx.server.annotation.UserAuthenticate;
import com.aswqazx.server.entity.ResultInfo;
import com.aswqazx.server.entity.table.SysUser;
import com.aswqazx.server.repository.SysUserRepository;
import com.aswqazx.server.util.JWTUtil;
import com.aswqazx.server.util.SendMsgUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

/**
 * @author OMNIS
 */
@Component
@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final SysUserRepository sysUserRepository;

    public static final String OPTIONS = "OPTIONS";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        if (request.getHeader(HttpHeaders.ORIGIN) != null) {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Credentials", "true");
            response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT, HEAD");
            response.addHeader("Access-Control-Allow-Headers", "Content-Type, X-Token");
            response.addHeader("Access-Control-Max-Age", "3600");
        }

        if (OPTIONS.equalsIgnoreCase(request.getMethod())) {
            log.info("OPTIONS");
            return false;
        }

        if(!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        UserAuthenticate userAuthenticate = method.getAnnotation(UserAuthenticate.class);

        if (Objects.nonNull(userAuthenticate)) {

            String token = request.getHeader("X-Token");
            log.info("token {}", token);
            if (StringUtils.isEmpty(token)) {
                log.info("没有token，请重新登录");
                response.setStatus(HttpServletResponse.SC_OK);
                SendMsgUtil.sendJsonMessage(response, ResultInfo.failure2("没有token，请重新登录"));
                return false;
            }
            String username = JWTUtil.getUsername(token);
            if (StringUtils.isEmpty(username)) {
                log.info("无效token，请重新登录 username is null");
                response.setStatus(HttpServletResponse.SC_OK);
                SendMsgUtil.sendJsonMessage(response, ResultInfo.failure2("无效token，请重新登录"));
                return false;
            }
            List<SysUser> sysUserList = sysUserRepository.findAllByName(username);
            if (sysUserList.size() == 0) {
                log.info("用户名不存在，请重新登录：{}", username);
                response.setStatus(HttpServletResponse.SC_OK);
                SendMsgUtil.sendJsonMessage(response, ResultInfo.failure2("用户名不存在，请重新登录"));
                return false;
            }
            SysUser sysUser = sysUserList.get(0);
            boolean verify = JWTUtil.verifyToken(token, sysUser.getUsername());
            if (!verify) {
                log.info("token失效，请重新登录");
                response.setStatus(HttpServletResponse.SC_OK);
                SendMsgUtil.sendJsonMessage(response, ResultInfo.failure2("token失效，请重新登录"));
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
