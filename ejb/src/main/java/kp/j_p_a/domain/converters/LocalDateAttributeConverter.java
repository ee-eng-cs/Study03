package kp.j_p_a.domain.converters;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * The attribute converter for <B>local date</B>.
 *
 */
@Converter(autoApply = true)
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, Date> {

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public Date convertToDatabaseColumn(LocalDate localDate) {
		return Objects.nonNull(localDate) ? Date.valueOf(localDate) : null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public LocalDate convertToEntityAttribute(Date date) {
		return Objects.nonNull(date) ? date.toLocalDate() : null;
	}
}