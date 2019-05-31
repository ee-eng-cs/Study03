package kp;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Constants.
 *
 */
public interface Constants {
	String BREAK = IntStream.rangeClosed(1, 150).boxed().map(arg -> "#").collect(Collectors.joining());
	String LINE = IntStream.rangeClosed(1, 50).boxed().map(arg -> "- ").collect(Collectors.joining());
}