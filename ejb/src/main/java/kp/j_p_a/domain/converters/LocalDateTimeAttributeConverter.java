package kp.j_p_a.domain.converters;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * The attribute converter for <B>local date time</B>.
 *
 */
@Converter(autoApply = true)
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {
		return Objects.nonNull(localDateTime) ? Timestamp.valueOf(localDateTime) : null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public LocalDateTime convertToEntityAttribute(Timestamp timestamp) {
		return Objects.nonNull(timestamp) ? timestamp.toLocalDateTime() : null;
	}
}