package com.bj.xnbb.service.serviceImpl;

import com.bj.xnbb.dao.UserDao;
import com.bj.xnbb.domain.User;
import com.bj.xnbb.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/7/22.
 */
@Service
public class UserServiceImpl implements UserService{

    @Resource
    private UserDao userDao;

    @Override
    public User getUserById(Integer id) {
        return userDao.getUserById(id);  //返回用户
    }

	@Override
	public void save(User user) {
        userDao.save(user);		
	}

	@Override
	public List<User> getAll() {
		return userDao.getAll();
	}
    
    
    
}
