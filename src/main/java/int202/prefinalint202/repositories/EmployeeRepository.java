package int202.prefinalint202.repositories;

import int202.prefinalint202.entities.Employee;
import int202.prefinalint202.entities.Office;
import int202.prefinalint202.utilities.EntityManagerBuilder;
import jakarta.persistence.EntityManager;

import java.util.List;

public class EmployeeRepository {
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        if (entityManager == null || !entityManager.isOpen()) {
            entityManager = EntityManagerBuilder.getEntityManager();
        }
        return entityManager;
    }

    public List<Employee> findAll() {
        return getEntityManager().createNamedQuery("EMPLOYEE.FIND_ALL").getResultList();
    }
}
