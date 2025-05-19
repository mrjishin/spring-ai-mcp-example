# pip install mcp

from mcp.server.fastmcp import FastMCP
import logging
import sys

# 로깅 설정
logging.basicConfig(
    level=logging.DEBUG,
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s',
    handlers=[
        logging.StreamHandler(sys.stdout)
    ]
)

logger = logging.getLogger("mcp-server")

# Create an MCP server
# mcp = FastMCP("MCP 서버")
mcp = FastMCP(name="MCP 서버", debug=True, port = 8081)

# Add an addition tool
@mcp.tool()
def add(a: int, b: int) -> int:
    """두 숫자를 더합니다"""
    message = f"{a} 더하기 {b}"
    logger.info(message)  # 로거를 통한 로그 출력
    return a + b

@mcp.tool()
def echo_tool(title: str, question: str) -> str:
    """상담내역을 등록합니다."""
    message = "상담 내역이 등록되었습니다. (ID: 100)"
    log_message = f"Tool echo: {message}"
    logger.info(log_message)  # 로거를 통한 로그 출력
    return message

@mcp.prompt()
def echo_prompt(message: str) -> str:
    """Create an echo prompt"""
    log_message = f"Prompt echo: {message}"
    logger.info(log_message)
    return f"Please process this message: {message}"

# Add a dynamic greeting resource
@mcp.resource("greeting://{name}")
def get_greeting(name: str) -> str:
    """Get a personalized greeting"""
    log_message = f"Greeting for: {name}"
    logger.info(log_message)
    return f"Hello, {name}!"

@mcp.resource("echo://{message}")
def echo_resource(message: str) -> str:
    """Echo a message as a resource"""
    log_message = f"Resource echo: {message}"
    logger.info(log_message)
    return f"Resource echo: {message}"


if __name__ == "__main__":
    logger.info("Starting MCP server...")
    # transport 옵션을 변경해봅니다
    mcp.run(transport="sse")