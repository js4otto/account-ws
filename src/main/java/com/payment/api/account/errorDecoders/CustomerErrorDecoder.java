package com.payment.api.account.errorDecoders;

import java.io.IOException;
import java.io.Reader;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.CharStreams;
import com.payment.api.account.exceptions.BusinessException;
import com.payment.api.account.v1.dto.ErrorDto;
import com.payment.api.account.v1.dto.ResponseDto;

import feign.Response;
import feign.codec.ErrorDecoder;

@Component
public class CustomerErrorDecoder implements ErrorDecoder{

	@Override
	public Exception decode(String methodKey, Response response) {
        ResponseDto<ErrorDto> responseDto = null;
        Reader reader = null;
        try {
        	reader = response.body().asReader();
            String result = CharStreams.toString(reader);

            ObjectMapper mapper = new ObjectMapper();  
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);


            TypeReference<ResponseDto<ErrorDto>> ref = new TypeReference<ResponseDto<ErrorDto>>() { };
            responseDto = mapper.readValue(result, ref);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {

                if (reader != null)
                    reader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
   		switch (response.status()) {
		case 400:
//			if (methodKey.contains("findByCustomerId")) {
//				return new BusinessException(responseDto.getData());
//			}
//			break;
			return new BusinessException(responseDto.getData());
		default:
			break;
		}
		return null;
	}

}
