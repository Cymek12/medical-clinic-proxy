package com.example.medical_clinic_proxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MedicalClinicProxyApplication {
	public static void main(String[] args) {
		SpringApplication.run(MedicalClinicProxyApplication.class, args);
	}

}

/*
Ja jako pacjent chciałbym miec możliwość:
Zobaczenia wszystkich swoich wizyt
Zapisania się na wizytę
Sprawdzenia wszystkich dostępnych wizyt dla danego doktora
Sprawdzenia wszystkich dostepnych terminow wizyt na dany dzien z danej specjalizacji doktora
 */