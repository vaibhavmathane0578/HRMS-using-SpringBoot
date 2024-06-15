package com.hr.manage.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.hr.manage.entity.Employee;
import com.hr.manage.payload.LoginPayload;

@Repository
public class EmployeeRepository {
	
	@PersistenceContext
	private EntityManager entityManager;

	public Employee save(Employee employee) {
		return entityManager.merge(employee);
	}

	public Employee getByEmail(String email) {
	    try {
	        Query query = entityManager.createQuery("SELECT e FROM Employee e WHERE e.officialEmail = :email AND e.isDeleted = false");
	        query.setParameter("email", email);
	        return (Employee) query.getSingleResult();
	    } catch (NoResultException ex) {
	        
	        return null; 
	        }
	}


	public Employee findById(String id) {try {
	    Query query = entityManager.createQuery("SELECT e FROM Employee e WHERE e.id = :id AND e.isDeleted = false");
	    query.setParameter("id", id);
	    return (Employee) query.getSingleResult();
	} catch (NoResultException e) {
	    return null; // or throw a custom exception
	}
	}
	@SuppressWarnings("unchecked")
	public List<Employee> getEmployeeByIds(List<String> ids) {
		Query query = entityManager.createQuery("SELECT e from Employee e WHERE e.id in (:ids) AND e.isDeleted = 0");
		query.setParameter("ids", ids);
		return query.getResultList();
	}
	public Employee getEmployeeByOfficialEmail(LoginPayload loginPayload) {
	    TypedQuery<Employee> query = entityManager.createQuery(
	        "SELECT e FROM Employee e WHERE e.officialEmail = :email AND e.isDeleted = false",Employee.class);
	    query.setParameter("email", loginPayload.getEmail());
	    return query.getSingleResult();
	}
	public String findEncryptedPasswordByEmail(String email) {
		Query query = entityManager
				.createQuery("SELECT e.password FROM Employee e WHERE e.officialEmail = :email AND e.isDeleted = 0");
		query.setParameter("email", email);
		return (String) query.getSingleResult();
	}
}
