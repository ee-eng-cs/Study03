package kp.e_j_b.remote;

import java.io.Serializable;

import javax.ejb.Singleton;

import kp.e_j_b.CommonImpl;

/**
 * Singleton Session Bean Implementation.<BR>
 * This is the Remote View Enterprise Bean.
 * 
 */
@Singleton
public class SingBean extends CommonImpl implements Sing, Serializable {
	private static final long serialVersionUID = 1L;
}