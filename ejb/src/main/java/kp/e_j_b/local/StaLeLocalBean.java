package kp.e_j_b.local;

import java.io.Serializable;

import javax.ejb.Stateless;

import kp.e_j_b.CommonImpl;

/**
 * Stateless Session Bean Implementation.<BR>
 * This is Local View Enterprise Bean.
 */
@Stateless
public class StaLeLocalBean extends CommonImpl implements StaLeLocal, Serializable {
	private static final long serialVersionUID = 1L;
}