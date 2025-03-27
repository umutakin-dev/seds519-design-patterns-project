# gemini_ops.py

import time
from google import genai
from google.genai import types
from config.configurator import Configurator  # Ensure you have this module or adjust as needed

class GeminiOperator:
    def __init__(self, model):
        self.cfg = Configurator()
        self.client = genai.Client(api_key=self.cfg.get_gpt_key())
        self.model = model

    def get_response(self, contents, response_mime_type, response_schema, max_attempts=3):
        for attempt_count in range(max_attempts):
            try:
                generate_content_config = types.GenerateContentConfig(
                    temperature=0,
                    top_p=0.95,
                    top_k=40,
                    max_output_tokens=8192,
                    response_mime_type=response_mime_type,
                    response_schema=response_schema,
                )
                response = self.client.models.generate_content(
                    model=self.model,
                    contents=contents,
                    config=generate_content_config
                )
                return {
                    "output_text": response.text,
                    "gemini_ai_usage": response.usage_metadata,
                    "attempt_time": attempt_count + 1
                }
            except Exception as e:
                print("Something went wrong in Gemini Prompt. Error msg is", e)
                time.sleep(5)

    def get_api_stats(self, response):
        in_token = response["gemini_ai_usage"].prompt_token_count
        out_token = response["gemini_ai_usage"].candidates_token_count
        cost_usd = self.get_cost(in_token, out_token)
        attempt_time = response["attempt_time"]
        return {
            "in_token": in_token,
            "out_token": out_token,
            "cost_usd": cost_usd,
            "attempt_time": attempt_time
        }

    def get_cost(self, in_token, out_token):
        costs = self.cfg.get_in_out_price(self.model)
        return ((costs["input"] / 1000000) * in_token) + ((costs["output"] / 1000000) * out_token)


