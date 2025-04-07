package domainevent.registry;

import java.util.EnumMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import domainevent.command.handler.EventHandler;

import msa.commons.event.EventId;
import msa.commons.microservices.customerairline.qualifier.CreateCustomerByCreateReservationEventQualifier;
import msa.commons.microservices.customerairline.qualifier.GetCustomerByCreateReservationEventQualifier;

@Singleton
@Startup
public class EventHandlerRegistry {
    private Map<EventId, EventHandler> handlers = new EnumMap<>(EventId.class);
    private EventHandler getCustomerByCreateReservationEvent;
    private EventHandler createCustomerByCreateReservationEvent;


    @PostConstruct
    public void init(){
        this.handlers.put(EventId.CUSTOMER_AIRLINE_GET_CUSTOMER_RESERVATION_AIRLINE_CREATE_RESERVATION, getCustomerByCreateReservationEvent);
        this.handlers.put(EventId.CUSTOMER_AIRLINE_CREATE_CUSTOMER_RESERVATION_AIRLINE_CREATE_RESERVATION_COMMIT_SAGA, createCustomerByCreateReservationEvent);
    }

    public EventHandler getHandler(EventId eventId) {
        return this.handlers.get(eventId);
    }

    @Inject
    public void setGetCustomerByCreateReservationEventr(@GetCustomerByCreateReservationEventQualifier EventHandler getCustomerByCreateReservationEvent) {
        this.getCustomerByCreateReservationEvent = getCustomerByCreateReservationEvent;
    }

    @Inject
    public void setCreateCustomerByCreateReservationEvent(@CreateCustomerByCreateReservationEventQualifier EventHandler createCustomerByCreateReservationEvent) {
        this.createCustomerByCreateReservationEvent = createCustomerByCreateReservationEvent;
    }
}
