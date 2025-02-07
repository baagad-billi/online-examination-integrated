package com.viva.project.service;

import com.viva.project.entity.UserAuthEntity;

import java.util.Optional;

public interface UserAuthService {
//	List<Employee> getAllEmployees();
//	void saveEmployee(Employee employee);
//	Employee getEmployeeById(long id);
//	void deleteEmployeeById(long id);
//	Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
	Optional<UserAuthEntity> getUser(String username, String password);

	UserAuthEntity doUserRegistration(String username, String password, String role);
}
