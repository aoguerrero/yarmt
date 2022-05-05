package io.github.aoguerrero.yarmt.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class MainController {

	private final Logger logger = LoggerFactory.getLogger(MainController.class);

	@RequestMapping(value = "post", method = RequestMethod.POST)
	public ResponseEntity<String> post(@RequestBody String body, @RequestHeader HttpHeaders headers) {
		logger.info("----- HEADERS -----");
		Set<String> keys = headers.keySet();
		for (String key : keys) {
			String value = headers.get(key) != null ? headers.get(key).toString() : "";
			logger.info(key + " : " + value);
		}
		logger.info("----- BODY -----");
		logger.info(body);
		try {
			InputStream inputStream = this.getClass().getResourceAsStream("/response.txt");
			return new ResponseEntity<>(inputStreamToString(inputStream), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/* */

	private String inputStreamToString(InputStream inputStream) throws IOException {
		StringBuilder resultStringBuilder = new StringBuilder();
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				resultStringBuilder.append(line).append("\n");
			}
		}
		return resultStringBuilder.toString();
	}

}
