package business.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import business.customer.Customer;
import business.customer.CustomerDTO;
import msa.commons.microservices.reservationairline.commandevent.model.CustomerInfo;

@Mapper
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);
    CustomerDTO entityToDto(Customer customer);

    @Mapping(target = "version", ignore = true)
    Customer dtoToEntity(CustomerDTO customerDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    CustomerDTO customerInfoCommandCreateReserationToDto(CustomerInfo customerInfo);
}
