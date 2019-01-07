package com.newx.blog.util;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = "/*", filterName = "indexFilter2")
public class IndexFilter implements Filter{
    @Override
    public void destroy() {
        System.out.println("filter2 destroy method");
    }
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp,FilterChain filterChain)
            throws IOException, ServletException {
        String hostName = "http://" + req.getServerName();
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpServletRequest httpRequest = (HttpServletRequest) req;
        if (hostName.startsWith("http://a.com")) {//如果请求旧域名
//String queryString = (httpRequest.getQueryString() == null ? "": "?" + httpRequest.getQueryString());
            response.setStatus(301);
            String requestUrl = httpRequest.getRequestURL().toString();
            requestUrl = requestUrl.replace("http://a","http://b.com");//把旧域名替换为新的域名
            response.setHeader("Location", requestUrl);
            response.setHeader("Connection", "close");
        }if(hostName.startsWith("http://www.a.com")){//处理带www的域名
            response.setStatus(301);
            response.setHeader("Location", "http://b.com");//替换成没有www的域名
            response.setHeader("Connection", "close");
        }
        else {
            filterChain.doFilter(req, resp);
        }

    }
    @Override
    public void init(FilterConfig arg0) throws ServletException {
        System.out.println("filter2 init method");
    }
}