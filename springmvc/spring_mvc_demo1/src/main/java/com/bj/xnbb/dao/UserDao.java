package com.bj.xnbb.dao;

import java.util.List;

import com.bj.xnbb.domain.User;

/**
 * Created by Administrator on 2017/7/22.
 */
public interface UserDao {

	/**
	 * 通过id获取用户
	 * @param id
	 * @return
	 */
    public User getUserById(Integer id);

    /**
     * 保存用户
     * @param user
     */
    public void save(User user);
    
    
    /**
     * 获取所有元素
     * @return
     */
    public List<User> getAll();
    
}
