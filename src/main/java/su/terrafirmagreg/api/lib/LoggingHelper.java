package su.terrafirmagreg.api.lib;

import su.terrafirmagreg.api.util.ModUtils;

import com.cleanroommc.groovyscript.api.GroovyLog;


import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ParameterizedMessage;
import org.intellij.lang.annotations.Flow;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static su.terrafirmagreg.api.data.Constants.MOD_NAME;

/**
 * A wrapper to the Log4J wrapper, which adds some extra utility, such as the stacktrace being added to warning messages.
 */
@Getter
@SuppressWarnings("unused")
public class LoggingHelper {

    public static final LoggingHelper LOGGER = LoggingHelper.of();
    /**
     * The logger delegate.
     */
    private final Logger logger;

    public static LoggingHelper of(String name) {
        return new LoggingHelper(LogManager.getLogger(ModUtils.name(name)));
    }

    public static LoggingHelper of() {
        return new LoggingHelper(LogManager.getLogger(MOD_NAME));
    }

    /**
     * Constructs the helper using a Log4J logger.
     *
     * @param logger The logger to use as a delegate.
     */
    private LoggingHelper(Logger logger) {

        this.logger = logger;
    }

    @NotNull
    public static LoggingHelper get() {
        return LoggingHelper.LOGGER;
    }

    public static List<String> wrapString(String string, int lnLength, boolean wrapLongWords, List<String> list) {

        final String[] lines = WordUtils.wrap(string, lnLength, null, wrapLongWords).split(SystemUtils.LINE_SEPARATOR);

        list.addAll(Arrays.asList(lines));

        return list;
    }

    public static String format(String msg, Object... args) {
        return args.length == 0 ? msg : new ParameterizedMessage(msg, args).getFormattedMessage();
    }

    /**
     * Logs an exception or error. This is intended for when you are catching an error and should be used over {@link Throwable#printStackTrace()}.
     *
     * @param t The Throwable object to catch.
     */
    public void catching(Throwable t) {

        this.logger.catching(t);
    }

    /**
     * Logs a debug message. Debug messages are not printed to the console, but they are added to the console file.
     *
     * @param message The message to print. Likely uses log4J's format which is {} for parameters.
     * @param params  The parameters for the messages. This can be used to insert info directly to the message, or completely ignored.
     */
    public void debug(String message, Object... params) {

        this.logger.debug(message, params);
    }

    /**
     * Logs an error message. Error messages are printed to the console and the log file.
     *
     * @param message The message to print. Likely uses log4J's format which is {} for parameters.
     * @param params  The parameters for the messages. This can be used to insert info directly to the message, or completely ignored.
     */
    public void error(String message, Object... params) {

        this.logger.error(message, params);
    }

    /**
     * Logs a fatal error message. This should be used to log errors which will prevent the game from working as expected and are likely to cause a crash. Fatal messages are
     * printed to the console and the log file.
     *
     * @param message The message to print. Likely uses log4J's format which is {} for parameters.
     * @param params  The parameters for the messages. This can be used to insert info directly to the message, or completely ignored.
     */
    public void fatal(String message, Object... params) {

        this.logger.fatal(message, params);
    }

    /**
     * Logs a generic info message. Info messages are printed to the console and the log file.
     *
     * @param message The message to print. Likely uses log4J's format which is {} for parameters.
     * @param params  The parameters for the messages. This can be used to insert info directly to the message, or completely ignored.
     */
    public void info(String message, Object... params) {

        this.logger.info(message, params);
    }

    /**
     * Logs a message at any Level.
     *
     * @param level   The level of the message to log.
     * @param message The message to print. Likely uses log4J's format which is {} for parameters.
     * @param params  The parameters for the messages. This can be used to insert info directly to the message, or completely ignored.
     */
    public void log(Level level, String message, Object... params) {

        this.logger.log(level, message, params);
    }

    /**
     * Logs a trace message. This is for fine grained debug messages. Trace messages are not printed to the console, but they are added to the console file.
     *
     * @param message The message to print. Likely uses log4J's format which is {} for parameters.
     * @param params  The parameters for the messages. This can be used to insert info directly to the message, or completely ignored.
     */
    public void trace(String message, Object... params) {

        this.logger.trace(message, params);
    }

