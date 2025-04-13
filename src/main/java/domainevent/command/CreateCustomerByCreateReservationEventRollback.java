package domainevent.command;

import javax.ejb.Local;
import javax.ejb.Stateless;

import domainevent.command.handler.BaseHandler;
import domainevent.command.handler.CommandPublisher;
import msa.commons.event.EventData;
import msa.commons.event.EventId;
import msa.commons.microservices.customerairline.qualifier.CreateCustomerByCreateReservationEventRollbackQualifier;
import msa.commons.microservices.reservationairline.commandevent.CreateReservationCommand;

@Stateless
@CreateCustomerByCreateReservationEventRollbackQualifier
@Local(CommandPublisher.class)
public class CreateCustomerByCreateReservationEventRollback  extends BaseHandler {

    @Override
    public void publishCommand(String json) {
        EventData event = EventData.fromJson(json, CreateReservationCommand.class);
        CreateReservationCommand c = (CreateReservationCommand) event.getData();
        if (!c.getCustomerInfo().isPreviouslyCreated() && this.customerServices.validateCustomerSagaId(c.getCustomerInfo().getName(), event.getSagaId())) 
            this.customerServices.remove(c.getCustomerInfo().getIdCustomer());
        this.jmsEventPublisher.publish(EventId.FLIGHT_UPDATE_FLIGHT_AIRLINE_CREATE_RESERVATION_ROLLBACK_SAGA, event);
    }
    
}
