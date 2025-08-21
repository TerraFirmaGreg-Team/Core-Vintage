package su.terrafirmagreg.api.library.exception;

import org.apache.commons.lang3.StringEscapeUtils;

public class InvalidIdentifierException
  extends RuntimeException {

  public InvalidIdentifierException(String message) {
    super(StringEscapeUtils.escapeJava(message));
  }

  public InvalidIdentifierException(String message, Throwable throwable) {
    super(StringEscapeUtils.escapeJava(message), throwable);
  }
}
