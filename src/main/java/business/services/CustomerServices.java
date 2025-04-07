package business.services;

import business.customer.CustomerDTO;

public interface CustomerServices {
    CustomerDTO getCustomerByDNI(String dni);
    CustomerDTO save(CustomerDTO customerDTO);
}
