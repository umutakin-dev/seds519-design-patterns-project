package com.group3.backend.io;

public class FileReaderConverter {
    public static AdapterFileReader getFileReader(String fileType) {
        if (fileType.equals("pdf")) {
            return new PdfReader();
        }

        else if (fileType.equals("excel")){
            return new ExcelReader();
        }

        else {
            return null;
        }
    }
}
