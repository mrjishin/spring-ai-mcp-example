/**
 * Copyright 2025 PICONET
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

package kr.co.piconet.ai.sample.mcp.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import kr.co.piconet.ai.sample.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class WeatherTool
{
	private final WeatherService weatherService;
	
	@Tool(
		description = "Get weather information by city name."
	)
    public String getWeather(String city)
	{

		log.info("---------------------------------------------------");
		log.info("Called getWeather...");
		log.info("---------------------------------------------------");

		String weather = weatherService.get(city);
		if(weather == null) {
			weather = "Unknown";
		}
		return String.format("The weather in %s is %s.", city, weather);
    }
}