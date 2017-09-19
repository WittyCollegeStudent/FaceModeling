package com.qianbo.service;

import com.qianbo.model.Demo1;
import org.beangle.commons.dao.Dao;
import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class Demo1Service {
    @Resource(name = "Demo1")
}
