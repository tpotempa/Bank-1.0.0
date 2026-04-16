package pl.edu.atar.bank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.camunda.client.CamundaClient;
import io.camunda.client.annotation.Deployment;

@SpringBootApplication
@Deployment(resources = "classpath*:/model/*.*")
public class BankApplication implements CommandLineRunner {

	private static final Logger LOG = LoggerFactory.getLogger(BankApplication.class);

	@Autowired
	private CamundaClient camundaClient;

	@Override
	public void run(final String... args) {
		var bpmnProcessId = "bank-loan";

		var result = camundaClient.newCreateInstanceCommand()
				.bpmnProcessId(bpmnProcessId)
				.latestVersion()
				.send()
				.join();

		LOG.info("Started a process instance: {}", result.getProcessInstanceKey());
	}

	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
	}
}