package su.terrafirmagreg.modules.wood.init;

import su.terrafirmagreg.api.library.json.creator.PatternLoader;

import net.minecraft.launchwrapper.Launch;

import java.io.File;
import java.io.IOException;

import static su.terrafirmagreg.Tags.MOD_ID;

public class DataGeneratorWood {

  public static File rootFolder = Launch.minecraftHome == null ? new File(".") : Launch.minecraftHome;


  public static void genForgeBlockStates(String object) throws IOException {

    PatternLoader.createBlockState("wood/" + object, MOD_ID, object.replace(" ", "_").toLowerCase());

    File foldersWood = new File(rootFolder + "/resources/" + MOD_ID + "/blockstates/wood/" + object.replace(" ", "_").toLowerCase() + "/");
    foldersWood.mkdirs();
  }
}
