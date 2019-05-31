package kp.j_p_a.domain.components;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The <B>term dates</B>.
 *
 */
@Embeddable
public class TermDates {
	/*-
	 * The '@Temporal' may only be used with 'java.util.Date' or 'java.util.Calendar'.
	 * 
	 * For 'LocalDate' and 'LocalDateTime' it was implemented the 'AttributeConverter'.
	 */

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar calendar;

	private LocalDate localDate;

	private LocalDateTime localDateTime;

	/**
	 * Constructor with initialization.
	 * 
	 */
	public TermDates() {
		super();
		this.date = new Date();
		this.calendar = Calendar.getInstance();
		this.localDate = LocalDate.now();
		this.localDateTime = LocalDateTime.now();
	}

	/**
	 * Get the date.
	 * 
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Set the date.
	 * 
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Get the calendar.
	 * 
	 * @return the calendar
	 */
	public Calendar getCalendar() {
		return calendar;
	}

	/**
	 * Set the calendar.
	 * 
	 * @param calendar the calendar to set
	 */
	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

	/**
	 * Get the local date.
	 * 
	 * @return the local date
	 */
	public LocalDate getLocalDate() {
		return localDate;
	}

	/**
	 * Set the local date.
	 * 
	 * @param localDate the local date to set
	 */
	public void setLocalDate(LocalDate localDate) {
		this.localDate = localDate;
	}

	/**
	 * Get the local date and time.
	 * 
	 * @return the local date and time
	 */
	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}

	/**
	 * Set the local date and time.
	 * 
	 * @param localDateTime the local date and time to set
	 */
	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}
}