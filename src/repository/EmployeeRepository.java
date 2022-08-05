package repository;

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
import domain.Category;
import domain.Employee;

public class EmployeeRepository {

	public Employee save(Employee employee) {
		Session session = HibernateConnect.getFactory().openSession();
		session.getTransaction().begin();
		session.save(employee);
		session.getTransaction().commit();
		return employee;
	}
	
	@SuppressWarnings("unchecked")
	public List<Employee> findAll(){
		List<Employee> list = new ArrayList<Employee>();
		Session session = HibernateConnect.getFactory().openSession();
		Query query = session.createQuery("From Employee");
		list = query.getResultList();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Employee> findByName(String param){
		List<Employee> list = new ArrayList<Employee>();
		Session session = HibernateConnect.getFactory().openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Employee> query = builder.createQuery(Employee.class);
		Root<Employee> root = query.from(Employee.class);
		Predicate p1 = builder.like(root.get("name").as(String.class), "%"+param+"%");
		Predicate p2 = builder.like(root.get("address").as(String.class), "%"+param+"%");
		Predicate p3 = builder.like(root.get("phone").as(String.class), "%"+param+"%");
		query.where(builder.or(p1,p2,p3));
		Query q = session.createQuery(query);
		list = q.getResultList();
		return list;
	}
}
