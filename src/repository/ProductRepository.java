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
import domain.Category;
import domain.Product;

public class ProductRepository {

	public Product save(Product product) {
		Session session = HibernateConnect.getFactory().openSession();
		session.getTransaction().begin();
		session.save(product);
		session.getTransaction().commit();
		return product;
	}
	
	public Product update(Product product) {
		Session session = HibernateConnect.getFactory().openSession();
		session.getTransaction().begin();
		session.update(product);
		session.getTransaction().commit();
		session.close();
		return product;
	}
	
	public void delete(Product product) {
		Session session = HibernateConnect.getFactory().openSession();
		session.getTransaction().begin();
		session.delete(product);
		session.getTransaction().commit();
	}
	
	@SuppressWarnings("unchecked")
	public List<Product> findAll(){
		List<Product> list = new ArrayList<Product>();
		Session session = HibernateConnect.getFactory().openSession();
		Query query = session.createQuery("From Product");
		list = query.getResultList();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Product> findByName(String name){
		List<Product> list = new ArrayList<Product>();
		Session session = HibernateConnect.getFactory().openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Product> query = builder.createQuery(Product.class);
		Root<Product> root = query.from(Product.class);
		Predicate p1 = builder.like(root.get("name").as(String.class), "%"+name+"%");
		query.where(builder.or(p1));
		Query q = session.createQuery(query);
		list = q.getResultList();
		return list;
	}
}
