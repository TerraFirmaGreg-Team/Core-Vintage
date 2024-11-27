package su.terrafirmagreg.modules.wood.object.generator;

import su.terrafirmagreg.modules.wood.api.generator.ITreeGenerator;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.TemplateManager;

import com.google.common.collect.LinkedListMultimap;

import java.util.Map;
import java.util.Random;

public class GeneratorTreeComposite implements ITreeGenerator {

  private final LinkedListMultimap<Float, ITreeGenerator> gens;
  private float totalWeight;

  public GeneratorTreeComposite() {
    gens = LinkedListMultimap.create();
    totalWeight = 0f;
  }

  public GeneratorTreeComposite add(float chance, ITreeGenerator gen) {
    gens.put(chance, gen);
    totalWeight += chance;
    return this;
  }

  @Override
  public void generateTree(TemplateManager manager, World world, BlockPos pos, WoodType type, Random rand, boolean isWorldGen) {
    if (gens.isEmpty()) {
      return;
    }
    float r = rand.nextFloat() * totalWeight;
    float countWeight = 0f;
    for (Map.Entry<Float, ITreeGenerator> entry : gens.entries()) {
      countWeight += entry.getKey();
      if (countWeight >= r) {
        entry.getValue().generateTree(manager, world, pos, type, rand, isWorldGen);
        return;
      }
    }
  }
}
