package com.appdynamics.monitors.VMWare;

import com.appdynamics.extensions.util.MetricUtils;
import com.appdynamics.extensions.util.MetricWriteHelper;
import com.singularity.ee.agent.systemagent.api.MetricWriter;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Satish Muddam
 */
public abstract class BaseMetricCollector {

    private MetricWriteHelper metricWriter;
    private String metricPrefix;

    public BaseMetricCollector(MetricWriteHelper metricWriter, String metricPrefix) {
        this.metricWriter = metricWriter;
        this.metricPrefix = metricPrefix;
    }

    protected String applyReplacers(String name, Map<Pattern, String> replacers) {

        if (name == null || name.length() == 0 || replacers == null) {
            return name;
        }

        for (Map.Entry<Pattern, String> replacerEntry : replacers.entrySet()) {

            Pattern pattern = replacerEntry.getKey();

            Matcher matcher = pattern.matcher(name);
            name = matcher.replaceAll(replacerEntry.getValue());
        }

        return name;
    }

    /**
     * Returns the metric to the AppDynamics Controller.
     *
     * @param metricName  Name of the Metric
     * @param metricValue Value of the Metric
     */
    protected void printMetric(String metricName, Object metricValue) {

        metricWriter.printMetric(metricPrefix + "|" + metricName, MetricUtils.toWholeNumberString(metricValue), MetricWriter.METRIC_AGGREGATION_TYPE_OBSERVATION,
                MetricWriter.METRIC_TIME_ROLLUP_TYPE_CURRENT, MetricWriter.METRIC_CLUSTER_ROLLUP_TYPE_INDIVIDUAL);
    }
}
