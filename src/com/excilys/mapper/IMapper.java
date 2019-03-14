package com.excilys.mapper;

import java.sql.ResultSet;
import java.util.ArrayList;

public interface IMapper<T> {
	public abstract T map(ResultSet res);
	public abstract ArrayList<T> mapList(ResultSet res);
}
