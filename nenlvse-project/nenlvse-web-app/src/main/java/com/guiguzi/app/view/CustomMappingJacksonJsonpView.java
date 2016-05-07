package com.guiguzi.app.view;

/**
 * Created by kqy on 2015/6/6.
 */

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * jsonp
 */
public class CustomMappingJacksonJsonpView extends MappingJacksonJsonView {
    /**
     * Default content type. Overridable as bean property.
     */
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    private ObjectMapper objectMapper = new ObjectMapper();
    private MediaType mediaType = new MediaType("application", "json", DEFAULT_CHARSET);

    public static final String DEFAULT_CONTENT_TYPE = "application/json;charset=UTF-8";
    public static final String JSONP_CONTENT_TYPE = "application/javascript;charset=UTF-8";

   /* @Override
    public String getContentType() {
        return DEFAULT_CONTENT_TYPE;
    }*/

    @Override
    protected Object filterModel(Map<String, Object> model) {
        Map<?, ?> result = (Map<?, ?>) super.filterModel(model);
        return result.get("baseResponse");
        //return result.values().iterator().next();

        /*if (result.size() == 1) {
            return result.values().iterator().next();
        } else {
            return result.values().iterator().next();
        }*/
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model,
                                           HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {

        Object value = filterModel(model);

        this.writeInternal(value, response);
        /*response.setContentType(DEFAULT_CONTENT_TYPE);
        response.getWriter().write((JSON.toJSONString(value)));
        response.getWriter().flush();*/
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String, String[]> params = request.getParameterMap();
        if (params.containsKey("callback")) {

            response.getOutputStream().write(new String(params.get("callback")[0] + "(").getBytes());
            super.render(model, request, response);
            response.getOutputStream().write(new String(");").getBytes());
            response.getOutputStream().flush();
            response.setContentType(JSONP_CONTENT_TYPE);

        } else {
            super.render(model, request, response);

        }
    }


    protected void writeInternal(Object object,HttpServletResponse response) throws IOException, HttpMessageNotWritableException {
        JsonEncoding encoding = this.getJsonEncoding(mediaType);
        JsonGenerator jsonGenerator = this.objectMapper.getJsonFactory().createJsonGenerator(response.getOutputStream(), encoding);
        if(this.objectMapper.getSerializationConfig().isEnabled(SerializationConfig.Feature.INDENT_OUTPUT)) {
            jsonGenerator.useDefaultPrettyPrinter();
        }

        try {
            this.objectMapper.writeValue(jsonGenerator, object);
        } catch (JsonProcessingException var6) {
            throw new HttpMessageNotWritableException("Could not write JSON: " + var6.getMessage(), var6);
        }
    }


    protected JsonEncoding getJsonEncoding(MediaType contentType) {
        if(contentType != null && contentType.getCharSet() != null) {
            Charset charset = contentType.getCharSet();
            JsonEncoding[] var3 = JsonEncoding.values();
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                JsonEncoding encoding = var3[var5];
                if(charset.name().equals(encoding.getJavaName())) {
                    return encoding;
                }
            }
        }

        return JsonEncoding.UTF8;
    }

}