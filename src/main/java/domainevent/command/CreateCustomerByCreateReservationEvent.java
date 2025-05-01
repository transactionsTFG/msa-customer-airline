package domainevent.command;

import javax.ejb.Local;
import javax.ejb.Stateless;


import business.customer.CustomerDTO;
import business.mapper.CustomerMapper;
import business.qualifier.createreservation.CreateCustomerByCreateReservationEventQualifier;
import domainevent.command.handler.BaseHandler;
import domainevent.command.handler.CommandPublisher;
import msa.commons.commands.createreservation.CreateReservationCommand;
import msa.commons.event.EventData;
import msa.commons.event.EventId;


@Stateless
@CreateCustomerByCreateReservationEventQualifier
@Local(CommandPublisher.class)
public class CreateCustomerByCreateReservationEvent extends BaseHandler {
    @Override
    public void publishCommand(String json) {
        EventData event = EventData.fromJson(json, CreateReservationCommand.class);
        CreateReservationCommand c = (CreateReservationCommand) event.getData();
        CustomerDTO customerDTO = CustomerMapper.INSTANCE.customerInfoCommandCreateReserationToDto(c.getCustomerInfo());
        customerDTO.setSagaId(event.getSagaId());
        if (!c.getCustomerInfo().isPreviouslyCreated()) 
            customerDTO = this.customerServices.save(customerDTO);
        c.getCustomerInfo().setIdCustomer(customerDTO.getId());
        this.jmsEventPublisher.publish(EventId.RESERVATION_AIRLINE_CREATE_RESERVATION_COMMIT_SAGA, event);
    }
    
}
