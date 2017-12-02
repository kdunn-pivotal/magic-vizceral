package io.pivotal.pde.magicvizceral.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.util.HashMap;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class VizceralNode {
    protected String renderer; // e.g. "global", "region", "focusedChild",

    @JsonProperty("class")
    protected String className; // "normal", "danger", or "warning"

    protected String name; // e.g. "edge"

    protected String displayName; // e.g. "EU-WEST-1"

    protected Long updated; // e.g. 1466838546805,

    protected HashMap<String, Object> metadata;
    /* e.g. {
                "streaming": 1
            }
    */

    protected HashMap<String, Double> metrics;
    /* e.g.   {
                "danger": 51.526,
                "normal": 21282.100000000002
              }
     */

    private Double pageRank;

    public VizceralNode() {
    }

    public VizceralNode(String renderer, String name) {
        this.renderer = renderer;
        this.name = name;
        this.displayName = name;
    }

    public VizceralNode(String nodeName, Double pageRank) {
        this.name = nodeName;
        this.pageRank = pageRank;
    }

    public String getRenderer() {
        return renderer;
    }

    public void setRenderer(String renderer) {
        this.renderer = renderer;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Long getUpdated() {
        return updated;
    }

    public void setUpdated(Long updated) {
        this.updated = updated;
    }

    public HashMap<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(HashMap<String, Object> metadata) {
        this.metadata = metadata;
    }

    public HashMap<String, Double> getMetrics() {
        return metrics;
    }

    public void setMetrics(HashMap<String, Double> metrics) {
        this.metrics = metrics;
    }

    // For the trip back to JSON
    private static final ObjectMapper mapper;
    static {
        mapper = new ObjectMapper();
        //mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }

    public String toJSON() throws JsonProcessingException {
        return mapper.writeValueAsString(this);
    }
}
