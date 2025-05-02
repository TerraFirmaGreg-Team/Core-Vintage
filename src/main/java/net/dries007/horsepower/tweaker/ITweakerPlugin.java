package net.dries007.horsepower.tweaker;


public interface ITweakerPlugin {

  void applyTweaker();

  void register();

  void run();
}
