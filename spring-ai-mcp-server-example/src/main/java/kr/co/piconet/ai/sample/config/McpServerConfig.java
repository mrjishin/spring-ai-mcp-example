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

package kr.co.piconet.ai.sample.config;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import kr.co.piconet.ai.sample.mcp.tools.InquiryTool;
import kr.co.piconet.ai.sample.mcp.tools.WeatherTool;

@Configuration
class McpServerConfig
{
    @Bean
    ToolCallbackProvider toolCallbackProvider(
    		WeatherTool weatherTool,
    		InquiryTool noticeTool) {
        return MethodToolCallbackProvider
        		.builder()
        		.toolObjects(weatherTool, noticeTool)
        		.build();
    }
}