package business.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import business.customer.Customer;
import business.customer.CustomerDTO;
import msa.commons.microservices.reservationairline.commandevent.model.CustomerInfo;
import msa.commons.saga.SagaPhases;

@Mapper
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);
    
    CustomerDTO entityToDto(Customer customer);
    
    @Mapping(target = "statusSaga", source = "sagaPhase" )
    Customer dtoToEntity(CustomerDTO customerDTO, SagaPhases sagaPhase);

    CustomerDTO customerInfoToDtoBusiness(CustomerInfo customerInfo);
}
