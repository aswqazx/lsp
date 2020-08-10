package com.aswqazx.camunda.delegate;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 短信服务
 * @author OMNIS
 */
@Component
@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SmsDelegate implements JavaDelegate {

    private final TaskService taskService;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        Map<String, Object> variables = delegateExecution.getVariables();
        log.info("variables is {}", variables);
    }
}
