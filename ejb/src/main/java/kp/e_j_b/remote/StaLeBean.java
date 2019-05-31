package kp.e_j_b.remote;

import java.io.Serializable;

import javax.ejb.Stateless;

import kp.e_j_b.CommonImpl;

/**
 * Stateless Session Bean Implementation.<BR>
 * This is the Remote View Enterprise Bean.
 * 
 */
@Stateless
public class StaLeBean extends CommonImpl implements StaLe, Serializable {
	private static final long serialVersionUID = 1L;
}