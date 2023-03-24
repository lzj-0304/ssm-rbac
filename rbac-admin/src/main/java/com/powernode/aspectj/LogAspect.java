package com.powernode.aspectj;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.powernode.annotation.Log;
import com.powernode.async.AsyncSysOperLogService;
import com.powernode.constants.CoreConstant;
import com.powernode.domain.SysOperLog;
import com.powernode.domain.SysUser;
import com.powernode.enums.StateEnums;
import com.powernode.utils.HttpUtils;
import com.powernode.utils.ServletUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

/**
 * 操作日志记录处
 */
@Aspect
@Component
public class LogAspect
{

    @Resource
    private AsyncSysOperLogService operLogService;

    private ObjectMapper objectMapper=new ObjectMapper();

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "@annotation(controllerLog)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Log controllerLog, Object jsonResult)
    {
        handleLog(joinPoint, controllerLog, null, jsonResult);
    }

    /**
     * 拦截异常操作
     * @param joinPoint 切点
     * @param e 异常
     */
    @AfterThrowing(value = "@annotation(controllerLog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Log controllerLog, Exception e)
    {
        handleLog(joinPoint, controllerLog, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, Log controllerLog, final Exception e, Object jsonResult)
    {
        try
        {
            // 获取当前的用户
            SysUser loginUser = (SysUser) ServletUtils.getSession().getAttribute(CoreConstant.SESSION_KEY);

            // 构造日志对象
            SysOperLog operLog = new SysOperLog();
            operLog.setOperTime(new Date());
            operLog.setStatus(StateEnums.REQUEST_SUCCESS.getCode());
            // 请求的地址
            String ip = HttpUtils.getIpAddr(ServletUtils.getRequest());
            operLog.setOperIp(ip);
            operLog.setOperUrl(ServletUtils.getRequest().getRequestURI());
            if (loginUser != null)
            {
                operLog.setUserId(loginUser.getUserId());
                operLog.setOperName(loginUser.getUserName());
            }

            if (e != null)
            {
                operLog.setStatus(StateEnums.REQUEST_ERROR.getCode());
                // 取出错误前2000个字符
                operLog.setErrorMsg(e.getMessage().substring(0,2000));
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setMethod(className + "." + methodName + "()");
            // 设置请求方式
            operLog.setRequestMethod(ServletUtils.getRequest().getMethod());
            // 处理设置注解上的参数
            getControllerMethodDescription(joinPoint, controllerLog, operLog, jsonResult);
            // 保存数据库
            operLogService.saveOperLogService(operLog);
        }
        catch (Exception exp)
        {
            // 记录本地异常日志
            exp.printStackTrace();
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param log 日志
     * @param operLog 操作日志
     * @throws Exception
     */
    public void getControllerMethodDescription(JoinPoint joinPoint, Log log, SysOperLog operLog, Object jsonResult) throws Exception
    {
        // 设置action动作
        operLog.setBusinessType(log.businessType().getCode());
        // 设置标题
        operLog.setTitle(log.title());
        // 是否需要保存request，参数和值
        if (log.isSaveRequestData())
        {
            // 获取参数的信息，传入到数据库中。
            setRequestValue(joinPoint, operLog);
        }
        // 是否需要保存response，参数和值
        if (log.isSaveResponseData() && jsonResult!=null)
        {
            String result = objectMapper.writeValueAsString(jsonResult);
            if(result!=null&&result.length()>2000){
                result=result.substring(0,2000);
            }
            operLog.setJsonResult(result);
        }
    }

    /**
     * 获取请求的参数，放到log中
     *
     * @param operLog 操作日志
     * @throws Exception 异常
     */
    private void setRequestValue(JoinPoint joinPoint, SysOperLog operLog) throws Exception
    {
        String requestMethod = operLog.getRequestMethod();
        if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod))
        {
            String params = argsArrayToString(joinPoint.getArgs());
            if(params!=null&&params.length()>2000){
                params=params.substring(0,2000);
            }
            operLog.setOperParam(params);
        }
        else
        {
            Map<?, ?> paramsMap = (Map<?, ?>) ServletUtils.getRequest().getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            String params=null;
            if(paramsMap!=null&&paramsMap.toString().length()>2000){
                params=paramsMap.toString().substring(0,2000);
            }
            operLog.setOperParam(params);
        }
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray)
    {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0)
        {
            for (Object o : paramsArray)
            {
                if (o!=null && !isFilterObject(o))
                {
                    try
                    {
                        String jsonObj = objectMapper.writeValueAsString(o);
                        params += jsonObj + " ";
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
        return params.trim();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    @SuppressWarnings("rawtypes")
    public boolean isFilterObject(final Object o)
    {
        Class<?> clazz = o.getClass();
        if (clazz.isArray())
        {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        }
        else if (Collection.class.isAssignableFrom(clazz))
        {
            Collection collection = (Collection) o;
            for (Object value : collection)
            {
                return value instanceof MultipartFile;
            }
        }
        else if (Map.class.isAssignableFrom(clazz))
        {
            Map map = (Map) o;
            for (Object value : map.entrySet())
            {
                Map.Entry entry = (Map.Entry) value;
                return entry.getValue() instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse
                || o instanceof BindingResult;
    }
}