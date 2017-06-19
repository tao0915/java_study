package com.admin.common.listener;

import java.util.Date;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpSessionCheckingListener implements HttpSessionListener {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void sessionCreated(HttpSessionEvent event) {
        if (logger.isDebugEnabled()) {
            logger.debug("Session ID".concat(event.getSession().getId()).concat(" created at ").concat(new Date().toString()));
        }
    }

    public void sessionDestroyed(HttpSessionEvent event) {
        if (logger.isDebugEnabled()) {
            logger.debug("Session ID".concat(event.getSession().getId()).concat(" destroyed at ").concat(new Date().toString()));
        }
        
        //HttpSessionBindingListener -> valueUnbound �޼ҵ尡 ��ȥ ���۵� �Ǿ� �ش� �޼ҵ忡�� ID ���� ���� �߰�
        SessionDupListener.removeLoginId(event.getSession());
    }
}
