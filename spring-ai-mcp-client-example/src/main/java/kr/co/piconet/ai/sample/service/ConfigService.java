/**
 * Copyright 2025 Jaeik Shin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kr.co.piconet.ai.sample.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class ConfigService
{
	private static final String KEY_MODEL = "model";

	private File configDir = new File(System.getProperty("user.home"), "piconet");
	private File configFile = new File(configDir, "pico-ai.properties");

	@PostConstruct
	private void init() {
		if(!configDir.exists())
			configDir.mkdirs();
	}

	public String getModel() {
		if(configFile.exists()) {
			Properties props = new Properties();
			try {
				props.load(new FileInputStream(configFile));
				return props.getProperty(KEY_MODEL);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public void saveModel(String model) {
		Properties props = new Properties();
		props.setProperty(KEY_MODEL, model);
		try {
			props.store(new FileWriter(configFile), "pico-ai configuration.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}