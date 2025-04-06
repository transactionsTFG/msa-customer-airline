package business.customer;

import lombok.Data;

@Data
public class CustomerDTO {
    private long id;
	private String name;
	private String email;
	private String phone;
	private String dni;
    private boolean active;
}
