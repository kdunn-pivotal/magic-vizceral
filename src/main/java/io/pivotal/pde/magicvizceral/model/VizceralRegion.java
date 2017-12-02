package io.pivotal.pde.magicvizceral.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class VizceralRegion extends VizceralNode {

    /* e.g.

    {
            "renderer": "region",
            "name": "us-east-1",
            "maxVolume": 50000,
            "class": "normal",
            "updated": 1466838546805,
            "displayName": "EU-WEST-1",
            "regions": [
                {
                    "name": "INTERNET",
                        "renderer": "focusedChild",
                        "class": "normal"
                },
                {
                    "name": "proxy-prod",
                        "renderer": "focusedChild",
                        "class": "normal"
                }
              ],
                "connections": [
                {
                    "source": "INTERNET",
                        "target": "proxy-prod",
                        "metrics": {
                    "danger": 116.524,
                            "normal": 15598.906
                },
              "class": "normal",
              "maxVolume": 67936.982,
              "props": {
                "maxSemaphores": [
                  {
                    "targetRegion": "eu-west-1",
                    "region": "us-east-1",
                    "value": "20"
                  },
                  {
                    "targetRegion": "us-east-1",
                    "region": "us-west-2",
                    "value": "160"
                  },
                  {
                    "targetRegion": "us-east-1",
                    "region": "eu-west-1",
                    "value": "20"
                  },
                  {
                    "targetRegion": "us-west-2",
                    "region": "eu-west-1",
                    "value": "20"
                  },
                  {
                    "targetRegion": "us-west-2",
                    "region": "us-east-1",
                    "value": "200"
                  }
                ]
              }
        }
      ]
    }

     */

    protected Long maxVolume;

    protected List<VizceralNode> nodes;
    protected List<VizceralConnection> connections;

    protected HashMap<String, List<HashMap<String, String>>> props;

    public VizceralRegion(String renderer, String name) {
        super(renderer, name);
    }

    public void setData(List<VizceralNode> nodes, List<VizceralConnection> connections) {
        for (VizceralConnection c : connections) {
            for (VizceralNode n : nodes) {
                if (c.getSourceNodeName().equals(n.getName())) {
                    // NOOP
                }
                else {
                    addNode(new VizceralNode("focusedChild", c.getSourceNodeName()));
                }
                if (c.getTargetNodeName().equals(n.getName())) {
                    continue;
                }
                else {
                    addNode(new VizceralNode("focusedChild", c.getTargetNodeName()));
                }
            }
        }
        this.connections = connections;
    }

    public void addNode(VizceralNode node) {
        if (this.nodes == null) {
            this.nodes = new ArrayList<VizceralNode>();
        }
        this.nodes.add(node);
    }

    public Long getMaxVolume() {
        return maxVolume;
    }

    public void setMaxVolume(Long maxVolume) {
        this.maxVolume = maxVolume;
    }

    public List<VizceralNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<VizceralNode> nodes) {
        this.nodes = nodes;
    }

    public List<VizceralConnection> getConnections() {
        return connections;
    }

    public void setConnections(List<VizceralConnection> connections) {
        this.connections = connections;
    }

    public HashMap<String, List<HashMap<String, String>>> getProps() {
        return props;
    }

    public void setProps(HashMap<String, List<HashMap<String, String>>> props) {
        this.props = props;
    }
}
