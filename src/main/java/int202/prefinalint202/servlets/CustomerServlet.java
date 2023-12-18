package int202.prefinalint202.servlets;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import int202.prefinalint202.entities.Customer;
import int202.prefinalint202.entities.Employee;
import int202.prefinalint202.repositories.CustomerRepository;
import int202.prefinalint202.repositories.EmployeeRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "CustomerServlet", value = "/005/customer")
public class CustomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "" : request.getParameter("action");
        String back = request.getParameter("back") == null ? "" : request.getParameter("back");
        if (back.equalsIgnoreCase("1")) {
            response.sendRedirect("../");
        } else if (action.equalsIgnoreCase("add")) {
            EmployeeRepository employeeRepository = new EmployeeRepository();
            List<Employee> employeeList = employeeRepository.findAll();
            request.setAttribute("employeeList", employeeList);
            request.getRequestDispatcher("../addCustomer.jsp").forward(request, response);
        } else {
            CustomerRepository customerRepository = new CustomerRepository();
            List<Customer> customerList = customerRepository.findAll();
            request.setAttribute("customerList", customerList);
            request.getRequestDispatcher("../customer.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "" : request.getParameter("action");
        CustomerRepository customerRepository = new CustomerRepository();
        if (action.equalsIgnoreCase("add")) {
            Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2d, 16, 16);
            int customerNumber = Integer.parseInt(request.getParameter("customerNumber"));
            String customerName = request.getParameter("customerName");
            String contactLastName = request.getParameter("contactLastName");
            String contactFirstName = request.getParameter("contactFirstName");
            String phone = request.getParameter("phone");
            String addressLine1 = request.getParameter("addressLine1");
            String addressLine2 = request.getParameter("addressLine2");
            String city = request.getParameter("city");
            String state = request.getParameter("state");
            String postalCode = request.getParameter("postalCode");
            String country = request.getParameter("country");
            int salesRepEmployee = Integer.parseInt(request.getParameter("salesRepEmployee"));
            Double creditLimit = Double.parseDouble(request.getParameter("creditLimit"));
            String password = argon2.hash(2, 16, 1, request.getParameter("customerNumber").toCharArray());
            Customer customer = new Customer(customerNumber, customerName, contactLastName, contactFirstName, phone, addressLine1,
                    addressLine2, city, state, postalCode, country, salesRepEmployee, creditLimit, password);
            customerRepository.insertCustomer(customer);
            response.sendRedirect("../005/customer");
        }
    }
}
