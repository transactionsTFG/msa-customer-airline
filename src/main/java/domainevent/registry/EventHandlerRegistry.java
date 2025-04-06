package domainevent.registry;

import java.util.EnumMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import domainevent.command.handler.EventHandler;

import msa.commons.event.EventId;
import msa.commons.microservices.customerairline.qualifier.StartedSagaCustomerQualifier;

@Singleton
@Startup
public class EventHandlerRegistry {
    private Map<EventId, EventHandler> handlers = new EnumMap<>(EventId.class);
    private EventHandler startCreationCustomerSagaHandler;

    @PostConstruct
    public void init(){
        this.handlers.put(EventId.RESERVATION_AIRLINE_INIT_CREATE_CUSTOMER, startCreationCustomerSagaHandler);
    }

    public EventHandler getHandler(EventId eventId) {
        return this.handlers.get(eventId);
    }

    @Inject
    public void setGetAircraftByIdHandler(@StartedSagaCustomerQualifier EventHandler startCreationCustomerSagaHandler) {
        this.startCreationCustomerSagaHandler = startCreationCustomerSagaHandler;
    }
}
