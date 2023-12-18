package int202.prefinalint202.repositories;

import int202.prefinalint202.entities.Customer;
import int202.prefinalint202.utilities.EntityManagerBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;

public class CustomerRepository {
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        if (entityManager == null || !entityManager.isOpen()) {
            entityManager = EntityManagerBuilder.getEntityManager();
        }
        return entityManager;
    }

    public boolean insertCustomer(Customer customer) {
        try {
            EntityManager em = getEntityManager();
            em.getTransaction().begin();
            em.persist(customer);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public Customer findByUserName(String accountName) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("CUSTOMER.FIND_USERNAME");
        query.setParameter("userName", accountName);
        Customer customer = null;
        try {
            customer = (Customer) query.getSingleResult();
        } catch (Exception e) {
            System.out.println("No result");
        }
        return customer;
    }

    public Customer findByCustomerNumber(int customerNumber) {
        return getEntityManager().find(Customer.class, customerNumber);
    }

    public List<Customer> findAll() {
        return getEntityManager().createNamedQuery("CUSTOMER.FIND_ALL").getResultList();
    }

    public boolean updateCustomer(Customer newCustomer) {
        EntityManager em = getEntityManager();
        Customer customer = (Customer) findByCustomerNumber(newCustomer.getCustomerNumber());
        if (customer != null) {
            em.getTransaction().begin();
            customer.setCustomerName(newCustomer.getCustomerName() == null ? customer.getCustomerName() : newCustomer.getCustomerName());
            customer.setContactLastName(newCustomer.getContactLastName() == null ? customer.getContactLastName() : newCustomer.getContactLastName());
            // more ...
            em.getTransaction().commit();
            return true;
        }
        return false;
    }

    public boolean removeCustomer(int customerNumber) {
        EntityManager em = getEntityManager();
        Customer customer = findByCustomerNumber(customerNumber);
        if (customer != null) {
            em.getTransaction().begin();
            em.remove(customer);
            em.getTransaction().commit();
            return true;
        }
        return false;
    }

}
