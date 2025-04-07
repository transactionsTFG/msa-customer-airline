package domainevent.command;

import javax.ejb.Local;
import javax.ejb.Stateless;

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
    public void handleCommand(Object data) {
        CreateReservationCommand c = (CreateReservationCommand) data;
        if (!c.isPreviouslyCreated()) 
            this.customerServices.save(CustomerMapper.INSTANCE.customerInfoCommandCreateReserationToDto(c.getCustomerInfo()));
        this.jmsEventPublisher.publish(EventId.RESERVATION_AIRLINE_CREATE_RESERVATION_COMMIT_SAGA, c);
    }
    
}
