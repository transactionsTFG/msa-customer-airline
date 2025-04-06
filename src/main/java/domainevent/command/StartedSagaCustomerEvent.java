package domainevent.command;

import javax.ejb.Local;
import javax.ejb.Stateless;

import business.customer.CustomerDTO;
import business.mapper.CustomerMapper;
import domainevent.command.handler.BaseHandler;
import domainevent.command.handler.EventHandler;
import msa.commons.event.EventId;
import msa.commons.microservices.customerairline.commandevent.ValidateFlightCommand;
import msa.commons.microservices.customerairline.qualifier.StartedSagaCustomerQualifier;
import msa.commons.microservices.reservationairline.commandevent.CreateCustomerCommand;

@Stateless
@StartedSagaCustomerQualifier
@Local(EventHandler.class)
public class StartedSagaCustomerEvent extends BaseHandler {

    @Override
    public void handleCommand(Object data) {
        final CreateCustomerCommand command = (CreateCustomerCommand) data;
        boolean previouslyCreated = true;
        CustomerDTO customerDTO = this.customerServices.getCustomerByDNI(command.getCustomerInfo().getDni());
        if(customerDTO == null) {
            previouslyCreated = false;
            customerDTO = this.customerServices.beginSagaSaveCustomer(CustomerMapper.INSTANCE.customerInfoToDtoBusiness(command.getCustomerInfo()));
        }

        ValidateFlightCommand buildValidateFlightCommand = new ValidateFlightCommand();
        buildValidateFlightCommand.setFlightInstanceInfo(command.getFlightInstanceInfo());
        buildValidateFlightCommand.setIdCustomner(customerDTO.getId());
        buildValidateFlightCommand.setPreviouslyCreated(previouslyCreated);
        this.jmsEventPublisher.publish(EventId.CUSTOMER_AIRLINE_INIT_VALIDATE_FLIGHT, buildValidateFlightCommand);
    }
    
}
