@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
package su.terrafirmagreg.api.spi.container;

import mcp.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;

// Весь этот пакет состоит из BlockBase и его копипаста во все основные классы майна, все блоки мода должны наследовать эти классы а не ванильные,
// при переходе на Cleanroom и Millennium, потребуется меньше усилий.
// Это сделано именно копипипастом BlockBase, а не по новому реализовывать от BlockBase,
// для сохранения обратной совместимости, возможно в будущем это изменится.