    /**
     * Logs a warning message. These messages will also include a stacktrace. Warning messages are printed to the console and the log file.
     *
     * @param message The message to print. Likely uses log4J's format which is {} for parameters.
     * @param params  The parameters for the messages. This can be used to insert info directly to the message, or completely ignored.
     */
    public void warn(String message, Object... params) {

        this.logger.warn(message, params);
    }

    /**
     * Logs a warning message. These messages will also include a stacktrace. Warning messages are printed to the console and the log file.
     *
     * @param message The message to print. Likely uses log4J's format which is {} for parameters.
     * @param params  The parameters for the messages. This can be used to insert info directly to the message, or completely ignored.
     */
    public void warn(Throwable t, String message, Object... params) {

        this.logger.warn(message, params);
        this.logger.catching(t);
    }

    /**
     * Creates a noticeable warning, similar to the ones created by the FMLLog.
     *
     * @param trace If true, a small stack trace will be included in the error message.
     * @param lines Each entry will be printed as part of the error message. If any entry is longer than 78 chars it will be auto wrapped into multiple lines.
     */
    public void noticableWarning(boolean trace, List<String> lines) {

        this.error("********************************************************************************");

        for (final String line : lines) {

            for (final String subline : wrapString(line, 78, false, new ArrayList<>())) {

                this.error("* " + subline);
            }
        }

        if (trace) {

            final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

            for (int i = 2; i < 8 && i < stackTrace.length; i++) {
                this.warn("*  at {}{}", stackTrace[i].toString(), i == 7 ? "..." : "");
            }
        }

        this.error("********************************************************************************");
    }

    public static Msg msg(String msg, Object... data) {
        return new Msg(msg, data);
    }

    public static class Msg {

        private final List<String> messages = new ArrayList<>();

        private final String mainMsg;

        @Getter
        private Level level = Level.INFO;
        private boolean logToMcLog;
        @Nullable
        private Throwable throwable;

        public Msg(String msg, Object... data) {
            this.mainMsg = LoggingHelper.format(msg, data);
        }

        @Flow(source = "this.level")
        public boolean isValid() {
            return level != null;
        }

        public Msg add(String msg, Object... data) {
            this.messages.add(LoggingHelper.format(msg, data));
            return this;
        }

        public Msg add(boolean condition, String msg, Object... args) {
            if (condition) {
                return add(msg, args);
            }
            return this;
        }

        public Msg add(boolean condition, Supplier<String> msg) {
            if (condition) {
                return add(msg.get());
            }
            return this;
        }

        public Msg add(boolean condition, Consumer<Msg> msgBuilder) {
            if (condition) {
                msgBuilder.accept(this);
            }
            return this;
        }

        public Msg exception(Throwable throwable) {
            this.throwable = throwable;
            return this;
        }

        private Msg level(Level level) {
            this.level = level;
            return this;
        }

        public Msg info() {
            return level(Level.INFO);
        }

        public Msg debug() {
            return level(Level.DEBUG);
        }

        public Msg warn() {
            return level(Level.WARN);
        }

        public Msg fatal() {
            return level(Level.FATAL);
        }

        public Msg error() {
            return level(Level.ERROR);
        }

        public Msg logToMc(boolean logToMC) {
            this.logToMcLog = logToMC;
            return this;
        }

        public @NotNull String getMainMsg() {
            return mainMsg;
        }

        public @NotNull List<String> getSubMessages() {
            return messages;
        }

        public @Nullable Throwable getException() {
            return throwable;
        }

        public boolean shouldLogToMc() {
            return logToMcLog;
        }

        public boolean hasMessages() {
            return !this.messages.isEmpty();
        }

        /**
         * Returns if any sub messages (including the exception) exist in this message
         *
         * @return true if any sub messages exist
         */
        public boolean hasSubMessages() {
            return !getSubMessages().isEmpty() && getException() == null;
        }

        /**
         * Logs all messages of this message to {@link GroovyLog}, but only if {@link #hasSubMessages()} is true
         *
         * @return value of {@link #hasSubMessages()}
         */
        public boolean postIfNotEmpty() {
            return hasSubMessages();
        }
    }
}
