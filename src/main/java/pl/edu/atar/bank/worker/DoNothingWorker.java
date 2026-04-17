package pl.edu.atar.bank.worker;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.camunda.client.CamundaClient;
import io.camunda.client.annotation.JobWorker;
import io.camunda.client.api.response.ActivatedJob;
import io.camunda.client.api.worker.JobClient;

@Component
public class DoNothingWorker {

    Logger LOGGER = LoggerFactory.getLogger(DoNothingWorker.class);

    @Autowired
    private CamundaClient client;

    @JobWorker(type = "doNothing", autoComplete = false)
    public void handleDoNothing(final JobClient jobClient, final ActivatedJob job) {

        LOGGER.info("Started task/job name/type {}.", job.getType());
        final Map<String, Object> jobVariables = job.getVariablesAsMap();
        for (Map.Entry<String, Object> entry : jobVariables.entrySet()) {
            LOGGER.info("Process variables & task/job variables (e.g. data submitted by user): {} : {}", entry.getKey(), entry.getValue());
        }

        Map<String, Object> variables = job.getVariablesAsMap();

        String information = "I did... well, basically I did nothing. Well done!";
        variables.put("information", information);

        jobClient.newCompleteCommand(job).variables(variables).send().join();
    }
}