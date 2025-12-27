package com.reloaded.sales.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.reloaded.sales.model.Setting;
import com.reloaded.sales.util.JsonFlattener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;
import java.util.function.Function;

import static com.reloaded.sales.util.ParseText.parseStringToMap;

@Slf4j
@Service
public class IntegrationService {

  private final WebClient webClient;
  private final ObjectMapper objectMapper;
  private Environment env;
  private SettingService settingService;

  public IntegrationService(WebClient webClient, ObjectMapper objectMapper, Environment env, SettingService settingService) {
    this.webClient = webClient;
    this.objectMapper = objectMapper;
    this.env = env;
    this.settingService = settingService;
  }

  private WebClient.ResponseSpec retrieve(String uri, String params) {
    return webClient.get().uri(uri, params.split(",")).retrieve();
  }

  public Mono<String> requestString(String api, String params, String result) {
    return retrieve(api, params)
      .bodyToMono(String.class);
  }

  public Mono<Map<String, Object>> requestMap(String api, String params, String result) {
    return retrieve(api, params)
      .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() { });
  }

  public Mono<JsonNode> requestJson(String api, String params, String result) {
    return retrieve(api, params)
      .bodyToMono(JsonNode.class);
  }

  public Mono<Map<String, Object>> requestFlattenMap(String api, String params, String result) {
    Map<String, String> mapping = parseStringToMap(result);
    Function<String, String> renameFunc = key -> mapping.getOrDefault(key, key);

    return retrieve(api, params)
      .bodyToMono(JsonNode.class)
      .map(jsonNode -> {
        return JsonFlattener.flatten(jsonNode, mapping.keySet(), renameFunc, false);
      });
  }

  public Mono<JsonNode> requestFlattenJson(String api, String params, String result) {
    Map<String, String> mapping = parseStringToMap(result);
    Function<String, String> renameFunc = key -> mapping.getOrDefault(key, key);

    return retrieve(api, params)
      .bodyToMono(JsonNode.class)
      .map(jsonNode -> {
        Map<String, Object> flatMap = JsonFlattener.flatten(jsonNode, mapping.keySet(), renameFunc, false);
        return objectMapper.valueToTree(flatMap);
      });
  }

  public JsonNode requestJsonTemplate(String api, String params, String template) {
    return retrieve(api, params)
      .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() { })
      .map(context -> {
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile(new StringReader(template), "template");

        StringWriter writer = new StringWriter();

        try {
          mustache.execute(writer, context).flush();
          String content = writer.toString();
          JsonNode res = objectMapper.readTree(content);
          return res;
        }
        catch (IOException e) {
          log.info("JSON:" + e.toString());
          throw new RuntimeException(e);
        }

      }).block();
  }

  public JsonNode requestJson(String api, String params) {
    Setting setting = settingService.getSettingByKey("service." + api);
    return requestJsonTemplate(setting.getSettingNote(), params, setting.getSettingValue());
  }

}
