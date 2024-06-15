package com.hr.manage.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.hr.manage.entity.Admin;
import com.hr.manage.payload.LoginPayload;

@Repository
public class AdminRepository {

	@PersistenceContext
	private EntityManager entityManager;

	public Admin save(Admin admin) {
		return entityManager.merge(admin);
	}

	public Admin getByEmail(String email) {
		try {
			Query query = entityManager
					.createQuery("SELECT a FROM Admin a WHERE a.email = :email AND a.isDeleted = false");
			query.setParameter("email", email);
			return (Admin) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

//	

	public Admin findById(String id) {
		Query query = entityManager.createQuery("from Admin a WHERE a.id = :id AND a.isDeleted = 0");
		query.setParameter("id", id);
		return (Admin) query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<Admin> getAdminByIds(List<String> ids) {
		Query query = entityManager.createQuery("SELECT a from Admin a where a.id in (:ids) AND a.isDeleted = 0");
		query.setParameter("ids", ids);
		return query.getResultList();
	}

	public Admin getAdminByEmail(LoginPayload loginpayload) {
		TypedQuery<Admin> query = entityManager
				.createQuery("SELECT a FROM Admin a WHERE a.email = :email AND a.isDeleted = false", Admin.class);
		query.setParameter("email", loginpayload.getEmail());
		return query.getSingleResult();
	}

	public String findEncryptedPasswordByEmail(String email) {
		Query query = entityManager
				.createQuery("SELECT a.password FROM Admin a WHERE a.email = :email AND a.isDeleted = 0");
		query.setParameter("email", email);
		return (String) query.getSingleResult();
	}
}