package kp.j_p_a.domain.units;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * The entity class for <B>side</B>.
 *
 */
@Entity
public class Side {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
}
