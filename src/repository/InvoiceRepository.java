package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import connect.Connect;
import connect.HibernateConnect;
import domain.Category;
import domain.Detail_Invoice;
import domain.Invoice;
import domain.Product;
import domain.Type_Invoice;

public class InvoiceRepository {

	public Invoice save(Invoice invoice) {
		Session session = HibernateConnect.getFactory().openSession();
		session.getTransaction().begin();
		Type_Invoice type_Invoice = session.get(Type_Invoice.class, 2);
		invoice.setType_Invoice(type_Invoice);
		session.save(invoice);
		for(Detail_Invoice p : invoice.getDetail_Invoices()){
			p.setInvoice(invoice);
			session.save(p);
			Product pro = p.getProduct();
			pro.setDetail_Invoices(null);
			pro.setQuantity(pro.getQuantity() - p.getQuantity());
			
			session.update(pro);
		
		}
		session.getTransaction().commit();
		return invoice;
	}
	
	public void saveInvoiceInput(Invoice invoice, List<Product> listProduct) {
		Session session = HibernateConnect.getFactory().openSession();
		session.getTransaction().begin();
		Type_Invoice type_Invoice = session.get(Type_Invoice.class, 1);
		invoice.setType_Invoice(type_Invoice);
		session.save(invoice);
		
		for(Product p : listProduct) {
			session.save(p);
		}
		
		for(Product p : listProduct) {
			Detail_Invoice detail_Invoice = new Detail_Invoice();
			detail_Invoice.setInvoice(invoice);
			detail_Invoice.setProduct(p);
			detail_Invoice.setQuantity(p.getQuantity());
			session.save(detail_Invoice);
		}
		
		session.getTransaction().commit();
	}

	public void delete(Integer id) {
		Session session = HibernateConnect.getFactory().openSession();
		session.getTransaction().begin();
		Invoice invoice = session.get(Invoice.class, id);
		for(Detail_Invoice d : invoice.getDetail_Invoices()) {
			d.getProduct().setQuantity(d.getQuantity() + d.getProduct().getQuantity());
			session.update(d.getProduct());
		}
		
		session.delete(invoice);
		session.getTransaction().commit();
	}

	@SuppressWarnings("unchecked")
	public List<Invoice> findAll() {
		List<Invoice> list = new ArrayList<Invoice>();
		Session session = HibernateConnect.getFactory().openSession();
		Query query = session.createQuery("From Invoice");
		list = query.getResultList();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Invoice> filter(Timestamp start, Timestamp end) {
		List<Invoice> list = new ArrayList<Invoice>();
		Session session = HibernateConnect.getFactory().openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Invoice> query = builder.createQuery(Invoice.class);
		Root<Invoice> root = query.from(Invoice.class);

		Predicate p = builder.between(root.get("createdDate"), start, end);

		query.where(builder.and(p));
		Query q = session.createQuery(query);
		list = q.getResultList();
		return list;
	}

	public static void main(String[] args) {
		InvoiceRepository invoiceRepository = new InvoiceRepository();

//		invoiceRepository.filter(new Timestamp(System.currentTimeMillis()),new Timestamp(System.currentTimeMillis())).forEach(p -> {
//			System.out.println(p);
//		});
//		;
		Session session = HibernateConnect.getFactory().openSession();
		Product product = session.get(Product.class, 3);

		Invoice invoice = new Invoice();
		Detail_Invoice detail_Invoice = new Detail_Invoice();
		detail_Invoice.setOutput_price(123D);
		detail_Invoice.setQuantity(4);
		detail_Invoice.setProduct(product);
		invoice.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		invoice.getDetail_Invoices().add(detail_Invoice);
		invoiceRepository.save(invoice);
	}
}
