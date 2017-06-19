package com.admin.common.comm.util;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.jasper.tagplugins.jstl.Util.ImportResponseWrapper;

/**
 * Htpp ���� ��ƿ
 *
 */
public class HttpUtil {

	
	/**
	 * request�� �޾Ƽ� �Ķ���� ���ڿ��� �����Ѵ�. - �Ķ���͸� �״�� �����ϴ� ��쿡 ����� ������.
	 * @param request
	 * @param skip
	 * @return
	 * @throws Exception
	 */
	public static String getParamStr(ServletRequest request, String skip){
		try{
	    	StringBuffer sb = new StringBuffer();

			Enumeration<?> paramNames = request.getParameterNames();
			String[] skips = StringUtils.isNotEmpty(skip) ? skip.split(",") : null;
	
			while(paramNames.hasMoreElements()){
				String name = (String)paramNames.nextElement();
				if(ArrayUtils.contains(skips, name)){ continue; }

				String value = request.getParameter(name);
				sb.append("&" + name + "=" + URLEncoder.encode(value, "UTF-8"));
			}
			
			return sb.length() > 0 ? sb.toString().substring(1) : "";
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	
	/**
	 * �ش� ����� JSP�� dispatch ��Ų ����� ��ȯ��.
	 * @param request
	 * @param response
	 * @param path
	 * @return
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws Exception
	 */
	public static String dispatch(HttpServletRequest request, HttpServletResponse response, Map<String, Object> dataMap, String path) throws ServletException, IOException{
		// request�� �ش� ������ �Ҵ���.
		for(Map.Entry<String, Object> e : dataMap.entrySet()){
			request.setAttribute(e.getKey(), e.getValue());
		}
		
		// dispatch ��Ű�� ����� ��ȯ��.
		ImportResponseWrapper wrapper = new ImportResponseWrapper(response);
		request.getRequestDispatcher(path).include(request, wrapper);
		return wrapper.getString();
	}
	
	/**
	 * request���� �ش� ������Ʈ�� ��ȯ��.
	 * 	- �±׶��̺귯�� ���ۿ��� Ȱ��
	 * @param pageContext
	 * @param name
	 * @param scope
	 * @return
	 * @throws JspTagException
	 */
    public static Object lookup(PageContext pageContext, String name, String scope) throws JspTagException{
        Object bean = null;
        if(scope == null)
        {
            bean = pageContext.findAttribute(name);
        }
        else if(scope.equalsIgnoreCase("page"))
        {
            bean = pageContext.getAttribute(name, PageContext.PAGE_SCOPE);
        }
        else if(scope.equalsIgnoreCase("request"))
        {
            bean = pageContext.getAttribute(name, PageContext.REQUEST_SCOPE);
        }
        else if(scope.equalsIgnoreCase("session"))
        {
            bean = pageContext.getAttribute(name, PageContext.SESSION_SCOPE);
        }
        else if(scope.equalsIgnoreCase("application"))
        {
            bean = pageContext.getAttribute(name, PageContext.APPLICATION_SCOPE);
        }
        else
        {
            JspTagException e = new JspTagException("Invalid scope " + scope);
            throw e;
        }
        return (bean);
    }	
}
