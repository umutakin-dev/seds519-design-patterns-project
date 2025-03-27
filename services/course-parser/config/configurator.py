import json
import os
from dotenv import load_dotenv

load_dotenv(".env")


class Configurator:
    def __init__(self, path: str = "config/gemini_config.json"):
        self.cfg_path = path
        self.cfg = self.load_cfg(path)

    def get_in_out_price(self, model):
        return self.cfg["Cost"][model]

    @staticmethod
    def load_cfg(path):
        with open(path) as json_file:
            return json.load(json_file)

    @staticmethod
    def get_gpt_key():
        return str(os.getenv("GEMINI_API_KEY"))

    # @staticmethod
    # def get_aws_key():
    #     return str(os.getenv("AWS_ACCESS_KEY")), str(os.getenv("AWS_SECRET_KEY"))
