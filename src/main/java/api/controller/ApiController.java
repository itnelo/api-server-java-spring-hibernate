package api.controller;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import javax.servlet.http.HttpServletRequest;
import api.classes.*;
import api.util.*;
import api.handler.ApiHandler;
import api.model.Client;

/**
 * Портирование PHP+Yii backend API в Java окружение.
 * `gm-api-plugin-php` ==> `gm-api-plugin-java`
 * Контроллер обрабатывает POST-запросы от различных клиентов (сайты, мобильные приложения)
 *
 * Плагины представляют дополнительный функционал для ПО клиента
 * Каждый клиент имеет свою БД, в которой хранятся данные плагина
 * Доступ к API осуществляется по ключу, который выдается клиенту при активации плагина
 * Для успешной аутентификации необходим актуальный ключ клиента (идентификатор) и ключ доступа к плагину
 */
@RestController
@RequestMapping(value = "/", method = RequestMethod.POST)
public class ApiController {

    @Autowired private ApiConfig config;
    @Autowired private ApiQueryParser parser;
    @Autowired private ApiClientManager clientManager;
    @Autowired private ApiHandlerFactory handlerFactory;
    @Autowired private ApiLogger logger;
    @Autowired private Translator tr;

    private boolean isCleaned;

    @RequestMapping(produces = "application/json;charset=UTF-8")
    @ResponseBody
    public byte[] process(HttpServletRequest request) throws Exception {
        ApiResponse response = new ApiResponse();

        try {
            isCleaned = false;
            ApiQuery query = parser.parse(request);
            Client client = clientManager.authorize(query);
            ApiHandler handler = handlerFactory.getHandler(query);
            response.setOutput(handler.handle(query, client));
        } catch (Exception e) {
            logger.log(ExceptionUtils.getRootCauseStackTrace(e));
            response.setOutput(tr.tr(Translator.CATEGORY.ERRORS, "Internal error"));
        } finally {
            free();
        }

        return response.getOutput();
    }

    private void free() {
        if (isCleaned) return;
        try {
            // Освобождение использованных данных...
            // Логирование запроса и деталей его обработки...
        } catch (Exception e) {
            logger.log(ExceptionUtils.getFullStackTrace(e));
        }
        isCleaned = true;
    }

    // В целях безопасности скрываем ошибки от клиента (используем только логирование)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public void error() {}

}