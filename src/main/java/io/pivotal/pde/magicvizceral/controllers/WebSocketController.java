package io.pivotal.pde.magicvizceral.controllers;

import io.pivotal.pde.magicvizceral.model.VizceralConnection;
import io.pivotal.pde.magicvizceral.model.VizceralDatasetPayload;
import io.pivotal.pde.magicvizceral.model.VizceralNode;
import io.pivotal.pde.magicvizceral.repositories.IVizPayloadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

public class WebSocketController {

    private static final Logger LOG = LoggerFactory.getLogger(WebSocketController.class);

    @Autowired
    private IVizPayloadRepository vizPayloadRepository;

    @Autowired
    private SimpMessagingTemplate webSocketMessageTemplate;

    //@Autowired
    //private GemFireServerFunctions gemfireServerFunctions;

    private volatile VizceralDatasetPayload currentPayload;

    private static int nSent = 0;

    @Async
    protected void sendNodesThroughWebSocket() throws Exception {
        currentPayload = vizPayloadRepository.getLatestPayload();

        for (VizceralNode n : currentPayload.getRegions()) {
            ++nSent;
            //JSONObject jsonObj = new JSONObject(JSONFormatter.toJSON((PdxInstance) n));
            //LOG.debug("Endpoint /ws: " + jsonObj.toString());
            webSocketMessageTemplate.convertAndSend("/topic/viz-nodes", n.toJSON());
        }
    }

    @Async
    protected void sendEdgesThroughWebSocket() throws Exception {
        currentPayload = vizPayloadRepository.getLatestPayload();

        for (VizceralConnection c : currentPayload.getConnections()) {
            ++nSent;
            //JSONObject jsonObj = new JSONObject(JSONFormatter.toJSON((PdxInstance) c));
            //LOG.debug("Endpoint /ws: " + jsonObj.toString());
            webSocketMessageTemplate.convertAndSend("/topic/viz-edges", c.toJSON());
        }
    }

    /*
     * The following function implements the asynchronous AppLog data feed
     * for the client-side DataTable.js object. The UI client invokes this
     * end point after first obtaining the expected schema (below) and
     * instantiating the DataTable. For each websocket payload, the .add()
     * method is called on the client side, appending one record at a time to
     * provide a better "reactive" user experience.
     */
    @RequestMapping(value = "/data.json", method = GET)
    public VizceralDatasetPayload publishGraphToWebSocketFromRequest() throws Exception {
        // kick of a server-side async publishing method() to send "merge-able" updates via websocket
        // return cached pagerank top N nodes immediately.

        VizceralDatasetPayload simplePayload = vizPayloadRepository.getLatestPageRankPayload();

        LOG.info(simplePayload.toJSON());

        sendNodesThroughWebSocket();
        sendEdgesThroughWebSocket();

        /*
        if (nSent == 0) {
            LOG.debug("Endpoint /ws: SENDING NULL MESSAGE");
            // Prevent DataTables from complaining
            webSocketMessageTemplate.convertAndSend("/topic/applogs", getAppLogSchema());
        }
        */

        return simplePayload;
    }
}
