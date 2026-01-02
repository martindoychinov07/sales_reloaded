package com.reloaded.sales.service;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.reloaded.sales.model.OrderForm;
import com.reloaded.sales.dto.PrintVariant;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.expression.ThymeleafEvaluationContext;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class PdfService {

  private final SpringTemplateEngine templateEngine;
  private final ApplicationContext applicationContext;
  private final ConversionService conversionService;

  public PdfService(
    SpringTemplateEngine templateEngine,
    ApplicationContext applicationContext,
    ConversionService conversionService
  ) {
    this.templateEngine = templateEngine;
    this.applicationContext = applicationContext;
    this.conversionService = conversionService;
  }

  public byte[] generateInvoicePdf(OrderForm orderForm, PrintVariant printVariant) throws IOException {
    Context context = new Context(LocaleContextHolder.getLocale());
    context.setVariable("orderForm", orderForm);
    context.setVariable(
      ThymeleafEvaluationContext.THYMELEAF_EVALUATION_CONTEXT_CONTEXT_VARIABLE_NAME,
      new ThymeleafEvaluationContext(applicationContext, conversionService)
    );
    context.setVariable("orderForm", orderForm);
    context.setVariable("sourceCy", orderForm.getOrderCcp().substring(0, 3));
    context.setVariable("targetCy", orderForm.getOrderCcp().substring(4));

    List<PrintVariant> variants = List.of(printVariant);
    context.setVariable("variants", variants);

    String html = templateEngine.process(orderForm.getOrderType().getTypePrint(), context);

    ByteArrayOutputStream out = new ByteArrayOutputStream();

    String baseUri = PdfService.class.getResource("/static/").toString();
    PdfRendererBuilder builder = new PdfRendererBuilder();
    builder.withHtmlContent(html, baseUri);
    builder.toStream(out);
    builder.run();

    return out.toByteArray();
  }
}