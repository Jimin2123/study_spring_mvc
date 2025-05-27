package com.springmvc.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MonitoringInterceptor implements HandlerInterceptor {
  private final Logger logger = LoggerFactory.getLogger(MonitoringInterceptor.class);
  ThreadLocal<StopWatch> stopWatchLocal = new ThreadLocal<StopWatch>();

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response
          , Object handler) throws Exception {

    StopWatch stopWatch = new StopWatch(handler.toString());
    stopWatch.start(handler.toString());

    this.stopWatchLocal.set(stopWatch);
    this.logger.info("접근한 URL 경로: {}", getURLPath(request));
    this.logger.info("요청 처리 시작 시간: {}", getCurrentTime());

    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler
          , ModelAndView modelAndView) throws Exception {
    this.logger.info("요청 처리 종료 시간: {}", getCurrentTime());
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response
          , Object handler, Exception ex) throws Exception {
    StopWatch stopWatch = this.stopWatchLocal.get();
    this.logger.info("요청 처리 소요시간: {} ms", stopWatch.getTotalTimeMillis());
    this.stopWatchLocal.set(null);
  }


  private String getURLPath(HttpServletRequest request) {
    String currentURL = request.getRequestURI();
    String queryString = request.getQueryString();
    queryString = queryString == null ? "" : "?" + queryString;

    return currentURL + queryString;
  }

  private String getCurrentTime() {
    DateFormat formatter = new SimpleDateFormat("yyy/MM/dd HH:mm:ss");
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(System.currentTimeMillis());

    return formatter.format(calendar.getTime());
  }
}
