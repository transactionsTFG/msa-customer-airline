package business.customer;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import msa.commons.saga.SagaPhases;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(name ="customer.findByDNI", query = "SELECT c FROM Customer c WHERE c.dni = :dni")
@Table(name = "customer")
public class Customer {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
	private long id;
	@Column(nullable = false, name = "first_name")
	private String name;
	@Column(nullable = false, name = "email")
	private String email;
	@Column(nullable = false, name = "phone")
	private String phone;
	@Column(nullable = false, unique = true, name = "dni")
	private String dni;
	@Column(nullable = false, name = "is_active")
    private boolean active;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "status_saga")
    private SagaPhases statusSaga;
	@Version
    @Column(name = "version")
	private int version;
}
