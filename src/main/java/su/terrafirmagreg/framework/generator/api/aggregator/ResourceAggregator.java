package su.terrafirmagreg.framework.generator.api.aggregator;

import java.io.IOException;
import java.io.OutputStream;


public interface ResourceAggregator<S, T> {

  S initialData();

  S combine(S data, T newData);

  void write(OutputStream stream, S data) throws IOException;
}
