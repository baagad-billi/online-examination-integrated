package com.viva.project.service;

import com.viva.project.entity.UserAuthEntity;
import com.viva.project.repository.UserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAuthServiceImpl implements UserAuthService {

	@Autowired
	private UserAuthRepository userAuthRepository;

	@Override
	public Optional<UserAuthEntity> getUser(String username, String password) {
		return userAuthRepository.findByUsername(username);
	}

	@Override
	public UserAuthEntity doUserRegistration(String username, String password, String role) {
		UserAuthEntity userAuth = new UserAuthEntity();
		userAuth.setUsername(username);
		userAuth.setPassword(password);
		userAuth.setIsActive("Y");
		userAuth.setRetryCount(0L);
		userAuth.setIsLoggedIn("N");
		userAuth.setRole(role);
		return userAuthRepository.save(userAuth);
	}

//	@Autowired
//	private EmployeeRepository employeeRepository;

//	@Override
//	public List<Employee> getAllEmployees() {
//		return employeeRepository.findAll();
//	}
//
//	@Override
//	public void saveEmployee(Employee employee) {
//		this.employeeRepository.save(employee);
//	}
//
//	@Override
//	public Employee getEmployeeById(long id) {
//		Optional<Employee> optional = employeeRepository.findById(id);
//		Employee employee = null;
//		if (optional.isPresent()) {
//			employee = optional.get();
//		} else {
//			throw new RuntimeException(" Employee not found for id :: " + id);
//		}
//		return employee;
//	}
//
//	@Override
//	public void deleteEmployeeById(long id) {
//		this.employeeRepository.deleteById(id);
//	}
//
//	@Override
//	public Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
//		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
//			Sort.by(sortField).descending();
//
//		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
//		return this.employeeRepository.findAll(pageable);
//	}
}
