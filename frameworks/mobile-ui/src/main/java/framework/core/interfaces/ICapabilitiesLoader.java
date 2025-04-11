package framework.core.interfaces;

import java.util.List;

public interface ICapabilitiesLoader {
    List<String> loadAllCapabilityFiles(String platformVariantPath);
}
