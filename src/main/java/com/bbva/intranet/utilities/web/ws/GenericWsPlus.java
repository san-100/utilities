package com.bbva.intranet.utilities.web.ws;

import com.bbva.intranet.utilities.web.ws.enums.ResponseCode;
import com.google.api.client.http.HttpStatusCodes;
import com.google.gson.GsonBuilder;
import org.springframework.context.MessageSource;

import javax.ws.rs.core.Response;
import java.util.Locale;
import java.util.logging.Level;

public class GenericWsPlus extends GenericWs {

    protected Response generateResponse(String prefix, ResponseCode responseCode, Object obj, String dateFormat,
                                        int httpStatusCode, MessageSource messageSource, Locale locale) {
        Response response;
        String code = String.format("%s-%s", prefix, responseCode.getCode());
        //String code = String.format("%s",responseCode.getCode());

        String message;
        switch (responseCode) {
            case SUCCESSFUL:
                message = "code.successful";
                break;
            case GENERIC:
            case ENOUGH_DATA:
            case DATABASE:
                message = String.format("code.error.%s", responseCode.getCode());
                break;
            default:
                message = String.format("code.error.%s.%s", prefix, responseCode.getCode());
                break;
        }

        if (dateFormat != null && !dateFormat.isEmpty()) {
            gson = new GsonBuilder().setDateFormat(dateFormat).create();
        }

        if (obj instanceof Exception) {
            Exception e = (Exception) obj;
            LOGGER.log(Level.SEVERE, e.toString(), e);
            message = e.getMessage() != null ? e.getMessage() : e.toString();
            response = generateResponse(code,
                    message,
                    obj, httpStatusCode);
        } else {
            response = generateResponse(code,
                    messageSource.getMessage(message, null, locale),
                    obj, httpStatusCode);
        }

        LOGGER.info(String.format("code: %s message: %s", code, message));

        return response;
    }

    protected Response generateResponse(String prefix, ResponseCode responseCode, Object obj, int httpStatusCode,
                                        MessageSource messageSource, Locale locale) {
        return generateResponse(prefix, responseCode, obj, null, httpStatusCode, messageSource, locale);
    }

//    protected Response generateResponse(String prefix, ResponseCode responseCode, Exception e, int httpStatusCode,
//                                        MessageSource messageSource, Locale locale) {
//        Response response;
//        String code = String.format("%s-%s", prefix, responseCode.getCode());
//        //String code = String.format("%s",responseCode.getCode());
//
//        String message;
//        switch (responseCode) {
//            case GENERIC:
//            case ENOUGH_DATA:
//            case DATABASE:
//                message = String.format("code.error.%s", responseCode.getCode());
//                break;
//            default:
//                message = String.format("code.error.%s.%s", prefix, responseCode.getCode());
//                break;
//        }
//
//        response = generateResponse(code,
//                messageSource.getMessage(message, null, locale),
//                e, httpStatusCode);
//        return response;
//    }

    protected Response validateRequestor(String requestor, String wsKey, MessageSource messageSource, Locale locale) {
        Response response = null;

        if (requestor == null || requestor.isEmpty()) {
            response = generateResponse(wsKey, ResponseCode.ENOUGH_DATA, null,
                    HttpStatusCodes.STATUS_CODE_BAD_REQUEST, messageSource, locale);
        }
        return response;
    }

}
