package com.excilys.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IDAO<T> {
	Connection conn = DAOFactory.getConnection();
	
	abstract ArrayList<T> getList() throws SQLException;
	abstract T findById(Long id) throws SQLException;
	abstract int create(T comp) throws SQLException;
	abstract int update(T comp) throws SQLException;
	abstract int deleteById(Long id) throws SQLException;
}
