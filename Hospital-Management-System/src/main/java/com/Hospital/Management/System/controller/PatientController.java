package com.Hospital.Management.System.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.AttributeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hospital.Management.System.entity.Patient;
import com.Hospital.Management.System.repository.PatientRepository;

@CrossOrigin(origins ="http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class PatientController {

	@Autowired
	private PatientRepository  patientRepository;

	public PatientController(PatientRepository patientRepository)
	{
		super ();
		this.patientRepository=patientRepository;
	}
	
	@PostMapping("/patients")
	public Patient createPatient(@RequestBody Patient patient)
	{
		return patientRepository.save(patient);
	}
	
	
	@GetMapping("/patients")
	public List<Patient>getAllPatient(){

		return patientRepository.findAll();
	}

	/*@PostMapping("/insert")
	public Patient createPatient1(@RequestBody Patient patient) {
		return patientRepository.save(patient);
	} */

	@GetMapping("/patients/{id}")
	public ResponseEntity<Patient> getPatientById(@PathVariable long id) throws AttributeNotFoundException {

		Patient patient = patientRepository.findById(id).orElseThrow(() -> new AttributeNotFoundException("PATIENT NOT FOUND IN THE ID: " + id));

		return ResponseEntity.ok(patient);
	}

	@PutMapping("/patients/{id}")
	public ResponseEntity<Patient> updatePatient(@PathVariable long id, @RequestBody Patient patientDetails) throws AttributeNotFoundException{

		Patient patient = patientRepository.findById(id)
				.orElseThrow(() -> new AttributeNotFoundException("ABCD" + id));

		patient.setAge(patientDetails.getAge());
		patient.setName(patientDetails.getName());
		patient.setBlood(patientDetails.getBlood());
		patient.setDose(patientDetails.getDose());
		patient.setFees(patientDetails.getFees());
		patient.setPrescription(patientDetails.getPrescription());
		patient.setUrgency(patientDetails.getUrgency());

		Patient updatedPatient = patientRepository.save(patient);
		return ResponseEntity.ok(updatedPatient);
	} 
	@DeleteMapping("/patients/{id}")
	public ResponseEntity<Map<String,Boolean>> deletePatient(@PathVariable long id) throws AttributeNotFoundException{

		Patient patient = patientRepository.findById(id)
				.orElseThrow(() -> new AttributeNotFoundException("Patient not found with id" + id));

		patientRepository.delete(patient);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response); 
	}
}