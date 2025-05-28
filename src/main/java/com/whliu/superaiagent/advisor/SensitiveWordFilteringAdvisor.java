
package com.whliu.superaiagent.advisor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import org.springframework.ai.chat.client.advisor.api.AdvisedRequest;
import org.springframework.ai.chat.client.advisor.api.AdvisedResponse;
import org.springframework.ai.chat.client.advisor.api.CallAroundAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAroundAdvisorChain;
import org.springframework.ai.chat.client.advisor.api.StreamAroundAdvisor;
import org.springframework.ai.chat.client.advisor.api.StreamAroundAdvisorChain;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.MessageAggregator;
import org.springframework.ai.model.ModelOptionsUtils;

/**
 * 违禁词校验Advisor
 *
 * @author whliu
 */
@Slf4j
public class SensitiveWordFilteringAdvisor implements CallAroundAdvisor, StreamAroundAdvisor {

	private List<String> sensitiveWordsList;

	public SensitiveWordFilteringAdvisor() {
		this.sensitiveWordsList = List.of("sb", "垃圾", "违禁词");
	}

	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public int getOrder() {
		return 1;
	}

	private AdvisedRequest before(AdvisedRequest request) {
		String requestText = request.userText();
		boolean hasSentitiveWords = sensitiveWordsList.stream().anyMatch(word -> word.equals(requestText));
		if (hasSentitiveWords) {
//			throw new IllegalArgumentException("用户输入包含违禁词");
			return AdvisedRequest.from(request)
					.systemText("用户输入包含违禁词，请直接回答：你的输入包含违禁词")
					.build();
		}
		return request;
	}

	private void observeAfter(AdvisedResponse advisedResponse) {
//		log.info("response: {}", advisedResponse.response().getResult().getOutput().getText());
	}

	@Override
	public String toString() {
		return SensitiveWordFilteringAdvisor.class.getSimpleName();
	}

	@Override
	public AdvisedResponse aroundCall(AdvisedRequest advisedRequest, CallAroundAdvisorChain chain) {
		advisedRequest = before(advisedRequest);
		AdvisedResponse advisedResponse = chain.nextAroundCall(advisedRequest);
		observeAfter(advisedResponse);
		return advisedResponse;
	}

	@Override
	public Flux<AdvisedResponse> aroundStream(AdvisedRequest advisedRequest, StreamAroundAdvisorChain chain) {
		advisedRequest = before(advisedRequest);
		Flux<AdvisedResponse> advisedResponses = chain.nextAroundStream(advisedRequest);
		return new MessageAggregator().aggregateAdvisedResponse(advisedResponses, this::observeAfter);
	}

}
