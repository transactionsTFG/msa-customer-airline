package business.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import business.customer.Customer;
import business.customer.CustomerDTO;
import business.mapper.CustomerMapper;

@Stateless
public class CustomerServicesImpl implements CustomerServices {

    private EntityManager entityManager;

    @Override
    public CustomerDTO getCustomerByDNI(String dni) {
        List<Customer> listCustomer = this.entityManager.createNamedQuery("customer.findByDNI", Customer.class)
                                        .setParameter("dni", dni)
                                        .setLockMode(LockModeType.OPTIMISTIC)
                                        .getResultList();
        return listCustomer.isEmpty() ? null : CustomerMapper.INSTANCE.entityToDto(listCustomer.get(0));
    }

    @Override
    public CustomerDTO save(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setDni(customerDTO.getDni());
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPhone(customerDTO.getPhone());
        this.entityManager.persist(customer);
        this.entityManager.flush();
        return CustomerMapper.INSTANCE.entityToDto(customer);
    }

    @Inject
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
