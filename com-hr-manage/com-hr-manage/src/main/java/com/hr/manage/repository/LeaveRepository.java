package com.hr.manage.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.hr.manage.entity.Leave;

@Repository
public class LeaveRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Leave save(Leave leave) {
        return entityManager.merge(leave);
    }
    
    

    public Leave getByLeaveId(String leaveId) {
        try {
            Query query = entityManager.createQuery("SELECT l FROM Leave l WHERE l.leaveId = :leaveId AND l.isDeleted = false");
            query.setParameter("leaveId", leaveId);
            return (Leave) query.getSingleResult();
        } catch (NoResultException e) {
            return null; // Handle case when no result is found
        }
    }

    public Leave findByEmployeeId(String employeeId) {
        try {
            Query query = entityManager.createQuery("SELECT l FROM Leave l WHERE l.employeeId = :employeeId AND l.isDeleted = false");
            query.setParameter("employeeId", employeeId);
            return (Leave) query.getSingleResult();
        } catch (NoResultException e) {
            return null; // Handle case when no result is found
        }
    }

    @SuppressWarnings("unchecked")
    public List<Leave> getLeaveByIds(List<String> ids) {
        Query query = entityManager.createQuery("SELECT l FROM Leave l WHERE l.leaveId IN (:ids) AND l.isDeleted = false");
        query.setParameter("ids", ids);
        return query.getResultList();
    }
    
    @SuppressWarnings("unchecked")
    public List<Leave> getAllLeaves() {
        Query query = entityManager.createQuery("SELECT l FROM Leave l WHERE l.isDeleted = false");
        return query.getResultList();
    }
}