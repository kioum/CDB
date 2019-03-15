package com.excilys.persistence;

import java.util.ArrayList;

public interface IDAO<T> {
	/**
	 * Get all the elements in the database
	 * @return ArrayList<T> the list of all elements
	 */
	abstract ArrayList<T> getList();
	
	/**
	 * find a element by this id
	 * @param id
	 * @return T found element. If element not found return null
	 */
	abstract T findById(Long id);
	
	/**
	 * 
	 * @param comp
	 * @return 1 element created. 0 if a any problems.
	 */
	abstract int create(T comp);
	
	/**
	 * update a element in the database
	 * @param comp
	 * @return 1 element updated. 0 if a any problems.
	 */
	abstract int update(T comp);
	
	/**
	 * delete a element by this id
	 * @param id
	 * @return 1 element deleted. 0 if a any problems. 
	 */
	abstract int deleteById(Long id);
}
