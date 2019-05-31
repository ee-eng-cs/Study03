package kp.j_p_a.domain.boxes;

import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;

import kp.j_p_a.domain.components.CardinalDirection;
import kp.j_p_a.domain.components.TermDates;

/**
 * The <B>central box</B> entity class.<BR>
 * If the relationship is bidirectional, the non-owning side must use the
 * <B>mappedBy</B> element.
 * 
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "CentralBox.findAll", query = "SELECT DISTINCT cb "
				+ "FROM CentralBox cb LEFT JOIN FETCH cb.multiBoxes LEFT JOIN FETCH cb.lowerBoxes ORDER BY cb.id"),
		@NamedQuery(name = "CentralBox.findFirst", query = "SELECT DISTINCT cb "
				+ "FROM CentralBox cb LEFT JOIN FETCH cb.multiBoxes LEFT JOIN FETCH cb.lowerBoxes "
				+ "WHERE cb.id = (SELECT MIN(cbm.id) FROM CentralBox cbm)") })
public class CentralBox extends Box {

	@Embedded
	private TermDates termDates;

	@Enumerated(EnumType.STRING)
	private CardinalDirection cardinalDirection = CardinalDirection.NORTH;

	// owning side of bidirectional relationship
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	private UpperBox upperBox;

	// owning side of bidirectional relationship
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private SingleBox singleBox;

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@OrderBy
	private Set<MultiBox> multiBoxes = new TreeSet<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy
	private Set<LowerBox> lowerBoxes = new TreeSet<>();

	/**
	 * Adds the multi box.
	 * 
	 * @param multiBox the multi box to add
	 */
	public void addMultiBox(MultiBox multiBox) {

		multiBoxes.add(multiBox);
		multiBox.getCentralBoxes().add(this);
	}

	/**
	 * Removes the multi box.
	 * 
	 * @param multiBox the multi box to remove
	 */
	public void removeMultiBox(MultiBox multiBox) {

		multiBoxes.remove(multiBox);
		multiBox.getCentralBoxes().remove(this);
	}

	/**
	 * Adds the lower box.
	 * 
	 * @param lowerBox the lower box to add
	 */
	public void addLowerBox(LowerBox lowerBox) {

		lowerBoxes.add(lowerBox);
		lowerBox.setCentralBox(this);
	}

	/**
	 * Removes the lower box.
	 * 
	 * @param lowerBox the lower box to remove
	 */
	public void removeLowerBox(LowerBox lowerBox) {

		lowerBoxes.remove(lowerBox);
		lowerBox.setCentralBox(null);
	}

	/**
	 * Gets the term dates.
	 * 
	 * @return the term dates
	 */
	public TermDates getTermDates() {
		return termDates;
	}

	/**
	 * Sets the term dates.
	 * 
	 * @param termDates the term dates to set
	 */
	public void setTermDates(TermDates termDates) {
		this.termDates = termDates;
	}

	/**
	 * Gets the cardinal direction.
	 * 
	 * @return the cardinal direction
	 */
	public CardinalDirection getCardinalDirection() {
		return cardinalDirection;
	}

	/**
	 * Sets the cardinal direction.
	 * 
	 * @param cardinalDirection the cardinal direction to set
	 */
	public void setCardinalDirection(CardinalDirection cardinalDirection) {
		this.cardinalDirection = cardinalDirection;
	}

	/**
	 * Gets the upper box.
	 * 
	 * @return the upper box
	 */
	public UpperBox getUpperBox() {
		return upperBox;
	}

	/**
	 * Sets the upper box.
	 * 
	 * @param upperBox the upper box to set
	 */
	public void setUpperBox(UpperBox upperBox) {
		this.upperBox = upperBox;
	}

	/**
	 * Gets the single box.
	 * 
	 * @return the single box
	 */
	public SingleBox getSingleBox() {
		return singleBox;
	}

	/**
	 * Sets the single box.
	 * 
	 * @param singleBox the single box to set
	 */
	public void setSingleBox(SingleBox singleBox) {
		this.singleBox = singleBox;
	}

	/**
	 * Gets the multi boxes.
	 * 
	 * @return the multi boxes set
	 */
	public Set<MultiBox> getMultiBoxes() {
		return multiBoxes;
	}

	/**
	 * Gets the lower boxes.
	 * 
	 * @return the lower boxes set
	 */
	public Set<LowerBox> getLowerBoxes() {
		return lowerBoxes;
	}
}