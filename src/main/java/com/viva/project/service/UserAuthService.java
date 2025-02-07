package com.viva.project.service;

import com.viva.project.entity.UserAuthEntity;

public interface UserAuthService {
//	List<Employee> getAllEmployees();
//	void saveEmployee(Employee employee);
//	Employee getEmployeeById(long id);
//	void deleteEmployeeById(long id);
//	Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
	boolean doUserLogin(String username, String password);

	UserAuthEntity doUserRegistration(String username, String password);
}
