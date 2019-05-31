package kp.e_j_b.remote;

import java.io.Serializable;

import javax.ejb.Stateful;

import kp.e_j_b.CommonImpl;

/**
 * Stateful Session Bean Implementation.<BR>
 * This is the Remote View Enterprise Bean.
 * 
 */
@Stateful
public class StaFuBean extends CommonImpl implements StaFu, Serializable {
	private static final long serialVersionUID = 1L;
}