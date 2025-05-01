package domainevent.command;

import javax.ejb.Local;
import javax.ejb.Stateless;

import business.customer.CustomerDTO;
import business.qualifier.createreservation.GetCustomerByCreateReservationEventQualifier;
import domainevent.command.handler.BaseHandler;
import domainevent.command.handler.CommandPublisher;
import msa.commons.commands.createreservation.CreateReservationCommand;
import msa.commons.event.EventData;
import msa.commons.event.EventId;


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
