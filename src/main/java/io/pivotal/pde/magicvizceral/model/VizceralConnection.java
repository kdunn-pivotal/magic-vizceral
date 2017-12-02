package io.pivotal.pde.magicvizceral.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.util.HashMap;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class VizceralConnection extends VizceralNode {

    /* e.g.

       {
          "source": "majordomo",
          "target": "proletarianised",
          "metadata": {
            "streaming": 1
          },
          "metrics": {
            "danger": 0.11200000000000002,
            "normal": 38.684
          }
          "notices": [
            {
              "title": "Bob Loblaws law blog logging log blobs",
              "link": "http://link/to/relevant/thing",
              "severity": 1
            }
          ]
        }

     */

    @JsonProperty("source")
    protected String sourceNodeName;

    @JsonProperty("target")
    protected String targetNodeName;

    protected List<HashMap<String, Object>> notices;

    public VizceralConnection(String sourceNodeName, String targetNodeName) {
        this.sourceNodeName = sourceNodeName;
        this.targetNodeName = targetNodeName;
    }

    public String getSourceNodeName() {
        return sourceNodeName;
    }

    public void setSourceNodeName(String sourceNodeName) {
        this.sourceNodeName = sourceNodeName;
    }

    public String getTargetNodeName() {
        return targetNodeName;
    }

    public void setTargetNodeName(String targetNodeName) {
        this.targetNodeName = targetNodeName;
    }

    public List<HashMap<String, Object>> getNotices() {
        return notices;
    }

    public void setNotices(List<HashMap<String, Object>> notices) {
        this.notices = notices;
    }
}
