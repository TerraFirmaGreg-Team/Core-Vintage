package net.dries007.tfc;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static org.apache.logging.log4j.Level.*;

/**
 * Этот класс предоставляет логгер для TerraFirmaGreg, который полезен для отладки.
 * <p>
 * Для того, чтобы использовать этот логгер правильно, необходимо отредактировать файл конфигурации Forge Log4j и добавить следующие строки:
 *
 * <pre>{@code
 * <Logger level="all" name="TerraFirmaGreg" additivity="false">
 *      <AppenderRef ref="FmlSysOut" />
 * </Logger>
 * }</pre>
 * <p>
 * Конфигурационный файл также необходимо поместить в рабочий каталог.
 * Кроме того, при запуске программы нужно добавить следующий параметр VM:
 *
 * <pre>-Dlog4j.configurationFile=path/to/log4j2.xml</pre>
 */

public final class TFCLogger {

    private static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    private final String name;
    private final Level maxLevel;

    public TFCLogger(Level maxLevel) {
        this((String) null, maxLevel);
    }

    public TFCLogger(Class<?> clazz, Level maxLevel) {
        this(clazz.getSimpleName(), maxLevel);
    }

    public TFCLogger(String name, Level maxLevel) {
        this.name = name;
        this.maxLevel = Level.DEBUG;
    }

    private void log(Level level, String message, Object... params) {
        if (level.isMoreSpecificThan(maxLevel) || level.compareTo(maxLevel) == 0) {
            String prefix = name == null ? "" : "[" + name + "] ";
            LOGGER.log(level, prefix + message, params);
        }
    }

    public void fatal(String message, Object... params) {
        log(FATAL, message, params);
    }

    public void error(String message, Object... params) {
        log(ERROR, message, params);
    }

    public void warn(String message, Object... params) {
        log(WARN, message, params);
    }

    public void info(String message, Object... params) {
        log(INFO, message, params);
    }

    public void debug(String message, Object... params) {
        log(DEBUG, message, params);
    }

    public void trace(String message, Object... params) {
        log(TRACE, message, params);
    }
}
