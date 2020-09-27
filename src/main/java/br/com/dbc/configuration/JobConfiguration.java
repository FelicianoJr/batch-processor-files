package br.com.dbc.configuration;

import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.integration.async.AsyncItemWriter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import br.com.dbc.model.Account;
import br.com.dbc.processor.Processor;


@Configuration
@EnableBatchProcessing
public class JobConfiguration {
	
	@Autowired
	private Processor processor;
	

	@Bean
	public Job job(JobBuilderFactory jobBuilderFactory) {
		return jobBuilderFactory.get("Asynchronous Processing Account JOB")
				.incrementer(new RunIdIncrementer())
				.flow(asyncStep(null))
				.end()
				.build();
	}
	
	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(500);
		executor.setMaxPoolSize(1000);
		executor.setQueueCapacity(1000);
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		return executor;
	}

	@Bean
	public Step asyncStep(StepBuilderFactory stepBuilderFactory) {
		return stepBuilderFactory.get(" .get(\"Read Account-> Process Account -> Write Account Response \")")
				.<Account, Future<Account>>chunk(100)
				.reader(asyncReader(null))
				.processor(asyncProcessor())
				.writer(asyncWriter())
				.taskExecutor(taskExecutor())
				.build();
	}

	@Bean
	@StepScope
	public FlatFileItemReader<Account> asyncReader(@Value("#{jobParameters['file']}") String file) {
		return new FlatFileItemReaderBuilder<Account>()
				.name("Item-Writer-CSV")
				.resource(new FileSystemResource(file))
				.strict(false)
				.linesToSkip(1)
				.delimited()
				.delimiter(";")
				.names("agencia", "conta", "saldo", "status")
				.targetType(Account.class)
				.build();
	}

	@Bean
	public ItemWriter<Account> itemWriter() {
		return new FlatFileItemWriterBuilder<Account>()
			.name("Item-Writer-CSV")
			.append(false)
			.resource(new FileSystemResource("account-update.csv"))
			.delimited()
			.delimiter(";")
			.names("agencia", "conta", "saldo", "status","update")
			.build();
	}

	@Bean
	public AsyncItemWriter<Account> asyncWriter() {
		AsyncItemWriter<Account> asyncItemWriter = new AsyncItemWriter<>();
		asyncItemWriter.setDelegate(itemWriter());
		return asyncItemWriter;
	}

	@Bean
	public AsyncItemProcessor<Account, Account> asyncProcessor() {
		AsyncItemProcessor<Account, Account> asyncItemProcessor = new AsyncItemProcessor<>();
		asyncItemProcessor.setDelegate(processor);
		asyncItemProcessor.setTaskExecutor(taskExecutor());
		return asyncItemProcessor;
	}

}
