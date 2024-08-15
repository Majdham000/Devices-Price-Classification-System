package com.example.crud.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.crud.repo.DeviceRepo;
import com.example.crud.model.Device;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;



@RestController
@RequestMapping("/api")
public class DeviceController {

    @Autowired
    private DeviceRepo deviceRepo;

    @GetMapping("/devices")
    public ResponseEntity<List<Device>> getAllDevices() {
        try {
            List<Device> deviceList = new ArrayList<>();
            deviceRepo.findAll().forEach(deviceList::add);

            if (deviceList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(deviceList, HttpStatus.OK);
        } 
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/devices/{id}")
    public ResponseEntity<Device> getDeviceById(@PathVariable Long id) {
        Optional<Device> deviceData = deviceRepo.findById(id);

        if (deviceData.isPresent()) {
            return new ResponseEntity<>(deviceData.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/devices")
    public ResponseEntity<Device> addDevice(@RequestBody Device device) {
        Device deviceObj = deviceRepo.save(device);

        return new ResponseEntity<>(deviceObj, HttpStatus.OK);
    }



    @PostMapping("/predict/{id}")
    public String predict(@PathVariable Long id) {
        Optional<Device> deviceData = deviceRepo.findById(id);
        Device device = deviceData.get();
        double[] inputData = mapDeviceToInputData(device);

        String result = callPythonScript(inputData);
        return result;
    }

    private String callPythonScript(double[] inputData) {
        StringBuilder inputStr = new StringBuilder();
        for (double num : inputData) {
            inputStr.append(num).append(" ");
        }

        try {
            ProcessBuilder pb = new ProcessBuilder("python3", "Task for Maid.cc/predict.py", inputStr.toString().trim());
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder output = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }

            return output.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred";
        }
    }

    private double[] mapDeviceToInputData(Device device) {
        return new double[] {
            device.getBattery_power(),
            device.getBlue(),
            device.getClock_speed(),
            device.getDual_sim(),
            device.getFc(),
            device.getFour_g(),
            device.getInt_memory(),
            device.getM_dep(),
            device.getMobile_wt(),
            device.getN_cores(),
            device.getPc(),
            device.getPx_height(),
            device.getPx_width(),
            device.getRam(),
            device.getSc_h(),
            device.getSc_w(),
            device.getTalk_time(),
            device.getThree_g(),
            device.getTouch_screen(),
            device.getWifi(),        
        };
    }

}
