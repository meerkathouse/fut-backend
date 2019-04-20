package com.meerkat.house.fut.controller;

import com.meerkat.house.fut.exception.RestException;
import com.meerkat.house.fut.exception.ResultCode;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/examples")
public class ExampleController {

    @RequestMapping(value = "/exception", method = RequestMethod.GET)
    public void exampleException() {
        throw new RestException(ResultCode.BAD_REQUEST);
    }

    @ApiOperation(
            value = "ApiOperation values",
            notes = "ApiOperation notes",
            response = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ApiImplicitParam name", value = "ApiImplicitParam value", required = true, dataType = "string", paramType = "query", defaultValue = "default value"),
            @ApiImplicitParam(name = "ApiImplicitParam name", value = "ApiImplicitParam value", required = false, dataType = "string", paramType = "query", defaultValue = "default value")
    })
    @ApiResponses({
            @ApiResponse(code = 4041000, message = "NOT FOUND"),
            @ApiResponse(code = 5001000, message = "INTERNAL SERVER ERROR")
    })
    @RequestMapping(value = "/swaggers", method = RequestMethod.GET)
    public String swagger() {
        return "swagger";
    }
}
