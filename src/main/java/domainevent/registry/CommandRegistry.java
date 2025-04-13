package domainevent.registry;

import java.util.EnumMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import domainevent.command.handler.CommandPublisher;

import msa.commons.event.EventId;
import msa.commons.microservices.customerairline.qualifier.CreateCustomerByCreateReservationEventQualifier;
import msa.commons.microservices.customerairline.qualifier.GetCustomerByCreateReservationEventQualifier;

@Singleton
@Startup
public class CommandRegistry {
    private Map<EventId, CommandPublisher> handlers = new EnumMap<>(EventId.class);
    private CommandPublisher getCustomerByCreateReservationEvent;
    private CommandPublisher createCustomerByCreateReservationEvent;


    @PostConstruct
    public void init(){
        this.handlers.put(EventId.CUSTOMER_AIRLINE_GET_CUSTOMER_RESERVATION_AIRLINE_CREATE_RESERVATION, getCustomerByCreateReservationEvent);
        this.handlers.put(EventId.CUSTOMER_AIRLINE_CREATE_CUSTOMER_RESERVATION_AIRLINE_CREATE_RESERVATION_COMMIT_SAGA, createCustomerByCreateReservationEvent);
    }

    public CommandPublisher getHandler(EventId eventId) {
        return this.handlers.get(eventId);
    }

    @Inject
    public void setGetCustomerByCreateReservationEventr(@GetCustomerByCreateReservationEventQualifier CommandPublisher getCustomerByCreateReservationEvent) {
        this.getCustomerByCreateReservationEvent = getCustomerByCreateReservationEvent;
    }

    @Inject
    public void setCreateCustomerByCreateReservationEvent(@CreateCustomerByCreateReservationEventQualifier CommandPublisher createCustomerByCreateReservationEvent) {
        this.createCustomerByCreateReservationEvent = createCustomerByCreateReservationEvent;
    }
}
