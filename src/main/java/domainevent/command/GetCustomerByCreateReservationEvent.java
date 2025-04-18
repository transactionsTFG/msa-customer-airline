package domainevent.command;

import javax.ejb.Local;
import javax.ejb.Stateless;

import business.customer.CustomerDTO;
import domainevent.command.handler.BaseHandler;
import domainevent.command.handler.CommandPublisher;
import msa.commons.event.EventData;
import msa.commons.event.EventId;

import msa.commons.microservices.customerairline.qualifier.GetCustomerByCreateReservationEventQualifier;
import msa.commons.microservices.reservationairline.commandevent.CreateReservationCommand;

@Stateless
@GetCustomerByCreateReservationEventQualifier
@Local(CommandPublisher.class)
public class GetCustomerByCreateReservationEvent extends BaseHandler {

    @Override
    public void publishCommand(String json) {
        EventData event = EventData.fromJson(json, CreateReservationCommand.class);
        CreateReservationCommand c = (CreateReservationCommand) event.getData();
        CustomerDTO customerDTO = this.customerServices.getCustomerByDNI(c.getCustomerInfo().getDni());
        final boolean previouslyCreated = customerDTO != null;
        final long idCustomer = previouslyCreated ? customerDTO.getId() : 0L;
        c.getCustomerInfo().setIdCustomer(idCustomer);
        c.getCustomerInfo().setPreviouslyCreated(previouslyCreated);
        this.jmsEventPublisher.publish(EventId.FLIGHT_VALIDATE_FLIGHT_RESERVATION_AIRLINE_CREATE_RESERVATION, event);
    }
    
}
