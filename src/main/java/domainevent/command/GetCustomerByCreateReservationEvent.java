package domainevent.command;

import javax.ejb.Local;
import javax.ejb.Stateless;

import business.customer.CustomerDTO;
import domainevent.command.handler.BaseHandler;
import domainevent.command.handler.EventHandler;
import msa.commons.event.EventId;

import msa.commons.microservices.customerairline.qualifier.GetCustomerByCreateReservationEventQualifier;
import msa.commons.microservices.reservationairline.commandevent.CreateReservationCommand;

@Stateless
@GetCustomerByCreateReservationEventQualifier
@Local(EventHandler.class)
public class GetCustomerByCreateReservationEvent extends BaseHandler {

    @Override
    public void handleCommand(Object data) {
        final CreateReservationCommand command = this.gson.fromJson(data.toString(), CreateReservationCommand.class);
        CustomerDTO customerDTO = this.customerServices.getCustomerByDNI(command.getCustomerInfo().getDni());
        final boolean previouslyCreated = customerDTO != null;
        final long idCustomer = previouslyCreated ? customerDTO.getId() : 0L;
        command.getCustomerInfo().setIdCustomer(idCustomer);
        command.getCustomerInfo().setPreviouslyCreated(previouslyCreated);
        this.jmsEventPublisher.publish(EventId.FLIGHT_VALIDATE_FLIGHT_RESERVATION_AIRLINE_CREATE_RESERVATION, command);
    }
    
}
