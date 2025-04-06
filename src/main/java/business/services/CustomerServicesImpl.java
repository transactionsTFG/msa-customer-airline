package business.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import business.customer.Customer;
import business.customer.CustomerDTO;
import business.mapper.CustomerMapper;
import msa.commons.saga.SagaPhases;

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
    public CustomerDTO beginSagaSaveCustomer(CustomerDTO customerDTO) {
        customerDTO.setActive(false);
        Customer c = CustomerMapper.INSTANCE.dtoToEntity(customerDTO, SagaPhases.STARTED);
        this.entityManager.persist(c);
        this.entityManager.flush();
        return CustomerMapper.INSTANCE.entityToDto(c);
    }

    
    @Override
    public void endSuccesSagaSaveCustomer(CustomerDTO customerDTO) {
        Customer c = this.entityManager.createNamedQuery("customer.findByDNI", Customer.class)
            .setParameter("dni", customerDTO.getDni())
            .setLockMode(LockModeType.OPTIMISTIC).getSingleResult();
        c.setActive(true);
        c.setStatusSaga(SagaPhases.COMPLETED);
        this.entityManager.merge(c);
    }

    @Override
    public void endRemoveSagaSaveCustomer(CustomerDTO customerDTO) {
        Customer c = this.entityManager.createNamedQuery("customer.findByDNI", Customer.class)
            .setParameter("dni", customerDTO.getDni())
            .setLockMode(LockModeType.OPTIMISTIC).getSingleResult();
        this.entityManager.remove(c);
    }

    @Inject
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
