package connect;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import domain.Account;
import domain.Category;
import domain.Detail_Invoice;
import domain.Employee;
import domain.Invoice;
import domain.Product;
import domain.Type_Invoice;


public class HibernateConnect {
	private final static SessionFactory FACTORY;

	static {
		Configuration conf = new Configuration();
		Properties props = new Properties();
		// set gía trị cho properties
		props.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
		props.put(Environment.URL, "jdbc:mysql://localhost:3306/1tung");
		props.put(Environment.USER, "root");
		props.put(Environment.PASS, "");
		props.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
		props.put(Environment.SHOW_SQL, "true");
		conf.addAnnotatedClass(Account.class);
		conf.addAnnotatedClass(Category.class);
		conf.addAnnotatedClass(Detail_Invoice.class);
		conf.addAnnotatedClass(Employee.class);
		conf.addAnnotatedClass(Invoice.class);
		conf.addAnnotatedClass(Product.class);
		conf.addAnnotatedClass(Type_Invoice.class);
		conf.setProperties(props);
		
		ServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
		FACTORY = conf.buildSessionFactory(registry);
	}
	public static SessionFactory getFactory() {
		return FACTORY;
	}
}
