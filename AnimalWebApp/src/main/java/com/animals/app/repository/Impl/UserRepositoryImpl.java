package com.animals.app.repository.Impl;

import com.animals.app.domain.User;
import com.animals.app.repository.MyBatisConnectionFactory;
import com.animals.app.repository.UserRepository;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * Created by oleg on 24.07.2015.
 */
public class UserRepositoryImpl {

    private SqlSessionFactory sqlSessionFactory;

    public UserRepositoryImpl() {
        sqlSessionFactory = new MyBatisConnectionFactory().getSqlSessionFactory();
    }

    /**
     * Insert an instance of User into the database.
     * @param user the instance to be persisted.
     */
    public void insert(User user) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            UserRepository mapper = session.getMapper(UserRepository.class);
            mapper.insert(user);

            session.commit();
        } finally {
            session.close();
        }
    }

    /**
     * Update an instance of User in the database.
     * @param user the instance to be updated.
     */
    public void update(User user) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            UserRepository mapper = session.getMapper(UserRepository.class);
            mapper.update(user);

            session.commit();
        } finally {
            session.close();
        }
    }

    /**
     * Delete an instance of User from the database.
     * @param id primary key value of the instance to be deleted.
     */
    public void delete(Integer id) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            UserRepository mapper = session.getMapper(UserRepository.class);
            mapper.delete(id);

            session.commit();
        } finally {
            session.close();
        }
    }

    /**
     * Returns a User instance from the database.
     * @param id primary key value used for lookup.
     * @return A User instance with a primary key value equals to pk. null if there is no matching row.
     */
    public User getById(int id){
        SqlSession sqlSession =  sqlSessionFactory.openSession();

        try{
            UserRepository mapper = sqlSession.getMapper(UserRepository.class);
            return mapper.getById(id);
        } finally {
            sqlSession.close();
        }
    }

    /**
     * Returns a User instance from the database for admin animals list.
     * @param id primary key value used for lookup.
     * @return A User instance with a primary key value equals to pk. null if there is no matching row.
     */
    public User getByIdForAdminAnimalList(int id){
        SqlSession sqlSession =  sqlSessionFactory.openSession();

        try{
            UserRepository mapper = sqlSession.getMapper(UserRepository.class);
            return mapper.getByIdForAdminAnimalList(id);
        } finally {
            sqlSession.close();
        }
    }

    /**
     * Returns the list of all Users instances from the database.
     * @return the list of all Users instances from the database.
     */
    public List<User> getAll(){
    	SqlSession sqlSession =  sqlSessionFactory.openSession();

        try{
            UserRepository mapper = sqlSession.getMapper(UserRepository.class);
            return mapper.getAll();
        } finally {
            sqlSession.close();
        }    	 	
    }

}
