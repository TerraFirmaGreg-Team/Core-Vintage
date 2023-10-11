package su.terrafirmagreg.api.modules;

import net.minecraftforge.fml.common.LoaderState;

/**
 * В основном {@link LoaderState}, но только для этапов запуска.
 * Также включает ранние этапы модуля.
 */
public enum ModuleStage {
    C_SETUP,               // Initializing ModuleContainers
    M_SETUP,               // Initializing Modules
    CONSTRUCTION,          // MC Construction stage
    PRE_INIT,              // MC PreInitialization stage
    INIT,                  // MC Initialization stage
    POST_INIT,             // MC PostInitialization stage
    FINISHED,              // MC LoadComplete stage
    SERVER_ABOUT_TO_START, // MC ServerAboutToStart stage
    SERVER_STARTING,       // MC ServerStarting stage
    SERVER_STARTED         // MC ServerStarted stage
}
