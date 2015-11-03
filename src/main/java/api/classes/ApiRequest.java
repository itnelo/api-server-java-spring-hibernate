package api.classes;

import lombok.ToString;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.HashMap;

@Component
@ToString
public class ApiRequest {
    @Getter @Setter private Integer version;
    @Getter @Setter private String queryKey;
    @Getter @Setter private String queryData;

    /**
     *
     * Но как быть, если отдельные параметры в теле POST запроса нужно десериализовать отдельно?
     * У них не постоянная структура, это может быть массив с несколькими уровнями вложенности,
     * содержащий данные различных типов, включая другие массивы/списки.
     *
     * На stackoverflow я получил четкий ответ:
     * Spring не имеет готовых решений для парсинга в данном конкретном случае,
     * надо писать свой парсер исходя из того, что ожидается получать, когда, и в каком виде.
     *
     */
    public Map<String, String> getHandlerParams() {
        Map<String, String> handlerParams = new HashMap<>();
        // здесь нужная кастомная десериализация queryData, опускаю ее
        // проект тренировочный и не думаю что я реально его буду использовать.
        handlerParams.put("param1", "value1");
        handlerParams.put("param2", "value2");
        return handlerParams;
    }
}
