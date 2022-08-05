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

public class CategoryRepository {

	public Category save(Category category) {
		Session session = HibernateConnect.getFactory().openSession();
		session.getTransaction().begin();
		session.save(category);
		session.getTransaction().commit();
		return category;
	}
	
	public Category update(Category category) {
		Session session = HibernateConnect.getFactory().openSession();
		session.getTransaction().begin();
		session.update(category);
		session.getTransaction().commit();
		return category;
	}
	
	public void delete (Category category) {
		Session session = HibernateConnect.getFactory().openSession();
		session.getTransaction().begin();
		session.delete(category);
		session.getTransaction().commit();
	}
	
	@SuppressWarnings("unchecked")
	public List<Category> findAll(){
		List<Category> list = new ArrayList<Category>();
		Session session = HibernateConnect.getFactory().openSession();
		Query query = session.createQuery("From Category");
		list = query.getResultList();
		return list;
	}
	
	public Category findById(Integer id) {
		Session session = HibernateConnect.getFactory().openSession();
		Category c = session.get(Category.class, id);
		return c;
	}
	
	@SuppressWarnings("unchecked")
	public List<Category> findByName(String name){
		List<Category> list = new ArrayList<Category>();
		Session session = HibernateConnect.getFactory().openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Category> query = builder.createQuery(Category.class);
		Root<Category> root = query.from(Category.class);
		Predicate p1 = builder.like(root.get("name").as(String.class), "%"+name+"%");
		query.where(builder.or(p1));
		Query q = session.createQuery(query);
		list = q.getResultList();
		return list;
	}
	
	
	public static void main(String[] args) {
		CategoryRepository categoryRepository = new CategoryRepository();
		Category c = categoryRepository.update(new Category(1,"áo nữ1"));
//		System.out.println(c);
		categoryRepository.findByName("nữ").forEach(p->{
			System.out.println(p);
		});
//		Category cate = categoryRepository.findById(1);
//		Category category = new Category("áo sơ mi nam");
//		category.setCategory(cate);
//		categoryRepository.save(category);
	}
}
