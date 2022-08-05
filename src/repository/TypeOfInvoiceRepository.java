package repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;

import connect.HibernateConnect;
import domain.Invoice;
import domain.Type_Invoice;

public class TypeOfInvoiceRepository {

	@SuppressWarnings("unchecked")
	public List<Type_Invoice> findAll() {
		List<Type_Invoice> list = new ArrayList<Type_Invoice>();
		Session session = HibernateConnect.getFactory().openSession();
		Query query = session.createQuery("From Type_Invoice");
		list = query.getResultList();
		return list;
	}
}
