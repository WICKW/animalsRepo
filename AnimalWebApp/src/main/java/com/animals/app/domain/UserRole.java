package com.animals.app.domain;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by oleg on 22.07.2015.
 */
public class UserRole/* extends BaseTypeHandler<List<? extends UserRole>>*/ implements Serializable, TypeHandler<List<? extends UserRole>> {

    private Integer id;
    private String role;

    public UserRole() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRole userRole = (UserRole) o;

        if (!id.equals(userRole.id)) return false;
        if (!role.equals(userRole.role)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + role.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "role='" + role + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, List<? extends UserRole> userRoles, JdbcType jdbcType) throws SQLException {
        preparedStatement.setObject(i, userRoles.get(0).getId());
    }

    @Override
    public List<? extends UserRole> getResult(ResultSet resultSet, String s) throws SQLException {
        return (List<? extends UserRole>) resultSet.getObject(s);
    }

    @Override
    public List<? extends UserRole> getResult(ResultSet resultSet, int i) throws SQLException {
        return (List<? extends UserRole>) resultSet.getObject(i);
    }

    @Override
    public List<? extends UserRole> getResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }

/*
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, List<? extends UserRole> list, JdbcType jdbcType) throws SQLException {
        preparedStatement.setObject(i, list.get(0).getId());
    }

    @Override
    public List getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return (List) resultSet.getObject(s);
    }

    @Override
    public List getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return null;
    }

    @Override
    public List getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }
*/
}
