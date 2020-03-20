package com.example.demo;

import javax.xml.bind.JAXBElement;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.example.demo.schemas.Hello.SayHello;
import com.example.demo.schemas.Hello.SayHelloResponse;
import com.example.demo.soapclient.SOAPConnector;

@ComponentScan(basePackages = "com.example.demo")
@SpringBootApplication
public class SoapConsumeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoapConsumeApplication.class, args);
    }

    @Bean
    CommandLineRunner lookup(SOAPConnector soapConnector) {
        return args -> {
            System.out.println("Inside Runner Method...URL --> "+soapConnector.getDefaultUri());
            SayHello request = new SayHello();
            request.setArg0("Ninja");
            JAXBElement<SayHelloResponse> response = (JAXBElement<SayHelloResponse>) soapConnector.callWebService(soapConnector.getDefaultUri(), request);
            System.out.println("================================\n" + response.getValue().getReturn());
            System.exit(0);
        };
    }
}

