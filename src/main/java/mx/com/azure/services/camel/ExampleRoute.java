package mx.com.azure.services.camel;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Executors;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * Clase base de MS para WS SOAP
 *
 * @author Adrian Miramar.
 */
@Component
public class ExampleRoute extends RouteBuilder {

    private static final String API_TITLE = "Example REST API";
    private static final String API_VERSION = "1.0.0";
    private static final String PROP_TITLE = "api.title";
    private static final String PROP_VERSION = "api.version";

    /**
     * Metodo en donde se configuran los pasos de la ruta.
     *
     * @throws java.lang.Exception
     */
    @Override
    public void configure() throws Exception {

        restConfiguration("servlet")
                .enableCORS(true)
                .bindingMode(RestBindingMode.auto)
                .apiContextPath("/api-doc")
                .apiProperty(PROP_TITLE, API_TITLE)
                .apiProperty(PROP_VERSION, API_VERSION)
                .apiContextRouteId("doc-api")
                .component("servlet")
                .contextPath("/api");

        rest("api")
                .post("hello")
                .produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .consumes(javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED)
                .bindingMode(RestBindingMode.auto)
                .description("Hello with name")
                .route()
                .log("${body}")
                .log("Solicitando CURP: ${header.sCurp}")
                .to("direct:internalq");
                
        from("direct:internalq")
                .threads()
                .executorService(Executors.newFixedThreadPool(1))
                .choice()
                .when(simple("${header.sCurp} == 'MIGA850615HDFRDD06'"))
                .to("direct:ok")
                .when(simple("${header.sCurp} == 'MIGA850615HDFRDD07'"))
                .to("direct:fail")
                .when(simple("${header.sCurp} == 'MIGA850615HDFRDD08'"))
                .to("direct:timeout")
                .end()
                .endRest();

        from("direct:ok")
                .setBody().body(() -> {
                    SimpleDateFormat sdfh = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Calendar c = Calendar.getInstance();
                    Saldos s = new Saldos();

                    s.setAhorroRetiroRCV(3000.00);
                    s.setAhorroVoluntario(444.24);
                    s.setAhorroVivienda(557.00);
                    s.setSaldoBuenCobro(870.00);
                    s.setSaldoTotalRetiro(1314.24);
                    s.setFechaSaldos(sdf.format(c.getTime()));
                    s.setSaldoAvTopado(1740.00);
                    s.setSaldoRetiroAvDeducible(0.00);
                    s.setSaldoRetiroAvNoDeducible(1740.00);
                    s.setFechaConsulta(sdfh.format(c.getTime()));
                    s.setCurp("MIGA850615HDFRDD06");
                    s.setNss("01137254601");

                    Response r = new Response();
                    r.setResultadoOperacion(200);
                    r.setMotivoRechazo("");
                    r.getSaldos().add(s);
                    return r;
                })
                .removeHeaders("*");

        from("direct:fail")
                .setBody().body(() -> {
                    SimpleDateFormat sdfh = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Calendar c = Calendar.getInstance();
                    Saldos s = new Saldos();

                    s.setAhorroRetiroRCV(0.00);
                    s.setAhorroVoluntario(0.00);
                    s.setAhorroVivienda(0.000);
                    s.setSaldoBuenCobro(0.00);
                    s.setSaldoTotalRetiro(0.00);
                    s.setFechaSaldos("00/00/0000");
                    s.setSaldoAvTopado(0.00);
                    s.setSaldoRetiroAvDeducible(0.00);
                    s.setSaldoRetiroAvNoDeducible(0.00);
                    s.setFechaConsulta(sdfh.format(c.getTime()));
                    s.setCurp("MIGA850615HDFRDD07");
                    s.setNss("01137254220");

                    Response r = new Response();
                    r.setResultadoOperacion(401);
                    r.setMotivoRechazo("815");
                    r.getSaldos().add(s);
                    return r;
                })
                .removeHeaders("*");
        
        from("direct:fail2")
                .removeHeaders("*")
                .setHeader(Exchange.HTTP_RESPONSE_CODE,simple(String.valueOf("308")));
        
        from("direct:timeout")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        Thread.sleep(20000);
                    }
                })
                .setBody().body(() -> {
                    SimpleDateFormat sdfh = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Calendar c = Calendar.getInstance();
                    Saldos s = new Saldos();

                    s.setAhorroRetiroRCV(0.00);
                    s.setAhorroVoluntario(0.00);
                    s.setAhorroVivienda(0.000);
                    s.setSaldoBuenCobro(0.00);
                    s.setSaldoTotalRetiro(0.00);
                    s.setFechaSaldos("00/00/0000");
                    s.setSaldoAvTopado(0.00);
                    s.setSaldoRetiroAvDeducible(0.00);
                    s.setSaldoRetiroAvNoDeducible(0.00);
                    s.setFechaConsulta(sdfh.format(c.getTime()));
                    s.setCurp("MIGA850615HDFRDD08");
                    s.setNss("01137254220");

                    Response r = new Response();
                    r.setResultadoOperacion(401);
                    r.setMotivoRechazo("500");
                    r.getSaldos().add(s);
                    return r;
                })
                .removeHeaders("*");

    }
}
