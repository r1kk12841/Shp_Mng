package repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import connect.HibernateConnect;
import domain.Account;
import domain.Employee;

public class AccountRepository {

	public List<Account> findAll() {
		List<Account> list = new ArrayList<Account>();
		Session session = HibernateConnect.getFactory().openSession();
		Query query = session.createQuery("From Account");
		list = query.getResultList();
		return list;
	}
	
	public List<Account> search(String param) {
		List<Account> list = new ArrayList<Account>();
		Session session = HibernateConnect.getFactory().openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Account> query = builder.createQuery(Account.class);
		Root<Account> root = query.from(Account.class);
		Predicate p1 = builder.like(root.get("username").as(String.class), "%"+param+"%");
		Predicate p2 = builder.like(root.get("role").as(String.class), "%"+param+"%");
		query.where(builder.or(p1,p2));
		Query q = session.createQuery(query);
		list = q.getResultList();
		return list;
	}
	
	/*luu tai khoan dong thoi luu nhan vien*/
	public Account save(Account account) {
		Session session = HibernateConnect.getFactory().openSession();
		session.getTransaction().begin();
		session.save(account);
		Employee emp = account.getEmployee();
		emp.setAccount(account);
		session.save(emp);
		session.getTransaction().commit();
		return account;
	}
	
	public Account update(Account account) {
		Session session = HibernateConnect.getFactory().openSession();
		session.getTransaction().begin();
		session.update(account);
		session.update(account.getEmployee());
		session.getTransaction().commit();
		return account;
	}

	public void delete (Account account) {
		Session session = HibernateConnect.getFactory().openSession();
		session.getTransaction().begin();
		session.delete(account);
		session.getTransaction().commit();
	}
	
	@SuppressWarnings("unchecked")
	public Account checkLogin(String username, String password) {
		List<Account> list = new ArrayList<Account>();
		Session session = HibernateConnect.getFactory().openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Account> query = builder.createQuery(Account.class);
		Root<Account> root = query.from(Account.class);
		Predicate p1 = builder.equal(root.get("username").as(String.class), username);
		Predicate p2 = builder.equal(root.get("password").as(String.class), password);
		query.where(builder.and(p1,p2));
		Query q = session.createQuery(query);
		list = q.getResultList();
		if(list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Account> findByName(String param) {
		List<Account> list = new ArrayList<Account>();
		Session session = HibernateConnect.getFactory().openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Account> query = builder.createQuery(Account.class);
		Root<Account> root = query.from(Account.class);
		Predicate p1 = builder.like(root.get("username").as(String.class), "%"+param+"%");
		query.where(builder.or(p1));
		Query q = session.createQuery(query);
		list = q.getResultList();
		return list;
	}

}
