package com.hr.manage.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.hr.manage.entity.Department;

@Repository
public class DepartmentRepository {
	@PersistenceContext
	private EntityManager entityManager;

	public Department save(Department department) {
		return entityManager.merge(department);
	}

	public Department getByDepartment(String deptName) {
		try {
			Query query = entityManager
					.createQuery("SELECT d FROM Department d WHERE d.deptName = :deptName AND a.isDeleted = false");
			query.setParameter("deptName", deptName);
			return (Department) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	public Department findById(String id) {
		Query query = entityManager.createQuery("from Department d WHERE d.id = :id AND d.isDeleted = 0");
		query.setParameter("id", id);
		return (Department) query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<Department> getDepartmentsByIds(List<String> ids) {
		Query query = entityManager.createQuery("SELECT d from Department d where d.id in (:ids) AND d.isDeleted = 0");
		query.setParameter("ids", ids);
		return query.getResultList();
	}
}
