package com.klef.jfsd.exam;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class ClientDemo {

    public static void main(String[] args) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.getCurrentSession();

        try {
            // I. Insert records manually in the table or use persistent object

            // Create some Customer objects
            Customer customer1 = new Customer("John Doe", "johndoe@example.com", 25, "New York");
            Customer customer2 = new Customer("Jane Smith", "janesmith@example.com", 30, "California");

            // Start a transaction
            session.beginTransaction();

            // Save the customers to the database
            session.save(customer1);
            session.save(customer2);

            // Commit the transaction
            session.getTransaction().commit();

            // II. Apply restrictions using Criteria Interface
            // Reopen a new session
            session = factory.getCurrentSession();
            session.beginTransaction();

            // Using Criteria to query customers with age greater than 25
            Criteria criteria = session.createCriteria(Customer.class);
            criteria.add(Restrictions.gt("age", 25));  // greater than 25

            List<Customer> customers = criteria.list();
            for (Customer customer : customers) {
                System.out.println(customer.getName());
            }

            // Using NOT EQUAL restriction
            criteria = session.createCriteria(Customer.class);
            criteria.add(Restrictions.ne("age", 30));  // not equal to 30

            customers = criteria.list();
            for (Customer customer : customers) {
                System.out.println(customer.getName());
            }

            // Using BETWEEN restriction
            criteria = session.createCriteria(Customer.class);
            criteria.add(Restrictions.between("age", 20, 30));  // age between 20 and 30

            customers = criteria.list();
            for (Customer customer : customers) {
                System.out.println(customer.getName());
            }

            // Commit the transaction
            session.getTransaction().commit();
        } finally {
            factory.close();
        }
    }
}
