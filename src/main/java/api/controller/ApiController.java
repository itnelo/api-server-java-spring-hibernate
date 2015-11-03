package api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import api.classes.*;
import api.services.Dispatcher;

@RestController
@RequestMapping(value = "/**", method = RequestMethod.POST)
public class ApiController
{
    @Autowired
    @Qualifier("apiDispatcher")
    private Dispatcher dispatcher;

    @SuppressWarnings("unchecked")
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ApiResponse receiveRequest(@RequestBody ApiRequest request) throws Exception {
        return (ApiResponse) dispatcher.process(request);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public void error() {}
}