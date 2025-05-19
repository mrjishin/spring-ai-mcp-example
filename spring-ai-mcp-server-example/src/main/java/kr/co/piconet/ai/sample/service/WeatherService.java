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

package kr.co.piconet.ai.sample.service;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class WeatherService
{
	private static final Map<String,String> weathers = Map.of(
			"Seoul", "clear",
			"Busan", "cloudy",
			"Daejeon", "storm",
			"Ilsan", "typhoon",
			"Daegu", "heavy rain",
			"Gwangju", "rainy"
	);

	public String get(String city) {
		return weathers.containsKey(city) ? weathers.get(city) : "알수없음";
	}
}