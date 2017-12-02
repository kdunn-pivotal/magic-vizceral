package io.pivotal.pde.magicvizceral.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class VizceralDatasetPayload extends VizceralRegion {

    /* e.g.

    {
      "renderer": "global",
      "name": "edge",
      "regions": [],
      "connections": [],
      "serverUpdateTime": 0L
    }

     */

    @JsonProperty("nodes")
    protected List<VizceralRegion> regions;
    protected List<VizceralConnection> connections;

    private Long serverUpdateTime;

    public VizceralDatasetPayload(String rootNodeRender, String rootNodeName) {
        super(rootNodeRender, rootNodeName);
        //serverUpdateTime = super.updated;
    }

    public List<VizceralRegion> getRegions() {
        return regions;
    }

    public void setRegionData(List<VizceralRegion> regions, List<VizceralConnection> connections) {
        for (VizceralConnection c : connections) {
            for (VizceralRegion n : regions) {
                if (c.getSourceNodeName().equals(n.getName())) {
                    // NOOP
                }
                else {
                    addRegion(new VizceralRegion("region", c.getSourceNodeName()));
                }
                if (c.getTargetNodeName().equals(n.getName())) {
                    continue;
                }
                else {
                    addRegion(new VizceralRegion("region", c.getTargetNodeName()));
                }
            }
        }
        this.connections = connections;
    }

    public void setRegions(List<VizceralRegion> regions) {
        this.regions = regions;
    }

    public void addRegion(VizceralRegion region) {
        if (this.regions == null) {
            this.regions = new ArrayList<VizceralRegion>();
        }
        this.regions.add(region);
    }

    public List<VizceralConnection> getConnections() {
        return connections;
    }

    public void setConnections(List<VizceralConnection> connections) {
        this.connections = connections;
    }

    public void addConnection(VizceralConnection connection) {
        if (this.connections == null) {
            this.connections = new ArrayList<VizceralConnection>();
        }
        this.connections.add(connection);
    }

    public Long getServerUpdateTime() {
        return serverUpdateTime;
    }

    public void setServerUpdateTime(Long serverUpdateTime) {
        this.serverUpdateTime = serverUpdateTime;
    }

}
