package domainevent.command;

import javax.ejb.Local;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mysql.cj.log.Log;

import business.customer.CustomerDTO;
import business.mapper.CustomerMapper;
import domainevent.command.handler.BaseHandler;
import domainevent.command.handler.EventHandler;
import msa.commons.event.EventId;
import msa.commons.microservices.customerairline.qualifier.CreateCustomerByCreateReservationEventQualifier;
import msa.commons.microservices.reservationairline.commandevent.CreateReservationCommand;

@Stateless
@CreateCustomerByCreateReservationEventQualifier
@Local(EventHandler.class)
public class CreateCustomerByCreateReservationEvent extends BaseHandler {
    @Override
    public void handleCommand(String json) {
        CreateReservationCommand c = this.gson.fromJson(json, CreateReservationCommand.class);
        CustomerDTO customerDTO = CustomerMapper.INSTANCE.customerInfoCommandCreateReserationToDto(c.getCustomerInfo());
        if (!c.getCustomerInfo().isPreviouslyCreated()) 
            customerDTO = this.customerServices.save(customerDTO);
        c.getCustomerInfo().setIdCustomer(customerDTO.getId());
        this.jmsEventPublisher.publish(EventId.RESERVATION_AIRLINE_CREATE_RESERVATION_COMMIT_SAGA, c);
    }
    
}
