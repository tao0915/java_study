package com.admin.study.login.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admin.study.login.dao.LoginDao;

@Service
public class LoginService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private LoginDao loginDao;
	
	//TODO
}
