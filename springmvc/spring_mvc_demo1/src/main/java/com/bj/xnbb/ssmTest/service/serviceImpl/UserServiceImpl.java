package com.bj.xnbb.ssmTest.service.serviceImpl;

import com.bj.xnbb.ssmTest.dao.UserDao;
import com.bj.xnbb.ssmTest.domain.User;
import com.bj.xnbb.ssmTest.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/7/22.
 */
@Service
public class UserServiceImpl implements UserService {

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
