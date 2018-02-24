package br.unb.cic.pistar.model;

import java.util.Map;

public class PistarNode extends BasicEntity {

    private Map<String, String> customProperties;

    public Map<String, String> getCustomProperties() {
        return customProperties;
    }

    public void setCustomProperties(Map<String, String> customProperties) {
        this.customProperties = customProperties;
    }
}
