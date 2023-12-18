package int202.prefinalint202.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "employees")
@NamedQueries({
        @NamedQuery(name = "EMPLOYEE.FIND_ALL",
                query = "select e from Employee e"),
})

public class Employee {
    @Id
    private int employeeNumber;
    private String lastName;
    private String firstName;
    private String extension;
    private String email;
    private String officeCode;
    private Integer reportsTo;
    private String jobTitle;
    @ManyToOne
    @JoinColumn(name = "officeCode", insertable = false, updatable = false)
    private Office office;
}
