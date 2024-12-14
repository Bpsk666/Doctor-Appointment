package com.DoctorAppointment;

import org.springframework.boot.SpringApplication;

public class TestDoctorAppointmentApplication {

	public static void main(String[] args) {
		SpringApplication.from(DoctorAppointmentApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
