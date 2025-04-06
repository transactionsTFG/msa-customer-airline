package business.services;

import business.customer.CustomerDTO;

public interface CustomerServices {
    CustomerDTO getCustomerByDNI(String dni);
    CustomerDTO beginSagaSaveCustomer(CustomerDTO customerDTO);
    void endSuccesSagaSaveCustomer(CustomerDTO customerDTO);
    void endRemoveSagaSaveCustomer(CustomerDTO customerDTO);
}
