package io.pivotal.pde.magicvizceral.repositories;

import io.pivotal.pde.magicvizceral.model.VizceralConnection;
import io.pivotal.pde.magicvizceral.model.VizceralDatasetPayload;
import io.pivotal.pde.magicvizceral.model.VizceralNode;
import io.pivotal.pde.magicvizceral.model.VizceralRegion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class IVizPayloadRepository {

    private static final Logger LOG = LoggerFactory.getLogger(IVizPayloadRepository.class);

    VizceralDatasetPayload currentPayload;

    private static Double aggregateVolume;

    private JdbcTemplate jdbcTemplate;

    private String featureName = "hash";

    public IVizPayloadRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.aggregateVolume = 0.;
        //this.currentPayload = new VizceralDatasetPayload();
    }

    public
    List<VizceralNode> getTopNRankedNodes(Integer topN) {


        String query = "SELECT " + featureName + ", pagerank FROM public.btc_pagerank_out ORDER BY 2 DESC ";

        if (topN > 0) {
            query = query + " LIMIT " + topN.toString();
        }

        List<VizceralNode> results = new ArrayList<VizceralNode>();

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);

        //internet

        for (Map<String, Object> row : rows) {
            //Long uniqueId = Long.parseLong(((String)row.get("dest_address")).replace(".", ""));
            VizceralNode n = new VizceralNode(row.get(featureName).toString(),
                    Double.parseDouble(row.get("pagerank").toString()));

            HashMap<String, Double> metricsData = new HashMap<String, Double>();
            //metricsData.put("normal", Double.parseDouble( row.get("count").toString() ));
            n.setMetrics(metricsData);
            n.setRenderer("focusedChild");
            n.setClassName("normal");

            results.add(n);
        }

        return results;
    }


    public
    List<VizceralConnection> getTopNRankedConnection(Integer topN) {
        String query = "" +
                "WITH edges AS (SELECT \n" +
                "       hash AS source,\n" +
                "       addr AS target,\n" +
                "       value\n" +
                "    FROM blockchain_item\n" +
                "    WHERE direction = 'in'\n" +
                "" +
                "    UNION ALL\n" +
                "    SELECT\n" +
                "       addr AS source,\n" +
                "       hash AS target,\n" +
                "       value\n" +
                "    FROM blockchain_item\n" +
                "    WHERE direction != 'in'\n" +
                ")\n" +
                "\n" +
                "SELECT \n" +
                "source, target, count(*) \n" +
                "FROM edges \n" +
                "GROUP BY 1, 2 \n" +
                "ORDER BY 3 DESC \n";

        if (topN > 0) {
            query = query + " LIMIT " + topN.toString();
        }

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);

        List<VizceralConnection> results = new ArrayList<VizceralConnection>();

        LOG.info("Got results for " + query);

        for (Map<String, Object> row : rows) {
            String t = row.get("target").toString(); //replace(".", ""));
            String s = row.get("source").toString(); //replace(".", ""));
            Long w = (Long)row.get("count");
            VizceralConnection r = new VizceralConnection(t, s);
            r.setUpdated(currentPayload.getServerUpdateTime());
            r.setMetadata(new HashMap<String, Object>());

            HashMap<String, Double> metricsData = new HashMap<String, Double>();
            Double thisVolume = Double.parseDouble( row.get("count").toString() );
            metricsData.put("normal", thisVolume);
            this.aggregateVolume += thisVolume;
            r.setMetrics(metricsData);
            r.setClassName("normal");

            results.add(r);
        }

        LOG.info("Size " + results.size());

        return results;
    }


    public VizceralDatasetPayload getLatestPayload() {

        // TODO "getAll()" result(s)
        return new VizceralDatasetPayload("global", "edge");
    }

    public VizceralDatasetPayload getLatestPageRankPayload() throws Exception {

        // TODO madlib pagerank
        currentPayload = new VizceralDatasetPayload("global", "edge");

        currentPayload.setServerUpdateTime(System.currentTimeMillis());

        VizceralRegion internet = new VizceralRegion("region", "INTERNET");
        internet.setDisplayName("INTERNET");
        internet.setClassName("normal");

        currentPayload.addRegion(internet);

        VizceralRegion btcBlockchain = new VizceralRegion("region", "BTC");
        btcBlockchain.setData(getTopNRankedNodes(25), getTopNRankedConnection(50));
        btcBlockchain.setClassName("normal");

        //currentPayload.setRegions();
        currentPayload.addRegion(btcBlockchain);

        VizceralConnection firehose = new VizceralConnection("INTERNET",
                "BTC");

        HashMap<String, Double> metricsData = new HashMap<String, Double>();
        metricsData.put("normal", aggregateVolume);
        firehose.setMetrics(metricsData);
        currentPayload.addConnection(firehose);

        //LOG.info(currentPayload.toJSON());

        return currentPayload;
    }
}
