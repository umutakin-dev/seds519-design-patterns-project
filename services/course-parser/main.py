# main.py

import os
import json
import tempfile
import PIL.Image
from fastapi import FastAPI, HTTPException
from fastapi.responses import JSONResponse
from pdf2image import convert_from_path
from gemini_ops import GeminiOperator

app = FastAPI()

# Initialize your GeminiOperator with the desired model.
gemini_operator = GeminiOperator("gemini-2.0-flash-001")

def get_images(pdf_path, directory):
    """
    Extracts high-quality images from the PDF and saves them locally.
    Returns a list of PIL Image objects.
    """
    images_dir = os.path.join(directory, "images")
    if not os.path.exists(images_dir):
        os.makedirs(images_dir)
    pages = convert_from_path(pdf_path, dpi=300)
    image_list = []
    for i, page in enumerate(pages, start=1):
        output_filename = os.path.join(images_dir, f"{i}.png")
        page.save(output_filename, "PNG")
        image = PIL.Image.open(output_filename)
        image_list.append(image)
    return image_list

def get_course_schedule(gemini_operator, image_list):
    """
    Prepend a prompt to the list of images and send them to Gemini
    for course schedule extraction. Returns the parsed JSON result.
    """
    prompt = (
        "You are provided with multiple images that represent pages from a course schedule. "
        "Extract the schedule from these images and output only a valid JSON array of courses. "
        "Each course must be an object with exactly these keys: 'day', 'courseName', 'time', 'instructor', and 'classroom'. "
        "For example, if there is one course on Monday, respond like: "
        "[{\"day\": \"Monday\", \"courseName\": \"Mathematics\", \"time\": \"09:00-10:00\", \"instructor\": \"Dr. Smith\", \"classroom\": \"Room 101\"}]. "
        "Do not include any extra text, markdown formatting, code fences, or explanations."
    )
    # Insert the prompt as the first element.
    image_list.insert(0, prompt)
    
    # Send the list (prompt + images) to Gemini.
    response = gemini_operator.get_response(
        contents=image_list,
        response_mime_type='application/json',
        response_schema=None  # You can pass a schema if available; otherwise, use None.
    )
    
    # Debug log the raw response.
    print("Raw Gemini response:", response["output_text"])
    
    # Clean up any unwanted prefix (e.g., if it starts with "json").
    raw_output = response["output_text"].strip()
    if raw_output.lower().startswith("json"):
        raw_output = "\n".join(raw_output.splitlines()[1:]).strip()
    
    return json.loads(raw_output)

@app.get("/process-static-pdf/")
def process_static_pdf():
    # Path to the static PDF file.
    pdf_path = os.path.join("static", "2024-25CourseSchedule.pdf")
    if not os.path.exists(pdf_path):
        raise HTTPException(status_code=404, detail="PDF file not found.")
    
    try:
        # Create a temporary directory to save extracted images.
        directory = "temp_volume"
        if not os.path.exists(directory):
            os.makedirs(directory)
        
        # Extract images from the PDF.
        image_list = get_images(pdf_path, directory)
        
        # Get the course schedule from Gemini using the extracted images.
        course_schedule = get_course_schedule(gemini_operator, image_list)
        
        # Build and return the final response.
        structured_response = {
            "course_schedule": course_schedule,
            # Optionally, include usage statistics:
            # "gemini_usage": gemini_operator.get_api_stats(response)
        }
        return JSONResponse(content=structured_response)
    
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


